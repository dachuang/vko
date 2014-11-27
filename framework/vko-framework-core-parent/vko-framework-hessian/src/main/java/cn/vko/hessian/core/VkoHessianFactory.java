package cn.vko.hessian.core;

import cn.vko.hessian.util.AESEncrypt;

import com.caucho.hessian.io.HessianFactory;

public class VkoHessianFactory extends HessianFactory {

	private HessianEncrypt encrypt;

	public HessianEncrypt getEncrypt() {
		if (encrypt == null) {
			encrypt = new AESEncrypt();
		}
		return encrypt;
	}

	public void setEncrypt(HessianEncrypt encrypt) {
		this.encrypt = encrypt;
	}

}
