package com.gcs.ophwc.services.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFFont;
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
import com.gcs.ophwc.services.persistance.dao.entity.User;
import com.gcs.ophwc.services.persistance.dao.entity.UserTypes;
import com.gcs.ophwc.services.resources.request.UserRequest;
import com.gcs.ophwc.services.service.DivisionService;
import com.gcs.ophwc.services.service.UserService;
import com.gcs.ophwc.services.service.UserTypeService;
import com.gcs.ophwc.services.util.ExcelFileUtil;

@Controller
@RequestMapping("/user")
public class UserController {

	private static final Logger applicationLog = Logger.getLogger("applicationLogger");
	private static final Logger errorLog = Logger.getLogger("errorLoggger");

	@Autowired
	private SessionData sessionobj;

	@Autowired
	private UserService userService;

	@Autowired
	private UserTypeService userTypeService;

	@Autowired
	private DivisionService divisionService;

	@RequestMapping(value = "/userCreation", method = RequestMethod.GET)
	public String createUser(ModelMap model) {
		applicationLog.debug("came into createUser()");
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			UserRequest userRequest = new UserRequest();
			List<UserTypes> userTypeList = userTypeService.getAllUserTypes();
			List<Divisions> divisionsList = divisionService.getAllActiveDivisions();
			model.put("userTypeList", userTypeList);
			model.put("divisionsList", divisionsList);
			model.put("userRequest", userRequest);
			return "createUser";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "saveUser", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute UserRequest userRequest, BindingResult result, ModelMap model) {
		applicationLog.debug("came into saveUser()");
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			try {
				User user = new User();
				user.setUsername(userRequest.getUsername());
				user.setPassword(userRequest.getPassword());
				user.setFirstName(userRequest.getFirstName());
				user.setLastName(userRequest.getLastName());
				if (userRequest.getUserTypes().getId() != 2 || userRequest.getUserTypes().getId() != 4
						|| userRequest.getUserTypes().getId() != 7)
					user.setDivisions(null);
				else
					user.setDivisions(userRequest.getDivisions());

				user.setUserTypes(userRequest.getUserTypes());
				user.setEmailId(userRequest.getEmailId());
				user.setPhoneNum(userRequest.getPhoneNum());
				if (userRequest.getStatus() != null || userRequest.getStatus() != "")
					user.setStatus(userRequest.getStatus());
				else
					user.setStatus("N");
				userService.saveUser(user);
				return "redirect:/user/searchUsers";
			} catch (Exception e) {
				errorLog.error("error in saveUser=" + e.getMessage());
				e.printStackTrace();
				return "redirect:/";
			}
		} else
			return "redirect:/";
	}

	@RequestMapping(value = "searchUsers", method = RequestMethod.GET)
	public String usersList(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("came into usersList()");
			List<User> userList = userService.getAllUsers();
			model.put("usersList", userList);
			return "usersList";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "deActivate/{id}", method = RequestMethod.GET)
	public String deActivateUser(@PathVariable Long id, ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			userService.deActivateUser(id);
			return "redirect:/user/searchUsers";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "activate/{id}", method = RequestMethod.GET)
	public String activateUser(@PathVariable Long id, ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			userService.activateUser(id);
			return "redirect:/user/searchUsers";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "deleteUser/{id}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable Long id, ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("came into deleteUser()");
			userService.deleteUser(id);
			return "redirect:/user/searchUsers";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/editUser/{userId}", method = RequestMethod.GET)
	public String editUser(@PathVariable Long userId, ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("came into editUser()");
			User user = userService.getUserByUserId(userId);
			UserRequest userReq = new UserRequest();
			List<UserTypes> userTypeList = userTypeService.getAllUserTypes();
			List<Divisions> divisionsList = divisionService.getAllActiveDivisions();

			model.put("userTypeList", userTypeList);
			model.put("divisionsList", divisionsList);
			model.put("user", user);
			model.put("userReq", userReq);

			return "editUser";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "updateUser", method = RequestMethod.POST)
	public String updateUser(@ModelAttribute UserRequest userRequest, BindingResult result, ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("came into updateUser()");
			User user = new User();
			user.setId(userRequest.getId());
			user.setUsername(userRequest.getUsername());
			user.setPassword(userRequest.getPassword());
			user.setFirstName(userRequest.getFirstName());
			user.setLastName(userRequest.getLastName());

			if (userRequest.getDivisions() != null && userRequest.getDivisions().getId() != null)
				user.setDivisions(userRequest.getDivisions());
			else
				user.setDivisions(null);
			user.setUserTypes(userRequest.getUserTypes());
			user.setEmailId(userRequest.getEmailId());
			user.setPhoneNum(userRequest.getPhoneNum());
			if (userRequest.getStatus() != null && userRequest.getStatus() != "")
				user.setStatus(userRequest.getStatus());
			else
				user.setStatus("N");
			userService.updateUser(user);

			return "redirect:/user/searchUsers";
		} else
			return "redirect:/";
	}

	@ResponseBody
	@RequestMapping(value = "checkUserName")
	public String checkUserAvailablity(@RequestParam String userName, HttpServletRequest request,
									   HttpServletResponse response, ModelMap model) {
		String msg = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("came into checkUserAvailablity()");
			User user = userService.getUserByUserName(userName);
			if (user != null)
				msg = "<i style=color:red>User Already Existed</i>";
		}
		return msg;
	}

	@RequestMapping(value = "usersUpload", method = RequestMethod.GET)
	public String employeeBulkUploadData(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("came into employeeBulkUploadData()");
			return "usersBulkUpload";
		} else
			return "redirect:/";
	}

	@RequestMapping(value = "uploadUsers", method = RequestMethod.POST)
	public String uploadUsers(@RequestParam CommonsMultipartFile myFile, HttpSession session, ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came into uploadUsers()");
			try {
				int iniUserCount = userService.getAllLatestUsers().size();

				List<UserTypes> typeList = userTypeService.getAllUserTypes();
				List<Divisions> divisionList = divisionService.getAllActiveDivisions();

				File file1 = new File("/tmp/myt.xlsx");
				myFile.transferTo(file1);
				FileInputStream fileStream = new FileInputStream(file1);
				@SuppressWarnings("resource")
				XSSFWorkbook workbook = new XSSFWorkbook(fileStream);
				XSSFSheet ws = workbook.getSheetAt(0);
				ws.setForceFormulaRecalculation(true);

				int rowNum = ws.getLastRowNum() + 1;
				int colNum = ws.getRow(0).getLastCellNum();

				List<String> defaultHeders = new ArrayList<>();
				defaultHeders.add("User Name");
				defaultHeders.add("Password");
				defaultHeders.add("First Name");
				defaultHeders.add("Last Name");
				defaultHeders.add("Moblie Number");
				defaultHeders.add("Email");
				defaultHeders.add("User Type");
				defaultHeders.add("Division");

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
					return "usersBulkUpload";
				} else {
					int userName = -1, password = -1, fname = -1, lname = -1, mobileNo = -1, gmail = -1, userType = -1,
							division = -1, status = -1;
					// Read the headers first. Locate the ones you need
					rowHeader = ws.getRow(0);
					for (int j = 0; j < colNum; j++) {
						XSSFCell cell = rowHeader.getCell(j);
						String cellValue = cellToString(cell);
						if ("User Name".equalsIgnoreCase(cellValue) || "User_Name".equalsIgnoreCase(cellValue)) {
							userName = j;
						} else if ("Password".equalsIgnoreCase(cellValue)) {
							password = j;
						} else if ("First Name".equalsIgnoreCase(cellValue)
								|| "First_Name".equalsIgnoreCase(cellValue)) {
							fname = j;
						} else if ("Last Name".equalsIgnoreCase(cellValue) || "Last_Name".equalsIgnoreCase(cellValue)) {
							lname = j;
						} else if ("Moblie Number".equalsIgnoreCase(cellValue)
								|| "Moblie_Number".equalsIgnoreCase(cellValue)) {
							mobileNo = j;
						} else if ("Email".equalsIgnoreCase(cellValue)) {
							gmail = j;
						} else if ("User Type".equalsIgnoreCase(cellValue) || "User_Type".equalsIgnoreCase(cellValue)) {
							userType = j;
						} else if ("Division".equalsIgnoreCase(cellValue)) {
							division = j;
						} else if ("Status".equalsIgnoreCase(cellValue)) {
							status = j;
						}
					}

					for (int i = 1; i < rowNum; i++) {
						XSSFRow row = ws.getRow(i);
						User user = new User();
						if (row.getCell(userName) != null)
							user.setUsername(cellToString(row.getCell(userName)));
						if (row.getCell(password) != null)
							user.setPassword(cellToString(row.getCell(password)));
						if (row.getCell(fname) != null)
							user.setFirstName(cellToString(row.getCell(fname)));
						if (row.getCell(lname) != null)
							user.setLastName(cellToString(row.getCell(lname)));
						if (row.getCell(mobileNo) != null)
							user.setPhoneNum(cellToString(row.getCell(mobileNo)));
						if (row.getCell(gmail) != null)
							user.setEmailId(cellToString(row.getCell(gmail)));
						if (status > 0 && row.getCell(status) != null)
							user.setStatus(cellToString(row.getCell(status)));

						UserTypes type = null;
						if (row.getCell(userType) != null) {
							for (UserTypes userTypes : typeList) {
								if (userTypes.getTypeName().equalsIgnoreCase(cellToString(row.getCell(userType)))) {
									type = userTypes;
								}
							}
						}
						user.setUserTypes(type);

						Divisions div = null;
						if (row.getCell(division) != null) {
							for (Divisions divisions : divisionList) {
								if (divisions.getDivisionName().equalsIgnoreCase(cellToString(row.getCell(division)))) {
									div = divisions;
								}
							}
						}
						user.setDivisions(div);

						if (user.getUsername() != null && type != null) {
							userService.saveUser(user);
							applicationLog.debug("User Saved Successfully with User Name=" + user.getUsername());
						} else if (user.getUsername() == null) {
							model.put("user", "0");
							return "usersBulkUpload";
						} else if (type == null) {
							model.put("type", "0");
							model.put("userName", user.getUsername());
							return "usersBulkUpload";
						}
						fileStream.close();
					}

					int finUserCount = userService.getAllLatestUsers().size();
					if (iniUserCount == finUserCount) {
						model.put("userData", 0);
						return "usersBulkUpload";
					} else
						return "usersSuccess";
				}
			} catch (Exception e) {
				errorLog.error(e.getMessage());
				e.printStackTrace();
			}

			return "usersBulkUpload";
		} else
			return "redirect:/";

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

	@RequestMapping(value = "latestUsers", method = RequestMethod.GET)
	public String latestUsersList(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("came into latestUsersList()");
			List<User> userList = userService.getAllLatestUsers();
			model.put("usersList", userList);
			return "latestUsersList";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "sampleUsersFile", method = RequestMethod.GET)
	public String downloadSampleUsersFile(HttpServletResponse response) throws Exception {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came into downloadSampleUsersFile()");
			XSSFWorkbook workBook = new XSSFWorkbook();
			XSSFSheet sheet = workBook.createSheet("Users");
			XSSFRow headingRow = sheet.createRow(0);

			XSSFCellStyle style = workBook.createCellStyle();
			XSSFFont font = workBook.createFont();
			font.setFontName(HSSFFont.FONT_ARIAL);
			font.setFontHeightInPoints((short) 10);
			font.setBold(true);
			style.setFont(font);

			headingRow.createCell((short) 0).setCellValue("User Name");
			headingRow.createCell((short) 1).setCellValue("Password");
			headingRow.createCell((short) 2).setCellValue("First Name");
			headingRow.createCell((short) 3).setCellValue("Last Name");
			headingRow.createCell((short) 4).setCellValue("Moblie Number");
			headingRow.createCell((short) 5).setCellValue("Email");
			headingRow.createCell((short) 6).setCellValue("User Type");
			headingRow.createCell((short) 7).setCellValue("Division");

			for (int j = 0; j <= 7; j++)
				headingRow.getCell(j).setCellStyle(style);

			/*
			 * HSSFRow row = sheet.createRow(1); row.createCell((short)
			 * 0).setCellValue("TestOphwc1"); row.createCell((short)
			 * 1).setCellValue("Ophwc@1234"); row.createCell((short)
			 * 2).setCellValue("fname"); row.createCell((short) 3).setCellValue("lname");
			 * row.createCell((short) 4).setCellValue("1234567890"); row.createCell((short)
			 * 5).setCellValue("test@gmail.com"); row.createCell((short)
			 * 6).setCellValue("Division User"); row.createCell((short)
			 * 7).setCellValue("Bhubaneswar Division");
			 */

			String file = "/tmp/ophwc_users_sample.xlsx";
			try {
				FileOutputStream fos = new FileOutputStream(file);
				workBook.write(fos);
				ExcelFileUtil.downloadFile(file, response);
				fos.flush();
				fos.close();
				applicationLog.debug("Returning Jsp Page");
				return "usersBulkUpload";

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
		return "usersBulkUpload";

	}
}
