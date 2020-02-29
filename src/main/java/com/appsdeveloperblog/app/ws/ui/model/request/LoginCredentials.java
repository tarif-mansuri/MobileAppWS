package com.appsdeveloperblog.app.ws.ui.model.request;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoginCredentials {

	private String userName;
	private String userpassword;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

}
