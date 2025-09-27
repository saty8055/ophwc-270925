package com.gcs.ophwc.services.util;

import java.util.List;

import com.gcs.ophwc.services.persistance.dao.entity.SiteVisit;

public class SiteVisitResponse {

	private int statusCode;
	private String statusMessage;
	private List<SiteVisit> siteList;
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
	public List<SiteVisit> getSiteList() {
		return siteList;
	}
	public void setSiteList(List<SiteVisit> siteList) {
		this.siteList = siteList;
	}
	
}
