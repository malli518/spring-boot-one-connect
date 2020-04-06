package org.rythmos.oneconnect.json.request;

public class LocationJSONRequest {

	private CountryJSONRequest country;

	private CityJSONRequest city;

	private StateJSONRequest state;

	public CountryJSONRequest getCountry() {
		return country;
	}

	public void setCountry(CountryJSONRequest country) {
		this.country = country;
	}

	public CityJSONRequest getCity() {
		return city;
	}

	public void setCity(CityJSONRequest city) {
		this.city = city;
	}

	public StateJSONRequest getState() {
		return state;
	}

	public void setState(StateJSONRequest state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "LocationJSONRequest [country = " + country + ", city = " + city + ", state = " + state + "]";
	}
}