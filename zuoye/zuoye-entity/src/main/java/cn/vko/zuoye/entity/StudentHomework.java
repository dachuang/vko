package cn.vko.zuoye.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 学生作业记录表实体
 * @author   杨闯
 * @Date	 2014-7-14
 */
@Data
public class StudentHomework implements Serializable {
	/**主键**/
	private Long id;
	/**作业id**/
	private Long hwId;
	/**作业名称**/
	private String hwName;
	/**学生id**/
	private Long studentId;
	/**学生姓名**/
	private String studentName;
	/**班级群id**/
	private Long groupId;
	/**状态(1未交,2已交)前缀102000**/
	private Integer status;
	/**开始答题时间**/
	private java.util.Date startTime;
	/**交作业时间**/
	private java.util.Date endTime;
	/**耗时**/
	private Integer elapse;
	/**创建时间**/
	private java.util.Date createTime;
	/**客观题正确题数**/
	private Integer rightNum;
	/**客观题总题数**/
	private Integer objectiveNum;
	/**客观题正答率**/
	private java.math.BigDecimal objectiveRate;
	/**主观题题数**/
	private Integer subjectiveNum;
	/**主观题正答率**/
	private java.math.BigDecimal subjectiveRate;
	/**学科id**/
	private Long subjectId;
	/**学科名称**/
	private String subjectName;
}
