package org.rythmos.oneconnect.json.response;

public class LocationJSONResponse {

	private CountryJSONResponse country;

	private CityJSONResponse city;

	private StateJSONResponse state;

	public CountryJSONResponse getCountry() {
		return country;
	}

	public void setCountry(CountryJSONResponse country) {
		this.country = country;
	}

	public CityJSONResponse getCity() {
		return city;
	}

	public void setCity(CityJSONResponse city) {
		this.city = city;
	}

	public StateJSONResponse getState() {
		return state;
	}

	public void setState(StateJSONResponse state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "ClassPojo [country = " + country + ", city = " + city + ", state = " + state + "]";
	}

}
