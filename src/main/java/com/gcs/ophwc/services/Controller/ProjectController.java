package com.gcs.ophwc.services.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.gcs.ophwc.services.persistance.dao.entity.Divisions;
import com.gcs.ophwc.services.persistance.dao.entity.NatureOfWork;
import com.gcs.ophwc.services.persistance.dao.entity.ProjectAmount;
import com.gcs.ophwc.services.persistance.dao.entity.ProjectResources;
import com.gcs.ophwc.services.persistance.dao.entity.ProjectStatus;
import com.gcs.ophwc.services.persistance.dao.entity.Projects;
import com.gcs.ophwc.services.persistance.dao.entity.SiteVisit;
import com.gcs.ophwc.services.persistance.dao.entity.User;
import com.gcs.ophwc.services.resources.request.ProjectStatusRequest;
import com.gcs.ophwc.services.resources.request.ProjectsRequest;
import com.gcs.ophwc.services.service.DivisionService;
import com.gcs.ophwc.services.service.NatureOfWorkService;
import com.gcs.ophwc.services.service.ProjectService;
import com.gcs.ophwc.services.service.UserService;
import com.gcs.ophwc.services.util.ExcelFileUtil;

@Controller
@RequestMapping("/project")
public class ProjectController {

	private static final Logger applicationLog = Logger.getLogger("applicationLogger");
	private static final Logger errorLog = Logger.getLogger("errorLoggger");

	@Autowired
	private SessionData sessionobj;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private NatureOfWorkService natureService;

	@Autowired
	private DivisionService divisionService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/statusCreation", method = RequestMethod.GET)
	public String createProjectStatus(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			ProjectStatusRequest statusRequest = new ProjectStatusRequest();
			model.put("statusRequest", statusRequest);
			return "createProjectStatus";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/saveProjectStatus", method = RequestMethod.GET)
	public String saveStatus(@ModelAttribute ProjectStatusRequest statusRequest, BindingResult result, ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			ProjectStatus status = new ProjectStatus();
			status.setStatus(statusRequest.getStatus());
			projectService.saveProjectStatus(status);
			return "redirect:/project/searchStatus";
		} else
			return "redirect:/";
	}

	@RequestMapping(value = "/searchStatus", method = RequestMethod.GET)
	public String statusList(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<ProjectStatus> statusList = projectService.getAllProjectStatus();
			model.put("statusList", statusList);
			return "statusList";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/projectCreation", method = RequestMethod.GET)
	public String createProject(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into createProject");
			ProjectsRequest req = new ProjectsRequest();
			List<Divisions> divisionsList = divisionService.getAllActiveDivisions();
			List<NatureOfWork> natureList = natureService.getAllNatureOfWorks();
			model.put("projReq", req);
			model.put("divisionsList", divisionsList);
			model.put("natureList", natureList);
			return "createProject";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/saveProject", method = RequestMethod.POST)
	public String saveProject(@ModelAttribute ProjectsRequest req, BindingResult result, ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into saveProject");
			Projects proj = new Projects();
			proj.setProjectName(req.getProjectName());
			proj.setProjectDefination(req.getProjectDefination());
			proj.setDivisions(req.getDivisions());
			proj.setNatureOfProject(req.getNatureOfProject());
			proj.setProjectLocation(req.getProjectLocation());
			proj.setClientName(req.getClientName());
			proj.setLatitude(req.getLatitude());
			proj.setLangitude(req.getLangitude());
			proj.setComments(req.getComments());
			proj.setIsHandovered("N");
			proj.setCreatedUser(sessionobj.getUser());
			projectService.saveProject(proj);
			return "redirect:/project/searchProjects";
		} else
			return "redirect:/";
	}

	@RequestMapping(value = "/searchProjects", method = RequestMethod.GET)
	public String projectsList(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into projectsList");
			List<Projects> projList = projectService.getAllProjects();
			model.put("projList", projList);
			return "projectsList";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/editProject/{projectId}", method = RequestMethod.GET)
	public String editProject(@PathVariable Long projectId, ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into editProject");
			Projects proj = projectService.getProjectById(projectId);
			ProjectsRequest req = new ProjectsRequest();
			List<Divisions> divisionsList = divisionService.getAllActiveDivisions();
			List<NatureOfWork> natureList = natureService.getAllNatureOfWorks();
			model.put("projReq", req);
			model.put("divisionsList", divisionsList);
			model.put("natureList", natureList);
			model.put("proj", proj);

			return "editProject";
		} else {
			return "redirect:/";
		}
	}

	@ResponseBody
	@RequestMapping(value = "checkProjName")
	public String getProjectAvailablity(@RequestParam String projName, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String msg = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into getProjectAvailablity");
			Projects proj = projectService.getProjectByProjectName(projName);
			if (proj != null)
				msg = "<i style=color:red>Project Already Existed</i>";
		}
		return msg;
	}

	@RequestMapping(value = "/deleteProject/{projectId}", method = RequestMethod.GET)
	public String deleteProject(ModelMap model, @PathVariable Long projectId) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into deleteProject");
			projectService.deleteProjectById(projectId);
			List<Projects> projList = projectService.getAllProjects();
			model.put("projList", projList);
			return "projectsList";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/updateProject", method = RequestMethod.POST)
	public String updateProject(@ModelAttribute ProjectsRequest req, BindingResult result, ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into updateProject");
			Projects proj = new Projects();
			proj.setId(req.getId());
			proj.setProjectName(req.getProjectName());
			proj.setProjectDefination(req.getProjectDefination());
			proj.setDivisions(req.getDivisions());
			proj.setNatureOfProject(req.getNatureOfProject());
			proj.setProjectLocation(req.getProjectLocation());
			proj.setClientName(req.getClientName());
			proj.setLatitude(req.getLatitude());
			proj.setLangitude(req.getLangitude());
			proj.setComments(req.getComments());
			proj.setCreatedUser(sessionobj.getUser());
			proj.setIsHandovered(req.getIsHandovered());
			proj.setStatus("Y");
			projectService.updateProject(proj);
			return "redirect:/project/searchProjects";
		} else
			return "redirect:/";
	}

	@RequestMapping(value = "/assignProject", method = RequestMethod.GET)
	public String assignProjects(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into assignProjects");
			List<User> usersList = userService.getAllDivisionUsers();
			usersList.addAll(userService.getAllClientUsers());
			List<NatureOfWork> natureList = natureService.getAllNatureOfWorks();
			model.put("usersList", usersList);
			model.put("natureList", natureList);
			return "assignProjectsToUser";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/assignNature/{userId}", method = RequestMethod.GET)
	public String assignNature(ModelMap model, @PathVariable Long userId) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into assignNature");
			List<NatureOfWork> natureList = natureService.getAllNatureOfWorks();
			List<ProjectResources> resList = projectService.getNatureByUser(userId);
			List<NatureOfWork> natureList1 = new ArrayList<>();
			NatureOfWork work = null;
			for (NatureOfWork nature : natureList) {
				work = nature;
				for (ProjectResources res : resList) {
					if (nature.getId().equals(res.getNatureOfProject().getId())) {
						work.setStatus("N");
						break;
					} else {
						work.setStatus("Y");
					}
				}
				natureList1.add(work);
			}

			model.put("natureList", natureList1);
			model.put("userId", userId);
			return "assignNatureToUser";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/getProjectsByNature", method = RequestMethod.POST)
	public String getProjectsByNature(ModelMap model, HttpServletRequest req) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into getProjectsByNature");
			Long userId = Long.parseLong(req.getParameter("userId"));
			User user = userService.getUserByUserId(userId);
			String natureIds = "0";
			if (req.getParameterValues("natureId") != null) {
				for (String id : req.getParameterValues("natureId")) {
					natureIds = natureIds + id + ",";
				}
				natureIds = natureIds.substring(1, natureIds.length() - 1);
			}
			List<Projects> projList = Objects.nonNull(user.getDivisions()) ? projectService.assignGetProjectsOfNatureAndDivision(natureIds,
					user.getDivisions().getId(), userId) : projectService.getProjectsOfNature(natureIds);
			List<ProjectResources> resList = projectService.getProjectResourcesByNatureAndUser(userId, natureIds);
			List<Projects> projectsList = new ArrayList<>();
			Projects proj = null;
			for (Projects projects : projList) {
				proj = projects;
				for (ProjectResources res : resList) {
					if (projects.getId().equals(res.getProjects().getId())) {
						proj.setStatus("N");
						break;
					} else
						proj.setStatus("Y");
				}
				projectsList.add(proj);
			}

			model.put("projList", projectsList);
			model.put("userId", userId);
			return "projectsByNature";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/assignNatureAndProjects", method = RequestMethod.POST)
	public String assignNatureAndProjects(ModelMap model, HttpServletRequest req,
			@RequestParam(value = "selected[]") List<Long> selected, @RequestParam(value = "user") Long userId) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into assignNatureAndProjects");

			User user = new User();
			user.setId(userId);
			List<Projects> projList = projectService.getAllProjects();
			ProjectResources res = null;
			userService.deleteProjectResources(userId);

			for (Long id : selected) {
				for (Projects proj : projList) {
					if (id.equals(proj.getId())) {
						res = new ProjectResources();
						res.setUser(user);
						res.setProjects(proj);
						res.setNatureOfProject(proj.getNatureOfProject());
						res.setStatus("Y");
						userService.saveProjectResource(res);
					}
				}
			}

			return "redirect:/project/assignProject";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/removeAllUserAssignments", method = RequestMethod.POST)
	public String removeAllUserAssignments(ModelMap model, HttpServletRequest req) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into removeAllUserAssignments");
			try {
				Long userId = Long.valueOf(req.getParameter("userId"));
				userService.deleteProjectResources(userId);
				return "redirect:/project/assignProject";
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return "redirect:/";
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/getAssignedProjectsForUser", method = RequestMethod.GET)
	public String getAssignedProjectsForUser(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into getAssignedProjectsForUser");
			List<User> userList = userService.getAllDivisionUsers();
			List<ProjectResources> resList = projectService.getAllProjectResources();
			List<User> userListAdd = new ArrayList<>();
			User userAdd = null;
			for (User user : userList) {
				userAdd = user;
				String projName = "";
				for (ProjectResources res : resList) {
					if (user.getId().equals(res.getUser().getId()) && !res.getProjects().getIsHandovered().equals("Y")) {
						projName = projName + ", " + res.getProjects().getProjectName();
					}
				}
				if (projName.length() > 1)
					projName = projName.substring(1);
				userAdd.setProjName(projName);
				userListAdd.add(userAdd);
			}
			model.put("usersList", userListAdd);
			return "userAssignedProjects";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/getAllNatures/{divId}", method = RequestMethod.GET)
	public String getAllNatureOfWorks(ModelMap model, @PathVariable Long divId) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into getAllNatureOfWorks");
			Divisions division = divisionService.getDivisionById(divId);
			List<NatureOfWork> natureList = natureService.getAllNatureOfWorksWithProjectCount(divId);
			model.put("natureList", natureList);
			model.put("divId", divId);
			model.put("division", division);
			return "natureForImages";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/getProjectsByNatureAndDivision/{natureId}/{divId}", method = RequestMethod.GET)
	public String getAllProjectsByNatureAndDivision(ModelMap model, @PathVariable Long natureId,
			@PathVariable Long divId, HttpServletRequest req) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into getAllProjectsByNatureAndDivision");
			// List<Projects> projList =
			// projectService.getProjectsOfNatureAndDivision(String.valueOf(natureId),
			// divId);

			List<Projects> projList = projectService.getProjectsOfNatureAndDivisionForWeb(String.valueOf(natureId),
					divId);
			model.put("projList", projList);
			return "projectsListForImages2";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/getImagesOfProject/{projId}", method = RequestMethod.GET)
	public String getImagesOfProject(ModelMap model, @PathVariable Long projId) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into getImagesOfProject");
			Projects proj = projectService.getProjectById(projId);
			List<SiteVisit> imagesList = new ArrayList<>();
			SiteVisit visit = null;
			if (proj.getImagesPaths() != null) {
				for (int i = 0; i < proj.getImagesPaths().length; i++) {
					visit = new SiteVisit();
					// String path="http://localhost:3030/"+proj.getImagesPaths()[i].substring(2);
					// /*Local Url*/
					// String path="http://183.82.125.183:9090/"+proj.getImagesPaths()[i]; /*Hyd Dev
					// URL*/
					// String path="http://164.100.141.155:8080/"+proj.getImagesPaths()[i];
					// /*Bhubaneswar Production URL*/
					String path = proj.getImagesPaths()[i]; /* Cloud Url */
					visit.setImagePath(path);
					imagesList.add(visit);
				}
			}
			model.put("imagesList", imagesList);
			model.put("projId", projId);
			return "imagesOfProject";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/hoMap/{divId}", method = RequestMethod.GET)
	public String hoUserMap(ModelMap model, @PathVariable Long divId) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into hoUserMap");
			List<Projects> projList = projectService.getAllDivisionProjects(divId);
			List<Projects> projectsList = new ArrayList<>();
			Projects proj = null;
			for (Projects projects : projList) {
				if (projects.getLatitude() != null && projects.getLangitude() != null) {
					proj = projects;
					projectsList.add(proj);
				}
			}
			model.put("projList", projectsList);
			return "hoUserMap";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/projectInMap/{projId}", method = RequestMethod.GET)
	public String projectInMap(ModelMap model, @PathVariable Long projId) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into hoUserMap");
			Projects proj = projectService.getProjectById(projId);
			List<Projects> projectsList = new ArrayList<>();
			projectsList.add(proj);
			model.put("projList", projectsList);
			return "hoUserMap";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "projectsUpload", method = RequestMethod.GET)
	public String projectsBulkUploadData(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			System.out.println("Came Into projectsBulkUploadData");
			applicationLog.debug("Came Into projectsBulkUploadData");
			return "projectsBulkUpload";
		} else
			return "redirect:/";
	}

	@RequestMapping(value = "uploadProjects", method = RequestMethod.POST)
	public String uploadProjects(@RequestParam CommonsMultipartFile myFile, HttpSession session, ModelMap model) {
		System.out.println("Out Side Session Check");
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			System.out.println("Came Into uploadProjects()");
			applicationLog.debug("Came into uploadProjects()");
			try {
				int iniPrjCount = projectService.getAllProjects().size();

				List<NatureOfWork> natureList = natureService.getAllNatureOfWorks();
				List<Divisions> divisionList = divisionService.getAllActiveDivisions();

				File file1 = new File("/tmp/myp.xlsx");
				myFile.transferTo(file1);
				FileInputStream fileStream = new FileInputStream(file1);
				@SuppressWarnings("resource")
				XSSFWorkbook workbook = new XSSFWorkbook(fileStream);
				XSSFSheet ws = workbook.getSheetAt(0);
				ws.setForceFormulaRecalculation(true);

				int rowNum = ws.getLastRowNum() + 1;
				int colNum = ws.getRow(0).getLastCellNum();

				List<String> defaultHeders = new ArrayList<>();
				defaultHeders.add("Project Name");
				defaultHeders.add("Project Defination");
				defaultHeders.add("Nature of Project");
				defaultHeders.add("Project Division");
				defaultHeders.add("Location");

				List<String> readHeaders = new ArrayList<>();
				XSSFRow rowHeader = ws.getRow(0);
				for (int j = 0; j < colNum; j++) {
					XSSFCell cell = rowHeader.getCell(j);
					String cellValue = cellToString(cell);
					readHeaders.add(cellValue);
				}

				String nfHeaders = "";

				for (String dh : defaultHeders) {
					int found = 0;
					for (String rh : readHeaders) {
						if (dh.equalsIgnoreCase(rh)) {
							found = 1;
							break;
						}
					}
					if (found == 0)
						nfHeaders = nfHeaders + dh + ",";
				}

				if (nfHeaders != "") {
					model.put("headerCheck", "0");
					model.put("headres", nfHeaders.substring(0, nfHeaders.length() - 1));
					return "projectsBulkUpload";
				} else {
					int projectName = -1, projectDefination = -1, natureOfProject = -1, projectDivision = -1,
							location = -1;
					// Read the headers first. Locate the ones you need
					rowHeader = ws.getRow(0);
					for (int j = 0; j < colNum; j++) {
						XSSFCell cell = rowHeader.getCell(j);
						String cellValue = cellToString(cell);
						if ("Project Name".equalsIgnoreCase(cellValue)) {
							projectName = j;
						} else if ("Project Defination".equalsIgnoreCase(cellValue)) {
							projectDefination = j;
						} else if ("Nature of Project".equalsIgnoreCase(cellValue)) {
							natureOfProject = j;
						} else if ("Project Division".equalsIgnoreCase(cellValue)) {
							projectDivision = j;
						} else if ("Location".equalsIgnoreCase(cellValue)) {
							location = j;
						}
					}

					for (int i = 1; i < rowNum; i++) {
						XSSFRow row = ws.getRow(i);
						Projects proj = new Projects();
						if (row.getCell(projectName) != null)
							proj.setProjectName(cellToString(row.getCell(projectName)));
						if (row.getCell(projectDefination) != null)
							proj.setProjectDefination(cellToString(row.getCell(projectDefination)));

						NatureOfWork nature = null;
						if (row.getCell(natureOfProject) != null) {
							for (NatureOfWork natureOfWork : natureList) {
								if (natureOfWork.getName()
										.equalsIgnoreCase(cellToString(row.getCell(natureOfProject)))) {
									nature = natureOfWork;
								}
							}
						}
						proj.setNatureOfProject(nature);

						Divisions division = null;
						if (row.getCell(projectDivision) != null) {
							for (Divisions divisions : divisionList) {
								if (divisions.getDivisionName()
										.equalsIgnoreCase(cellToString(row.getCell(projectDivision)))) {
									division = divisions;
								}
							}
						}
						proj.setDivisions(division);

						if (row.getCell(location) != null)
							proj.setProjectLocation(cellToString(row.getCell(location)));
						proj.setCreatedUser(sessionobj.getUser());

						if (proj.getProjectName() != null && nature != null && division != null) {
							projectService.saveProject(proj);
							applicationLog
									.debug("Project Checked Successfully with Project Name=" + proj.getProjectName());
							System.out
									.println("Project Checked Successfully with Project Name=" + proj.getProjectName());
						} else if (proj.getProjectName() == null) {
							model.put("proj", "0");
							return "projectsBulkUpload";

						} else if (nature == null) {
							model.put("nature", "0");
							model.put("projName", proj.getProjectName());
							return "projectsBulkUpload";

						} else if (division == null) {
							model.put("divsion", "0");
							model.put("projName", proj.getProjectName());
							return "projectsBulkUpload";
						}

						fileStream.close();
					}

					int finProjCount = projectService.getAllProjects().size();
					if (iniPrjCount == finProjCount) {
						model.put("uploadData", 0);
						return "projectsBulkUpload";
					} else
						return "projectsSuccess";
				}

			} catch (IOException e) {
				errorLog.error(e.getMessage());
				e.printStackTrace();
			}

			return "projectsBulkUpload";
		} else
			return "redirect:/";
	}

	@RequestMapping(value = "/latestProjects", method = RequestMethod.GET)
	public String latestProjectsList(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into latestProjectsList");
			// System.out.println("Came Into latestProjectsList");
			List<Projects> projList = projectService.getAllLatestProjects();
			model.put("projList", projList);
			return "latestProjectsList";
		} else {
			return "redirect:/";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getMonthlyBudgetForDivision")
	public String getMonthlyBudgetForDivision(@RequestParam Long divId, @RequestParam String date, ModelMap model)
			throws IOException {
		String actalAmnt = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			try {
				applicationLog.debug("Came Into getMonthlyBudgetForDivision in ProjectController");
				String[] monthYear = date.split("/");
				// System.out.println("divId=" + divId + ",,Month=" + monthYear[0] + ",,Year=" +
				// monthYear[1]);
				String amount = projectService.getMonthlyBudgetByDivision(divId, monthYear[0], monthYear[1]);
				if (amount == null)
					amount = "0.0";
				StringBuffer sbrObj = new StringBuffer();
				sbrObj.append("'").append(amount).append("'");
				if (sbrObj != null)
					actalAmnt = sbrObj.toString();

				// System.out.println("actalAmnt=" + actalAmnt);
				return actalAmnt;
			} catch (Exception e) {
				errorLog.error("Error getMonthlyBudgetForDivision in ProjectController=" + e.getMessage());
				e.printStackTrace();
			}

		}
		return actalAmnt;

	}

	@ResponseBody
	@RequestMapping(value = "/getMonthlyBudgetForNatureOfWork")
	public String getMonthlyBudgetForNatureOfWork(@RequestParam Long divId, @RequestParam Long natureId,
			@RequestParam String date, ModelMap model) throws IOException {
		String actalAmnt = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			try {
				applicationLog.debug("Came Into getMonthlyBudgetForNatureOfWork in ProjectController");
				String[] monthYear = date.split("/");
				// System.out.println("divId=" + divId + ",,natureId="+natureId+ ",,Month=" +
				// monthYear[0] + ",,Year=" + monthYear[1]);
				String amount = projectService.getMonthlyBudgetByDivisionAndNature(divId, natureId, monthYear[0],
						monthYear[1]);
				if (amount == null)
					amount = "0.0";
				StringBuffer sbrObj = new StringBuffer();
				sbrObj.append("'").append(amount).append("'");
				if (sbrObj != null)
					actalAmnt = sbrObj.toString();

				// System.out.println("actalAmnt=" + actalAmnt);
				return actalAmnt;
			} catch (Exception e) {
				errorLog.error("Error getMonthlyBudgetForNatureOfWork in ProjectController=" + e.getMessage());
				e.printStackTrace();
			}

		}
		return actalAmnt;

	}

	@RequestMapping(value = "/generatedBudgetReportWithDates", method = RequestMethod.GET)
	public String budgetReportWithDates(ModelMap model, @RequestParam Long divId, @RequestParam Date startdate,
			@RequestParam Date enddate, HttpServletRequest req) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into budgetReportWithDates");
			System.out.println("startdate=" + startdate + ",enddate=" + enddate + ",divId=" + divId);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			String strFromDate = formatter.format(startdate);
			String strToDate = formatter.format(enddate);
			List<ProjectAmount> amountList = projectService.getProjectAmountWithDatesAndDivision(divId, strFromDate,
					strToDate);
			System.out.println("amountList[]=" + amountList);
			model.put("amountList", amountList);
			Divisions division = divisionService.getDivisionById(divId);
			model.put("division", division);

			SimpleDateFormat expFormatter = new SimpleDateFormat("dd-MMM-yyyy");
			String expFromDate = expFormatter.format(startdate);
			String expToDate = expFormatter.format(enddate);
			model.put("startdate", expFromDate);
			model.put("enddate", expToDate);

			return "generatedBudgetReportWithDates";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/generatedNatureBudgetReportWithDates", method = RequestMethod.GET)
	public String generatedNatureBudgetReportWithDates(ModelMap model, @RequestParam Long divId,
			@RequestParam Long natureId, @RequestParam Date startdate, @RequestParam Date enddate,
			HttpServletRequest req) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into budgetReportWithDates");
			System.out.println("startdate=" + startdate + ",enddate=" + enddate + ",divId=" + divId);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			String strFromDate = formatter.format(startdate);
			String strToDate = formatter.format(enddate);
			List<ProjectAmount> amountList = projectService.getProjectAmountWithDatesAndDivisionAndNature(divId,
					natureId, strFromDate, strToDate);
			System.out.println("amountList[]=" + amountList);
			model.put("amountList", amountList);

			Divisions division = divisionService.getDivisionById(divId);
			model.put("division", division);

			NatureOfWork nature = natureService.getNatureOfWork(natureId);
			model.put("nature", nature);

			SimpleDateFormat expFormatter = new SimpleDateFormat("dd-MMM-yyyy");
			String expFromDate = expFormatter.format(startdate);
			String expToDate = expFormatter.format(enddate);
			model.put("startdate", expFromDate);
			model.put("enddate", expToDate);

			return "generatedNatureBudgetReportWithDates";
		} else {
			return "redirect:/";
		}
	}

	public static String cellToString(XSSFCell cell) {

		int type;
		Object result = null;
		type = cell.getCellType();

		switch (type) {

		case XSSFCell.CELL_TYPE_NUMERIC:
			result = BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();

			break;
		case XSSFCell.CELL_TYPE_STRING:
			result = cell.getStringCellValue();
			break;
		case XSSFCell.CELL_TYPE_BOOLEAN:
			result = cell.getBooleanCellValue();
			break;
		case XSSFCell.CELL_TYPE_BLANK:
			result = "";
			break;
		case XSSFCell.CELL_TYPE_FORMULA:
			result = cell.getCellFormula();
		}

		return result.toString();
	}

	@RequestMapping(value = "sampleProjectsFile", method = RequestMethod.GET)
	public String downloadSampleProjectsFile(HttpServletResponse response) throws Exception {
		System.out.println("Out Side Session Check");
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came into downloadSampleProjectsFile()");
			System.out.println("Came into downloadSampleProjectsFile()");
			XSSFWorkbook workBook = new XSSFWorkbook();
			XSSFSheet sheet = workBook.createSheet("Projects");
			XSSFRow headingRow = sheet.createRow(0);

			XSSFCellStyle style = workBook.createCellStyle();
			XSSFFont font = workBook.createFont();
			font.setFontName(HSSFFont.FONT_ARIAL);
			font.setFontHeightInPoints((short) 10);
			font.setBold(true);
			style.setFont(font);

			headingRow.createCell((short) 0).setCellValue("Project Name");
			headingRow.createCell((short) 1).setCellValue("Project Defination");
			headingRow.createCell((short) 2).setCellValue("Nature of Project");
			headingRow.createCell((short) 3).setCellValue("Project Division");
			headingRow.createCell((short) 4).setCellValue("Location");

			for (int j = 0; j <= 4; j++)
				headingRow.getCell(j).setCellStyle(style);

			/*
			 * HSSFRow row = sheet.createRow(1); row.createCell((short)
			 * 0).setCellValue("TestPol_0001"); row.createCell((short)
			 * 1).setCellValue("Test Police Project"); row.createCell((short)
			 * 2).setCellValue("Police Project"); row.createCell((short)
			 * 3).setCellValue("Bhubaneswar Division"); row.createCell((short)
			 * 4).setCellValue("Orissa");
			 */

			String file = "/tmp/ophwc_projects_sample.xlsx";
			try {
				FileOutputStream fos = new FileOutputStream(file);
				workBook.write(fos);
				ExcelFileUtil.downloadFile(file, response);
				fos.flush();
				fos.close();
				applicationLog.debug("Returning Jsp Page");
				System.out.println("Returning Jsp Page");
				return "projectsBulkUpload";

			} catch (FileNotFoundException e) {
				errorLog.error(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				errorLog.error(e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				errorLog.error(e.getMessage());
				e.printStackTrace();
			}
		} else
			return "redirect:/";
		return "projectsBulkUpload";
	}

	@RequestMapping(value = "/exportBudgetReportForDivision/{divId}/{startdate}/{enddate}", method = RequestMethod.GET)
	public String expoertBudgetReportForDivision(HttpServletResponse response, @PathVariable Long divId,
			@PathVariable Date startdate, @PathVariable Date enddate) throws Exception {
		System.out.println("Out Side Session Check");
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came into expoertBudgetReportForDivision()");
			System.out.println("Came into expoertBudgetReportForDivision()");
			HSSFWorkbook workBook = new HSSFWorkbook();
			HSSFSheet sheet = workBook.createSheet("Budget Report");
			Divisions division = divisionService.getDivisionById(divId);

			for (int columnIndex = 0; columnIndex <= 5; columnIndex++) {
				sheet.autoSizeColumn(columnIndex);
			}

			sheet.setColumnWidth(0, 10000);
			sheet.setColumnWidth(1, 10000);
			sheet.setColumnWidth(2, 10000);
			sheet.setColumnWidth(3, 10000);
			sheet.setColumnWidth(4, 10000);
			sheet.setColumnWidth(5, 10000);
			sheet.setHorizontallyCenter(true);
			sheet.setVerticallyCenter(true);
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);
			sheet.autoSizeColumn(4);
			sheet.autoSizeColumn(5);
			PrintSetup ps = sheet.getPrintSetup();

			sheet.setAutobreaks(true);

			ps.setFitHeight((short) 1);
			ps.setFitWidth((short) 1);

			Font font = workBook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setFontName("Calibri");
			font.setColor(IndexedColors.BLACK.getIndex());
			font.setBold(true);

			HSSFCellStyle style = workBook.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			style.setWrapText(true);
			style.setFont(font);

			HSSFRow row0 = sheet.createRow(0);
			HSSFCell ch0 = row0.createCell(0);
			style.setFillForegroundColor(IndexedColors.RED.getIndex());

			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
			ch0.setCellStyle(style);

			HSSFRow row1 = sheet.createRow(1);
			HSSFCell ch1 = row1.createCell(0);
			HSSFCellStyle style1 = workBook.createCellStyle();
			style1.setAlignment(CellStyle.ALIGN_CENTER);
			style1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			font.setFontHeightInPoints((short) 11);
			style1.setFont(font);
			ch1.setCellValue("OPHWC BUDGET REPORT FOR " + division.getDivisionName());
			ch1.setCellStyle(style1);
			sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 5));

			/*
			 * HSSFRow row2 = sheet.createRow(2); HSSFCell ch2 = row2.createCell(0);
			 * ch2.setCellStyle(style1);
			 * ch2.setCellValue("OPHWC BUDGET REPORT FOR "+division.getDivisionName());
			 * sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 2));
			 */

			style1.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			// style1.setFillForegroundColor(IndexedColors.valueOf("DarkOliveGreen").index);
			HSSFPalette palette = workBook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.GREEN.index, (byte) 170, (byte) 220, (byte) 170);
			palette.setColorAtIndex(HSSFColor.RED.index, (byte) 211, (byte) 207, (byte) 207);

			style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			HSSFCellStyle style2 = workBook.createCellStyle();
			style2.setAlignment(CellStyle.ALIGN_CENTER);
			style2.setWrapText(true);
			style2.setFont(font);
			HSSFRow headingRow = sheet.createRow(3);
			font.setFontHeightInPoints((short) 8);
			style2.setFont(font);
			style2.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			HSSFCell ch3 = headingRow.createCell((short) 0);
			ch3.setCellValue("PROJECT NAME");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 1);
			ch3.setCellValue("PROJECT DEFINITION");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 2);
			ch3.setCellValue("FROM DATE");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 3);
			ch3.setCellValue("TO DATE");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 4);
			ch3.setCellValue("AMOUNT");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 5);
			ch3.setCellValue("REMARKS");
			ch3.setCellStyle(style2);

			sheet.setFitToPage(true);

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			String strFromDate = formatter.format(startdate);
			String strToDate = formatter.format(enddate);

			List<ProjectAmount> amountList = projectService.getProjectAmountWithDatesAndDivision(divId, strFromDate,
					strToDate);
			int rowNo = 4;
			for (ProjectAmount amount : amountList) {
				HSSFRow row = sheet.createRow(rowNo);
				row.createCell((short) 0).setCellValue(amount.getProject().getProjectName());
				row.createCell((short) 1).setCellValue(amount.getProject().getProjectDefination());
				row.createCell((short) 2).setCellValue(amount.getStrFromDate());
				row.createCell((short) 3).setCellValue(amount.getStrToDate());
				row.createCell((short) 4).setCellValue(amount.getAmount());
				row.createCell((short) 5).setCellValue(amount.getComments());
				rowNo++;
			}

			String file = "ophwc_division_budget_report.xlsx";
			try {
				FileOutputStream fos = new FileOutputStream(file);
				workBook.write(fos);
				ExcelFileUtil.downloadFile(file, response);
				fos.flush();
				fos.close();
				applicationLog.debug("Returning Jsp Page");
				System.out.println("Returning Jsp Page");
				
				SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MMM-yyyy");
				String strFromDate1 = formatter1.format(startdate);
				String strToDate1 = formatter1.format(enddate);
				
				return "redirect:/project/generatedBudgetReportWithDates?divId=" + divId + "&startdate="
						+ strFromDate1 + "&enddate=" + strToDate1;

			} catch (FileNotFoundException e) {
				errorLog.error(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				errorLog.error(e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				errorLog.error(e.getMessage());
				e.printStackTrace();
			}
		} else
			return "redirect:/";
		return "redirect:/";
	}

	@RequestMapping(value = "/exportNatureBudgetReportForDivision/{divId}/{natureId}/{startdate}/{enddate}", method = RequestMethod.GET)
	public String expoertNatureBudgetReportForDivision(HttpServletResponse response, @PathVariable Long divId,
			@PathVariable Long natureId, @PathVariable Date startdate, @PathVariable Date enddate) throws Exception {
		System.out.println("Out Side Session Check");
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came into expoertBudgetReportForDivision()");
			System.out.println("Came into expoertBudgetReportForDivision()");
			HSSFWorkbook workBook = new HSSFWorkbook();
			HSSFSheet sheet = workBook.createSheet("Budget Report");

			Divisions division = divisionService.getDivisionById(divId);
			NatureOfWork nature = natureService.getNatureOfWork(natureId);

			for (int columnIndex = 0; columnIndex <= 5; columnIndex++) {
				sheet.autoSizeColumn(columnIndex);
			}

			sheet.setColumnWidth(0, 10000);
			sheet.setColumnWidth(1, 10000);
			sheet.setColumnWidth(2, 10000);
			sheet.setColumnWidth(3, 10000);
			sheet.setColumnWidth(4, 10000);
			sheet.setColumnWidth(5, 10000);
			sheet.setHorizontallyCenter(true);
			sheet.setVerticallyCenter(true);
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);
			sheet.autoSizeColumn(4);
			sheet.autoSizeColumn(5);
			PrintSetup ps = sheet.getPrintSetup();

			sheet.setAutobreaks(true);

			ps.setFitHeight((short) 1);
			ps.setFitWidth((short) 1);

			Font font = workBook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setFontName("Calibri");
			font.setColor(IndexedColors.BLACK.getIndex());
			font.setBold(true);

			HSSFCellStyle style = workBook.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			style.setWrapText(true);
			style.setFont(font);

			HSSFRow row0 = sheet.createRow(0);
			HSSFCell ch0 = row0.createCell(0);
			style.setFillForegroundColor(IndexedColors.RED.getIndex());

			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
			ch0.setCellStyle(style);

			HSSFRow row1 = sheet.createRow(1);
			HSSFCell ch1 = row1.createCell(0);
			HSSFCellStyle style1 = workBook.createCellStyle();
			style1.setAlignment(CellStyle.ALIGN_CENTER);
			style1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			font.setFontHeightInPoints((short) 11);
			style1.setFont(font);
			ch1.setCellValue("OPHWC BUDGET REPORT FOR " + nature.getName() + " IN " + division.getDivisionName());
			ch1.setCellStyle(style1);
			sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 5));

			/*
			 * HSSFRow row2 = sheet.createRow(2); HSSFCell ch2 = row2.createCell(0);
			 * ch2.setCellStyle(style1);
			 * ch2.setCellValue("OPHWC BUDGET REPORT FOR "+division.getDivisionName());
			 * sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 2));
			 */

			style1.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			// style1.setFillForegroundColor(IndexedColors.valueOf("DarkOliveGreen").index);
			HSSFPalette palette = workBook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.GREEN.index, (byte) 170, (byte) 220, (byte) 170);
			palette.setColorAtIndex(HSSFColor.RED.index, (byte) 211, (byte) 207, (byte) 207);

			style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			HSSFCellStyle style2 = workBook.createCellStyle();
			style2.setAlignment(CellStyle.ALIGN_CENTER);
			style2.setWrapText(true);
			style2.setFont(font);
			HSSFRow headingRow = sheet.createRow(3);
			font.setFontHeightInPoints((short) 8);
			style2.setFont(font);
			style2.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			HSSFCell ch3 = headingRow.createCell((short) 0);
			ch3.setCellValue("PROJECT NAME");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 1);
			ch3.setCellValue("PROJECT DEFINITION");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 2);
			ch3.setCellValue("FROM DATE");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 3);
			ch3.setCellValue("TO DATE");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 4);
			ch3.setCellValue("AMOUNT");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 5);
			ch3.setCellValue("REMARKS");
			ch3.setCellStyle(style2);

			sheet.setFitToPage(true);

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			String strFromDate = formatter.format(startdate);
			String strToDate = formatter.format(enddate);

			List<ProjectAmount> amountList = projectService.getProjectAmountWithDatesAndDivisionAndNature(divId,
					natureId, strFromDate, strToDate);
			int rowNo = 4;
			for (ProjectAmount amount : amountList) {
				HSSFRow row = sheet.createRow(rowNo);
				row.createCell((short) 0).setCellValue(amount.getProject().getProjectName());
				row.createCell((short) 1).setCellValue(amount.getProject().getProjectDefination());
				row.createCell((short) 2).setCellValue(amount.getStrFromDate());
				row.createCell((short) 3).setCellValue(amount.getStrToDate());
				row.createCell((short) 4).setCellValue(amount.getAmount());
				row.createCell((short) 5).setCellValue(amount.getComments());
				rowNo++;
			}

			String file = "ophwc_natureOfWork_budget_report.xlsx";
			try {
				FileOutputStream fos = new FileOutputStream(file);
				workBook.write(fos);
				ExcelFileUtil.downloadFile(file, response);
				fos.flush();
				fos.close();
				applicationLog.debug("Returning Jsp Page");
				System.out.println("Returning Jsp Page");
				
				SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MMM-yyyy");
				String strFromDate1 = formatter1.format(startdate);
				String strToDate1 = formatter1.format(enddate);
				
				return "redirect:/project/generatedNatureBudgetReportWithDates?divId=" + divId + "&natureId="
						+ natureId + "&startdate=" + strFromDate1 + "&enddate=" + strToDate1;

			} catch (FileNotFoundException e) {
				errorLog.error(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				errorLog.error(e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				errorLog.error(e.getMessage());
				e.printStackTrace();
			}
		} else
			return "redirect:/";
		return "redirect:/";
	}
	
	@RequestMapping(value = "/getBudgetDetails/{projectId}", method = RequestMethod.GET)
	public String getBudgetDetails(@PathVariable Long projectId, ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into editProject");
			Projects proj = projectService.getProjectById(projectId);
			List<ProjectAmount> amountList=projectService.getProjectAmountDetailsByProject(projectId);
			model.put("amountList", amountList);
			model.put("proj", proj);

			return "budgetDetailsForProject";
		} else {
			return "redirect:/";
		}
	}
	
	@RequestMapping(value = "/deleteBudget/{budgetId}/{projectId}", method = RequestMethod.GET)
	public String deleteBudget(ModelMap model, @PathVariable Long budgetId,@PathVariable Long projectId) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into deleteProject");
			projectService.deleteBudgetById(budgetId);
			List<Projects> projList = projectService.getAllProjects();
			model.put("projList", projList);
			return "redirect:/project/getBudgetDetails/"+projectId;
		} else {
			return "redirect:/";
		}
	}
	
	@RequestMapping(value = "/handOverProject/{projectId}", method = RequestMethod.GET)
	public String handOverProject(ModelMap model, @PathVariable Long projectId) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into handOverProject");
			projectService.handOverProject(projectId);
			List<Projects> projList = projectService.getAllProjects();
			model.put("projList", projList);
			return "projectsList";
		} else {
			return "redirect:/";
		}
	}
	
	@RequestMapping(value = "/handOveredProject", method = RequestMethod.GET)
	public String handOveredProjectList(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into projectsList");
			List<Projects> projList = projectService.getAllHandOveredProjects();
			model.put("projList", projList);
			return "handOveredProjectsList";
		} else {
			return "redirect:/";
		}
	}

}
