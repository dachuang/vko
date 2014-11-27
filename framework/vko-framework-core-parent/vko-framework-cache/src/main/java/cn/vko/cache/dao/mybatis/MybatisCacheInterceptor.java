package cn.vko.cache.dao.mybatis;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.vko.cache.util.ExpireCache;
import cn.vko.cache.util.Invoker;
import cn.vko.cache.util.Md5KeyGenerator;
import cn.vko.cache.util.TableNamesUtil;

import com.vko.core.common.util.ApplicationUtil;

/**
 * 配置针对单个不经常变动的表进行单表查询长时间缓存
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-8
 */
@Intercepts({
		@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class }) })
public class MybatisCacheInterceptor implements Interceptor {

	static final Logger logger = LoggerFactory.getLogger(MybatisCacheInterceptor.class);

	private Properties properties;
	//需要缓存的表名称,不分大小写
	private static HashSet<String> CACHED_TABLE_NAME = new HashSet<String>();

	@Override
	public Object intercept(final Invocation invocation) throws Throwable {

		String methodName = invocation.getMethod().getName();
		if ("query".equals(methodName)) {
			MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
			Executor exe = (Executor) invocation.getTarget();
			Object parameter = invocation.getArgs()[1];
			RowBounds rowBounds = (RowBounds) invocation.getArgs()[2];
			BoundSql boundSql = mappedStatement.getBoundSql(parameter);
			CacheKey cacheKey = exe.createCacheKey(mappedStatement, parameter, rowBounds, boundSql);
			String sql = boundSql.getSql();
			sql = sql.toUpperCase();
			if (!sql.contains("FROM")) {
				return invocation.proceed();
			}
			List<String> tableList = TableNamesUtil.getTableNames(sql);
			if (tableList.size() == 1 && CACHED_TABLE_NAME.contains(tableList.get(0))) {
				//进行缓存操作,先获取,没有,然后放入
				if (logger.isDebugEnabled()) {
					logger.debug(tableList.get(0) + "进行一次缓存:" + sql.replaceAll("[\\s]+", " "));
				}
				Invoker invoker = new Invoker() {
					@Override
					public Object invoke() {
						try {
							return invocation.proceed();
						} catch (Exception e) {
							logger.error("", e);
						}
						return null;
					}

				};
				String key = Md5KeyGenerator.MD5(cacheKey.toString());
				ExpireCache expireCache = ApplicationUtil.getBean(ExpireCache.class);
				Object obj = expireCache.get(key, invoker, 7 * 24 * 3600);
				return obj;
			} else {
				return invocation.proceed();
			}

		} else if ("update".equals(methodName)) {
			Object obj = invocation.proceed();
			return obj;
		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		this.properties = properties;
		//通过配置获取需要进行缓存的表名称
		String cachedTable = this.properties.getProperty("Cached_Table");
		if (cachedTable != null) {
			String[] mapper = cachedTable.split(",");
			for (int i = 0; i < mapper.length; i++) {
				String tableName = mapper[i].trim();
				if (tableName.length() > 0) {
					CACHED_TABLE_NAME.add(mapper[i].trim().toUpperCase());
				}
			}
		}
	}
}