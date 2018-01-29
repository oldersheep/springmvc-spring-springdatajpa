package com.xxx.bos.service.impl;

import javax.transaction.Transactional;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.xxx.bos.entity.Role;
import com.xxx.bos.repository.RoleRepository;
import com.xxx.bos.service.RoleService;

/** 
  * @className   类名 : RoleServiceImpl
  * @description 作用 : 角色管理的Service层实现类
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月21日 下午4:09:25 
  * @version 版本 : V1.0  
  */
@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private IdentityService identityService;
	
	

	@Override
	public Page<Role> findAll(Specification<Role> specification, Pageable pageable) {
		
		return roleRepository.findAll(specification, pageable);
	}
	
	
	@Override
	@Transactional
	public Role save(Role role, String ids) {
		Role _role = roleRepository.saveAndFlush(role);
		Group group = new GroupEntity(_role.getName());
		
		identityService.saveGroup(group);
		
		String[] functionIds = ids.split(",");
		
		for(String id : functionIds) {
			roleRepository.saveRoleAndFunction(_role.getId(), id);
		}
		
		return _role;
	}

}
