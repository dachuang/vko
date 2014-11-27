package cn.vko.zuoye.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 教师作业和试题映射表实体
 * @author   杨闯
 * @Date	 2014-7-14
 */
@Data
public class HwExam implements Serializable {
	/**主键**/
	private Long id;
	/**作业id**/
	private Long hwId;
	/**是否是客观题**/
	private Boolean objective;
	/**试题id(自定义试题和题库试题)**/
	private Long examId;
	/**题号**/
	private Integer examOrder;
	/**求解析次数**/
	private Integer begNum;
	/**客观题正确率**/
	private java.math.BigDecimal rightRate;
	/**客观题正确人数**/
	private Integer rightNum;
	/**客观题错误人数**/
	private Integer wrongNum;
	/**已审阅人数**/
	private Integer reviewedNum;
	/**未审阅人数**/
	private Integer unreviewNum;
	/**类型(1根据题库发布,2老师上传发布)前缀100000**/
	private Integer type;

}
