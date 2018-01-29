package com.xxx.bos.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 
  * @className   类名 : Staff
  * @description 作用 : 取派员实体
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月10日 下午9:38:10 
  * @version 版本 : V1.0  
  */
@Table(name="bc_staff")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Staff implements Serializable {
	
	private static final long serialVersionUID = -1039374395707175502L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	private String name;
	private String telephone;
	private String haspda = "0";// 是否有PDA 1：有 0：无
	private String deltag = "0";// 删除标识位 1：已删除 0：未删除
	private String station;
	private String standard;
	
	@OneToMany
	@LazyCollection(LazyCollectionOption.EXTRA) //LazyCollection属性设置成EXTRA指定了当如果查询数据的个数时候，只会发出一条 count(*)的语句，提高性能
	private Set<Decidedzone> decidedzones = new HashSet<Decidedzone>(0);
	
	/** 此构造函数就是为 作废 量身定做 */
	public Staff(String deltag){
		this.deltag = deltag;
	}
}
