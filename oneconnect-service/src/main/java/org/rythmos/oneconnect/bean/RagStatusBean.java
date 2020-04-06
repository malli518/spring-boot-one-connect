package org.rythmos.oneconnect.bean;

import org.springframework.stereotype.Component;

@Component
public class RagStatusBean {

	private long id;
	private String status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "RagStatusBean [id=" + id + ", status=" + status + "]";
	}

}
