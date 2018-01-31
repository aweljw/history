package com.awsSesUsingSDK.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.awsSesUsingSDK.service.SendMailRawService;
import com.awsSesUsingSDK.service.SendMailService;

@Controller
public class SendMailController {

	@Autowired
	private SendMailService sendMailService;

	@Autowired
	private SendMailRawService sendMailRawService;

	@GetMapping("/sendMail")
	public void sendMail() {
		sendMailService.sendMail();
	}

	@GetMapping("/sendMailRaw")
	public void sendMailRaw() {
		try {
			sendMailRawService.sendMailRaw();	
		} catch (Exception ex) {
			System.out.println("The email was not sent. Error message: " 
			          + ex.getMessage());
		}
	}

}
