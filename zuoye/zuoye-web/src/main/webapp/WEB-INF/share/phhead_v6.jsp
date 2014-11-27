<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" session="false" errorPage="/WEB-INF/share/500.jsp"%>
<%@include file="/WEB-INF/share/tld.jsp"%>

<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Cache-control" content="no-cache" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;"/>
<c:choose><c:when test='${not empty param.title}'><title>${param.title} | 微课网在线教育 国内首家中学生ESNS视频学习社区。加入微课网，学的不只是好课，实现高效学习，快乐分享。</title></c:when><c:otherwise><title> 微课网在线教育 国内首家中学生ESNS视频学习社区。加入微课网，学的不只是好课，实现高效学习，快乐分享。</title></c:otherwise></c:choose>
<c:choose><c:when test='${not empty param.keywords}'><meta name="keywords" content="${param.keywords },微课网,在线教育,网校,学习网,高考,中考,VKO,ESNS" /></c:when><c:otherwise><meta name="keywords" content="微课网,在线教育,网校,学习网,高考,中考,VKO,ESNS" /></c:otherwise></c:choose>
<c:choose><c:when test='${not empty param.description}'><meta name="description" content="${param.description }，微课网 国内首家中学生ESNS学习社交网络，着眼于中考、高考命题的深刻解析，提供丰富的初高中各学科的在线教育名师微课程视频，在这里你可以和同学组成圈子共同学习、互动答疑、测试并分享学习动态！微课网热线：4008-900-800。" /></c:when><c:otherwise><meta name="description" content="微课网 国内首家中学生ESNS学习社交网络，着眼于中考、高考命题的深刻解析，提供丰富的初高中各学科的在线教育名师微课程视频，在这里你可以和同学组成圈子共同学习、互动答疑、测试并分享学习动态！微课网热线：4008-900-800。" /></c:otherwise></c:choose>
<link href="${static}/common/css/phbase.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${static}/vko/css/common.css" rel="stylesheet" type="text/css" media="screen"/>
<script type="text/javascript" src="${static}/common/js/phvkofileloader.js"></script>
<script type="text/javascript" src="${static}/common/js/seajs/seajs/2.2.0/sea.js"></script>
<script type="text/javascript" src="${static}/common/js/phconfig.js"></script>
<script type="text/javascript" src="${static}/vko/js/phconfig.js"></script>
<script type="text/javascript">
try{if(document.URL.indexOf('photo/view')==-1)window.document.domain = "vko.cn";}catch(e){}
</script>
</head>
<body>
