package com.vko.core.common.util.gson;

/*
 * Copyright 2010 Fuchun.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * 包含操作 {@code JSON} 数据的常用方法的工具类。
 * <p />
 * 该工具类使用的 {@code JSON} 转换引擎是 <a href="http://code.google.com/p/google-gson/"
 * mce_href="http://code.google.com/p/google-gson/" target="_blank"> {@code
 * Google Gson}</a>。 下面是工具类的使用案例：
 * 
 * <pre>
 * public class User { 
 *     @SerializedName("pwd")
 *     private String password; 
 *     @Expose
 *     @SerializedName("uname")
 *     private String username; 
 *     @Expose
 *     @Since(1.1)
 *     private String gender; 
 *     @Expose
 *     @Since(1.0)
 *     private String sex; 
 *      
 *     public User() {} 
 *     public User(String username, String password, String gender) { 
 *         // user constructor code... ... ... 
 *     } 
 *      
 *     public String getUsername() 
 *     ... ... ... 
 * }
 * List<User> userList = new LinkedList<User>(); 
 * User jack = new User("Jack", "123456", "Male"); 
 * User marry = new User("Marry", "888888", "Female"); 
 * userList.add(jack); 
 * userList.add(marry); 
 * Type targetType = new TypeToken<List<User>>(){}.getType(); 
 * String sUserList1 = JSONUtils.toJson(userList, targetType); 
 * sUserList1 ----> [{"uname":"jack","gender":"Male","sex":"Male"},{"uname":"marry","gender":"Female","sex":"Female"}] 
 * String sUserList2 = JSONUtils.toJson(userList, targetType, false); 
 * sUserList2 ----> [{"uname":"jack","pwd":"123456","gender":"Male","sex":"Male"},{"uname":"marry","pwd":"888888","gender":"Female","sex":"Female"}] 
 * String sUserList3 = JSONUtils.toJson(userList, targetType, 1.0d, true); 
 * sUserList3 ----> [{"uname":"jack","sex":"Male"},{"uname":"marry","sex":"Female"}]
 * </pre>
 * 
 * @author Fuchun
 * @since ay-commons-lang 1.0
 * @version 1.1.0
 */
public class GsonUtil {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GsonUtil.class);
	/** 空的 {@code JSON} 数据 - <code>"{}"</code>。 */
	public static final String EMPTY_JSON = "{}";
	/** 空的 {@code JSON} 数组(集合)数据 - {@code "[]"}。 */
	public static final String EMPTY_JSON_ARRAY = "[]";
	/** 默认的 {@code JSON} 日期/时间字段的格式化模式。 */
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss SSS";
	/** {@code Google Gson} 的 <code>@Since</code> 注解常用的版本号常量 - {@code 1.0}。 */
	public static final double SINCE_VERSION_10 = 1.0d;
	/** {@code Google Gson} 的 <code>@Since</code> 注解常用的版本号常量 - {@code 1.1}。 */
	public static final double SINCE_VERSION_11 = 1.1d;
	/** {@code Google Gson} 的 <code>@Since</code> 注解常用的版本号常量 - {@code 1.2}。 */
	public static final double SINCE_VERSION_12 = 1.2d;
	/** {@code Google Gson} 的 <code>@Until</code> 注解常用的版本号常量 - {@code 1.0}。 */
	public static final double UNTIL_VERSION_10 = SINCE_VERSION_10;
	/** {@code Google Gson} 的 <code>@Until</code> 注解常用的版本号常量 - {@code 1.1}。 */
	public static final double UNTIL_VERSION_11 = SINCE_VERSION_11;
	/** {@code Google Gson} 的 <code>@Until</code> 注解常用的版本号常量 - {@code 1.2}。 */
	public static final double UNTIL_VERSION_12 = SINCE_VERSION_12;

	/**
	 * <p>
	 * <code>JSONUtils</code> instances should NOT be constructed in standard
	 * programming. Instead, the class should be used as
	 * <code>JSONUtils.fromJson("foo");</code>.
	 * </p>
	 * <p>
	 * This constructor is public to permit tools that require a JavaBean
	 * instance to operate.
	 * </p>
	 */
	public GsonUtil() {
		super();
	}

	/**
	 * 将给定的目标对象根据指定的条件参数转换成 {@code JSON} 格式的字符串。
	 * <p />
	 * <strong>该方法转换发生错误时，不会抛出任何异常。若发生错误时，曾通对象返回 <code>"{}"</code>； 集合或数组对象返回
	 * <code>"[]"</code> </strong>
	 * 
	 * @param target
	 *            目标对象。
	 * @param targetType
	 *            目标对象的类型。
	 * @param isSerializeNulls
	 *            是否序列化 {@code null} 值字段。
	 * @param version
	 *            字段的版本号注解。
	 * @param datePattern
	 *            日期字段的格式化模式。
	 * @param excludesFieldsWithoutExpose
	 *            是否排除未标注 {@literal @Expose} 注解的字段。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 * @since 1.0
	 */
	public static String toJson(Object target, Type targetType,
			boolean isSerializeNulls, Double version, String datePattern,
			boolean excludesFieldsWithoutExpose) {
		if (target == null)
			return EMPTY_JSON;
		GsonBuilder builder = new GsonBuilder();
		if (isSerializeNulls)
			builder.serializeNulls();
		if (version != null)
			builder.setVersion(version.doubleValue());
		if (StringUtils.isBlank(datePattern))
			datePattern = DEFAULT_DATE_PATTERN;
		builder.setDateFormat(datePattern);
		if (excludesFieldsWithoutExpose)
			builder.excludeFieldsWithoutExposeAnnotation();
		return toJson(target, targetType, builder);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 * 
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 * @since 1.0
	 */
	public static String toJson(Object target) {
		return toJson(target, null, false, null, null, true);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * </ul>
	 * 
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param datePattern
	 *            日期字段的格式化模式。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 * @since 1.0
	 */
	public static String toJson(Object target, String datePattern) {
		return toJson(target, null, false, null, datePattern, true);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 * 
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param version
	 *            字段的版本号注解({@literal @Since})。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 * @since 1.0
	 */
	public static String toJson(Object target, Double version) {
		return toJson(target, null, false, version, null, true);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * <ul>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 * 
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param excludesFieldsWithoutExpose
	 *            是否排除未标注 {@literal @Expose} 注解的字段。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 * @since 1.0
	 */
	public static String toJson(Object target,
			boolean excludesFieldsWithoutExpose) {
		return toJson(target, null, false, null, null,
				excludesFieldsWithoutExpose);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * <ul>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 * 
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param version
	 *            字段的版本号注解({@literal @Since})。
	 * @param excludesFieldsWithoutExpose
	 *            是否排除未标注 {@literal @Expose} 注解的字段。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 * @since 1.0
	 */
	public static String toJson(Object target, Double version,
			boolean excludesFieldsWithoutExpose) {
		return toJson(target, null, false, version, null,
				excludesFieldsWithoutExpose);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSSS}；</li>
	 * </ul>
	 * 
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param targetType
	 *            目标对象的类型。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 * @since 1.0
	 */
	public static String toJson(Object target, Type targetType) {
		return toJson(target, targetType, false, null, null, true);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSSS}；</li>
	 * </ul>
	 * 
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param targetType
	 *            目标对象的类型。
	 * @param version
	 *            字段的版本号注解({@literal @Since})。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 * @since 1.0
	 */
	public static String toJson(Object target, Type targetType, Double version) {
		return toJson(target, targetType, false, version, null, true);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
	 * <ul>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 * 
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param targetType
	 *            目标对象的类型。
	 * @param excludesFieldsWithoutExpose
	 *            是否排除未标注 {@literal @Expose} 注解的字段。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 * @since 1.0
	 */
	public static String toJson(Object target, Type targetType,
			boolean excludesFieldsWithoutExpose) {
		return toJson(target, targetType, false, null, null,
				excludesFieldsWithoutExpose);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
	 * <ul>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 * 
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param targetType
	 *            目标对象的类型。
	 * @param version
	 *            字段的版本号注解({@literal @Since})。
	 * @param excludesFieldsWithoutExpose
	 *            是否排除未标注 {@literal @Expose} 注解的字段。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 * @since 1.0
	 */
	public static String toJson(Object target, Type targetType, Double version,
			boolean excludesFieldsWithoutExpose) {
		return toJson(target, targetType, false, version, null,
				excludesFieldsWithoutExpose);
	}

	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。
	 * 
	 * @param <T>
	 *            要转换的目标类型。
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param token
	 *            {@code com.google.gson.reflect.TypeToken} 的类型指示类对象。
	 * @param datePattern
	 *            日期格式模式。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 * @since 1.0
	 */
	public static <T> T fromJson(String json, TypeToken<T> token,
			String datePattern) {
		if (StringUtils.isBlank(json)) {
			return null;
		}
		GsonBuilder builder = new GsonBuilder();
		if (StringUtils.isBlank(datePattern)) {
			datePattern = DEFAULT_DATE_PATTERN;
		}
		Gson gson = builder.create();
		try {
			return (T)gson.fromJson(json, token.getType());
		} catch (Exception ex) {
			LOGGER.error(json + " 无法转换为 " + token.getRawType().getName()
					+ " 对象!", ex);
			return null;
		}
	}

	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。
	 * 
	 * @param <T>
	 *            要转换的目标类型。
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param token
	 *            {@code com.google.gson.reflect.TypeToken} 的类型指示类对象。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 * @since 1.0
	 */
	public static <T> T fromJson(String json, TypeToken<T> token) {
		return fromJson(json, token, null);
	}

	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。<strong>此方法通常用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * 
	 * @param <T>
	 *            要转换的目标类型。
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param clazz
	 *            要转换的目标类。
	 * @param datePattern
	 *            日期格式模式。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 * @since 1.0
	 */
	public static <T> T fromJson(String json, Class<T> clazz, String datePattern) {
		if (StringUtils.isBlank(json)) {
			return null;
		}
		GsonBuilder builder = new GsonBuilder();
		if (StringUtils.isBlank(datePattern)) {
			datePattern = DEFAULT_DATE_PATTERN;
		}
		Gson gson = builder.create();
		try {
			return gson.fromJson(json, clazz);
		} catch (Exception ex) {
			LOGGER.error(json + " 无法转换为 " + clazz.getName() + " 对象!", ex);
			return null;
		}
	}

	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。<strong>此方法通常用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * 
	 * @param <T>
	 *            要转换的目标类型。
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param clazz
	 *            要转换的目标类。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 * @since 1.0
	 */
	public static <T> T fromJson(String json, Class<T> clazz) {
		return fromJson(json, clazz, null);
	}

	/**
	 * 将给定的目标对象根据{@code GsonBuilder} 所指定的条件参数转换成 {@code JSON} 格式的字符串。
	 * <p />
	 * 该方法转换发生错误时，不会抛出任何异常。若发生错误时，{@code JavaBean} 对象返回 <code>"{}"</code>；
	 * 集合或数组对象返回 <code>"[]"</code>。 其本基本类型，返回相应的基本值。
	 * 
	 * @param target
	 *            目标对象。
	 * @param targetType
	 *            目标对象的类型。
	 * @param builder
	 *            可定制的{@code Gson} 构建器。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 * @since 1.1
	 */
	public static String toJson(Object target, Type targetType,
			GsonBuilder builder) {
		if (target == null)
			return EMPTY_JSON;
		Gson gson = null;
		if (builder == null) {
			gson = new Gson();
		} else {
			gson = builder.create();
		}
		String result = EMPTY_JSON;
		try {
			if (targetType == null) {
				result = gson.toJson(target);
			} else {
				result = gson.toJson(target, targetType);
			}
		} catch (Exception ex) {
			LOGGER.warn("目标对象 " + target.getClass().getName()
					+ " 转换 JSON 字符串时，发生异常！", ex);
			if (target instanceof Collection<?>
					|| target instanceof Iterator<?>
					|| target instanceof Enumeration<?>
					|| target.getClass().isArray()) {
				result = EMPTY_JSON_ARRAY;
			}
		}
		return result;
	}
	public static void main(String[] args) {
		String str = "{\"id\": \"77555781-1bf7-4d58-b64e-3efd8a7ec6db\",\"wapstatus\": \"0\",\"name\": \"微电影《美丽大学》热映 舒淇演绎单身圣女受追捧\",\"duration\": \"77\",\"url\": \"http://v.ifeng.com/ent/mingxing/201211/77555781-1bf7-4d58-b64e-3efd8a7ec6db.shtml\",\"img\": \"http://img.ifeng.com/itvimg//2012/11/30/16e9b832-162a-409c-9835-a9657ab26150.jpg\"}";
		//EntityVideo entityVideo = GsonUtil.fromJson(str, EntityVideo.class);
		EntityVideo entity = GsonUtil.fromJson(str, EntityVideo.class);
		System.out.println(entity.toString());
	}
	
	/**
	 * 凤凰视频实体
	 * 
	 * @author kingzhu
	 *
	 */
	public class EntityVideo {
		private String id;
		private String wapstatus;
		private String name;
		private String duration;
		private String url;
		private String img;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getWapstatus() {
			return wapstatus;
		}
		public void setWapstatus(String wapstatus) {
			this.wapstatus = wapstatus;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDuration() {
			return duration;
		}
		public void setDuration(String duration) {
			this.duration = duration;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getImg() {
			return img;
		}
		public void setImg(String img) {
			this.img = img;
		} 
		
		public String toString() {
			return ToStringBuilder.reflectionToString(this);
		}
	}
}