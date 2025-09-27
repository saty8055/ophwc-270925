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

import com.gcs.ophwc.services.persistance.dao.entity.Divisions;
import com.gcs.ophwc.services.resources.request.DivisionsRequest;
import com.gcs.ophwc.services.service.DivisionService;

@Controller
@RequestMapping("/division")
public class DivisionController {
	
	private static final Logger applicationLog = Logger.getLogger("applicationLogger");
	private static final Logger errorLog = Logger.getLogger("errorLoggger");

	@Autowired
	private SessionData sessionobj;
	
	@Autowired
	private DivisionService divisionService;
	
	@RequestMapping(value = "/divisionCreation", method = RequestMethod.GET)
	public String createDivision(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into createDivision");
			DivisionsRequest divisionRequest = new DivisionsRequest();
			model.put("divisionRequest", divisionRequest);

			return "createDivision";
		} else {
			return "redirect:/";
		}
	}
	
	@RequestMapping(value = "/saveDivision", method = RequestMethod.GET)
	public String createOrupdateDivision(@ModelAttribute DivisionsRequest divisionRequest, BindingResult result,
			ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into createOrupdateDivision");
			Divisions  division = new Divisions();
			
			if(divisionRequest.getId()!=null)
				division.setId(divisionRequest.getId());
			division.setDivisionName(divisionRequest.getDivisionName());
			division.setStatus(divisionRequest.getStatus());
			divisionService.saveDivision(division);
			return "redirect:/division/searchDivisions";
		} else
			return "redirect:/";
	}
	
	@RequestMapping(value = "/searchDivisions", method = RequestMethod.GET)
	public String divisionsList(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into divisionsList");
			List<Divisions> divisionsList = divisionService.getAllDivisions();
					
			model.put("divisionsList", divisionsList);
			return "divisionsList";
		} else {
			return "redirect:/";
		}
	}
	
	@RequestMapping(value = "deActivate/{id}", method = RequestMethod.GET)
	public String deActivateDivision(@PathVariable Long id, ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into deActivateDivision");
			divisionService.deActivateDivision(id);
			return "redirect:/division/searchDivisions";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "activate/{id}", method = RequestMethod.GET)
	public String activateDivision(@PathVariable Long id, ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into activateDivision");
			divisionService.activateDivision(id);
			return "redirect:/division/searchDivisions";
		} else {
			return "redirect:/";
		}
	}
}
