package com.personal.requestmanagement;

import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.personal.requestmanagement.service.UserService;

@SpringBootTest
//@RunWith(SpringRunner.class)
class RequestmanagementApplicationTests {
	
	@Autowired
	private UserService userService;
	
	@Test
	void contextLoads() {
		System.out.println(userService.getCurrentUser().getUsername());
	}

}
