package com.springSecurityDB.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.springSecurityDB.domain.UserAccountAuthVO;
import com.springSecurityDB.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl  implements AuthenticationService {

	@Override
	public boolean isAuthenticated(Authentication authentication) {
		return authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() != null;
	}

	@Override
	public UserAccountAuthVO getAuthenticationUserDetail(Authentication authentication) {
		if (!isAuthenticated(authentication)) {
			return null;
		}
		return (UserAccountAuthVO) authentication.getPrincipal();
	}

}
