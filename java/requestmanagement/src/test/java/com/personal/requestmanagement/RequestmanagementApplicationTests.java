package com.personal.requestmanagement;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.personal.requestmanagement.model.dto.UserDto;
import com.personal.requestmanagement.service.UserService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RequestmanagementApplicationTests {
	
	@Autowired
	private UserService userService;
	
	@Test
	public void test() {
		UserDto dto = new UserDto();
		dto.setPassword("123456");
		dto.setUserName("quyetnv");
		dto.setRoleCodes(Arrays.asList(new String[] {"ROLE_EMP"}));
		boolean save = userService.save(dto);
		
		assertThat(save).isEqualTo(true);
	}

}
