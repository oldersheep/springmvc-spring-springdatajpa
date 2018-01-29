package com.xxx.bos.controller;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.xxx.bos.entity.Noticebill;
import com.xxx.bos.entity.User;
import com.xxx.bos.service.NoticebillService;
import com.yyy.crm.entity.Customer;
import com.yyy.crm.service.CustomerService;

/** 
  * @className   类名 : NoticebillController
  * @description 作用 : 业务通知单表现层处理
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月15日 下午11:53:31 
  * @version 版本 : V1.0  
  */
@Controller
@RequestMapping("/noticebill")
public class NoticebillController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private NoticebillService noticebillService;
	
	/**
	 * 此方法描述的是：Ajax请求返回客户数据
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月16日 上午12:06:14 
	 * @version: V1.0
	 */
	@RequestMapping(value = "/findCustomerByTelephone", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String findCustomerByTelephone(String telephone){
		
		Customer customer = customerService.findCustomerByTelephone(telephone);
		
		return JSONObject.toJSONString(customer);
	}

	/**
	 * 此方法描述的是：新增新单
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月16日 上午12:06:39 
	 * @version: V1.0
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HttpServletRequest request, Noticebill noticebill){
		
		User user = (User) request.getSession().getAttribute("loginUser");
		noticebill.setUser(user);
		
		noticebillService.save(noticebill);
		
		return "qupai/noticebilladd";
	}
	
	@InitBinder 
	public void InitBinder(ServletRequestDataBinder binder) {
		binder.registerCustomEditor(java.util.Date.class,
				new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
