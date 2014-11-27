<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" session="false" errorPage="/WEB-INF/share/500.jsp"%>
<%@include file="/WEB-INF/share/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<meta http-equiv="Access-Control-Allow-Origin" content="*" />
<meta name="alexaVerifyID" content="COpV9RoXtcanUi3TXPNlodOTxuw" />
<c:choose><c:when test='${not empty param.title}'><title>${param.title}</title></c:when><c:otherwise><title>微课网在线教育 小学在线学习</title></c:otherwise></c:choose>
<c:choose><c:when test='${not empty param.keywords}'><meta name="keywords" content="${param.keywords }" /></c:when><c:otherwise><meta name="keywords" content="微课网,在线教育,网校,学习网,VKO,ESNS,小学思维训练,练习题,小学微课" /></c:otherwise></c:choose>
<c:if test='${not empty param.description}'><meta name="description" content="${param.description }" /></c:if>
<link href="${static }/v5/css/common/base.css" rel="stylesheet" type="text/css" media="screen"/>
<script type="text/javascript" src="${static }/v5/js/common/vkofileloader.js"></script>
<%
	String[] links = request.getParameterValues("link");
	if(links!=null){
		for(String link : links){
			out.println("<link href='" + link + "' rel='stylesheet' type='text/css' media='screen'>");	
		}
	}
	//out.println("<!-- "+application.getRealPath(request.getRequestURI())+" -->");
	//由于与小学项目已导入的版本为2.0.1的sea.js冲突，在没有导入2.0.1的sea.js的页面做以下配置：
	//(目前主要用于vko3.0\vko_web\src\main\webapp\WEB-INF\v6\xiaoxue\order.jsp)
	/**
	<jsp:include page="/WEB-INF/share/xx_head.jsp" flush="true">
		<jsp:param value="${static}/common/js/seajs/seajs/2.2.0/sea.js" name="script"/>
		<jsp:param value="${static}/common/js/config.js" name="script"/>
		<jsp:param value="${static}/vko/js/config.js" name="script"/>
	</jsp:include>
	*/
	String[] scripts = request.getParameterValues("script");
	if(scripts != null){
		for(String script : scripts){
			out.println("<script type='text/javascript' src='" + script + "'></script>");	
		}
	}
%>
<script type="text/javascript">
	window.document.domain = "vko.cn";
	vkoFileLoader.load('/v5/css/xiaoxue/common.css');
	var userType = 'xx';
	var vkohost = 'xiaoxue.vko.cn';
</script> 
</head>
<body>
<!--headerbar start-->
<div id="headbar">
	<div class="headbar">
        <div class="tool">
           <!--购物车 start-->
        	<!-- <div class="shoppingcart" id="head_shoppingcart"></div> -->
        	<div class="shoppingcart" id="head_shoppingcart">
	        	 <a href="http://course.vko.cn/cart.html" class="goods-number" target="_blank">
	            	<span>购物车共<b>0</b>件</span>
	                <em></em>
	            </a>
	        </div>
        	<!--购物车 end-->
           	<!-- <a class="activity" href="#">活动</a> -->
           	<a class="activity" href="${vgc.xiaoxue }/news/">消息</a>
          	<a class="help" href="${vgc.xiaoxue }/help/">帮助</a>
        </div>
        <!-- 第一种 没有登录 start-->
		<div class="welcome unlogin">
			<a id="headLogin" href="javascript:;">登录</a>
			<a id="headReg" href="javascript:;">注册</a>
		</div>
		<!-- 第一种 没有登录 end-->
		<!-- 登录状态start -->
    	<div class="welcome islogin">
        	<span>欢迎你，</span>
            <div class="userinfo">
                <span class="name"><a href="javascript:;"></a></span>
                <dl class="myinfo">
                    <dd><a href="${vgc.xiaoxue }/personalset.html" target="_blank">• 我的资料</a></dd>
                    <dd><a href="${vgc.xiaoxue }/myhome.html"  target="_blank">• 我的学习</a></dd>
                    <dd><a href="${vgc.xiaoxue }/mycourse.html" target="_blank">• 我的课程</a></dd>
                    <dd><a href="${vgc.xiaoxue }/myfavorite.html" target="_blank">• 我的收藏</a></dd>
                    <dd><a href="${vgc.xiaoxue }/orderlist.html" target="_blank">• 我的交易</a></dd>
                </dl>
            </div>
            <a class="logout" href="${vgc.sso}/logoutv5?destinationUrl=http://xiaoxue.vko.cn/">[退出]</a>
        </div>
    	<!-- 登录状态end -->
    </div>
</div>
<!--headerbar end-->

<!-- header start-->
<div id="header">
	<div class="header">
    	<div class="logo">
        	<h1 class="hidden">微课网</h1>
            <a class="xiaoxue-logo" href="http://xiaoxue.vko.cn" >微课网 学的不只是好课</a>
        </div>
        <div class="nav">
        	<ul>
            	<li class="index"><a <c:if test="${param.type==1 }">class="current"</c:if> href="http://xiaoxue.vko.cn">首页</a></li>
                <li class="math"><a <c:if test="${empty param.type || param.type==2}">class="current"</c:if> href="http://xiaoxue.vko.cn/theme/">奥数</a></li>
                <li class="english"><a <c:if test="${param.type==3}">class="current"</c:if> href="http://xiaoxue.vko.cn/yingyu/">英语</a></li>
                <li class="patriarch"><a href="javascript:;">语文</a></li>
                <li class="bbs"><a href="javascript:;">论坛</a></li>
            </ul>
        </div>
        <%-- <div class="search">
        	<input class="global" type="text" id="allSearchText" placeholder="请输入关键字"/><input class="global-btn" type="button" />
        </div> --%>
    </div>
</div>
<!-- header end-->