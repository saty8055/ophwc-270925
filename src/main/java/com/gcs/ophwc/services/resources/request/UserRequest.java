package com.gcs.ophwc.services.resources.request;

import java.util.List;

import com.gcs.ophwc.services.persistance.dao.entity.Divisions;
import com.gcs.ophwc.services.persistance.dao.entity.NatureOfWork;
import com.gcs.ophwc.services.persistance.dao.entity.Projects;
import com.gcs.ophwc.services.persistance.dao.entity.UserTypes;

public class UserRequest {

	private Long id;
	private Divisions divisions;
	private UserTypes userTypes;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String phoneNum;
	private String emailId;
	private byte[] userImage;
	private String status;
	private List<Projects> projList;
	private List<NatureOfWork> natureList;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Divisions getDivisions() {
		return divisions;
	}
	public void setDivisions(Divisions divisions) {
		this.divisions = divisions;
	}
	public UserTypes getUserTypes() {
		return userTypes;
	}
	public void setUserTypes(UserTypes userTypes) {
		this.userTypes = userTypes;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public byte[] getUserImage() {
		return userImage;
	}
	public void setUserImage(byte[] userImage) {
		this.userImage = userImage;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Projects> getProjList() {
		return projList;
	}
	public void setProjList(List<Projects> projList) {
		this.projList = projList;
	}
	public List<NatureOfWork> getNatureList() {
		return natureList;
	}
	public void setNatureList(List<NatureOfWork> natureList) {
		this.natureList = natureList;
	}
	
	
}
