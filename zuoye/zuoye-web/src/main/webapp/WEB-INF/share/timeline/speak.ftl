<div class="status_items ispeak">
	<div class="status_body">
		<cms:user st="middle" uid="${userId}"/>
		<div class="title"><cms:user st='name' uid='${userId}'/> 同学发言道：</div>
		<div class="details">
			${content!""}
			<span class="quote"></span>
		</div>
	</div>
	<div class="status_opreate">
		<span class="time"><cms:date uid='${now}'/></span>
		<a class="to_comment btn_cmt" href="javascript:;" title="评论">评论</a>
	</div>
	<div class="comment_con vkoComments" objid="${id }" type="3"></div>
</div>