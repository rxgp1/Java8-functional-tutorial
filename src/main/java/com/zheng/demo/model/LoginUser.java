package com.zheng.demo.model;

public class LoginUser {
	private String userName;
	private String password;

	public String getUsertName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginUser [userName=" + userName + ", password=" + password + "]";
	}
}
