package com.springSecurityDB.controller;

import org.springframework.security.core.Authentication;
import org.springframework.ui.ModelMap;

import com.springSecurityDB.domain.UserAccountAuthVO;


public class CommonController {
	
	public String woongModel(ModelMap model, Authentication authentication, String page_path) {
		if(authentication != null) {
			model.addAttribute("authentication",(UserAccountAuthVO) authentication.getPrincipal());
		}
		
		return page_path;
	}

}
