package cn.vko.zuoye.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 试题选项选择人表实体
 * @author   杨闯
 * @Date	 2014-7-14
 */
@Data
public class StudentOptions implements Serializable {
	/**主键**/
	private Long id;
	/**选项统计id**/
	private Long statId;
	/**用户id**/
	private Long studentId;
	/**用户姓名**/
	private String realName;

}
