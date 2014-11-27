package cn.vko.hessian.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import cn.vko.hessian.object.RequestWrapper;
import cn.vko.hessian.object.Service;
import cn.vko.hessian.object.ServiceProvider;
import cn.vko.hessian.object.ServiceVersion;

import com.esotericsoftware.reflectasm.MethodAccess;

/**
 * @author guolei
 */
public class ServiceHandler {

	private static Logger logger = LoggerFactory.getLogger(ServiceHandler.class);

	// key:service name value:service
	public static final Map<String, Service> serviceMap = new HashMap<String, Service>();

	// key:client user # service name value:version
	public static final Map<String, String> versionMap = new HashMap<String, String>();

	protected static ApplicationContext context;

	/**
	 * 1.if service has a non default version,add 2.put service to map
	 * 
	 * @param service
	 */
	public static void addToServiceMap(Service service) {
		if (service.getDefaultVersion() == null) {
			Iterator<String> iter = service.getServiceProvider().keySet().iterator();
			if (iter.hasNext()) {
				String defaultVersion = iter.next();
				service.setDefaultVersion(defaultVersion);
			} else {
				throw new RuntimeException("your have a wrong in service config.check service[" + service.getName()
						+ "]'s provider.");
			}
		}
		serviceMap.put(service.getName(), service);
	}

	/**
	 * check whether service is exits according service name
	 * 
	 * @param serviceName
	 * @return true exits false not exits
	 */
	public static boolean isServiceExits(String serviceName) {
		return serviceMap.containsKey(serviceName);
	}

	/**
	 * put version info to map
	 * 
	 * @param version
	 */
	public static void addToVersionMap(ServiceVersion version) {
		if (version.getVersion() == null) {
			return;
		}
		if (!isServiceExits(version.getService())) {
			throw new RuntimeException("please check your configure file,service[" + version.getService()
					+ "] can't find.");
		}
		StringBuffer user = version.getUser();
		if (user.length() > 0) {
			user.append("#");
		}
		versionMap.put(user.append(version.getService()).toString(), version.getVersion());
	}

	/**
	 * according the request to invoke the method and return the invoke result
	 * 1.get serviceName,methodName,user,password,version 2.get version 3.get
	 * provider 4.invoke
	 */
	public static Object handleRequest(RequestWrapper request) {
		Object result = null;
		//		if (logger.isDebugEnabled()) {
		//			ServiceReporter.reportBeforeInvoke(request);
		//		}

		try {
			String serviceName = request.getServiceName();
			String methodName = request.getMethodName();
			String user = request.getUser();
			//			String password = request.getPassword();

			//			boolean isRight = HettySecurity.checkPermission(user, password);
			//			if (!isRight) {
			//				throw new RuntimeException("the user or password is wrong,please check your user and password.");
			//			}
			StringBuffer serviceKey = new StringBuffer();
			if (user != null) {
				serviceKey.append(user);
				serviceKey.append("#");
			}
			serviceKey.append(serviceName);
			String version = versionMap.get(serviceKey.toString());
			Service service = serviceMap.get(serviceName);
			if (service == null) {
				throw new RuntimeException("we cannot find service[" + serviceName + "].");
			}

			ServiceProvider serviceProvider = service.getProviderByVersion(version);
			// 从spring中获取
			Object processor = serviceProvider.getProcessor();
			Object[] args = request.getArgs();

			MethodAccess method = serviceProvider.getMethodAccess();
			int methodIndex = method.getIndex(methodName, request.getArgsTypes());
			result = method.invoke(processor, methodIndex, args);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return result;
	}

	public static void setApplicationContext(ApplicationContext arg0) throws BeansException {
		context = arg0;
	}

	public static ApplicationContext getApplicationContext() {
		return context;
	}

	public static Map<String, Service> getServiceMap() {
		return Collections.unmodifiableMap(serviceMap);
	}

	public static Service getServiceByName(String serviceName) {
		return serviceMap.get(serviceName);
	}
}
