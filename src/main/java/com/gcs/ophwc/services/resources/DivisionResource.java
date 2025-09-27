package com.gcs.ophwc.services.resources;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcs.ophwc.services.Controller.SessionData;
import com.gcs.ophwc.services.persistance.dao.entity.Divisions;
import com.gcs.ophwc.services.resources.request.DivisionsRequest;
import com.gcs.ophwc.services.service.DivisionService;
import com.gcs.ophwc.services.util.Constants;
import com.gcs.ophwc.services.util.DivisionResponse;
import com.gcs.ophwc.services.util.Response;

/* This is Division Service */
@RestController
@RequestMapping("/divisionService")
public class DivisionResource {
	
	private static final Logger applicationLog = Logger.getLogger("applicationLogger");
	private static final Logger errorLog = Logger.getLogger("errorLoggger");

	@Autowired
	private SessionData sessionObj;

	@Autowired
	private DivisionService divisionService;
	@RequestMapping(value = "/createDivision", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Response createDivision(@RequestBody DivisionsRequest request) {
		applicationLog.debug("Came Into createDivision");
		Response resp = null;
		if (request.getDivisionName() != null && request.getDivisionName() != "") {
			Divisions division = new Divisions();
			division.setDivisionName(request.getDivisionName());
			division.setStatus(request.getStatus());
			resp=divisionService.saveDivision(division);
		} else {
			System.out.println("Division Name Must not be empty");
			resp = new Response();
			resp.setStatusCode(0);
			resp.setStatusMessage("Validation Error");
		}
		return resp;
	}

	@RequestMapping(value = "/getDivision", produces = "application/json", method = RequestMethod.POST)
	public Divisions getDivisionById(@RequestBody DivisionsRequest req) {
		applicationLog.debug("Came Into getDivisionById");
		Divisions division = divisionService.getDivisionById(req.getId());
		return division;
	}

	@RequestMapping(value = "/deleteDivision/{id}", produces = "application/json", method = RequestMethod.DELETE)
	public Response deleteDivision(@PathVariable("id") Long id) {
		applicationLog.debug("Came Into deleteDivision");
		Response resp = divisionService.deleteDivision(id);
		return resp;
	}

	@RequestMapping(value = "/getAllDivisions", produces = "application/json", method = RequestMethod.GET)
	public DivisionResponse getAllDivisions() {
		applicationLog.debug("Came Into getAllDivisions");
		DivisionResponse resp = new DivisionResponse();
		List<Divisions> divisionList = divisionService.getAllActiveDivisions();
		if (divisionList.size() != 0) {
			resp.setStatusCode(Constants.SUCCESS);
			resp.setStatusMessage(Constants.SUCCESSMSG);
			resp.setDivisionList(divisionList);
		} else {
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			resp.setDivisionList(null);
		}
		return resp;
	}
	
	@RequestMapping(value = "/getAllUserDivisions", produces = "application/json",consumes = "application/json", method = RequestMethod.POST)
	public DivisionResponse getAllUserDivisions(@RequestBody DivisionsRequest request) {
		applicationLog.debug("Came Into getAllUserDivisions");
		DivisionResponse resp = new DivisionResponse();
		List<Divisions> divisionList = divisionService.getAllUserDivisions(request.getId());
		if (divisionList.size() != 0) {
			resp.setStatusCode(Constants.SUCCESS);
			resp.setStatusMessage(Constants.SUCCESSMSG);
			resp.setDivisionList(divisionList);
		} else {
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			resp.setDivisionList(null);
		}
		return resp;
	}

	@RequestMapping(value = "/updateDivision", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public Response updateDivision(@RequestBody DivisionsRequest request) {
		applicationLog.debug("Came Into updateDivision");
		Response resp = null;
		if (request.getDivisionName() != null && request.getDivisionName() != "") {
			Divisions division = new Divisions();
			division.setId(request.getId());
			division.setDivisionName(request.getDivisionName());
			division.setStatus(request.getStatus());
			resp = divisionService.updateDivision(division);
		} else {
			System.out.println("Division Name Must not be empty");
			resp = new Response();
			resp.setStatusCode(0);
			resp.setStatusMessage("Validation Error");
		}
		return resp;
	}
}
