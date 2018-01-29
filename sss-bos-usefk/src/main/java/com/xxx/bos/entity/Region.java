package com.xxx.bos.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @className   类名 : Region
 * @description 作用 : 区域实体
 * @author  作者 : Lilg
 * @date 创建时间 : 2018年1月10日 下午10:09:08 
 * @version 版本 : V1.0
 */
@Entity
@Table(name="bc_region")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Region implements Serializable {

	private static final long serialVersionUID = -6584543463810307988L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	private String province;
	private String city;
	private String district;
	private String postcode;
	private String shortcode;
	private String citycode;
	
	@OneToMany(cascade={CascadeType.ALL})
	private Set<Subarea> subareas = new HashSet<>(0);

	public Region(String id) {
		this.id = id;
	}
	
	public String getName(){
		return province + city +district;
	}

}