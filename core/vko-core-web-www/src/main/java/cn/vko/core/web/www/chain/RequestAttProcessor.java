/**
 * RequestAttProcessor.java
 * cn.vko.web.www.chain
 * Copyright (c) 2013, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.core.web.www.chain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.mvc.ActionContext;
import org.nutz.mvc.impl.processor.UpdateRequestAttributesProcessor;

import cn.vko.core.web.config.ConfigKey;
import cn.vko.core.web.config.KvConfig;

/**
 * web项目通用属性设置
 * @author   彭文杰
 * @Date	 2013-11-20 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RequestAttProcessor extends UpdateRequestAttributesProcessor {
	private KvConfig config;

	@Override
	public void process(final ActionContext ac) throws Throwable {
		ac.getRequest().setAttribute("static", config.getValue(ConfigKey.STATIC));
		super.process(ac);
	}
}
