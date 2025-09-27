package com.gcs.ophwc.services.persistance.dao.entity;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class NatureOfWork implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String status;
	
	private int projCount,uploadedCount,notUploadedCount,handOveredProjectsCount,runningProjects;
	private String workAmount,montlyWorkAmount;
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
	public int getProjCount() {
		return projCount;
	}
	public void setProjCount(int projCount) {
		this.projCount = projCount;
	}
	public int getUploadedCount() {
		return uploadedCount;
	}
	public void setUploadedCount(int uploadedCount) {
		this.uploadedCount = uploadedCount;
	}
	public int getNotUploadedCount() {
		return notUploadedCount;
	}
	public void setNotUploadedCount(int notUploadedCount) {
		this.notUploadedCount = notUploadedCount;
	}
	public String getWorkAmount() {
		return workAmount;
	}
	public void setWorkAmount(String workAmount) {
		this.workAmount = workAmount;
	}
	public String getMontlyWorkAmount() {
		return montlyWorkAmount;
	}
	public void setMontlyWorkAmount(String montlyWorkAmount) {
		this.montlyWorkAmount = montlyWorkAmount;
	}
	public int getHandOveredProjectsCount() {
		return handOveredProjectsCount;
	}
	public void setHandOveredProjectsCount(int handOveredProjectsCount) {
		this.handOveredProjectsCount = handOveredProjectsCount;
	}
	public int getRunningProjects() {
		return runningProjects;
	}
	public void setRunningProjects(int runningProjects) {
		this.runningProjects = runningProjects;
	}
	
	
}
