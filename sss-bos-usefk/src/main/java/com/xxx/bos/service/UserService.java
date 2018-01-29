package com.xxx.bos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.xxx.bos.entity.User;

/** 
 * @className   类名 : UserService
 * @description 作用 : TODO(这里用一句话描述这个类的作用) 
 * @author  作者 : Lilg
 * @date 创建时间 : 2017年12月31日 下午1:49:53 
 * @version 版本 : V1.0  
 */
public interface UserService {
	
	/**
	 * 此方法描述的是：分页+条件查询用户信息
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月21日 下午5:36:57 
	 * @version: V1.0
	 */
	Page<User> findAll(Specification<User> specification, Pageable pageable);

	/**
	 * 此方法描述的是： 用户登录操作
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月10日 下午8:37:49 
	 * @version: V1.0
	 */
	User login(User user);
	
	/**
	 * 此方法描述的是： 根据用户id更新密码
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月10日 下午8:38:03 
	 * @version: V1.0
	 */
	void modifyPasswordById(String id, String password);

	
	/**
	 * 此方法描述的是：保存用户信息，同时同步与角色的关联，同时同步activiti的act_id_user、act_id_membership
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月21日 下午8:22:15 
	 * @version: V1.0
	 */
	User save(User user, String[] roleIds);

}
