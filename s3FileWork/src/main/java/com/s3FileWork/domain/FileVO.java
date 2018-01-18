package com.s3FileWork.domain;

public class FileVO {

	private String fileSeq;
	private String notiSeq;
	private String fileUuid;
	private String fileName;
	private String fileRegDate;
	private String fileDelYn;
	private String category;

	public String getFileSeq() {
		return fileSeq;
	}
	public void setFileSeq(String fileSeq) {
		this.fileSeq = fileSeq;
	}
	public String getNotiSeq() {
		return notiSeq;
	}
	public void setNotiSeq(String notiSeq) {
		this.notiSeq = notiSeq;
	}
	public String getFileUuid() {
		return fileUuid;
	}
	public void setFileUuid(String fileUuid) {
		this.fileUuid = fileUuid;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileRegDate() {
		return fileRegDate;
	}
	public void setFileRegDate(String fileRegDate) {
		this.fileRegDate = fileRegDate;
	}
	public String getFileDelYn() {
		return fileDelYn;
	}
	public void setFileDelYn(String fileDelYn) {
		this.fileDelYn = fileDelYn;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

}
