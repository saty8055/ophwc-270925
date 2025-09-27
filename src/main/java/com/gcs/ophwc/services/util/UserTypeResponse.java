package com.gcs.ophwc.services.util;

import java.util.List;

import com.gcs.ophwc.services.persistance.dao.entity.UserTypes;

public class UserTypeResponse {
	
	private int statusCode;
	private String statusMessage;
	private List<UserTypes> userTypesList;
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public List<UserTypes> getUserTypesList() {
		return userTypesList;
	}
	public void setUserTypesList(List<UserTypes> userTypesList) {
		this.userTypesList = userTypesList;
	}
	
}
