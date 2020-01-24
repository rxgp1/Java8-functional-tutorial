package com.zheng.demo.dao;

import java.util.List;

import com.zheng.demo.model.Contact;
import com.zheng.demo.model.DataUtil;

public class ContactDao {

	public List<Contact> findAllContacts(){
		return DataUtil.getListOfContacts();
	}
}
