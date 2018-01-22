package com.springSecurityDB.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.springSecurityDB.domain.UserAccountVO;
import com.springSecurityDB.mapper.UserAccountMapper;

@Controller
public class LoginController {

	final static Logger logger = LoggerFactory.getLogger(LoginController.class);

	private PasswordEncoder passwordEncoder;

	@Autowired
	UserAccountMapper userAccountMapper;

	@Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

	@GetMapping("/login")
	public String login() {
		return "/login/login";	
	}

	@PostMapping(value="/accoutRegister")
	public String register(UserAccountVO userAccount){
		try {
			userAccount.setEncryptedPassword(passwordEncoder.encode(userAccount.getPassword()));

			userAccountMapper.accoutRegister(userAccount);
		} catch (Exception e) {
			logger.info("register error", e);
		}

		return "redirect:/";
	}

	@GetMapping("/access-denied")
    public String error() {
        return "/login/error";
    }
}

