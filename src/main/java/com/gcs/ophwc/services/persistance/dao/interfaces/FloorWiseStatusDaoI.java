package com.gcs.ophwc.services.persistance.dao.interfaces;

import java.util.List;

import com.gcs.ophwc.services.persistance.dao.api.IOphwcDaoSupport;
import com.gcs.ophwc.services.persistance.dao.entity.FloorWork;
import com.gcs.ophwc.services.persistance.dao.entity.Floors;
import com.gcs.ophwc.services.persistance.dao.entity.ProjectFloorStatus;
import com.gcs.ophwc.services.persistance.dao.entity.ProjectStatus;

public interface FloorWiseStatusDaoI extends IOphwcDaoSupport<ProjectFloorStatus, Long> {

	public List<Floors> getAllFloors();

	public List<FloorWork> getAllFloorWorks();

	public List<ProjectStatus> getAllStatus();

	public List<ProjectFloorStatus> getAllProjectFloorStatus();
	
	public List<ProjectFloorStatus> getAllProjectFloorStatusByProject(Long projId);
	
	public List<ProjectFloorStatus> getAllProjectFloorStatusByProjectAndFloor(Long projId,Long floorId);
	
	public List<ProjectFloorStatus> getAllProjectFloorStatusByProjectAndFloorAndWork(Long projId,Long floorId,Long workId);
	
	public List<ProjectFloorStatus> getAllProjectFloorStatusByProjectAndFloorAndWorkAndStatus(Long projId,Long floorId,Long workId,Long statusId);
	
	public Floors getFloorById(Long floorId);
	
	public List<ProjectFloorStatus> getAllProjectsWithFloorsUnderNatureAndDivision(Long divId, Long natureId);
}
