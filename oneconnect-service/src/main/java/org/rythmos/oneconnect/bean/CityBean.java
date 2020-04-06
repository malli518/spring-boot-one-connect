package org.rythmos.oneconnect.bean;

import org.springframework.stereotype.Component;

@Component
public class CityBean {

	private long id;

	private String name;

	private StateBean state;

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

	public StateBean getState() {
		return state;
	}

	public void setState(StateBean state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "CityBean [id=" + id + ", name=" + name + ", state=" + state + "]";
	}

}
