package com.gcs.ophwc.services.persistance.dao.entity;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class ProjectFloorStatus {

	@Id
	@GeneratedValue
	private Long id;

	private Projects project;

	private Floors floor;

	private FloorWork floorWork;

	private ProjectStatus projectStatus;

	private List<Floors> floorsList;
	private List<FloorWork> floorWorksList;
	private List<ProjectStatus> projectStatusList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Projects getProject() {
		return project;
	}

	public void setProject(Projects project) {
		this.project = project;
	}

	public Floors getFloor() {
		return floor;
	}

	public void setFloor(Floors floor) {
		this.floor = floor;
	}

	public FloorWork getFloorWork() {
		return floorWork;
	}

	public void setFloorWork(FloorWork floorWork) {
		this.floorWork = floorWork;
	}

	public ProjectStatus getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(ProjectStatus projectStatus) {
		this.projectStatus = projectStatus;
	}

	public List<Floors> getFloorsList() {
		return floorsList;
	}

	public void setFloorsList(List<Floors> floorsList) {
		this.floorsList = floorsList;
	}

	public List<FloorWork> getFloorWorksList() {
		return floorWorksList;
	}

	public void setFloorWorksList(List<FloorWork> floorWorksList) {
		this.floorWorksList = floorWorksList;
	}

	public List<ProjectStatus> getProjectStatusList() {
		return projectStatusList;
	}

	public void setProjectStatusList(List<ProjectStatus> projectStatusList) {
		this.projectStatusList = projectStatusList;
	}

	@Override
	public String toString() {
		return "ProjectFloorStatus [id=" + id + ", project=" + project + ", floor=" + floor + ", floorWork=" + floorWork
				+ ", projectStatus=" + projectStatus + "]";
	}
}
