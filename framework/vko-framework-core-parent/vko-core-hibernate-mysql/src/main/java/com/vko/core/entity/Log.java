package com.vko.core.entity;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;


/**
 * 日志实体
 * @author xjd
 *
 */

@Entity
@Table(name="tab_pub_log")
@Proxy(lazy = false)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Log extends EntityID implements Externalizable{

	private String logID; 		//日志流水号
	private String operator;	//操作员
	private String userID;		//操作人员ID
	private String logInfo;		//处理信息
	private Date receiveDate;	//受理时间
	private String result;		//处理结果（1-成功 ，0-失败）
	private String companyName;	//所属单位名
//	private String channelName;	//操作栏目
	
	public String getLogID() {
		return logID;
	}
	public void setLogID(String logID) {
		this.logID = logID;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
//	public String getChannelName() {
//		return channelName;
//	}
//	public void setChannelName(String channelName) {
//		this.channelName = channelName;
//	}
	public String getLogInfo() {
		return logInfo;
	}
	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public void readExternal(ObjectInput in)
    throws IOException, ClassNotFoundException
  {
    setId(in.readUTF());
    setLogID(in.readUTF());
    setOperator(in.readUTF());
    setUserID(in.readUTF());
    setLogInfo(in.readUTF());
    setReceiveDate(new Date(in.readLong()));
    setResult(in.readUTF());
    setCompanyName(in.readUTF());
  }

  public void writeExternal(ObjectOutput out)
    throws IOException
  {
    out.writeUTF(this.id);
    out.writeUTF(this.logID);
    out.writeUTF(this.operator);
    out.writeUTF(this.userID);
    out.writeUTF(this.logInfo);
    out.writeLong(this.receiveDate.getTime());
    out.writeUTF(this.result);
    out.writeUTF(this.companyName);
  }
}
