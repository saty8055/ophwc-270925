package com.gcs.ophwc.services.Controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gcs.ophwc.services.persistance.dao.entity.UserTypes;
import com.gcs.ophwc.services.resources.request.UserTypesRequest;
import com.gcs.ophwc.services.service.UserTypeService;

@Controller
@RequestMapping("/userType")
public class UserTypeController {

	private static final Logger applicationLog = Logger.getLogger("applicationLogger");
	private static final Logger errorLog = Logger.getLogger("errorLoggger");

	@Autowired
	private SessionData sessionobj;

	@Autowired
	private UserTypeService userTypeService;

	@RequestMapping(value = "/createUserType", method = RequestMethod.GET)
	public String createUserType(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into createUserType");
			UserTypesRequest userTypesRequest = new UserTypesRequest();
			model.put("userTypesRequest", userTypesRequest);

			return "createUserTypes";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/saveUserType", method = RequestMethod.GET)
	public String createOrupdateUserType(@ModelAttribute UserTypesRequest userTypeRequest, BindingResult result,
			ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into createOrupdateUserType");
			UserTypes userTypes = new UserTypes();
			userTypes.setTypeName(userTypeRequest.getTypeName());
			userTypes.setStatus("Y");
			userTypeService.saveUserType(userTypes);
			return "redirect:/userType/searchUserType";
		} else
			return "redirect:/";
	}

	@RequestMapping(value = "/searchUserType", method = RequestMethod.GET)
	public String usersTypeList(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into usersTypeList");
			List<UserTypes> userTypeList = userTypeService.getAllUserTypes();
			model.put("userTypeList", userTypeList);
			return "userTypesList";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "deActivate/{id}", method = RequestMethod.GET)
	public String deActivateUserType(@PathVariable Long id, ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into deActivateUserType");
			userTypeService.deActivateUserType(id);
			return "redirect:/userType/searchUserType";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "activate/{id}", method = RequestMethod.GET)
	public String activateUserType(@PathVariable Long id, ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into activateUserType");
			userTypeService.activateUserType(id);
			return "redirect:/userType/searchUserType";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/editUserType/{userId}", method = RequestMethod.GET)
	public String editUserType(@PathVariable Long userId, ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into editUserType");
			UserTypes userType = userTypeService.getUserTypeById(userId);
			UserTypesRequest typeReq = new UserTypesRequest();

			model.put("userType", userType);
			model.put("typeReq", typeReq);

			return "editUserType";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "updateUserType", method = RequestMethod.POST)
	public String updateUser(@ModelAttribute UserTypesRequest typeRequest, BindingResult result, ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into updateUser");
			UserTypes userType = new UserTypes();
			userType.setId(typeRequest.getId());
			userType.setTypeName(typeRequest.getTypeName());
			if (typeRequest.getStatus() != null && typeRequest.getStatus() != "")
				userType.setStatus(typeRequest.getStatus());
			else
				userType.setStatus("N");
			userTypeService.updateUserType(userType);

			return "redirect:/userType/searchUserType";
		} else
			return "redirect:/";
	}

}
