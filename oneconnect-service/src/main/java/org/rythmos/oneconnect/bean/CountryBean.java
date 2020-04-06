package org.rythmos.oneconnect.bean;

import org.springframework.stereotype.Component;

@Component
public class CountryBean {

	private long id;

	private String sortName;

	private String name;

	private String phoneCode;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	@Override
	public String toString() {
		return "CountryBean [id=" + id + ", sortName=" + sortName + ", name=" + name + ", phoneCode=" + phoneCode + "]";
	}

}
