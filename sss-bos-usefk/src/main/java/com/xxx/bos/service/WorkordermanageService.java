package com.xxx.bos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xxx.bos.entity.Workordermanage;

/** 
  * @className   类名 : WorkordermanageService
  * @description 作用 : 工作单Service层接口
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月16日 下午9:48:41 
  * @version 版本 : V1.0  
  */

public interface WorkordermanageService {

	
	/**
	 * 此方法描述的是：分页查询工单类表信息
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月16日 下午10:07:48 
	 * @version: V1.0
	 */
	Page<Workordermanage> findAll(Pageable pageable);
	
	/**
	 * 此方法描述的是： 保存工作单信息
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月16日 下午9:56:10 
	 * @version: V1.0
	 */
	Workordermanage save(Workordermanage workordermanage);

}
