package com.mashup.boracay.login.controller;

import java.io.File;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mashup.boracay.common.util.CommonUtil;
import com.mashup.boracay.login.mapper.MemberMapper;
import com.mashup.boracay.login.vo.UserVo;

@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired private MemberMapper memberMapper;
	
	//2016.06.26 작성 by Thomas Yoo
	//로그인 에러 메세지 controller 
	//type : get
	@RequestMapping(value = "/error", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String error(
			Principal principal,
			Locale locale,
			Model model,
			@RequestParam(value = "error") String error,
			RedirectAttributes redirectAttr,
			HttpServletRequest request) {
		logger.debug("======== error ==========");
		
		logger.debug("error " + error);
		
		JSONObject data = new JSONObject();
		if(error != null) {
			if(error.compareTo("fail") == 0) {
				data.put("type", "MSG_LOGIN");
				data.put("result", "fail");
				data.put("data", "로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.");
			}else if(error.compareTo("session") == 0) {
				data.put("type", "MSG_LOGIN");
				data.put("result", "fail");
				data.put("data", "이미 로그인 되어있습니다.");
			}
		}
		return data.toString();
	}
	
	
	//2016.06.26 작성 by Thomas Yoo
	//아이디 중복 검사 controller 
	//type : get
	@RequestMapping(value = "/member_search", method = RequestMethod.GET)
	@ResponseBody
	public String memberSearch(Locale locale,
			Model model,
			@ModelAttribute("") UserVo userVo,
			HttpServletRequest request) throws Exception {
		logger.debug("============= memberSearch =============");
		logger.debug("userVo : {}", new JSONObject(userVo).toString());
		
		UserVo vo = memberMapper.getUser(userVo);
		
		if(vo != null) {
			return "fail";
		}
		return "sucess";
	}
	
	//2016.06.26 작성 by Thomas Yoo
	//계정 추가 controller 
	//type : post
	@RequestMapping(value = "/member_add", method = RequestMethod.POST)
	@ResponseBody
	public String memberAdd(Locale locale,
			Model model,
			@ModelAttribute("") UserVo userVo,
			HttpServletRequest request) throws Exception {
		logger.debug("============= member_add =============");
		logger.debug("userVo : {}", new JSONObject(userVo).toString());
		
		String userPw = userVo.getMemberPw();
		
		System.out.println(CommonUtil.messageToCryp(userPw));
		System.out.println(CommonUtil.messageToCryp(userPw).length());
		userVo.setMemberPw(CommonUtil.messageToCryp(userPw));
		
		//중복처리 mapper ( 기능변경으로 인한 회원가입시 처리 )
		UserVo vo = memberMapper.getUser(userVo);
		
		JSONObject object = new JSONObject();
		
		if(vo != null) {
			object.put("type", "MSG_MAN_JOIN");
			object.put("result", "fail");
			object.put("data", "중복된 아이디입니다.");
			return object.toString();
		}
		
		memberMapper.setUser(userVo);
		
		System.out.println("signin success");
		
		object.put("type", "MSG_MAN_JOIN");
		object.put("result", "success");
		return object.toString();
	}
	
	//2016.06.26 작성 by Thomas Yoo
	//로그인 성공시 처리 controller 
	//type : get
	@RequestMapping(value = "/success", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String being(
			Principal principal,
			Locale locale,
			Model model,
			HttpServletRequest request) {
		logger.info("Welcome home! The client locale is {}.", locale);

		logger.debug("userID : {}", principal.getName());
		request.getSession().setAttribute("name", principal.getName());
		
		JSONObject json = new JSONObject();
		json.put("type", "MSG_LOGIN");
		json.put("result", "success");
		json.put("data", "로그인 되었습니다.");
		json.put("userId", principal.getName());
		return json.toString();
	}
}
