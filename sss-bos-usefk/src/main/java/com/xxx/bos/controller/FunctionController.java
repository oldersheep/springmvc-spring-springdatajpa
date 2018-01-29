package com.xxx.bos.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.xxx.bos.dto.EasyUIResult;
import com.xxx.bos.dto.SysResult;
import com.xxx.bos.entity.Function;
import com.xxx.bos.service.FunctionService;
import com.xxx.bos.utils.PinYin4jUtil;

/** 
  * @className   类名 : FunctionController
  * @description 作用 : TODO(这里用一句话描述这个类的作用) 
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月17日 下午10:00:52 
  * @version 版本 : V1.0  
  */
@Controller
@RequestMapping("/function")
public class FunctionController {
	
	private static final Logger LOG = Logger.getLogger(FunctionController.class);

	@Autowired
	private FunctionService functionService;
	
	/**
	 * 此方法描述的是： 分页查询
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月18日 下午9:01:14 
	 * @version: V1.0
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyUIResult list(int page, int rows){
		
		Pageable pageable = new PageRequest(page-1, rows);
		Page<Function> functionPage = functionService.findAll(pageable);
		
		return new EasyUIResult(functionPage.getTotalElements(), functionPage.getContent());
	}
	
	/**
	 * 此方法描述的是： ajax查询所有的权限与其id
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月18日 下午9:01:27 
	 * @version: V1.0
	 */
	@RequestMapping(value = "/listAjax", produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String listAjax(){
		List<Function> functionList = functionService.findAll(null).getContent();
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Function.class, "id", "name", "pId");
		return JSONArray.toJSONString(functionList, filter);
	}
	
	/**
	 * 此方法描述的是：新增权限页面，其实可以直接返回页面
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月21日 下午3:23:24 
	 * @version: V1.0
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public SysResult add(Function function){
		try{
			String code = StringUtils.join(PinYin4jUtil.getHeadByString(function.getName()), "");
			function.setCode(code.toLowerCase());
			if(function.getFunction() != null && StringUtils.isBlank(function.getFunction().getId())) {
				function.setFunction(null);
			}
			functionService.save(function);
			
			return SysResult.ok();
		} catch (Exception e) {
			LOG.error(e.getMessage());
			
			return SysResult.build(201, "新增出错");
		}
	}
	
}
