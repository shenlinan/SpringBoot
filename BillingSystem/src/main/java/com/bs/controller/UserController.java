package com.bs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bs.domain.UserBean;
import com.bs.mapper.UserMapper;

@RestController
public class UserController {

	@Autowired
	private UserMapper userMapper;

	@RequestMapping(value = "/listUsers", method = RequestMethod.GET)
	List<UserBean> listUsers() {

		return userMapper.listUsers();
	}

}
