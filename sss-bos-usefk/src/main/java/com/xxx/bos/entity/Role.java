package com.xxx.bos.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 
  * @className   类名 : Role
  * @description 作用 : TODO(这里用一句话描述这个类的作用) 
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月16日 下午10:45:04 
  * @version 版本 : V1.0  
  */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="auth_role")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	private String name;
	private String code;
	private String description;
	
	@ManyToMany//(mappedBy="roles")
	private Set<Function> functions = new HashSet<>(0);
	
	@ManyToMany//(mappedBy="roles")
	private Set<User> users = new HashSet<>(0);

}
