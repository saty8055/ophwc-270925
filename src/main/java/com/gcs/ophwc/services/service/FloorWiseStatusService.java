package com.gcs.ophwc.services.service;

import java.util.List;

import com.gcs.ophwc.services.persistance.dao.entity.FloorWork;
import com.gcs.ophwc.services.persistance.dao.entity.Floors;
import com.gcs.ophwc.services.persistance.dao.entity.ProjectFloorStatus;
import com.gcs.ophwc.services.persistance.dao.entity.ProjectStatus;
import com.gcs.ophwc.services.resources.request.ProjectFloorStatusRequest;
import com.gcs.ophwc.services.util.ProjectFloorsUtil;

public interface FloorWiseStatusService {

	public List<Floors> getAllFloors(Long projId);

	public List<FloorWork> getAllFloorWorks();

	public List<ProjectStatus> getAllStatus();

	public List<ProjectFloorStatus> getAllProjectFloorStatus();
	
	public ProjectFloorStatus getAllProjectFloorStatusByProject(Long projId);
	
	public void saveProjectFloorStatus(ProjectFloorStatus floorStatus);
	
	public void saveProjectFloorStatusMultipleFloors(ProjectFloorStatusRequest floorStatus);
	
	public List<FloorWork> getAllFloorStatusByprojectAndFloor(Long projId,Long floorId);
	
	public Floors getFloorById(Long floorId);
	
	public Floors getAllFloorWorkStatusByProjctAndFloor(Long projId,Long floorId);
	
	public List<ProjectFloorsUtil> getAllProjectsWithFloorsUnderNatureAndDivision(Long divId, Long natureId);
	
	

}
