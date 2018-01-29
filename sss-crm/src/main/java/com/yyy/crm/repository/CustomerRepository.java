package com.yyy.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yyy.crm.entity.Customer;

/** 
  * @className   类名 : CustomerRepository
  * @description 作用 : CRM系统客户表的DAO层
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月15日 下午8:48:06 
  * @version 版本 : V1.0  
  */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>, JpaSpecificationExecutor<Customer> {

	/**
	 * 此方法描述的是：将decidedzone_id置空
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月15日 下午9:00:02 
	 * @version: V1.0
	 */
	@Modifying
	@Query("update Customer c set c.decidedzone_id = null where decidedzone_id = ?1")
	int updateDecidedzoneIdToNull(String decidedzoneId);
	
	/**
	 * 此方法描述的是： 根据id更新定区id
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月15日 下午9:02:16 
	 * @version: V1.0
	 */
	@Modifying
	@Query("update Customer c set c.decidedzone_id = ?1 where id = ?2")
	int updateDecidedzoneIdById(String decidedZoneId, Integer id);

	/**
	 * 此方法描述的是： 根据用户手机号查询用户
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月15日 下午11:38:44 
	 * @version: V1.0
	 */
	Customer findByTelephone(String telephone);
	
	/**
	 * 此方法描述的是： 根据地址模糊查询定区id
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月15日 下午11:38:23 
	 * @version: V1.0
	 */
	Customer findByAddress(String address);
}
