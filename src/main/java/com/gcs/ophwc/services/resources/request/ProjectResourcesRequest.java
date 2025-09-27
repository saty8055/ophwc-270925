package com.gcs.ophwc.services.resources.request;

import com.gcs.ophwc.services.persistance.dao.entity.NatureOfWork;
import com.gcs.ophwc.services.persistance.dao.entity.Projects;
import com.gcs.ophwc.services.persistance.dao.entity.User;

public class ProjectResourcesRequest {

	private Long id;
	private Projects projects;
	private User user;
	private NatureOfWork natureOfProject;
	private String status;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Projects getProjects() {
		return projects;
	}
	public void setProjects(Projects projects) {
		this.projects = projects;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public NatureOfWork getNatureOfProject() {
		return natureOfProject;
	}
	public void setNatureOfProject(NatureOfWork natureOfProject) {
		this.natureOfProject = natureOfProject;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
