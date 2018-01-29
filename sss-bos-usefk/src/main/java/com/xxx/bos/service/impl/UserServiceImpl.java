package com.xxx.bos.service.impl;

import javax.transaction.Transactional;

import org.activiti.engine.IdentityService;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.xxx.bos.entity.Role;
import com.xxx.bos.entity.User;
import com.xxx.bos.repository.RoleRepository;
import com.xxx.bos.repository.UserRepository;
import com.xxx.bos.service.UserService;
import com.xxx.bos.utils.MD5Util;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private IdentityService identityService;

	@Override
	public User login(User user) {
		
		String username = user.getUsername();
		String password = MD5Util.md5(user.getPassword());
		
		return userRepository.findByUsernameAndPassword(username, password);
	}

	@Override
	@Transactional
	public void modifyPasswordById(String id, String password) {
		
		userRepository.updatePasswordById(id, password);
	}

	@Override
	public Page<User> findAll(Specification<User> specification, Pageable pageable) {
		
		return userRepository.findAll(specification, pageable);
	}

	@Override
	@Transactional
	public User save(User user, String[] roleIds) {
		
		user.setPassword(MD5Util.md5(user.getPassword()));
		User _user = userRepository.saveAndFlush(user);
		
		// ========= 2018.1.27新加内容 同步用户
		org.activiti.engine.identity.User actUser = new UserEntity(_user.getId());
		identityService.saveUser(actUser);
		// =========
		
		for(String id : roleIds) {
			
			userRepository.saveUserAndRole(_user.getId(), id);
			// ========= 2018.1.27新加内容 同步用户
			Role role = roleRepository.findOne(id);
			identityService.createMembership(_user.getId(), role.getName());
			// ========= 
		}
		
		return _user;
	}

}
