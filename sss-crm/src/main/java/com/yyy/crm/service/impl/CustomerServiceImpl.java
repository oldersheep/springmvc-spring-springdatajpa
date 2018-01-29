package com.yyy.crm.service.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import com.yyy.crm.entity.Customer;
import com.yyy.crm.repository.CustomerRepository;
import com.yyy.crm.service.CustomerService;

/** 
  * @className   类名 : CustomerServiceImpl
  * @description 作用 :  
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月14日 下午11:20:06 
  * @version 版本 : V1.0  
  */

public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public List<Customer> findNoAssociationCustomers() {
		Specification<Customer> specification = new Specification<Customer>(){
			// root其实就是要查询的实体类型
			// query添加查询条件
			// criteriaBuilder 构建一个Predicate
			@Override
			public Predicate toPredicate(Root<Customer> root,
										 CriteriaQuery<?> query,
										 CriteriaBuilder criteriaBuilder) {
				
				Path<String> path = root.get("decidedzone_id");
				
				return criteriaBuilder.isNull(path);
			}
		};
		
		return customerRepository.findAll(specification);
	}

	@Override
	public List<Customer> findHasAssociationCustomers(String decidedZoneId) {
		
		Specification<Customer> specification = new Specification<Customer>(){
			// root其实就是要查询的实体类型
			// query添加查询条件
			// criteriaBuilder 构建一个Predicate
			@Override
			public Predicate toPredicate(Root<Customer> root,
										 CriteriaQuery<?> query,
										 CriteriaBuilder criteriaBuilder) {
				
				Path<String> path = root.get("decidedzone_id");
				
				return criteriaBuilder.equal(path, decidedZoneId);
			}
		};
		
		return customerRepository.findAll(specification);
	}

	@Override
	@Transactional
	public void assignCustomersToDecidedZone(Integer[] customerIds, String decidedZoneId) {
		
		// 取消定区所有关联客户
		customerRepository.updateDecidedzoneIdToNull(decidedZoneId);
		
		// 进行关联
		if (customerIds != null) {
			for (Integer id : customerIds) {
				customerRepository.updateDecidedzoneIdById(decidedZoneId, id);
			}
		}
		
	}

	@Override
	public Customer findCustomerByTelephone(String telephone) {
		
		return customerRepository.findByTelephone(telephone);
	}

	@Override
	public String findDecidedzoneIdByAddress(String address) {
		
		return customerRepository.findByAddress(address).getDecidedzone_id();
	}

}
