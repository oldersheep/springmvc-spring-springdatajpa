package com.xxx.bos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.xxx.bos.entity.Subarea;

/** 
  * @className   类名 : SubareaService
  * @description 作用 : 分区的service层接口
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月13日 下午3:22:52 
  * @version 版本 : V1.0  
  */

public interface SubareaService {

	/**
	 * 此方法描述的是： 分页+条件查询所有信息
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月13日 下午3:28:34 
	 * @version: V1.0
	 * @param specification 
	 */
	Page<Subarea> findAll(Specification<Subarea> specification, Pageable pageable);

	/**
	 * 此方法描述的是：保存或更新分区信息
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月13日 下午3:36:16 
	 * @version: V1.0
	 */
	Subarea save(Subarea subarea);

}
