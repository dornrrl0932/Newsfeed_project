package org.example.newsfeed_project;

import org.example.newsfeed_project.filter.LoginCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.servlet.Filter;

@Configuration
public class WebConfig {
	/*로그인 상태 체크 필터 등록*/
	@Bean
	public FilterRegistrationBean loginCheckFilter() {

		FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
		filterFilterRegistrationBean.setFilter(new LoginCheckFilter());
		filterFilterRegistrationBean.setOrder(1);
		filterFilterRegistrationBean.addUrlPatterns("/*");

		return filterFilterRegistrationBean;
	}
}
