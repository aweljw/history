package com.springSecurityDB.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.springSecurityDB.helper.RequestHelper;
import com.springSecurityDB.helper.ResponseHelper;

public class InjectCommonModelHandlerInterceptor extends HandlerInterceptorAdapter {

    public static final String SERVER_HOST_ATTRIBUTE = "serverHost";

    /**
     * super 클래스로의 제어 위임.
     * @param request ServletRequest
     * @param response ServletResponse
     * @param handler 핸들러 매핑이 찾아준 컨트롤러 빈 오브젝트
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(request, response, handler);
    }

    /**
     * 사이트에 필요한 공통적인 모델 주입.
     * @param request ServletRequest
     * @param response ServletResponse
     * @param handler 핸들러 매핑이 찾아준 컨트롤러 빈 오브젝트
     * @param modelAndView Spring MVC 의 ViewModel.
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        super.postHandle(request, response, handler, modelAndView);
        if (!ResponseHelper.isHtmlViewResponse(modelAndView) || RequestHelper.isAjaxRequest(request)) {
            return;
        }
        modelAndView.addObject("currentSchema", RequestHelper.getRequestSchema(request));
        modelAndView.addObject(SERVER_HOST_ATTRIBUTE, String.format("%s://%s", request.getScheme(), request.getHeader("host")));
    }

}

