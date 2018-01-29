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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className   类名 : Decidedzone
 * @description 作用 : 定区实体
 * @author  作者 : Lilg
 * @date 创建时间 : 2018年1月10日 下午9:42:18 
 * @version 版本 : V1.0
 */
@Entity
@Table(name="bc_decidedzone")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Decidedzone implements Serializable {

	private static final long serialVersionUID = 7465849022992354436L;
	
	@Id
	@GenericGenerator(name = "id",strategy = "assigned")
	@GeneratedValue(generator = "id")
	private String id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="staff_id")
	private Staff staff;
	private String name;
	
	@OneToMany(cascade={CascadeType.ALL})
	private Set<Subarea> subareas = new HashSet<>(0);

	public Decidedzone(String id) {
		this.id = id;
	}

}