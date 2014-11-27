/**
 * Test.java
 * cn.vko.zuoye.test
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.test;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 * @author   宋汝波
 * @Date	 2014-7-18 	 
 */
public class Test {

	/**
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param args TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 * @throws IOException 
	*/

	public static void main(String[] args) throws IOException {

		Document doc = Jsoup.connect("http://www.dookbook.com").get();
		//		doc.setBaseUri("");
		Element link = doc.select("a").first();

		//link.absUrl("href");
		//String relHref = link.attr("href"); // == "/"
		//String absHref = link.attr("abs:href");
		//System.out.println(absHref);
		System.out.println(link.absUrl("href"));
		System.out.println(link.attr("href"));

	}

}
