package com.gcs.ophwc.services.service;

import java.util.List;

import com.gcs.ophwc.services.persistance.dao.entity.NatureOfWork;
import com.gcs.ophwc.services.util.Response;

public interface NatureOfWorkService {
	public NatureOfWork getNatureOfWork(Long id);
	public Response deleteNatureOfWork(Long id);
	public List<NatureOfWork> getAllNatureOfWorks();
	public List<NatureOfWork> getAllNatureOfWorksByUser(Long id);
	public List<NatureOfWork> getAllNatureOfWorksWithProjectCount(Long id);
	
	public List<NatureOfWork> getDivisionNatureOfWorksWithProjectCount(Long divId,Long userId);
}
