/**
 * @Title:BaseEntity.java
 * @Package:com.goothink.core.entity
 * @Description:TODO
 * @version 1.0
 *
 */
package com.vko.core.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @ClassName:BaseEntity
 * @Desctiption:定义实体类的基类
 * @author kingzhu
 * @date 2012-8-24下午03:29:09
 * 
 */
@MappedSuperclass
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = -7138899045374410195L;

	/**
	 * 创建时间
	 * 
	 */
	protected Date gmtCreated;

	/**
	 * 最后更新时间
	 * 
	 */
	protected Date gmtModify;

	/**
	 * 创建者id
	 * 
	 */
	protected Long gmtCreateId;

	/**
	 * 最后最新者Id
	 * 
	 */
	protected Long gmtModifyId;

	/**
	 * 删除状态
	 * @see DeleteStatus
	 * 
	 */
	protected Integer gmtDeleted = DeleteStatus.NOT_DEL.intKey();

	@Column(name = "gmt_created")
	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	@Column(name = "gmt_modify")
	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	@Column(name = "gmt_create_id")
	public Long getGmtCreateId() {
		return gmtCreateId;
	}

	public void setGmtCreateId(Long gmtCreateId) {
		this.gmtCreateId = gmtCreateId;
	}

	@Column(name = "gmt_modify_id")
	public Long getGmtModifyId() {
		return gmtModifyId;
	}

	public void setGmtModifyId(Long gmtModifyId) {
		this.gmtModifyId = gmtModifyId;
	}

	@Column(name = "gmt_delete")
	public Integer getGmtDeleted() {
		return gmtDeleted;
	}

	public void setGmtDeleted(Integer gmtDeleted) {
		this.gmtDeleted = gmtDeleted;
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	

	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * 定义删除状态,状态可增加不可删除,约定如下
	 * 
	 * <ul>
	 * 	<li>1:未删除</li>
	 * 	<li>2:删除</li>
	 * </ul>
	 */
	public static enum DeleteStatus {

		NOT_DEL(1, "未删除"), DEL(2, "删除");

		private int key;

		private String value;

		private DeleteStatus(final int key, final String value) {
			this.key = key;
			this.value = value;
		}

		public String key() {
			return String.valueOf(key);
		}

		public String value() {
			return value;
		}

		public int intKey() {
			return key;
		}

	}

}
