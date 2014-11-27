package cn.vko.cache.dao.mybatis.ext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.BatchExecutor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;

import cn.vko.cache.annotation.Batch;
import cn.vko.cache.dao.mybatis.interceptor.MyBatisInvocation;

/**
 * 批处理扩展功能
 * <p>
 * @author   bo
 * @Date	 2014-9-13
 */
public class BatchExecutorExt extends BatchExecutor {
	private boolean closed;
	private static final Log log = LogFactory.getLog(BatchExecutorExt.class);

	public BatchExecutorExt(Configuration configuration, Transaction transaction) {
		super(configuration, transaction);
	}

	@Override
	public boolean isClosed() {
		return closed;
	}

	@Override
	public CacheKey createCacheKey(MappedStatement ms, Object parameterObject, RowBounds rowBounds, BoundSql boundSql) {
		if (closed) {
			throw new ExecutorException("Executor was closed.");
		}
		return super.createCacheKey(ms, parameterObject, rowBounds, boundSql);

	}

	@Override
	public void deferLoad(MappedStatement ms, MetaObject resultObject, String property, CacheKey key,
			Class<?> targetType) {
		if (closed) {
			throw new ExecutorException("Executor was closed.");
		}
		super.deferLoad(ms, resultObject, property, key, targetType);

	}

	@Override
	public List<BatchResult> flushStatements(boolean isRollBack) throws SQLException {
		if (closed) {
			throw new ExecutorException("Executor was closed.");
		}
		return super.flushStatements(isRollBack);
	}

	@Override
	public int update(MappedStatement ms, Object parameter) throws SQLException {
		if (closed) {
			throw new ExecutorException("Executor was closed.");
		}
		return super.update(ms, parameter);

	}

	@Override
	public <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler,
			CacheKey key, BoundSql boundSql) throws SQLException {
		if (closed) {
			throw new ExecutorException("Executor was closed.");
		}
		return super.query(ms, parameter, rowBounds, resultHandler, key, boundSql);
	}

	@Override
	public void close(boolean forceRollback) {
		try {
			rollback(forceRollback);
		} catch (SQLException e) {
			// Ignore.  There's nothing that can be done at this point.
			log.debug("Unexpected exception on closing transaction.  Cause: " + e);
		} finally {
			transaction = null;
			deferredLoads = null;
			localCache = null;
			localOutputParameterCache = null;
			closed = true;
		}

	}

	@Override
	public void rollback(boolean required) throws SQLException {
		if (!closed) {
			clearLocalCache();
			flushStatements(true);
		}
	}

	@Override
	public void commit(boolean required) throws SQLException {
		if (closed) {
			throw new ExecutorException("Cannot commit, transaction is already closed");
		}
		clearLocalCache();
		flushStatements();
	}

	public static Executor wrap(Executor exe, Configuration configuration) {
		Executor newExe = new BatchExecutorExt(configuration, exe.getTransaction());
		Class<?> type = exe.getClass();
		Class<?>[] interfaces = new Class<?>[] { Executor.class };
		Executor target = (Executor) Proxy.newProxyInstance(type.getClassLoader(), interfaces, new ProxyHandler(newExe,
				exe));
		return target;
	}

	public static class ProxyHandler implements InvocationHandler {
		Executor batchExe;
		Executor exe;

		public ProxyHandler(Executor newExe, Executor exe) {
			this.batchExe = newExe;
			this.exe = exe;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			String name = method.getName();
			if ("commit".equals(name) || "rollback".equals(name) || "close".equals(name)) {
				//先执行批处理的，没有真正关闭
				method.invoke(batchExe, args);
				method.invoke(exe, args);
				return null;
			}
			MyBatisInvocation invocation = MapperProxyExt.getMyBatisInvocation();
			if (invocation != null) {
				Batch batch = invocation.getMethod().getAnnotation(Batch.class);
				if (batch != null) {
					return method.invoke(batchExe, args);
				}
			}
			return method.invoke(exe, args);

		}
	}
}
