package com.gcs.ophwc.services.resources;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcs.ophwc.services.persistance.dao.entity.Floors;
import com.gcs.ophwc.services.persistance.dao.entity.ProjectFloorStatus;
import com.gcs.ophwc.services.resources.request.ProjectFloorStatusRequest;
import com.gcs.ophwc.services.service.FloorWiseStatusService;
import com.gcs.ophwc.services.util.Response;

@RestController
@RequestMapping("/floorStatusService")
public class FloorWiseStatusResource {

	private static final Logger applicationLog = Logger.getLogger("applicationLogger");
	private static final Logger errorLog = Logger.getLogger("errorLoggger");

	@Autowired
	private FloorWiseStatusService floorService;

	@RequestMapping(value = "/getProjectFloorsWorksStatus", produces = "application/json", method = RequestMethod.POST)
	public ProjectFloorStatus getProjectFloorStatus(@RequestBody ProjectFloorStatus req) {
		applicationLog.debug("Came Into getProjectFloorStatus in FloorWiseStatusResource");
		ProjectFloorStatus floorStatus = floorService.getAllProjectFloorStatusByProject(req.getId());
		return floorStatus;
	}

	@RequestMapping(value = "/saveProjectFloorStatusSingleFloor", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Response saveProjectFloorStatusSingleFloor(@RequestBody ProjectFloorStatus request) {
		applicationLog.debug("Came Into saveProjectFloorStatusSingleFloor in FloorWiseStatusResource");
		Response resp = new Response();
		try {
			floorService.saveProjectFloorStatus(request);
			resp.setStatusCode(1);
			resp.setStatusMessage("Success");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatusCode(0);
			resp.setStatusMessage("Failure");
		}
		return resp;
	}

	@RequestMapping(value = "/saveProjectFloorStatusMultipleFloors", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Response saveProjectFloorStatusMultipleFloors(@RequestBody ProjectFloorStatusRequest request) {
		applicationLog.debug("Came Into saveProjectFloorStatusMultipleFloors in FloorWiseStatusResource");
		Response resp = new Response();
		try {
			floorService.saveProjectFloorStatusMultipleFloors(request);
			resp.setStatusCode(1);
			resp.setStatusMessage("Success");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatusCode(0);
			resp.setStatusMessage("Failure");
		}
		return resp;
	}
	
	@RequestMapping(value = "/getProjectFloorsWorksStatusByFloor", produces = "application/json", method = RequestMethod.POST)
	public Floors getProjectFloorsWorksStatusByFloor(@RequestBody ProjectFloorStatus req) {
		applicationLog.debug("Came Into getProjectFloorsWorksStatusByFloor in FloorWiseStatusResource");
		Floors floor = floorService.getAllFloorWorkStatusByProjctAndFloor(req.getProject().getId(), req.getFloor().getId());
		return floor;
	}
}
