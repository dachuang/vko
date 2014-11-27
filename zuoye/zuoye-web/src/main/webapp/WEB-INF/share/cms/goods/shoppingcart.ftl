<#if objtype==1>
	<#assign url="<cms:url st='course_book' goodsId='${id}' bookId='${objid}'/>">
<#else>
	<#assign url="<cms:url st='course_pack' goodsId='${id}' packId='${objid}'/>">
</#if>
	<#if showtype==1>
	<a class="colorcoursebg like${bg}" href="${url}" title="${name}" target="_blank">
		<strong>${name}</strong><span>${version}</span>
	</a>
	<#else>
	<span class="pic">
		<a target="_blank" href="${url}" title="${name}"><img src="<#if (cover??&&cover?length>0)>${cover}<#else>http://static.vko.cn/vko/images/other/tcourse.jpg</#if>" width="202" height="120" alt="${name}"/></a>
	</span>
	</#if>

<p class="tit">
	<a target="_blank" href="${url}" title="${name}">${name}</a>
</p>
<div class="name">主讲教师：<cms:user st='name' uid='${userid}' /></div>
<div class="course_long">课时数：${coursenum!"0"}节<em class="long">（${coursetime!"0"}分钟）</em></div>
<div class="price">¥${sellprice!"0"}</div>