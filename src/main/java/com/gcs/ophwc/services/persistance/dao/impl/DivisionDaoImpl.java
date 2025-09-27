package com.gcs.ophwc.services.persistance.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.gcs.ophwc.services.persistance.dao.api.OphwcDaoSupport;
import com.gcs.ophwc.services.persistance.dao.entity.Divisions;
import com.gcs.ophwc.services.persistance.dao.interfaces.DivisionDao;

@Repository
@SuppressWarnings("unchecked")
public class DivisionDaoImpl extends OphwcDaoSupport<Divisions, Long> implements  DivisionDao {

	/**
	 * 
	 */
	private static final Logger applicationLog = Logger.getLogger("applicationLogger");
	private static final Logger errorLog = Logger.getLogger("errorLoggger");
	
	private static final long serialVersionUID = 1L;

	@Override
	public List<Divisions> findAll() {
		applicationLog.debug("Came Into findAll()");
		Query query=getEntityManager().createQuery("FROM Divisions");
		List<Divisions> div=query.getResultList();
		return div;
	}
	
	@Override
	public List<Divisions> findAllActiveDivisions() {
		applicationLog.debug("Came Into findAllActiveDivisions()");
		Query query=getEntityManager().createQuery("FROM Divisions where status='Y'");
		List<Divisions> div=query.getResultList();
		return div;
	}

	@Override
	public List<Divisions> findAllUserDivisions(Long userId) {
		applicationLog.debug("Came Into findAllUserDivisions()");
		Query query=getEntityManager().createQuery("from Divisions where id in(select divisions from User where id="+userId+") and status='Y'");
		List<Divisions> divisionList=query.getResultList();
		return divisionList;
	}

	@Override
	public void deActivateDivision(Long id) {
		applicationLog.debug("Came Into deActivateDivision()");
		Query query=getEntityManager().createQuery("update Divisions set status='N' where id="+id);
		query.executeUpdate();
		
	}

	@Override
	public void activateDivision(Long id) {
		applicationLog.debug("Came Into activateDivision()");
		Query query=getEntityManager().createQuery("update Divisions set status='Y' where id="+id);
		query.executeUpdate();
		
	}

	

}
