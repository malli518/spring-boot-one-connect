package org.rythmos.oneconnect.json.response;

public class CountryJSONResponse {
	private String sortName;

	private String name;

	private String phoneCode;

	private Long id;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ClassPojo [sortName = " + sortName + ", name = " + name + ", phoneCode = " + phoneCode + ", id = " + id
				+ "]";
	}
}