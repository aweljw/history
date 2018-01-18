package com.s3FileWork.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.s3FileWork.domain.FileVO;
import com.s3FileWork.mapper.FileMapper;
import com.s3FileWork.service.S3Service;

@Service
public class S3ServiceImpl implements S3Service {

	private Logger logger = LoggerFactory.getLogger(S3ServiceImpl.class);

	@Autowired
	private AmazonS3 s3client;

	@Autowired
	FileMapper fileMapper;

	@Value("${jsa.s3.bucket}")
	private String bucketName;

	@Override
	public void downloadFile(HttpServletRequest request, HttpServletResponse response, String fileUuid) throws Exception{
		try {

			String keyName = fileMapper.getFileName(fileUuid);
			S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, keyName));
            S3ObjectInputStream s3is = s3object.getObjectContent();            
            Long s3size = s3object.getObjectMetadata().getContentLength();
            OutputStream os = null;
            boolean skip = false;
            String client = "";

            client = request.getHeader("User-Agent");
            response.reset();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Description", "JSP Generated Data");

            if (!skip) {
            	// IE
                if (client.indexOf("MSIE") != -1) {
                    response.setHeader("Content-Disposition", "attachment; filename=\""
                            + java.net.URLEncoder.encode(keyName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
                   // IE 11 이상.
                } else if (client.indexOf("Trident") != -1) {
                    response.setHeader("Content-Disposition", "attachment; filename=\""
                            + java.net.URLEncoder.encode(keyName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
                } else {
                   // 한글 파일명 처리
                    response.setHeader("Content-Disposition",
                            "attachment; filename=\"" + new String(keyName.getBytes("UTF-8"), "ISO8859_1") + "\"");
                    response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
                }
                response.setHeader("Content-Length", "" + s3size);
                os = response.getOutputStream();

                byte[] read_buf = new byte[1024];
                int read_len = 0;

                while ((read_len = s3is.read(read_buf)) > 0) {
                	os.write(read_buf, 0, read_len);
    			}
            } else {
                response.setContentType("text/html;charset=UTF-8");
                System.out.println("<script language='javascript'>alert('파일을 찾을 수 없습니다');history.back();</script>");
            }
            s3is.close();
            os.close();
        } catch (AmazonServiceException ase) {
        	logger.info("Caught an AmazonServiceException from GET requests, rejected reasons:");
			logger.info("Error Message:    " + ase.getMessage());
			logger.info("HTTP Status Code: " + ase.getStatusCode());
			logger.info("AWS Error Code:   " + ase.getErrorCode());
			logger.info("Error Type:       " + ase.getErrorType());
			logger.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
        	logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
        }catch (IOException ioe) {
        	logger.info("IOE Error Message: " + ioe.getMessage());
		}catch (Exception e) {
			logger.info("error", e);
		}
	}

	@Override
	public void uploadFile(MultipartFile[] files) throws Exception{
		for(MultipartFile file : files) {
			File convertFile = null;

			if(!file.isEmpty() && file.getSize() < 10000000){
				try {
					convertFile = multipartToFile(file);
					uploadS3(file.getOriginalFilename(), convertFile);
					convertFile.delete();

					FileVO fileVo = new FileVO();
					fileVo.setFileName(file.getOriginalFilename());
					fileVo.setFileUuid(UUID.randomUUID().toString());
					fileMapper.insertFile(fileVo);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void deleteFile(String fileUuid) throws Exception {
		try {
			String keyName = fileMapper.getFileName(fileUuid);
			s3client.deleteObject(new DeleteObjectRequest(bucketName, keyName));
			int result = fileMapper.deleteFile(fileUuid);
			if(result < 1) {
				throw new Exception("file delete error. uuid =" + fileUuid);
			}
        } catch (AmazonServiceException ase) {
            logger.info("Caught an AmazonServiceException.");
            logger.info("Error Message:    " + ase.getMessage());
            logger.info("HTTP Status Code: " + ase.getStatusCode());
            logger.info("AWS Error Code:   " + ase.getErrorCode());
            logger.info("Error Type:       " + ase.getErrorType());
            logger.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
        	logger.info("Caught an AmazonClientException.");
        	logger.info("Error Message: " + ace.getMessage());
        } catch (Exception e) {
        	logger.info("error", e);
		}
	}

	public void uploadS3(String keyName, File uploadfile) throws Exception{
		try {
	        s3client.putObject(new PutObjectRequest(bucketName, keyName, uploadfile));
	        logger.info("===================== Upload File - Done! =====================");
		} catch (AmazonServiceException ase) {
			logger.info("Caught an AmazonServiceException from PUT requests, rejected reasons:");
			logger.info("Error Message:    " + ase.getMessage());
			logger.info("HTTP Status Code: " + ase.getStatusCode());
			logger.info("AWS Error Code:   " + ase.getErrorCode());
			logger.info("Error Type:       " + ase.getErrorType());
			logger.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
        }
	}

	public File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException{
		File convFile = new File(multipart.getOriginalFilename());
	    convFile.createNewFile(); 
	    FileOutputStream fos = new FileOutputStream(convFile); 
	    fos.write(multipart.getBytes());
	    fos.close();

	    return convFile;
	}

}