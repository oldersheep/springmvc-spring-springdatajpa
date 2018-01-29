package com.xxx.bos.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className   类名 : Subarea
 * @description 作用 : 分区实体
 * @author  作者 : Lilg
 * @date 创建时间 : 2018年1月10日 下午10:09:47 
 * @version 版本 : V1.0
 */
@Entity
@Table(name="bc_subarea")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subarea implements Serializable {

	private static final long serialVersionUID = 458610202989000757L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="decidedzone_id")
	private Decidedzone decidedzone;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="region_id")
	private Region region;
	
	
	private String addresskey;
	private String startnum;
	private String endnum;
	private String single;
	private String position;

	public Subarea(String id) {
		this.id = id;
	}
	
	public String getSubareaid(){
		return id;
	}

}