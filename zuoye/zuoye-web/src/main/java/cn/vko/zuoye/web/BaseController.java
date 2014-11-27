/**
 * BaseController.java
 * cn.vko.zuoye.web
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.web;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.vko.cache.dao.mybatis.Page;

import com.vko.core.common.util.HttpUtil;
import com.vko.core.common.util.SmartMap;
import com.vko.core.web.filter.Invocation;

/**
 * 所有控制器的父类
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-2 	 
 */
public abstract class BaseController {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	protected Invocation inv;

	/**
	 * 获取分页对象
	 * <p>
	 *
	*/
	protected Page getPage() {
		int pageSize = HttpUtil.getInt(inv.getRequest(), "_size", 20);
		int pageIndex = HttpUtil.getInt(inv.getRequest(), "_index", 1);
		Page page = new Page(pageIndex, pageSize);
		return page;
	}

	@SuppressWarnings({ "unchecked" })
	protected SmartMap<String, Object> getParameterMap() {
		Map<String, String[]> param = inv.getRequest().getParameterMap();
		SmartMap<String, Object> result = new SmartMap<String, Object>();
		for (Map.Entry<String, String[]> ele : param.entrySet()) {
			if (ele.getValue().length == 1) {
				result.put(ele.getKey(), ele.getValue()[0]);
			} else {
				result.put(ele.getKey(), ele.getValue());
			}
		}
		return result;
	}

}
