package cn.vko.hessian.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.vko.hessian.object.Service;

public class MetadataProcessor {

	private static Map<String, ServiceMetaData> hessianServiceMetaMap = new HashMap<String, ServiceMetaData>();

	public static void initMetaDataMap() {
		Map<String, Service> serviceMap = ServiceHandler.getServiceMap();
		Set<String> serviceNames = serviceMap.keySet();
		for (String name : serviceNames) {
			Service service = serviceMap.get(name);
			Class<?> clazz = service.getTypeClass();
			ServiceMetaData smd = new ServiceMetaData(clazz,service.isOverload());
			hessianServiceMetaMap.put(name, smd);
		}
	}

	public static ServiceMetaData getServiceMetaData(String sname) {
		return hessianServiceMetaMap.get(sname);
	}
}
