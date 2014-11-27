<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/share/tld.jsp"%>
<div class="left_side">
	<cms:user st="big" uid="${obj.userInfos.id }"/>
	<input type="hidden" name="studentUserId" value="${obj.userInfos.id }"/>
	<!-- 右侧导航 start -->
	<ul class="mine_menu">
		<li <c:if test="${param.type==1 }">class="current"</c:if>><a class="total unLearnNum" href="javascript:;" style="display:none;">0</a><a class="mine_course" href="<cms:url st='student_course'  userId='${obj.userInfos.id}'/>" title="TA的课程">TA的课程</a></li>
		<li <c:if test="${param.type==2 }">class="current"</c:if>><a class="mine_speak" href="<cms:url st='student_speak' userId='${obj.userInfos.id}'/>" title="TA发言">TA发言</a></li>
	    <li <c:if test="${param.type==4 }">class="current"</c:if>><a class="total groupNum" href="javascript:;" style="display:none;">0</a><a class="mine_zao" href="<cms:url st='student_goup' userId='${obj.userInfos.id}'/>" title="TA的群">TA的群</a></li>
	    <li <c:if test="${param.type==5 }">class="current"</c:if>><a class="mine_photo" href="<cms:url st='student_photo' userId='${obj.userInfos.id}'/>" title="TA的相册">TA的相册</a></li>
		<li <c:if test="${param.type==9 }">class="current"</c:if>><a class="mine_statistic" href="<cms:url st='student_stat' userId='${obj.userInfos.id}'/>" title="学习统计">学习统计</a></li>
	</ul>
	<!-- 左侧导航 end -->
</div>
<script type="text/javascript">
seajs.use('vkoweb/js/web/mine/include/stuleft');
</script>
