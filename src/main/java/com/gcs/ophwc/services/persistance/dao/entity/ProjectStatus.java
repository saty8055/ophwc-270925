package com.gcs.ophwc.services.persistance.dao.entity;
// Generated Jun 6, 2018 7:17:41 PM by Hibernate Tools 5.2.6.Final

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class ProjectStatus implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	private String status;
	private String checkStatus;

	public ProjectStatus() {
	}

	public ProjectStatus(String status) {
		this.status = status;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	@Override
	public String toString() {
		return "ProjectStatus [id=" + id + ", status=" + status + ", checkStatus=" + checkStatus + "]";
	}

}
