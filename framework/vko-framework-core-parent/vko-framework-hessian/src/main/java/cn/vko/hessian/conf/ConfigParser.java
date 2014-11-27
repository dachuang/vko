package cn.vko.hessian.conf;

import java.util.List;

import cn.vko.hessian.object.Application;
import cn.vko.hessian.object.Service;
import cn.vko.hessian.object.ServiceVersion;

public interface ConfigParser {
	List<Service> parseService();

	List<Application> parseApplication();

	List<ServiceVersion> parseSecurity();
}