package com.zheng.demo.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.zheng.demo.model.Contact;

public class ContactDaoTest {

	private ContactDao testClass = new ContactDao();

	@Test
	public void findAllContacts() {
		List<Contact> allContacts = testClass.findAllContacts();
		assertEquals(3, allContacts.size());
	}
}
