<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/share/tld.jsp"%>
<div class="left_side">
	<cms:user st="big" uid="${obj.userInfos.id }"/>
		<!-- 右侧导航 start -->
		<ul class="mine_menu">
			<li <c:if test="${param.type==1 }">class="current"</c:if>><a class="total noLearnNum" href="<cms:url st='mycourse'/>" style="display:none;">0<a class="mine_course" href="<cms:url st='mycourse'/>" title="${obj.nickName}的课程">${obj.nickName}的课程</a></li>
			<li <c:if test="${param.type==2 }">class="current"</c:if>><a class="mine_speak" href="<c:if test="${obj.isme }"><cms:url st='speak'/></c:if><c:if test="${!obj.isme }"><cms:url st='speakwithparam' userId="${obj.userInfos.id }"/></c:if>" title="${obj.nickName}的发言">${obj.nickName}的发言</a></li>
		    <li <c:if test="${param.type==3 }">class="current"</c:if>><a class="mine_question" href="<c:if test="${obj.isme }"><cms:url st='question'/></c:if><c:if test="${!obj.isme }"><cms:url st='questionwithparam' userId="${obj.userInfos.id }"/></c:if>" title="${obj.nickName}的问答">${obj.nickName}的问答</a></li>
		    <li <c:if test="${param.type==4 }">class="current"</c:if>><a class="mine_zao" href="<cms:url st='mygoup'/>" title="${obj.nickName}的群">${obj.nickName}的群</a></li>
			<li <c:if test="${param.type==5 }">class="current"</c:if>><a class="mine_photo" href="<cms:url st='myphoto'/>" title="${obj.nickName}的相册">${obj.nickName}的相册</a></li>
		    <li <c:if test="${param.type==6 }">class="current"</c:if>><a class="mine_comment" href="<c:if test="${obj.isme }"><cms:url st='comment2me'/></c:if><c:if test="${!obj.isme }"><cms:url st='comment2mewithparam' userId="${obj.userInfos.id }"/></c:if>" title="${obj.nickName}的评论">${obj.nickName}的评论</a></li>
			<!-- <li><a class="total" href="#" title="87节课程">87</a><a class="mine_work" href="#" title="${obj.nickName}的作业">${obj.nickName}的作业</a></li>
		    <li><a class="test_btn" href="#" title="测试">测试</a><a class="mine_test" href="#" title="${obj.nickName}的测试">${obj.nickName}的测试</a></li>
		    <li><a class="total" href="#" title="87节课程">87</a><a class="mine_wrong" href="#" title="错题本">错题本</a></li> -->
		   <!--  <li class="line"></li> -->
		    <!-- <li><a class="create_btn" href="#" title="创建小灶">新建</a><a class="mine_zao" href="#" title="${obj.nickName}的小灶">${obj.nickName}的小灶</a></li>
		    <li><a class="mine_photo" href="#" title="${obj.nickName}的相册">${obj.nickName}的相册</a></li>
		    <li><a class="mine_post" href="#" title="${obj.nickName}的转帖">${obj.nickName}的转帖</a></li>
		    <li class="line"></li>
		    <li><a class="mine_dream" href="#" title="${obj.nickName}的梦想">${obj.nickName}的梦想</a></li>
		    <li><a class="mine_task" href="#" title="${obj.nickName}的任务">${obj.nickName}的任务</a></li>
		    <li><a class="mine_medal" href="#" title="${obj.nickName}的勋章">${obj.nickName}的勋章</a></li> -->
		    <li class="line"></li>
		    <li <c:if test="${param.type==9 }">class="current"</c:if>><a class="mine_statistic" href="<c:if test="${obj.isme }"><cms:url st='stat'/></c:if><c:if test="${!obj.isme }"><cms:url st='statwithparam' userId="${obj.userInfos.id }"/></c:if>" title="学习统计">学习统计</a></li>
		   <!--  <li><a class="mine_order" href="#" title="${obj.nickName}的订单">${obj.nickName}的订单</a></li> -->
		</ul>
	<!-- 左侧导航 end -->
</div>
<script type="text/javascript">
 seajs.use('vkoweb/js/web/mine/left');
</script>
