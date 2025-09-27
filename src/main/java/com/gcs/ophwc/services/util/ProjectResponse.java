package com.gcs.ophwc.services.util;

import java.util.List;

import com.gcs.ophwc.services.persistance.dao.entity.Projects;

public class ProjectResponse {
	private int statusCode;
	private String statusMessage;
	private List<Projects> prjectList;
	
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
	public List<Projects> getPrjectList() {
		return prjectList;
	}
	public void setPrjectList(List<Projects> prjectList) {
		this.prjectList = prjectList;
	}
	
}
