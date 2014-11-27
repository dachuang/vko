package cn.vko.zuoye.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 试题选项统计表实体
 * @author   杨闯
 * @Date	 2014-7-14
 */
@Data
public class ExamOptions implements Serializable {
	/**主键**/
	private Long id;
	/**作业id**/
	private Long hwId;
	/**试题id**/
	private Long examId;
	/**选项**/
	private String options;
	/**选择人数**/
	private Integer total;
	/**选项是否正确**/
	private Boolean isRight;

}
