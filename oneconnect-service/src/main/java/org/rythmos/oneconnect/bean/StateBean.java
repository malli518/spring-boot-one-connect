package org.rythmos.oneconnect.bean;

import org.springframework.stereotype.Component;

@Component
public class StateBean {

	private long id;

	private String name;

	private CountryBean country;

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

	public CountryBean getCountry() {
		return country;
	}

	public void setCountry(CountryBean country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "StateBean [id=" + id + ", name=" + name + ", country=" + country + "]";
	}

}
