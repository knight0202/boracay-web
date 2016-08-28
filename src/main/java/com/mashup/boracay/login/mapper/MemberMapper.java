package com.mashup.boracay.login.mapper;

import org.mybatis.spring.annotation.MapperScan;
import com.mashup.boracay.login.vo.UserVo;

public interface MemberMapper {
	public UserVo getUser(UserVo userVo);
	public void setUser(UserVo userVo);
}
