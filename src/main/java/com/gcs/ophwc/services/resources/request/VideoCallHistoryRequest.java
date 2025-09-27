package com.gcs.ophwc.services.resources.request;

import java.util.Date;

import com.gcs.ophwc.services.persistance.dao.entity.User;

public class VideoCallHistoryRequest {

	private Long id;
	private User user;
	private String calledToNumber;
	private String duration;
	private Date createdDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getCalledToNumber() {
		return calledToNumber;
	}
	public void setCalledToNumber(String calledToNumber) {
		this.calledToNumber = calledToNumber;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}
