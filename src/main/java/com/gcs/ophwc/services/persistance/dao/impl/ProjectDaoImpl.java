package com.gcs.ophwc.services.persistance.dao.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.gcs.ophwc.services.persistance.dao.api.OphwcDaoSupport;
import com.gcs.ophwc.services.persistance.dao.entity.ProjectAmount;
import com.gcs.ophwc.services.persistance.dao.entity.ProjectResources;
import com.gcs.ophwc.services.persistance.dao.entity.Projects;
import com.gcs.ophwc.services.persistance.dao.entity.SiteVisit;
import com.gcs.ophwc.services.persistance.dao.interfaces.ProjectDao;

@SuppressWarnings("unchecked")
@Repository
public class ProjectDaoImpl extends OphwcDaoSupport<Projects, Long> implements ProjectDao {

	/**
	 * 
	 */
	private static final Logger applicationLog = Logger.getLogger("applicationLogger");
	private static final Logger errorLog = Logger.getLogger("errorLoggger");

	private static final long serialVersionUID = 1L;

	@Override
	public Projects updateSiteLocation(Long id, String lattitude, String langitude) {
		applicationLog.debug("Came Into updateSiteLocation");
		Query query = getEntityManager().createQuery(
				"update Projects set latitude='" + lattitude + "',langitude='" + langitude + "' where id=" + id);
		query.executeUpdate();
		return findOne(id);
	}

	@Override
	public List<Projects> getProjectsByUser(Long id) {
		applicationLog.debug("Came Into getProjectsByUser");
		List<Projects> projList = getEntityManager()
				.createQuery("from Projects where createdUser=" + id + " or assignTo=" + id).getResultList();
		return projList;
	}

	public List<Projects> findAllProjects() {
		applicationLog.debug("Came Into findAllProjects");
		List<Projects> projList = getEntityManager()
				.createQuery("from Projects where divisions.status='Y' ORDER BY id asc").getResultList();
		List<Projects> imageProjList = new ArrayList<>();
		Projects proj = null;
		for (Projects projects : projList) {
			String[] paths = null;
			proj = projects;
			int i = 0;
			List<SiteVisit> siteList = getEntityManager()
					.createQuery("from SiteVisit where projects=" + projects.getId() + " ORDER BY id desc")
					.getResultList();
			if (siteList != null && siteList.size() != 0) {
				paths = new String[siteList.size()];
				for (SiteVisit siteVisit : siteList) {
					// paths[i]=siteVisit.getImagePath().substring(28,siteVisit.getImagePath().length());
					paths[i] = siteVisit.getImagePath();
					i++;
				}

			}
			proj.setImagesPaths(paths);
			imageProjList.add(proj);
		}
		return imageProjList;
	}

	public List<Projects> findAllLatestProjects() {
		applicationLog.debug("Came Into findAllLatestProjects");
		List<Projects> projList = getEntityManager()
				.createQuery("from Projects where divisions.status='Y' ORDER BY id desc").getResultList();
		return projList;
	}

	public List<Projects> findAllDivisionProjects(Long id) {
		applicationLog.debug("Came Into findAllDivisionProjects");
		List<Projects> projList = getEntityManager()
				.createQuery("from Projects where divisions=" + new Long(id) + " and isHandovered!='Y' ORDER BY id asc")
				.getResultList();
		List<Projects> imageProjList = new ArrayList<>();
		Projects proj = null;
		for (Projects projects : projList) {
			String[] paths = null;
			proj = projects;
			int i = 0;
			List<SiteVisit> siteList = getEntityManager()
					.createQuery("from SiteVisit where projects=" + projects.getId() + " ORDER BY id desc")
					.getResultList();
			if (siteList != null && siteList.size() != 0) {
				paths = new String[siteList.size()];
				for (SiteVisit siteVisit : siteList) {
					paths[i] = siteVisit.getImagePath();
					i++;
				}

			}
			proj.setImagesPaths(paths);
			imageProjList.add(proj);
		}
		return imageProjList;
	}

	@Override
	public List<Projects> getProjectsByUserAndDivision(Long userId) {
		applicationLog.debug("Came Into getProjectsByUserAndDivision");
		List<ProjectResources> resList = getEntityManager()
				.createQuery("from ProjectResources where user=" + userId
						+ "and projects in(select id from Projects where isHandovered!='Y') ORDER BY projects asc")
				.getResultList();
		List<Projects> projList = new ArrayList<>();
		Projects proj = null;
		for (ProjectResources res : resList) {
			proj = new Projects();
			proj = res.getProjects();
			projList.add(proj);
		}

		List<Projects> imageProjList = new ArrayList<>();
		for (Projects projects : projList) {
			String[] paths = null;
			proj = projects;
			int i = 0;
			List<SiteVisit> siteList = getEntityManager()
					.createQuery("from SiteVisit where projects=" + projects.getId() + " ORDER BY id desc")
					.getResultList();
			if (siteList != null && siteList.size() != 0) {
				paths = new String[siteList.size()];
				for (SiteVisit siteVisit : siteList) {
					paths[i] = siteVisit.getImagePath();
					i++;
				}

			}
			proj.setImagesPaths(paths);
			imageProjList.add(proj);
		}
		return imageProjList;
	}

	@Override
	public List<ProjectResources> getProjectsByResource(Long userId) {
		applicationLog.debug("Came Into getProjectsByResource");
		List<ProjectResources> resourceList = getEntityManager()
				.createQuery("from ProjectResources where user=" + userId).getResultList();
		return resourceList;
	}

	@Override
	public List<ProjectResources> getNatureByUser(Long userId) {
		applicationLog.debug("Came Into getNatureByUser");
		List<ProjectResources> resourceList = getEntityManager().createQuery(
				"from ProjectResources where user=" + userId + " group by natureOfProject order by natureOfProject")
				.getResultList();
		return resourceList;
	}

	@Override
	public List<ProjectResources> getProjectResourcesByNatureAndUser(Long userId, String natureIds) {
		applicationLog.debug("Came Into getProjectResourcesByNatureAndUser");
		List<ProjectResources> resourceList = getEntityManager().createQuery("from ProjectResources where user="
				+ userId + " and natureOfProject in(" + natureIds + ") order by natureOfProject").getResultList();
		return resourceList;
	}

	@Override
	public List<ProjectResources> getProjectResourcesByNature(String natureIds) {
		applicationLog.debug("Came Into getProjectResourcesByNatureAndUser");
		List<ProjectResources> resourceList = getEntityManager().createQuery("from ProjectResources where natureOfProject in(" + natureIds + ") order by natureOfProject").getResultList();
		return resourceList;
	}

	@Override
	public List<ProjectResources> getProjectResources() {
		applicationLog.debug("Came Into getProjectResources");
		List<ProjectResources> resourceList = getEntityManager().createQuery("from ProjectResources order by user")
				.getResultList();
		return resourceList;
	}

	@Override
	public List<Projects> getProjectsOfNature(Long id) {
		applicationLog.debug("Came Into getProjectsOfNature");
		return getEntityManager().createQuery("from Projects where natureOfProject=" + id + " and divisions.status='Y'")
				.getResultList();
	}

	@Override
	public List<Projects> getProjectsOfNatureAndUser(Long natureId, Long userId) {
		applicationLog.debug("Came Into getProjectsOfNatureAndUser");
		List<ProjectResources> resList = getEntityManager().createQuery("from ProjectResources where user=" + userId
				+ " and natureOfProject=" + natureId + " ORDER BY projects asc").getResultList();
		List<Projects> projList = new ArrayList<>();
		Projects proj = null;
		for (ProjectResources res : resList) {
			if (!res.getProjects().getIsHandovered().equalsIgnoreCase("Y")) {
				proj = new Projects();
				proj = res.getProjects();
				projList.add(proj);
			}
		}

		List<Projects> imageProjList = new ArrayList<>();
		for (Projects projects : projList) {
			String[] paths = null;
			proj = projects;
			int i = 0;
			List<SiteVisit> siteList = getEntityManager()
					.createQuery("from SiteVisit where projects=" + projects.getId() + " ORDER BY id desc")
					.getResultList();
			if (siteList != null && siteList.size() != 0) {
				paths = new String[siteList.size()];
				for (SiteVisit siteVisit : siteList) {
					paths[i] = siteVisit.getImagePath();
					i++;
				}

			}
			proj.setImagesPaths(paths);
			imageProjList.add(proj);
		}
		return imageProjList;
	}

	@Override
	public List<Projects> getProjectsOfNatureAndDivision(String natureIds, Long divisionId) {
		applicationLog.debug("Came Into getProjectsOfNatureAndDivision");
		List<Projects> projList = getEntityManager().createQuery("from Projects where natureOfProject in(" + natureIds
				+ ") and divisions=" + divisionId + " and isHandovered!='Y'").getResultList();
		List<Projects> imageProjList = new ArrayList<>();
		Projects proj = null;
		for (Projects projects : projList) {
			String[] paths = null;
			proj = projects;
			int i = 0;
			List<SiteVisit> siteList = getEntityManager()
					.createQuery("from SiteVisit where projects=" + projects.getId() + " ORDER BY id desc")
					.getResultList();
			if (siteList != null && siteList.size() != 0) {
				paths = new String[siteList.size()];
				for (SiteVisit siteVisit : siteList) {
					paths[i] = siteVisit.getImagePath();
					i++;
				}

			}
			proj.setImagesPaths(paths);
			imageProjList.add(proj);
		}
		return imageProjList;
	}

	@Override
	public List<Projects> getProjectsOfNatureAndDivisionForWeb(String natureIds, Long divisionId) {
		applicationLog.debug("Came Into getProjectsOfNatureAndDivisionForWeb");

		List<ProjectResources> resList = getEntityManager().createQuery(
				"from ProjectResources where projects in(select id from Projects where natureOfProject in(" + natureIds
						+ ") and divisions=" + divisionId + " and isHandovered!='Y') order by projects desc")
				.getResultList();
		List<Projects> projList = getEntityManager().createQuery("from Projects where natureOfProject in(" + natureIds
				+ ") and divisions=" + divisionId + " and isHandovered!='Y' order by id desc").getResultList();

		List<Projects> userProjList = new ArrayList<>();
		for (Projects proj : projList) {
			String[] paths = null;
			int i = 0;
			for (ProjectResources res : resList) {
				if (proj.getId().equals(res.getProjects().getId())) {
					proj.setAssignTo(res.getUser());
				}
			}
			List<SiteVisit> siteList = getEntityManager()
					.createQuery("from SiteVisit where projects=" + proj.getId() + " ORDER BY id desc").getResultList();
			if (siteList != null && siteList.size() != 0) {
				paths = new String[siteList.size()];
				for (SiteVisit siteVisit : siteList) {
					paths[i] = siteVisit.getImagePath();
					i++;
				}
				proj.setLastUpdated(siteList.get(0).getCreatedDate());
			}

			double workAmnt = 0;
			String remarks = null;
			List<ProjectAmount> amountList = getEntityManager()
					.createQuery("from ProjectAmount where project =" + proj.getId() + " order by id asc")
					.getResultList();
			for (ProjectAmount amount : amountList) {
				if (amount.getAmount() != null && !amount.getAmount().isEmpty() && amount.getAmount() != "") {
					workAmnt = workAmnt + Double.valueOf(amount.getAmount());
				}
				remarks = amount.getComments();
			}

			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
			String workAmountStr = decimalFormat.format(workAmnt);

			proj.setImagesPaths(paths);
			proj.setAmount(workAmountStr);
			proj.setComments(remarks);
			userProjList.add(proj);
		}

		return userProjList;

	}

	@Override
	public List<Projects> assignGetProjectsOfNatureAndDivision(String natureIds, Long divisionId, Long userId) {
		applicationLog.debug("Came Into assignGetProjectsOfNatureAndDivision");
		return getEntityManager()
				.createQuery("from Projects where id not in(select projects from ProjectResources where user!=" + userId
						+ ") and natureOfProject in(" + natureIds + ") and divisions=" + divisionId
						+ " and isHandovered!='Y'")
				.getResultList();
	}

	@Override
	public List<Projects> getProjectsOfNature(String natureIds) {
		applicationLog.debug("Came Into getProjectsOfNature");
		List<ProjectResources> resourceList = getProjectResourcesByNature(natureIds);
		String resources = "";
		for (ProjectResources r : resourceList) {
			resources = String.format("%s, %s",resources, r.getProjects().getId());
		}
		resources = resources.substring(1);
		return getEntityManager()
				.createQuery("from Projects where id in(" + resources + ") and isHandovered!='Y'")
				.getResultList();
	}

	@Override
	public Projects getProjectById(Long id) {
		applicationLog.debug("Came Into getProjectById");
		Projects proj = getEntityManager().find(Projects.class, id);
		List<Projects> imageProjList = new ArrayList<>();
		String[] paths = null;
		int i = 0;
		List<SiteVisit> siteList = getEntityManager()
				.createQuery("from SiteVisit where projects=" + id + " ORDER BY id desc").getResultList();
		if (siteList != null && siteList.size() != 0) {
			paths = new String[siteList.size()];
			for (SiteVisit siteVisit : siteList) {
				paths[i] = siteVisit.getImagePath();
				i++;
			}

			proj.setImagesPaths(paths);
			imageProjList.add(proj);
		}
		return proj;
	}

	@Override
	public Projects getProjectImagesForWeb(Long id) {
		applicationLog.debug("Came Into getProjectImagesForWeb");
		Projects proj = getEntityManager().find(Projects.class, id);
		List<Projects> imageProjList = new ArrayList<>();
		String[] paths = null;
		int i = 0;
		List<SiteVisit> siteList = getEntityManager()
				.createQuery("from SiteVisit where projects=" + id + " ORDER BY id desc").getResultList();
		if (siteList != null && siteList.size() != 0) {
			paths = new String[siteList.size()];
			for (SiteVisit siteVisit : siteList) {
				paths[i] = siteVisit.getImagePath();
				i++;
			}

			proj.setImagesPaths(paths);
			imageProjList.add(proj);
		}
		return proj;
	}

	@Override
	public Projects getProjectByProjectName(String projName) {
		applicationLog.debug("Came Into getProjectByProjectName");
		Query query = getEntityManager().createQuery("from Projects where projectName='" + projName + "'");
		List<Projects> list = query.getResultList();
		if (list != null && list.size() != 0)
			return list.get(0);
		else
			return null;
	}

	@Override
	public Projects sap_getProjectsByName(String projName) {
		applicationLog.debug("Came Into sap_getProjectsByName");
		List<Projects> list = getEntityManager().createQuery("from Projects where projectName='" + projName + "'")
				.getResultList();
		Projects proj = null;
		if (list != null && list.size() != 0) {
			proj = list.get(0);
			List<ProjectResources> resList = getEntityManager()
					.createQuery("from ProjectResources where projects='" + proj.getId() + "'").getResultList();

			if (resList != null && resList.size() != 0)
				proj.setAssignTo(resList.get(0).getUser());
			List<Projects> imageProjList = new ArrayList<>();
			String[] paths = null;
			int i = 0;
			List<SiteVisit> siteList = getEntityManager()
					.createQuery("from SiteVisit where projects=" + proj.getId() + " ORDER BY id desc").getResultList();
			if (siteList != null && siteList.size() != 0) {
				paths = new String[siteList.size()];
				for (SiteVisit siteVisit : siteList) {
					paths[i] = siteVisit.getImagePath();
					i++;
				}

				proj.setImagesPaths(paths);
				imageProjList.add(proj);
			}
		}
		return proj;
	}

	@Override
	public List<Projects> findAllProjectsAcendingNature(Long divId) {
		applicationLog.debug("Came Into findAllProjectsAcendingNature");
		/*
		 * return getEntityManager() .createQuery("from Projects where divisions=" +
		 * divId + " order by natureOfProject asc") .getResultList();
		 */

		List<Projects> projList = getEntityManager()
				.createQuery("from Projects where divisions=" + divId + " order by natureOfProject asc")
				.getResultList();
		List<Projects> imageProjList = new ArrayList<>();
		Projects proj = null;
		for (Projects projects : projList) {
			String[] paths = null;
			proj = projects;
			int i = 0;
			List<SiteVisit> siteList = getEntityManager()
					.createQuery("from SiteVisit where projects=" + projects.getId() + " ORDER BY id desc")
					.getResultList();
			if (siteList != null && siteList.size() != 0) {
				paths = new String[siteList.size()];
				for (SiteVisit siteVisit : siteList) {
					paths[i] = siteVisit.getImagePath();
					i++;
				}

			}
			proj.setImagesPaths(paths);
			imageProjList.add(proj);
		}
		return imageProjList;
	}

	@Override
	public List<Projects> findProjectsAcendingNatureWithUserAndDivision(Long divId, Long userId) {
		applicationLog.debug("Came Into findProjectsAcendingNatureWithUserAndDivision");
		// TODO Auto-generated method stub
		return getEntityManager()
				.createQuery("from Projects where id in(select projects from ProjectResources where user=" + userId
						+ ") and divisions=" + divId + " and isHandovered!='Y' order by natureOfProject asc")
				.getResultList();
	}

	@Override
	public void saveProjectAmount(ProjectAmount amount) {
		applicationLog.debug("Came Into saveProjectAmount() in ProjectDaoImpl");
		getEntityManager().persist(amount);
	}

	@Override
	public void updateProjectAmount(ProjectAmount amount) {
		applicationLog.debug("Came Into saveProjectAmount() in ProjectDaoImpl");
		getEntityManager().merge(amount);
	}

	@Override
	public List<ProjectAmount> getProjectAmountByProjectID(Long id) {
		applicationLog.debug("Came Into getProjectAmountByProjectID() in ProjectDaoImpl");
		List<ProjectAmount> amountList = getEntityManager()
				.createQuery("from ProjectAmount where project =" + id + " order by id asc").getResultList();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd");
		for (ProjectAmount amount : amountList) {
			amount.setStrFromDate(formatter.format(amount.getFromDate()));
			amount.setStrToDate(formatter.format(amount.getToDate()));
		}

		return amountList;
	}

	@Override
	public String getMonthlyBudgetByDivision(Long divId, String month, String year) {
		applicationLog.debug("Came Into getMonthlyBudgetByDivision() in ProjectDaoImpl");
		Query sumQuery = getEntityManager().createQuery(
				"SELECT SUM(amount) FROM ProjectAmount WHERE project IN (SELECT id FROM Projects WHERE divisions="
						+ divId + " and isHandovered!='Y') AND year(fromDate)=" + year + " AND year(toDate)=" + year
						+ " and MONTH(fromDate)=" + month + " AND MONTH(toDate)=" + month);
		return (String) sumQuery.getResultList().get(0);
	}

	@Override
	public String getMonthlyBudgetByDivisionAndNature(Long divId, Long natureId, String month, String year) {
		applicationLog.debug("Came Into getMonthlyBudgetByDivisionAndNature() in ProjectDaoImpl");
		Query sumQuery = getEntityManager().createQuery(
				"SELECT SUM(amount) FROM ProjectAmount WHERE project IN (SELECT id FROM Projects WHERE divisions="
						+ divId + " AND natureOfProject=" + natureId + " and isHandovered!='Y') AND year(fromDate)="
						+ year + " AND year(toDate)=" + year + " and MONTH(fromDate)=" + month + " AND MONTH(toDate)="
						+ month);
		return (String) sumQuery.getResultList().get(0);
	}

	@Override
	public String getTotalBudgetByDivision(Long divId) {
		applicationLog.debug("Came Into getTotalBudgetByDivision() in ProjectDaoImpl");
		Query sumQuery = getEntityManager().createQuery(
				"SELECT SUM(amount) FROM ProjectAmount WHERE project IN (SELECT id FROM Projects WHERE divisions="
						+ divId + " and isHandovered!='Y')");
		return (String) sumQuery.getResultList().get(0);
	}

	@Override
	public String getTotalBudgetByDivisionAndNature(Long divId, Long natureId) {
		applicationLog.debug("Came Into getMonthlyBudgetByDivisionAndNature() in ProjectDaoImpl");
		Query sumQuery = getEntityManager().createQuery(
				"SELECT SUM(amount) FROM ProjectAmount WHERE project IN (SELECT id FROM Projects WHERE divisions="
						+ divId + " AND natureOfProject=" + natureId + " and isHandovered!='Y')");
		return (String) sumQuery.getResultList().get(0);
	}

	@Override
	public ProjectAmount getTotalBudgetByProjectId(Long projId) {
		applicationLog.debug("Came Into getTotalBudgetByProjectId() in ProjectDaoImpl");
		List<ProjectAmount> amountList = getEntityManager()
				.createQuery("FROM ProjectAmount WHERE project=" + projId + " order by id desc").getResultList();
		ProjectAmount amount = null;
		if (amountList != null && amountList.size() != 0) {
			amount = amountList.get(0);
			Query sumQuery = getEntityManager()
					.createQuery("SELECT SUM(amount) FROM ProjectAmount WHERE project=" + projId);
			String totalAmnt = (String) sumQuery.getResultList().get(0);
			if (totalAmnt == null)
				totalAmnt = "0";
			amount.setTotalAmnt(totalAmnt);
		} else
			amount = new ProjectAmount();
		return amount;
	}

	@Override
	public List<ProjectAmount> getProjectAmountWithDatesAndDivision(Long divId, String fromDate, String toDate) {
		applicationLog.debug("Came Into getProjectAmountWithDatesAndDivision() in ProjectDaoImpl");
		toDate = toDate + " 24:59:59";
		List<ProjectAmount> amountList = getEntityManager()
				.createQuery("from ProjectAmount where project in(select id from Projects where divisions=" + divId
						+ " and isHandovered!='Y') and fromDate >='" + fromDate + "'and toDate<='" + toDate
						+ "' order by Projects,fromDate asc")
				.getResultList();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd");
		for (ProjectAmount amount : amountList) {
			amount.setStrFromDate(formatter.format(amount.getFromDate()));
			amount.setStrToDate(formatter.format(amount.getToDate()));
		}
		return amountList;
	}

	@Override
	public List<ProjectAmount> getProjectAmountWithDatesAndDivisionAndNature(Long divId, Long natureId, String fromDate,
			String toDate) {
		applicationLog.debug("Came Into getProjectAmountWithDatesAndDivisionAndNature() in ProjectDaoImpl");
		toDate = toDate + " 24:59:59";
		List<ProjectAmount> amountList = getEntityManager()
				.createQuery("from ProjectAmount where project in(select id from Projects where divisions=" + divId
						+ " and natureOfProject=" + natureId + " and isHandovered!='Y') and fromDate >='" + fromDate
						+ "'and toDate<='" + toDate + "' order by Projects,fromDate asc")
				.getResultList();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd");
		for (ProjectAmount amount : amountList) {
			amount.setStrFromDate(formatter.format(amount.getFromDate()));
			amount.setStrToDate(formatter.format(amount.getToDate()));
		}
		return amountList;
	}

	@Override
	public void deleteBugetById(Long id) {
		applicationLog.debug("Came Into deleteBugetById() in ProjectDaoImpl");
		Query query = getEntityManager().createQuery("delete from ProjectAmount where id=" + id);
		query.executeUpdate();

	}

	@Override
	public void handOverProject(Long id) {
		applicationLog.debug("Came Into handOverProject() in ProjectDaoImpl");
		Query query = getEntityManager().createQuery("update Projects set isHandovered='Y' where id=" + id);
		query.executeUpdate();

	}

	@Override
	public List<Projects> getAllHandOveredProjects() {
		applicationLog.debug("Came Into getAllHandOveredProjects");
		return getEntityManager().createQuery("from Projects where isHandovered='Y'").getResultList();
	}

	@Override
	public List<Projects> getProjectsOfNatureAndDivisionForFloorStatus(Long divId, Long natureId) {
		List<Projects> projList = getEntityManager().createQuery("from Projects where natureOfProject =" + natureId
				+ " and divisions=" + divId + " and isHandovered!='Y' order by id asc").getResultList();
		return projList;
	}

}
