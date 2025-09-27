package com.gcs.ophwc.services.service.impl;

import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.ophwc.services.persistance.dao.entity.UserTypes;
import com.gcs.ophwc.services.persistance.dao.interfaces.UserTypeDao;
import com.gcs.ophwc.services.service.UserTypeService;

import static com.gcs.ophwc.services.util.Constants.*;

@Service
public class UserTypeServiceImpl implements UserTypeService {
	
	private static final Logger applicationLog = Logger.getLogger("applicationLogger");
	private static final Logger errorLog = Logger.getLogger("errorLoggger");

	@Autowired
	private UserTypeDao userTypeDao;

    @PostConstruct
    public void init() {
//        validateAndConfigureEssentialUserTypes();
    }

	@Override
	@Transactional
	public void saveUserType(UserTypes userTypes) {
		applicationLog.debug("Came Into saveUserType()");
        validateAndSave(userTypes);
	}

	@Override
	public UserTypes getUserTypeById(Long id) {
		applicationLog.debug("Came Into getUserTypeById()");
		return userTypeDao.findOne(id);
	}

	@Override
	public List<UserTypes> getAllUserTypes() {
		applicationLog.debug("Came Into getAllUserTypes()");
		return userTypeDao.findAll();
	}

	@Override
	@Transactional
	public void deleteUserType(Long id) {
		applicationLog.debug("Came Into deleteUserType()");
		userTypeDao.deleteById(id);		
	}

	@Override
	@Transactional
	public void updateUserType(UserTypes userType) {
		applicationLog.debug("Came Into updateUserType()");
		userTypeDao.update(userType);		
	}

	@Override
	@Transactional
	public void deActivateUserType(Long id) {
		applicationLog.debug("Came Into deActivateUserType()");
		userTypeDao.deActivateType(id);
		
	}

	@Override
	@Transactional
	public void activateUserType(Long id) {
		applicationLog.debug("Came Into activateUserType()");
		userTypeDao.activateType(id);
		
	}

    @Override
    @Transactional
    public void validateAndConfigureEssentialUserTypes() {

        boolean DIVISION_CHECK = false, ADMIN_CHECK = false, HO_CHECK = false, CLIENT_CHECK = false;
        List<UserTypes> userTypes = userTypeDao.findAll();

        for(UserTypes userType : userTypes) {

            if(DIVISION_CHECK && ADMIN_CHECK && HO_CHECK && CLIENT_CHECK) {
                break;
            }

            if(DIVISION_USER_TYPE.equals(userType.getTypeName()))
                DIVISION_CHECK = true;
            if(CLIENT_USER_TYPE.equals(userType.getTypeName()))
                CLIENT_CHECK = true;
            if(ADMIN_USER_TYPE.equals(userType.getTypeName()))
                ADMIN_CHECK = true;
            if(HO_USER_TYPE.equals(userType.getTypeName()))
                HO_CHECK = true;

        }

        if(!DIVISION_CHECK)
            configureUserType(DIVISION_USER_TYPE);
        if(!ADMIN_CHECK)
            configureUserType(ADMIN_USER_TYPE);
        if(!HO_CHECK)
            configureUserType(HO_USER_TYPE);
        if(!CLIENT_CHECK)
            configureUserType(CLIENT_USER_TYPE);

    }

    private void configureUserType(String userType) {

        UserTypes userTypes = new UserTypes();
        userTypes.setTypeName(userType);
        userTypes.setStatus("Y");
        validateAndSave(userTypes);

    }

    private void validateAndSave(UserTypes userType) {

       if(userTypeDao.getUserTypeId(userType.getTypeName()) < 0)
           userTypeDao.save(userType);

    }

    @Override
    public long getId(String userTypes) {

        UserTypes userType = userTypeDao.findAll().stream()
                .filter(u -> u.getTypeName().equals(userTypes)).findFirst().orElse(null);

        return Objects.nonNull(userType) ? userType.getId() : -1;
    }

}

