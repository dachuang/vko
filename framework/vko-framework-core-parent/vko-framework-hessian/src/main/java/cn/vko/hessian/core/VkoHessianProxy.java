/*
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2001-2004 Caucho Technology, Inc.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Caucho Technology (http://www.caucho.com/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "Hessian", "Resin", and "Caucho" must not be used to
 *    endorse or promote products derived from this software without prior
 *    written permission. For written permission, please contact
 *    info@caucho.com.
 *
 * 5. Products derived from this software may not be called "Resin"
 *    nor may "Resin" appear in their names without prior written
 *    permission of Caucho Technology.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL CAUCHO TECHNOLOGY OR ITS CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * @author Scott Ferguson
 */

package cn.vko.hessian.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import org.apache.commons.io.IOUtils;

import com.caucho.hessian.client.HessianConnection;
import com.caucho.hessian.client.HessianProxy;
import com.vko.core.common.util.Lz4Compress;

/**
 * Proxy implementation for Hessian clients. Applications will generally use
 * HessianProxyFactory to create proxy clients.
 */
public class VkoHessianProxy extends HessianProxy {

	private static final long serialVersionUID = -2297995560232700247L;

	protected VkoHessianProxyFactory factory;

	public VkoHessianProxy(URL url, VkoHessianProxyFactory factory, Class<?> type) {
		super(url, factory, type);
		this.factory = factory;
	}

	@Override
	protected InputStream getInputStream(HessianConnection conn) throws IOException {
		InputStream is = conn.getInputStream();
		byte[] content = IOUtils.toByteArray(is);
		is.close();
		if (factory.isEncrypt()) {
			// 解密
			content = factory.getEncrypt().decrypt(content);
		}
		if ("lz4".equals(conn.getContentEncoding())) {
			content = Lz4Compress.uncompress(content);
			is = new ByteArrayInputStream(content);
		} else if ("deflate".equals(conn.getContentEncoding())) {
			is = new InflaterInputStream(new ByteArrayInputStream(content), new Inflater(true));
		}
		return is;
	}

	/**
	 * Method that allows subclasses to add request headers such as cookies.
	 * Default implementation is empty.
	 */
	@Override
	protected void addRequestHeaders(HessianConnection conn) {
		// 是否加密
		if (factory.isEncrypt()) {
			conn.addHeader("Encrypt", "yes");
		}
		conn.addHeader("Compress", "lz4");
		super.addRequestHeaders(conn);
	}

	/**
	 * Method that allows subclasses to parse response headers such as cookies.
	 * Default implementation is empty.
	 * 
	 * @param conn
	 */
	@Override
	protected void parseResponseHeaders(URLConnection conn) {
		// 验证登陆状态
	}

}
