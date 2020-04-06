package org.rythmos.oneconnect.bean;

import org.springframework.stereotype.Component;

@Component
public class LocationBean {

	private CountryBean country;

	private StateBean state;

	private CityBean city;

	public CountryBean getCountry() {
		return country;
	}

	public void setCountry(CountryBean country) {
		this.country = country;
	}

	public StateBean getState() {
		return state;
	}

	public void setState(StateBean state) {
		this.state = state;
	}

	public CityBean getCity() {
		return city;
	}

	public void setCity(CityBean city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "LocationBean [country=" + country + ", state=" + state + ", city=" + city + "]";
	}

}
