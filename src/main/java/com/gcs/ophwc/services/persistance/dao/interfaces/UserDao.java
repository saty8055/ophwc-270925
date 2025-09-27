package com.gcs.ophwc.services.persistance.dao.interfaces;

import java.util.List;

import com.gcs.ophwc.services.persistance.dao.api.IOphwcDaoSupport;
import com.gcs.ophwc.services.persistance.dao.entity.ProjectResources;
import com.gcs.ophwc.services.persistance.dao.entity.User;

public interface UserDao extends IOphwcDaoSupport<User, Long> {

	public void deactivateUser(Long id);
	public void activateUser(Long id);
	public User validateGetUser(String userName, String pwd);
	public User validateAdminUser(String userName, String pwd);
	public void saveProjectResource(ProjectResources resource);
	public User getUserByName(String userName);
	public List<User> getAllUsersWithProjects();
	public List<User> getAllLatestUsers();
	public List<User> getAllDivisionUser();
	public User getUserWithProject(Long id);
	public void deleteProjectResources(Long userId);

	List<User> getAllClientUser();
}
