package com.gcs.ophwc.services.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.ophwc.services.persistance.dao.entity.Divisions;
import com.gcs.ophwc.services.persistance.dao.interfaces.DivisionDao;
import com.gcs.ophwc.services.service.DivisionService;
import com.gcs.ophwc.services.util.Constants;
import com.gcs.ophwc.services.util.Response;

@Service
public class DivisionServiceImpl implements DivisionService ,Serializable{
	
	private static final Logger applicationLog = Logger.getLogger("applicationLogger");
	private static final Logger errorLog = Logger.getLogger("errorLoggger");

	/**
	 * 
	 */
	private static final long serialVersionUID = 878138317724541010L;
	@Autowired
	private DivisionDao divisionDao;

	@Override
	@Transactional
	public Response saveDivision(Divisions division) {
		applicationLog.debug("Came Into saveDivision()");
		Response resp=new Response();
		try {
			divisionDao.save(division);
			resp.setStatusCode(Constants.SUCCESS);
			resp.setStatusMessage(Constants.SUCCESSMSG);
			return resp;
		} catch (Exception e) {
			errorLog.error("Error In saveDivision()="+e.getMessage());
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			e.printStackTrace();
			return resp;
		}
		
	}

	@Override
	public Divisions getDivisionById(Long id) {
		applicationLog.debug("Came Into getDivisionById()");
		return divisionDao.findOne(id);
	}

	@Override
	@Transactional
	public List<Divisions> getAllDivisions() {
		applicationLog.debug("Came Into getAllDivisions()");
		return divisionDao.findAll();
	}
	
	@Override
	public List<Divisions> getAllActiveDivisions() {
		applicationLog.debug("Came Into getAllActiveDivisions()");
		return divisionDao.findAllActiveDivisions();
	}

	@Override
	@Transactional
	public Response deleteDivision(Long id) {
		applicationLog.debug("Came Into deleteDivision()");
		Response resp=new Response();
		try {
			divisionDao.deleteById(id);
			resp.setStatusCode(Constants.SUCCESS);
			resp.setStatusMessage(Constants.SUCCESSMSG);
			return resp;
		} catch (Exception e) {
			errorLog.error("Error In deleteDivision()="+e.getMessage());
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			e.printStackTrace();
			return resp;
		}
		
		
	}

	@Override
	@Transactional
	public Response updateDivision(Divisions division) {
		applicationLog.debug("Came Into updateDivision()");
		Response resp=new Response();
		try {
			divisionDao.update(division);
			resp.setStatusCode(Constants.SUCCESS);
			resp.setStatusMessage(Constants.SUCCESSMSG);
			return resp;
		} catch (Exception e) {
			errorLog.error("Error in updateDivision()="+e.getMessage());
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			e.printStackTrace();
			return resp;
		}
		
		
	}

	@Override
	public List<Divisions> getAllUserDivisions(Long userId) {
		applicationLog.debug("Came Into getAllUserDivisions()");
		return divisionDao.findAllUserDivisions(userId);
	}

	@Override
	@Transactional
	public void deActivateDivision(Long id) {
		applicationLog.debug("Came Into deActivateDivision()");
		divisionDao.deActivateDivision(id);
		
	}

	@Override
	@Transactional
	public void activateDivision(Long id) {
		applicationLog.debug("Came Into activateDivision()");
		divisionDao.activateDivision(id);
		
	}
	
}
