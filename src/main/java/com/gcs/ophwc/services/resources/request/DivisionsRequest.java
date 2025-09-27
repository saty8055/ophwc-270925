package com.gcs.ophwc.services.resources.request;

import java.util.List;

import com.gcs.ophwc.services.persistance.dao.entity.NatureOfWork;

public class DivisionsRequest {
	private Long id;
	private Long userId;
	private String divisionName;
	private String status;
	private List<NatureOfWork> natureOfProject;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getDivisionName() {
		return divisionName;
	}
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<NatureOfWork> getNatureOfProject() {
		return natureOfProject;
	}
	public void setNatureOfProject(List<NatureOfWork> natureOfProject) {
		this.natureOfProject = natureOfProject;
	}
	

}
