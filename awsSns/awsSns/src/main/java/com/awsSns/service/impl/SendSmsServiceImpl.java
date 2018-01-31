package com.awsSns.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.awsSns.service.SendSmsService;

@Service
public class SendSmsServiceImpl implements SendSmsService {

	@Autowired
	private AmazonSNS snsClient;

	@Override
	public void sendSms() {
        sendSMSMessage(smsInfo(), null);
	}

	@Override
	public void sendSmsSpecial() {
		//<set SMS attributes>
        Map<String, MessageAttributeValue> smsAttributes =
                new HashMap<String, MessageAttributeValue>();
        smsAttributes.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue()
                .withStringValue("mySenderID") //The sender ID shown on the device.
                .withDataType("String"));
        smsAttributes.put("AWS.SNS.SMS.MaxPrice", new MessageAttributeValue()
                .withStringValue("0.50") //Sets the max price to 0.50 USD.
                .withDataType("Number"));
        smsAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue()
                .withStringValue("Transactional") //Sets the type to promotional.
                .withDataType("String"));

		sendSMSMessage(smsInfo(), smsAttributes);
	}

	public Map<String, String> smsInfo() {
		Map<String, String> smsInfo = new HashMap<String, String>();

		smsInfo.put("message", "문자 가즈아!!!!");
		smsInfo.put("phoneNumber", "+821049068062");
		//smsInfo.put("message", "My SMS message");
		//smsInfo.put("phoneNumber", "+821055556666");

		return smsInfo;
	}

	public void sendSMSMessage(Map<String, String> smsInfo, Map<String, MessageAttributeValue> smsAttributes) {

		PublishResult result = new PublishResult(); 

		if(smsAttributes == null) {
			result = snsClient.publish(new PublishRequest()
                    .withMessage(smsInfo.get("message"))                        
                    .withPhoneNumber(smsInfo.get("phoneNumber")));
		}else{
			result = snsClient.publish(new PublishRequest()
                    .withMessage(smsInfo.get("message"))                        
                    .withPhoneNumber(smsInfo.get("phoneNumber"))
                    .withMessageAttributes(smsAttributes));
		}

        System.out.println(result); // Prints the message ID.
	}

}
