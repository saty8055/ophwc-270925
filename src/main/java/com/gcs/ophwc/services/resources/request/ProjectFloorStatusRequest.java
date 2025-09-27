package com.gcs.ophwc.services.resources.request;

import java.util.List;

import com.gcs.ophwc.services.persistance.dao.entity.ProjectFloorStatus;

public class ProjectFloorStatusRequest {

	private List<ProjectFloorStatus> projectFloorStatus;

	public List<ProjectFloorStatus> getProjectFloorStatus() {
		return projectFloorStatus;
	}

	public void setProjectFloorStatus(List<ProjectFloorStatus> projectFloorStatus) {
		this.projectFloorStatus = projectFloorStatus;
	}
}
