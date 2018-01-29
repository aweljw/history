package com.snsOauthLogin.service.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.snsOauthLogin.domain.SnsUserInfo;
import com.snsOauthLogin.service.SnsService;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

@Service
public class SnsServiceImpl implements SnsService{
	
	private String consumerKey;
	private String consumerSecret;
	private String facebookClientId;
	private String redirectUrl;
	private String facebookClientSecretKey;

	@Value("${sns.twitter.consumerKey}")
	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	@Value("${sns.twitter.consumerSecret}")
	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	@Value("${sns.facebook.facebookClientId}")
	public void setFacebookClientId(String facebookClientId) {
		this.facebookClientId = facebookClientId;
	}
	
	public String getFacebookClientId() {
		return facebookClientId;
	}

	@Value("${sns.facebook.redirectUrl}")
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}
	
	@Value("${sns.facebook.facebookClientSecretKey}")
	public void setFacebookClientSecretKey(String facebookClientSecretKey) {
		this.facebookClientSecretKey = facebookClientSecretKey;
	}

	public String getFacebookClientSecretKey() {
		return facebookClientSecretKey;
	}

	@Override
	public void snsLogin(String divn, HttpServletRequest request, HttpServletResponse response) {
		if("twitter".equals(divn)) {
			twitterOauth(request, response);
		}else if("facebook".equals(divn)) {
			facebookOauth(response);
		}
	}

	@Override
	public SnsUserInfo snsLoginSuccess(String oauthVerifier, String code, HttpServletRequest request, HttpSession session) {
		SnsUserInfo snsUserInfo = new SnsUserInfo();
		
		if(oauthVerifier != null) {
			Twitter twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer(consumerKey, consumerSecret);
			AccessToken accessToken = null;

			RequestToken requestToken = (RequestToken )request.getSession().getAttribute("requestToken");			
			try {
				accessToken = twitter.getOAuthAccessToken(requestToken, oauthVerifier);
			} catch (TwitterException e) {
				e.printStackTrace();
			}

			twitter.setOAuthAccessToken(accessToken);

			snsUserInfo.setSnsDivn("twitter");
			snsUserInfo.setUserId(Long.toString(accessToken.getUserId()));
			snsUserInfo.setUserName(accessToken.getScreenName());
		}if(code != null) {
		    JSONObject jsonObject = new JSONObject(); 

			try {
				String accessToken = requesFaceBooktAccesToken(session, code);
				jsonObject = facebookUserDataLoadAndSave(accessToken, session);

				snsUserInfo.setSnsDivn("facebook");
				snsUserInfo.setUserId(jsonObject.get("id").toString());
				snsUserInfo.setUserName(jsonObject.get("name").toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		

		return snsUserInfo;
	}

	public void twitterOauth(HttpServletRequest request, HttpServletResponse response) {
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(consumerKey, consumerSecret);

		RequestToken requestToken;
		try {
			requestToken = twitter.getOAuthRequestToken();

			request.getSession().setAttribute("requestToken", requestToken);
			requestToken.getAuthorizationURL();
			requestToken.getToken();
			requestToken.getTokenSecret();

			response.sendRedirect(requestToken.getAuthorizationURL());
		} catch (TwitterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void facebookOauth(HttpServletResponse response) {
		try {
			response.sendRedirect(redirectUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String requesFaceBooktAccesToken(HttpSession session, String code) throws Exception {
		String facebookUrl = "https://graph.facebook.com/v2.8/oauth/access_token?"+
						 	"client_id="+facebookClientId+
						 	"&redirect_uri="+redirectUrl+
						 	"&client_secret="+facebookClientSecretKey+
						 	"&code="+code;
		
		HttpClientBuilder.create().build();
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet getRequest = new HttpGet(facebookUrl);
		String rawJsonString = client.execute(getRequest, new BasicResponseHandler());

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(rawJsonString);
		String faceBookAccessToken = (String) jsonObject.get("access_token");
		
		session.setAttribute("faceBookAccessToken", faceBookAccessToken);
		
		return faceBookAccessToken;
	}

	private JSONObject facebookUserDataLoadAndSave(String accessToken, HttpSession session) throws Exception {
	    String facebookUrl = "https://graph.facebook.com/me?"+
	            "access_token="+accessToken+
	            "&fields=id,name,email,picture";

	    HttpClient client = HttpClientBuilder.create().build();
	    HttpGet getRequest = new HttpGet(facebookUrl);
	    String rawJsonString = client.execute(getRequest, new BasicResponseHandler());

	    JSONParser jsonParser = new JSONParser();
	    JSONObject jsonObject = (JSONObject) jsonParser.parse(rawJsonString);
	    
	    return jsonObject;

	}

}