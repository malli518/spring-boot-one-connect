package org.rythmos.oneconnect.json.request;

public class CountryJSONRequest {

	private String sortName;

	private String name;

	private String phoneCode;

	private String id;

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "CountryJSONRequest [sortName = " + sortName + ", name = " + name + ", phoneCode = " + phoneCode
				+ ", id = " + id + "]";
	}
}
