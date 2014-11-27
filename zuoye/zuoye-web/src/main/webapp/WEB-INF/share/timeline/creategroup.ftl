<div class="status_items create_group">
	<div class="status_body">
		<cms:user st="middle" uid="${crId}"/>
		<div class="title"><cms:user st='name' uid='${crId}'/>创建了<a href="<cms:url st='group' gid="${groupId}"/>" title="${groupName}" target="_blank">${groupName}</a>群，快来加入吧！</div>
		<cms:group st='summary' gid='${groupId}' uid='${groupId}'/>
	</div>
	<div class="status_opreate">
		<span class="time"><cms:date uid='${now}'/></span>
		<a class="to_see" href="<cms:url st='group' gid="${groupId}"/>" title="去看看" target="_blank">去看看</a>
	</div>
</div>
