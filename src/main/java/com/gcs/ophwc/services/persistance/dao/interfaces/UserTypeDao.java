package com.gcs.ophwc.services.persistance.dao.interfaces;

import com.gcs.ophwc.services.persistance.dao.api.IOphwcDaoSupport;
import com.gcs.ophwc.services.persistance.dao.entity.UserTypes;

public interface UserTypeDao extends IOphwcDaoSupport<UserTypes, Long> {

	void deActivateType(Long id);

	void activateType(Long id);

    long getUserTypeId(String userTypeName);
}
