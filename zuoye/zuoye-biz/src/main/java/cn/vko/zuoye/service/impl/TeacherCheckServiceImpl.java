/**
 * TeacherCheckServiceImpl.java
 * cn.vko.zuoye.service.impl
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.vko.zuoye.entity.ExamOptions;
import cn.vko.zuoye.entity.ExamResolve;
import cn.vko.zuoye.entity.Homework;
import cn.vko.zuoye.entity.HwAnswerCard;
import cn.vko.zuoye.entity.HwExam;
import cn.vko.zuoye.entity.HwImages;
import cn.vko.zuoye.entity.StudentAnswer;
import cn.vko.zuoye.entity.StudentHomework;
import cn.vko.zuoye.entity.StudentOptions;
import cn.vko.zuoye.keyvalue.ExamResvoleType;
import cn.vko.zuoye.keyvalue.HomeworkStatus;
import cn.vko.zuoye.keyvalue.HomeworkType;
import cn.vko.zuoye.service.BaseService;
import cn.vko.zuoye.service.StudentDoHWService;
import cn.vko.zuoye.service.TeacherCheckService;
import cn.vko.zuoye.service.TeacherMakeHWService;
import cn.vko.zuoye.service.sms.SmsUtil;
import cn.vko.zuoye.service.sso.SsoFetchUser;

import com.vko.core.common.exception.VkoServiceException;
import com.vko.core.common.util.DateUtil;
import com.vko.core.common.util.SmartMap;
import com.vko.core.common.util.Util;

/**
 * 审批作业服务接口实现类
 * @author   杨闯
 * @Date	 2014-7-14 	 
 */
@Service
public class TeacherCheckServiceImpl extends BaseService implements TeacherCheckService {

	@Autowired
	private StudentDoHWService studentDoHWService;
	@Autowired
	private TeacherMakeHWService teacherMakeHWService;

	/**
	 * (non-Javadoc)
	 * @see cn.vko.zuoye.service.TeacherCheckService#viewRelaseHw(long)
	 */
	@Override
	public Map<String, Object> viewRelaseHw(long hwId) {
		SsoFetchUser.getUserIdWithEx();
		if (hwId <= 0) {
			throw new VkoServiceException("该作业不存在");
		}
		Homework homework = homeworkMapper.selectHWForDoHW(hwId);
		if (Util.isEmpty(homework)) {
			throw new VkoServiceException("该作业不存在");
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("homework", homework);
		//判断作业类型
		if (HomeworkType.TIKU_CHANNEL.getKey() == homework.getType()) {
			List<SmartMap<String, Object>> examList = studentDoHWService.getDataFromTK(hwId);
			result.put("examList", examList);
		} else {
			List<HwAnswerCard> examList = studentDoHWService.getDataFromAnswerCard(hwId);
			result.put("examList", examList);
			//查询图片
			List<HwImages> hwImagesList = hwImagesMapper.selectByHWId(hwId);
			result.put("hwImagesList", hwImagesList);
		}

		return result;
	}

	@Override
	public Map<String, Object> index(long hwId) {
		SsoFetchUser.getUserIdWithEx();
		if (hwId <= 0) {
			throw new VkoServiceException("该作业不存在");
		}
		//查询，作业详细
		Homework hw = homeworkMapper.selectByPrimaryKey(hwId);
		if (Util.isEmpty(hw)) {
			throw new VkoServiceException("该作业不存在");
		}
		if (hw.getStatus() == HomeworkStatus.SENDED.getKey()) {
			throw new VkoServiceException("该作业未到收缴时间,您还不能批阅");
		}
		if (hw.getStatus() == HomeworkStatus.CORRECTED.getKey()) {
			//throw new VkoServiceException("该作业您已批阅");//预览
		}
		SsoFetchUser.getUserIdWithEx();//当前登录用户ID

		//查询，作业的所有试题
		List<HwExam> hwExamList = hwExamMapper.selectAllExamByHwId(hwId);

		if (Util.isEmpty(hwExamList)) {
			throw new VkoServiceException("此作业目前无试题");
		}
		List<Long> objectiveIds = new ArrayList<Long>();
		List<Long> subjectiveIds = new ArrayList<Long>();
		if (hwExamList.size() > 0) {
			for (int i = 0; i < hwExamList.size(); i++) {
				HwExam hwExam = hwExamList.get(i);
				if (hwExam.getObjective()) {
					objectiveIds.add(hwExam.getExamId());
				} else {
					subjectiveIds.add(hwExam.getExamId());
				}
			}
		}

		List<SmartMap<String, Object>> objectiveTikuMap = null;
		List<SmartMap<String, Object>> subjectiveTikuMap = null;
		List<HwImages> imagesExamMap = null;
		if (HomeworkType.TIKU_CHANNEL.getKey() == hw.getType().intValue()) {
			//从题库中获取试题信息
			if (!Util.isEmpty(objectiveIds)) {
				objectiveTikuMap = tikuMapper.getExamsContent(objectiveIds);
				//查询试题解析（用于预览已批阅的作业）
				fillTikuExamResolve(objectiveTikuMap, hwExamList);
			}
			if (!Util.isEmpty(subjectiveIds)) {
				subjectiveTikuMap = tikuMapper.getExamsContent(subjectiveIds);
				//查询试题解析（用于预览已批阅的作业）
				fillTikuExamResolve(subjectiveTikuMap, hwExamList);
			}
		} else {
			//作业引用图片试题
			imagesExamMap = hwImagesMapper.selectHwImages(hwId);

		}

		//查询试题统计信息
		List<Long> hwExamIds = new ArrayList<Long>();
		List<HwExam> examStatList = hwExamMapper.selectExam(hwId);
		SmartMap<Long, HwExam> examStat = new SmartMap<Long, HwExam>();
		if (examStatList.size() > 0) {
			for (int i = 0; i < examStatList.size(); i++) {
				HwExam obj = examStatList.get(i);
				examStat.put(obj.getExamId(), obj);
				hwExamIds.add(obj.getId());
			}
		}

		//获取试题解析
		List<ExamResolve> examResolveList = new ArrayList<ExamResolve>();
		if (hwExamIds.size() > 0) {
			examResolveList = examResolveMapper.selectResolveByHwExamIds(hwExamIds);
		}
		SmartMap<String, List<ExamResolve>> examResolve = new SmartMap<String, List<ExamResolve>>();
		for (int i = 0; i < examResolveList.size(); i++) {
			ExamResolve obj = examResolveList.get(i);

			List<ExamResolve> resovles = examResolve.get(obj.getHwExamId().toString());
			if (resovles == null) {
				resovles = new ArrayList<ExamResolve>();
				examResolve.put(obj.getHwExamId().toString(), resovles);
			}
			resovles.add(obj);
		}

		//查询此次作业的学生列表 
		List<StudentHomework> stuList = studentHomeworkMapper.selectStudent(hwId);

		//查询客观试题选项的统计信息
		List<ExamOptions> optionsStatList = examOptionsMapper.selectExamOptions(hwId);
		SmartMap<Long, Object> optionsStat = new SmartMap<Long, Object>();
		if (optionsStatList.size() > 0) {
			for (int i = 0; i < optionsStatList.size(); i++) {
				ExamOptions obj = optionsStatList.get(i);

				@SuppressWarnings("unchecked")
				SmartMap<String, ExamOptions> stat = (SmartMap<String, ExamOptions>) optionsStat.get(obj.getExamId());
				if (stat == null) {
					stat = new SmartMap<String, ExamOptions>();
				}
				stat.put(obj.getOptions(), obj);
				optionsStat.put(obj.getExamId(), stat);
			}
		}

		//主观题的学生列表（{examId:List}）
		SmartMap<Long, Object> studentStat = new SmartMap<Long, Object>();
		SmartMap<Long, StudentAnswer> defaultAnswer = new SmartMap<Long, StudentAnswer>();
		if (!Util.isEmpty(subjectiveIds)) {
			List<StudentAnswer> studentList = studentAnswerMapper.selectStudentAnswer(hwId, subjectiveIds);
			Long defaultStudentId = 0L;
			if (studentList.size() > 0) {
				for (int i = 0; i < studentList.size(); i++) {
					StudentAnswer obj = studentList.get(i);
					Long examId = obj.getExamId();
					@SuppressWarnings("unchecked")
					SmartMap<Long, StudentAnswer> stuMap = (SmartMap<Long, StudentAnswer>) studentStat.get(examId);
					if (stuMap == null) {
						stuMap = new SmartMap<Long, StudentAnswer>();
					}
					Long stuId = obj.getStudentId();
					if (i == 0) {
						defaultStudentId = stuId;
					}
					stuMap.put(stuId, obj);
					studentStat.put(examId, stuMap);
				}
			}

			//默认显示试题的答案及分数
			List<StudentAnswer> defaultAnswerList = studentAnswerMapper.selectDefaultStudentAnswer(hwId,
					defaultStudentId, subjectiveIds);
			if (defaultAnswerList.size() > 0) {
				for (int i = 0; i < defaultAnswerList.size(); i++) {
					StudentAnswer obj = defaultAnswerList.get(i);
					defaultAnswer.put(obj.getExamId(), obj);
				}
			}
		}

		//组装返回的数据
		Map<String, Object> initData = new HashMap<String, Object>();
		initData.put("hw", hw);
		initData.put("students", stuList);
		initData.put("objective", objectiveTikuMap);
		initData.put("subjective", subjectiveTikuMap);
		/*引用手机图片时使用*/
		initData.put("objectiveIds", objectiveIds);
		initData.put("subjectiveIds", subjectiveIds);
		initData.put("imagesExam", imagesExamMap);
		/*引用手机图片时使用*/
		initData.put("examStat", examStat);
		initData.put("examResolve", examResolve);
		initData.put("optionsStat", optionsStat);
		initData.put("studentStat", studentStat);
		initData.put("defaultAnswer", defaultAnswer);
		return initData;
	}

	//组装数据（组装试题解析）
	private void fillTikuExamResolve(List<SmartMap<String, Object>> tikuMap, List<HwExam> hwExamList) {
		Map<Long, HwExam> orderMap = new HashMap<Long, HwExam>();
		List<Long> idlist = new ArrayList<Long>();
		for (HwExam exam : hwExamList) {
			idlist.add(exam.getId());
			orderMap.put(exam.getExamId(), exam);
		}

		for (int i = 0; i < tikuMap.size(); i++) {
			SmartMap<String, Object> exam = tikuMap.get(i);
			exam.put("zyInfo", orderMap.get(exam.getLong("id")));

		}
		List<ExamResolve> resolveMap = examResolveMapper.selectResolveByHwExamIds(idlist);
		SmartMap<Long, List<ExamResolve>> map = new SmartMap<Long, List<ExamResolve>>();
		for (ExamResolve examResolve : resolveMap) {
			List<ExamResolve> lst = map.get(examResolve.getHwExamId());
			if (lst == null) {
				lst = new ArrayList<ExamResolve>();
				map.put(examResolve.getHwExamId(), lst);
			}
			lst.add(examResolve);
		}

		for (int i = 0; i < tikuMap.size(); i++) {
			SmartMap<String, Object> exam = tikuMap.get(i);
			exam.put("k2List", studentDoHWService.getExamsAnswerK2(exam.getLong("id")));
			exam.put("resolvesList", studentDoHWService.getExamsResolve(exam.getLong("id")));
			HwExam hwExam = (HwExam) exam.get("zyInfo");
			exam.put("teacherResolves", map.get(hwExam.getId()));
		}
	}

	@Override
	public StudentAnswer selectAnswerByStudetId(long hwId, long uId, long eId) {
		SsoFetchUser.getUserIdWithEx();
		if (hwId < 0) {
			throw new VkoServiceException(String.format("作业不存在(%s)", hwId));
		} else if (uId < 0) {
			throw new VkoServiceException(String.format("学生不存在(%s)", uId));
		} else if (eId < 0) {
			throw new VkoServiceException(String.format("试题不存在(%s)", eId));
		}
		//加载用户的试题答案
		return studentAnswerMapper.selectAnswerByStudetId(hwId, uId, eId);
	}

	@Override
	public List<StudentOptions> look(long statId) {
		SsoFetchUser.getUserIdWithEx();
		if (statId < 0) {
			throw new VkoServiceException(String.format("选项统计id不存在：%s", statId));
		}
		//查询，选项的人员统计
		return studentOptionsMapper.selectStudentById(statId);
	}

	@Override
	public boolean judgeSubjective(long hwId, long uId, long eId, Integer score) {
		SsoFetchUser.getUserIdWithEx();
		if (hwId < 0) {
			throw new VkoServiceException(String.format("作业不存在(%s)", hwId));
		} else if (uId < 0) {
			throw new VkoServiceException(String.format("学生不存在(%s)", uId));
		} else if (eId < 0) {
			throw new VkoServiceException(String.format("试题不存在(%s)", eId));
		} else if (Util.isEmpty(score) || score.doubleValue() < 0) {
			throw new VkoServiceException(String.format("分数设置异常(%s)", score));
		}
		StudentAnswer stuAnswer = studentAnswerMapper.selectAnswerByStudetId(hwId, uId, eId);
		if (Util.isEmpty(stuAnswer.getIsCheck()) || !stuAnswer.getIsCheck()) {
			//更新此题的已批阅人数，未批阅人数
			hwExamMapper.updateCheckSubjectiveNum(hwId, eId);
		}
		//更新主观题得分，主观题总的正答率在完成批阅时更新，并更新此题的批阅状态为已阅
		studentAnswerMapper.updateScore(hwId, uId, eId, score);
		return true;
	}

	@Override
	public SmartMap<String, Object> releaseResolve(long hwId, long hwExamId, String resolve) {
		SsoFetchUser.getUserIdWithEx();
		if (hwId < 0) {
			throw new VkoServiceException(String.format("作业不存在(%s)", hwId));
		} else if (hwExamId < 0) {
			throw new VkoServiceException(String.format("试题不存在(%s)", hwExamId));
		} else if (Util.isEmpty(resolve)) {
			throw new VkoServiceException("解析内容不可以为空");
		}

		ExamResolve r = new ExamResolve();
		r.setHwExamId(hwExamId);//作业试题映射id(即：zy_hw_exam 主键)
		r.setType(ExamResvoleType.TEXT.getKey());
		r.setContent(resolve);
		int num = examResolveMapper.insert(r);
		boolean result = true;
		if (num < 0) {
			result = false;
		}
		//更新作业试题映射对象的是否有解析
		result = updateHasResolve(hwId);

		//组装返回的数据
		SmartMap<String, Object> retVal = new SmartMap<String, Object>();
		retVal.put("result", result);
		retVal.put("teacherId", SsoFetchUser.getUserIdWithEx());
		//retVal.put("resolve", resolve);
		return retVal;
	}

	/**
	 * 更新作业试题映射对象的是否有解析
	 * <p>
	 *
	 * @param hwId 作业对象Id
	 */
	private boolean updateHasResolve(long hwId) {
		SsoFetchUser.getUserIdWithEx();
		//查询作业是否有解析
		Boolean hasResolve = homeworkMapper.selectHasResolveById(hwId);
		if (Util.isEmpty(hasResolve) || !hasResolve) {
			//更新作业是否有解析
			return homeworkMapper.updateHasResolveById(hwId, true) > 0;
		}
		return true;
	}

	// 每个学生的作业的subjectiveRate(主观题正答率)[zy_student_homework table]
	@Override
	public boolean finishStudentStat(long hwId) {
		SsoFetchUser.getUserIdWithEx();
		if (hwId < 0) {
			throw new VkoServiceException(String.format("作业不存在(%s)", hwId));
		}
		// 主观题正答率规则：
		// · 主客观题的满分各是100分;
		// · 主观题：一个主观题得分=主观题得分；主观题得分（平均分）=主观题得分的求和/主观题总数/100分*100%；
		return studentHomeworkMapper.updateSubjectiveRate(hwId) > 0;
	}

	/**
	 * @see cn.vko.zuoye.service.TeacherCheckService#finishHomwork(long)
	 */
	@Override
	public boolean finishHomwork(long hwId) {
		SsoFetchUser.getUserIdWithEx();
		//更新作业的avgScore(主观题平均正答率), status=3（已判）
		//BigDecimal avgRate = studentHomeworkMapper.selectAvgRate(hwId);
		//TODO 主观题平均正答率:过滤未提交的人
		return homeworkMapper.updateAvgScoreAndStatus(hwId, HomeworkStatus.CORRECTED.getKey()) > 0;
	}

	/**
	 * 向学生发送消息提醒（提醒查看成绩）
	 * <p>
	 * 1.发送短信提醒
	 * 2.发送站内信
	 * 3.发送App消息（TODO）
	 *
	 * @param hwId 作业Id
	 */
	@Override
	public void sendMsgToStudent(long hwId) {
		SsoFetchUser.getUserIdWithEx();
		Homework hw = homeworkMapper.selectByPrimaryKey(hwId);
		//获取所有学生
		List<SmartMap<String, Object>> student = groupMapper.selectStudentByGroupId(hw.getGroupId());
		List<Long> userIds = new ArrayList<Long>();
		List<StudentHomework> records = studentHomeworkMapper.selectStudent(hwId);
		for (int j = 0; j < student.size(); j++) {
			userIds.add(student.get(j).getLong("userId"));
		}

		//给学生发送短信
		List<String> mobileArray = new ArrayList<String>();
		List<SmartMap<String, Object>> mobiles = vkoMapper.selectMobileByUserId(userIds);
		for (SmartMap<String, Object> mobile : mobiles) {
			String mobileStr = mobile.getString("mobile");
			if (mobileStr != null && mobileStr.trim().length() > 0) {
				mobileArray.add(mobileStr.trim());
			}
		}
		String message = "%s的%s作业成绩已可以查询,请及时查看!";
		String content = String.format(message, DateUtil.formatDateTime(hw.getStartTime()), hw.getName(),
				DateUtil.formatDateTime(hw.getEndTime()));
		SmsUtil.send(content + "【微课网】", mobileArray.toArray(new String[0]));

		//给学生发站内信 
		vkoMapper.insertMessage(teacherMakeHWService.getMessages(records,
				"<a href=\"http://zy.vko.cn/vhw/%s.html\" target=\"_blank\">" + content + "</a>"));
	}
}
