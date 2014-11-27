package cn.vko.zuoye.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 作业试题解析表实体
 * @author   杨闯
 * @Date	 2014-7-14
 */
@Data
public class ExamResolve implements Serializable {
	/**主键**/
	private Long id;
	/**作业试题映射id**/
	private Long hwExamId;
	/**解析类型(1文本,2视频)**/
	private Integer type;
	/**解析内容**/
	private String content;
	/**视频地址**/
	private String videoUrl;

}
