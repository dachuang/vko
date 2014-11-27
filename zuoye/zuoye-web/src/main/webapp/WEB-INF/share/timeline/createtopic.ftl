<div class="status_items topic">
	<div class="status_body">
		<cms:user st="middle" uid="${crId}"/>
		<div class="title"><cms:user st='name' uid='${crId}'/> 在<a href="<cms:url st='group' gid='${groupId}'/>" title="${groupName}"  target="_blank">${groupName}</a> 群发话题</div>
		<div class="details">
			<div class="topic_tit"><a href="<cms:url st='topic' gid='${groupId}' tid='${id}' />" title="${title}" target="_blank">${title}</a></div>
			<div class="topic_brief">${summary}</div>
			<#if (picList??&&picList?size>0)>
				<div class="topic_img picture">
					<#list picList as pic>
						<img class="imgzoom" src="${pic}" width="75" height="75" />
					</#list>
				</div>
			</#if>
			<#if (videoUrl??&&videoUrl?trim?length>0)>
				<div class="video">
					<img height="90" src="${videoImg}" alt="${videoTitle}" url="${videoUrl}" swf="${videoSwf}"/>
					<span class="video_play"></span>
				</div>
			</#if>
		</div>
	</div>
	<div class="status_opreate">
		<span class="time"><cms:date uid='${now}'/></span>
		<a class="to_see" <a href="<cms:url st='topic' gid='${groupId}' tid='${id}'/>" title="去看看" target="_blank">去看看</a>
	</div>
</div>
