package cn.vko.hessian.test;

public class HelloImpl implements Hello {

	//@Override
	public String hello() {
		//System.out.println("revision 2");
		return "revision 2";
	}

	@Override
	public String hello(String name) {

		System.out.println("revision 2：" + name);
		return "revision 2";
	}

	//@Override
	public String hello(String name1, String name2) {

		System.out.println("revision 2");
		return "revision 2";
	}

	@Override
	public User getUser(int id) {

		System.out.println("revision 2");
		return null;
	}

	@Override
	public String getAppSecret(String key) {

		System.out.println("revision 2");
		return "revision 2";
	}

	///	@Override
	public byte[] hello(byte[] gg) {

		return gg;

	}

}
