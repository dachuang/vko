<div class="reply" qaCount="${qAnswerCount}">
	<!-- 用户头像mini start -->
	<div class="vkouser vkouser_mini" uid="${questionAnswer.crId }">
	    <div class="vkouser_avatar"><a href="${vgc.www}/home/${ud.userId}.html" title="${ud.webUser.realname}" target="_blank"><img src="${vgc.static}/${ud.uploadFace}_mini.jpg" width="50" height="50" alt="${ud.webUser.realname}"><i class="vkouser_mask"></i></a></div>
	    <div class="vkouser_name"><a href="${vgc.www}/home/${ud.userId}.html" title="${ud.webUser.realname}" target="_blank">${ud.webUser.realname}</a></div>
	    <div class="vkouser_school">${ud.school.name}</div>
	</div>
	<!-- 用户头像mini end -->
    <div class="author"><a href="${vgc.www}/home/${questionAnswer.crId}.html">${questionAnswer.crName }</a>：</div>
    <div class="audio"></div>
    <div class="content">${questionAnswer.content }</div>
    <div class="picture"></div>
    <div class="info">
        <div class="isuseful"> <span class="praisebtn" thisid="${questionAnswer.id}">+<b>${questionAnswer.top }</b><i></i></span>人认为有帮助 </div>
        <span class="time">${questionAnswer.crTime?string("yyyy-MM-dd HH:mm:ss")}</span> 
       </div>
</div>
