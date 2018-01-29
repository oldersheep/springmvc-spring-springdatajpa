package com.xxx.bos.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.xxx.bos.dto.EasyUIResult;
import com.xxx.bos.dto.SysResult;
import com.xxx.bos.entity.Role;
import com.xxx.bos.service.RoleService;

/** 
  * @className   类名 : RoleController
  * @description 作用 : 角色管理
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月21日 下午4:07:06 
  * @version 版本 : V1.0  
  */
@Controller
@RequestMapping("/role")
public class RoleController {
	
	private static final Logger LOG = Logger.getLogger(RoleController.class);

	@Autowired
	private RoleService roleService;
	
	/**
	 * 此方法描述的是： 分页+条件，条件没做
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月21日 下午5:16:53 
	 * @version: V1.0
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyUIResult list(int page, int rows){
		Pageable pageable = new PageRequest(page - 1, rows);
		Page<Role> rolePage = roleService.findAll(null, pageable);
		LOG.info("查询成功！！");
		return new EasyUIResult(rolePage.getTotalElements(), rolePage.getContent());
	}
	
	/**
	 * 此方法描述的是： Ajax请求所有的角色信息，只返回id和name即可
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月21日 下午8:09:11 
	 * @version: V1.0
	 */
	@RequestMapping(value = "/listAjax", produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String listAjax(){
		List<Role> roleList = roleService.findAll(null, null).getContent();
		
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Role.class, "id", "name");
		
		return JSONObject.toJSONString(roleList, filter);
	}
	
	/**
	 * 此方法描述的是：这个保存可能是有问题的，不知道事务是否可以回滚
	 * 经测试，是可以回滚的，虽然SQL语句已经发出了
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月21日 下午5:11:02 
	 * @version: V1.0
	 */
	@RequestMapping("/add")
	@ResponseBody
	public SysResult add(Role role, String ids){
		try{
			roleService.save(role, ids);
			LOG.info("保存成功！！");
			return SysResult.ok();
		} catch (Exception e){
			LOG.error(e.getMessage());
			return SysResult.build(201, "保存失败！");
		}
	}
}
