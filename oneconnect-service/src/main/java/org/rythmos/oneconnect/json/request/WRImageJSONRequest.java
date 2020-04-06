package org.rythmos.oneconnect.json.request;

public class WRImageJSONRequest {
	private Long id;
	private String fileName;
	private String fileType;
	private String data;

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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "WRImageJSONRequest [id=" + id + ", fileName=" + fileName + ", fileType=" + fileType + ", data=" + data
				+ "]";
	}

}