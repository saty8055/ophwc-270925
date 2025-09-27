package com.gcs.ophwc.services.persistance.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.gcs.ophwc.services.persistance.dao.api.OphwcDaoSupport;
import com.gcs.ophwc.services.persistance.dao.entity.NatureOfWork;
import com.gcs.ophwc.services.persistance.dao.interfaces.NatureOfWorkDao;

@SuppressWarnings("serial")
@Repository
public class NatureOfWorkDaoImpl extends OphwcDaoSupport<NatureOfWork, Long> implements NatureOfWorkDao {

	private static final Logger applicationLog = Logger.getLogger("applicationLogger");
	private static final Logger errorLog = Logger.getLogger("errorLoggger");
	
	@Override
	public List<NatureOfWork> getAllNatureOfWorksByUser(Long id) {
		applicationLog.debug("Came Into getAllNatureOfWorksByUser()");
		Query query=getEntityManager().createQuery("select distinct natureOfProject from ProjectResources where user_id="+id);
		return query.getResultList();
	}

	@Override
	public List<NatureOfWork> getAllNatureOfWorksInAsc() {
		applicationLog.debug("Came Into getAllNatureOfWorksInAsc()");
		Query query=getEntityManager().createQuery("from NatureOfWork order by id asc");
		return query.getResultList();
	}

}
