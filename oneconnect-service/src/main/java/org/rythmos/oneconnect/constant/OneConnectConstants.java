package org.rythmos.oneconnect.constant;

public class OneConnectConstants {

	public enum assessmentType {
		PARTNERSHIP, TEAMWORK, ENTHUSIASM, CANDOR, FOCUS, INNOVATION
	}

	public enum clientStatus {
		ACTIVE, INACTIVE
	}

	public enum status {
		OPEN, CLOSED
	}

	public enum transactionType {
		SUMMARY, ACCOMPLISHMENT, RISKANDMITIGATION, RAGSTATUS, MAJORUPDATE, COMMENT
	}

	public static final String DEFAULT_DATE_FORMAT = "MM/dd/yyyy";
	public static final String MONTH_YEAR = "MMMMM yyyy";
	public static final String DEFAULT_TIME_ZONE = "IST";
	public static final String NUMBER_ZERO = "0";

	public static final String HI_VALUE_DELIVARY_VALUE = "1";
	public static final String HI_VALUE_CODE_HEALTH = "2";
	public static final String HI_VALUE_SPEED = "3";
	public static final String HI_VALUE_CURRENT_PROCESS = "4";
	public static final String HI_VALUE_COMM_N_REPORT = "5";

	// E-Mail
	public static final String MAIL_NAME = "Oneconnect";
	public static final String MAIL_EMAIL = "oneconnect@rythmos.com";
	public static final String MAIL_PASSWORD = "VFR$5tgb";
	public static final String MAIL_HOST = "m.outlook.com";
	public static final String MAIL_PORT = "587";

	public enum employProjectStatus {
		ACTIVE, INACTIVE
	}

	public enum employStatus {
		ACTIVE, INACTIVE
	}

	public static final String EXECUTIVE_SUMMARY_REPORT = "Executive Summary Report";
	public static final String WEEKLY_SUMMARY_REPORT = "Weekly Summary Report";
	public static final String CLIENT_HEALTH_REPORT = "Client Health Report";
	public static final String AGILE_MATURITY_ASSESSMENT = "Agile Maturity Assessment";
	public static final String RESOURCE = "Resource";
	public static final String SAVE = "save";
	public static final String UPDATE = "update";
	public static final String APPROVE = "approve";
	public static final String DELETE = "delete";

}
