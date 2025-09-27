package com.gcs.ophwc.services.resources.request;

import com.gcs.ophwc.services.persistance.dao.entity.Divisions;
import com.gcs.ophwc.services.persistance.dao.entity.NatureOfWork;
import com.gcs.ophwc.services.persistance.dao.entity.User;

public class ProjectsRequest {
	private Long id;
	private String clientName;
	private Divisions divisions;
	private User createdUser;
	private User assignTo;
	private NatureOfWork natureOfProject;
	private String projectName;
	private String projectDefination;
	private String projectLocation;
	private String latitude;
	private String langitude;
	private String status;
	private byte[] projectLogo;
	private String comments;
	private String isHandovered;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public Divisions getDivisions() {
		return divisions;
	}
	public void setDivisions(Divisions divisions) {
		this.divisions = divisions;
	}
	public User getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(User createdUser) {
		this.createdUser = createdUser;
	}
	
	public User getAssignTo() {
		return assignTo;
	}
	public void setAssignTo(User assignTo) {
		this.assignTo = assignTo;
	}
	
	public NatureOfWork getNatureOfProject() {
		return natureOfProject;
	}
	public void setNatureOfProject(NatureOfWork natureOfProject) {
		this.natureOfProject = natureOfProject;
	}
	public String getProjectDefination() {
		return projectDefination;
	}
	public void setProjectDefination(String projectDefination) {
		this.projectDefination = projectDefination;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectLocation() {
		return projectLocation;
	}
	public void setProjectLocation(String projectLocation) {
		this.projectLocation = projectLocation;
	}
	
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLangitude() {
		return langitude;
	}
	public void setLangitude(String langitude) {
		this.langitude = langitude;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public byte[] getProjectLogo() {
		return projectLogo;
	}
	public void setProjectLogo(byte[] projectLogo) {
		this.projectLogo = projectLogo;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getIsHandovered() {
		return isHandovered;
	}
	public void setIsHandovered(String isHandovered) {
		this.isHandovered = isHandovered;
	}
	
}
