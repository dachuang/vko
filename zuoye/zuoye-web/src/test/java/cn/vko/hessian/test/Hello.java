package cn.vko.hessian.test;

public interface Hello {

	//public String hello();
	//
	public String hello(String name);

	//public byte[] hello(byte[] gg);

	//
	//public String hello(String name1, String name2);

	public User getUser(int id);

	public String getAppSecret(String key);
}
