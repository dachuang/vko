<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" session="false" errorPage="/WEB-INF/share/500.jsp"%>
<%@include file="/WEB-INF/share/tld.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<meta http-equiv="Access-Control-Allow-Origin" content="*" />
<meta name="alexaVerifyID" content="COpV9RoXtcanUi3TXPNlodOTxuw" />
<c:choose><c:when test='${not empty param.title}'><title>${param.title} | 微课网在线教育 国内首家中学生ESNS视频学习社区。加入微课网，学的不只是好课，实现高效学习，快乐分享。</title></c:when><c:otherwise><title> 微课网在线教育 国内首家中学生ESNS视频学习社区。加入微课网，学的不只是好课，实现高效学习，快乐分享。</title></c:otherwise></c:choose>
<c:choose><c:when test='${not empty param.keywords}'><meta name="keywords" content="${param.keywords },微课网,在线教育,网校,学习网,高考,中考,VKO,ESNS" /></c:when><c:otherwise><meta name="keywords" content="微课网,在线教育,网校,学习网,高考,中考,VKO,ESNS" /></c:otherwise></c:choose>
<c:choose><c:when test='${not empty param.description}'><meta name="description" content="${param.description }，微课网 国内首家中学生ESNS学习社交网络，着眼于中考、高考命题的深刻解析，提供丰富的初高中各学科的在线教育名师微课程视频，在这里你可以和同学组成圈子共同学习、互动答疑、测试并分享学习动态！微课网热线：4008-900-800。" /></c:when><c:otherwise><meta name="description" content="微课网 国内首家中学生ESNS学习社交网络，着眼于中考、高考命题的深刻解析，提供丰富的初高中各学科的在线教育名师微课程视频，在这里你可以和同学组成圈子共同学习、互动答疑、测试并分享学习动态！微课网热线：4008-900-800。" /></c:otherwise></c:choose>
<link href="${static}/common/css/base.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${static}/vko/css/common.css" rel="stylesheet" type="text/css" media="screen"/>
<script type="text/javascript" src="${static}/common/js/stacktrace.js"></script>
<script type="text/javascript" src="${static}/common/js/vkofileloader.js"></script>
<script type="text/javascript" src="${static}/common/js/seajs/seajs/2.2.0/sea.js"></script>
<script type="text/javascript" src="${static}/common/js/config.js"></script>
<script type="text/javascript" src="${static}/vko/js/config.js"></script>
<script type="text/javascript">try{if(document.URL.indexOf('photo/view')==-1)window.document.domain = "vko.cn";}catch(e){}</script>
</head>
<body>
<div id="headbar">
	<div class="headbar">
    	<a class="vko" href="${vgc.www}" target="_blank" title="微课网">微课网</a>
        <div class="grade_toggle">
	        <a href="${vgc.course}/grade/5.html" target="_blank" title="高中">高中</a>
	        <a href="${vgc.course}/grade/2.html" target="_blank" title="初中">初中</a>
            <a href="${vgc.xiaoxue}" target="_blank"  title="小学">小学</a>
        </div>
        <a class="course_btn" href="${vgc.course}" target="_blank"  title="课程">课程</a>
        <div title="分站" class="coursearea_btn">
        	<span class="daming">微课分站</span>
        <div style="display: none;" class="substation clearfix">
        	<div class="btnclear_sub clearfix">
        	
        	<div class="title_subs">
        	选择分站：<span>[ 当前 : <b id="headOrgName" class="colgrn">主站</b> ]</span>
        	</div>
        	<div class="fndciy_sub">
        	<ul id="headOrgList" class="subcity clearfix">
        		
        			<div style="display: none" class="ab_tcsb">
	        	<div class="town_csbc">
		        	<ul class="ton_tcs town_tt">
		        			
		        	</ul>        	
		        	<ul class="tns_tcs sc_tt o">
		        			
		        	</ul>
	        	</div>
        		</div>
        	</ul>
       	
        	</div>
        	<div class="goareabtn" style="top: 282px;"><a style="margin-right:8px" class="btn_large_blue01" href="">进入分站</a><span class="btn_large_orange02">取消</span></div>
        	</div>
        </div>
        </div>
        <ul class="menu">
            <li class="notice"><a href="javascript:;" title="查看消息">消息</a></li>
            <li class="friend"><a href="${vgc.www}/mymates.html" title="好友">好友</a></li>
            <li class="setting"><a href="javascript:;" title="设置">设置</a></li>
            <li class="mobile"><a href="${vgc.www}/pai.html"  title="手机版">手机版</a></li>
            <li class="help"><a href="${vgc.www}/help.html" title="帮助">帮助</a></li>   
        </ul>
        <div class="settingbox">
        	<ul>
            	<li><a target="_blank" href="${vgc.www}/personal/information.html" title="查看个人信息">个人信息</a></li>
                <li><a target="_blank" href="${vgc.www}/order.html" title="账户">账户</a></li>
            	<li><a target="_blank" href="${vgc.www}/score.html" title="积分等级">积分等级</a></li>
                <li><a target="_blank" href="${vgc.www}/safety.html" title="查看安全设置">安全</a></li>
                <li><a target="_blank" href="${vgc.www}/safety/privacyset.html" title="查看隐私设置">隐私设置</a></li>
            </ul>
        </div>
        <div class="welcome">
        	<span>你好，</span>
            <a id="headName" href="${vgc.www}">亲</a>
            <span>欢迎来到微课网！</span>
        	<a id="headLogin" href="${vgc.login_page}">登录</a>
        	<a id="headReg" href="${vgc.www}/reg.html">注册</a>
        	<a id="headLogout" href="${vgc.loginout_url}?destinationUrl=${vgc.loginout_to}" style="display:none;">退出</a>
        </div>
        <div class="shoppingcart" id="head_shoppingcart">
        	 <a href="${vgc.course}/cart.html" class="goods-number" target="_blank">
            	<span>购物车共<b>0</b>件</span>
                <em></em>
            </a>
        </div>
    </div>
</div>
<%
//分站ID, 默认【主站】
String headOrgId = request.getParameter("headOrgId");
if (headOrgId == null || "".equals(headOrgId)) {
	headOrgId = "0";
}
%>
<script type="text/javascript">
seajs.use('vkoweb/js/web/area/temphead');
//分站导航
seajs.use('vkoweb/js/web/area/orgnav', function(orgnav){
	orgnav.loadOrgList('<%=headOrgId%>');
});
</script>