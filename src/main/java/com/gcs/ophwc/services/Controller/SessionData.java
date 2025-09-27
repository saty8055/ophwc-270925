package com.gcs.ophwc.services.Controller;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.gcs.ophwc.services.persistance.dao.entity.User;
import com.gcs.ophwc.services.util.TokenResponce;

@Component("sessionObj")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionData {

	private boolean isValidLogin;
	private String statusCode;
	private String statusMessage;
	
	private TokenResponce token;

	private User user;

	public boolean getIsValidLogin() {
		return isValidLogin;
	}

	public void setValidLogin(boolean isValidLogin) {
		this.isValidLogin = isValidLogin;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public TokenResponce getToken() {
		return token;
	}

	public void setToken(TokenResponce token) {
		this.token = token;
	}
	

}
