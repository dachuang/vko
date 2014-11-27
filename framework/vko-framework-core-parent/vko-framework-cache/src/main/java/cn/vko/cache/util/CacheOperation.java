package cn.vko.cache.util;

import org.springframework.util.Assert;

/**
 * Base class implementing {@link CacheOperation}.
 * 
 * @author Costin Leau
 */
public class CacheOperation {

	public static final int SELECT_BY_ID = 1;

	public static final int UPDATE_BY_ID = 2;

	public static final int DELETE_BY_ID = 3;

	public static final int DELETE_ALL = 4;

	public static final int DELETE = 5;

	public static final int ADD = 6;

	private String condition = "";

	private String key = "";

	private int expire = 5 * 60;

	private String id = null;

	private String table_key = "";

	private int operation = 0;

	private String value = "";

	private boolean compress = false;

	public int getExpire() {
		return expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getOperation() {
		return operation;
	}

	public void setOperation(int operation) {
		this.operation = operation;
	}

	public String getTable_key() {
		return table_key;
	}

	public void setTable_key(String table_key) {
		this.table_key = table_key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCondition() {
		return condition;
	}

	public boolean isCompress() {
		return compress;
	}

	public void setCompress(boolean compress) {
		this.compress = compress;
	}

	public String getKey() {
		return key;
	}

	public void setCondition(String condition) {
		Assert.notNull(condition);
		this.condition = condition;
	}

	public void setKey(String key) {
		Assert.notNull(key);
		this.key = key;
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof CacheOperation && toString().equals(other.toString()));
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public String toString() {
		return getOperationDescription().toString();
	}

	protected StringBuilder getOperationDescription() {
		StringBuilder result = new StringBuilder();
		result.append(getClass().getSimpleName());
		result.append("[");
		result.append("] key=");
		result.append(this.key);
		result.append(" | condition='");
		result.append(this.condition);
		result.append("' | id='");
		result.append("table_key:");
		result.append(this.table_key);
		result.append("value:");
		result.append(value);
		result.append("'");
		result.append("operation:");
		result.append(operation);
		return result;
	}
}
