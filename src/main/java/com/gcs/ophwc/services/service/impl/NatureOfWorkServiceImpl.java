package com.gcs.ophwc.services.service.impl;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.ophwc.services.persistance.dao.entity.NatureOfWork;
import com.gcs.ophwc.services.persistance.dao.entity.ProjectAmount;
import com.gcs.ophwc.services.persistance.dao.entity.Projects;
import com.gcs.ophwc.services.persistance.dao.interfaces.NatureOfWorkDao;
import com.gcs.ophwc.services.persistance.dao.interfaces.ProjectDao;
import com.gcs.ophwc.services.service.NatureOfWorkService;
import com.gcs.ophwc.services.util.Response;

@Service
public class NatureOfWorkServiceImpl implements NatureOfWorkService {

	private static final Logger applicationLog = Logger.getLogger("applicationLogger");
	private static final Logger errorLog = Logger.getLogger("errorLoggger");

	@Autowired
	private NatureOfWorkDao natureWorkDao;

	@Autowired
	private ProjectDao projectDao;

	@Override
	public List<NatureOfWork> getAllNatureOfWorks() {
		applicationLog.debug("Came into getAllNatureOfWorks()");
		return natureWorkDao.findAll();
	}

	@Override
	public NatureOfWork getNatureOfWork(Long id) {
		applicationLog.debug("Came into getNatureOfWork()");
		return natureWorkDao.findOne(id);
	}

	@Override
	public Response deleteNatureOfWork(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NatureOfWork> getAllNatureOfWorksByUser(Long id) {
		applicationLog.debug("Came into getAllNatureOfWorksByUser()");
		return natureWorkDao.getAllNatureOfWorksByUser(id);
	}

	@Override
	public List<NatureOfWork> getAllNatureOfWorksWithProjectCount(Long id) {
		applicationLog.debug("Came into getAllNatureOfWorksWithProjectCount()");
		List<NatureOfWork> natureList = natureWorkDao.findAll();
		List<Projects> prjList = projectDao.findAllProjectsAcendingNature(id);
		List<NatureOfWork> newList = new ArrayList<>();

		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year = localDate.getYear();
		int month = localDate.getMonthValue();
		// System.out.println("year=" + year + ",month=" + month);

		for (NatureOfWork nature : natureList) {
			int count = 0, handOverdProjectCount = 0, runningProjects = 0;
			int uploadedCount = 0, notUploadedCount = 0;
			double workAmount = 0, monthlyAmont = 0;

			for (Projects projects : prjList) {
				if (nature.getId() == projects.getNatureOfProject().getId()) {
					count++;
					if (projects.getIsHandovered().equalsIgnoreCase("Y"))
						handOverdProjectCount++;
					else {
						runningProjects++;
						if (projects.getImagesPaths() != null)
							uploadedCount++;
						else if (projects.getImagesPaths() == null)
							notUploadedCount++;
					}

					/*
					 * List<ProjectAmount> amountList =
					 * projectDao.getProjectAmountByProjectID(projects.getId()); for (ProjectAmount
					 * amount : amountList) { workAmount = workAmount +
					 * Double.valueOf(amount.getAmount()); if (amount.getFromDate().getYear() == new
					 * Date().getYear() && amount.getFromDate().getMonth() == new Date().getMonth()
					 * && amount.getToDate().getYear() == new Date().getYear() &&
					 * amount.getToDate().getMonth() == new Date().getMonth()) { monthlyAmont =
					 * monthlyAmont + Double.valueOf(amount.getAmount()); } }
					 */

				}

			}

			String mnthAmt = projectDao.getMonthlyBudgetByDivisionAndNature(id, nature.getId(), String.valueOf(month),
					String.valueOf(year));
			String ttlAmt = projectDao.getTotalBudgetByDivisionAndNature(id, nature.getId());
			// System.out.println("mnthAmt="+mnthAmt+",ttlAmt="+ttlAmt);
			monthlyAmont = mnthAmt != null ? Double.valueOf(mnthAmt) : 0.0;
			workAmount = ttlAmt != null ? Double.valueOf(ttlAmt) : 0.0;

			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
			String workAmountStr = decimalFormat.format(workAmount);
			String monthlyWorkAmountStr = decimalFormat.format(monthlyAmont);

			nature.setProjCount(runningProjects);
			nature.setHandOveredProjectsCount(handOverdProjectCount);
			nature.setRunningProjects(runningProjects);
			nature.setUploadedCount(uploadedCount);
			nature.setNotUploadedCount(notUploadedCount);
			nature.setWorkAmount(workAmountStr);
			nature.setMontlyWorkAmount(monthlyWorkAmountStr);
			newList.add(nature);
		}
		return newList;
	}

	@Override
	public List<NatureOfWork> getDivisionNatureOfWorksWithProjectCount(Long id, Long userId) {
		applicationLog.debug("Came into getDivisionNatureOfWorksWithProjectCount()");
		List<NatureOfWork> natureList = natureWorkDao.findAll();
		List<Projects> prjList = projectDao.findProjectsAcendingNatureWithUserAndDivision(id, userId);
		List<NatureOfWork> newList = new ArrayList<>();
		for (NatureOfWork nature : natureList) {
			int count = 0;
			for (Projects projects : prjList) {
				if (nature.getId() == projects.getNatureOfProject().getId()) {
					count++;
				}
			}
			nature.setProjCount(count);
			newList.add(nature);
		}
		return newList;
	}
}
