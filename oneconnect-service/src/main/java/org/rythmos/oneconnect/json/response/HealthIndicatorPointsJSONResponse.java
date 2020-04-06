package org.rythmos.oneconnect.json.response;

public class HealthIndicatorPointsJSONResponse {
	private String deliveryValue;

	private String communicationAndReporting;

	private String healthOfCodebase;

	private String speed;

	private String currentProcess;

	public String getDeliveryValue() {
		return deliveryValue;
	}

	public void setDeliveryValue(String deliveryValue) {
		this.deliveryValue = deliveryValue;
	}

	public String getCommunicationAndReporting() {
		return communicationAndReporting;
	}

	public void setCommunicationAndReporting(String communicationAndReporting) {
		this.communicationAndReporting = communicationAndReporting;
	}

	public String getHealthOfCodebase() {
		return healthOfCodebase;
	}

	public void setHealthOfCodebase(String healthOfCodebase) {
		this.healthOfCodebase = healthOfCodebase;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getCurrentProcess() {
		return currentProcess;
	}

	public void setCurrentProcess(String currentProcess) {
		this.currentProcess = currentProcess;
	}

	@Override
	public String toString() {
		return "HealthIndicatorPointsJSONResponse [deliveryValue=" + deliveryValue + ", communicationAndReporting="
				+ communicationAndReporting + ", healthOfCodebase=" + healthOfCodebase + ", speed=" + speed
				+ ", currentProcess=" + currentProcess + "]";
	}

}
