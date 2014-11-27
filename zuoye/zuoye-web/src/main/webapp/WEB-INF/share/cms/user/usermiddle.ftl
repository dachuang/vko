<#assign url="<cms:url st='hishome' userId='#{id}'/>">
<div class="vkouser vkouser_middle" uid="#{id}">
    <div class="vkouser_avatar">
    	<a href="${url}" title="${realname}" target="_blank">
	    	<img src="${vgc.static}/${uploadface}_big.jpg" width="50" height="50" alt="${realname}">
	    	<i class="vkouser_mask"></i>
    	</a>
		<#if isteacher>
    		<i class="teacherV_mini"></i>
    	</#if>
	</div>
    <div class="vkouser_name"><a href="${url}" title="${realname}" target="_blank">${realname}</a></div>
    <div class="vkouser_school">${schoolname}</div>
</div>