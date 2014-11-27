package com.vko.core.web.wrap.cookie;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class URLOperation implements ObjectSerialize{
	private ObjectSerialize ser = null;

	public URLOperation(ObjectSerialize ser) {
		this.ser = ser;
	}

	public Object deSerialize(Object obj) {
		String code = null;
		try {
			code = URLDecoder.decode((String) obj, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		obj = ser.deSerialize(code);
		return obj;
	}

	public Object serialize(Object obj) {
		obj = ser.serialize(obj);
		String code = null;
		try {
			code = URLEncoder.encode((String) obj, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code;
	}
}
