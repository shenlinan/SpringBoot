package com.bs;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bs.domain.UserBean;
import com.bs.mapper.UserMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BillingSystemApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Test
	public void contextLoads() {
		List<UserBean> userList = userMapper.listUsers();
		for (UserBean user : userList) {
			System.out.println(user.getName());
		}

	}

}
