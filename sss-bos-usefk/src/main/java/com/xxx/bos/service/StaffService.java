package com.xxx.bos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.xxx.bos.entity.Staff;

/** 
  * @className   类名 : StaffService
  * @description 作用 : TODO(这里用一句话描述这个类的作用) 
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月10日 下午9:45:29 
  * @version 版本 : V1.0  
  */

public interface StaffService {

	/**
	 * 此方法描述的是： 分页+条件查询所有记录
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月10日 下午10:27:56 
	 * @version: V1.0
	 */
	Page<Staff> findAll(Specification<Staff> specification, Pageable pageable);
	
	/**
	 * 此方法描述的是： 保存或更新取派员信息
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月10日 下午10:16:27 
	 * @version: V1.0
	 */
	Staff save(Staff staff);

	/**
	 * 此方法描述的是： 批量作废取派员，将删除标志位置1
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月10日 下午11:13:01 
	 * @version: V1.0
	 */
	void deletBatch(String ids);

}
