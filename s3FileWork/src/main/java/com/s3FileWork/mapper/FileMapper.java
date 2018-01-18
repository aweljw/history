package com.s3FileWork.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.s3FileWork.domain.FileVO;

@Mapper
public interface FileMapper {

	List<FileVO> getFileList();

	String getFileName(String uuid);

	int insertFile(FileVO fileVo);
	
	int deleteFile(String uuid);

}
