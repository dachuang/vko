package com.vko.core.entity;

/**
 * 全局常量定义类
 * 
 * @author darkwing
 *
 */
public class Global {
	
	public static final String LOGIN_SESSION_USER = "sessionUser";
	
	
	public static final String DATETIME_FORMAT_SHORT = "yyyy-MM-dd";
	
	public static final String DATETIME_FORMAT_SHORT_LINE = "yyyy/MM/dd";
	
	public static final String DATETIME_FORMAT_SHORT_BY_DIAN = "yyyy.MM.dd";

	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";
	
	public static final String DATETIME_FORMAT_LINE = "yyyy/MM/dd HH:mm";

	public static final String DATETIME_FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
	
	public static final String DATETIME_FORMAT_LONG_LINE = "yyyy/MM/dd HH:mm:ss";

	public static final String DATETIME_FORMAT_ANOTHER = "MM/dd HH:mm";
	
	public static final String UPLOAD_IMAGE_NOT_ALLOWED_BLANK = "上传的文件不可以为空";
	
	public static long UPLOAD_MAX_FILE_SIZE = 524288; // 设置允许上传照片的最大文件大小0.5MB
	
	public static final String UP_UPLOAD_MAX_FILE_SIZE = "上传的文件超过最大文件0.5MB的限制";
	
	public static final String PHOTO_ALLOWED_TYPE = "jpg,gif,png"; // 允许上传的照片类型
	
	public static final String NOT_ALLOWED_UPLOAD_IMAGE_TYPE = "不允许上传的图片类型";
	
	public static long UPLOAD_MAX_VIDEO_SIZE = 52428800; // 设置允许上传照片的最大文件大小0.5MB
	
	public static final String UP_UPLOAD_MAX_VIDEO_SIZE = "上传的视频超过最大文件50MB的限制";
	
	public static final String VIDEO_ALLOWED_TYPE = "jmpg,wmv,3gp,avi,mov,mp4,asf,asx,flv,wmv9,rm,rmvb"; // 允许上传的视频类型
	
	public static final String NOT_ALLOWED_UPLOAD_VIDEO_TYPE = "不允许上传的视频类型";
	
	public static final int NORMAL_STATUS = 1;  //正常
	
	public static final int DISABLE_STATUS = 3; //注销
	
	public static final String LOG_SUCCESS = "1";  //正确操作，在日志中记录成功
	
	public static final String LOG_FAIL = "0";  //失败操作，在日志中记录失败
	
	public static final int VIDEO_TYPE_STAGE = 3; //阶段类型
	
	public static final int VIDEO_TYPE_GRADE = 4; //年级
	
	public static final int VIDEO_TYPE_SUBJECT = 5; //科目
	
	//用户角色权限
	public static final String ROLE_ADMINERATOR = "系统管理员";
	
	public static final String ROLE_SCHOOL_ADMINISTRATOR = "校园管理员";
	
	public static final String ROLE_AREA_ADMINISTRATOR = "区域管理员";
	
	public static final String ROLE_PRIVILLEGE_ADMIN = "ROLE_ADMIN";
	
    public static final int ADMINERATOR = 0xffffff; //系统管理员
    
    public static final int ADMIN = 3; //管理员
    
    public static final int SCHOOL_ADMINISTRATOR = 4; //校园管理员
    
    public static final int AREA_ADMINISTRATOR = 5; //区域管理员
    
    public static String VERIFY_KEY = "verify_code";
    
    public static String ADMIN_INIT_PASSWORD = "123456";
    
  //exception
	public static final String ACCESS_DENIED_PRIVILIEGE = "您没有权限执行此操作";
	
	public static final String DATABASE_EXCEPTION = "数据库异常,请联系管理员";
	
	public static final String EXCEPTION_FRONT_VIEW = "showFrontException";
	
	public static final String EXCEPTION_PROFILE_CODE = "profile"; //个人中心访问异常
	
	public static final String EXCEPTION_PROFILE_MESSAGE = "请登录系统平台后再进入个人中心"; 
	
	public static final String EXCEPTION_SERVICE_CODE = "service"; //个人中心访问异常
	
	public static final String EXCEPTION_SERVICE_MESSAGE = "请登录系统平台后再进入客服中心"; 
	
	public static final String EXCEPTION_USER_ASSIGN_ROLE_MESSAGE = "请至少选择一个角色";
	
	public static final String EXCEPTION_ROLE_ASSIGN_RESOURCE_MESSAGE = "请至少选择一个资源";
	
	// ==================字符常量=====================================
	public static final String DELETED_SUCCESS = "删除成功";
	
	public static final String DISABLE_SUCCESS = "注销成功";
	
	public static final String ENABLE_SUCCESS = "启用成功";
	
	public static final String VIDEO_CHECK_SUCCESS = "审核成功";
	
	public static final String ADMINISTRATOR_CREATE_ADMIN_SUCCESSED ="创建管理员成功";
	
	public static final String ADMINISTRATOR_MODIFY_ADMIN_SUCCESSED ="修改管理员成功";
	
	public static final String ROLE_CREATE_ADMIN_SUCCESSED ="创建角色成功";
	
	public static final String ROLE_MODIFY_ADMIN_SUCCESSED ="修改角色成功";
	
	public static final String COMPANY_CREATE_ADMIN_SUCCESSED ="创建单位成功";
	
	public static final String COMPANY_MODIFY_ADMIN_SUCCESSED ="修改单位成功";
	
	public static final String SCHOOL_CREATE_ADMIN_SUCCESSED ="创建学校成功";
	
	public static final String SCHOOL_MODIFY_ADMIN_SUCCESSED ="修改学校成功";
	
	public static final String RESOURCE_CREATE_ADMIN_SUCCESSED ="创建资源成功";
	
	public static final String RESOURCE_MODIFY_ADMIN_SUCCESSED ="修改资源成功";
	
	public static final String VIDEO_CREATE_SUCCESSED ="视频添加成功";
	
	public static final String VIDEO_CREATE_EXISTS ="视频添加失败";
	
	public static final String VIDEO_MODIFY_ADMIN_SUCCESSED ="视频修改成功";
	
	public static final String CHANNEL_CREATE_ADMIN_SUCCESSED ="创建频道成功";
	
	public static final String CHANNEL_MODIFY_ADMIN_SUCCESSED ="修改频道成功";

	public static final String CHANNEL_NAME_EXISTS ="操作失败，同级的频道名称已存在或未修改";
	
	public static final String USER_ASSIGN_ROLE_ADMIN_SUCCESSED = "用户分配角色成功";
	
	public static final String ROLE_ASSIGN_RESOURCE_ADMIN_SUCCESSED = "角色分配资源成功";

	public static final String USER_MODIFY_ADMIN_SUCCESSED = "用户修改成功";
	
	public static final String VIDEO_RECOMMEND_SUCCESS = "视频推荐成功";
	
	public static final String VIDEO_RECOMMEND_EXISTS="视频已经推荐";
	
	public static final String VIDEO_MODIFY_ADMIN_EXISTS="视频已经审核,您没有权限执行此操作";
	
	//add by chenzb 2010-01-06 
	public static final String IVT_PWD = "1234567890";
	
	public static final String FILE_UPLOAD_SUCCESS = "文件上传成功";
	
	public static final String REGEX_TELEPHONE = "^\\d{3,4}-\\d{7,8}$";
	
	public static final String REGEX_MOBILEPHONE = "^1(\\d){10}$";
	
	public static final String REGEX_QQ = "^[1-9][0-9]{4,}$";
	
	public static final String REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
}
