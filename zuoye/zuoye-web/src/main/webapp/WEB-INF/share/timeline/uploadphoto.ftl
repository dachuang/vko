<div class="status_items upload">
	<div class="status_body">
		<cms:user st="middle" uid="${crId}"/>
		<div class="title"><cms:user st='name' uid='${crId}'/>&nbsp;&nbsp;上传照片</div>
		<div class="picture">
			<#list photos as photo>
				<img class="imgzoom" src="${photo.url}" width="75" height="75" alt="${photo.name}" />
			</#list>
		</div>
	</div>
	<div class="status_opreate">
		<span class="time"><cms:date uid='${now}'/></span>
		<a class="to_see" href="http://photo.vko.cn/album/stuphoto.html?userId=${crId}&albumId=${albumId}" title="去看看" target="_blank">去看看</a>
	</div>
</div>