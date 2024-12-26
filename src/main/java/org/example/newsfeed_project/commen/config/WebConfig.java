package org.example.newsfeed_project.commen.config;

import org.example.newsfeed_project.commen.filter.LoginCheckFilter;
import org.example.newsfeed_project.commen.filter.URICheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.servlet.Filter;

@Configuration
public class WebConfig {

	/*URL 형식을 체크하는 필터*/
	@Bean
	public FilterRegistrationBean uriCheckFilter() {
		FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
		filterFilterRegistrationBean.setFilter(new URICheckFilter());
		filterFilterRegistrationBean.setOrder(1);
		filterFilterRegistrationBean.addUrlPatterns("/*");

		return filterFilterRegistrationBean;
	}

	/*로그인 상태 체크 필터 등록*/
	@Bean
	public FilterRegistrationBean loginCheckFilter() {

		FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
		filterFilterRegistrationBean.setFilter(new LoginCheckFilter());
		filterFilterRegistrationBean.setOrder(2);
		filterFilterRegistrationBean.addUrlPatterns("/*");

		return filterFilterRegistrationBean;
	}
}
