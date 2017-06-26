package com.penghai.testoficp.management.model;

import java.util.Date;
/**
 * 
 * @author 刘晓强
 * @date 2017年5月10日
 */
public class User {
	private Integer id;

	private String nickname;

	private String enterpriseName;

	private String enterpriseURL;

	private String organizationCode;

	private String address;

	private String tel;

	private String contacts;

	private String email;

	private String password;

	private Date registrationTime;

	private Byte status;

	private String companyAddress;

	private String companyEmail;

	private String companyTel;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname == null ? null : nickname.trim();
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName == null ? null : enterpriseName.trim();
	}

	public String getEnterpriseURL() {
		return enterpriseURL;
	}

	public void setEnterpriseURL(String enterpriseURL) {
		this.enterpriseURL = enterpriseURL == null ? null : enterpriseURL.trim();
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode == null ? null : organizationCode.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel == null ? null : tel.trim();
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts == null ? null : contacts.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public Date getRegistrationTime() {
		return registrationTime;
	}

	public void setRegistrationTime(Date registrationTime) {
		this.registrationTime = registrationTime;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress == null ? null : companyAddress.trim();
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail == null ? null : companyEmail.trim();
	}

	public String getCompanyTel() {
		return companyTel;
	}

	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

}