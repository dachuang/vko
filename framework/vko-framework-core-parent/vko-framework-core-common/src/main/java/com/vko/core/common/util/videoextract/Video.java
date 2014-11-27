package com.vko.core.common.util.videoextract;

import java.io.Serializable;

public class Video implements Serializable {

	private static final long serialVersionUID = -6220973207083491817L;

	private String title = "";// 视频标题

	private String thumbnail = "";// 视频缩略图

	private String summary = "";// 视频简介

	private String time = "";// 视频时长

	private String source = "";// 视频来源

	private String pageUrl = "";// 视频页面地址

	private String flashUrl = "";// 视频FLASH地址

	private String htmlCode = "";// 视频HTML代码

	public String getTitle() {
		if (null == title) {
			return "";
		} else {
			return title;
		}
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getThumbnail() {
		if (null == thumbnail) {
			return "";
		} else {
			return thumbnail;
		}
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getSummary() {
		if (null == summary) {
			return "";
		} else {
			return summary;
		}
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getTime() {
		if (null == time) {
			return "";
		} else {
			return time;
		}
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSource() {
		if (null == source) {
			return "";
		} else {
			return source;
		}
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getPageUrl() {
		if (null == pageUrl) {
			return "";
		} else {
			return pageUrl;
		}
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getFlashUrl() {
		if (null == flashUrl) {
			return "";
		} else {
			return flashUrl;
		}
	}

	public void setFlashUrl(String flashUrl) {
		this.flashUrl = flashUrl;
	}

	public String getHtmlCode() {
		if (null == htmlCode) {
			return "";
		} else {
			return htmlCode;
		}
	}

	public void setHtmlCode(String htmlCode) {
		this.htmlCode = htmlCode;
	}

}
