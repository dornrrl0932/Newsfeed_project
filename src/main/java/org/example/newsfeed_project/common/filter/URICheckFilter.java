package org.example.newsfeed_project.common.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class URICheckFilter implements Filter {

	//허용되는 URL 패턴 목록
	private static final List<Pattern> ALLOWED_PATTERNS = new ArrayList<>();

	static {
		ALLOWED_PATTERNS.add(Pattern.compile("^/users/signup$"));
		ALLOWED_PATTERNS.add(Pattern.compile("^/users/\\d+$"));
		ALLOWED_PATTERNS.add(Pattern.compile("^/users/login$"));
		ALLOWED_PATTERNS.add(Pattern.compile("^/users/logout$"));
		ALLOWED_PATTERNS.add(Pattern.compile("^/users/profile/\\d+$"));
		ALLOWED_PATTERNS.add(Pattern.compile("^/users/follow/\\d+$"));
		ALLOWED_PATTERNS.add(Pattern.compile("^/users/follow/\\d+/followers$"));
		ALLOWED_PATTERNS.add(Pattern.compile("^/users/follow/\\d+/followings$"));
		ALLOWED_PATTERNS.add(Pattern.compile("^/users/profile/\\d+$"));
		ALLOWED_PATTERNS.add(Pattern.compile("^/feed$"));
		ALLOWED_PATTERNS.add(Pattern.compile("^/feed/\\d+$"));
		ALLOWED_PATTERNS.add(Pattern.compile("^/feed/page/\\d+$"));
		ALLOWED_PATTERNS.add(Pattern.compile("^/feed/dateRange/\\d+$"));
		ALLOWED_PATTERNS.add(Pattern.compile("^/feed/\\d+/comments$"));
		ALLOWED_PATTERNS.add(Pattern.compile("^/feed/\\d+/comments/\\d+$"));
		ALLOWED_PATTERNS.add(Pattern.compile("^/feed/\\d+/comments/\\d+/\\d+/like$"));
		ALLOWED_PATTERNS.add(Pattern.compile("^/feed/\\d+/like$"));
		ALLOWED_PATTERNS.add(Pattern.compile("^/feed/\\d+/\\d+/like$"));
		ALLOWED_PATTERNS.add(Pattern.compile("^/feed/pageFriend/\\d+$"));
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws
		IOException,
		ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest)servletRequest;
		HttpServletResponse httpResponse = (HttpServletResponse)servletResponse;
		String requestURI = httpRequest.getRequestURI();

		//허용되는 URL이 아닌 경우
		if (!isAllowedURL(requestURI)) {
			httpResponse.setContentType("text/html; charset=UTF-8");
			httpResponse.setCharacterEncoding("UTF-8");
			httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
			httpResponse.getWriter().write("사이트에 연결할 수 없음. " + requestURI + "에 오타가 있는지 확인해주세요.");
			return;
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}

	/*허용되는 URL 인지 확인하는 메서드(URL 패턴 확인)*/
	private boolean isAllowedURL(String url) {
		for (Pattern pattern : ALLOWED_PATTERNS) {
			if (pattern.matcher(url).matches()) {
				return true;
			}
		}
		return false;
	}
}
