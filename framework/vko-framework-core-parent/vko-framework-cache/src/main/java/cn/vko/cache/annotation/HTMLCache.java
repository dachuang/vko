package cn.vko.cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface HTMLCache {
	// 加上次标签代表改controller生成的html进行缓存
	int expire() default 200;// 静态页面过期时间默认200s,-1代表不过期,0从配置文件中读取默认秒数
}
