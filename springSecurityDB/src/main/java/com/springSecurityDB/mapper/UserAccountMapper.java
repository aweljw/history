package com.springSecurityDB.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.springSecurityDB.domain.UserAccountVO;

@Mapper
public interface UserAccountMapper {

	UserAccountVO findByUserid(String userid);
	
	void accoutRegister(UserAccountVO userAccount);
}