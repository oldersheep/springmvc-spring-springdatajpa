package com.xxx.bos.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xxx.bos.dto.EasyUIResult;
import com.xxx.bos.dto.SysResult;
import com.xxx.bos.entity.User;
import com.xxx.bos.service.UserService;
import com.xxx.bos.utils.MD5Util;

/** 
  * @className   类名 : UserController
  * @description 作用 : 用户相关的控制层操作
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月10日 下午8:31:06 
  * @version 版本 : V1.0  
  */
@Controller
@RequestMapping("/user")
public class UserController {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@RequestMapping("/list")
	@ResponseBody
	public EasyUIResult list(User user, Date _birthday, int page,int rows) {
		Pageable pageable = new PageRequest(page - 1, rows);
		// 条件
		Specification<User> specification = new Specification<User>(){
			// root其实就是要查询的实体类型
			// query添加查询条件
			// criteriaBuilder 构建一个Predicate
			@Override
			public Predicate toPredicate(Root<User> root,
										 CriteriaQuery<?> query,
										 CriteriaBuilder criteriaBuilder) {
				// 如果条件为空，直接返回
				if(user == null && _birthday == null)
					return null;
				
				String username = user.getUsername();
				String gender = user.getGender();
				Date birthday = user.getBirthday();
				List<Predicate> predicates = new ArrayList<Predicate>();
				if(StringUtils.isNotBlank(username)){
					predicates.add(criteriaBuilder.like(root.get("username").as(String.class), "%" + username + "%"));
				}
				if(StringUtils.isNotBlank(gender)){
					predicates.add(criteriaBuilder.equal(root.get("gender").as(String.class), gender));
				}
				if(birthday != null && _birthday != null){
					predicates.add(criteriaBuilder.between(root.get("birthday").as(Date.class), birthday, _birthday));
				} else if (birthday != null) {
					predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("birthday").as(Date.class), birthday));
				} else if (_birthday != null) {
					predicates.add(criteriaBuilder.lessThan(root.get("birthday").as(Date.class), _birthday));
				}
				
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		Page<User> userPage = userService.findAll(specification, pageable);
		LOG.info("查询成功！！");
		return new EasyUIResult(userPage.getTotalElements(), userPage.getContent());
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public SysResult add(User user, String[] roleIds){
		
		try{
			userService.save(user, roleIds);
			LOG.info("保存成功！！");
			return SysResult.ok();
		} catch (Exception e) {
			LOG.error(e.getMessage());
			
			return SysResult.build(201, "新增出错");
		}
		
	}
	
	/**
	 * 此方法描述的是：  编辑密码
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月10日 下午8:21:15 
	 * @version: V1.0
	 */
	@RequestMapping("/editPassword")
	@ResponseBody
	public SysResult editPassword(HttpServletRequest request, String password){
		try{
			User _user = (User) request.getSession().getAttribute("loginUser");
			String _password = MD5Util.md5(password);
			userService.modifyPasswordById(_user.getId(), _password);
			
			return SysResult.build(200, "密码修改成功！");
		}catch(Exception e){
			e.printStackTrace();
			LOG.error(e.getMessage());
			return SysResult.build(201, "系统异常，请稍后重试！");
		}
	}
	
	
	@InitBinder 
	public void InitBinder(ServletRequestDataBinder binder) {
		binder.registerCustomEditor(java.util.Date.class,
				new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	
}
