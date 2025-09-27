package com.gcs.ophwc.services.service;

import java.util.List;

import com.gcs.ophwc.services.persistance.dao.entity.ProjectAmount;
import com.gcs.ophwc.services.persistance.dao.entity.ProjectResources;
import com.gcs.ophwc.services.persistance.dao.entity.ProjectStatus;
import com.gcs.ophwc.services.persistance.dao.entity.Projects;
import com.gcs.ophwc.services.util.Response;

public interface ProjectService {

	public void saveProjectStatus(ProjectStatus status);
	public ProjectStatus getProjectStatusById(Long id);
	public List<ProjectStatus> getAllProjectStatus();
	public void deleteProjectStatus(Long id);
	public void updateProjectStatus(ProjectStatus status);
	
	public Response saveProject(Projects proj);
	public Projects getProjectById(Long id);
	public Response deleteProjectById(Long id);
	public Response updateProject(Projects proj);
	public List<Projects> getAllDivisionProjects(Long id);
	public List<Projects> getAllProjects();
	public List<Projects> getAllLatestProjects();
	public List<Projects> getAllUserDivisionProjects(Long id);
	
	public List<Projects> getProjectsOfNature(Long id);
	public List<Projects> getProjectsOfNatureAndUser(Long natureId,Long userId);
	public List<Projects> getProjectsOfNatureAndDivision(String natureIds,Long divisonId);
	public List<Projects> getProjectsOfNatureAndDivisionForWeb(String natureIds,Long divisonId);
	public List<Projects> assignGetProjectsOfNatureAndDivision(String natureIds,Long divisonId,Long userId);
	
	public List<Projects> getProjectsByUser(Long id);
	public List<ProjectResources> getProjectsByResource(Long userId);
	public List<ProjectResources> getNatureByUser(Long userId);
	public List<ProjectResources> getProjectResourcesByNatureAndUser(Long userId,String naturIds);
	public List<ProjectResources> getAllProjectResources();
	
	public Projects getProjectImagesForWeb(Long id);
	
	public Projects getProjectByProjectName(String projName);
	public Projects sap_getProjectByProjectName(String projName);

	List<Projects> getProjectsOfNature(String natureIds);

	public List<Projects> findAllProjectsAcendingNature(Long divId);
		
	public Response saveProjectAmount(ProjectAmount amount);
	
	public Response updateProjectAmount(ProjectAmount amount);
	
	public String getMonthlyBudgetByDivision(Long divId,String month,String year);
	
	public String getMonthlyBudgetByDivisionAndNature(Long divId,Long natureId,String month,String year);
	
	public String getTotalBudgetByDivision(Long divId);
	
	public String getTotalBudgetByDivisionAndNature(Long divId,Long natureId);
	
	public ProjectAmount getTotalBudgetByProjectId(Long projId);
	
	public List<ProjectAmount> getProjectAmountWithDatesAndDivision(Long divId,String fromDate,String toDate);
	
	public List<ProjectAmount> getProjectAmountWithDatesAndDivisionAndNature(Long divId,Long natureId,String fromDate,String toDate);
	
	public List<ProjectAmount> getProjectAmountDetailsByProject(Long projId);
	
	public Response deleteBudgetById(Long id);
	
	public Response handOverProject(Long id);
	
	public List<Projects> getAllHandOveredProjects();
	
}
