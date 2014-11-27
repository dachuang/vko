package cn.vko.cache.dao.transaction;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class MultipleCauseException extends Throwable {
	private List<Throwable> causes = new ArrayList<Throwable>();

	public MultipleCauseException() {
	}

	public MultipleCauseException(List<Throwable> causes) {
		if (!(causes == null || causes.isEmpty()))
			this.causes.addAll(causes);
	}

	public void add(Throwable cause) {
		this.causes.add(cause);
	}

	public List<Throwable> getCauses() {
		return new ArrayList<Throwable>(this.causes);
	}

}
