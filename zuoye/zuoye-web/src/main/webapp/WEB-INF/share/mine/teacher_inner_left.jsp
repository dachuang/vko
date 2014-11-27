<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/share/tld.jsp"%>
<div class="left_side">
	<cms:user st="big" uid="${obj.userInfos.id }"/>
	<input type="hidden" name="studentUserId" value="${obj.userInfos.id }"/>
	<!-- 右侧导航 start -->
	<ul class="mine_menu">
		<li <c:if test="${param.type==0 }">class="current"</c:if>><a class="total unLearnNum" href="javascript:;" style="display:none;">0</a><a class="mine_course" href="<cms:url st='teacher_buycourse'/>" title="我已购买的课程">我的课程</a></li>
		<li <c:if test="${param.type==1 }">class="current"</c:if>><a class="total teacourseNum" href="javascript:;" style="display:none;">0</a><a class="mine_course" href="<cms:url st='teacher_mycourse'/>" title="课程统计">课程统计</a></li>
		 
		<li <c:if test="${param.type==13 }">class="current"</c:if>><a class="total uncheckhw" href="javascript:;" style="display:none;">0</a><a class="mine_work" href="http://www.vko.cn/teacher/myhomework.html?type=13" title="作业管理">作业管理</a></li>
		 
		 <li <c:if test="${param.type==7 }">class="current"</c:if>><a class="total paperNum" href="javascript:;" style="display:none;">0</a>
		<a class="mine_paper" href="teacher/mypaper.html" title="我的试卷">我的试卷</a></li>
		<li <c:if test="${param.type==2 }">class="current"</c:if>><a class="mine_speak" href="<cms:url st='teacher_myspeak'/>" title="我的发言">我的发言</a></li>
	    <li <c:if test="${param.type==3 }">class="current"</c:if>><a class="mine_question" href="<cms:url st='teacher_myquestion'/>" title="我的问答">我的问答</a></li>
	    <li <c:if test="${param.type==4 }">class="current"</c:if>><a class="total groupNum" href="javascript:;" style="display:none;">0</a><a class="mine_zao" href="<cms:url st='teacher_mygoup'/>" title="我的群">我的群</a></li>
	    <li <c:if test="${param.type==5 }">class="current"</c:if>><a class="mine_photo" href="<cms:url st='teacher_myphoto'/>" title="我的相册">我的相册</a></li>
		<li <c:if test="${param.type==6 }">class="current"</c:if>><a class="mine_comment" href="<cms:url st='mycomment2me'/>" title="我的评论">我的评论</a></li>
		<li><span class="activity_btn toactcode mine_course" style="cursor: pointer;">激活课程</span></li>
	</ul>
	<!-- 左侧导航 end -->
</div>
<script type="text/javascript">
 seajs.use('vkoweb/js/web/mine/include/tealeft');
</script>
