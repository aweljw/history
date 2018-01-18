package com.s3FileWork.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.s3FileWork.domain.FileVO;
import com.s3FileWork.mapper.FileMapper;
import com.s3FileWork.service.S3Service;

@Controller
@RequestMapping(value="/file")
public class FileController {

	final static Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	S3Service s3Service;

	@Autowired
	FileMapper fileMapper;

	@GetMapping(value="/fileList")
	public String fileList(Model model) {
		List<FileVO> fileList = fileMapper.getFileList();
		model.addAttribute("fileList", fileList);

		return "file/fileList";
	}
	
	@PostMapping(value="/upload")
	public String upload(@RequestParam(value = "filedata", required = false) MultipartFile[] files
					,HttpServletRequest request) {
		try {
			s3Service.uploadFile(files);
		} catch (Exception e) {
			logger.info("upload error", e);
		}

		return "redirect:/file/fileList";
	}

	@GetMapping(value="/download")
	public void download(@RequestParam(value = "fileUuid", required = true) String fileUuid
						, HttpServletRequest request, HttpServletResponse response) {
		try {
			s3Service.downloadFile(request, response, fileUuid);
		} catch (Exception e) {
			logger.info("download error", e);
		}
	}

	@GetMapping(value="/deleteFile")
	public String deleteFile(@RequestParam(value = "fileUuid", required = true) String fileUuid) {
		try {
			s3Service.deleteFile(fileUuid);
		} catch (Exception e) {
			logger.info("deleteFile error", e);
		}

		return "redirect:/file/fileList";
	}
}
