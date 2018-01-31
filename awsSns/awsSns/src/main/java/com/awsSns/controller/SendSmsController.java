package com.awsSns.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.awsSns.service.SendSmsService;

@Controller
public class SendSmsController {

	@Autowired
	private SendSmsService sendSmsService;

	@GetMapping("/sendSms")
	public void sendSms() {
		sendSmsService.sendSms();
	}

	@GetMapping("/sendSmsSpecial")
	public void sendSmsSpecial() {
		sendSmsService.sendSmsSpecial();
	}

}
