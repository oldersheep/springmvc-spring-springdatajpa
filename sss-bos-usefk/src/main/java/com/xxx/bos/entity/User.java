package com.xxx.bos.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * @className   类名 : User
 * @description 作用 : 用户的实体
 * @author  作者 : Lilg
 * @date 创建时间 : 2018年1月10日 下午10:10:02 
 * @version 版本 : V1.0
 */
@Data
@Table(name="auth_user")
@Entity
public class User implements Serializable{
	
	private static final long serialVersionUID = -6561991589942036214L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	private String username;
	private String password;
	private Double salary;
	private Date birthday;
	private String gender;
	private String station;
	private String telephone;
	private String remark;
	
	@OneToMany(cascade={CascadeType.ALL})
	private Set<Noticebill> noticebills = new HashSet<Noticebill>();
	
	@ManyToMany(targetEntity=Role.class, fetch=FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name="auth_user_role", joinColumns={@JoinColumn(name ="user_id")}, inverseJoinColumns={@JoinColumn(name="role_id")})
	private Set<Role> roles = new HashSet<Role>();
	
}
