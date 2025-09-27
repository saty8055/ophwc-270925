package com.gcs.ophwc.services.util;

public class TokenResponce {

	private String token;
	private String expiryTime;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getExpiryTime() {
		return expiryTime;
	}
	public void setExpiryTime(String expiryTime) {
		this.expiryTime = expiryTime;
	}
	@Override
	public String toString() {
		return "TokenResponce [token=" + token + ", expiryTime=" + expiryTime + "]";
	}
}
