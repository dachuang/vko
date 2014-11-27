<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" session="false" errorPage="/WEB-INF/share/500.jsp"%><%@include file="/WEB-INF/share/tld.jsp"%>
<!-- footer start-->
<div id="footer">
	<div class="footer">
        <p class="links">
            <a href="http://www.vko.cn/about.html" title="关于我们">关于我们</a>|
            <a href="http://www.vko.cn/express.html" title="微课快讯" class="news">微课快讯</a>|
            <a href="http://www.vko.cn/contact.html" title="联系我们">联系我们</a>|
            <a href="http://www.vko.cn/service.html" title="服务条款">服务条款</a>|
            <a href="http://www.vko.cn/hr.html" title="诚聘英才">诚聘英才</a>|
            <a href="http://www.vko.cn/protection.html" title="隐私保护">隐私保护</a>|
            <a href="http://www.vko.cn/join.html" title="加盟合作">加盟合作</a>|
            <a href="http://www.vko.cn/suggest.html" title="投诉建议">投诉建议</a>
        </p>
        <p class="copyright">版权所有：北京微课创景教育科技有限公司  www.vko.cn  京ICP备12002746号</p>
    </div>
</div>
<!-- footer start-->
</body>
<c:if test="${not empty REQ_ERROR_KEY}">
<script type="text/javascript">
seajs.use('dialog',function($dialog){
	$dialog.alert(${REQ_ERROR_KEY});
});
</script>	
</c:if>
<!-- 百度统计代码 04/08  SEO+WEB-->
<script type="text/javascript">
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F637d3bd793ae39095a7b9fcf1f8afb55' type='text/javascript'%3E%3C/script%3E"));
</script>
<span style="display: none;"><script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1253228775'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s4.cnzz.com/z_stat.php%3Fid%3D1253228775' type='text/javascript'%3E%3C/script%3E"));</script></span>
</html>