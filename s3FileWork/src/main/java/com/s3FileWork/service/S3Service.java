package com.s3FileWork.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {

	public void downloadFile(HttpServletRequest request, HttpServletResponse response, String fileUuid) throws Exception;

	public void uploadFile(MultipartFile[] files) throws Exception;

	public void deleteFile(String fileUuid) throws Exception;
}