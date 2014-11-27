
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/share/tld.jsp"%>
<div class="left_side">
	<cms:user st="big" uid="${obj.userInfos.id }"/>
	<input type="hidden" name="studentUserId" value="${obj.userInfos.id }"/>
	<!-- 右侧导航 start -->
	<ul class="mine_menu">
		<li <c:if test="${param.type==1 }">class="current"</c:if>><a class="total unLearnNum" href="javascript:;" style="display:none;">0</a>
		<a class="mine_course" href="<cms:url st='mycourse'/>" title="我的课程">我的课程</a></li>
		
		
		<li <c:if test="${param.type==13 }">class="current"</c:if>><a class="total myunhw" href="javascript:;" style="display:none;">0</a>
		<a class="mine_work" href="http://www.vko.cn/student/myhomework.html?type=13" title="我的作业">我的作业</a></li>
		
		 
		<li <c:if test="${param.type==7 }">class="current"</c:if>><a class="total paperNum" href="javascript:;" style="display:none;">0</a>
		<a class="mine_paper" href="<cms:url st='mypaper'/>" title="我的试卷">我的试卷</a></li>
		<li <c:if test="${param.type==2 }">class="current"</c:if>><a class="mine_speak" href="<cms:url st='student_myspeak'/>" title="我的发言">我的发言</a></li>
	    <li <c:if test="${param.type==3 }">class="current"</c:if>><a class="mine_question" href="<cms:url st='student_myquestion'/>" title="我的问答">我的问答</a></li>
	    <li <c:if test="${param.type==14 }">class="current"</c:if>><a class="mine_wrong" href="http://www.vko.cn/student/myfalse.html?type=14" title="错题本">错题本</a></li>
	    <li <c:if test="${param.type==4 }">class="current"</c:if>><a class="total groupNum" href="javascript:;" style="display:none;">0</a>
	    <a class="mine_zao" href="<cms:url st='student_mygoup'/>" title="我的群">我的群</a></li>
	    <li <c:if test="${param.type==5 }">class="current"</c:if>><a class="mine_photo" href="<cms:url st='student_myphoto'/>" title="我的相册">我的相册</a></li>
		<li <c:if test="${param.type==6 }">class="current"</c:if>><a class="mine_comment" href="<cms:url st='mycomment2me'/>" title="我的评论">我的评论</a></li>
		<!-- <li><a class="total" href="#" title="87节课程">87</a><a class="mine_work" href="#" title="我的作业">我的作业</a></li>
	    <li><a class="test_btn" href="#" title="测试">测试</a><a class="mine_test" href="#" title="我的测试">我的测试</a></li>
	    <li><a class="total" href="#" title="87节课程">87</a><a class="mine_wrong" href="#" title="错题本">错题本</a></li> -->
	   <!--  <li class="line"></li> -->
	    <!-- <li><a class="create_btn" href="#" title="创建小灶">新建</a><a class="mine_zao" href="#" title="我的小灶">我的小灶</a></li>
	    <li><a class="mine_photo" href="#" title="我的相册">我的相册</a></li>
	    <li><a class="mine_post" href="#" title="我的转帖">我的转帖</a></li>
	    <li class="line"></li>
	    <li><a class="mine_dream" href="#" title="我的梦想">我的梦想</a></li>
	    <li><a class="mine_task" href="#" title="我的任务">我的任务</a></li>
	    <li><a class="mine_medal" href="#" title="我的勋章">我的勋章</a></li> -->
	    <li class="line"></li>
	    <li <c:if test="${param.type==9 }">class="current"</c:if>><a class="mine_statistic" href="<cms:url st='student_mystat'/>" title="学习统计">学习统计</a></li>
	</ul>
	<!-- 左侧导航 end -->
</div>
<script type="text/javascript">
 seajs.use('vkoweb/js/web/mine/include/stuleft');
</script>
