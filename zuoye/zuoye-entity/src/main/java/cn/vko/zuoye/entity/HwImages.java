package cn.vko.zuoye.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 教师作业上传图片表
 * @author   杨闯
 * @Date	 2014-7-14
 */
@Data
public class HwImages implements Serializable {
	/**主键**/
	private Long id;
	/**作业id**/
	private Long hwId;
	/**原图路径**/
	private String path;
	/**缩略图路径**/
	private String cover;
	/**名称**/
	private String name;
	/**顺序**/
	private Integer orderNum;
	/**创建时间**/
	private java.util.Date createTime;

}
