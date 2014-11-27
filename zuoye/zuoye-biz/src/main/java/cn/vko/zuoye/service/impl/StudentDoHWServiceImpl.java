/**
 * TeacherHomeServiceImpl.java
 * cn.vko.zuoye.service.impl
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.vko.zuoye.entity.ExamOptions;
import cn.vko.zuoye.entity.ExamResolve;
import cn.vko.zuoye.entity.Homework;
import cn.vko.zuoye.entity.HwAnswerCard;
import cn.vko.zuoye.entity.HwExam;
import cn.vko.zuoye.entity.HwImages;
import cn.vko.zuoye.entity.StudentAnswer;
import cn.vko.zuoye.entity.StudentHomework;
import cn.vko.zuoye.keyvalue.HomeworkDistribution;
import cn.vko.zuoye.keyvalue.HomeworkStatus;
import cn.vko.zuoye.keyvalue.HomeworkType;
import cn.vko.zuoye.keyvalue.StudentHomeworkStatus;
import cn.vko.zuoye.service.BaseService;
import cn.vko.zuoye.service.StudentDoHWService;
import cn.vko.zuoye.service.sso.SsoFetchUser;

import com.alibaba.fastjson.JSON;
import com.vko.core.common.exception.VkoServiceException;
import com.vko.core.common.util.MathUtil;
import com.vko.core.common.util.SimpleJudgeUtil;
import com.vko.core.common.util.SmartMap;

/**
 * 学生做作业
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-16
 */
@Service
@SuppressWarnings("rawtypes")
public class StudentDoHWServiceImpl extends BaseService implements StudentDoHWService {

	@Override
	public Map<String, Object> getData4DoHW(Long studentHWId) {
		StudentHomework stHW = studentHomeworkMapper.selectByPrimaryKey(studentHWId);
		long userId = SsoFetchUser.getUserIdWithEx();
		if (stHW == null) {
			throw new VkoServiceException("该作业不存在");
		}
		if (stHW.getStudentId() != userId) {
			throw new VkoServiceException("该作业不是您的作业");
		}
		if (stHW.getStatus() != StudentHomeworkStatus.NOT_HAND_IN.getKey()) {
			throw new VkoServiceException("您已经提交过作业");
		}
		Map<String, Object> result = new HashMap<String, Object>();
		Homework homework = homeworkMapper.selectHWForDoHW(stHW.getHwId());
		//判断作业状态
		if (homework.getStatus() != HomeworkStatus.SENDED.getKey()) {
			throw new VkoServiceException("该作业已经超过交作业期限");
		}
		//判断分发状态
		if (homework.getDistribution() != HomeworkDistribution.DAI_SHOU.getKey()) {
			throw new VkoServiceException("该作业已经超过交作业期限");
		}
		result.put("homework", homework);
		result.put("stHW", stHW);
		//判断作业类型
		if (HomeworkType.TIKU_CHANNEL.getKey() == homework.getType()) {
			List<SmartMap<String, Object>> examList = getDataFromTK(stHW.getHwId());
			result.put("examList", examList);
		} else {
			List<HwAnswerCard> examList = getDataFromAnswerCard(stHW.getHwId());
			result.put("examList", examList);
			//查询图片
			List<HwImages> hwImagesList = hwImagesMapper.selectByHWId(stHW.getHwId());
			result.put("hwImagesList", hwImagesList);
		}

		return result;
	}

	/**
	 * 保存用户求解析次数
	 * 返回多少人求解析
	 * <p>
	 * @param hwId
	*/
	@Override
	public int saveBegTimes(Long hwId, Long examId) {
		hwExamMapper.updateBegNum(hwId, examId);
		return hwExamMapper.selectBegNums(hwId, examId);
	}

	/**
	 * 用户开始答题的更新操作
	 * <p>
	*/
	@Override
	public void updateStudentHWStart(Long studentHWId) {
		StudentHomework record = new StudentHomework();
		record.setId(studentHWId);
		//更新开始答题时间
		record.setStartTime(new Date());
		studentHomeworkMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 学生提交作业完成后的操作
	 * <p>
	 *
	*/
	@Override
	public void updateStudentHWEnd(Long studentHWId) {
		StudentHomework homework = studentHomeworkMapper.selectByPrimaryKey(studentHWId);
		//判定客观题
		List<StudentAnswer> studentAnswers = studentAnswerMapper.selectObjectiveByStudentHWId(studentHWId);
		List<Long> rightIds = new ArrayList<Long>();
		List<Long> wrongIds = new ArrayList<Long>();
		for (int i = 0; i < studentAnswers.size(); i++) {
			StudentAnswer sa = studentAnswers.get(i);
			//如果答案是0,1是判断
			boolean isRight = SimpleJudgeUtil.isRight(sa.getAnswer(), sa.getRightAnswer());
			if (isRight) {
				rightIds.add(sa.getId());
			} else {
				wrongIds.add(sa.getId());
			}
		}
		//分别更新对错
		if (rightIds.size() > 0) {
			studentAnswerMapper.updateObjectiveIsRight(rightIds, 1);
		}
		if (wrongIds.size() > 0) {
			studentAnswerMapper.updateObjectiveIsRight(wrongIds, 0);
		}
		StudentHomework record = new StudentHomework();
		record.setId(studentHWId);
		Date startTime = homework.getStartTime();
		Date endTime = new Date();
		record.setEndTime(endTime);
		Long elapse = (endTime.getTime() - startTime.getTime()) / 1000;
		record.setElapse(elapse.intValue());
		record.setRightNum(rightIds.size());
		//结束实时判定学生的客观题成绩
		if (homework.getObjectiveNum() != null) {
			if (homework.getObjectiveNum() == 0) {
				record.setObjectiveRate(new BigDecimal(0));
			} else {
				record.setObjectiveRate(new BigDecimal(MathUtil.div(rightIds.size() * 100, homework.getObjectiveNum(),
						2)));
			}
		}
		record.setStatus(StudentHomeworkStatus.HAND_IN.getKey());
		studentHomeworkMapper.updateByPrimaryKeySelective(record);
	}

	/*
	 * 以下根据题库中的试题做作业
	 */
	@Override
	public List<SmartMap<String, Object>> getDataFromTK(Long hwId) {
		//查询试题
		List<HwExam> hwExamList = hwExamMapper.selectExamsByHWId(hwId);
		List<Long> examIds = new ArrayList<Long>();
		final Map<Long, HwExam> orderMap = new HashMap<Long, HwExam>();
		for (int i = 0; i < hwExamList.size(); i++) {
			HwExam exam = hwExamList.get(i);
			examIds.add(exam.getExamId());
			orderMap.put(exam.getExamId(), exam);
		}
		List<SmartMap<String, Object>> examsList = tikuMapper.getExamsContent(examIds);
		for (int i = 0; i < examsList.size(); i++) {
			SmartMap<String, Object> exam = examsList.get(i);
			exam.put("zyInfo", orderMap.get(exam.getLong("id")));

		}
		Collections.sort(examsList, new Comparator<SmartMap>() {
			@Override
			public int compare(SmartMap o1, SmartMap o2) {
				Long id1 = o1.getLong("id");
				Long id2 = o2.getLong("id");
				Integer order1 = orderMap.get(id1).getExamOrder();
				Integer order2 = orderMap.get(id2).getExamOrder();
				return order1.compareTo(order2);

			}

		});
		return examsList;
	}

	@Override
	public void saveHWAnswer4TK(Map<String, String[]> answers, Long studentHWId) {
		StudentHomework stHW = studentHomeworkMapper.selectByPrimaryKey(studentHWId);

		long userId = SsoFetchUser.getUserIdWithEx();
		if (stHW == null) {
			throw new VkoServiceException("该作业不存在");
		}
		if (stHW.getStudentId() != userId) {
			throw new VkoServiceException("该作业不是您的作业");
		}
		if (stHW.getStatus() != StudentHomeworkStatus.NOT_HAND_IN.getKey()) {
			throw new VkoServiceException("您已经提交过作业");
		}
		Homework homework = homeworkMapper.selectHWForDoHW(stHW.getHwId());
		//判断作业状态
		if (homework.getStatus() != HomeworkStatus.SENDED.getKey()) {
			throw new VkoServiceException("该作业已经超过交作业期限");
		}
		//判断分发状态
		if (homework.getDistribution() != HomeworkDistribution.DAI_SHOU.getKey()) {
			throw new VkoServiceException("该作业已经超过交作业期限");
		}
		if (stHW.getStatus() == StudentHomeworkStatus.HAND_IN.getKey()) {
			throw new VkoServiceException("不能重复交作业");
		}
		//		Homework homework = homeworkMapper.selectHWForDoHW(stHW.getHwId());
		List<HwExam> hwExamList = hwExamMapper.selectExamsByHWId(stHW.getHwId());
		List<Long> examIds = new ArrayList<Long>();
		for (int i = 0; i < hwExamList.size(); i++) {
			HwExam exam = hwExamList.get(i);
			examIds.add(exam.getExamId());
		}
		//包含所有试题答案
		List<SmartMap<String, Object>> examsList = tikuMapper.getExamsAnswers(examIds);
		Map<Long, SmartMap<String, Object>> answerMap = new HashMap<Long, SmartMap<String, Object>>();
		for (int i = 0; i < examsList.size(); i++) {
			SmartMap<String, Object> map = examsList.get(i);
			answerMap.put(map.getLong("id"), map);
		}
		//需插入数据
		List<StudentAnswer> studentAnswerList = new ArrayList<StudentAnswer>();
		for (int i = 0; i < hwExamList.size(); i++) {
			HwExam exam = hwExamList.get(i);
			String[] answerString = answers.get("answer_" + exam.getExamId());
			//保存到学生答题表里面
			StudentAnswer answer = new StudentAnswer();
			answer.setAnswer(join(answerString));
			answer.setExamId(exam.getExamId());
			answer.setHwId(stHW.getHwId());
			answer.setRealName(stHW.getStudentName());
			answer.setRightAnswer(answerMap.get(exam.getExamId()).getString("answer"));
			//answer.setObjective(answerMap.get(exam.getExamId()).getBoolean("objective"));
			answer.setStudentHwId(studentHWId);
			answer.setStudentId(stHW.getStudentId());
			answer.setObjective(exam.getObjective());
			studentAnswerList.add(answer);
		}
		studentAnswerMapper.insert(studentAnswerList);
	}

	private static String join(String[] answerString) {
		if (answerString == null) {
			return "";
		}
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < answerString.length; i++) {
			str.append(answerString[i].trim());
		}
		return str.toString();
	}

	/*
	 * 以下根据上传的图片做作业的处理
	 */
	@Override
	public List<HwAnswerCard> getDataFromAnswerCard(Long hwId) {
		//查询试题
		List<HwExam> hwExamList = hwExamMapper.selectExamsByHWId(hwId);
		List<Long> examIds = new ArrayList<Long>();
		final Map<Long, Integer> orderMap = new HashMap<Long, Integer>();
		for (int i = 0; i < hwExamList.size(); i++) {
			HwExam exam = hwExamList.get(i);
			examIds.add(exam.getExamId());
			orderMap.put(exam.getExamId(), exam.getExamOrder());
		}
		List<HwAnswerCard> cardList = hwAnswerCardMapper.selectAllByIds(examIds);
		Collections.sort(cardList, new Comparator<HwAnswerCard>() {
			@Override
			public int compare(HwAnswerCard o1, HwAnswerCard o2) {
				Long id1 = o1.getId();
				Long id2 = o2.getId();
				Integer order1 = orderMap.get(id1);
				Integer order2 = orderMap.get(id2);
				return order1.compareTo(order2);

			}

		});
		return cardList;
	}

	@Override
	public void saveHWAnswer4Shouji(Map<String, String[]> answers, Long studentHWId) {
		StudentHomework stHW = studentHomeworkMapper.selectByPrimaryKey(studentHWId);
		long userId = SsoFetchUser.getUserIdWithEx();
		if (stHW == null) {
			throw new VkoServiceException("该作业不存在");
		}
		if (stHW.getStudentId() != userId) {
			throw new VkoServiceException("该作业不是您的作业");
		}
		if (stHW.getStatus() != StudentHomeworkStatus.NOT_HAND_IN.getKey()) {
			throw new VkoServiceException("您已经提交过作业");
		}
		Homework homework = homeworkMapper.selectHWForDoHW(stHW.getHwId());
		//判断作业状态
		if (homework.getStatus() != HomeworkStatus.SENDED.getKey()) {
			throw new VkoServiceException("该作业已经超过交作业期限");
		}
		//判断分发状态
		if (homework.getDistribution() != HomeworkDistribution.DAI_SHOU.getKey()) {
			throw new VkoServiceException("该作业已经超过交作业期限");
		}
		if (stHW.getStatus() == StudentHomeworkStatus.HAND_IN.getKey()) {
			throw new VkoServiceException("不能重复交作业");
		}
		if (stHW.getStatus() == StudentHomeworkStatus.HAND_IN.getKey()) {
			throw new VkoServiceException("不能重复交作业");
		}
		//		Homework homework = homeworkMapper.selectHWForDoHW(stHW.getHwId());
		List<HwExam> hwExamList = hwExamMapper.selectExamsByHWId(stHW.getHwId());
		List<Long> examIds = new ArrayList<Long>();
		for (int i = 0; i < hwExamList.size(); i++) {
			HwExam exam = hwExamList.get(i);
			examIds.add(exam.getExamId());
		}
		//包含所有试题答案
		List<HwAnswerCard> cardList = hwAnswerCardMapper.selectAllByIds(examIds);
		Map<Long, String> answerMap = new HashMap<Long, String>();
		for (int i = 0; i < cardList.size(); i++) {
			HwAnswerCard map = cardList.get(i);
			answerMap.put(map.getId(), map.getAnswer());
		}
		//需插入数据
		List<StudentAnswer> studentAnswerList = new ArrayList<StudentAnswer>();
		for (int i = 0; i < hwExamList.size(); i++) {
			HwExam exam = hwExamList.get(i);
			String[] answerString = answers.get("answer_" + exam.getExamId());
			//保存到学生答题表里面
			StudentAnswer answer = new StudentAnswer();
			answer.setAnswer(join(answerString));
			answer.setExamId(exam.getExamId());
			answer.setHwId(stHW.getHwId());
			answer.setRealName(stHW.getStudentName());
			answer.setRightAnswer(answerMap.get(exam.getExamId()));
			answer.setObjective(exam.getObjective());
			answer.setStudentHwId(studentHWId);
			answer.setStudentId(stHW.getStudentId());
			studentAnswerList.add(answer);
		}

		studentAnswerMapper.insert(studentAnswerList);
	}

	/**
	 * 查看作业
	 */
	@Override
	public Map<String, Object> getData4ViewHW(Long studentHWId) {
		StudentHomework stHW = studentHomeworkMapper.selectByPrimaryKey(studentHWId);
		long userId = SsoFetchUser.getUserIdWithEx();
		if (stHW == null) {
			throw new VkoServiceException("该作业不存在");
		}
		if (stHW.getStudentId() != userId) {
			throw new VkoServiceException("该作业不是您的作业");
		}
		Map<String, Object> result = new HashMap<String, Object>();
		Homework homework = homeworkMapper.selectHWForDoHW(stHW.getHwId());
		result.put("homework", homework);
		result.put("stHW", stHW);
		String useTime = TeacherStatServiceImpl.fillUseTime(stHW.getElapse());
		result.put("useTime", useTime);
		//查询试题
		//查询答案
		List<StudentAnswer> studentAnswers = studentAnswerMapper.selectAllByStudentHWId(studentHWId);
		Map<Long, StudentAnswer> answerMap = new HashMap<Long, StudentAnswer>();
		for (int i = 0; i < studentAnswers.size(); i++) {
			StudentAnswer answer = studentAnswers.get(i);
			if ("1".equals(answer.getAnswer())) {
				answer.setAnswer("对");
			} else if ("0".equals(answer.getAnswer())) {
				answer.setAnswer("错");
			}
			if ("1".equals(answer.getRightAnswer())) {
				answer.setRightAnswer("对");
			} else if ("0".equals(answer.getRightAnswer())) {
				answer.setRightAnswer("错");
			}

			answerMap.put(answer.getExamId(), answer);
		}
		//查询别人都选什么
		List<ExamOptions> optionList = examOptionsMapper.selectExamOptions(stHW.getHwId());
		//转化成map
		Map<Long, List<ExamOptions>> optionMap = new HashMap<Long, List<ExamOptions>>();
		for (int i = 0; i < optionList.size(); i++) {
			ExamOptions option = optionList.get(i);
			if ("1".equals(option.getOptions())) {
				option.setOptions("对");
			} else if ("0".equals(option.getOptions())) {
				option.setOptions("错");
			}
			List<ExamOptions> list = optionMap.get(option.getExamId());
			if (list == null) {
				list = new ArrayList<ExamOptions>();
				optionMap.put(option.getExamId(), list);
			}
			list.add(option);
		}
		//判断作业类型
		if (HomeworkType.TIKU_CHANNEL.getKey() == homework.getType()) {
			List<SmartMap<String, Object>> examList = getDataFromTK(stHW.getHwId());
			List<HwExam> hwExamList = hwExamMapper.selectExamsByHWId(stHW.getHwId());
			List<Long> idlist = new ArrayList<Long>();
			for (HwExam exam : hwExamList) {
				idlist.add(exam.getId());
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
			for (int i = 0; i < examList.size(); i++) {
				SmartMap<String, Object> exam = examList.get(i);
				StudentAnswer an = answerMap.get(exam.getLong("id"));
				if (an == null) {
					an = new StudentAnswer();
					an.setRightAnswer(exam.getString("answer"));
				}
				exam.put("answer", an);
				exam.put("option", optionMap.get(exam.getLong("id")));
				exam.put("k2List", getExamsAnswerK2(exam.getLong("id")));
				exam.put("resolvesList", getExamsResolve(exam.getLong("id")));
				HwExam hwExam = (HwExam) exam.get("zyInfo");
				exam.put("teacherResolves", map.get(hwExam.getId()));
			}
			result.put("examList", examList);
		} else {
			List<HwAnswerCard> examList = getDataFromAnswerCard(stHW.getHwId());
			List<SmartMap<String, Object>> mapList = new ArrayList<SmartMap<String, Object>>();
			for (int i = 0; i < examList.size(); i++) {
				HwAnswerCard exam = examList.get(i);
				SmartMap<String, Object> map = JSON.parseObject(JSON.toJSONString(exam), SmartMap.class);
				StudentAnswer an = answerMap.get(exam.getId());
				if (an == null) {
					an = new StudentAnswer();
					an.setRightAnswer(exam.getAnswer());
				}
				map.put("answer", an);
				map.put("option", optionMap.get(exam.getId()));

				mapList.add(map);
			}
			result.put("examList", mapList);
			//查询图片
			List<HwImages> hwImagesList = hwImagesMapper.selectByHWId(stHW.getHwId());
			result.put("hwImagesList", hwImagesList);
		}

		return result;

	}

	@Override
	public List<SmartMap<String, Object>> getExamsResolve(long examId) {
		//String sql = "select * from tk_exams_resolve where examsId=" + examId + " and status=2 order by type asc";
		List<SmartMap<String, Object>> examList = tikuMapper.selectResolveByExamId(examId);
		for (int i = 0; i < examList.size(); i++) {
			SmartMap<String, Object> resolve = examList.get(i);
			Long type = (Long) resolve.get("type");
			if (3 == type) {

			}
		}
		return examList;

	}

	@Override
	public List<SmartMap<String, Object>> getExamsAnswerK2(long examId) {
		SmartMap<String, Object> result = tikuMapper.selectK2ByExamId(examId);
		String[] ids = StringUtils.tokenizeToStringArray(result.getString("k2"), ",");
		List<SmartMap<String, Object>> resultList = new ArrayList<SmartMap<String, Object>>();
		for (int i = 0; i < ids.length; i++) {
			resultList.add(vkoMapper.selectSysCodeById(new Long(ids[i])));
		}
		return resultList;

	}

	@Override
	public int saveMyFalse(Long hwId, Long examId) {
		//先查询有木有
		long studentId = SsoFetchUser.getUserIdWithEx();
		Integer flag = tikuMapper.selectIsAddedMyFalse(studentId, examId);
		if (flag != null) {
			throw new VkoServiceException("您已经添加过此题");
		}
		Homework homework = homeworkMapper.selectByPrimaryKey(hwId);
		SmartMap<String, Object> result = tikuMapper.getExamBaseInfoById(examId);
		result.put("gmt_create_id", studentId);
		result.put("gmt_created", new Date());
		result.put("examsId", examId);
		result.put("reason", "");
		result.put("source", homework.getName());

		return tikuMapper.insertMyFlase(result);

	}

	@Override
	public int updateCommentTimes(Long hwId) {
		return homeworkMapper.updateCommentTimes(hwId);
	}

	@Override
	public Homework getHomeworkInfo(Long hwId, Long studentHWId) {
		if (hwId != null) {
			return homeworkMapper.selectByPrimaryKey(hwId);
		}
		StudentHomework homework = studentHomeworkMapper.selectByPrimaryKey(studentHWId);
		return homeworkMapper.selectByPrimaryKey(homework.getHwId());
	}

	@Override
	public StudentHomework getStHomeworkInfo(Long studentHWId) {

		return studentHomeworkMapper.selectByPrimaryKey(studentHWId);

	}
}
