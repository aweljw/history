package com.awsSns.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.GetSMSAttributesRequest;
import com.amazonaws.services.sns.model.SetSMSAttributesRequest;


@Configuration
public class AwsConfig {

	@Bean
	public AmazonSNS snsClient(){
		AmazonSNS snsClient = AmazonSNSClientBuilder.standard()
								.withRegion(Regions.AP_NORTHEAST_1)
								.build();

		SetSMSAttributesRequest setRequest = new SetSMSAttributesRequest()
				.addAttributesEntry("DefaultSenderID", "mySenderID")
				.addAttributesEntry("DefaultSMSType", "Promotional")
				.addAttributesEntry("MonthlySpendLimit", "1");
				//.addAttributesEntry("UsageReportS3Bucket", "emo-sns-test")
				//.addAttributesEntry("DeliveryStatusIAMRole", "arn:aws:iam::123456789012:role/mySnsRole")
				//.addAttributesEntry("DeliveryStatusSuccessSamplingRate", "10")
		snsClient.setSMSAttributes(setRequest);

		Map<String, String> myAttributes = snsClient.getSMSAttributes(new GetSMSAttributesRequest()).getAttributes();
		System.out.println("My SMS attributes:");
		for (String key : myAttributes.keySet()) {
			System.out.println(key + " = " + myAttributes.get(key));
		}

		return snsClient;
	}
}
