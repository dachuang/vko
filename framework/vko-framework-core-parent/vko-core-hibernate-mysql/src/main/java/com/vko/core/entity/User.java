package com.vko.core.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

/**
 * 用户实体
 * 
 * @author leosun
 * 
 */

@Entity
@Table(name = "dk_user")
@Proxy(lazy = false)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
// @Indexed(index = "search_user")
public class User extends EntityID {
	private static final long serialVersionUID = 8026813053768023527L;
	private String username; // 帐名
	private String password; // 登录密码
	private String paypassword; // 支付密码（属于二级密码）
	private String nickname; // 昵称
	private int type = 1; // 用户类型 1-普通用户 2-蓝钻用户 3-黄钻用户
	private int admintype = 0; // 管理员类型 16777215-系统管理员 2-地区系统管理员 3-旅行社管理员
	private String name; // 真名
	private int sex = 1; // 性别 1-男性 2-女性
	private String email; // 电子邮件
	private String qq; // qq
	private String wangwang; // 旺旺号
	private String msn; // msn
	private Date birthday; // 出生日期
	private int age = 18; // 年龄
	private int faith = 0; // 诚信值
	private int love = 0; // 爱心值
	private int positiveRating = 0; // 好评率
	private int point = 0; // 积分
	private int level = 0; // 等级
	private String image; // 头像
	private int education = 0; // 教育程度 1-高中以下 2-高中毕业 3-技术学院 4-大专 5-学士 6-硕士 7-博士
	private int bloodType = 0; // 血型 1-A型 2-B型 3-O型 4-AB型 5-其他
	private int bodilyType = 0; // 体型 1-保密 2-很瘦 3-较瘦 4-苗条 5-匀称 6-高挑 7-丰满 8-健壮
								// 9-较胖 10-胖
	private int smoke = 0; // 吸烟习惯 1-不吸烟 2-偶尔吸烟 3-常吸烟
	private int drink = 0; // 喝酒习惯 1-不喝酒 2-偶尔喝酒 3-常喝酒
	private int religion = 0; // 宗教信仰习惯 1-无神论者 2-佛教 3-道教 4-回教 5-基督教 6-天主教 7-其他
	private int occupation = 0; // 职业 1-金融/保险/证券 2-IT 3-互联网 4-电信 5-贸易 6-法律 7-食品
								// 8-化妆品 9-服装 10-家装 11-医药 12-机械 13-汽车 14-房产
								// 15-化工 16-广告/媒体 17-出版印刷 18-咨询 19-酒店/餐饮 20-旅游
								// 21-体育 22-物流 23-培训 24-科研 25-政府 26-农业 27-其它
								// 28-学生 29-教育

	private String mobilephone = "-1"; // 手机号码
	private String idcard = "-1"; // 身份证号
	private int status = 1; // 状态 1-正常 2-锁定
	private String address; // 联系地址
	private String postcode; // 邮编
	private String singurate; // 个性签名
	private int isSingurate = 1; // 默认显示 1-显示 2-隐藏
	private String introduction; // 个人简介
	private Date createdDate; // 创建日期
	private Date updateDate; // 更新日期

	private Date loginDate; // 登录日期
	private String ip; // 最后一次登录的IP地址
	private long topicTotal = 0; // 发帖总数
	private long shareTotal = 0; // 分享总数

	private float perfect = 0.0f; // 资料完善程度

	private int visitCount = 0; // 博客访问总量

	private int isEmailVerify = 0; // 0-邮箱未验证 1-邮箱验证通过
	private int isTelphoneVerify = 0; // 0-手机未验证 1-手机验证通过
	private int isIdCardVerify = 0; // 0-身份证未验证 1-身份证验证通过
	private long bimodifyCount = 0; // 基本资料填写完成标志 1-基本资料填写完成
	private long dimodifyCount = 0; // 详细资料填写完成标志 1-详细资料填写完成

	private int isOnline = 0; // 是否在线 1-在线

	private String blogName; // 博客名称
	private String blogIntroduction; // 博客简介
	private String keyRegister; // 注册邮件确认key
	private String keyInviteEmail; // 邀请邮件确认key
	private String keyPassword; // 找回密码邮件确认key
	private String inviteuser; // 邀请者
	
	private boolean activated = false; //是否激活账户

	private Set<Role> roles;
	private Map<String, List<Resource>> roleResources;

	public User() {
	}

	public User(String id, String username, String password, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public User(String username, String password, String email, Date createdDate) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.createdDate = createdDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Transient
	public String getCreatedDateAsString() {
		return new SimpleDateFormat(Global.DATETIME_FORMAT).format(createdDate);
	}

	@Transient
	public String getLoginDateAsString() {
		if (null != loginDate)
			return new SimpleDateFormat(Global.DATETIME_FORMAT_LONG)
					.format(loginDate);
		else
			return null;
	}

	@Transient
	public String getDateAsString() {
		StringBuffer buffer = new StringBuffer();
		long millTime = System.currentTimeMillis() - updateDate.getTime();
		long second = millTime / 1000;
		long minutes = second / 60;
		long hours = minutes / 60;
		if (second < 60) {
			buffer.append(second).append("秒前");
		} else if (minutes < 60) {
			buffer.append(minutes).append("分钟前");
		} else if (hours < 60) {
			buffer.append(hours).append("小时前");
		} else {
			buffer.append(getCreatedDateAsString());
		}
		return buffer.toString();
	}

	@Column(nullable = true, length = 50)
	// @Email(message="无效的Email地址（示例：xyz@abc.com）")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImage() {
		if (null == image || "" == image) {
			image = "static-resource/images/m-pic.jpg";
		}

		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Column(nullable = false, length = 32)
	// @Pattern(regex="[0-9a-f]{32}", message="无效的口令")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	@Column(updatable = false, nullable = false, length = 20)
	// @Pattern(regex="[a-z0-9]{3,20}", message="用户名只能由英文字母和数字构成，长度为3-20个字符")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Transient
	public Map<String, List<Resource>> getRoleResources() {
		return roleResources;
	}

	public void setRoleResources(Map<String, List<Resource>> roleResources) {
		this.roleResources = roleResources;
	}

	@ManyToMany(targetEntity = Role.class, cascade = { CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinTable(name = "tab_pub_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}


	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getWangwang() {
		return wangwang;
	}

	public void setWangwang(String wangwang) {
		this.wangwang = wangwang;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getFaith() {
		return faith;
	}

	public void setFaith(int faith) {
		this.faith = faith;
	}

	public int getLove() {
		return love;
	}

	public void setLove(int love) {
		this.love = love;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getSingurate() {
		return singurate;
	}

	public void setSingurate(String singurate) {
		this.singurate = singurate;
	}

	public float getPerfect() {
		return perfect;
	}

	public void setPerfect(float perfect) {
		this.perfect = perfect;
	}

	@Transient
	public String getBirthdayString() {
		if (birthday != null && !birthday.equals("")) {
			return new SimpleDateFormat(Global.DATETIME_FORMAT_SHORT)
					.format(birthday);
		}
		return "";
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public int getAdmintype() {
		return admintype;
	}

	public void setAdmintype(int admintype) {
		this.admintype = admintype;
	}

	@Transient
	public long getTopicTotal() {
		return topicTotal;
	}

	public void setTopicTotal(long topicTotal) {
		this.topicTotal = topicTotal;
	}

	@Transient
	public long getShareTotal() {
		return shareTotal;
	}

	public void setShareTotal(long shareTotal) {
		this.shareTotal = shareTotal;
	}

	public int getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("id", id).append("username", username).toString();
	}

	public int getIsEmailVerify() {
		return isEmailVerify;
	}

	public void setIsEmailVerify(int isEmailVerify) {
		this.isEmailVerify = isEmailVerify;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	@Transient
	public int getNextLevelPoint() {
		int nextPoint = 0;
		int nextLevel = this.level + 1;
		if (1 == nextLevel) {
			nextPoint = 1 - this.point;
		} else if (2 == nextLevel) {
			nextPoint = 11 - this.point;
		} else if (3 == nextLevel) {
			nextPoint = 26 - this.point;
		} else if (4 == nextLevel) {
			nextPoint = 51 - this.point;
		} else if (5 == nextLevel) {
			nextPoint = 151 - this.point;
		} else if (6 == nextLevel) {
			nextPoint = 251 - this.point;
		} else if (7 == nextLevel) {
			nextPoint = 501 - this.point;
		} else if (8 == nextLevel) {
			nextPoint = 1001 - this.point;
		} else if (9 == nextLevel) {
			nextPoint = 2001 - this.point;
		} else if (10 == nextLevel) {
			nextPoint = 5001 - this.point;
		} else if (11 == nextLevel) {
			nextPoint = 10001 - this.point;
		} else if (12 == nextLevel) {
			nextPoint = 20001 - this.point;
		} else if (13 == nextLevel) {
			nextPoint = 50001 - this.point;
		} else if (14 == nextLevel) {
			nextPoint = 100001 - this.point;
		} else if (15 == nextLevel) {
			nextPoint = 200001 - this.point;
		}
		return nextPoint;
	}

	public int getLevel() {
		if (1 <= this.point && this.point <= 10) {
			this.level = 1;
		} else if (11 <= this.point && this.point <= 25) {
			this.level = 2;
		} else if (26 <= this.point && this.point <= 50) {
			this.level = 3;
		} else if (51 <= this.point && this.point <= 150) {
			this.level = 4;
		} else if (151 <= this.point && this.point <= 250) {
			this.level = 5;
		} else if (251 <= this.point && this.point <= 500) {
			this.level = 6;
		} else if (501 <= this.point && this.point <= 1000) {
			this.level = 7;
		} else if (1001 <= this.point && this.point <= 2000) {
			this.level = 8;
		} else if (2001 <= this.point && this.point <= 5000) {
			this.level = 9;
		} else if (5001 <= this.point && this.point <= 10000) {
			this.level = 10;
		} else if (10001 <= this.point && this.point <= 20000) {
			this.level = 11;
		} else if (20001 <= this.point && this.point <= 50000) {
			this.level = 12;
		} else if (50001 <= this.point && this.point <= 100000) {
			this.level = 13;
		} else if (100001 <= this.point && this.point <= 200000) {
			this.level = 14;
		} else if (200001 <= this.point && this.point <= 500000) {
			this.level = 15;
		}
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}


	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public int getEducation() {
		return education;
	}

	public void setEducation(int education) {
		this.education = education;
	}

	public int getBloodType() {
		return bloodType;
	}

	public void setBloodType(int bloodType) {
		this.bloodType = bloodType;
	}

	public int getBodilyType() {
		return bodilyType;
	}

	public void setBodilyType(int bodilyType) {
		this.bodilyType = bodilyType;
	}

	public int getSmoke() {
		return smoke;
	}

	public void setSmoke(int smoke) {
		this.smoke = smoke;
	}

	public int getDrink() {
		return drink;
	}

	public void setDrink(int drink) {
		this.drink = drink;
	}

	public int getReligion() {
		return religion;
	}

	public void setReligion(int religion) {
		this.religion = religion;
	}

	public int getOccupation() {
		return occupation;
	}

	public void setOccupation(int occupation) {
		this.occupation = occupation;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public int getIsSingurate() {
		return isSingurate;
	}

	public void setIsSingurate(int isSingurate) {
		this.isSingurate = isSingurate;
	}

	public int getPositiveRating() {
		return positiveRating;
	}

	public void setPositiveRating(int positiveRating) {
		this.positiveRating = positiveRating;
	}

	public int getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(int isOnline) {
		this.isOnline = isOnline;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getBlogName() {
		return blogName;
	}

	public void setBlogName(String blogName) {
		this.blogName = blogName;
	}

	public String getBlogIntroduction() {
		return blogIntroduction;
	}

	public void setBlogIntroduction(String blogIntroduction) {
		this.blogIntroduction = blogIntroduction;
	}

	public String getPaypassword() {
		return paypassword;
	}

	public void setPaypassword(String paypassword) {
		this.paypassword = paypassword;
	}

	public int getIsTelphoneVerify() {
		return isTelphoneVerify;
	}

	public void setIsTelphoneVerify(int isTelphoneVerify) {
		this.isTelphoneVerify = isTelphoneVerify;
	}

	public int getIsIdCardVerify() {
		return isIdCardVerify;
	}

	public void setIsIdCardVerify(int isIdCardVerify) {
		this.isIdCardVerify = isIdCardVerify;
	}

	public long getBimodifyCount() {
		return bimodifyCount;
	}

	public void setBimodifyCount(long bimodifyCount) {
		this.bimodifyCount = bimodifyCount;
	}

	public long getDimodifyCount() {
		return dimodifyCount;
	}

	public void setDimodifyCount(long dimodifyCount) {
		this.dimodifyCount = dimodifyCount;
	}

	public String getKeyRegister() {
		return keyRegister;
	}

	public void setKeyRegister(String keyRegister) {
		this.keyRegister = keyRegister;
	}

	public String getKeyInviteEmail() {
		return keyInviteEmail;
	}

	public void setKeyInviteEmail(String keyInviteEmail) {
		this.keyInviteEmail = keyInviteEmail;
	}

	public String getKeyPassword() {
		return keyPassword;
	}

	public void setKeyPassword(String keyPassword) {
		this.keyPassword = keyPassword;
	}

	public String getInviteuser() {
		return inviteuser;
	}

	public void setInviteuser(String inviteuser) {
		this.inviteuser = inviteuser;
	}

}
