package com.gcs.ophwc.services.persistance.dao.api;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.apache.log4j.Logger;

import com.gcs.ophwc.services.util.Constants;

@SuppressWarnings("unchecked")
public abstract class OphwcDaoSupport<T, ID extends Serializable> implements IOphwcDaoSupport<T, ID>, Serializable {
	
	private static final Logger applicationLog = Logger.getLogger("applicationLogger");
	private static final Logger errorLog = Logger.getLogger("errorLoggger");

	private static final long serialVersionUID = 88766925821795497L;
	private Class<T> clazz;
	protected boolean isActive = true;

	public OphwcDaoSupport() {
		this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@PersistenceContext
	EntityManager entityManager;

	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getSession() {
		return this.entityManager;
	}

	public void setClazz(Class<T> clazzToSet) {
		this.clazz = clazzToSet;
	}

	public T findOne(Long id) {
		applicationLog.debug("came into findOne()");
		return this.entityManager.find(this.clazz, id);
	}

	public List<T> findAll() {
		applicationLog.debug("came into findAll()");
		return this.entityManager.createQuery("from " + this.clazz.getName()).getResultList();
	}

	public void save(T entity) {
		applicationLog.debug("came into save()");
		this.entityManager.persist(entity);
	}

	public void update(T entity) {
		applicationLog.debug("came into update()");
		this.entityManager.merge(entity);
	}

	public void delete(T entity) {
		applicationLog.debug("came into delete()");
		this.entityManager.remove(entity);
	}

	public void deleteById(Long entityId) {
		applicationLog.debug("came into deleteById()");
		T entity = this.findOne(entityId);
		this.delete(entity);
	}

	/**** DAO Intelligence START ****/

	protected String activeYnQueryString(String className, List<String> list, String rootName, boolean isStart,
			boolean isOnlyForRoot, boolean value) {
		String returnValue = "";
		if (list == null)
			list = new ArrayList<String>();
		if (!list.contains(className))
			list.add(className);
		if (!isOnlyForRoot) {
			try {
				Class c = Class.forName(className);
				Field[] f = c.getDeclaredFields();
				for (int i = 0; i < f.length; i++) {
					String typeName = f[i].getType().getName();
					if (typeName.startsWith("com.gcs.iportal.services") && !list.contains(typeName)) {
						String rootValue = (rootName != null && !rootName.equals("") ? (rootName + ".") : "")
								+ f[i].getName();
						returnValue = " AND (" + rootValue + " is null OR " + rootValue + ".activeYn = "
								+ (value ? "true" : "false") + ")";
						returnValue = returnValue + activeYnQueryString(typeName, list, rootValue, false, false, value);
					}
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		returnValue = (isStart ? ("activeYn =" + (value ? "true" : "false")) : "") + returnValue;
		return returnValue;
	}

	protected String buildQuery(String whereClause, short operation, boolean isOnlyForRoot) {
		String tableName = clazz.getName();
		tableName = tableName.substring(tableName.lastIndexOf('.') + 1);
		boolean activeYn = false;
		if (operation == Constants.ONLY_ACTIVE)
			activeYn = true;
		else if (operation == Constants.ONLY_IN_ACTIVE)
			activeYn = false;

		String activeYnStr = (operation < Constants.ALL
				? activeYnQueryString(clazz.getName(), null, null, true, isOnlyForRoot, activeYn)
				: "");
		activeYnStr = activeYnStr != null && !activeYnStr.trim().equals("") ? (" where " + activeYnStr) : "";
		whereClause = (activeYnStr.equals("") && whereClause != null && !whereClause.trim().equals("")
				? (" where " + whereClause)
				: whereClause != null && !whereClause.trim().equals("") ? (" AND " + whereClause) : "");
		whereClause = whereClause != null && !whereClause.trim().equals("") ? whereClause : "";
		tableName = "FROM " + tableName + " " + activeYnStr + " " + whereClause;
		return tableName;
	}

	private static String getClassName(Object obj, boolean isPackageRequired) {
		String className = obj.getClass().getName();
		if (isPackageRequired)
			return className;
		else
			return className.substring(className.lastIndexOf('.') + 1);
	}

	protected void doTransaction(Object object, Integer userId, int type) {
		// Transaction tx = null;Byte(b)
		try {
			setAuditData(object, userId, type);

			String tableName = getTableName(this.entityManager, this.clazz);
			// logt.setLogTypeTx(type==Constants.INSERT?"I":type==Constants.DELETE?"D":"U");
			// logt.setLogTableTx(tableName);
			if (type != Constants.INSERT) {
				// entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(arg0)
				// Query q = this.entityManager.createQuery("FROM "+getClassName(object,false) +
				// " ");
			}
			switch (type) {
			case Constants.INSERT:
				this.entityManager.persist(object);
				break;
			case Constants.UPDATE:
				this.entityManager.merge(object);
				break;
			case Constants.DELETE:
				this.entityManager.remove(object);
				break;
			}

			// TODO -- need to get old values and new values and should configurae it
			// logt.setUpdatedBy(userId);
			// logt.setUpdatedDt(new Date());
			// session.save(logt);

			// TODO - need to save the LOG_T
			// tx.commit();
		} catch (Exception ex) {
			// if(tx!=null)tx.rollback();
			// TODO - raise exception
		}
	}

	/**
	 * Returns the table name for a given entity type in the {@link EntityManager}.
	 * 
	 * @param em
	 * @param entityClass
	 * @return
	 */
	public static <T> String getTableName(EntityManager em, Class<T> entityClass) {
		/*
		 * Check if the specified class is present in the metamodel. Throws
		 * IllegalArgumentException if not.
		 */
		Metamodel meta = em.getMetamodel();
		EntityType<T> entityType = meta.entity(entityClass);
		Table t = entityClass.getAnnotation(Table.class);
		String tableName = (t == null) ? entityType.getName().toUpperCase() : t.name();
		return tableName;
	}

	private void setAuditData(Object obj, Integer userId, int type) {

	}

	private void setAuditValue(String methodName, Object obj, Class c, Class[] classArgs, Object args) {
		try {
			Method method = c.getMethod(methodName, classArgs);
			method.invoke(obj, args);
		} catch (Exception ex) {
			System.out.println("exception when setaudit " + methodName);
			ex.printStackTrace();
		}
	}

	public void insertDto(Object obj, Integer userId) {
		doTransaction(obj, userId, Constants.INSERT);
	}

	public void updateDto(Object obj, Integer userId) {
		doTransaction(obj, userId, Constants.UPDATE);
	}

	public void deleteDto(Object obj, Integer userId) {
		doTransaction(obj, userId, Constants.DELETE);
	}

	/**** DAO Intelligence STOP ****/
}
