package org.rythmos.oneconnect.bean;

import org.springframework.stereotype.Component;

@Component
public class HealthIndicatorTypeBean {

	private long type;
	private String value;

	public long getType() {
		return type;
	}

	public void setType(long type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "HealthIndicatorTypeBean [type=" + type + ", value=" + value + "]";
	}

}
