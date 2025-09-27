package com.gcs.ophwc.services.util;

import java.util.List;

import com.gcs.ophwc.services.persistance.dao.entity.Divisions;

public class DivisionResponse {

	private int statusCode;
	private String statusMessage;
	private List<Divisions> divisionList;
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
	public List<Divisions> getDivisionList() {
		return divisionList;
	}
	public void setDivisionList(List<Divisions> divisionList) {
		this.divisionList = divisionList;
	}
	
}
