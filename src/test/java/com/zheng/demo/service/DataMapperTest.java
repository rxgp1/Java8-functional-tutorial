package com.zheng.demo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.zheng.demo.model.Contact;
import com.zheng.demo.model.LoginUser;

public class DataMapperTest {

	private DataMapper dto = new DataMapper();

	@Test
	public void toUser() {
		Contact contact = new Contact("firstName", "lastName", "userName", "password", 40);
		LoginUser user = dto.toUser(contact);
		assertNotNull(user);
		assertEquals("USERNAME", user.getUsertName());
		assertEquals("password", user.getPassword());
	}
}
