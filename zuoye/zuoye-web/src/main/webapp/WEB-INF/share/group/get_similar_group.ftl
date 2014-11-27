<#list groupList as similarGroup> 
<li>
	<div class="vkogroup vkogroup_mini">
        <div class="vkogroup_avatar">
        	<a href="${vgc.group }/group/${similarGroup.id}.html" title="${similarGroup.name}" target="_blank">
        	<img src="${similarGroup.avatarSmall}" width="80" height="80" alt="${similarGroup.name}">
        	<i class="vkogroup_mask"></i>
        	</a>
        </div>
        <#if similarGroup.type = 1>
		    <div class="qClass_icon"></div>
		 <#elseif similarGroup.type = 2>
		    <div class="qInterest_icon"></div>
		 </#if>
        <div class="group_name"><a href="${vgc.group }/class/${similarGroup.id}.html" title="${similarGroup.name}">${similarGroup.name}</a></div>
        <div class="group_master">创建人：<a href="${vgc.www }/user/${similarGroup.crId}" title="${similarGroup.userDetail.webUser.realname }">${similarGroup.userDetail.webUser.realname }</a></div>
		<div class="group_member">成  员：<a href="#" title="${similarGroup.name}">${similarGroup.members }人</a></div>
 		<div class="group_post">话  题：<a href="${vgc.group }/group/${similarGroup.id}" title="${similarGroup.name}">${similarGroup.topicNum }个</a></div>
    </div>
    <span class="joinBtn btnJoinGroup" qId="${similarGroup.id}">加入</span>
</li>
</#list> 