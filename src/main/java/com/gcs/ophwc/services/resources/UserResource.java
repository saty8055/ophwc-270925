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
import com.gcs.ophwc.services.persistance.dao.entity.ProjectResources;
import com.gcs.ophwc.services.persistance.dao.entity.Projects;
import com.gcs.ophwc.services.persistance.dao.entity.User;
import com.gcs.ophwc.services.resources.request.UserRequest;
import com.gcs.ophwc.services.service.ProjectService;
import com.gcs.ophwc.services.service.UserService;
import com.gcs.ophwc.services.util.Constants;
import com.gcs.ophwc.services.util.Response;
import com.gcs.ophwc.services.util.UserResponse;
import com.gcs.ophwc.services.util.Validations;

/* This is User management Service */
@RestController
@RequestMapping("/userService")
public class UserResource {
	
	private static final Logger applicationLog = Logger.getLogger("applicationLogger");
	private static final Logger errorLog = Logger.getLogger("errorLoggger");

	@Autowired
	private SessionData sessionObj;

	@Autowired
	private UserService userService;

	@Autowired
	private ProjectService projectService;

	@RequestMapping(value = "/validateUser", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public UserResponse validateUserGetUser(@RequestBody UserRequest request) {
		applicationLog.debug("Came Into validateUserGetUser()");
		UserResponse resp=new UserResponse();
		User user = userService.validateGetUser(request.getUsername(), request.getPassword());
		if(user!=null) {
			resp.setStatusCode(Constants.SUCCESS);
			resp.setStatusMessage(Constants.SUCCESSMSG);
			resp.setUser(user);
		}else {
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			resp.setUser(null);
		}
		return resp;
	}

	@RequestMapping(value = "/createUser", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Response createUser(@RequestBody UserRequest request) {
		applicationLog.debug("Came Into createUser()");

		Response resp = null;
		boolean valid = true;
		Validations validations = new Validations();
		if (!validations.nameValidation(request.getUsername()))
			valid = false;
		if (!validations.passwordValidation(request.getPassword()))
			valid = false;
		if (!validations.emailIdValidation(request.getEmailId()))
			valid = false;
		if (!validations.phoneNoValidation(request.getPhoneNum()))
			valid = false;
		if (request.getUserTypes() == null)
			valid = false;
		if (request.getDivisions() == null)
			valid = false;

		if (valid) {
			User user = new User();
			user.setUsername(request.getUsername());
			user.setPassword(request.getPassword());
			user.setEmailId(request.getEmailId());
			user.setFirstName(request.getFirstName());
			user.setLastName(request.getLastName());
			user.setPhoneNum(request.getPhoneNum());
			user.setUserImage(request.getUserImage());
			user.setStatus(request.getStatus());
			user.setUserTypes(request.getUserTypes());

			if (request.getUserTypes().getId() == 1 || request.getUserTypes().getId() == 3)
				user.setDivisions(null);
			else
				user.setDivisions(request.getDivisions());

			resp = userService.saveUser(user);

			List<NatureOfWork> natureList = request.getNatureList();
			if (natureList != null && natureList.size() != 0 && resp.getStatusCode() == 1) {
				for (NatureOfWork nature : natureList) {
					List<Projects> projList = nature.getProjList();
					ProjectResources resource = null;
					// if (projList != null && projList.size() != 0 && resp.getStatusCode() == 1) {
					for (Projects projects : projList) {
						System.out.println("ProjectId=" + projects.getId());
						resource = new ProjectResources();
						resource.setUser(user);
						resource.setNatureOfProject(nature);
						resource.setProjects(projects);
						resource.setStatus("Y");
						userService.saveProjectResource(resource);
						// }
					}
				}
			}
		} else {
			resp = new Response();
			resp.setStatusCode(0);
			resp.setStatusMessage("Validation Errors");
		}
		return resp;
	}

	@RequestMapping(value = "/getUser", produces = "application/json", method = RequestMethod.POST)
	public User getUserById(@RequestBody User request) {
		applicationLog.debug("Came Into getUserById()");
		User user = userService.getUserById(request.getId());
		return user;
	}

	@RequestMapping(value = "/deleteUser", produces = "application/json", method = RequestMethod.DELETE)
	public Response deleteUser(@RequestBody User user) {
		applicationLog.debug("Came Into deleteUser()");
		Response resp = userService.deleteUser(user.getId());
		/*
		 * List<Projects> projList = projectService.getProjectsByUser(user.getId()); //
		 * System.out.println("size=="+projList.size()); if (projList == null ||
		 * projList.size() == 0) { resp = userService.deleteUser(user.getId()); } else {
		 * resp = new Response(); resp.setStatusCode(0);
		 * resp.setStatusMessage("User is in assigned"); }
		 */
		return resp;
	}

	@RequestMapping(value = "/getAllUsers", produces = "application/json", method = RequestMethod.GET)
	public UserResponse getAllUsers() {
		applicationLog.debug("Came Into getAllUsers()");
		UserResponse resp = new UserResponse();
		List<User> usersList = userService.getAllUsers();
		if (usersList.size() != 0) {
			resp.setStatusCode(Constants.SUCCESS);
			resp.setStatusMessage(Constants.SUCCESSMSG);
			resp.setUserList(usersList);
		} else {
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			resp.setUserList(null);
		}
		return resp;
	}

	/*
	 * @RequestMapping(value = "/updateUser", method = RequestMethod.PUT, produces =
	 * "application/json", consumes = "application/json") public Response
	 * updateUser(@RequestBody UserRequest request) {
	 * 
	 * Response resp = null; boolean valid = true; Validations validations = new
	 * Validations(); if (!validations.nameValidation(request.getUsername())) valid
	 * = false; if (!validations.passwordValidation(request.getPassword())) valid =
	 * false; if (!validations.emailIdValidation(request.getEmailId())) valid =
	 * false; if (!validations.phoneNoValidation(request.getPhoneNum())) valid =
	 * false; if (request.getUserTypes() == null) valid = false; if
	 * (request.getDivisions() == null) valid = false;
	 * 
	 * if (valid) { User user = new User(); user.setId(request.getId());
	 * user.setUsername(request.getUsername());
	 * user.setPassword(request.getPassword());
	 * user.setEmailId(request.getEmailId());
	 * user.setFirstName(request.getFirstName());
	 * user.setLastName(request.getLastName());
	 * user.setPhoneNum(request.getPhoneNum());
	 * user.setUserImage(request.getUserImage());
	 * user.setStatus(request.getStatus());
	 * user.setUserTypes(request.getUserTypes());
	 * user.setDivisions(request.getDivisions()); resp =
	 * userService.updateUser(user);
	 * 
	 * List<Projects> projList = request.getProjList(); List<ProjectResources>
	 * resourceList = projectService.getProjectsByResource(request.getId());
	 * ProjectResources resource = null; if (projList != null && projList.size() !=
	 * 0) { for (Projects projects : projList) { boolean flag = true; for
	 * (ProjectResources res : resourceList) { if (projects.getId() ==
	 * res.getProjects().getId()) { System.out.println("projects.getId()=" +
	 * projects.getId() + ",res.getProjects().getId()=" +
	 * res.getProjects().getId()); flag = false; } } if (flag) {
	 * System.out.println("ProjectId=" + projects.getId()); resource = new
	 * ProjectResources(); resource.setUser(user); resource.setProjects(projects);
	 * resource.setStatus("Y"); userService.saveProjectResource(resource); } } }
	 * 
	 * } else { resp = new Response(); resp.setStatusCode(0);
	 * resp.setStatusMessage("Validation Errors"); } return resp; }
	 */

	@RequestMapping(value = "/updateUser", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public Response updateUserWithProjects(@RequestBody UserRequest request) {
		applicationLog.debug("Came Into updateUserWithProjects()");
		Response resp = null;
		boolean valid = true;
		Validations validations = new Validations();
		if (!validations.nameValidation(request.getUsername()))
			valid = false;
		if (!validations.passwordValidation(request.getPassword()))
			valid = false;
		if (!validations.emailIdValidation(request.getEmailId()))
			valid = false;
		if (!validations.phoneNoValidation(request.getPhoneNum()))
			valid = false;
		if (request.getUserTypes() == null)
			valid = false;
		if (request.getDivisions() == null)
			valid = false;

		if (valid) {
			User user = new User();
			user.setId(request.getId());
			user.setUsername(request.getUsername());
			user.setPassword(request.getPassword());
			user.setEmailId(request.getEmailId());
			user.setFirstName(request.getFirstName());
			user.setLastName(request.getLastName());
			user.setPhoneNum(request.getPhoneNum());
			user.setUserImage(request.getUserImage());
			user.setStatus(request.getStatus());
			user.setUserTypes(request.getUserTypes());

			if (request.getUserTypes().getId() == 1 || request.getUserTypes().getId() == 3)
				user.setDivisions(null);
			else
				user.setDivisions(request.getDivisions());
			
			resp = userService.updateUser(user);

			List<NatureOfWork> natureList = request.getNatureList();
			if (natureList != null && natureList.size() != 0 && resp.getStatusCode() == 1) {
				userService.deleteProjectResources(user.getId());
				for (NatureOfWork nature : natureList) {
					List<Projects> projList = nature.getProjList();
					ProjectResources resource = null;
					// if (projList != null && projList.size() != 0 && resp.getStatusCode() == 1) {
					for (Projects projects : projList) {
						System.out.println("ProjectId=" + projects.getId());
						resource = new ProjectResources();
						resource.setUser(user);
						resource.setNatureOfProject(nature);
						resource.setProjects(projects);
						resource.setStatus("Y");
						userService.saveProjectResource(resource);
						// }
					}
				}
			}
		} else {
			resp = new Response();
			resp.setStatusCode(0);
			resp.setStatusMessage("Validation Errors");
		}
		return resp;
	}
}
