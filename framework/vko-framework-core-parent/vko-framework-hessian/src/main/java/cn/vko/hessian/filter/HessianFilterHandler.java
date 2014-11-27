/**
 * FilterHandler.java
 * cn.vko.hessian.filter.interceptor
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.hessian.filter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.vko.hessian.core.HessianServiceConfig;
import cn.vko.hessian.core.MetadataProcessor;
import cn.vko.hessian.core.ServiceHandler;
import cn.vko.hessian.core.ServiceMetaData;
import cn.vko.hessian.core.VkoHessianFactory;
import cn.vko.hessian.object.RequestWrapper;

import com.caucho.hessian.io.AbstractHessianInput;
import com.caucho.hessian.io.AbstractHessianOutput;
import com.caucho.hessian.io.HessianInputFactory;
import com.caucho.hessian.io.SerializerFactory;
import com.caucho.services.server.ServiceContext;
import com.vko.core.common.sign.Base64;
import com.vko.core.common.util.Lz4Compress;

/**
 * 调用
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-7 	 
 */
public class HessianFilterHandler {
	private final Logger log = LoggerFactory.getLogger(HessianFilterHandler.class);
	private HessianInputFactory _inputFactory = new HessianInputFactory();
	private VkoHessianFactory _hessianFactory = new VkoHessianFactory();
	private SerializerFactory _serializerFactory;
	private String prefix = "/apis/";

	public HessianFilterHandler(HessianServiceConfig config) {
		this.prefix = config.getPrefix();
	}

	public HessianFilterHandler(String prefix) {
		this.prefix = prefix;
	}

	public HessianFilterHandler() {
	}

	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		String uri = request.getRequestURI();
		if (!uri.startsWith(prefix)) {
			response.setHeader("Content-Type", "text/plain; charset=UTF-8");
			response.sendError(404, "没找到对应的服务");
			return;
		}
		if (uri.endsWith("/")) {
			uri = uri.substring(0, uri.length() - 1);
		}

		String serviceName = uri.substring(uri.lastIndexOf("/") + 1);
		handleService(serviceName, request, response, os);
	}

	private void handleService(final String serviceName, final HttpServletRequest request,
			final HttpServletResponse response, final ByteArrayOutputStream os) throws IOException {

		service(serviceName, request, os);

		writeResponse(request, response, os);
	}

	private void writeResponse(HttpServletRequest request, HttpServletResponse response, ByteArrayOutputStream os)
			throws IOException {
		String keep = request.getHeader("Connection");
		boolean keepAlive = false;
		if ("keep-alive".equals(keep)) {
			keepAlive = true;
		}
		String accept = request.getHeader("Accept-Encoding");
		String encrypt = request.getHeader("Encrypt");
		String compress = request.getHeader("Compress");
		byte[] result = os.toByteArray();
		if (compress != null && compress.contains("lz4")) {
			response.setHeader("content-encoding", "lz4");
			result = Lz4Compress.compress(result);
		} else if (accept != null && accept.contains("deflate")) {
			// 压缩支持
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			response.setHeader("content-encoding", "deflate");
			DeflaterOutputStream output = new DeflaterOutputStream(out, new Deflater(Deflater.BEST_COMPRESSION, true));
			output.write(result);
			output.close();
			result = out.toByteArray();
		}
		// 加密
		if (encrypt != null && encrypt.contains("yes")) {
			result = _hessianFactory.getEncrypt().encrypt(result);
		}
		if (keepAlive) {
			response.setHeader("content-length", result.length + "");
		}
		response.addHeader("Content-Type", "x-application/hessian");
		response.getOutputStream().write(result);

	}

	public void setSerializerFactory(SerializerFactory factory) {
		_serializerFactory = factory;
	}

	/**
	 * Gets the serializer factory.
	 */
	public SerializerFactory getSerializerFactory() {
		if (_serializerFactory == null)
			_serializerFactory = new SerializerFactory();

		return _serializerFactory;
	}

	private String[] getUsernameAndPassword(HttpServletRequest req) {
		String auths = req.getHeader("Authorization");
		if (auths == null) {
			String str[] = { "", "" };
			return str;
		}
		String auth[] = auths.split(" ");
		String bauth = auth[1];
		String dauth = new String(Base64.decode(bauth));
		String authLink[] = dauth.split(":");
		return authLink;
	}

	private void service(String serviceName, HttpServletRequest req, ByteArrayOutputStream os) throws IOException {
		byte[] bytes = IOUtils.toByteArray(req.getInputStream());//get request content
		InputStream is = new ByteArrayInputStream(bytes);

		SerializerFactory serializerFactory = getSerializerFactory();
		String username = null;
		String password = null;
		String[] authLink = getUsernameAndPassword(req);
		username = authLink[0].equals("") ? null : authLink[0];
		password = authLink[1].equals("") ? null : authLink[1];
		String clientIP = req.getRemoteAddr();
		RequestWrapper rw = new RequestWrapper(username, password, clientIP, serviceName);

		invoke(rw, is, os, serializerFactory);
	}

	private void invoke(RequestWrapper rw, InputStream is, OutputStream os, SerializerFactory serializerFactory) {
		AbstractHessianInput in = null;
		AbstractHessianOutput out = null;
		//		String username = rw.getUser();
		//		String password = rw.getPassword();
		try {

			HessianInputFactory.HeaderType header = _inputFactory.readHeader(is);

			switch (header) {
			case CALL_1_REPLY_1:
				in = _hessianFactory.createHessianInput(is);
				out = _hessianFactory.createHessianOutput(os);
				break;

			case CALL_1_REPLY_2:
				in = _hessianFactory.createHessianInput(is);
				out = _hessianFactory.createHessian2Output(os);
				break;

			case HESSIAN_2:
				in = _hessianFactory.createHessian2Input(is);
				in.readCall();
				out = _hessianFactory.createHessian2Output(os);
				break;

			default:
				throw new IllegalStateException(header + " is an unknown Hessian call");
			}

			if (serializerFactory != null) {
				in.setSerializerFactory(serializerFactory);
				out.setSerializerFactory(serializerFactory);
			}

			//			if (username == null || password == null) {
			//				Exception exception = new RuntimeException(
			//						"the client can't offer the user or password infor,please check.");
			//				out.writeFault("ServiceException", exception.getMessage(), exception);
			//				log.error("the client can't offer the user or password infor,now we have refused.");
			//				throw exception;
			//			}
			invoke(rw, in, out);
		} catch (Exception e) {
			log.error("", e);
			try {
				if (out != null) {
					out.writeFault("ServiceException", e.getMessage(), e);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (in != null) {
					in.close();

				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	private void invoke(RequestWrapper rw, AbstractHessianInput in, AbstractHessianOutput out) throws Exception {
		ServiceContext context = ServiceContext.getContext();

		String serviceName = rw.getServiceName();

		// backward compatibility for some frameworks that don't read
		// the call type first
		in.skipOptionalCall();

		// Hessian 1.0 backward compatibility
		String header;
		while ((header = in.readHeader()) != null) {
			Object value = in.readObject();

			context.addHeader(header, value);
		}
		ServiceMetaData metaData = MetadataProcessor.getServiceMetaData(serviceName);
		if (metaData == null) {
			log.error("service " + serviceName + " can't find.");
			out.writeFault("NoSuchService", "service " + serviceName + " can't find.", null);
			out.close();
			return;
		}
		String methodName = in.readMethod();
		int argLength = in.readMethodArgLength();

		Method method = metaData.getMethod(methodName + "__" + argLength);

		if (method == null) {
			method = metaData.getMethod(methodName);
		}
		if (method == null) {
			out.writeFault("NoSuchMethod", "service[" + methodName + "]'s method " + methodName + " cannot find", null);
			out.close();
			return;
		}
		Class<?>[] argTypes = method.getParameterTypes();
		Object[] argObjs = new Object[argTypes.length];
		for (int i = 0; i < argTypes.length; i++) {
			argObjs[i] = in.readObject(argTypes[i]);
		}

		//wrap the request to a wapper
		rw.setMethodName(method.getName());
		rw.setArgs(argObjs);
		rw.setArgsTypes(argTypes);

		if (argLength != argObjs.length && argLength >= 0) {
			out.writeFault("NoSuchMethod", "service[" + methodName + "]'s method " + methodName
					+ " argument length mismatch, received length=" + argLength, null);
			out.close();
			return;
		}

		Object result = null;

		try {
			//handle request
			result = ServiceHandler.handleRequest(rw);
		} catch (Exception e) {
			Throwable e1 = e;
			if (e1 instanceof InvocationTargetException)
				e1 = ((InvocationTargetException) e).getTargetException();

			log.debug(this + " " + e1.toString(), e1);
			result = e;
			out.writeFault("ServiceException", e1.getMessage(), e1);
			out.close();
			return;
		}

		// The complete call needs to be after the invoke to handle a
		// trailing InputStream
		in.completeCall();

		out.writeReply(result);

		out.close();
	}

}
