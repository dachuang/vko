package cn.vko.stat.mapper.annotation;

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
public @interface Statistics {

	//键值的前缀,和key组合成唯一的键,用来获取或保存统计值,常量cn.vko.projectname.tablename.fieldname
	String[] group();

	//组合的键值,ognl表达式
	String[] key();

	//计数,可以为负数,表示减掉
	double[] result() default 1;

	boolean[] incremental() default true;
}
