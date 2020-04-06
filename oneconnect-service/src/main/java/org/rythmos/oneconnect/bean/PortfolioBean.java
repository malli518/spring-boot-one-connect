package org.rythmos.oneconnect.bean;

import org.springframework.stereotype.Component;

@Component
public class PortfolioBean {

	private long id;

	private String name;

	private ClientDetailBean clientDetailBean;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ClientDetailBean getClientDetail() {
		return clientDetailBean;
	}

	public void setClientDetail(ClientDetailBean clientDetailBean) {
		this.clientDetailBean = clientDetailBean;
	}

	@Override
	public String toString() {
		return "PortfolioBean [id=" + id + ", name=" + name + ", clientDetailBean=" + clientDetailBean + "]";
	}

}
