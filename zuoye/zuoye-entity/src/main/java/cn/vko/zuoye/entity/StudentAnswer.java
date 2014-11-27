package cn.vko.zuoye.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 学生作业作答表实体
 * @author   杨闯
 * @Date	 2014-7-14
 */
@Data
public class StudentAnswer implements Serializable {
	/**主键**/
	private Long id;
	/**学生作业记录id**/
	private Long studentHwId;
	/**学生id**/
	private Long studentId;
	/**学生姓名**/
	private String realName;
	/**作业id**/
	private Long hwId;
	/**试题id**/
	private Long examId;
	/**用户答案**/
	private String answer;
	/**主观题得分分数**/
	private Integer score;
	/**客观题是否正确**/
	private Boolean isRight;
	/**正确答案**/
	private String rightAnswer;
	/**是否是客观题**/
	private Boolean objective;
	/**是否已批阅**/
	private Boolean isCheck;

}
