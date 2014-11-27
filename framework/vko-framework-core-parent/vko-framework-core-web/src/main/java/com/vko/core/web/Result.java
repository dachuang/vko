package com.vko.core.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Result {
	private boolean success;

	private String resultCode;

	private List<Object> defaultModel;

	private Map<String, Object> values;

	private String view;

	public Result(String view) {
		this(view, false);
	}

	public Result(String view, boolean success) {
		this.success = success;
		this.view = view;
		this.values = new HashMap<String, Object>();
	}

	public Result addValue(String key, Object value) {
		values.put(key, value);
		return this;
	}

	public Map<String, Object> getValues() {
		return values;
	}

	public String getView() {
		return view;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public List<Object> getDefaultModel() {
		return defaultModel;
	}

	public void setDefaultModel(List<Object> defaultModel) {
		this.defaultModel = defaultModel;
	}

	public void setView(String view) {
		this.view = view;
	}

}
