package com.bs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.bs.domain.UserBean;

@Mapper
public interface UserMapper {

	@Select("SELECT * FROM T_USER ")
	List<UserBean> listUsers();
}
