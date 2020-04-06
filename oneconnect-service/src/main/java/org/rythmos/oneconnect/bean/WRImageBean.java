package org.rythmos.oneconnect.bean;

import java.util.Arrays;

public class WRImageBean {

	private Long id;

	private String fileName;

	private String fileType;

	private byte[] data;

	private WRBean weeklyReport;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public WRBean getWeeklyReport() {
		return weeklyReport;
	}

	public void setWeeklyReport(WRBean weeklyReport) {
		this.weeklyReport = weeklyReport;
	}

	@Override
	public String toString() {
		return "WRImageBean [id=" + id + ", fileName=" + fileName + ", fileType=" + fileType + ", data="
				+ Arrays.toString(data) + ", weeklyReport=" + weeklyReport + "]";
	}

}
