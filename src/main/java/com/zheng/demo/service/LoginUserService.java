package com.zheng.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.zheng.demo.dao.ContactDao;
import com.zheng.demo.model.Contact;
import com.zheng.demo.model.DataUtil;
import com.zheng.demo.model.LoginUser;

public class LoginUserService {

	ContactDao contactDao = new ContactDao();
	DataMapper dto = new DataMapper();

	public List<LoginUser> getAllUser_java8Style_Lambda_1() {
		return contactDao.findAllContacts().stream().map(contact -> {
			LoginUser user = new LoginUser();
			user.setPassword(contact.getPassword());
			user.setUserName(contact.getUserName().toUpperCase());
			return user;
		}).collect(Collectors.toList());
	}
	
	public List<LoginUser> getAllUser_java8Style_Lambda_2() {
		return contactDao.findAllContacts().stream().map(c -> {
			return toUser(c);
		}).collect(Collectors.toList());
	}
	
	public List<LoginUser> getAllUser_java8Style_Lambda_3() {
		return contactDao.findAllContacts().stream().map(c -> toUser(c)).collect(Collectors.toList());
	}

	public List<LoginUser> getAllUser_java8Style_methodReference_1() {
		return contactDao.findAllContacts().stream().map(DataUtil::toUser).collect(Collectors.toList());
	}

	public List<LoginUser> getAllUser_java8Style_methodReference_2() {
		return contactDao.findAllContacts().stream().map(this::toUser).collect(Collectors.toList());
	}

	public List<LoginUser> getAllUser_java8Style_methodReference_best() {
		return contactDao.findAllContacts().stream().map(dto::toUser).collect(Collectors.toList());
	}

	public List<LoginUser> getAllUser_java8Style_namedLambda() {
		Function<Contact, LoginUser> convertContactToLoginUser = contact -> {
			return toUser(contact);
		};
		return contactDao.findAllContacts().stream().map(convertContactToLoginUser).collect(Collectors.toList());
	}

	public List<LoginUser> getAllUser_loopStyle() {
		List<Contact> allContacts = contactDao.findAllContacts();
		List<LoginUser> allUser = new ArrayList<>();
		for (Contact contact : allContacts) {
			allUser.add(toUser(contact));
		}
		return allUser;
	}

	private LoginUser toUser(Contact contact) {
		LoginUser user = new LoginUser();
		user.setPassword(contact.getPassword());
		user.setUserName(contact.getUserName().toUpperCase());
		return user;
	}

}
