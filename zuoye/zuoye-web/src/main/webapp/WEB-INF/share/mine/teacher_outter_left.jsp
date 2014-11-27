<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/share/tld.jsp"%>
<div class="left_side">
	<cms:user st="big" uid="${obj.userInfos.id }"/>
	<input type="hidden" name="studentUserId" value="${obj.userInfos.id }"/>
	<!-- 右侧导航 start -->
	<ul class="mine_menu">
		<li <c:if test="${param.type==2 }">class="current"</c:if>><a class="mine_speak" href="<cms:url st='teacher_speak' userId='${obj.userInfos.id}'/>" title="TA发言" target="_tea${obj.userInfos.id}">TA的发言</a></li>
	    <li <c:if test="${param.type==4 }">class="current"</c:if>><a class="total groupNum" href="javascript:;" style="display:none;">0</a><a class="mine_zao" href="<cms:url st='teacher_goup' userId='${obj.userInfos.id}'/>" title="TA群" target="_tea${obj.userInfos.id}">TA的群</a></li>
		<li <c:if test="${param.type==5 }">class="current"</c:if>><a class="mine_photo" href="<cms:url st='teacher_photo' userId='${obj.userInfos.id}'/>"  title="TA相册" target="_stu${obj.userInfos.id}">TA的相册</a></li>
		<li <c:if test="${param.type==6 }">class="current"</c:if>><a class="mine_photo" href="<cms:url st='teacher_dynamic' userId='${obj.userInfos.id}'/>"  title="TA动态" target="_tea${obj.userInfos.id}">TA的动态</a></li>
	</ul>
	<!-- 左侧导航 end -->
</div>
<script type="text/javascript">
seajs.use('vkoweb/js/web/mine/include/tealeft');
</script>
