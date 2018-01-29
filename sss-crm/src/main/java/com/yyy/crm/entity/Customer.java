package com.yyy.crm.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/** 
  * @className   类名 : Customer
  * @description 作用 : CRM系统的客户表
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月14日 下午11:20:31 
  * @version 版本 : V1.0  
  */
@Entity
@Table(name="t_customer")
@Data
public class Customer implements Serializable {

	private static final long serialVersionUID = 1014180072104118442L;
	
	@Id
	private Integer id;
	private String name;
	private String station;
	private String telephone;
	private String address;

	private String decidedzone_id;
}
