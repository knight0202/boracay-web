package com.mashup.boracay.login.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

public class CustomLogoutSucessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler{
	private static final Logger logger = LoggerFactory.getLogger(CustomLogoutSucessHandler.class);

	@Override
	public void onLogoutSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		if(authentication != null) {
			logger.debug("logout : {}", authentication.getName());
			request.getSession().invalidate();
		}
		
		setDefaultTargetUrl("/");
		super.onLogoutSuccess(request, response, authentication);
	}
	
	
}
