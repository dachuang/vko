package cn.vko.cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标示语句是更新语句
 * <p>
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Write {
	String vaule() default "write";
}
