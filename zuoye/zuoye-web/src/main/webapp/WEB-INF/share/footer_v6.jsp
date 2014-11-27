<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" session="false" errorPage="/WEB-INF/share/500.jsp"%>

<%
	String logo="http://static.vko.cn/vko/images/logo/slogan.png";
	String info="微课网";
	String copyright="北京微课创景教育科技有限公司";
	Object obj=request.getParameter("type");
	if(obj!=null && !obj.toString().trim().equals("") && !obj.toString().trim().equals("null"))
	{
		String type=obj.toString().trim();
		if(type.equals("8"))
		{//四联微课用户
			logo="http://static.vko.cn/vko/images/logo/sl_footer.png";
			info="四联微课网";
			copyright="内蒙古四联微课教育有限责任公司";
		}
	}
%>

<!-- 页脚 start -->
<div id="footer">
    <div class="footer">
        <p class="links">
            <a target="_blank" href="${vgc.www}/about.html" title="关于我们">关于我们</a>|
            <a target="_blank" href="${vgc.www}/express.html" title="微课快讯" class="news">微课快讯</a>|
            <a target="_blank" href="${vgc.www}/contact.html" title="联系我们">联系我们</a>|
            <a target="_blank" href="${vgc.www}/service.html" title="服务条款">服务条款</a>|
            <a target="_blank" href="${vgc.www}/hr.html" title="诚聘英才">诚聘英才</a>|
            <a target="_blank" href="${vgc.www}/protection.html" title="隐私保护">隐私保护</a>|
            <a target="_blank" href="${vgc.www}/join.html" title="加盟合作">加盟合作</a>|
            <a target="_blank" href="${vgc.www}/suggest.html" title="投诉建议">投诉建议</a>
        </p>
        <p class="copyright">版权所有：<%=copyright %>  www.vko.cn  京ICP备12002746号</p>
        <p class="slogn">
            <a target="_blank" href="${vgc.www}" title="<%=info %>_学的不只是好课">
            	<img style="display:block;" class="ifixpng" src="<%=logo %>" width="347" height="58" alt="<%=info %>_学的不只是好课" />
            </a>
        </p>
        <p class="weibo">
            <a target="_blank" href="http://e.weibo.com/vkocn" class="sina" title="微课网 新浪微博">新浪微博</a>
            <a target="_blank" href="http://t.qq.com/vkovko" class="tencent" title="微课网 腾讯微博">腾讯微博</a>
        </p>
    </div>
</div>
<!-- 页脚 end -->
<script type="text/javascript">
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F637d3bd793ae39095a7b9fcf1f8afb55' type='text/javascript'%3E%3C/script%3E"));
</script>
<span style="display: none;"><script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1253228775'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s4.cnzz.com/z_stat.php%3Fid%3D1253228775' type='text/javascript'%3E%3C/script%3E"));</script></span>
</body>
</html>