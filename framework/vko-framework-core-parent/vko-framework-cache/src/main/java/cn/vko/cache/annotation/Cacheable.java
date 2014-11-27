package cn.vko.cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 过期缓存
 * <p>
 * @author   bo
 * @Date	 2014-8-9
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Cacheable {
	//expire过期缓存,entity实体缓存基于主键,table对于某个表的基于sql的缓存(加在Mapper类上,对于单表查询,子查询和连接查询不管)
	String type() default "expire";

	//只对过期缓存有用
	int expire() default 5 * 60;

	//只对实体缓存有用
	String id() default "";

	//是否清除缓存
	boolean evict() default false;

	//数据是否压缩
	boolean compress() default false;

	//是否走缓存
	boolean cache() default true;
}
