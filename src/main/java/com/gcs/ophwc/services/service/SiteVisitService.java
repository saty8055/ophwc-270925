package com.gcs.ophwc.services.service;

import java.util.List;

import com.gcs.ophwc.services.persistance.dao.entity.Projects;
import com.gcs.ophwc.services.persistance.dao.entity.SiteVisit;
import com.gcs.ophwc.services.util.Response;

public interface SiteVisitService {

	public String save(SiteVisit siteVisit);
	public Response setSiteLocation(SiteVisit site);
	public Projects updateSiteLocation(Long id,String lattitude,String langitude);
	public List<SiteVisit> getSiteVisitList();
	
	public int deleteImageByPath(String path);
	
}
