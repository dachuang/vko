<div class="group_info">
	<div class="vkogroup vkogroup_mini">
        <div class="vkogroup_avatar"><a href="<cms:url st='group' gid='${id}'/>" title="${name}" target="_blank"><img src="${avatarmiddle}" width="80" height="80" alt="${name}"><i class="vkogroup_mask"></i></a></div>
        <div class="qInterest_icon"></div>
        <div class="group_name"><a href="<cms:url st='group' gid='${id}'/>" title="${name}" target="_blank">${name}</a></div>
        <div class="group_master">创建人：<cms:user st='name' uid='${crid}'/></div>
		<div class="group_member">成  员：<a href="<cms:url st='group' gid='${id}'/>" title="${name}"  target="_blank">${members}</a>人</div>
 		<div class="group_post">话  题：<a href="<cms:url st='group' gid='${id}'/>" title="${name}"  target="_blank">${topicnum}</a>个</div>
    </div>
    <a href="<cms:url st='group' gid='${id}'/>" title="${name}" target="_blank"><span class="joinBtn">加入</span></a>
</div>