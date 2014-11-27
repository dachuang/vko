<#if objType==1>
	<#assign url="<cms:url st='course_book' goodsId='${goodsId}' bookId='${objId}'/>">
<#elseif objType==7 || objType==8>
	<#assign url="${vgc.tiku}/paper.html">
<#elseif objType==2>
	<#assign url="<cms:url st='course_pack' goodsId='${goodsId}' packId='${objId}'/>">
</#if>
<div class="status_items buy">
	<div class="status_body">
		<cms:user st="middle" uid="${crId}"/>
		<div class="title"><cms:user st='name' uid='${crId}'/>购买了<a href="${url}" title="${goodsName}" target="_blank">${goodsName}</a></div>
		<cms:course st='summary' goodsId='${goodsId}' uid='${goodsId}' objId='${objId}' objType='${objType}'/>
	</div>
	<div class="status_opreate">
		<span class="time"><cms:date uid='${now}'/></span>
		<a class="to_see" href="${url}" title="去看看" target="_blank">去看看</a>
	</div>
</div>
