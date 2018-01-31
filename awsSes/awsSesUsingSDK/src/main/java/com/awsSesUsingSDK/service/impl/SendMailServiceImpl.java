package com.awsSesUsingSDK.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.awsSesUsingSDK.service.SendMailService;

@Service
public class SendMailServiceImpl implements SendMailService{

	@Autowired
	AmazonSimpleEmailService sesClient;

	@Override
	public void sendMail() {
		Map<String, String> mailInfo = mailInfo();

		try {
			SendEmailRequest request = new SendEmailRequest()
			          .withDestination(
			              new Destination().withToAddresses(mailInfo.get("to")))
			          .withMessage(new Message()
			              .withBody(new Body()
			                  .withHtml(new Content()
			                      .withCharset("UTF-8").withData(mailInfo.get("htmlBody")))
			                  .withText(new Content()
			                      .withCharset("UTF-8").withData(mailInfo.get("textBody"))))
			              .withSubject(new Content()
			                  .withCharset("UTF-8").withData(mailInfo.get("subject"))))
			          .withSource(mailInfo.get("from"));
			          // Comment or remove the next line if you are not using a
			          // configuration set
			          //.withConfigurationSetName(CONFIGSET);
			sesClient.sendEmail(request);
			System.out.println("Email sent!");
		} catch (Exception ex) {
			System.out.println("The email was not sent. Error message: " 
			          + ex.getMessage());
		}
	}

	public Map<String, String> mailInfo() {
		Map<String, String> mailInfo = new HashMap<String, String>();

		String from = "aweljw@naver.com";
		String to = "aweljw@naver.com";
		String subject = "Amazon SES test (AWS SDK for Java)";
		String htmlBody = "<h1>Amazon SES test (AWS SDK for Java)</h1>"
			      			+ "<p>This email was sent with <a href='https://aws.amazon.com/ses/'>"
		      				+ "Amazon SES</a> using the <a href='https://aws.amazon.com/sdk-for-java/'>" 
		      				+ "AWS SDK for Java</a>";
		String textBody = "This email was sent through Amazon SES "
			      			+ "using the AWS SDK for Java.";

		mailInfo.put("to", to);
		mailInfo.put("from", from);
		mailInfo.put("subject", subject);
		mailInfo.put("htmlBody", htmlBody);
		mailInfo.put("textBody", textBody);

		return mailInfo;
	}

}
