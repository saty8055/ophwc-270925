package com.gcs.ophwc.services.persistance.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;
import org.springframework.stereotype.Repository;

import com.gcs.ophwc.services.persistance.dao.api.OphwcDaoSupport;
import com.gcs.ophwc.services.persistance.dao.entity.SiteVisit;
import com.gcs.ophwc.services.persistance.dao.interfaces.SiteVisitDao;

@Repository
public class SiteVisitDaoImpl extends OphwcDaoSupport<SiteVisit, Long> implements SiteVisitDao {
	
	private static final Logger applicationLog = Logger.getLogger("applicationLogger");
	private static final Logger errorLog = Logger.getLogger("errorLoggger");

	@Override
	public String checkPohotosLimit(Long projId) {
		applicationLog.debug("Came Into checkPohotosLimit()");

		CallableStatement stmt = null;
		ResultSet rs = null;
		String path = null;
		EntityManager em = getEntityManager();
		Connection con = ((SessionImpl) em.unwrap(Session.class)).connection();

		try {
			stmt = con.prepareCall("{call photo_check(?)}");
			stmt.setLong("proj_id", projId);
			stmt.execute();
			rs = stmt.getResultSet();
			if (rs != null) {
				while (rs.next()) {
					path = rs.getString(1);
				}
			}
			// System.out.println("path in SiteVisitDaoImpl==" + path);
		} catch (SQLException e) {
			errorLog.error("Error in checkPohotosLimit()"+e.getMessage());
			e.printStackTrace();
		}
		return path;
	}

	@Override
	public void deleteSiteVisitByUser(Long userId) {
		applicationLog.debug("Came Into deleteSiteVisitByUser()");
		Query query = getEntityManager().createQuery(
				"delete from SiteVisit where projects in (select projects from ProjectResources where user=" + userId
						+ ")");
		query.executeUpdate();
	}

	@Override
	public int deleteSiteVisitByPath(String path) {
		applicationLog.debug("Came Into deleteSiteVisitByPath()");
		Query query = getEntityManager().createQuery("delete from SiteVisit where imagePath='" + path + "'");
		int val = query.executeUpdate();
		return val;
	}

	@Override
	public void deleteSiteVisitProjectId(Long projId) {
		applicationLog.debug("Came Into deleteSiteVisitProjectId()");
		Query query = getEntityManager().createQuery("delete from SiteVisit where projects =" + projId + ")");
		query.executeUpdate();
	}

}
