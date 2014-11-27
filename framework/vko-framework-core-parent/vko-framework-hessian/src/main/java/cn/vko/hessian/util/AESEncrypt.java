/**
 * AESEncrypt.java
 * cn.vko.hessian.util
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package cn.vko.hessian.util;

import cn.vko.hessian.core.HessianEncrypt;

import com.vko.core.common.util.AESUtils;

/**
 * 默认加密方式
 * <p>
 * @author   宋汝波
 * @Date	 2014-7-7 	 
 */
public class AESEncrypt extends AESUtils implements HessianEncrypt {

	@Override
	public byte[] encrypt(byte[] data) {

		return encryptData(data);

	}

	@Override
	public byte[] decrypt(byte[] data) {

		return decryptData(data);

	}

}
