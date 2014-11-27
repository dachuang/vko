package com.vko.core.common.util.videoextract;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.vko.core.common.util.gson.GsonUtil;


/**
 * 视频网址视频信息提取工具类 支持以下网址：优酷、土豆、酷6、6间房、新浪、搜狐、56(我乐)、凤凰视频 版本更新说明： 1、2011-09-12 创建
 * 2、2011-09-13 新加对凤凰视频的支持，链接地址不在支持的列表中时返回原链接地址以及链接的页面标题
 * 
 * @author Guihua Liu
 * @version v1.0
 * @QQ 459041012
 * @email 850823422@139.com
 * @Website http://liuguihua.com
 */
public class VideoUtil {

	/**
	 * 获取视频信息
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static Video getVideoInfo(String url) throws Exception {
		Video video = null;

		if (url.indexOf(Constants.VIDEO_DOMAIN_YOUKU) != -1) {
			try {
				video = getYouKuVideo(url);
			} catch (Exception e) {
				video = null;
			}
		} else if (url.indexOf(Constants.VIDEO_DOMAIN_TUDOU) != -1) {
			try {
				video = getTudouVideo(url);
			} catch (Exception e) {
				video = null;
			}
		} else if (url.indexOf(Constants.VIDEO_DOMAIN_KU6) != -1) {
			try {
				video = getKu6Video(url);
			} catch (Exception e) {
				video = null;
			}
		}/*
		 * else if (url.indexOf(Constants.VIDEO_DOMAIN_CN6) != -1) { try { video
		 * = get6Video(url); } catch (Exception e) { video = null; } }
		 */else if (url.indexOf(Constants.VIDEO_DOMAIN_WOLE) != -1) {
			try {
				video = get56Video(url);
			} catch (Exception e) {
				video = null;
			}
		} else if (url.indexOf(Constants.VIDEO_DOMAIN_SINA) != -1) {
			try {
				video = getSinaVideo(url);
			} catch (Exception e) {
				video = null;
			}
		} else if (url.indexOf(Constants.VIDEO_DOMAIN_SOHU) != -1) {
			try {
				video = getSohuVideo(url);
			} catch (Exception e) {
				video = null;
			}
		} else if (url.indexOf(Constants.VIDEO_DOMAIN_IFENG) != -1) {
			try {
				video = getIfengVideo(url);
			} catch (Exception e) {
				video = null;
			}
		} else if (url.indexOf(Constants.VIDEO_DOMAIN_YINYUETAI) != -1) {
			try {
				video = getYinYueTaiVideo(url);
			} catch (Exception e) {
				video = null;
			}
		} else if (url.indexOf(Constants.VIDEO_DOMAIN_NETEASE) != -1) {
			try {
				video = getNetEaseVideo(url);
			} catch (Exception e) {
				video = null;
			}
		} /*else if (url.indexOf(Constants.VIDEO_DOMAIN_QQ) != -1) {
			try {
				video = getQQVideo(url);
			} catch (Exception e) {
				video = null;
			}
		} */else {
			// 链接地址不在支持的列表中时返回原链接地址以及链接的页面标题
			Document doc = getURLContent(url);
			video = new Video();
			video.setTitle(doc.title());
			video.setPageUrl(url);
		}

		return video;
	}

	/**
	 * 获取优酷视频
	 * 
	 * @param url
	 *            视频URL
	 */
	public static Video getYouKuVideo(String url) throws Exception {
		Document doc = getURLContent(url);

		/**
		 * 视频标题
		 */
		String title = doc.title().split("—")[0].trim();

		/**
		 * 获取视频名称
		 * 
		 *//*
		String name = "";
		try {
		    name = getElementAttrById(doc, "subtitle");
		}catch(Exception e) {
		    name = getElementAttrById(doc, "title");
		}*/
		

		/**
		 * 获取视频描述
		 * 
		 */
		String description = getElementAttrByIdAndClass(doc, "long", "item");
		if (StringUtils.isEmpty(description)) {
			description = getElementAttrByIdAndClass(doc, "show_info_short",
					"short");
		}

		/**
		 * 获取视频缩略图
		 */
		String pic = getElementAttrById(doc, "s_msn1", "href");
		int local = pic.indexOf("screenshot=");
		pic = pic.substring(local + 11);

		/**
		 * 获取视频地址
		 */
		String flash = getElementAttrById(doc, "link2", "value");

		/**
		 * 获取视频网页代码
		 */
		String htmlCode = getElementAttrById(doc, "link3", "value");

		/**
		 * 获取视频时间
		 */
		/*
		 * String time = getElementAttrById(doc, "download", "_href"); String[]
		 * arrays = time.split("\\|"); time = arrays[4];
		 */
		Video video = new Video();
		video.setTitle(title);
		video.setThumbnail(pic);
		video.setFlashUrl(flash);
		// video.setTime(time);
		video.setSource("优酷视频");
		video.setPageUrl(url);
		video.setSummary(description);
		video.setHtmlCode(htmlCode);

		return video;
	}

	/**
	 * 获取土豆视频
	 * 
	 * @param url
	 *            视频URL
	 */
	public static Video getTudouVideo(String url) throws Exception {
		Document doc = getURLContent(url);
		String content = doc.html();
		int beginLocal = content.indexOf("<script>document.domain");
		int endLocal = content
				.indexOf("<div class=\"g-mini g-mini-play\" id=\"gTop\">");
		content = content.substring(beginLocal, endLocal);

		/**
		 * 视频标题
		 */
		String title = getElementAttrById(doc, "vcate_title");

		/**
		 * 获取视频地址
		 */
		String flash = getScriptVarByName("icode", content);
		flash = "http://www.tudou.com/v/" + flash + "/v.swf";

		/**
		 * 获取视频缩略图
		 */
		String pic = getScriptVarByName("pic", content);

		/**
		 * 视频简介
		 */
		String summary = doc.select("meta[name=Description]").attr("content");

		/**
		 * 
		 * 视频描述
		 * 
		 */
		// String desc = getScriptVarByName("desc", content);

		/**
		 * 视频关键词
		 * 
		 */
		// String keys = getScriptVarByName("k", content);

		/**
		 * 获取视频时间
		 */
		String time = getScriptVarByName("time", content);

		Video video = new Video();
		video.setTitle(title);
		video.setThumbnail(pic);
		video.setFlashUrl(flash);
		video.setTime(time);
		video.setSource("土豆视频");
		video.setPageUrl(url);
		video.setSummary(summary);
		video.setHtmlCode(getHtmlCode(flash));

		return video;
	}

	/**
	 * 获取酷6视频
	 * 
	 * @param url
	 *            视频URL
	 */
	public static Video getKu6Video(String url) throws Exception {
		Document doc = getURLContent(url);

		/**
		 * 获取视频标题
		 */
		// String title = doc.title().split(" ")[0].trim();
		String title = doc.select("meta[name=title]").attr("content");

		/**
		 * 获取视频地址
		 */
		String flash = getElementAttrById(doc, "swf_url", "value");

		/**
		 * 获取视频地址
		 */
		String htmlCode = getElementAttrById(doc, "embed_code", "value");

		/**
		 * 获取视频缩略图
		 */
		String content = doc.html();
		int beginLocal = content.indexOf("<script>");
		int endLocal = content.indexOf("</script>");
		content = content.substring(beginLocal, endLocal);
		String pic = getScriptVarByName("cover", content).replace("\"", " ")
				.trim();

		/*
		 * String pic = getElementAttrById(doc, "zt_xl", "href"); int local =
		 * pic.indexOf("pic="); pic = pic.substring(local + 11);
		 */

		/**
		 * 视频简介
		 */
		String summary = doc.select("meta[name=Description]").attr("content");

		Video video = new Video();
		video.setTitle(title);
		video.setThumbnail(pic);
		video.setFlashUrl(flash);
		// video.setTime(time);
		video.setSource("酷6视频");
		video.setPageUrl(url);
		video.setSummary(summary);
		video.setHtmlCode(htmlCode);
		return video;

	}

	/**
	 * 获取6间房视频
	 * 
	 * @param url
	 *            视频URL
	 */
	public static Video get6Video(String url) throws Exception {
		Document doc = getURLContent(url);

		/**
		 * 视频标题
		 */
		String title = doc.title().split("-")[0].trim();

		/**
		 * 获取视频缩略图
		 */
		Element picEt = doc.getElementsByClass("summary").first();
		String pic = picEt.getElementsByTag("img").first().attr("src");

		/**
		 * 视频简介
		 */
		String summary = doc.select("meta[name=Description]").attr("content");

		/**
		 * 获取视频地址
		 */
		Element flashEt = doc.getElementById("video-share-code");
		doc = Jsoup.parse(flashEt.attr("value"));
		String flash = doc.select("embed").attr("src");

		Video video = new Video();
		video.setTitle(title);
		video.setThumbnail(pic);
		video.setFlashUrl(flash);
		video.setSource("6间房");
		video.setPageUrl(url);
		video.setSummary(summary);
		video.setHtmlCode(getHtmlCode(flash));

		return video;
	}

	/**
	 * 获取56视频
	 * 
	 * @param url
	 *            视频URL
	 */
	public static Video get56Video(String url) throws Exception {
		Document doc = getURLContent(url);
		String content = doc.html();

		/**
		 * 视频标题
		 */
		String title = doc.getElementById("VideoTitle").select("h1").text();

		/**
		 * 获取视频缩略图
		 */
		int begin = content.indexOf("\"img\":\"");
		content = content.substring(begin + 7, begin + 200);
		int end = content.indexOf("\"};");
		String pic = content.substring(0, end).trim();
		pic = pic.replaceAll("\\\\", "");

		/**
		 * 获取视频地址
		 */
		String flash = "http://player.56.com"
				+ url.substring(url.lastIndexOf("/"), url.lastIndexOf(".html"))
				+ ".swf";

		/**
		 * 视频简介
		 */
		String summary = doc.select("meta[name=Description]").attr("content");

		Video video = new Video();
		video.setTitle(title);
		video.setThumbnail(pic);
		video.setFlashUrl(flash);
		video.setSource("56视频");
		video.setPageUrl(url);
		video.setSummary(summary);
		video.setHtmlCode(getHtmlCode(flash));
		return video;
	}

	/**
	 * 获取新浪视频
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static Video getSinaVideo(String url) throws Exception {
		Document doc = getURLContent(url);

		/**
		 * 视频标题
		 */
		String title = doc.getElementById("videoTitle").text();

		/**
		 * 视频简介
		 */
		String summary = doc.getElementById("videoContent").text();

		String content = doc.html();
		int beginLocal = content.indexOf("document.domain");
		int endLocal = content.indexOf("</script>");
		content = content.substring(beginLocal + 2, endLocal);

		/**
		 * 视频缩略图
		 */
		String pic = getScriptVarByName("pic", content);

		/**
		 * flash地址
		 */
		String flash = getScriptVarByName("swfOutsideUrl", content);

		Video video = new Video();
		video.setTitle(title);
		video.setThumbnail(pic);
		video.setFlashUrl(flash);
		video.setSource("新浪视频");
		video.setPageUrl(url);
		video.setSummary(summary);
		video.setHtmlCode(getHtmlCode(flash));
		return video;
	}

	/**
	 * 获取搜狐视频
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static Video getSohuVideo(String url) throws Exception {
		Document doc = getURLContent(url);

		/**
		 * 视频标题
		 */
		String title = doc.title().split("-")[0].trim();

		/**
		 * 视频简介
		 */
		String summary = doc.select(".vIntro.clear > p").text();

		/**
		 * 视频缩略图
		 */
		String thumbnail = doc.getElementById("thumbnail").attr("src");

		String videoId = thumbnail.split("_")[2];

		/**
		 * 视频FLASH地址
		 */
		String flash = "http://share.vrs.sohu.com/" + videoId
				+ "/v.swf&autoplay=false";

		Video video = new Video();
		video.setTitle(title);
		video.setThumbnail(thumbnail);
		video.setFlashUrl(flash);
		video.setSource("搜狐视频");
		video.setPageUrl(url);
		video.setSummary(summary);
		video.setHtmlCode(getHtmlCode(flash));
		return video;
	}

	/**
	 * 获取凤凰视频
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static Video getIfengVideo(String url) throws Exception {
		Document doc = getURLContent(url);
		String content = doc.html();
		int beginLocal = content.indexOf("var videoinfo = {");
		int endLocal = content.indexOf("videoseid");
		content = content.substring(beginLocal, endLocal - 4);

		content = content.substring(content.indexOf("{"),
				content.indexOf("}") + 1);

		/**
		 * 视频标题
		 */
		String title = doc.title().split("-")[0].trim();

		/**
		 * 视频简介
		 */
		// String summary = getScriptVarByName("shortDescription", content);
		String summary = doc.select("meta[name=Description]").attr("content");

		/**
		 * 视频FLASH地址
		 */
		String videoId = GsonUtil.fromJson(content, GsonUtil.EntityVideo.class)
				.getId();
		String flash = "http://v.ifeng.com/include/exterior.swf?guid="
				+ videoId + "&AutoPlay=false";

		/**
		 * 视频缩略图
		 */
		String thumbnail = GsonUtil.fromJson(content,
				GsonUtil.EntityVideo.class).getImg();

		Video video = new Video();
		video.setTitle(title);
		video.setThumbnail(thumbnail);
		video.setFlashUrl(flash);
		// video.setTime(hour+":"+minute+":"+second);
		video.setSource("凤凰视频");
		video.setPageUrl(url);
		video.setSummary(summary);
		video.setHtmlCode(getHtmlCode(flash));
		return video;
	}

	/**
	 * 获取音悦台MV
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static Video getYinYueTaiVideo(String url) throws Exception {
		Document doc = getURLContent(url);

		/**
		 * 获取视频地址
		 */
		String flash = getElementAttrById(doc, "flashCode", "value");

		/**
		 * 视频标题
		 */
		String title = getElementAttrById(doc, "videoTitle") + " - "
				+ getElementAttrById(doc, "videoArtistName");

		/**
		 * 视频内容
		 */
		String summary = doc.select("meta[property=og:description]").attr(
				"content");

		/**
		 * 视频缩略图
		 */
		String thumbnail = doc.select("meta[property=og:image]")
				.attr("content");

		Video video = new Video();
		video.setTitle(title);
		video.setThumbnail(thumbnail);
		video.setFlashUrl(flash);
		video.setSource("音悦台MV");
		video.setPageUrl(url);
		video.setSummary(summary);
		video.setHtmlCode(getHtmlCode(flash));
		return video;
	}

	/**
	 * 获取网易视频
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static Video getNetEaseVideo(String url) throws Exception {
		Document doc = getURLContent(url);

		String content = doc.html();
		content = content.substring(content.indexOf("topicid"));
		content = content.substring(0, content.indexOf("</script>"));
		content = content.replaceAll("\"", "").replaceAll("\n", "").trim();
		content = content.substring(0, content
				.indexOf("name=flashvars></object>"));

		String[] conArrays = content.split("&");
		/**
		 * 视频标题
		 */
		String title = getElementAttrById(doc, "js_Set");

		/**
		 * 视频缩略图
		 */
		String imgpath = getScriptVarByName("coverpic=", conArrays).get(0);

		String topicid = getScriptVarByName("topicid=", conArrays).get(0);
		String vid = getScriptVarByName("vid=", conArrays).get(0);

		/**
		 * 视频简介
		 * 
		 */
		String summary = doc.select("meta[name=Description]").attr("content");

		/**
		 * 视频地址
		 */
		String flash = "http://img1.cache.netease.com/flvplayer081128/~false~"
				+ topicid + "_" + vid + "~"
				+ imgpath.substring(7, imgpath.length() - 4) + "~.swf";
		Video video = new Video();
		video.setTitle(title);
		video.setThumbnail(imgpath);
		video.setFlashUrl(flash);
		video.setSource("网易视频");
		video.setPageUrl(url);
		video.setSummary(summary);
		video.setHtmlCode(getHtmlCode(flash));
		return video;
	}

	/**
	 * 获取腾讯视频
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static Video getQQVideo(String url) throws Exception {
		Document doc = getURLContent(url);
		/**
		 * 视频标题
		 */
		String vid = url.substring(url.lastIndexOf("/") + 1, url
				.lastIndexOf("."));
		System.out.println(vid);
		String title = doc.title().split("-")[0].trim();
		/**
		 * 视频地址
		 */
		String flash = "http://imgcache.qq.com/tencentvideo_v1/player/TencentPlayer.swf?vid="
				+ vid;
		Video video = new Video();
		video.setTitle(title);
		video.setSource("腾讯视频");
		video.setFlashUrl(flash);
		return video;
	}

	/**
	 * 获取script某个变量的值
	 * 
	 * @param name
	 *            变量名称
	 * @return 返回获取的值
	 */
	private static String getScriptVarByName(String name, String content) {
		String script = content;
		int begin = script.indexOf(name);
		script = script.substring(begin + name.length() + 2);
		int end = script.indexOf(",");
		script = script.substring(0, end);
		String result = script.replaceAll("'", "");
		return result.trim();
	}

	/**
	 * 获取script某个变量的值
	 * 
	 * @param name
	 *            变量名称
	 * @return 返回获取的值
	 */
	private static List<String> getScriptVarByName(String name,
			String[] contents) {
		List<String> list = new ArrayList<String>();
		if (null != contents && 0 < contents.length) {
			for (String str : contents) {
				int begin = str.indexOf(name);
				if (name.equals("coverpic=") && begin != -1) {
					list.add(str.substring(begin + 9, str.length()));
				} else if (name.equals("topicid=") && begin != -1 ) {
					list.add(str.substring(begin + 8, str.length()));
				} else if (name.equals("vid=") && begin != -1 ) {
					list.add(str.substring(begin + 4, str.length()));
				}
			}
		}
		return list;
	}

	/**
	 * 根据HTML的ID键及属于名，获取属于值
	 * 
	 * @param id
	 *            HTML的ID键
	 * @param attrName
	 *            属于名
	 * @return 返回属性值
	 */
	private static String getElementAttrById(Document doc, String id,
			String attrName) throws Exception {
		Element et = doc.getElementById(id);
		String attrValue = et.attr(attrName);
		return attrValue;
	}

	/**
	 * 根据HTML的id键获取html内容
	 * 
	 * @param doc
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private static String getElementAttrById(Document doc, String id)
			throws Exception {
		Element et = doc.getElementById(id);
		String attrValue = et.html();
		return attrValue;
	}

	/**
	 * 根据HTML的id键获取html内容
	 * 
	 * @param doc
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private static String getElementAttrByIdAndClass(Document doc, String id,
			String className) throws Exception {
		Element et = doc.getElementById(id);
		if (null != et) {
			String attrValue = et.getElementsByClass(className).get(0).html();
			return attrValue;
		}
		return null;
	}

	/**
	 * 根据FLASH地址生成页面代码
	 * 
	 * @param flashUrl
	 * @return
	 * @throws Exception
	 */
	private static String getHtmlCode(String flashUrl) throws Exception {
		return "<embed src=\""
				+ flashUrl
				+ "\" allowFullScreen=\"true\" quality=\"high\" width=\"480\" height=\"400\" align=\"middle\" allowScriptAccess=\"always\" type=\"application/x-shockwave-flash\"></embed>";
	}

	/**
	 * 获取网页的内容
	 */
	private static Document getURLContent(String url) throws Exception {
		Document doc = Jsoup.connect(url).data("query", "Java").userAgent(
				"Mozilla").cookie("auth", "token").timeout(5000).get();
		return doc;
	}

	public static void main(String[] args) throws Exception {
		// String url = "http://v.youku.com/v_show/id_XMzAyMjE1Nzgw.html";
		// String url = "http://www.tudou.com/programs/view/zu7R9pP_xRU/";
		// String url = "http://v.ku6.com/show/nM5r4HICKVtDEso8jYVqCg...html";
		// String url = "http://v.ku6.com/show/bFx2VCEiF15U53E6.html";
		// String url = "http://www.56.com/u37/v_NjE3OTQ4NTg.html";
		// String url =
		// "http://video.sina.com.cn/p/news/c/v/2012-11-29/225561930637.html";
		// String url = "http://tv.sohu.com/20110912/n319076906.shtml";
		// String url =
		// "http://v.ifeng.com/ent/mingxing/201211/77555781-1bf7-4d58-b64e-3efd8a7ec6db.shtml";
		// String url = "http://www.yinyuetai.com/video/271439";
		//String url = "http://v.163.com/jishi/V5R8VCP1E/V7BLITISH.html";
		String url = "http://v.qq.com/cover/z/zqeiywzbbhz8ul8.html?vid=a0011hxiol3";

		Video video = VideoUtil.getVideoInfo(url);
		System.out.println(video);
		System.out.println("视频标题：" + video.getTitle());
		System.out.println("视频地址：" + video.getFlashUrl());
		System.out.println("视频时长：" + video.getTime());
		System.out.println("视频来源：" + video.getSource());
		System.out.println("视频简介：" + video.getSummary());
		System.out.println("视频缩略图：" + video.getThumbnail());
		System.out.println("视频原始地址：" + video.getPageUrl());
		System.out.println("视频网页代码：" + video.getHtmlCode());
	}

}