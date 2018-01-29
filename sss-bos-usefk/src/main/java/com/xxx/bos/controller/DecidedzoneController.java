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
import com.xxx.bos.entity.Decidedzone;
import com.xxx.bos.service.DecidedzoneService;
import com.yyy.crm.entity.Customer;
import com.yyy.crm.service.CustomerService;



/** 
  * @className   类名 : DecidedzoneController
  * @description 作用 : 定区的表现层
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月13日 下午2:31:45 
  * @version 版本 : V1.0  
  */
@Controller
@RequestMapping("/decidedzone")
public class DecidedzoneController {
	
	private static final Logger LOG = Logger.getLogger(DecidedzoneController.class);
	
	@Autowired
	private DecidedzoneService decidedzoneService;
	
	@Autowired
	private CustomerService customerService;
	
	/**
	 * 此方法描述的是： 条件+分页查询，条件还没做
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月20日 下午5:34:55 
	 * @version: V1.0
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyUIResult list(int page, int rows){
		
		Pageable pageable = new PageRequest(page - 1, rows);
		Page<Decidedzone> decidedzonePage = decidedzoneService.findAll(null, pageable);
		LOG.info("查询成功！！");
		return new EasyUIResult(decidedzonePage.getTotalElements(), decidedzonePage.getContent());
	}
	
	
	@RequestMapping("/save")
	@ResponseBody
	public SysResult save(Decidedzone decidedzone, String[] subareaid){
		try{
			decidedzoneService.save(decidedzone, subareaid);
			LOG.info("保存成功，不知是新增还是修改！");
			return SysResult.ok();
		}catch(Exception e){
			e.printStackTrace();
			LOG.error(e.getMessage());
			return SysResult.build(201, "保存出错，请联系管理员！");
		}
	}
	
	/**
	 * 此方法描述的是： 查询未被指定的客户
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月15日 下午10:42:59 
	 * @version: V1.0
	 */
	@RequestMapping(value = "/findNoAssociationCustomers", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String findNoAssociationCustomers(){
		List<Customer> customerList = customerService.findNoAssociationCustomers();
		
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Customer.class, "id", "name"); 
		return JSONObject.toJSONString(customerList, filter);
	}
	
	/**
	 * 此方法描述的是： 查询已被关联的客户
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月15日 下午10:43:14 
	 * @version: V1.0
	 */
	@RequestMapping(value = "/findHasAssociationCustomers", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String findHasAssociationCustomers(String id){
		List<Customer> customerList = customerService.findHasAssociationCustomers(id);
		
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Customer.class, "id", "name"); 
		return JSONObject.toJSONString(customerList, filter);
	}
	
	/**
	 * 此方法描述的是： 关联客户
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月15日 下午10:42:44 
	 * @version: V1.0
	 */
	@RequestMapping("/assignCustomerstodecidedzone")
	public String assignCustomerstodecidedzone(Integer[] customerIds, String id){
		customerService.assignCustomersToDecidedZone(customerIds, id);
		return "base/decidedzone";
	}

}
