package com.zheng.demo.service;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.Test;

import com.zheng.demo.model.LoginUser;

public class LoginUserServiceTest {
	
	private LoginUserService testService = new LoginUserService();

	@Test
	public void getAllUser_java8Style_Lambda_1() {
		List<LoginUser> allusers = testService.getAllUser_java8Style_Lambda_1();
		assertTrue(allusers.size() == 3);
		
		validate(allusers);		
	}
	
	@Test
	public void getAllUser_java8Style_Lambda_2() {
		List<LoginUser> allusers = testService.getAllUser_java8Style_Lambda_2();
		assertTrue(allusers.size() == 3);
		
		validate(allusers);		
	}
	
	@Test
	public void getAllUser_java8Style_Lambda_3() {
		List<LoginUser> allusers = testService.getAllUser_java8Style_Lambda_3();
		assertTrue(allusers.size() == 3);
		
		validate(allusers);		
	}
	
	@Test
	public void getAllUser_java8Style_methodReference_1() {
		List<LoginUser> allusers = testService.getAllUser_java8Style_methodReference_1();
		assertTrue(allusers.size() == 3);
		
		validate(allusers);		
	}
	
	@Test
	public void getAllUser_java8Style_methodReference_2() {
		List<LoginUser> allusers = testService.getAllUser_java8Style_methodReference_2();
		assertTrue(allusers.size() == 3);
		
		validate(allusers);		
	}
	
	@Test
	public void getAllUser_java8Style_methodReference_best() {
		List<LoginUser> allusers = testService.getAllUser_java8Style_methodReference_best();
		assertTrue(allusers.size() == 3);
		
		validate(allusers);		
	}
	
	@Test
	public void getAllUser_java8Style_namedLambda() {
		List<LoginUser> allusers = testService.getAllUser_java8Style_namedLambda();
		assertTrue(allusers.size() == 3);
		
		validate(allusers);		
	}
	
	@Test
	public void getAllUser_loopStyle() {
		List<LoginUser> allusers = testService.getAllUser_loopStyle();
		assertTrue(allusers.size() == 3);
		
		validate(allusers);		
	}

	private void validate(List<LoginUser> allusers) {
		Consumer<LoginUser> printOutUser = System.out::println;
		allusers.stream().forEach(printOutUser );
		
		Predicate<LoginUser> foundMary = e -> e.getUsertName().equalsIgnoreCase("bzheng") ;
		List<LoginUser> foundusers = allusers.stream().filter(foundMary ).collect(Collectors.toList());
		assertTrue(foundusers.size() == 1);
	}
}
