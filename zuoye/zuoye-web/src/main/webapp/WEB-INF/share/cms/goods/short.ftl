<#if objtype==1>
	<#assign url="<cms:url st='course_book' goodsId='${id}' bookId='${objid}'/>">
<#else>
	<#assign url="<cms:url st='course_pack' goodsId='${id}' packId='${objid}'/>">
</#if>
	<em class="courseIcon${showtype}"></em>
	<#if showtype==1>
	<a class="colorcoursebg like${bg}" href="${url}" title="${name}" target="_blank">
		<strong>${name}</strong><span>${version}</span>
	</a>
	<#else>
	<span class="pic">
		<strong><a href="${url}" title="${name}" target="_blank"><img src="<#if (cover??&&cover?length>0)>${cover}<#else>http://static.vko.cn/vko/images/other/tcourse.jpg</#if>" width="202" height="120" alt="${name}"></a></strong>
	</span>
	</#if>
	
<div class="details">
	<div class="tit">
		<strong><a href="${url}" title="${name}" target="_blank">${name}</a></strong>
		<a class="listen" href="${url}" title="试学该课程" target="_blank">试学该课程<i class="play_icon"></i></a>
	</div>
	<div class="infos">
		主讲教师：
		<cms:user st='name' uid='${userid}' />课程数：${coursenum!"0"}节<em class="long">（${coursetime!"0"}分钟）</em></div>
	<div class="desc">
		<em>内容简介：</em>${description}
		<a href="${url}" title="详细">[详细]</a>
	</div>
</div>