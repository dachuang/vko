<#if objType==1>
	<#assign url="<cms:url st='course_book' goodsId='${goodsId}' bookId='${objId}'/>">
<#elseif objType==7 || objType==8>
	<#assign url="${vgc.tiku}/paper.html">
<#elseif objType==2>
	<#assign url="<cms:url st='course_pack' goodsId='${goodsId}' packId='${objId}'/>">
</#if>
<div class="course_intro">
	<em class="courseIcon${showtype}"></em>
	<#if objType==1 || objType==2>
		<#if showtype==1>
		<a class="colorcoursebg like${bg}" href="${url}" title="${name}" target="_blank">
			<strong>${name}</strong><span>${version}</span>
		</a>
		<#else>
		<div class="pic">
			<a href="${url}" title="${name}" target="_blank"><img src="<#if (cover??&&cover?length>0)>${cover}
			<#else>http://static.vko.cn/vko/images/other/tcourse.jpg</#if>" width="202" height="120" alt="${teachername}"></a>
		</div>
		</#if>
	</#if>
	
	<strong class="course_tit"><a href="${url}" title="${name}" target="_blank">${name}</a></strong>
	
	<#if objType==1 || objType==2>
	<div class="teacher_name">主讲教师：<cms:user st='name' uid='${userid}'/></div>
	<div class="time_long">课 程 数：${coursenum!"0"}节<span>（${courseTime!"0"}分钟）</span></div>
	<div class="buy_num">${buycount!"0"}人购买</div>
	<div class="comment_num"><a href="${url}" title="${commentNum!"0"}条评论" target="_blank">${commentNum!"0"}条评论</a></div>
	</#if>
</div>