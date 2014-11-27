<#if objtype==1>
	<#assign url="<cms:url st='course_book_seo' goodsId='${goodsid}' bookId='${objid}'/>">
<#else>
	<#assign url="<cms:url st='course_pack_seo' goodsId='${goodsid}' packId='${objid}'/>">
</#if>
<div class="items" gbuy="0" goodsId="${goodsid}">
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
			<strong><a href="${url}" title="${name}" target="_blank">${sname}</a></strong>
			<a class="listen" href="<cms:url st='course_play_seo' courseId='${videoid}' bookId='${objid}' goodsId='${goodsid}'/>" title="试学该课程" target="_blank">试学该课程<i class="play_icon"></i></a>
		</div>
		<div class="infos">
			主讲教师：
			<cms:user st='name' uid='${userid}' />课程数：${coursenum!"0"}节<em class="long">（${coursetime!"0"}分钟）</em></div>
		<div class="desc">
			<em>内容简介：</em>${description}
			<a href="${url}" title="详细" target="_blank">[详细]</a>
		</div>
	</div>
	<div class="course_operate">
	    <div class="price">¥${sellprice}</div>
	    <#if sellprice?number lt 300>
	    	<div class="tips">打包买更优惠</div>
	    </#if>
	    <#if sellprice?number gte 300>
	    	<div class="o">
				<div class="tips left" style="width:72px;">打包买更优惠</div>
				<div class="tips right" style="width:50px;  background-color:#61a9be">货到付款</div>
			</div>
		</#if>
	    <div class="d_info">
	    	<span>${buycount!"0"}人购买</span>
					<a href="${url}" target="_blank" title="有${commentcount!"0"}</i>&nbsp;条评论">有&nbsp;<i class="vkoCommentsNum vkocomment_cmtcount" objid="${objid}">${commentcount!"0"}</i>&nbsp;条评论</a>
	    </div>
	    <div class="operate">
	    	<a class="btn_buycourse" href="javascript:;" goodsid="${goodsid}"><span class="btn_mini_orange01">购买</span></a>
	    	<span id="${goodsid}" class="btn_mini_orange02 btn_addcart"> 加入购物车</span>
	    </div>
	</div>
</div>