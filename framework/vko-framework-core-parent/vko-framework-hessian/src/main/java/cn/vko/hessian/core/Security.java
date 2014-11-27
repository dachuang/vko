package cn.vko.hessian.core;

import java.util.HashMap;
import java.util.Map;

import cn.vko.hessian.object.Application;
import cn.vko.hessian.util.StringUtil;

public class Security {
	
	private static final Map<String, Application> applicationMap = new HashMap<String, Application> ();
	
	public static void addToApplicationMap(Application app){
		applicationMap.put(app.getUser(), app);
	}
	
	/**
	 * check permission
	 * @param user
	 * @param password
	 */
	public static boolean checkPermission(String user,String password){
		if(StringUtil.isEmpty(user) || StringUtil.isEmpty(password)){
			throw new IllegalArgumentException("user or password is null,please check.");
		}
		if(applicationMap.containsKey(user) && applicationMap.get(user).getPassword().equals(password)){
			return true;
		}else{
			return false;
		}
	}
}
