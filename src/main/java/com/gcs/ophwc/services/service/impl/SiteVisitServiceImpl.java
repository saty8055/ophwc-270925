package com.gcs.ophwc.services.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.ophwc.services.persistance.dao.entity.Projects;
import com.gcs.ophwc.services.persistance.dao.entity.SiteVisit;
import com.gcs.ophwc.services.persistance.dao.interfaces.ProjectDao;
import com.gcs.ophwc.services.persistance.dao.interfaces.SiteVisitDao;
import com.gcs.ophwc.services.service.SiteVisitService;
import com.gcs.ophwc.services.util.Constants;
import com.gcs.ophwc.services.util.Response;

@Service
public class SiteVisitServiceImpl implements SiteVisitService {
	
	private static final Logger applicationLog = Logger.getLogger("applicationLogger");
	private static final Logger errorLog = Logger.getLogger("errorLoggger");

	@Autowired
	private SiteVisitDao siteVisitDao;

	@Autowired
	private ProjectDao projectDao;

	@Override
	@Transactional
	public Response setSiteLocation(SiteVisit site) {
		applicationLog.debug("Came Into setSiteLocation()");
		Response resp = new Response();
		try {
			siteVisitDao.save(site);
			resp.setStatusCode(Constants.SUCCESS);
			resp.setStatusMessage(Constants.SUCCESSMSG);
			return resp;
		} catch (Exception e) {
			errorLog.error("Error in setSiteLocation()="+e.getMessage());
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			e.printStackTrace();
			return resp;
		}

	}

	@Override
	@Transactional
	public Projects updateSiteLocation(Long id, String lattitude, String langitude) {
		applicationLog.debug("Came Into updateSiteLocation()");
		return projectDao.updateSiteLocation(id, lattitude, langitude);
	}

	@Override
	public List<SiteVisit> getSiteVisitList() {
		applicationLog.debug("Came Into getSiteVisitList()");
		return siteVisitDao.findAll();
	}

	@Override
	@Transactional
	public String save(SiteVisit siteVisit) {
		applicationLog.debug("Came Into save()");
		String path = siteVisitDao.checkPohotosLimit(siteVisit.getProjects().getId());
		siteVisitDao.save(siteVisit);
		return path;
	}

	@Override
	@Transactional
	public int deleteImageByPath(String path) {
		applicationLog.debug("Came Into deleteImageByPath()");
		int val = siteVisitDao.deleteSiteVisitByPath(path);
		return val;
	}
}
