<#assign url="<cms:url st='hishome' userId='#{id}'/>">
<div class="vkouser vkouser_large" uid="#{id}">
    <div class="vkouser_avatar">
    	<a href="${url}" title="${realname}" target="_blank">
	    	<img src="${vgc.static}/${uploadface}_big.jpg" width="100" height="100" alt="${realname}">
	    	<i class="vkouser_mask"></i>
    	</a>
    	<#if isteacher>
    		<i class="teacherV"></i>
    	</#if>
	</div>
    <div class="vkouser_name"><a href="${url}" title="${realname}" target="_blank">${realname}</a></div>
    <div class="vkouser_school">${schoolname}</div>
</div>