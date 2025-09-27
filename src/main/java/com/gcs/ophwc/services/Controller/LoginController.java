/**
 * @author Phaneendra Dasineni
 *
 */

package com.gcs.ophwc.services.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gcs.ophwc.services.persistance.dao.entity.Divisions;
import com.gcs.ophwc.services.persistance.dao.entity.NatureOfWork;
import com.gcs.ophwc.services.persistance.dao.entity.User;
import com.gcs.ophwc.services.resources.request.UserRequest;
import com.gcs.ophwc.services.service.DivisionService;
import com.gcs.ophwc.services.service.NatureOfWorkService;
import com.gcs.ophwc.services.service.ProjectService;
import com.gcs.ophwc.services.service.UserService;

@Controller
//@RequestMapping("/logging")
public class LoginController {

	private static final Logger applicationLog = Logger.getLogger("applicationLogger");
	private static final Logger errorLog = Logger.getLogger("errorLoggger");

	@Autowired
	private SessionData sessionobj;

	@Autowired
	private UserService userService;

	@Autowired
	private DivisionService divisionService;

	@Autowired
	private NatureOfWorkService natureService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	DriverManagerDataSource dataSource;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login(ModelMap model) {
		System.out.println("login");
		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(dataSource.getUrl(),
					dataSource.getUsername(), dataSource.getPassword());
			System.out.print("CONNECTED");

		} catch (Exception e) {
			e.printStackTrace();
		}
		UserRequest userRequest = new UserRequest();
		model.put("userRequest", userRequest);
		return "login";
	}

	@RequestMapping(value = "/validateUser", method = RequestMethod.POST)
	public String validateUser(ModelMap model, @ModelAttribute UserRequest request) {

		applicationLog.debug("Came into validateUser()");
		User user = userService.validateAdminUser(request.getUsername(), request.getPassword());
		if (user != null) {
			sessionobj.setValidLogin(true);
			sessionobj.setUser(user);
			sessionobj.setStatusMessage("Valid");
			if (user.getUserTypes().getTypeName().equalsIgnoreCase("Admin User"))
				return "redirect:/admin_dashboard";
			else if (user.getUserTypes().getTypeName().equalsIgnoreCase("HO User")) {
				return "redirect:/hoDashboard";
			} else if (user.getUserTypes().getTypeName().equalsIgnoreCase("Super Division User")) {
				return "redirect:/superDivisionDashboard";
			}

		} else {
			sessionobj.setStatusMessage("Please Enter Valid Credentials");
			return "login";
		}
		return null;
	}

	@RequestMapping(value = "/hoDashboard", method = RequestMethod.GET)
	public String hoDashboard(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.info("Came into hoDashboard");
			List<Divisions> divList = divisionService.getAllActiveDivisions();

			Date date = new Date();
			LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			int year = localDate.getYear();
			int month = localDate.getMonthValue();
			// System.out.println("year=" + year + ",month=" + month);

			for (Divisions division : divList) {
				double workAmount = 0, monthlyWorkAmount = 0;

				/*
				 * List<NatureOfWork>
				 * natureList=natureService.getAllNatureOfWorksWithProjectCount(division.getId()
				 * ); for (NatureOfWork nature : natureList) {
				 * workAmount=workAmount+nature.getWorkAmount();
				 * monthlyWorkAmount=monthlyWorkAmount+nature.getMontlyWorkAmount(); }
				 */

				String mnthAmt = projectService.getMonthlyBudgetByDivision(division.getId(), String.valueOf(month),
						String.valueOf(year));
				String ttlAmt = projectService.getTotalBudgetByDivision(division.getId());
				// System.out.println("mnthAmt="+mnthAmt+",ttlAmt="+ttlAmt);
				monthlyWorkAmount = mnthAmt != null ? Double.valueOf(mnthAmt) : 0.0;
				workAmount = ttlAmt != null ? Double.valueOf(ttlAmt) : 0.0;

				DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
				String workAmountStr = decimalFormat.format(workAmount);
				String monthlyWorkAmountStr = decimalFormat.format(monthlyWorkAmount);

				division.setWorkAmount(workAmountStr);
				division.setMonthlyWorkAmount(monthlyWorkAmountStr);
			}

			model.put("divList", divList);
			return "HO_Dashboard";
		} else
			return "redirect:/";
	}

	@RequestMapping(value = "/superDivisionDashboard", method = RequestMethod.GET)
	public String superDivisionDashboard(ModelMap model) {
		applicationLog.debug("Came into superDivisionDashboard");
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<NatureOfWork> natureList = natureService
					.getAllNatureOfWorksWithProjectCount(sessionobj.getUser().getDivisions().getId());
			model.put("natureList", natureList);
			model.put("divId", sessionobj.getUser().getDivisions().getId());
			return "natureForImages";
		} else
			return "redirect:/";
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(ModelMap model, HttpServletRequest req) {
		applicationLog.debug("Came into logout");
		sessionobj.setValidLogin(false);
		req.getSession(false).invalidate();
		return "redirect:/";
	}

	@RequestMapping(value = "/admin_dashboard", method = RequestMethod.GET)
	public String adminDashBoard(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin() == true) {
			applicationLog.debug("Came into adminDashBoard");
			return "Admin_Dashboard";
		} else
			return "redirect:/";
	}

}