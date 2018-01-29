package com.xxx.bos.shiro;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.xxx.bos.entity.Function;
import com.xxx.bos.entity.User;
import com.xxx.bos.repository.FunctionRepository;
import com.xxx.bos.repository.UserRepository;

/** 
  * @className   类名 : SelfRealm
  * @description 作用 : 自定义的realm
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月17日 下午8:50:02 
  * @version 版本 : V1.0  
  */

public class SelfRealm extends AuthorizingRealm {
	
	private static final Logger LOG = Logger.getLogger(SelfRealm.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FunctionRepository functionRepository;
	
	/**
	 * 授权方法
	 * 
	 * @param principals : 
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		User user = (User) principals.getPrimaryPrincipal();
		List<Function> list = null;
		if("admin".equals(user.getUsername().toLowerCase())){
			list = functionRepository.findAll();
		} else {
			list = functionRepository.findListByUserId(user.getId());
		}
		for(Function function : list){
			info.addStringPermission(function.getCode());
		}
		return info;
	}

	/**
	 * 认证方法
	 * 
	 * @param token : 令牌 
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		LOG.info("开始认证!");
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();// 从令牌中获得用户名

		User user = userRepository.findByUsername(username);
		
		if (user == null) {
			// 用户名不存在
			return null;
		} else {
			// 用户名存在
			String password = user.getPassword();// 获得数据库中存储的密码
			// 创建简单认证信息对象
			/***
			 * 参数一：签名，程序可以在任意位置获取当前放入的对象
			 * 参数二：从数据库中查询出的密码
			 * 参数三：当前realm的名称
			 */
			AuthenticationInfo info = new SimpleAuthenticationInfo(user, password, this.getClass().getSimpleName());
			return info;//返回给安全管理器，由安全管理器负责比对数据库中查询出的密码和页面提交的密码
		}
	}

}
