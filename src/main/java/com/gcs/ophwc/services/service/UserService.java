package com.gcs.ophwc.services.service;

import java.util.List;

import com.gcs.ophwc.services.persistance.dao.entity.ProjectResources;
import com.gcs.ophwc.services.persistance.dao.entity.User;
import com.gcs.ophwc.services.util.Response;

public interface UserService {

	public Response saveUser(User user);
	public User getUserByUserId(Long id);
	public User getUserById(Long id);
	public List<User> getAllUsers();
	public List<User> getAllLatestUsers();
	public List<User> getAllDivisionUsers();

	List<User> getAllClientUsers();

	public Response deleteUser(Long id);
	public Response updateUser(User user);
	public void deActivateUser(Long id);
	public void activateUser(Long id);
	public User validateGetUser(String username, String password);
	public User validateAdminUser(String username, String password);
	
	public User getUserByUserName(String username);
	
	public void saveProjectResource(ProjectResources resource);
	
	public void deleteProjectResources(Long userId);
}
