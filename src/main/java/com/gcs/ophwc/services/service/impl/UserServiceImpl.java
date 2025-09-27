package com.gcs.ophwc.services.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import com.gcs.ophwc.services.service.UserTypeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.ophwc.services.persistance.dao.entity.ProjectResources;
import com.gcs.ophwc.services.persistance.dao.entity.User;
import com.gcs.ophwc.services.persistance.dao.interfaces.SiteVisitDao;
import com.gcs.ophwc.services.persistance.dao.interfaces.UserDao;
import com.gcs.ophwc.services.service.UserService;
import com.gcs.ophwc.services.util.Constants;
import com.gcs.ophwc.services.util.Response;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger applicationLog = Logger.getLogger("applicationLogger");
	private static final Logger errorLog = Logger.getLogger("errorLoggger");

    @Autowired
    private UserTypeService userTypeService;

	@Autowired
	private UserDao userDao;

	@Autowired
	private SiteVisitDao siteVisitDao;

	@Override
	@Transactional
	public Response saveUser(User user) {
		applicationLog.debug("came into saveUser()");

		// validate that the user must have a status
		if(user.getStatus() == null || user.getStatus().isEmpty())
			user.setStatus("Y");

		Response resp = new Response();
		try {
			User checkUser = userDao.getUserByName(user.getUsername());
			if (checkUser == null) {
				userDao.save(user);
				resp.setStatusCode(Constants.SUCCESS);
				resp.setStatusMessage(Constants.SUCCESSMSG);
				return resp;
			} else {
				resp.setStatusCode(Constants.FAILURE);
				resp.setStatusMessage("User Already Existed");
				return resp;
			}
		} catch (Exception e) {
			errorLog.error(e.getMessage());
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			e.printStackTrace();
			return resp;
		}
	}

	@Override
	public User getUserByUserId(Long id) {
		applicationLog.debug("came into getUserByUserId()");
		return userDao.findOne(id);
	}

	@Override
	public User getUserById(Long id) {
		applicationLog.debug("came into getUserById()");
		return userDao.getUserWithProject(id);
	}

	@Override
	public List<User> getAllUsers() {
		applicationLog.debug("came into getAllUsers()");
		return userDao.getAllUsersWithProjects();
	}

	@Override
	public List<User> getAllLatestUsers() {
		applicationLog.debug("came into getAllLatestUsers()");
		return userDao.getAllLatestUsers();
	}

	@Override
	public List<User> getAllDivisionUsers() {
		applicationLog.debug("came into getAllDivisionUsers()");
		return userDao.getAllDivisionUser();
	}

	@Override
	public List<User> getAllClientUsers() {
		applicationLog.debug("came into getAllClientUsers()");
		return userDao.getAllClientUser();
	}

	@Override
	@Transactional
	public Response deleteUser(Long id) {
		applicationLog.debug("came into deleteUser()");
		Response resp = new Response();
		try {
			siteVisitDao.deleteSiteVisitByUser(id);
			userDao.deleteById(id);
			resp.setStatusCode(Constants.SUCCESS);
			resp.setStatusMessage(Constants.SUCCESSMSG);
			return resp;
		} catch (Exception e) {
			errorLog.error("Error In deleteUser()="+e.getMessage());
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			e.printStackTrace();
			return resp;
		}

	}

	@Override
	@Transactional
	public Response updateUser(User user) {
		applicationLog.debug("came into updateUser()");
		Response resp = new Response();
		try {
			userDao.update(user);
			resp.setStatusCode(Constants.SUCCESS);
			resp.setStatusMessage(Constants.SUCCESSMSG);
			return resp;
		} catch (Exception e) {
			errorLog.error("Error in updateUser()");
			resp.setStatusCode(Constants.FAILURE);
			resp.setStatusMessage(Constants.FAILURESMSG);
			e.printStackTrace();
			return resp;
		}
	}

	@Override
	@Transactional
	public void deActivateUser(Long id) {
		applicationLog.debug("came into deActivateUser()");
		userDao.deactivateUser(id);

	}

	@Override
	@Transactional
	public void activateUser(Long id) {
		applicationLog.debug("came into activateUser()");
		userDao.activateUser(id);
	}

	@Override
	public User validateGetUser(String userName, String pwd) {
		applicationLog.debug("came into validateGetUser()");
		return userDao.validateGetUser(userName, pwd);
	}

	@Override
	public User validateAdminUser(String userName, String pwd) {

        applicationLog.debug("Validating Essential User Types");
        userTypeService.validateAndConfigureEssentialUserTypes();

        applicationLog.debug("validating Admin user in validateAdminUser()");
        return userDao.validateAdminUser(userName, pwd);
	}

	@Override
	public User getUserByUserName(String userName) {
		applicationLog.debug("came into getUserByUserName()");
		return userDao.getUserByName(userName);
	}

	@Override
	@Transactional
	public void saveProjectResource(ProjectResources resource) {
		//applicationLog.debug("came into saveProjectResource()");
		userDao.saveProjectResource(resource);

	}

	@Override
	@Transactional
	public void deleteProjectResources(Long userId) {
		//applicationLog.debug("came into deleteProjectResources()");
		userDao.deleteProjectResources(userId);

	}
}
