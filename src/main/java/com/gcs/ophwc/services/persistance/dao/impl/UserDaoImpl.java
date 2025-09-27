package com.gcs.ophwc.services.persistance.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gcs.ophwc.services.persistance.dao.api.OphwcDaoSupport;
import com.gcs.ophwc.services.persistance.dao.entity.ProjectResources;
import com.gcs.ophwc.services.persistance.dao.entity.Projects;
import com.gcs.ophwc.services.persistance.dao.entity.User;
import com.gcs.ophwc.services.persistance.dao.interfaces.UserDao;
import com.gcs.ophwc.services.persistance.dao.interfaces.UserTypeDao;

import static com.gcs.ophwc.services.util.Constants.CLIENT_USER_TYPE;
import static com.gcs.ophwc.services.util.Constants.DIVISION_USER_TYPE;

@SuppressWarnings("unchecked")
@Repository
public class UserDaoImpl extends OphwcDaoSupport<User, Long> implements UserDao {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger applicationLog = Logger.getLogger("applicationLogger");
	private static final Logger errorLog = Logger.getLogger("errorLoggger");

    @Autowired
    private UserTypeDao userTypeDao;

	@Override
	public void deactivateUser(Long id) {
		applicationLog.debug("Came Into deactivateUser()");
		Query query = getEntityManager().createQuery("update User set status='N' where id=" + id);
		query.executeUpdate();

	}

	@Override
	public void activateUser(Long id) {
		applicationLog.debug("Came Into activateUser()");
		Query query = getEntityManager().createQuery("update User set status='Y' where id=" + id);
		query.executeUpdate();

	}

	@Override
	public User validateGetUser(String userName, String pwd) {
		applicationLog.debug("Came Into validateGetUser()");
		Query query = getEntityManager()
				.createQuery("from User where username='" + userName + "' and password='" + pwd + "'");
		List list = query.getResultList();
		if (list.size() != 0)
			return (User) list.get(0);
		else
			return null;
	}
	
	@Override
	public User validateAdminUser(String userName, String pwd) {
		applicationLog.debug("Came Into validateAdminUser()");
		Query query = getEntityManager()
				.createQuery("from User where username='" + userName + "' and password='" + pwd + "'");
		List list = query.getResultList();
		if (list.size() != 0)
			return (User) list.get(0);
		else
			return null;
	}

	@Override
	public void saveProjectResource(ProjectResources resource) {
		getEntityManager().persist(resource);
	}

	@Override
	public User getUserByName(String userName) {
		applicationLog.debug("came into getUserByName()");
		Query query = getEntityManager().createQuery("from User where username='" + userName + "'");
		List list = query.getResultList();
		if (list.size() != 0)
			return (User) list.get(0);
		else
			return null;
	}

	@Override
	public List<User> getAllUsersWithProjects() {
		applicationLog.debug("Came Into getAllUsersWithProjects()");
		List<User> userList = getEntityManager().createQuery("from User order by id").getResultList();
		List<ProjectResources> resourceList = getEntityManager().createQuery("from ProjectResources order by user")
				.getResultList();
		List<User> projUserList = new ArrayList<>();
		for (User user : userList) {
			User userProj = null;
			List<Projects> projList = new ArrayList<>();
			Projects proj = null;
			for (ProjectResources res : resourceList) {
				if (user.getId() == res.getUser().getId()) {
					proj = new Projects();
					proj = res.getProjects();
					projList.add(proj);
				}
			}
			userProj = new User();
			userProj = user;
			userProj.setProjList(projList);
			projUserList.add(userProj);
		}
		return projUserList;
	}
	
	@Override
	public List<User> getAllLatestUsers() {
		applicationLog.debug("Came Into getAllLatestUsers()");
		List<User> userList = getEntityManager().createQuery("from User order by id desc").getResultList();
		return userList;
	}

	@Override
	public User getUserWithProject(Long id) {
		applicationLog.debug("Came Into getUserWithProject()");
		User user = null;
		List<Projects> projList = new ArrayList<>();
		List<Object[]> object = getEntityManager()
				.createQuery("from User as u,ProjectResources as p where u.id=p.user and u.id=" + id).getResultList();
		for (Object[] obj : object) {
			user = new User();
			user = (User) obj[0];
			ProjectResources res = (ProjectResources) obj[1];
			Projects prj = res.getProjects();
			projList.add(prj);
		}
		if (user != null) {
			user.setProjList(projList);
			return user;
		} else
			return null;
	}

	@Override
	public void deleteProjectResources(Long userId) {
		//applicationLog.debug("Came Into deleteProjectResources()");
		getEntityManager().createQuery("delete from ProjectResources where user="+userId).executeUpdate();	
	}

	@Override
	public List<User> getAllDivisionUser() {
		applicationLog.debug("Came Into getAllDivisionUser()");
        String query = String.format("from User where userTypes=%d and divisions.status='Y'",
                userTypeDao.getUserTypeId(DIVISION_USER_TYPE));
		List<User> userList = getEntityManager().createQuery(query).getResultList();
		return userList;
	}

	@Override
	public List<User> getAllClientUser() {
		applicationLog.debug("Came Into getAllClientUser()");
        String query = String.format("from User where userTypes=%d",
                userTypeDao.getUserTypeId(CLIENT_USER_TYPE));
		List<User> userList = getEntityManager().createQuery(query).getResultList();
		return userList;
	}

}
