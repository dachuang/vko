/**
 * ReadWriteSqlSessionTemplate.java
 * cn.vko.fz.dao.support.ext
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.cache.dao.mybatis.ext;

import static java.lang.reflect.Proxy.*;
import static org.apache.ibatis.reflection.ExceptionUtil.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.mybatis.spring.MyBatisExceptionTranslator;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionUtils;
import org.mybatis.spring.transaction.SpringManagedTransaction;
import org.springframework.dao.support.PersistenceExceptionTranslator;

import cn.vko.cache.annotation.Read;
import cn.vko.cache.annotation.Write;
import cn.vko.cache.dao.mybatis.interceptor.MyBatisInvocation;
import cn.vko.cache.dao.rw.IDataSourceService;

/**
 * 读写分离实现
 * <p>
 * @author   宋汝波
 * @Date	 2014-5-17 	 
 */
public class ReadWriteSqlSessionTemplate extends SqlSessionTemplate {

	protected final SqlSession sqlSessionProxy;

	protected IDataSourceService dataSourceService;

	public ReadWriteSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		this(sqlSessionFactory, sqlSessionFactory.getConfiguration().getDefaultExecutorType());
	}

	public ReadWriteSqlSessionTemplate(SqlSessionFactory sqlSessionFactory, ExecutorType executorType) {
		this(sqlSessionFactory, executorType, new MyBatisExceptionTranslator(sqlSessionFactory.getConfiguration()
				.getEnvironment().getDataSource(), true));
	}

	@SuppressWarnings("synthetic-access")
	public ReadWriteSqlSessionTemplate(SqlSessionFactory sqlSessionFactory, ExecutorType executorType,
			PersistenceExceptionTranslator exceptionTranslator) {
		super(sqlSessionFactory, executorType, exceptionTranslator);
		this.sqlSessionProxy = (SqlSession) newProxyInstance(SqlSessionFactory.class.getClassLoader(),
				new Class[] { SqlSession.class }, new SqlSessionInterceptor());
	}

	public IDataSourceService getDataSourceService() {
		return dataSourceService;
	}

	public void setDataSourceService(IDataSourceService dataSourceService) {
		this.dataSourceService = dataSourceService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Read
	public <T> T selectOne(String statement) {
		return this.sqlSessionProxy.<T> selectOne(statement);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Read
	public <T> T selectOne(String statement, Object parameter) {
		return this.sqlSessionProxy.<T> selectOne(statement, parameter);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Read
	public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
		return this.sqlSessionProxy.<K, V> selectMap(statement, mapKey);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Read
	public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
		return this.sqlSessionProxy.<K, V> selectMap(statement, parameter, mapKey);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Read
	public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds) {
		return this.sqlSessionProxy.<K, V> selectMap(statement, parameter, mapKey, rowBounds);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Read
	public <E> List<E> selectList(String statement) {
		return this.sqlSessionProxy.<E> selectList(statement);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Read
	public <E> List<E> selectList(String statement, Object parameter) {
		return this.sqlSessionProxy.<E> selectList(statement, parameter);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Read
	public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
		return this.sqlSessionProxy.<E> selectList(statement, parameter, rowBounds);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Read
	public void select(String statement, ResultHandler handler) {
		this.sqlSessionProxy.select(statement, handler);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Read
	public void select(String statement, Object parameter, ResultHandler handler) {
		this.sqlSessionProxy.select(statement, parameter, handler);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Read
	public void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler) {
		this.sqlSessionProxy.select(statement, parameter, rowBounds, handler);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Write
	public int insert(String statement) {
		return this.sqlSessionProxy.insert(statement);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Write
	public int insert(String statement, Object parameter) {
		return this.sqlSessionProxy.insert(statement, parameter);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Write
	public int update(String statement) {
		return this.sqlSessionProxy.update(statement);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Write
	public int update(String statement, Object parameter) {
		return this.sqlSessionProxy.update(statement, parameter);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Write
	public int delete(String statement) {
		return this.sqlSessionProxy.delete(statement);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Write
	public int delete(String statement, Object parameter) {
		return this.sqlSessionProxy.delete(statement, parameter);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearCache() {
		this.sqlSessionProxy.clearCache();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Connection getConnection() {
		return this.sqlSessionProxy.getConnection();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @since 1.0.2
	 *
	 */
	@Override
	public List<BatchResult> flushStatements() {
		return this.sqlSessionProxy.flushStatements();
	}

	protected SqlSession getSqlSession(String methodName) {
		if (dataSourceService == null) {
			return getDefaultSqlSession();
		}
		List<DataSource> readDS = dataSourceService.getReadDataSources();
		List<DataSource> writeDS = dataSourceService.getWriteDataSources();
		if (methodName.startsWith("select")) {
			MyBatisInvocation inv = MapperProxyExt.getMyBatisInvocation();
			Write write = inv.getMethod().getAnnotation(Write.class);
			if (write != null) {
				//读取的时候要求从写库读取
				if (!writeDS.isEmpty()) {
					return getSqlSession(writeDS.get(0));
				}
			}
			if (!readDS.isEmpty()) {
				return getSqlSession(readDS.get(0));
			}
		} else {
			if (!writeDS.isEmpty()) {
				return getSqlSession(writeDS.get(0));
			}
		}
		return getDefaultSqlSession();
	}

	private SqlSession getDefaultSqlSession() {
		SqlSession sqlSession = SqlSessionUtils.getSqlSession(this.getSqlSessionFactory(), this.getExecutorType(),
				this.getPersistenceExceptionTranslator());
		return sqlSession;
	}

	protected SqlSession getSqlSession(DataSource ds) {
		SpringManagedTransaction tran = new SpringManagedTransaction(ds);
		Executor exe = this.getConfiguration().newExecutor(tran, this.getExecutorType());
		DefaultSqlSession session = new DefaultSqlSession(this.getConfiguration(), exe);
		return session;
	}

	/**
	 * Proxy needed to route MyBatis method calls to the proper SqlSession got
	 * from Spring's Transaction Manager
	 * It also unwraps exceptions thrown by {@code Method#invoke(Object, Object...)} to
	 * pass a {@code PersistenceException} to the {@code PersistenceExceptionTranslator}.
	 */
	private class SqlSessionInterceptor implements InvocationHandler {
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			SqlSession sqlSession = null;
			try {
				sqlSession = getSqlSession(method.getName());
				Object result = method.invoke(sqlSession, args);

				if (!SqlSessionUtils.isSqlSessionTransactional(sqlSession, getSqlSessionFactory())) {
					// force commit even on non-dirty sessions because some databases require
					// a commit/rollback before calling close()
					sqlSession.commit(true);
				}
				return result;
			} catch (Throwable t) {
				Throwable unwrapped = unwrapThrowable(t);
				if (getPersistenceExceptionTranslator() != null && unwrapped instanceof PersistenceException) {
					Throwable translated = getPersistenceExceptionTranslator().translateExceptionIfPossible(
							(PersistenceException) unwrapped);
					if (translated != null) {
						unwrapped = translated;
					}
				}
				throw unwrapped;
			} finally {
				SqlSessionUtils.closeSqlSession(sqlSession, getSqlSessionFactory());
			}
		}
	}

}
