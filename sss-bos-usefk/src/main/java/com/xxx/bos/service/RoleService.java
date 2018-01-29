package com.xxx.bos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.xxx.bos.entity.Role;

/** 
  * @className   类名 : RoleService
  * @description 作用 : 角色管理Service层接口
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月21日 下午4:08:58 
  * @version 版本 : V1.0  
  */

public interface RoleService {

	
	/**
	 * 此方法描述的是： 保存角色信息，并管理权限信息，同时同步到activiti表中
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月21日 下午4:15:37 
	 * @version: V1.0
	 */
	Role save(Role role, String ids);

	
	/**
	 * 此方法描述的是：分页查询角色信息  
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月21日 下午5:15:36 
	 * @version: V1.0
	 */
	Page<Role> findAll(Specification<Role> specification, Pageable pageable);

}
