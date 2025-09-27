package com.gcs.ophwc.services.persistance.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.gcs.ophwc.services.persistance.dao.api.OphwcDaoSupport;
import com.gcs.ophwc.services.persistance.dao.entity.FloorWork;
import com.gcs.ophwc.services.persistance.dao.entity.Floors;
import com.gcs.ophwc.services.persistance.dao.entity.ProjectFloorStatus;
import com.gcs.ophwc.services.persistance.dao.entity.ProjectStatus;
import com.gcs.ophwc.services.persistance.dao.interfaces.FloorWiseStatusDaoI;

@Repository
public class FloorWiseStatusDaoImpl extends OphwcDaoSupport<ProjectFloorStatus, Long> implements FloorWiseStatusDaoI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -282548389658081193L;
	private static final Logger applicationLog = Logger.getLogger("applicationLogger");
	private static final Logger errorLog = Logger.getLogger("errorLoggger");

	@Override
	public List<Floors> getAllFloors() {
		Query query = getEntityManager().createQuery("from Floors ORDER BY id asc");
		return query.getResultList();
	}

	@Override
	public List<FloorWork> getAllFloorWorks() {
		Query query = getEntityManager().createQuery("from FloorWork ORDER BY id asc");
		return query.getResultList();
	}

	@Override
	public List<ProjectStatus> getAllStatus() {
		Query query = getEntityManager().createQuery("from ProjectStatus ORDER BY id asc");
		return query.getResultList();
	}

	@Override
	public List<ProjectFloorStatus> getAllProjectFloorStatus() {
		Query query = getEntityManager().createQuery("from ProjectFloorStatus");
		return query.getResultList();
	}

	@Override
	public List<ProjectFloorStatus> getAllProjectFloorStatusByProject(Long projId) {
		Query query = getEntityManager().createQuery(
				"from ProjectFloorStatus where project=" + projId + " order by floor,floorWork,projectStatus asc");
		return query.getResultList();
	}

	@Override
	public List<ProjectFloorStatus> getAllProjectFloorStatusByProjectAndFloor(Long projId, Long floorId) {
		Query query = getEntityManager()
				.createQuery("from ProjectFloorStatus where project=" + projId + " and floor=" + floorId);
		return query.getResultList();
	}

	@Override
	public Floors getFloorById(Long floorId) {
		Query query = getEntityManager().createQuery("from Floors where id=" + floorId);
		return (Floors) query.getResultList().get(0);
	}

	@Override
	public List<ProjectFloorStatus> getAllProjectFloorStatusByProjectAndFloorAndWork(Long projId, Long floorId,
			Long workId) {
		Query query = getEntityManager().createQuery("from ProjectFloorStatus where project=" + projId + " and floor="
				+ floorId + " and floorWork=" + workId);
		return query.getResultList();
	}

	@Override
	public List<ProjectFloorStatus> getAllProjectFloorStatusByProjectAndFloorAndWorkAndStatus(Long projId, Long floorId,
			Long workId, Long statusId) {
		Query query = getEntityManager().createQuery("from ProjectFloorStatus where project=" + projId + " and floor="
				+ floorId + " and floorWork=" + workId + " and projectStatus=" + statusId);
		return query.getResultList();
	}

	@Override
	public List<ProjectFloorStatus> getAllProjectsWithFloorsUnderNatureAndDivision(Long divId, Long natureId) {
		Query query = getEntityManager()
				.createQuery("from ProjectFloorStatus where project in(select id from Projects where natureOfProject="
						+ natureId + " and divisions=" + divId + " and isHandovered!='Y') order by project");
		return query.getResultList();
	}

}
