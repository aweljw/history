package com.springSecurityDB.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController extends CommonController{

	private static final String MAIN_MAIN_PREPIX_PATH = "/main";

	@GetMapping("/")
	public String main(ModelMap model,
						Authentication authentication) {
		return woongModel(model, authentication,MAIN_MAIN_PREPIX_PATH + "/main");
	}

	@GetMapping("/about")
	public String about(ModelMap model,
						Authentication authentication) {
		return woongModel(model, authentication,"/about/about");
	}
	
	@GetMapping("/user")
	public String user(ModelMap model,
						Authentication authentication) {
		return woongModel(model, authentication,"/user/user");
	}
	
	@GetMapping("/admin")
	public String admin(ModelMap model,
						Authentication authentication) {
		return woongModel(model, authentication,"/admin/admin");
	}
}
