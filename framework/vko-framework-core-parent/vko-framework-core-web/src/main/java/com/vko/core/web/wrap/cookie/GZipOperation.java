package com.vko.core.web.wrap.cookie;

import com.vko.core.common.util.GZipCompress;

public class GZipOperation implements ObjectSerialize {

	private ObjectSerialize ser = null;

	public GZipOperation(ObjectSerialize ser) {
		this.ser = ser;
	}

	@Override
	public Object deSerialize(Object obj) {
		byte[] byteArray = GZipCompress.doUncompress((byte[]) obj);
		return ser.deSerialize(byteArray);
	}

	@Override
	public Object serialize(Object obj) {
		return GZipCompress.doCompress((byte[]) ser.serialize(obj));
	}
}
