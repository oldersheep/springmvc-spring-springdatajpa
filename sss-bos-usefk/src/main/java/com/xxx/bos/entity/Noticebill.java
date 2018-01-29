package com.xxx.bos.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/** 
  * @className   类名 : Noticebill
  * @description 作用 : 业务通知单
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月15日 下午11:01:19 
  * @version 版本 : V1.0  
  */
@Data
@Entity
@Table(name="qp_noticebill")
public class Noticebill implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;//业务员
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="staff_id")
	private Staff staff;//当前业务通知单对应的取派员
	
	private String customerId;//客户id
	private String customerName;//客户姓名
	private String delegater;//联系人
	private String telephone;//电话
	private String pickaddress;//取件地址
	private String arrivecity;//到达城市
	private String product;//产品
	
	//@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date pickdate;//取件时间
	private Integer num;//数量
	
	@Column(name="weight", precision=22, scale=0)
	private Double weight;//重量
	private String volume;//体积
	private String remark;//备注
	private String ordertype;//分单类型：自动、人工
	
	@OneToMany(cascade={CascadeType.ALL})
	private Set<Workbill> workbills = new HashSet<>(0);//当前业务通知单对应的工单
}
