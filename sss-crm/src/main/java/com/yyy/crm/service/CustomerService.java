package com.yyy.crm.service;

import java.util.List;

import com.yyy.crm.entity.Customer;

/**
 * @className 类名 : CustomerService
 * @description 作用 : 模拟CRM系统中客户的一些操作
 * @author 作者 : Lilg
 * @date 创建时间 : 2018年1月14日 下午11:19:50
 * @version 版本 : V1.0
 */

public interface CustomerService {

	/**
	 * 此方法描述的是： 未关联定区客户
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月15日 下午8:47:13 
	 * @version: V1.0
	 */
	List<Customer> findNoAssociationCustomers();

	/**
	 * 此方法描述的是： 查询已经关联指定定区的客户
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月15日 下午8:47:22 
	 * @version: V1.0
	 */
	List<Customer> findHasAssociationCustomers(String decidedZoneId);

	/**
	 * 此方法描述的是： 将未关联定区客户关联到定区上
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月15日 下午8:47:31 
	 * @version: V1.0
	 */
	void assignCustomersToDecidedZone(Integer[] customerIds, String decidedZoneId);

	
	/**
	 * 此方法描述的是： 根据手机号查询客户信息
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月15日 下午11:26:54 
	 * @version: V1.0
	 */
	Customer findCustomerByTelephone(String telephone);
	
	/**
	 * 此方法描述的是：  根据取件地址查询定区id
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月15日 下午11:27:03 
	 * @version: V1.0
	 */
	String findDecidedzoneIdByAddress(String address);
}
