package com.springSecurityDB.config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springSecurityDB.interceptor.InjectCommonModelHandlerInterceptor;

@Configuration
@EnableWebMvc
@EnableConfigurationProperties({FreeMarkerProperties.class})
public class WebConfig extends WebMvcConfigurerAdapter {

	private FreeMarkerProperties freeMarkerProperties;
	private HandlerInterceptor injectCommonModelHandlerInterceptor;

	@Autowired
	public void setFreeMarkerProperties( FreeMarkerProperties freeMarkerProperties ) {
		this.freeMarkerProperties = freeMarkerProperties;
	}

	@Autowired
	@Qualifier(value = "injectCommonModelHandlerInterceptor")
	public void setInjectCommonModelHandlerInterceptor(HandlerInterceptor injectCommonModelHandlerInterceptor) {
		this.injectCommonModelHandlerInterceptor = injectCommonModelHandlerInterceptor;
	}

	/**
	 * 리소스 핸들러 설정
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/js/**")
				.addResourceLocations("/resources/js/")
				.resourceChain(true);
		registry.addResourceHandler("/ckeditor/**")
				.addResourceLocations("/resources/ckeditor/")
				.resourceChain(true);
	}

	/**
	 * Interceptor 설정
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		String[] staticResourcePatterns = {"/js/**", "/ckeditor/**"};

		List<InterceptorRegistration> interceptorRegistrationList = new ArrayList<>();

        interceptorRegistrationList.add(registry.addInterceptor(injectCommonModelHandlerInterceptor));

		for (InterceptorRegistration registration : interceptorRegistrationList) {
			registration.excludePathPatterns(staticResourcePatterns);
		}
	}

	/**
	 * ViewResolver 등록
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));

		MappingJackson2JsonView mappingJackson2JsonView = new MappingJackson2JsonView();
		mappingJackson2JsonView.setExtractValueFromSingleKeyModel(true);
		mappingJackson2JsonView.setObjectMapper(objectMapper);
		registry.enableContentNegotiation(mappingJackson2JsonView);

		registry.viewResolver(new BeanNameViewResolver());

		FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
		freeMarkerViewResolver.setCache(freeMarkerProperties.isCache());
		freeMarkerViewResolver.setPrefix(freeMarkerProperties.getPrefix());
		freeMarkerViewResolver.setSuffix(freeMarkerProperties.getSuffix());
		freeMarkerViewResolver.setContentType(freeMarkerProperties.getContentType().toString());

		registry.viewResolver(freeMarkerViewResolver);
	}

	/**
	 * controller parameter 수정
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		
	}

	@Bean
	public HandlerInterceptor injectCommonModelHandlerInterceptor() {
		return new InjectCommonModelHandlerInterceptor();
    }
}
