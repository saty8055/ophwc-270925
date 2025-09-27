package com.gcs.ophwc.services.util;

import com.gcs.ophwc.services.persistance.dao.entity.Floors;
import com.gcs.ophwc.services.persistance.dao.entity.Projects;

public class ProjectFloorsUtil {

	private Projects project;
	private Floors floor;
	
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
	@Override
	public String toString() {
		return "ProjectFloorsUtil [project=" + project + ", floor=" + floor + "]";
	}
	
	
}
