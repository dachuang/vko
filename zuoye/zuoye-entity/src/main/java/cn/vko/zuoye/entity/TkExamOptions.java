/**
 * TKExamOptions.java
 * cn.vko.zuoye.entity
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 * @author   pc
 * @Date	 2014-7-29 	 
 */
@Data
public class TkExamOptions implements Serializable {

	/**主键**/
	private Long id;
	/**试题id**/
	private Long examId;
	/**选项**/
	private String selection;
	/**选择人数**/
	private Integer orderNum;
	/**选项是否正确**/
	private Integer num;
}
