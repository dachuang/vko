/**
 * BaseService.java
 * cn.vko.zuoye.service
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.vko.zuoye.mapper.ExamOptionsMapper;
import cn.vko.zuoye.mapper.ExamResolveMapper;
import cn.vko.zuoye.mapper.GroupMapper;
import cn.vko.zuoye.mapper.HomeworkMapper;
import cn.vko.zuoye.mapper.HwAnswerCardMapper;
import cn.vko.zuoye.mapper.HwExamMapper;
import cn.vko.zuoye.mapper.HwImagesMapper;
import cn.vko.zuoye.mapper.MyFalseMapper;
import cn.vko.zuoye.mapper.StudentAnswerMapper;
import cn.vko.zuoye.mapper.StudentHomeworkMapper;
import cn.vko.zuoye.mapper.StudentOptionsMapper;
import cn.vko.zuoye.mapper.TikuMapper;
import cn.vko.zuoye.mapper.VkoMapper;

/**
 * 服务层基类
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-11 	 
 */
public abstract class BaseService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	protected ExamOptionsMapper examOptionsMapper;
	@Autowired
	protected ExamResolveMapper examResolveMapper;
	@Autowired
	protected HomeworkMapper homeworkMapper;
	@Autowired
	protected HwAnswerCardMapper hwAnswerCardMapper;
	@Autowired
	protected HwExamMapper hwExamMapper;
	@Autowired
	protected HwImagesMapper hwImagesMapper;
	@Autowired
	protected StudentAnswerMapper studentAnswerMapper;
	@Autowired
	protected StudentHomeworkMapper studentHomeworkMapper;
	@Autowired
	protected StudentOptionsMapper studentOptionsMapper;
	@Autowired
	protected VkoMapper vkoMapper;
	@Autowired
	protected TikuMapper tikuMapper;
	@Autowired
	protected GroupMapper groupMapper;
	@Autowired
	protected MyFalseMapper myFalsemapper;

	//年级和学段的映射,
	private static Map<Integer, Integer> GRADE_MAPPING = new HashMap<Integer, Integer>();
	static {
		GRADE_MAPPING.put(0, 52);
		GRADE_MAPPING.put(1, 52);
		GRADE_MAPPING.put(2, 52);
		GRADE_MAPPING.put(3, 51);
		GRADE_MAPPING.put(4, 51);
		GRADE_MAPPING.put(5, 51);
		GRADE_MAPPING.put(6, 53);
		GRADE_MAPPING.put(7, 53);
		GRADE_MAPPING.put(8, 53);
		GRADE_MAPPING.put(9, 53);
		GRADE_MAPPING.put(10, 53);
		GRADE_MAPPING.put(11, 53);
		GRADE_MAPPING.put(51, 51);
		GRADE_MAPPING.put(52, 52);
		GRADE_MAPPING.put(53, 53);
	}

	/**
	 * 根据年级获得学段
	 * <p>
	 *
	 * @param grade
	 * @return 
	*/
	protected Integer getXueDuan(Integer grade) {
		if (grade == null) {
			return -1;
		}
		return GRADE_MAPPING.get(grade);
	}
}
