package com.springCloudClient.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@Service
@RefreshScope
public class ConfigServerTestDynamicService {

	@Value("${configServerTest.title}")
	private String title;
	
	@Value("${configServerTest.content}")
	private String content;
	
	public Map<String, String> getTestKeyword(){
		Map<String, String> keyword = new HashMap<>();
		keyword.put("title", title);
		keyword.put("content", content);

		return keyword; 
	}
}
