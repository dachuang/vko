package cn.vko.hessian.object;

import java.io.Serializable;

import org.springframework.context.ApplicationContext;

import com.esotericsoftware.reflectasm.MethodAccess;
import cn.vko.hessian.core.ServiceHandler;

public class ServiceProvider implements Serializable {

	private static final long serialVersionUID = 2323169647020692097L;

	private String version;

	private Class<?> processorClass;

	private MethodAccess methodAccess;

	private final boolean isSington;

	private final boolean isBean;

	private Object processor;

	public boolean isBean() {
		return isBean;
	}

	public boolean isSington() {
		return isSington;
	}

	public ServiceProvider(String version, Class<?> processorClass, boolean isSington, boolean isBean) {
		this.version = version;
		this.processorClass = processorClass;
		this.methodAccess = MethodAccess.get(processorClass);
		this.isBean = isBean;
		this.isSington = isSington;

		this.processor = getProcessor();

	}

	public String getVersion() {
		return version;
	}

	public Class<?> getProcessorClass() {
		return processorClass;
	}

	public MethodAccess getMethodAccess() {
		return methodAccess;
	}

	public Object getProcessor() {
		if (isSington && processor != null) {
			return processor;
		}
		Object localObj = null;
		try {
			if (isBean) {
				// 从spring中获取
				ApplicationContext context = ServiceHandler.getApplicationContext();
				if (context != null) {
					localObj = context.getBean(processorClass);
				}
			} else {
				localObj = processorClass.newInstance();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		if (isSington) {
			processor = localObj;
		}

		return localObj;
	}
}