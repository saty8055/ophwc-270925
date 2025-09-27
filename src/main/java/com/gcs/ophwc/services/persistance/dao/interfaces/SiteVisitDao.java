package com.gcs.ophwc.services.persistance.dao.interfaces;

import com.gcs.ophwc.services.persistance.dao.api.IOphwcDaoSupport;
import com.gcs.ophwc.services.persistance.dao.entity.SiteVisit;

public interface SiteVisitDao extends IOphwcDaoSupport<SiteVisit, Long>{

	public String checkPohotosLimit(Long projId);
	public void deleteSiteVisitByUser(Long userId);
	public int deleteSiteVisitByPath(String path);
	public void deleteSiteVisitProjectId(Long projId);
}
