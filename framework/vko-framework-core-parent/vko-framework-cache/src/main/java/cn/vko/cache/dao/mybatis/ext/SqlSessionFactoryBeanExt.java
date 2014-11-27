package cn.vko.cache.dao.mybatis.ext;

import java.io.IOException;
import java.lang.reflect.Field;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author songrubo
 * @version 2013年11月30日 下午1:11:48 扩展功能,mapper文件自动加载,空resultMap自动配置,mapper拦截
 */
public class SqlSessionFactoryBeanExt extends SqlSessionFactoryBean implements ApplicationContextAware {

	private ApplicationContext applicationContext;
	private Class<?> superClass;
	private SqlSessionFactory sqlSessionFactory;

	public SqlSessionFactoryBeanExt() {
		superClass = SqlSessionFactoryBean.class;

	}

	public void setValue(String name, Object value) {
		try {
			Field field = superClass.getDeclaredField(name);
			field.setAccessible(true);
			field.set(this, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected SqlSessionFactory buildSqlSessionFactory() throws IOException {
		SqlSessionFactory factory = super.buildSqlSessionFactory();

		try {
			Configuration config = factory.getConfiguration();
			Class<?> classConfig = Configuration.class;
			// 拦截
			Field field = classConfig.getDeclaredField("mapperRegistry");
			field.setAccessible(true);
			field.set(config, new MapperRegistryExt(config, applicationContext));
			//
		} catch (Exception e) {
			e.printStackTrace();
		}
		return factory;
	}

	@Override
	public SqlSessionFactory getObject() throws Exception {
		if (this.sqlSessionFactory == null) {
			sqlSessionFactory = buildSqlSessionFactory();
			setValue("sqlSessionFactory", sqlSessionFactory);
		}
		return this.sqlSessionFactory;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// setValue("sqlSessionFactory", buildSqlSessionFactory());
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
