/**
 * MyFalse.java
 * cn.vko.zuoye.entity
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.zuoye.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import lombok.Data;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 * @author   pc
 * @Date	 2014-7-23 	 
 */
@Data
public class MyFalse implements Serializable {

	/**
	 * 主键
	 */
	private Long id;
	private Long gmt_create_id;
	private Date gmt_created;
	private Integer gmt_delete;
	private Date gmt_modify;
	private Long gmt_modify_id;
	/**
	 * 试题ID
	 * */
	private Long examsId;
	/**
	 * 试题内容
	 */
	private String examContent;

	private Map<String, Object> resolve;
	/**
	 * 错题原因
	 */
	private String reason;
	/**
	 * 学科ID
	 */
	private Long subjectId;
	/**
	 * 学科名称
	 */
	private String subjectName;
	/**
	 * 题型ID
	 */
	private Long typeId;
	/**
	 * 题型名称
	 */
	private String typeName;
	/**
	 *来源 
	 */
	private String source;
}
