package com.gcs.ophwc.services.util;

import java.util.List;

import com.gcs.ophwc.services.persistance.dao.entity.NatureOfWork;


public class NatureOfWorkResponse {

	private int statusCode;
	private String statusMessage;
	private List<NatureOfWork> natureWorkList;
	
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
	public List<NatureOfWork> getNatureWorkList() {
		return natureWorkList;
	}
	public void setNatureWorkList(List<NatureOfWork> natureWorkList) {
		this.natureWorkList = natureWorkList;
	}
	
	
}
