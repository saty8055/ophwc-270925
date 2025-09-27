package com.gcs.ophwc.services.service;

import java.util.List;

import com.gcs.ophwc.services.persistance.dao.entity.Divisions;
import com.gcs.ophwc.services.util.Response;

public interface DivisionService {

	public Response saveDivision(Divisions division);
	public Divisions getDivisionById(Long id);
	public Response deleteDivision(Long id);
	public Response updateDivision(Divisions division);
	public List<Divisions> getAllDivisions();
	public List<Divisions> getAllActiveDivisions();
	public List<Divisions> getAllUserDivisions(Long userId);
	
	public void deActivateDivision(Long id);
	public void activateDivision(Long id);
}
