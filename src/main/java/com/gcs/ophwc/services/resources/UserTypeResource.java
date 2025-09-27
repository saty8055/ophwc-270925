package com.gcs.ophwc.services.resources;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcs.ophwc.services.persistance.dao.entity.UserTypes;
import com.gcs.ophwc.services.resources.request.UserTypesRequest;
import com.gcs.ophwc.services.service.UserTypeService;
import com.gcs.ophwc.services.util.Constants;
import com.gcs.ophwc.services.util.UserTypeResponse;
/*This is UserType management Service*/
@RestController
@RequestMapping("/userTypeService")
public class UserTypeResource {
	
	private static final Logger applicationLog = Logger.getLogger("applicationLogger");
	private static final Logger errorLog = Logger.getLogger("errorLoggger");

	@Autowired
	private UserTypeService userTypeService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN)
	public String getIt() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String resp = df.format(new Date());
		return resp;
	}

	@RequestMapping(value = "/createUserType", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public void createUserType(@RequestBody UserTypesRequest request) {
		applicationLog.debug("Came Into createUserType()");
		UserTypes userType = new UserTypes();
		userType.setTypeName(request.getTypeName());
		userTypeService.saveUserType(userType);
	}

	@RequestMapping(value = "/getUserType/{id}", produces = "application/json", method = RequestMethod.GET)
	public UserTypes getUserTypeById(@PathVariable("id") Long id) {
		applicationLog.debug("Came Into getUserTypeById()");
		UserTypes userType = userTypeService.getUserTypeById(id);
		return userType;
	}

	@RequestMapping(value = "/deleteUserType/{id}", produces = "application/json", method = RequestMethod.DELETE)
	public void deleteUserType(@PathVariable("id") Long id) {
		applicationLog.debug("Came Into deleteUserType()");
		userTypeService.deleteUserType(id);
	}

	@RequestMapping(value = "/getAllUserTypes", produces = "application/json", method = RequestMethod.GET)
	public UserTypeResponse getAllUserTypes() {
		applicationLog.debug("Came Into getAllUserTypes()");
		
		UserTypeResponse resp = new UserTypeResponse();
		List<UserTypes> usertypesList = userTypeService.getAllUserTypes();
		if (usertypesList.size() != 0) {
			resp.setStatusCode(Constants.SUCCESS);
			resp.setStatusMessage(Constants.SUCCESSMSG);
			resp.setUserTypesList(usertypesList);
		} else {
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			resp.setUserTypesList(null);
		}
		return resp;
	}

	@RequestMapping(value = "/updateUserType", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public void deleteUserType(@RequestBody UserTypesRequest request) {
		applicationLog.debug("Came Into deleteUserType()");
		UserTypes userType = new UserTypes();
		userType.setId(request.getId());
		userType.setTypeName(request.getTypeName());
		userTypeService.updateUserType(userType);
	}
}
