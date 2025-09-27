package com.gcs.ophwc.services.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.ophwc.services.persistance.dao.entity.FloorWork;
import com.gcs.ophwc.services.persistance.dao.entity.Floors;
import com.gcs.ophwc.services.persistance.dao.entity.ProjectFloorStatus;
import com.gcs.ophwc.services.persistance.dao.entity.ProjectStatus;
import com.gcs.ophwc.services.persistance.dao.entity.Projects;
import com.gcs.ophwc.services.persistance.dao.interfaces.FloorWiseStatusDaoI;
import com.gcs.ophwc.services.persistance.dao.interfaces.ProjectDao;
import com.gcs.ophwc.services.resources.request.ProjectFloorStatusRequest;
import com.gcs.ophwc.services.service.FloorWiseStatusService;
import com.gcs.ophwc.services.util.ProjectFloorsUtil;

@Service
public class FloorWiseStatusServiceImpl implements FloorWiseStatusService {

	@Autowired
	private FloorWiseStatusDaoI floorDao;

	@Autowired
	private ProjectDao projectDao;

	@Override
	public List<Floors> getAllFloors(Long projId) {
		System.out.println("getAllFloors() service");
		List<Floors> floorsList = floorDao.getAllFloors();
		List<FloorWork> floorWorksList = floorDao.getAllFloorWorks();
		int size=floorWorksList.size();
		List<ProjectFloorStatus> projectFloorStatusList = floorDao.getAllProjectFloorStatusByProject(projId);

		for (Floors floors : floorsList) {
			int floorTemp = 0, workTemp = 0, statusTemp = 0;
			for (ProjectFloorStatus projectFloorStatus : projectFloorStatusList) {
				if (floors.getId().equals(projectFloorStatus.getFloor().getId())) {
					for (FloorWork floorWork : floorWorksList) {
						if (floorWork.getId().equals(projectFloorStatus.getFloorWork().getId())) {
							if (projectFloorStatus.getProjectStatus().getId() == 2) {
								statusTemp++;
							}
							workTemp++;
						}
					}
					floorTemp++;
				}

			}
			if (floorTemp == 0)
				floors.setStatus("N");
			else if (floors.getId() == 1) {
				if (workTemp == size && statusTemp == size) {
					floors.setStatus("C");
				} else
					floors.setStatus("P");
			} else {
				if (workTemp == size-1 && statusTemp == size-1) {
					floors.setStatus("C");
				} else
					floors.setStatus("P");
			}
		}

		return floorsList;
	}

	@Override
	public List<FloorWork> getAllFloorWorks() {
		return floorDao.getAllFloorWorks();
	}

	@Override
	public List<ProjectStatus> getAllStatus() {
		return floorDao.getAllStatus();
	}

	@Override
	public List<ProjectFloorStatus> getAllProjectFloorStatus() {
		return floorDao.getAllProjectFloorStatus();
	}

	@Override
	public ProjectFloorStatus getAllProjectFloorStatusByProject(Long projId) {

		List<Floors> floorsList = floorDao.getAllFloors();
		List<FloorWork> floorWorksList = floorDao.getAllFloorWorks();
		int size=floorWorksList.size();
		List<ProjectStatus> projectStatusList = floorDao.getAllStatus();
		List<ProjectFloorStatus> projectFloorStatusList = floorDao.getAllProjectFloorStatusByProject(projId);

		for (Floors floors : floorsList) {
			int floorTemp = 0, workTemp = 0, statusTemp = 0;
			for (ProjectFloorStatus projectFloorStatus : projectFloorStatusList) {
				if (floors.getId().equals(projectFloorStatus.getFloor().getId())) {
					for (FloorWork floorWork : floorWorksList) {
						if (floorWork.getId().equals(projectFloorStatus.getFloorWork().getId())) {
							if (projectFloorStatus.getProjectStatus().getId() == 2) {
								statusTemp++;
							}
							workTemp++;
						}
					}
					floorTemp++;
				}

			}
			if (floorTemp == 0)
				floors.setStatus("N");
			else if (floors.getId() == 1) {
				if (workTemp == size && statusTemp == size) {
					floors.setStatus("C");
				} else
					floors.setStatus("P");
			} else {
				if (workTemp == size-1 && statusTemp == size-1) {
					floors.setStatus("C");
				} else
					floors.setStatus("P");
			}
		}

		ProjectFloorStatus projectFloorStatus = new ProjectFloorStatus();
		projectFloorStatus.setFloorsList(floorsList);
		projectFloorStatus.setFloorWorksList(floorWorksList);
		projectFloorStatus.setProjectStatusList(projectStatusList);
		return projectFloorStatus;
	}

	@Override
	@Transactional
	public void saveProjectFloorStatus(ProjectFloorStatus floorStatus) {
		ProjectFloorStatus projectFloorStatus = null;
		List<FloorWork> floorsWorkList = floorStatus.getFloor().getFloorsWork();
		for (FloorWork floorWork : floorsWorkList) {
			projectFloorStatus = new ProjectFloorStatus();
			projectFloorStatus.setProject(floorStatus.getProject());
			projectFloorStatus.setFloor(floorStatus.getFloor());
			projectFloorStatus.setFloorWork(floorWork);
			projectFloorStatus.setProjectStatus(floorWork.getProjectStatus());
			floorDao.save(projectFloorStatus);
		}

	}

	@Override
	@Transactional
	public void saveProjectFloorStatusMultipleFloors(ProjectFloorStatusRequest floorStatus) {
		List<ProjectFloorStatus> floorStatusList = floorStatus.getProjectFloorStatus();
		for (ProjectFloorStatus projectFloorStatus : floorStatusList) {
			List<ProjectFloorStatus> checkListListWithStatus = floorDao
					.getAllProjectFloorStatusByProjectAndFloorAndWorkAndStatus(projectFloorStatus.getProject().getId(),
							projectFloorStatus.getFloor().getId(), projectFloorStatus.getFloorWork().getId(),
							projectFloorStatus.getProjectStatus().getId());
			if (checkListListWithStatus != null && checkListListWithStatus.size() != 0) {

			} else {
				List<ProjectFloorStatus> checkListListWithoutStatus = floorDao
						.getAllProjectFloorStatusByProjectAndFloorAndWork(projectFloorStatus.getProject().getId(),
								projectFloorStatus.getFloor().getId(), projectFloorStatus.getFloorWork().getId());
				if (checkListListWithoutStatus != null && checkListListWithoutStatus.size() != 0) {
					projectFloorStatus.setId(checkListListWithoutStatus.get(0).getId());
					floorDao.update(projectFloorStatus);
				} else
					floorDao.save(projectFloorStatus);
			}

		}

	}

	@Override
	public List<FloorWork> getAllFloorStatusByprojectAndFloor(Long projId, Long floorId) {
		List<ProjectFloorStatus> floorStatusList = floorDao.getAllProjectFloorStatusByProjectAndFloor(projId, floorId);
		List<FloorWork> allWorksList = floorDao.getAllFloorWorks();

		if (floorId != 1 && allWorksList != null && allWorksList.size() != 0) {
			allWorksList.remove(0);
		}
		List<FloorWork> workList = new ArrayList<>();

		for (ProjectFloorStatus projectFloorStatus : floorStatusList) {
			FloorWork work = projectFloorStatus.getFloorWork();
			work.setProjectStatus(projectFloorStatus.getProjectStatus());
			workList.add(work);

		}

		ProjectStatus status = new ProjectStatus();
		status.setId((long) 3);
		status.setStatus("Not Started");

		for (FloorWork floorWork : allWorksList) {
			for (FloorWork work : workList) {
				if (floorWork.getId() == work.getId())
					floorWork.setProjectStatus(work.getProjectStatus());
			}

		}

		return allWorksList;
	}

	@Override
	public Floors getFloorById(Long floorId) {
		return floorDao.getFloorById(floorId);
	}

	@Override
	public Floors getAllFloorWorkStatusByProjctAndFloor(Long projId, Long floorId) {
		List<FloorWork> floorWorksList = floorDao.getAllFloorWorks();
		List<ProjectFloorStatus> projectFloorStatusList = floorDao.getAllProjectFloorStatusByProjectAndFloor(projId,
				floorId);

		for (FloorWork floorWork : floorWorksList) {
			String status = "N";
			for (ProjectFloorStatus projectFloorStatus : projectFloorStatusList) {
				if (floorWork.getId().equals(projectFloorStatus.getFloorWork().getId())) {
					if (projectFloorStatus.getProjectStatus().getId() == 1)
						status = "P";
					else if (projectFloorStatus.getProjectStatus().getId() == 2)
						status = "C";
					else if (projectFloorStatus.getProjectStatus().getId() == 3)
						status = "N";

					break;
				}
			}
			floorWork.setStatus(status);
		}
		if (floorId != 1)
			floorWorksList.remove(0);
		Floors floor = new Floors();
		floor.setFloorsWork(floorWorksList);

		if (projectFloorStatusList != null && projectFloorStatusList.size() != 0) {
			floor.setId(projectFloorStatusList.get(0).getFloor().getId());
			floor.setFloorNo(projectFloorStatusList.get(0).getFloor().getFloorNo());
			floor.setFloorName(projectFloorStatusList.get(0).getFloor().getFloorName());

		}
		return floor;
	}

	@Override
	public List<ProjectFloorsUtil> getAllProjectsWithFloorsUnderNatureAndDivision(Long divId, Long natureId) {
		System.out.println("getAllProjectsWithFloorsUnderNatureAndDivision() service");

		List<ProjectFloorsUtil> floorStatusList = new ArrayList<>();
		int size = floorDao.getAllFloorWorks().size();

		List<Projects> projectList = projectDao.getProjectsOfNatureAndDivisionForFloorStatus(divId, natureId);
		for (Projects project : projectList) {
			List<ProjectFloorStatus> projectFloorStatusList = floorDao
					.getAllProjectFloorStatusByProject(project.getId());

			List<Floors> floorsList = floorDao.getAllFloors();

			for (Floors floors : floorsList) {
				int floorTemp = 0, workTemp = 0, statusTemp = 0;
				
				for (ProjectFloorStatus projectFloorStatus : projectFloorStatusList) {
					if (floors.getId().equals(projectFloorStatus.getFloor().getId())) {
						
						List<FloorWork> floorWorksList = floorDao.getAllFloorWorks();
						
						for (FloorWork floorWork : floorWorksList) {
							if (floorWork.getId().equals(projectFloorStatus.getFloorWork().getId())) {
								if (projectFloorStatus.getProjectStatus().getId() == 2) {
									statusTemp++;
								}
								workTemp++;
							}
						}
						floorTemp++;
					}

				}
				if (floorTemp == 0)
					floors.setStatus("N");
				else if (floors.getId() == 1) {
					if (workTemp == size && statusTemp == size) {
						floors.setStatus("C");
					} else
						floors.setStatus("P");
				} else {
					if (workTemp == size-1 && statusTemp == size-1) {
						floors.setStatus("C");
					} else
						floors.setStatus("P");
				}
				ProjectFloorsUtil floorStatus = new ProjectFloorsUtil();
				floorStatus.setProject(project);
				floorStatus.setFloor(floors);
				floorStatusList.add(floorStatus);

			}
		}

		return floorStatusList;
	}
}
