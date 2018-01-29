package com.snsOauthLogin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.snsOauthLogin.domain.SnsUserInfo;
import com.snsOauthLogin.service.SnsService;

@Controller
@RequestMapping(value="/sns")
public class SnsController {

	@Autowired
	SnsService snsService;

	@RequestMapping(value="/snsLink")
	public String snsLink() {
		return "/sns/snsLink";
	}
	
	@RequestMapping(value="/snsLogin")
	public void snsLogin(@RequestParam("divn") String divn, HttpServletRequest request, HttpServletResponse response) {
		try {
			snsService.snsLogin(divn, request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value="/snsLoginSuccess")
	public String snsLoginSuccess(@RequestParam(value="oauth_verifier", required=false) String oauthVerifier
								 	, @RequestParam(value="code", required=false) String code
									, HttpServletRequest request
									, HttpServletResponse response
									, HttpSession session
									, Model model) {
		SnsUserInfo snsUserInfo = new SnsUserInfo();

		try {
			snsUserInfo = snsService.snsLoginSuccess(oauthVerifier, code, request, session);
		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("snsUserInfo", snsUserInfo);

		return "/sns/snsLoginSuccess";
	}

}