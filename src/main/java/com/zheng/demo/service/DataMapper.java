package com.zheng.demo.service;

import com.zheng.demo.model.Contact;
import com.zheng.demo.model.DataUtil;
import com.zheng.demo.model.LoginUser;

public class DataMapper {
	public LoginUser toUser(Contact contact) {		
		return DataUtil.toUser(contact);
	}
}