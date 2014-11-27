package cn.vko.hessian.core;

public interface HessianEncrypt {

	public byte[] encrypt(byte[] data);

	public byte[] decrypt(byte[] data);
}
