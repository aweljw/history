package com.springSecurityDB.helper;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.CookieGenerator;

/**
 * Response 관련 Helper Static Method.
 */
public class ResponseHelper {

    /**
     * 생성자.(static 용도의 객체 이므로, 인스턴스 생성을 제약)
     */
    private ResponseHelper() {
        throw new IllegalAccessError("Request Helper Static Method Class.");
    }

    /**
     * 특정 Response 와 연관된 Spring ModelAndView 가 HTML View 인지 여부를 판별한다.
     * @param modelAndView Spring MVC 의 ViewModel.
     * @return Spring ModelAndView 의 HTML View 여부.
     */
    public static boolean isHtmlViewResponse(ModelAndView modelAndView) {
        if (modelAndView == null) {
            return false;
        }

        Object viewObject = modelAndView.getView();
        if (viewObject != null && viewObject instanceof RedirectView) {
            return false;
        }

        String viewName = modelAndView.getViewName();
        return !(viewName == null || viewName.matches("(redirect|forward):.+"));
    }

    /**
     * 쿠기 생성 및 추가
     * @param response ServletResponse
     * @param name 쿠키 명
     * @param value 쿠키 값
     * @param maxAge CookieMaxAge
     * @param cookieDomain CookieDomain
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge, String cookieDomain) {
        CookieGenerator cookieGenerator = new CookieGenerator();
        cookieGenerator.setCookieName(name);
        cookieGenerator.setCookieMaxAge(maxAge);
        if (cookieDomain != null && !cookieDomain.startsWith("localhost")) {
            cookieGenerator.setCookieDomain(cookieDomain);
        }
        cookieGenerator.addCookie(response, value);
    }

}
