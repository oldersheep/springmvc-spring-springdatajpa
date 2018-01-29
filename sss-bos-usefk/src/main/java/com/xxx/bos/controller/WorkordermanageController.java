package com.xxx.bos.controller;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xxx.bos.dto.EasyUIResult;
import com.xxx.bos.dto.SysResult;
import com.xxx.bos.entity.Workordermanage;
import com.xxx.bos.service.WorkordermanageService;

/** 
  * @className   类名 : WorkordermanageController
  * @description 作用 : 工作单的表现层
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月16日 下午9:50:19 
  * @version 版本 : V1.0  
  */
@Controller
@RequestMapping("/workordermanage")
public class WorkordermanageController {
	
	private static final Logger LOG = Logger.getLogger(WorkordermanageController.class);
	@Autowired
	private WorkordermanageService workordermanageService;
	
	
	/**
	 * 此方法描述的是： 分页+排序查询所有的工单信息
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月16日 下午10:01:29 
	 * @version: V1.0
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyUIResult list(int page, int rows){
		
		Sort.Order order = new Sort.Order(Sort.Direction.DESC, "updatetime");
		Sort sort = new Sort(order);
		Pageable pageable = new PageRequest(page - 1, rows, sort);
		Page<Workordermanage> workordermanagePage = workordermanageService.findAll(pageable);
		LOG.info("查询成功！！");
		return new EasyUIResult(workordermanagePage.getTotalElements(), workordermanagePage.getContent());
	}
	
	
	/**
	 * 此方法描述的是：快速保存工单信息
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月16日 下午10:00:24 
	 * @version: V1.0
	 */
	@RequestMapping("/add")
	@ResponseBody
	public SysResult add(Workordermanage workordermanage){
		try{
			workordermanage.setUpdatetime(new Date());
			workordermanageService.save(workordermanage);
			return SysResult.ok();
		}catch(Exception e){
			e.printStackTrace();
			LOG.error(e.getMessage());
			return SysResult.build(201, "出现异常");
		}
		
	}

}
