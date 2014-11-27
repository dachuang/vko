package cn.vko.zuoye.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 教师作业表实体
 * @author   杨闯
 * @Date	 2014-7-14
 */
@Data
public class Homework implements Serializable {
	/**主键**/
	private Long id;
	/**作业名称**/
	private String name;
	/**类型(1根据题库发布,2老师上传发布)前缀100000**/
	private Integer type;
	/**发布时间**/
	private java.util.Date startTime;
	/**收缴时间**/
	private java.util.Date endTime;
	/**创建时间**/
	private java.util.Date createTime;
	/**主观题数量**/
	private Integer subjectiveNum;
	/**客观题数量**/
	private Integer objectiveNum;
	/**总题数**/
	private Integer examNum;
	/**需交作业人数**/
	private Integer needHandNum;
	/**已交作业人数**/
	private Integer handedNum;
	/**未交作业人数**/
	private Integer notFinishNum;
	/**学科id**/
	private Long subjectId;
	/**学科名称**/
	private String subjectName;
	/**老师id**/
	private Long teacherId;
	/**教师名称**/
	private String teacherName;
	/**班级群id**/
	private Long groupId;
	/**群名称**/
	private String groupName;
	/**用时限制**/
	private Long timeout;
	/**作业说明**/
	private String remark;
	/**状态(1已发,2待判,3已判)前缀101000**/
	private Integer status;
	/**平均耗时**/
	private Integer elapse;
	/**平均耗时(字符显示)**/
	private String elapseDisplay;
	/**客观题平均正答率**/
	private java.math.BigDecimal rightRate;
	/**主观题平均正答率**/
	private java.math.BigDecimal avgScore;
	/**是否有解析**/
	private Boolean hasResolve;
	/**评论数**/
	private Integer commentTimes;
	/**作业分发状态控制(1待发,2待收,3已收,4已判)前缀104000**/
	private Integer distribution;

}
