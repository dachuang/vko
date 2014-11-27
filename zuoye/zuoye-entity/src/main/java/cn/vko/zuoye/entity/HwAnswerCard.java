package cn.vko.zuoye.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 教师上传图片作业答题卡表实体
 * @author   杨闯
 * @Date	 2014-7-14
 */
@Data
public class HwAnswerCard implements Serializable {
	/**主键**/
	private Long id;
	/**作业id**/
	private Long hwId;
	/**题号**/
	private Integer examOrder;
	/**题目类型(单选,多选,判断,其他)类型同题库**/
	private Integer type;
	/**试题答案(A,AB,0,1)**/
	private String answer;
	/**是否是客观题**/
	private Boolean objective;

}
