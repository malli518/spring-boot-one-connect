package org.rythmos.oneconnect.json.response;

public class PortfolioJSONResponse {

	private Long id;
	private String name;
	private ClientDetailsJSONResponse client;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ClientDetailsJSONResponse getClient() {
		return client;
	}

	public void setClient(ClientDetailsJSONResponse client) {
		this.client = client;
	}

}
