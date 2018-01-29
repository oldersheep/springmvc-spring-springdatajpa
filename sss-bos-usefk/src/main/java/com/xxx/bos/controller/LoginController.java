package com.xxx.bos.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xxx.bos.dto.SysResult;
import com.xxx.bos.entity.User;
import com.xxx.bos.utils.MD5Util;

/**
 * 
 * @className   类名 : LoginController
 * @description 作用 : 登录相关的控制层
 * @author  作者 : Lilg
 * @date 创建时间 : 2018年1月10日 下午8:20:19 
 * @version 版本 : V1.0
 */
@Controller
public class LoginController {
	
	private static final Logger LOG = Logger.getLogger(LoginController.class);

	/**
	 * 此方法描述的是： 用户登录
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月10日 下午8:20:47 
	 * @version: V1.0
	 */
	@RequestMapping("/login")
	@ResponseBody
	public SysResult login(HttpServletRequest request, User user, String checkcode) {

		String key = (String) request.getSession().getAttribute("key");
		if (StringUtils.isNotBlank(checkcode) && checkcode.equals(key)) {
			
			//获得当前用户对象
			Subject subject = SecurityUtils.getSubject();//状态为“未认证”
			String password = user.getPassword();
			password = MD5Util.md5(password);
			//构造一个用户名密码令牌
			AuthenticationToken token = new UsernamePasswordToken(user.getUsername(), password);
			
			try{
				subject.login(token);
			}catch (UnknownAccountException e) {
				e.printStackTrace();
				//设置错误信息
				LOG.error("用户名不存在:" + e.getMessage());
				return SysResult.build(201, "用户名或者密码错误！！");
			}catch (IncorrectCredentialsException e) {
				e.printStackTrace();
				//设置错误信息 loginError
				LOG.error("密码错误：" + e.getMessage());
				return SysResult.build(202, "用户名或者密码错误！！");
			} catch(Exception e) {
				LOG.error("其他未知错误：" + e.getMessage());
				return SysResult.build(203, "系统异常，请稍后重试！！");
			}
			//获取认证信息对象中存储的User对象
			User loginUser = (User) subject.getPrincipal();
			request.getSession().setAttribute("loginUser", loginUser);
			
			return SysResult.ok();

		}
		//验证码不存在
		return SysResult.build(204, "验证码错误！！");
	}
	
	/**
	 * 此方法描述的是：  用户登出
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月10日 下午8:21:03 
	 * @version: V1.0
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request){
		request.getSession().invalidate();
		return "redirect:login.jsp";
	}
	
}
