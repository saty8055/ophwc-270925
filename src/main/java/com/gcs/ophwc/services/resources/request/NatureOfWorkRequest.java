package com.gcs.ophwc.services.resources.request;

import java.util.List;

import com.gcs.ophwc.services.persistance.dao.entity.Projects;

public class NatureOfWorkRequest {

	private Long id;
	private String name;
	private String status;
	private Long userId;
	private List<Projects> projList;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Projects> getProjList() {
		return projList;
	}
	public void setProjList(List<Projects> projList) {
		this.projList = projList;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
}
