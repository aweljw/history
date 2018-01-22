package com.springSecurityDB.helper;

import javax.servlet.http.HttpServletRequest;

/**
 * Request 관련 Helper Static Method.
 */
public class RequestHelper {

	public static final String AJAX_REQUEST_HEADER_VALUE = "xmlhttprequest";
	public static final String MANAGE_REQUEST_URI_PREFIX = "/manage";

	/**
	 * HTTP Request 가 Ajax 요청인지 여부를 확인. Ajax 여부는 Request Header 의 X-Requested-With 값으로 판별.
	 * @param request http request.
	 * @return ajax 요청 여부.
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String requestedWith = request.getHeader("X-Requested-With");

		return (requestedWith != null && requestedWith.equalsIgnoreCase(AJAX_REQUEST_HEADER_VALUE))
				|| request.getRequestURI().startsWith(MANAGE_REQUEST_URI_PREFIX);
    }

	/**
	 * HTTP Request 의 SCHEMA 확인.
	 * @param request http request.
	 * @return schema (http or https)
	 */
	public static String getRequestSchema(HttpServletRequest request) {
		String schema = request.getHeader("X-Forwarded-Proto");

		if (schema == null || schema.trim().isEmpty()) {
			schema = request.getScheme();
		}

		return schema.toLowerCase();
	}

	/**
	 * 요청 Client의 IP 확인.
	 * @param request http request.
	 * @return 요청 client ip.
	 */
	public static String getRequestRemoteIp(HttpServletRequest request) {
		String remoteIp = request.getHeader("X-FORWARDED-FOR");

		if (remoteIp == null) {
			remoteIp = request.getRemoteAddr();
		}
		if (remoteIp.contains(",")) {
			String[] remoteIpParts = remoteIp.split(",");
			remoteIp = remoteIpParts[0].trim();
		}

		return remoteIp;
	}

	/**
	 * HTTP Request 의 User-Agent 로 OSX 여부 확인.
	 * @param request 확인하려는 http request.
	 * @return MacOS 일 경우 true.
	 */
	public static boolean isMacOSRequest(HttpServletRequest request) {
		String userAgentString = request.getHeader("User-Agent");
		if (!(userAgentString == null || userAgentString.isEmpty())) {
			String lowerUserAgentString = userAgentString.toLowerCase();
			if (lowerUserAgentString.contains("os x")) {
				return true;
			}
		}

		return false;
	}

	/**
	 * HTTP Request 의 Referer 확인.
	 * @param request http request.
	 * @return 이전 페이지 url
	 */
	public static String getPreviousUrl(HttpServletRequest request) {
		String defaultUrl = "/";
		if (request == null) {
			return defaultUrl;
		}

		String previousUrl = request.getHeader("Referer");
		if (previousUrl == null || previousUrl.isEmpty()) {
			return defaultUrl;
		}

		return previousUrl;
	}
}
