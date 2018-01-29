package com.awsSesUsingSDK;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.awsSesUsingSDK.sendMail.SendMail;
import com.awsSesUsingSDK.sendMail.SendMailRaw;

@SpringBootApplication
public class AwsSesUsingSdkApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwsSesUsingSdkApplication.class, args);

		SendMail sendMail = new SendMail();
		SendMailRaw sendMailRaw = new SendMailRaw();

		try {
			sendMail.sendMail();
			sendMailRaw.sendMail();
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
