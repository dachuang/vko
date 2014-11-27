package cn.vko.hessian.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.URL;

import cn.vko.hessian.util.AESEncrypt;

import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.io.HessianRemoteObject;

public class VkoHessianProxyFactory extends HessianProxyFactory {

	private boolean _isHessian2Reply = true;

	private boolean _isHessian2Request = false;

	// 服务器返回结果加密
	private boolean isEncrypt = false;

	private HessianEncrypt encrypt;

	@Override
	public void setHessian2Reply(boolean isHessian2) {
		_isHessian2Reply = isHessian2;
		super.setHessian2Reply(isHessian2);
	}

	public boolean isHessian2Reply() {
		return _isHessian2Reply;
	}

	public boolean isHessian2Request() {
		return _isHessian2Request;
	}

	public boolean isEncrypt() {
		return isEncrypt;
	}

	public void setEncrypt(boolean isEncrypt) {
		this.isEncrypt = isEncrypt;
	}

	@Override
	public void setHessian2Request(boolean isHessian2) {
		_isHessian2Request = isHessian2;
		super.setHessian2Request(isHessian2);
	}

	@Override
	public Object create(Class<?> api, URL url, ClassLoader loader) {
		if (api == null)
			throw new NullPointerException("api must not be null for HessianProxyFactory.create()");
		InvocationHandler handler = null;
		handler = new VkoHessianProxy(url, this, api);
		return Proxy.newProxyInstance(loader, new Class[] { api, HessianRemoteObject.class }, handler);
	}

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
