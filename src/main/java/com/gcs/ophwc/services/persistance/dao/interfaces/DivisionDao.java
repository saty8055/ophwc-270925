package com.gcs.ophwc.services.persistance.dao.interfaces;

import java.util.List;

import com.gcs.ophwc.services.persistance.dao.api.IOphwcDaoSupport;
import com.gcs.ophwc.services.persistance.dao.entity.Divisions;

public interface DivisionDao extends IOphwcDaoSupport<Divisions, Long>{
public List<Divisions> findAll();
public List<Divisions> findAllActiveDivisions();
public List<Divisions> findAllUserDivisions(Long userId);

public void deActivateDivision(Long id);
public void activateDivision(Long id);	

}
