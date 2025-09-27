package com.gcs.ophwc.services.resources;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcs.ophwc.services.Controller.SessionData;
import com.gcs.ophwc.services.persistance.dao.entity.NatureOfWork;
import com.gcs.ophwc.services.persistance.dao.entity.ProjectAmount;
import com.gcs.ophwc.services.persistance.dao.entity.ProjectStatus;
import com.gcs.ophwc.services.persistance.dao.entity.Projects;
import com.gcs.ophwc.services.resources.request.DivisionsRequest;
import com.gcs.ophwc.services.resources.request.NatureOfWorkRequest;
import com.gcs.ophwc.services.resources.request.ProjectStatusRequest;
import com.gcs.ophwc.services.resources.request.ProjectsRequest;
import com.gcs.ophwc.services.resources.request.UserRequest;
import com.gcs.ophwc.services.service.ProjectService;
import com.gcs.ophwc.services.util.Constants;
import com.gcs.ophwc.services.util.ProjectResponse;
import com.gcs.ophwc.services.util.Response;

@RestController
@RequestMapping("/projects")
public class ProjectResource {

	private static final Logger applicationLog = Logger.getLogger("applicationLogger");
	private static final Logger errorLog = Logger.getLogger("errorLoggger");

	@Autowired
	private SessionData sessionObj;

	@Autowired
	private ProjectService projectService;

	@RequestMapping(value = "/createProjectStatus", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public void createProjectStatus(@RequestBody ProjectStatusRequest request) {
		ProjectStatus status = new ProjectStatus();
		status.setStatus(request.getStatus());
		projectService.saveProjectStatus(status);
	}

	@RequestMapping(value = "/getProjectStatus", produces = "application/json", method = RequestMethod.GET)
	public ProjectStatus getProjectStatus(@RequestBody ProjectStatusRequest request) {
		ProjectStatus status = projectService.getProjectStatusById(request.getId());
		return status;
	}

	@RequestMapping(value = "/deleteProjectStatus", produces = "application/json", method = RequestMethod.DELETE)
	public void deleteProjectStatus(@RequestBody ProjectStatusRequest request) {
		projectService.deleteProjectStatus(request.getId());
	}

	@RequestMapping(value = "/getAllProjectStatuses", produces = "application/json", method = RequestMethod.GET)
	public List<ProjectStatus> getAllProjectStatuses() {
		List<ProjectStatus> statusList = projectService.getAllProjectStatus();
		return statusList;
	}

	@RequestMapping(value = "/updateProjectStatus", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public void updateProjectStatus(@RequestBody ProjectStatusRequest request) {
		ProjectStatus status = new ProjectStatus();
		status.setId(request.getId());
		status.setStatus(request.getStatus());
		projectService.updateProjectStatus(status);
	}

	@RequestMapping(value = "/createProject", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Response createproject(@RequestBody ProjectsRequest request) {
		applicationLog.debug("Came Into createproject()");
		Response resp = null;
		boolean valid = true;
		if (request.getProjectName() == null || request.getProjectName() == "") {
			System.out.println("Project Name Must not be empty");
			valid = false;
		}
		if (request.getDivisions() == null) {
			System.out.println("Division is Manadatory for the project");
			valid = false;
		}

		if (valid) {
			Projects proj = new Projects();
			proj.setProjectName(request.getProjectName());
			proj.setProjectDefination(request.getProjectDefination());
			proj.setClientName(request.getClientName());
			proj.setProjectLocation(request.getProjectLocation());
			proj.setDivisions(request.getDivisions());
			proj.setNatureOfProject(request.getNatureOfProject());
			proj.setLatitude(request.getLatitude());
			proj.setLangitude(request.getLangitude());
			proj.setStatus(request.getStatus());
			proj.setProjectLogo(request.getProjectLogo());
			proj.setComments(request.getComments());
			proj.setCreatedUser(request.getCreatedUser());
			proj.setAssignTo(request.getAssignTo());
			resp = projectService.saveProject(proj);
		} else {
			resp = new Response();
			resp.setStatusCode(0);
			resp.setStatusMessage("Validation Errors");
		}
		return resp;
	}

	@RequestMapping(value = "/getProjectById", produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
	public Projects getProjectById(@RequestBody ProjectsRequest req) {
		applicationLog.debug("Came Into getProjectById()");
		Projects proj = projectService.getProjectById(req.getId());
		return proj;
	}

	@RequestMapping(value = "/deleteProjectById", produces = "application/json", consumes = "application/json", method = RequestMethod.DELETE)
	public Response deleteProject(@RequestBody ProjectsRequest req) {
		applicationLog.debug("Came Into deleteProject()");
		Response resp = projectService.deleteProjectById(req.getId());
		return resp;
	}

	@RequestMapping(value = "/getAllProjects", produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
	public ProjectResponse getAllDivisionProjects(@RequestBody DivisionsRequest request) {
		applicationLog.debug("Came Into getAllDivisionProjects()");
		ProjectResponse resp = new ProjectResponse();
		List<Projects> projectList = projectService.getAllDivisionProjects(request.getId());
		if (projectList.size() != 0) {
			resp.setStatusCode(Constants.SUCCESS);
			resp.setStatusMessage(Constants.SUCCESSMSG);
			resp.setPrjectList(projectList);
		} else {
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			resp.setPrjectList(null);
		}
		return resp;
	}

	@RequestMapping(value = "/getProjects", produces = "application/json", method = RequestMethod.GET)
	public ProjectResponse getProjects() {
		applicationLog.debug("Came Into getProjects()");
		ProjectResponse resp = new ProjectResponse();
		List<Projects> projectList = projectService.getAllProjects();
		if (projectList.size() != 0) {
			resp.setStatusCode(Constants.SUCCESS);
			resp.setStatusMessage(Constants.SUCCESSMSG);
			resp.setPrjectList(projectList);
		} else {
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			resp.setPrjectList(null);
		}
		return resp;
	}

	@RequestMapping(value = "/getAllUserProjects", produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
	public ProjectResponse getAllUserDivisionProjects(@RequestBody UserRequest request) {
		applicationLog.debug("Came Into getAllUserDivisionProjects()");
		ProjectResponse resp = new ProjectResponse();
		List<Projects> projectList = projectService.getAllUserDivisionProjects(request.getId());
		if (projectList.size() != 0) {
			resp.setStatusCode(Constants.SUCCESS);
			resp.setStatusMessage(Constants.SUCCESSMSG);
			resp.setPrjectList(projectList);
		} else {
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			resp.setPrjectList(null);
		}
		return resp;
	}

	@RequestMapping(value = "/getProjectsByNature", produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
	public ProjectResponse getProjectsByNature(@RequestBody NatureOfWorkRequest request) {
		applicationLog.debug("Came Into getProjectsByNature()");
		ProjectResponse resp = new ProjectResponse();
		List<Projects> projectList = projectService.getProjectsOfNature(request.getId());
		if (projectList.size() != 0) {
			resp.setStatusCode(Constants.SUCCESS);
			resp.setStatusMessage(Constants.SUCCESSMSG);
			resp.setPrjectList(projectList);
		} else {
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			resp.setPrjectList(null);
		}
		return resp;
	}

	@RequestMapping(value = "/getProjectsByNatureAndUser", produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
	public ProjectResponse getProjectsByNatureAndUser(@RequestBody NatureOfWorkRequest req) {
		applicationLog.debug("Came Into getProjectsByNatureAndUser()");
		ProjectResponse resp = new ProjectResponse();
		List<Projects> projectList = projectService.getProjectsOfNatureAndUser(req.getId(), req.getUserId());
		if (projectList.size() != 0) {
			resp.setStatusCode(Constants.SUCCESS);
			resp.setStatusMessage(Constants.SUCCESSMSG);
			resp.setPrjectList(projectList);
		} else {
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			resp.setPrjectList(null);
		}
		return resp;
	}

	@RequestMapping(value = "/getProjectsByDivisionAndNature", produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
	public ProjectResponse getProjectsByDivisionAndNature(@RequestBody DivisionsRequest req) {
		applicationLog.debug("Came Into getProjectsByDivisionAndNature()");
		String natureIds = "0";
		List<NatureOfWork> natureList = req.getNatureOfProject();
		for (NatureOfWork nature : natureList) {
			natureIds = natureIds + nature.getId() + ",";
		}
		natureIds = natureIds.substring(1, natureIds.length() - 1);
		// System.out.println("natureIds=="+natureIds);
		ProjectResponse resp = new ProjectResponse();
		List<Projects> projectList = projectService.getProjectsOfNatureAndDivision(natureIds, req.getId());
		if (projectList.size() != 0) {
			resp.setStatusCode(Constants.SUCCESS);
			resp.setStatusMessage(Constants.SUCCESSMSG);
			resp.setPrjectList(projectList);
		} else {
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			resp.setPrjectList(null);
		}
		return resp;
	}

	@RequestMapping(value = "/assignGetProjectsByDivisionAndNature", produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
	public ProjectResponse assignGetProjectsByDivisionAndNature(@RequestBody DivisionsRequest req) {
		applicationLog.debug("Came Into assignGetProjectsByDivisionAndNature()");
		String natureIds = "0";
		List<NatureOfWork> natureList = req.getNatureOfProject();
		for (NatureOfWork nature : natureList) {
			natureIds = natureIds + nature.getId() + ",";
		}
		natureIds = natureIds.substring(1, natureIds.length() - 1);
		// System.out.println("natureIds=="+natureIds);
		ProjectResponse resp = new ProjectResponse();
		List<Projects> projectList = projectService.assignGetProjectsOfNatureAndDivision(natureIds, req.getId(),
				req.getUserId());
		if (projectList.size() != 0) {
			resp.setStatusCode(Constants.SUCCESS);
			resp.setStatusMessage(Constants.SUCCESSMSG);
			resp.setPrjectList(projectList);
		} else {
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			resp.setPrjectList(null);
		}
		return resp;
	}

	@RequestMapping(value = "/updateProject", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public Response updateProject(@RequestBody ProjectsRequest request) {
		applicationLog.debug("Came Into updateProject()");
		Response resp = null;
		boolean valid = true;
		if (request.getProjectName() == null || request.getProjectName() == "") {
			valid = false;
		}
		if (request.getDivisions() == null) {
			valid = false;
		}

		if (valid) {
			Projects proj = new Projects();
			proj.setId(request.getId());
			proj.setProjectName(request.getProjectName());
			proj.setProjectDefination(request.getProjectDefination());
			proj.setClientName(request.getClientName());
			proj.setProjectLocation(request.getProjectLocation());
			proj.setDivisions(request.getDivisions());
			proj.setNatureOfProject(request.getNatureOfProject());
			proj.setLatitude(request.getLatitude());
			proj.setLangitude(request.getLangitude());
			proj.setStatus(request.getStatus());
			proj.setProjectLogo(request.getProjectLogo());
			proj.setComments(request.getComments());
			proj.setCreatedUser(request.getCreatedUser());
			proj.setAssignTo(request.getAssignTo());
			resp = projectService.updateProject(proj);
		} else {
			resp = new Response();
			resp.setStatusCode(0);
			resp.setStatusMessage("Validation Errors");
		}
		return resp;
	}

	@RequestMapping(value = "/updateProjectLocation", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public Response updateProjectLocation(@RequestBody ProjectsRequest request) {
		applicationLog.debug("Came Into updateProjectLocation()");
		Projects proj = new Projects();
		proj.setId(request.getId());
		proj.setProjectName(request.getProjectName());
		proj.setProjectDefination(request.getProjectDefination());
		proj.setClientName(request.getClientName());
		proj.setProjectLocation(request.getProjectLocation());
		proj.setDivisions(request.getDivisions());
		proj.setNatureOfProject(request.getNatureOfProject());
		proj.setLatitude(request.getLatitude());
		proj.setLangitude(request.getLangitude());
		proj.setStatus(request.getStatus());
		proj.setProjectLogo(request.getProjectLogo());
		proj.setComments(request.getComments());
		proj.setCreatedUser(request.getCreatedUser());
		proj.setAssignTo(request.getAssignTo());
		Response resp = projectService.updateProject(proj);
		return resp;
	}

	@RequestMapping(value = "/sap_getProjectsByName", produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
	public ProjectResponse getProjectsByName(@RequestBody ProjectsRequest request) {
		applicationLog.debug("Came Into getProjectsByName()");
		ProjectResponse resp = new ProjectResponse();
		Projects project = projectService.sap_getProjectByProjectName(request.getProjectName());
		List<Projects> projectList = null;
		if (project != null) {
			projectList = new ArrayList<>();
			projectList.add(project);
			resp.setStatusCode(Constants.SUCCESS);
			resp.setStatusMessage(Constants.SUCCESSMSG);
			resp.setPrjectList(projectList);
		} else {
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			resp.setPrjectList(null);
		}
		return resp;
	}

	@RequestMapping(value = "/addAmmount", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Response addAmmount(@RequestBody ProjectAmount request) {
		applicationLog.debug("Came Into addAmmount() in ProjectResource");
		Response resp = projectService.saveProjectAmount(request);
		return resp;
	}
	
	@RequestMapping(value = "/updateAmmount", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public Response updateAmmount(@RequestBody ProjectAmount request) {
		applicationLog.debug("Came Into updateAmmount() in ProjectResource");
		Response resp = projectService.updateProjectAmount(request);
		return resp;
	}
	
	@RequestMapping(value = "/getTotalAmntOfProject", produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
	public ProjectAmount getProjectAmountById(@RequestBody ProjectsRequest req) {
		applicationLog.debug("Came Into getProjectById()");
		ProjectAmount amnt = projectService.getTotalBudgetByProjectId(req.getId());
		return amnt;
	}
}
