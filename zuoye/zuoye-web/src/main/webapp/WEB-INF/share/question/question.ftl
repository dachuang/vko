<div class="items" qCount="${questionCount}">
	<!-- 用户头像middle start -->
	<div class="vkouser vkouser_middle" uid="${question.crId }">
	    <div class="vkouser_avatar"><a href="${vgc.www}/home/${ud.userId}.html" title="${ud.webUser.realname}" target="_blank"><img src="${vgc.static}/${ud.uploadFace}_middle.jpg" width="50" height="50" alt="${ud.webUser.realname}"><i class="vkouser_mask"></i></a></div>
	    <div class="vkouser_name"><a href="${vgc.www}/home/${ud.userId}.html" title="${ud.webUser.realname}" target="_blank">${ud.webUser.realname}</a></div>
	    <div class="vkouser_school">${ud.school.name}</div>
	</div>
	<!-- 用户头像middle end -->
	<div class="name"><a href="${vgc.www}/home/${ud.userId}.html" title="${ud.webUser.realname}" target="_blank">${question.crName}</a>提问：</div>
	<div class="time">
		${question.crTime?string("yyyy-MM-dd HH:mm:ss")}
	</div>
	<h4 class="title">
		${question.name }
	</h4>
	<div class="details">${question.content }</div>
	<div class="sameques">
		<span class="samequesbtn" thisid="${question.id}">同问 +<b>${question.approve}</b></span>
	</div>
	<div class="replyArea">
			<div class="re-count">
				0条回答
			</div>
			<div id="listAnswer_${question.id}" class="replylist">
				
			</div>
			<div class="ireply" style="margin-top:20px;">
				<!-- 用户头像mini start -->
				<div class="vkouser vkouser_mini" uid="${udReply.userId }">
				    <div class="vkouser_avatar"><a href="${vgc.www}/home/${udReply.userId}.html" title="${udReply.webUser.realname}" target="_blank"><img src="${vgc.static}/${udReply.uploadFace}_mini.jpg" width="30" height="30" alt="${udReply.webUser.realname}"><i class="vkouser_mask"></i></a></div>
				    <div class="vkouser_name"><a href="${vgc.www}/home/${udReply.userId}.html" title="${udReply.webUser.realname}" target="_blank">${udReply.webUser.realname}</a></div>
				    <div class="vkouser_school">${ud.school.name}</div>
				</div>
				<!-- 用户头像mini end -->
				<textarea class="consult_reply" placeholder="So easy! 我来回答..."  qid="${question.id}" videoId="${videoId}" goodsId="${goodsId}" bookId="${bookId}"></textarea>
			</div>
	</div>
</div>