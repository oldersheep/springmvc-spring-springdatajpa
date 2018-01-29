package com.xxx.bos.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xxx.bos.entity.Function;

/** 
  * @className   类名 : FunctionService
  * @description 作用 : 权限Service层接口 
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月17日 下午10:02:29 
  * @version 版本 : V1.0  
  */

public interface FunctionService {

	
	/**
	 * 此方法描述的是： 分页查询所有权限数据 
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月17日 下午10:08:26 
	 * @version: V1.0
	 */
	Page<Function> findAll(Pageable pageable);

	
	/**
	 * 此方法描述的是： 保存权限信息
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月18日 下午9:11:13 
	 * @version: V1.0
	 */
	Function save(Function function);

	/**
	 * 此方法描述的是：根据用户id查询此用户所有的权限
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月21日 下午10:14:10 
	 * @version: V1.0
	 */
	List<Function> findListByUserId(String userId);
}
