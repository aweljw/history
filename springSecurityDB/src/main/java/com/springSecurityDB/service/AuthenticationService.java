package com.springSecurityDB.service;

import org.springframework.security.core.Authentication;

import com.springSecurityDB.domain.UserAccountAuthVO;

public interface AuthenticationService {

	/**
     * 인증이 이루어진 객체인지 확인.
     * @param authentication 사용자 인증 객체.
     * @return 사용자 인증 여부.
     */
    boolean isAuthenticated(Authentication authentication);

	/**
     * 인증된 사용자의 정보 조회.
     * @param authentication 사용자 인증 객체.
     * @return 인증된 사용자의 정보.
     */
    UserAccountAuthVO getAuthenticationUserDetail(Authentication authentication);
}
