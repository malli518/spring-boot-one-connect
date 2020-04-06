package org.rythmos.oneconnect.json.request;

import org.rythmos.oneconnect.bean.ClientDetailBean;

public class ClientRisksJSONRequest {

	private ClientDetailBean client;

	public ClientDetailBean getClient() {
		return client;
	}

	public void setClient(ClientDetailBean client) {
		this.client = client;
	}

	@Override
	public String toString() {
		return "ClientRisksJSONRequest [client=" + client + "]";
	}

}
