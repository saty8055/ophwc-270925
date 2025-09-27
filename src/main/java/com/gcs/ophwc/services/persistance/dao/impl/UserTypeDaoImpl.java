package com.gcs.ophwc.services.persistance.dao.impl;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.gcs.ophwc.services.persistance.dao.api.OphwcDaoSupport;
import com.gcs.ophwc.services.persistance.dao.entity.UserTypes;
import com.gcs.ophwc.services.persistance.dao.interfaces.UserTypeDao;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
public class UserTypeDaoImpl extends OphwcDaoSupport<UserTypes, Long> implements UserTypeDao {
	
	private static final long serialVersionUID = 1L;
	private static final Logger applicationLog = Logger.getLogger("applicationLogger");

	@Override
	public void deActivateType(Long id) {
		applicationLog.debug("Came Into deActivateType");
		Query query=getEntityManager().createQuery("update UserTypes set status='N' where id="+id);
		query.executeUpdate();
	}

	@Override
	public void activateType(Long id) {
		applicationLog.debug("Came Into activateType");
		Query query=getEntityManager().createQuery("update UserTypes set status='Y' where id="+id);
		query.executeUpdate();
	}

    @Override
    public long getUserTypeId(String userTypeName) {

        String queryString = String.format("from UserTypes where typeName='%s'", userTypeName);
        Query query = getEntityManager().createQuery(queryString);
        List<UserTypes> userTypesList = query.getResultList();
        return CollectionUtils.isEmpty(userTypesList) ? -1 : userTypesList.get(0).getId();

    }

}
