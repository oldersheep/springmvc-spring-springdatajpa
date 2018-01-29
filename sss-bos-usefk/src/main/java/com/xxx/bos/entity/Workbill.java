package com.xxx.bos.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/** 
  * @className   类名 : Workbill
  * @description 作用 : 工单
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月15日 下午11:02:46 
  * @version 版本 : V1.0  
  */
@Data
@Entity
@Table(name="qp_workbill")
public class Workbill implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="noticebill_id")
	private Noticebill noticebill;//工单对应的业务通知单
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="staff_id")
	private Staff staff;//工单对应的取派员
	
	private String type;//工单类型：新、追、改、销
	private String pickstate;//取件状态：未取件、取件中、已取件
	private Timestamp buildtime;//系统时间
	private Integer attachbilltimes;//追单次数
	private String remark;//备注
}
