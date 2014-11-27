/**
 * TeacherMakeHWServiceImpl.java
 * cn.vko.zuoye.service.impl
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.vko.cache.dao.mybatis.Page;
import cn.vko.cache.util.CacheOperationContext;
import cn.vko.zuoye.entity.ExamOptions;
import cn.vko.zuoye.entity.Homework;
import cn.vko.zuoye.entity.HwAnswerCard;
import cn.vko.zuoye.entity.HwExam;
import cn.vko.zuoye.entity.HwImages;
import cn.vko.zuoye.entity.StudentAnswer;
import cn.vko.zuoye.entity.StudentHomework;
import cn.vko.zuoye.entity.StudentOptions;
import cn.vko.zuoye.keyvalue.HomeworkDistribution;
import cn.vko.zuoye.keyvalue.HomeworkStatus;
import cn.vko.zuoye.keyvalue.HomeworkType;
import cn.vko.zuoye.keyvalue.ImageExamType;
import cn.vko.zuoye.keyvalue.StudentHomeworkStatus;
import cn.vko.zuoye.keyvalue.SysCodeType;
import cn.vko.zuoye.service.BaseService;
import cn.vko.zuoye.service.TeacherMakeHWService;
import cn.vko.zuoye.service.sms.SmsUtil;
import cn.vko.zuoye.service.sso.SsoFetchUser;
import cn.vko.zuoye.service.work.ReceiveHWWorker;
import cn.vko.zuoye.service.work.SendHWWorker;

import com.alibaba.fastjson.JSON;
import com.vko.core.common.exception.VkoServiceException;
import com.vko.core.common.sign.Base64;
import com.vko.core.common.util.DateUtil;
import com.vko.core.common.util.IdWorker;
import com.vko.core.common.util.MathUtil;
import com.vko.core.common.util.SerializeUtil;
import com.vko.core.common.util.SmartMap;
import com.vko.core.common.util.Util;

/**
 * 教师发作业服务类接口实现类
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-14 	 
 */
@Service
@SuppressWarnings("rawtypes")
public class TeacherMakeHWServiceImpl extends BaseService implements TeacherMakeHWService {
	@Autowired
	ReceiveHWWorker receiveHWWorker;
	@Autowired
	SendHWWorker sendHWWorker;

	/**
	 * 获取用户的学段id和学科id
	 * <p>
	 *
	*/
	@Override
	public Map<String, Integer> getTeacherInfo() {
		long teacherId = SsoFetchUser.getUserIdWithEx();
		CacheOperationContext.setCache(false);
		SmartMap<String, Object> map = vkoMapper.getTeacherDetail(teacherId);
		Integer grade = map.getInteger("grade");
		Integer subject = map.getInteger("subject");
		Boolean isTeacher = map.getBoolean("isTeacher");
		if (isTeacher == null || !isTeacher) {
			throw new VkoServiceException("亲,您不是老师,不能发布作业!");
		}
		//根据老师id获得学段和学科user_detail
		if (grade == null) {
			throw new VkoServiceException("您所在年级是空的,请补全吧!");
		}
		Integer xueDuanId = getXueDuan(grade);
		if (subject == null) {
			throw new VkoServiceException("您所在学科是空的,请补全吧!");
		}
		Map<String, Integer> result = new HashMap<String, Integer>();
		result.put("xueDuanId", xueDuanId);
		result.put("subjectId", subject);
		return result;
	}

	/**
	 * 分页查询
	 * <p>
	 *
	 * @param page
	 * @param teacherId
	 * @param k1
	 * @param k2
	 * @param k3
	 * @param type
	 * @param sort
	*/
	@Override
	public Page getSearchExam(Page page, Integer xuDuanId, Integer subjectid, String k1, String k2, String k3,
			String type, int sort) {

		String[] k1s = StringUtils.tokenizeToStringArray(k1, "-");
		String[] k2s = StringUtils.tokenizeToStringArray(k2, "-");
		String[] k3s = StringUtils.tokenizeToStringArray(k3, "-");
		String[] types = StringUtils.tokenizeToStringArray(type, "-");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("xueDuanId", xuDuanId);
		param.put("subjectId", subjectid);
		param.put("k1s", k1s);
		param.put("k2s", k2s);
		param.put("k3s", k3s);
		param.put("types", types);
		param.put("sort", sort);
		//查询试题表
		return tikuMapper.getPageFromTkExam(page, param);
		//return null;
	}

	/**
	 * 根据试题类型组合id
	 * <p>
	 *
	 * @param examIds
	*/
	public Map<String, List<Long>> assembleHomework(List<Long> examIds) {
		List<SmartMap<String, Object>> exams = tikuMapper.getExamTypeById(examIds);
		Map<String, List<Long>> group = new LinkedHashMap<String, List<Long>>();
		for (int i = 0; i < exams.size(); i++) {
			Long id = exams.get(i).getLong("id");
			String typeName = exams.get(i).getString("typeName");
			List<Long> list = group.get(typeName);
			if (list == null) {
				list = new ArrayList<Long>();
				group.put(typeName, list);
			}
			list.add(id);
		}
		return group;
	}

	private Map<Long, String> getGroupNameMap(List<Long> groupIds) {
		if (groupIds.size() == 0) {
			return Collections.emptyMap();
		}
		List<SmartMap<String, Object>> list = groupMapper.selectGroupNameByIds(groupIds);
		Map<Long, String> nameMap = new HashMap<Long, String>();
		for (int i = 0; i < list.size(); i++) {
			SmartMap<String, Object> map = list.get(i);
			nameMap.put(map.getLong("id"), map.getString("name"));
		}
		return nameMap;
	}

	/**
	 * 提交已经组合好的作业
	 * <p>
	 *
	 * @param name
	 * @param ids
	 * @param groupId
	 * @param limitTime
	 * @param startTime
	 * @param endTime
	 * @throws Exception 
	*/
	@Override
	public boolean saveTikuHomework(List<Long> examIds, SmartMap<String, Object> param) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long teacherId = SsoFetchUser.getUserIdWithEx();
		SmartMap<String, Object> teacherInfo = vkoMapper.getTeacherInfo(teacherId);
		List<Long> groupIds = param.getList("groupId", Long.class);
		if (groupIds == null) {
			throw new VkoServiceException("请选择班级群");
		}
		Map<Long, String> nameMap = getGroupNameMap(groupIds);
		SmartMap<String, Object> map = vkoMapper.getTeacherDetail(teacherId);
		Integer subject = map.getInteger("subject");
		SmartMap<String, Object> subjectMap = vkoMapper.selectSysCodeById(subject.longValue());
		if (examIds.size() == 0) {
			throw new VkoServiceException("请选择试题");
		}
		//更新试题引用次数
		tikuMapper.updateReferenceNumByExamIds(examIds);
		List<SmartMap<String, Object>> examsList = tikuMapper.getExamsObjective(examIds);
		int objectiveNum = 0;
		//图片上传类型
		for (int i = 0; i < examsList.size(); i++) {
			SmartMap<String, Object> mapObj = examsList.get(i);
			if (mapObj.getBoolean("objective")) {
				objectiveNum++;
			}
		}
		for (int i = 0; i < groupIds.size(); i++) {
			Homework record = new Homework();
			record.setName(param.getString("name"));
			record.setGroupId(groupIds.get(i));
			record.setCreateTime(new Date());
			record.setExamNum(examIds.size());
			record.setGroupName(nameMap.get(groupIds.get(i)));
			record.setTimeout(getSeconds(param.getint("hour"), param.getint("minute")));
			record.setType(HomeworkType.TIKU_CHANNEL.getKey());
			record.setTeacherName(teacherInfo.getString("realName"));
			record.setTeacherId(teacherId);
			record.setStatus(HomeworkStatus.SENDED.getKey());
			record.setDistribution(HomeworkDistribution.DAI_FA.getKey());
			record.setSubjectId(subject.longValue());
			record.setSubjectName(subjectMap.getString("name"));
			record.setObjectiveNum(objectiveNum);
			record.setSubjectiveNum(examsList.size() - objectiveNum);
			record.setStartTime(format.parse(param.getString("startTime")));
			record.setEndTime(format.parse(param.getString("endTime")));
			record.setHasResolve(false);
			homeworkMapper.insertSelective(record);
			//试题作业关系保存
			saveHWExamMapping(record.getId(), examsList);

		}
		//刷新发送任务
		sendHWWorker.refresh();
		return true;
	}

	@Override
	public void saveSendHomework(List<Long> needDistributIds) throws Exception {
		for (int i = 0; i < needDistributIds.size(); i++) {
			Long id = needDistributIds.get(i);
			int count = homeworkMapper.updateHWDistribution(id, HomeworkDistribution.DAI_SHOU.getKey());
			if (count == 0) {
				continue;
			}
			Homework hw = homeworkMapper.selectByPrimaryKey(id);
			//List<HwExam> hwExamsList = hwExamMapper.selectExamForDistribution(id);
			//获取所有学生
			List<SmartMap<String, Object>> student = groupMapper.selectStudentByGroupId(hw.getGroupId());
			List<Long> userIds = new ArrayList<Long>();
			List<StudentHomework> records = new ArrayList<StudentHomework>();
			for (int j = 0; j < student.size(); j++) {
				userIds.add(student.get(j).getLong("userId"));
				StudentHomework stHW = new StudentHomework();
				stHW.setCreateTime(new Date());
				stHW.setId(IdWorker.getId());
				stHW.setGroupId(hw.getGroupId());
				stHW.setHwName(hw.getName());
				stHW.setObjectiveNum(hw.getObjectiveNum());
				stHW.setStatus(StudentHomeworkStatus.NOT_HAND_IN.getKey());
				stHW.setStudentId(student.get(j).getLong("userId"));
				stHW.setStudentName(student.get(j).getString("realname"));
				stHW.setSubjectiveNum(hw.getSubjectiveNum());
				stHW.setSubjectId(hw.getSubjectId());
				stHW.setSubjectName(hw.getSubjectName());
				stHW.setRightNum(0);
				stHW.setSubjectiveRate(new BigDecimal(0));
				stHW.setObjectiveRate(new BigDecimal(0));
				stHW.setHwId(id);
				records.add(stHW);
			}
			if (records.size() > 0) {
				studentHomeworkMapper.insert(records);
			}
			Homework record = new Homework();
			record.setId(id);
			record.setNeedHandNum(student.size());
			homeworkMapper.updateByPrimaryKeySelective(record);
			if (userIds.size() > 0) {
				//给学生发站内信
				List<String> mobileArray = new ArrayList<String>();
				List<SmartMap<String, Object>> mobiles = vkoMapper.selectMobileByUserId(userIds);
				for (SmartMap<String, Object> mobile : mobiles) {
					String mobileStr = mobile.getString("mobile");
					if (mobileStr != null && mobileStr.trim().length() > 0) {
						mobileArray.add(mobileStr.trim());
					}
				}
				String message = "您的老师于%s发布了%s,作业截止到%s,请及时完成!";
				String content = String.format(message, DateUtil.formatDateTime(hw.getStartTime()), hw.getName(),
						DateUtil.formatDateTime(hw.getEndTime()));
				SmsUtil.send(content + "【微课网】", mobileArray.toArray(new String[0]));

				vkoMapper.insertMessage(getMessages(records,
						"<a href=\"http://zy.vko.cn/dohw/%s.html\" target=\"_blank\">" + content + "</a>"));
			}

		}
		//刷新接收任务
		receiveHWWorker.refresh();
	}

	@Override
	public List<SmartMap<String, Object>> getMessages(List<StudentHomework> records, String content) {
		List<SmartMap<String, Object>> list = new ArrayList<SmartMap<String, Object>>();
		for (StudentHomework hw : records) {
			SmartMap<String, Object> map = new SmartMap<String, Object>();
			list.add(map);
			map.put("id", IdWorker.getId());
			map.put("sendTime", new Date());
			map.put("body", String.format(content, hw.getId()));
			map.put("recipientId", hw.getStudentId());
			map.put("isRead", 0);
			map.put("type", 0);
			map.put("source", 1);
			map.put("title", "");
		}
		return list;
	}

	private List<SmartMap<String, Object>> getMessage(Homework hw, String content) {
		List<SmartMap<String, Object>> list = new ArrayList<SmartMap<String, Object>>();
		SmartMap<String, Object> map = new SmartMap<String, Object>();
		list.add(map);
		map.put("id", IdWorker.getId());
		map.put("sendTime", new Date());
		map.put("body", String.format(content, hw.getId()));
		map.put("recipientId", hw.getTeacherId());
		map.put("isRead", 0);
		map.put("type", 0);
		map.put("source", 1);
		map.put("title", "");
		return list;
	}

	@Override
	public void saveReceiveHomework(List<Long> needDistributIds) {
		for (int i = 0; i < needDistributIds.size(); i++) {
			Long id = needDistributIds.get(i);
			int count = homeworkMapper.updateHWDistribution(id, HomeworkDistribution.YI_SHOU.getKey());
			if (count == 0) {
				continue;
			}
			List<StudentAnswer> studentAnswerList = studentAnswerMapper.selectObjectiveByHWId(id, true);
			Map<Long, List<StudentAnswer>> examAnswerMap = new HashMap<Long, List<StudentAnswer>>();
			for (int j = 0, size = studentAnswerList.size(); j < size; j++) {
				StudentAnswer studentAnswer = studentAnswerList.get(j);
				List<StudentAnswer> list = examAnswerMap.get(studentAnswer.getExamId());
				if (list == null) {
					list = new ArrayList<StudentAnswer>();
					examAnswerMap.put(studentAnswer.getExamId(), list);
				}
				list.add(studentAnswer);
			}

			List<StudentOptions> studentOptionsList = new ArrayList<StudentOptions>(studentAnswerList.size() * 4);
			Map<String, ExamOptions> optionsMap = new HashMap<String, ExamOptions>();
			int totalRight = 0, totalNum = 0;
			for (Map.Entry<Long, List<StudentAnswer>> studentAnswer : examAnswerMap.entrySet()) {
				//统计此题正确数
				int rightNum = 0;
				Long examId = studentAnswer.getKey();
				List<StudentAnswer> values = studentAnswer.getValue();
				totalNum += values.size();
				for (StudentAnswer sa : values) {
					if (sa.getIsRight()) {
						rightNum++;
						totalRight++;
					}
					//选项统计
					String answer = sa.getAnswer();
					for (int k = 0; k < answer.length(); k++) {
						char ch = answer.charAt(k);
						ExamOptions examOptons = optionsMap.get(examId + Character.toString(ch));
						if (examOptons == null) {
							examOptons = new ExamOptions();
							examOptons.setId(IdWorker.getId());
							examOptons.setHwId(id);
							examOptons.setExamId(examId);
							if (sa.getRightAnswer().contains(Character.toString(ch))) {
								examOptons.setIsRight(true);
							} else {
								examOptons.setIsRight(false);
							}
							examOptons.setOptions(Character.toString(ch));
							examOptons.setTotal(1);
							optionsMap.put(examId + Character.toString(ch), examOptons);
						} else {
							examOptons.setTotal(examOptons.getTotal() + 1);
						}
						StudentOptions stOptions = new StudentOptions();
						stOptions.setRealName(sa.getRealName());
						stOptions.setStatId(examOptons.getId());
						stOptions.setStudentId(sa.getStudentId());
						studentOptionsList.add(stOptions);
					}
				}

				//正确率
				double rightRate = MathUtil.div(rightNum * 100, values.size(), 2);
				HwExam hwExam = new HwExam();
				hwExam.setHwId(id);
				hwExam.setExamId(examId);
				hwExam.setRightNum(rightNum);
				hwExam.setWrongNum(values.size() - rightNum);
				hwExam.setUnreviewNum(values.size());
				hwExam.setRightRate(new BigDecimal(rightRate));
				hwExamMapper.updateObjectiveStat(hwExam);

			}
			//更新主观题未阅人数
			studentAnswerList = studentAnswerMapper.selectObjectiveByHWId(id, false);
			examAnswerMap = new HashMap<Long, List<StudentAnswer>>();
			for (int j = 0, size = studentAnswerList.size(); j < size; j++) {
				StudentAnswer studentAnswer = studentAnswerList.get(j);
				List<StudentAnswer> list = examAnswerMap.get(studentAnswer.getExamId());
				if (list == null) {
					list = new ArrayList<StudentAnswer>();
					examAnswerMap.put(studentAnswer.getExamId(), list);
				}
				list.add(studentAnswer);
			}
			for (Map.Entry<Long, List<StudentAnswer>> studentAnswer : examAnswerMap.entrySet()) {
				Long examId = studentAnswer.getKey();
				List<StudentAnswer> values = studentAnswer.getValue();
				HwExam hwExam = new HwExam();
				hwExam.setHwId(id);
				hwExam.setExamId(examId);
				hwExam.setUnreviewNum(values.size());
				hwExam.setRightNum(0);
				hwExam.setWrongNum(0);
				hwExam.setRightRate(new BigDecimal(0));
				hwExamMapper.updateObjectiveStat(hwExam);
			}
			List<ExamOptions> examOptionsList = new ArrayList<ExamOptions>(optionsMap.values());
			if (examOptionsList.size() > 0) {
				examOptionsMapper.insert(examOptionsList);
			}
			if (studentOptionsList.size() > 0) {
				studentOptionsMapper.insert(studentOptionsList);
			}
			//统计选项信息

			////更新选项统计
			Homework record = new Homework();
			record.setId(id);
			record.setStatus(HomeworkStatus.WILL_CORRECT.getKey());
			Double elapse = studentHomeworkMapper.selectAvgElapseByHWId(id);
			if (elapse == null) {
				elapse = 0d;
			}
			//平均耗时    
			record.setElapse(elapse.intValue());
			//record.setNeedHandNum(needHandNum);
			SmartMap<String, Object> result = studentHomeworkMapper.selectHandedNumByHWId(id);
			if (result != null) {
				record.setNotFinishNum(result.getint("notFinishNum"));
				record.setHandedNum(result.getint("handedNum"));
			}
			double rightRate = MathUtil.div(totalRight * 100, totalNum, 2);
			record.setRightRate(new BigDecimal(rightRate));
			homeworkMapper.updateByPrimaryKeySelective(record);
			//给老师发站内信 
			Homework hw = homeworkMapper.selectByPrimaryKey(id);
			List<Long> userIds = new ArrayList<Long>(1);
			userIds.add(hw.getTeacherId());
			List<SmartMap<String, Object>> list = vkoMapper.selectMobileByUserId(userIds);
			String message = "您于%s发布的%s已收缴完毕,请您批阅!";
			String content = String.format(message, DateUtil.formatDateTime(hw.getStartTime()), hw.getName());
			vkoMapper.insertMessage(getMessage(hw, "<a href=\"http://zy.vko.cn/check/%s.html\" target=\"_blank\">"
					+ content + "</a>"));
			if (list.size() > 0) {
				String mobile = list.get(0).getString("mobile");
				if (mobile != null && mobile.trim().length() > 0) {
					SmsUtil.send(content + "【微课网】", mobile.trim());
				}
			}
		}

	}

	/**
	 * 预览即将组合的作业
	 * <p>
	 *
	 * @param ids
	*/
	@Override
	public List<SmartMap<String, Object>> getAssembleExamContent(List<Long> ids) {

		return tikuMapper.getExamsContent(ids);
	}

	/**
	 * 组作业第二部
	 * <p>
	 *
	 * @param ids
	*/
	//	@Override
	//	public List<SmartMap<String, Object>> nextAssembleHomework(List<Long> ids) {
	//
	//		return tikuMapper.getExamsContent(ids);
	//	}

	/**
	 * 根据试题id
	 * 获取试题内容
	 * <p>
	 *
	 * @param ids
	*/
	@Override
	public List<SmartMap<String, Object>> getExamsContent(List<Long> ids) {
		if (ids.size() == 0) {
			return Collections.emptyList();
		}
		return tikuMapper.getExamsContent(ids);
	}

	private Long getSeconds(Integer limitsHour, Integer limitsMinute) {
		Long seconds = 0L;
		if (limitsHour != null) {
			seconds += limitsHour * 3600;
		}
		if (limitsMinute != null) {
			seconds += limitsMinute * 60;
		}
		return seconds;
	}

	private void saveHWExamMapping(Long hwId, List<SmartMap<String, Object>> examObjectve) {
		List<HwExam> record = new ArrayList<HwExam>();
		Map<Long, Boolean> objeciveMap = new HashMap<Long, Boolean>();

		for (int i = 0; i < examObjectve.size(); i++) {
			SmartMap<String, Object> map = examObjectve.get(i);
			objeciveMap.put(map.getLong("id"), map.getBoolean("objective"));
		}
		//图片上传类型
		for (int i = 0; i < examObjectve.size(); i++) {
			SmartMap<String, Object> exam = examObjectve.get(i);
			HwExam examMapping = new HwExam();
			examMapping.setExamId(exam.getLong("id"));
			examMapping.setHwId(hwId);
			examMapping.setObjective(objeciveMap.get(exam.getLong("id")));
			examMapping.setType(HomeworkType.TIKU_CHANNEL.getKey());
			examMapping.setExamOrder(i + 1);
			examMapping.setBegNum(0);
			examMapping.setReviewedNum(0);
			examMapping.setRightNum(0);
			examMapping.setRightRate(new BigDecimal(0));
			examMapping.setUnreviewNum(0);
			examMapping.setWrongNum(0);
			record.add(examMapping);
		}
		if (record.size() > 0) {
			hwExamMapper.insert(record);
		}
	}

	/*
	 * 以下上传图片作为作业的处理
	 */

	@Override
	public boolean saveImageHomework(SmartMap<String, Object> param) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String base64Data = param.getString("base64Data");
		//解包
		SmartMap<String, Object> data = SerializeUtil.fastDeserialize(Base64.decode(base64Data));
		String remark = data.getString("remark");
		//json格式的答题卡
		String answer = data.getString("answer");
		String jsonImages = data.getString("jsonImages");
		List<SmartMap> answers = JSON.parseArray(answer, SmartMap.class);
		List<SmartMap> imagesList = JSON.parseArray(jsonImages, SmartMap.class);
		Long teacherId = SsoFetchUser.getUserIdWithEx();
		List<Long> groupIds = param.getList("groupId", Long.class);
		if (groupIds == null) {
			throw new VkoServiceException("请选择班级群");
		}
		Map<Long, String> nameMap = getGroupNameMap(groupIds);
		SmartMap<String, Object> map = vkoMapper.getTeacherDetail(teacherId);
		Integer subject = map.getInteger("subject");
		SmartMap<String, Object> subjectMap = vkoMapper.selectSysCodeById(subject.longValue());
		SmartMap<String, Object> teacherInfo = vkoMapper.getTeacherInfo(teacherId);
		int objectiveNum = 0;
		for (int i = 0; i < answers.size(); i++) {
			SmartMap answerData = answers.get(i);
			if (ImageExamType.isObjective(answerData.getint("type"))) {
				objectiveNum++;
			}
		}
		for (int i = 0; i < groupIds.size(); i++) {
			Homework record = new Homework();
			record.setName(param.getString("name"));
			record.setGroupId(groupIds.get(i));
			record.setCreateTime(new Date());
			record.setExamNum(answers.size());
			record.setGroupName(nameMap.get(groupIds.get(i)));
			record.setTimeout(getSeconds(param.getint("hour"), param.getint("minute")));
			record.setRemark(remark);
			record.setType(HomeworkType.SELF_UPLOAD.getKey());
			record.setTeacherName(teacherInfo.getString("realName"));
			record.setTeacherId(teacherId);
			record.setStatus(HomeworkStatus.SENDED.getKey());
			record.setDistribution(HomeworkDistribution.DAI_FA.getKey());
			record.setSubjectId(subject.longValue());
			record.setObjectiveNum(objectiveNum);
			record.setSubjectiveNum(answers.size() - objectiveNum);
			record.setSubjectName(subjectMap.getString("name"));
			record.setStartTime(format.parse(param.getString("startTime")));
			record.setEndTime(format.parse(param.getString("endTime")));
			record.setHasResolve(false);
			homeworkMapper.insertSelective(record);
			//答题卡保存
			saveImageExamAnswer(record.getId(), answers);
			//照片保存
			saveImages(record.getId(), imagesList);
		}

		//刷新发送任务
		sendHWWorker.refresh();
		return true;
	}

	private void saveImages(Long hwId, List<SmartMap> imagesList) {
		List<HwImages> record = new ArrayList<HwImages>();
		for (int i = 0; i < imagesList.size(); i++) {
			SmartMap mapData = imagesList.get(i);
			HwImages images = new HwImages();
			images.setCover(mapData.getString("photoUrl") + "!mid");
			images.setName(mapData.getString("name"));
			images.setCreateTime(new Date());
			images.setHwId(hwId);
			images.setOrderNum(i + 1);
			images.setPath(mapData.getString("photoUrl"));
			record.add(images);
		}
		if (record.size() > 0) {
			hwImagesMapper.insert(record);
		}
	}

	private void saveImageExamAnswer(Long hwId, List<SmartMap> answers) {
		List<HwAnswerCard> record = new ArrayList<HwAnswerCard>();
		for (int i = 0; i < answers.size(); i++) {
			SmartMap map = answers.get(i);
			HwAnswerCard card = new HwAnswerCard();
			List<String> list = map.getList("answer", String.class);
			StringBuilder str = new StringBuilder();
			for (String string : list) {
				str.append(string);
			}
			card.setAnswer(str.toString());
			card.setExamOrder(i + 1);
			card.setHwId(hwId);
			card.setObjective(ImageExamType.isObjective(map.getInteger("type")));
			card.setType(map.getInteger("type"));
			card.setId(IdWorker.getId());
			record.add(card);
		}
		if (record.size() > 0) {
			hwAnswerCardMapper.insert(record);
		}
		//试题和作业的关系
		saveImageHWExamMapping(hwId, record);
	}

	private void saveImageHWExamMapping(Long hwId, List<HwAnswerCard> listCard) {
		List<HwExam> record = new ArrayList<HwExam>();
		//图片上传类型
		for (int i = 0; i < listCard.size(); i++) {
			HwAnswerCard card = listCard.get(i);
			HwExam examMapping = new HwExam();
			examMapping.setExamId(card.getId());
			examMapping.setHwId(hwId);
			examMapping.setObjective(card.getObjective());
			examMapping.setType(HomeworkType.SELF_UPLOAD.getKey());
			examMapping.setExamOrder(i + 1);
			examMapping.setBegNum(0);
			examMapping.setReviewedNum(0);
			examMapping.setRightNum(0);
			examMapping.setRightRate(new BigDecimal(0));
			examMapping.setUnreviewNum(0);
			examMapping.setWrongNum(0);
			record.add(examMapping);
		}
		if (record.size() > 0) {
			hwExamMapper.insert(record);
		}
	}

	/**
	 * 根据标签类型(type)与父标签ID,获取标签
	 * 
	 * @param type
	 * @param tagIds
	 * @return 标签
	 */
	public List<SmartMap<String, Object>> getK2K3Tags(SysCodeType type, String tagIds, String split) {
		if (Util.isEmpty(tagIds)) {
			return null;
		}
		String[] ids = StringUtils.tokenizeToStringArray(tagIds, split);
		if (ids.length == 0) {
			return null;
		}
		List<Long> idList = vkoMapper.selectIdsByParentId(ids);
		if (idList.size() == 0) {
			return null;
		}
		List<SmartMap<String, Object>> list = vkoMapper.selectSysCodeByIdType(idList, type.type());
		return list;
	}

	@Override
	public Map<String, Object> getSearchOptions(Integer xueDuanId, Integer subjectId, String k1, String k2) {
		//试题类型
		List<SmartMap<String, Object>> typeList = vkoMapper.selectByPhaseSubjectType(xueDuanId, subjectId,
				SysCodeType.EXAM_TYPE.type());
		List<SmartMap<String, Object>> k1List = vkoMapper.selectByPhaseSubjectType(xueDuanId, subjectId,
				SysCodeType.K1.type());
		List<SmartMap<String, Object>> k2List = getK2K3Tags(SysCodeType.K2, k1, "-");
		List<SmartMap<String, Object>> k3List = getK2K3Tags(SysCodeType.K3, k2, "-");

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("typeList", typeList);
		result.put("k1List", k1List);
		result.put("k2List", k2List);
		result.put("k3List", k3List);
		return result;

	}

	@Override
	public Map<Long, String> getGroupsByTeacherId(long teacherId) {
		List<Long> groupIdList = groupMapper.selectGroupsIdByTeacherId(teacherId);
		Map<Long, String> nameMap = getGroupNameMap(groupIdList);
		return nameMap;
	}
}
