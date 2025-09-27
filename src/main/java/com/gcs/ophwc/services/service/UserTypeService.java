package com.gcs.ophwc.services.service;

import java.util.List;

import com.gcs.ophwc.services.persistance.dao.entity.UserTypes;

public interface UserTypeService {
	public void saveUserType(UserTypes userTypes);
	public UserTypes getUserTypeById(Long id);
	public List<UserTypes> getAllUserTypes();
	public void deleteUserType(Long id);
	public void updateUserType(UserTypes userType);
	public void deActivateUserType(Long id);
	public void activateUserType(Long id);

    void validateAndConfigureEssentialUserTypes();

    long getId(String userTypes);
}
