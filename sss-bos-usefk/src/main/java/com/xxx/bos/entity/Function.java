package com.xxx.bos.entity;

import java.io.Serializable;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 
  * @className   类名 : Function
  * @description 作用 : TODO(这里用一句话描述这个类的作用) 
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月16日 下午10:43:04 
  * @version 版本 : V1.0  
  */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="auth_function")
public class Function implements Serializable{

	private static final long serialVersionUID = -7137221364047437450L;
	
	@Id
	@GenericGenerator(name = "id",strategy = "assigned")
	@GeneratedValue(generator = "id")
	private String id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="pid")
	private Function function;
	private String name;
	private String code;
	private String description;
	private String page;
	private String generatemenu;
	private Integer zindex;
	
	@OneToMany(cascade={CascadeType.ALL})
	private Set<Function> functions = new HashSet<>(0);
	
	@ManyToMany(targetEntity=Role.class, fetch=FetchType.LAZY)
    @JoinTable(name="auth_role_function", joinColumns={@JoinColumn(name="function_id" )},inverseJoinColumns={@JoinColumn(name="role_id")})
	private Set<Role> roles = new HashSet<>(0);

	public Function(String id) {
		this.id = id;
	}
	
	public String getpId() {
		if(function != null)
			return function.getId();
		
		return "0";
	}
}
