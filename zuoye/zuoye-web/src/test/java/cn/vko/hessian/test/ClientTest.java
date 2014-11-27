/**
 * ClientTest.java
 * cn.vko.hessian.test
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.hessian.test;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import cn.vko.hessian.core.VkoHessianProxyFactory;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 * @author   宋汝波
 * @Date	 2014-7-3 	 
 */
public class ClientTest {

	public static void main(String[] args) throws IOException {
		String url = "http://localhost:9771/apis/hello";
		VkoHessianProxyFactory fac = new VkoHessianProxyFactory();
		fac.setHessian2Reply(true);
		fac.setHessian2Request(true);
		fac.setOverloadEnabled(true);
		//		fac.setEncrypt(true);
		//		fac.set
		Hello api = (Hello) fac.create(Hello.class, url);
		System.out.println(api.hello("sfsd"));

		String sss = IOUtils.toString(new FileInputStream("e:/Cards11_54256.txt"));
		//
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			api.hello("45666666666666666666");
		}
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}
}
