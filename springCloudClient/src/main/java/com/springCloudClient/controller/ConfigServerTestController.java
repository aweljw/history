package com.springCloudClient.controller;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.springCloudClient.service.ConfigServerTestDynamicService;
import com.springCloudClient.service.ConfigServerTestStaticService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@RequestMapping(value = "/config", produces = MediaType.TEXT_HTML_VALUE)
public class ConfigServerTestController {

	private final @NotNull ConfigServerTestStaticService configServerTestStaticService;
    private final @NotNull ConfigServerTestDynamicService configServerTestDynamicService;
    
    @Value("${configServerTest.title}")
    private String SUBJECT;
    
    @Value("${configServerTest.content}")
    private String HTMLBODY;

	@GetMapping(value = "/static")
    public String getKeywordFromConfigServerByStatic (Model model) {
        model.addAllAttributes(configServerTestStaticService.getTestKeyword());
        return "configServerTest";
    }

    @GetMapping(value = "/dynamic")
    public String getKeywordFromConfigServerByDynamic (Model model) {
        model.addAllAttributes(configServerTestDynamicService.getTestKeyword());
        return "configServerTest";
    }
    
    @GetMapping(value = "/sendEmail")
    public void sendEmail() {
    	// Replace sender@example.com with your "From" address.
  	  // This address must be verified with Amazon SES.
  	  final String FROM = "aweljw@naver.com";

  	  // Replace recipient@example.com with a "To" address. If your account
  	  // is still in the sandbox, this address must be verified.
  	  final String TO = "aweljw@naver.com";

  	  // The configuration set to use for this email. If you do not want to use a
  	  // configuration set, comment the following variable and the 
  	  // .withConfigurationSetName(CONFIGSET); argument below.
  	  final String CONFIGSET = "ConfigSet";

  	  // The subject line for the email.
  	  //final String SUBJECT = "Amazon SES test (AWS SDK for Java)";
  	  
  	  // The HTML body for the email.
  	  //final String HTMLBODY = "<h1>Amazon SES test (AWS SDK for Java)</h1>"
  	      //+ "<p>This email was sent with <a href='https://aws.amazon.com/ses/'>"
  	      //+ "Amazon SES</a> using the <a href='https://aws.amazon.com/sdk-for-java/'>" 
  	      //+ "AWS SDK for Java</a>";

  	  // The email body for recipients with non-HTML email clients.
  	  final String TEXTBODY = "This email was sent through Amazon SES "
  	      + "using the AWS SDK for Java.";

    	try {
		      ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();

				try {
					credentialsProvider.getCredentials();
			    }catch(Exception e) {
			    	throw new AmazonClientException( "Cannot load the credentials from the credential profiles file. " +
			    										"Please make sure that your credentials file is at the correct " +
			    										"location (~/.aws/credentials), and is in valid format.", e);
			    }

				AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
						.withCredentials(credentialsProvider) 
						.withRegion("us-west-2") 
						.build();

		      SendEmailRequest request = new SendEmailRequest()
		          .withDestination(
		              new Destination().withToAddresses(TO))
		          .withMessage(new Message()
		              .withBody(new Body()
		                  .withHtml(new Content()
		                      .withCharset("UTF-8").withData(HTMLBODY))
		                  .withText(new Content()
		                      .withCharset("UTF-8").withData(TEXTBODY)))
		              .withSubject(new Content()
		                  .withCharset("UTF-8").withData(SUBJECT)))
		          .withSource(FROM);
		          // Comment or remove the next line if you are not using a
		          // configuration set
		      client.sendEmail(request);
		      System.out.println("Email sent!");
		    } catch (Exception ex) {
		      System.out.println("The email was not sent. Error message: " 
		          + ex.getMessage());
		    }
    }
}
