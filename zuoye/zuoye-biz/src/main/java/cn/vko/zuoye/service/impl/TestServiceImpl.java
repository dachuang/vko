/**
 * TestServiceImpl.java
 * cn.vko.zuoye.service.impl
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.vko.cache.dao.mybatis.Page;
import cn.vko.zuoye.mapper.HwImagesMapper;
import cn.vko.zuoye.service.BaseService;
import cn.vko.zuoye.service.TestService;

import com.vko.core.common.util.SmartMap;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 * @author   宋汝波
 * @Date	 2014-7-11 	 
 */
@Service
public class TestServiceImpl extends BaseService implements TestService {

	@Autowired
	private HwImagesMapper mapper;

	@Override
	public Object getObj() {
		List<SmartMap<String, Object>> lll = mapper.testSmartMap();

		logger.debug(lll.get(0).getlong("id") + "");
		logger.debug(lll.get(0).getString("cover") + "");
		logger.debug(lll.get(0).getTimestamp("createTime") + "");
		logger.debug(lll.get(0).getDate("createTime") + "");
		Page page = new Page(1, 10);
		page = mapper.testPaging(page, 1L);
		logger.debug(page.getRecords().size() + "");
		return mapper.selectByPrimaryKey(1L);

	}

}
