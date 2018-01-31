package com.awsSesUsingSDK.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;

@Configuration
public class AwsConfig {

	@Bean
	public AmazonSimpleEmailService sesClient() {
		AmazonSimpleEmailService sesClient = 
		          AmazonSimpleEmailServiceClientBuilder.standard()
		          // Replace US_WEST_2 with the AWS Region you're using for
		          // Amazon SES.
		            .withRegion(Regions.US_WEST_2)
		            .build();
		return sesClient;
	}

}
