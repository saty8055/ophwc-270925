package com.gcs.ophwc.services.resources;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcs.ophwc.services.Controller.SessionData;
import com.gcs.ophwc.services.persistance.dao.entity.NatureOfWork;
import com.gcs.ophwc.services.resources.request.DivisionsRequest;
import com.gcs.ophwc.services.resources.request.UserRequest;
import com.gcs.ophwc.services.service.NatureOfWorkService;
import com.gcs.ophwc.services.util.Constants;
import com.gcs.ophwc.services.util.NatureOfWorkResponse;

@RestController
@RequestMapping("/natureWork")
public class NatureOfWorkResource {
	
	private static final Logger applicationLog = Logger.getLogger("applicationLogger");
	private static final Logger errorLog = Logger.getLogger("errorLoggger");

	@Autowired
	private SessionData sessionObj;

	@Autowired
	private NatureOfWorkService natureWorkService;

	@RequestMapping(value = "/getAllNatureWorks", produces = "application/json", method = RequestMethod.GET)
	public NatureOfWorkResponse getAllNatureWorks() {
		applicationLog.debug("Came Into getAllNatureWorks()");
		NatureOfWorkResponse resp = new NatureOfWorkResponse();
		List<NatureOfWork> natureList = natureWorkService.getAllNatureOfWorks();
		if (natureList.size() != 0) {
			resp.setStatusCode(Constants.SUCCESS);
			resp.setStatusMessage(Constants.SUCCESSMSG);
			resp.setNatureWorkList(natureList);
		} else {
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			resp.setNatureWorkList(null);
		}
		return resp;
	}

	@RequestMapping(value = "/getAllNatureWorksWithProjCount", produces = "application/json",consumes = "application/json", method = RequestMethod.POST)
	public NatureOfWorkResponse getAllNatureWorks(@RequestBody DivisionsRequest req) {
		applicationLog.debug("Came Into getAllNatureWorks()");
		NatureOfWorkResponse resp = new NatureOfWorkResponse();
		List<NatureOfWork> natureList = null;
		if (req.getUserId() == 0)
			natureList = natureWorkService.getAllNatureOfWorksWithProjectCount(req.getId());
		else
			natureList = natureWorkService.getDivisionNatureOfWorksWithProjectCount(req.getId(), req.getUserId());
		if (natureList.size() != 0) {
			resp.setStatusCode(Constants.SUCCESS);
			resp.setStatusMessage(Constants.SUCCESSMSG);
			resp.setNatureWorkList(natureList);
		} else {
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			resp.setNatureWorkList(null);
		}
		return resp;
	}

	@RequestMapping(value = "/getNatureWorksByUser", produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
	public NatureOfWorkResponse getNatureWorksByUser(@RequestBody UserRequest req) {
		applicationLog.debug("Came Into getNatureWorksByUser()");
		NatureOfWorkResponse resp = new NatureOfWorkResponse();
		List<NatureOfWork> natureList = natureWorkService.getAllNatureOfWorksByUser(req.getId());
		if (natureList != null && natureList.size() != 0) {
			resp.setStatusCode(Constants.SUCCESS);
			resp.setStatusMessage(Constants.SUCCESSMSG);
			resp.setNatureWorkList(natureList);
		} else {
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			resp.setNatureWorkList(null);
		}
		return resp;
	}
}
