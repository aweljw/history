package com.snsOauthLogin.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.snsOauthLogin.domain.SnsUserInfo;

public interface SnsService {

    /**
     * sns인증 요청
     * @param divn sns구분(twitter, twitter).
     * @param request HttpServletRequest.
     * @param response HttpServletResponse.
     */	
	public void snsLogin(String divn, HttpServletRequest request, HttpServletResponse response);

    /**
     * sns인증 요청성공
     * @param oauthVerifier sns에서 전달받은 회원토큰정보(twitter) 
     * @param code sns에서 전달받은 회원토큰정보(facebook)
     * @param request HttpServletRequest.
     * @param session HttpSession.
     * @return sns회원정보.
     */
	public SnsUserInfo snsLoginSuccess(String oauthVerifier, String code, HttpServletRequest request, HttpSession session);

}