package com.gcs.ophwc.services.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.ophwc.services.persistance.dao.entity.ProjectAmount;
import com.gcs.ophwc.services.persistance.dao.entity.ProjectResources;
import com.gcs.ophwc.services.persistance.dao.entity.ProjectStatus;
import com.gcs.ophwc.services.persistance.dao.entity.Projects;
import com.gcs.ophwc.services.persistance.dao.interfaces.ProjectDao;
import com.gcs.ophwc.services.persistance.dao.interfaces.ProjectStatusDao;
import com.gcs.ophwc.services.persistance.dao.interfaces.SiteVisitDao;
import com.gcs.ophwc.services.service.ProjectService;
import com.gcs.ophwc.services.util.Constants;
import com.gcs.ophwc.services.util.Response;

import javassist.expr.NewArray;

@Service
public class ProjectServiceImpl implements ProjectService {
	
	private static final Logger applicationLog = Logger.getLogger("applicationLogger");
	private static final Logger errorLog = Logger.getLogger("errorLoggger");

	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private ProjectStatusDao statusDao;
	
	@Autowired
	private SiteVisitDao siteVisitDao;

	@Override
	@Transactional
	public void saveProjectStatus(ProjectStatus status) {
		statusDao.save(status);
	}

	@Override
	public ProjectStatus getProjectStatusById(Long id) {
		return statusDao.findOne(id);
	}

	@Override
	public List<ProjectStatus> getAllProjectStatus() {
		return statusDao.findAll();
	}

	@Override
	@Transactional
	public void deleteProjectStatus(Long id) {
		statusDao.deleteById(id);
	}

	@Override
	@Transactional
	public void updateProjectStatus(ProjectStatus status) {
		statusDao.update(status);
	}

	@Override
	@Transactional
	public Response saveProject(Projects proj) {
		applicationLog.debug("Came into saveProject()");
		Response resp=new Response();
		try {
			Projects project=projectDao.getProjectByProjectName(proj.getProjectName());
			if(project==null) {
			projectDao.save(proj);
			resp.setStatusCode(Constants.SUCCESS);
			resp.setStatusMessage(Constants.SUCCESSMSG);
			}else {
				resp.setStatusCode(Constants.FAILURE);
				resp.setStatusMessage("Project Already Existed");
			}
			return resp;
		} catch (Exception e) {
			errorLog.error("Error in saveProject()="+e.getMessage());
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			e.printStackTrace();
			return resp;
		}
	}

	@Override
	public Projects getProjectById(Long id) {
		applicationLog.debug("Came into saveProject()");
		return projectDao.getProjectById(id);
	}
	
	@Override
	public Projects getProjectImagesForWeb(Long id) {
		applicationLog.debug("Came into getProjectImagesForWeb()");
		return projectDao.getProjectImagesForWeb(id);
	}
	
	@Override
	public Projects getProjectByProjectName(String projName) {
		applicationLog.debug("Came into getProjectByProjectName()");
		return projectDao.getProjectByProjectName(projName);
	}
	
	@Override
	public Projects sap_getProjectByProjectName(String projName) {
		applicationLog.debug("Came into sap_getProjectByProjectName()");
		return projectDao.sap_getProjectsByName(projName);
	}

	@Override
	@Transactional
	public Response deleteProjectById(Long id) {
		applicationLog.debug("Came into deleteProjectById()");
		Response resp=new Response();
		try {
			projectDao.deleteById(id);
			siteVisitDao.deleteSiteVisitProjectId(id);
			resp.setStatusCode(Constants.SUCCESS);
			resp.setStatusMessage(Constants.SUCCESSMSG);
			return resp;
		} catch (Exception e) {
			errorLog.error("Error in deleteProjectById()="+e.getMessage());
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			e.printStackTrace();
			return resp;
		}
	}

	@Override
	@Transactional
	public Response updateProject(Projects proj) {
		applicationLog.debug("Came into updateProject()");
		Response resp=new Response();
		try {
			projectDao.update(proj);
			resp.setStatusCode(Constants.SUCCESS);
			resp.setStatusMessage(Constants.SUCCESSMSG);
			return resp;
		} catch (Exception e) {
			errorLog.error("Error in updateProject()="+e.getMessage());
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			e.printStackTrace();
			return resp;
		}
	}

	@Override
	public List<Projects> getAllProjects() {
		applicationLog.debug("Came into getAllProjects()");
		return projectDao.findAllProjects();
	} 
	
	@Override
	public List<Projects> getAllLatestProjects() {
		applicationLog.debug("Came into getAllLatestProjects()");
		return projectDao.findAllLatestProjects();
	} 
	@Override
	public List<Projects> getAllDivisionProjects(Long id) {
		applicationLog.debug("Came into getAllDivisionProjects()");
		return projectDao.findAllDivisionProjects(id);
	}

	@Override
	public List<Projects> getProjectsByUser(Long id) {
		applicationLog.debug("Came into getProjectsByUser()");
		return projectDao.getProjectsByUser(id);
	}

	@Override
	public List<Projects> getAllUserDivisionProjects(Long userId) {
		applicationLog.debug("Came into getAllUserDivisionProjects()");
		return projectDao.getProjectsByUserAndDivision(userId);
	}

	@Override
	public List<ProjectResources> getProjectsByResource(Long userId) {
		applicationLog.debug("Came into getProjectsByResource()");
		return projectDao.getProjectsByResource(userId);
	}
	
	@Override
	public List<ProjectResources> getNatureByUser(Long userId) {
		applicationLog.debug("Came into getNatureByUser()");
		return projectDao.getNatureByUser(userId);
	}
	
	@Override
	public List<ProjectResources> getProjectResourcesByNatureAndUser(Long userId,String natureIds) {
		applicationLog.debug("Came into getProjectResourcesByNatureAndUser()");
		return projectDao.getProjectResourcesByNatureAndUser(userId,natureIds);
	}
	
	@Override
	public List<ProjectResources> getAllProjectResources() {
		applicationLog.debug("Came into getAllProjectResources()");
		return projectDao.getProjectResources();
	}

	@Override
	public List<Projects> getProjectsOfNature(Long id) {
		applicationLog.debug("Came into getProjectsOfNature()");
		return projectDao.getProjectsOfNature(id);
	}

	@Override
	public List<Projects> getProjectsOfNatureAndUser(Long natureId, Long userId) {
		applicationLog.debug("Came into getProjectsOfNatureAndUser()");
		return projectDao.getProjectsOfNatureAndUser(natureId, userId);
	}

	@Override
	public List<Projects> getProjectsOfNatureAndDivision(String natureIds, Long divisonId) {
		applicationLog.debug("Came into getProjectsOfNatureAndDivision()");
		return projectDao.getProjectsOfNatureAndDivision(natureIds, divisonId);
	}
	
	@Override
	public List<Projects> getProjectsOfNatureAndDivisionForWeb(String natureIds, Long divisonId) {
		applicationLog.debug("Came into getProjectsOfNatureAndDivisionForWeb()");
		return projectDao.getProjectsOfNatureAndDivisionForWeb(natureIds, divisonId);
	}

	@Override
	public List<Projects> assignGetProjectsOfNatureAndDivision(String natureIds, Long divisonId,Long userId) {
		applicationLog.debug("Came into assignGetProjectsOfNatureAndDivision()");
		return projectDao.assignGetProjectsOfNatureAndDivision(natureIds, divisonId,userId);
	}

	@Override
	public List<Projects> getProjectsOfNature(String natureIds) {
		applicationLog.debug("Came into assignGetProjectsOfNatureAndDivision()");
		return projectDao.getProjectsOfNature(natureIds);
	}

	@Override
	public List<Projects> findAllProjectsAcendingNature(Long divId) {
		applicationLog.debug("Came into findAllProjectsAcendingNature()");
		return projectDao.findAllProjectsAcendingNature(divId);
	}
	
	@Override
	@Transactional
	public Response saveProjectAmount(ProjectAmount amount) {
		applicationLog.debug("Came into saveProject()");
		Response resp=new Response();
		try {
			Projects proj=new Projects();
			proj.setId(amount.getId());
			amount.setId(null);
			amount.setCreatedDate(new Date());
			amount.setProject(proj);
			System.out.println("amount=="+amount);
			projectDao.saveProjectAmount(amount);
			resp.setStatusCode(Constants.SUCCESS);
			resp.setStatusMessage(Constants.SUCCESSMSG);
			
			return resp;
		} catch (Exception e) {
			errorLog.error("Error in saveProjectAmount()="+e.getMessage());
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			e.printStackTrace();
			return resp;
		}
	}
	
	@Override
	@Transactional
	public Response updateProjectAmount(ProjectAmount amount) {
		applicationLog.debug("Came into saveProject()");
		Response resp=new Response();
		try {
			amount.setCreatedDate(new Date());
			projectDao.updateProjectAmount(amount);
			resp.setStatusCode(Constants.SUCCESS);
			resp.setStatusMessage(Constants.SUCCESSMSG);
			
			return resp;
		} catch (Exception e) {
			errorLog.error("Error in updateProjectAmount()="+e.getMessage());
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			e.printStackTrace();
			return resp;
		}
	}

	@Override
	public String getMonthlyBudgetByDivision(Long divId, String month, String year) {
		return projectDao.getMonthlyBudgetByDivision(divId, month, year);
	}

	@Override
	public String getMonthlyBudgetByDivisionAndNature(Long divId, Long natureId, String month, String year) {
		return projectDao.getMonthlyBudgetByDivisionAndNature(divId, natureId, month, year);
	}

	@Override
	public String getTotalBudgetByDivision(Long divId) {
		return projectDao.getTotalBudgetByDivision(divId);
	}

	@Override
	public String getTotalBudgetByDivisionAndNature(Long divId, Long natureId) {
		return projectDao.getTotalBudgetByDivisionAndNature(divId, natureId);
	}

	@Override
	public ProjectAmount getTotalBudgetByProjectId(Long projId) {
		return projectDao.getTotalBudgetByProjectId(projId);
	}

	@Override
	public List<ProjectAmount> getProjectAmountWithDatesAndDivision(Long divId, String fromDate, String toDate) {
		return projectDao.getProjectAmountWithDatesAndDivision(divId, fromDate, toDate);
	}

	@Override
	public List<ProjectAmount> getProjectAmountWithDatesAndDivisionAndNature(Long divId, Long natureId, String fromDate,
			String toDate) {
		return projectDao.getProjectAmountWithDatesAndDivisionAndNature(divId, natureId, fromDate, toDate);
	}

	@Override
	public List<ProjectAmount> getProjectAmountDetailsByProject(Long projId) {
		return projectDao.getProjectAmountByProjectID(projId);
	}

	@Override
	@Transactional
	public Response deleteBudgetById(Long id) {
		applicationLog.debug("Came into deleteBudgetById()");
		Response resp=new Response();
		try {
			projectDao.deleteBugetById(id);
			resp.setStatusCode(Constants.SUCCESS);
			resp.setStatusMessage(Constants.SUCCESSMSG);
			return resp;
		} catch (Exception e) {
			errorLog.error("Error in deleteProjectById()="+e.getMessage());
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			e.printStackTrace();
			return resp;
		}
	}
	
	@Override
	@Transactional
	public Response handOverProject(Long id) {
		applicationLog.debug("Came into deleteProjectById()");
		Response resp=new Response();
		try {
			projectDao.handOverProject(id);
			resp.setStatusCode(Constants.SUCCESS);
			resp.setStatusMessage(Constants.SUCCESSMSG);
			return resp;
		} catch (Exception e) {
			errorLog.error("Error in deleteProjectById()="+e.getMessage());
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			e.printStackTrace();
			return resp;
		}
	}
	
	@Override
	public List<Projects> getAllHandOveredProjects() {
		applicationLog.debug("Came into findAllProjectsAcendingNature()");
		return projectDao.getAllHandOveredProjects();
	}
}
