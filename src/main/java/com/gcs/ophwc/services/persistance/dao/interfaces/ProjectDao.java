package com.gcs.ophwc.services.persistance.dao.interfaces;

import java.util.List;

import com.gcs.ophwc.services.persistance.dao.api.IOphwcDaoSupport;
import com.gcs.ophwc.services.persistance.dao.entity.ProjectAmount;
import com.gcs.ophwc.services.persistance.dao.entity.ProjectResources;
import com.gcs.ophwc.services.persistance.dao.entity.Projects;

public interface ProjectDao extends IOphwcDaoSupport<Projects, Long> {

	public Projects updateSiteLocation(Long id, String lattitude, String langitude);

	public List<Projects> getProjectsByUser(Long id);

	public List<Projects> getProjectsByUserAndDivision(Long userId);

	public List<ProjectResources> getProjectsByResource(Long userId);

	public List<ProjectResources> getNatureByUser(Long userId);

	public List<ProjectResources> getProjectResourcesByNatureAndUser(Long userId, String natureIds);

	List<ProjectResources> getProjectResourcesByNature(String natureIds);

	public List<ProjectResources> getProjectResources();

	public List<Projects> getProjectsOfNature(Long id);

	public List<Projects> getProjectsOfNatureAndUser(Long natureId, Long userId);

	public List<Projects> getProjectsOfNatureAndDivision(String natureIds, Long divisionId);

	public List<Projects> getProjectsOfNatureAndDivisionForWeb(String natureIds, Long divisionId);

	public List<Projects> assignGetProjectsOfNatureAndDivision(String natureIds, Long divisionId, Long userId);

	public List<Projects> findAllProjects();

	public List<Projects> findAllLatestProjects();

	public List<Projects> findAllDivisionProjects(Long id);

	List<Projects> getProjectsOfNature(String natureIds);

	public Projects getProjectById(Long id);

	public Projects getProjectImagesForWeb(Long id);

	public Projects getProjectByProjectName(String projName);

	public Projects sap_getProjectsByName(String projName);

	public List<Projects> findAllProjectsAcendingNature(Long divId);

	public List<Projects> findProjectsAcendingNatureWithUserAndDivision(Long divId, Long UserId);

	public void saveProjectAmount(ProjectAmount amount);
	
	public void updateProjectAmount(ProjectAmount amount);

	public List<ProjectAmount> getProjectAmountByProjectID(Long id);

	public String getMonthlyBudgetByDivision(Long divId, String month, String year);

	public String getMonthlyBudgetByDivisionAndNature(Long divId, Long natureId, String month, String year);

	public String getTotalBudgetByDivision(Long divId);

	public String getTotalBudgetByDivisionAndNature(Long divId, Long natureId);
	
	public ProjectAmount getTotalBudgetByProjectId(Long projId);
	
	public List<ProjectAmount> getProjectAmountWithDatesAndDivision(Long divId, String fromDate, String toDate);
	
	public List<ProjectAmount> getProjectAmountWithDatesAndDivisionAndNature(Long divId, Long natureId, String fromDate,String toDate);
		
	public void deleteBugetById(Long id);
	
	public void handOverProject(Long id);
	
	public List<Projects> getAllHandOveredProjects();
	
	public List<Projects> getProjectsOfNatureAndDivisionForFloorStatus(Long divId, Long natureId);
}
