package com.gcs.ophwc.services.persistance.dao.impl;

import org.springframework.stereotype.Repository;

import com.gcs.ophwc.services.persistance.dao.api.OphwcDaoSupport;
import com.gcs.ophwc.services.persistance.dao.entity.ProjectStatus;
import com.gcs.ophwc.services.persistance.dao.interfaces.ProjectStatusDao;

@Repository
public class ProjectStatusDaoImpl extends OphwcDaoSupport<ProjectStatus, Long> implements ProjectStatusDao{

}
