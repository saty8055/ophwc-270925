package com.gcs.ophwc.services.persistance.dao.interfaces;

import java.util.List;

import com.gcs.ophwc.services.persistance.dao.api.IOphwcDaoSupport;
import com.gcs.ophwc.services.persistance.dao.entity.NatureOfWork;

public interface NatureOfWorkDao extends IOphwcDaoSupport<NatureOfWork, Long> {

	public List<NatureOfWork> getAllNatureOfWorksByUser(Long id);
	public List<NatureOfWork> getAllNatureOfWorksInAsc();
}
