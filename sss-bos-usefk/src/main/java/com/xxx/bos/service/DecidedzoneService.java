package com.xxx.bos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.xxx.bos.entity.Decidedzone;

/** 
  * @className   类名 : DecidedzoneService
  * @description 作用 : 定区的service层接口
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月13日 下午2:29:44 
  * @version 版本 : V1.0  
  */

public interface DecidedzoneService {

	/**
	 * 此方法描述的是：分页查询所有的
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月14日 上午12:26:40 
	 * @version: V1.0
	 */
	Page<Decidedzone> findAll(Specification<Decidedzone> specification, Pageable pageable);

	/**
	 * 此方法描述的是：保存定区对象，同时更新分区的外键
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月14日 上午12:53:26 
	 * @version: V1.0
	 */
	Decidedzone save(Decidedzone decidedzone, String[] subareaid);

	/**
	 * 此方法描述的是：根据id查询定区信息
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月16日 下午8:43:28 
	 * @version: V1.0
	 */
	Decidedzone findById(String id);

}
