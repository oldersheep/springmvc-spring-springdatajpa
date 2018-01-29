package com.xxx.bos.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/** 
  * @className   类名 : Workordermanage
  * @description 作用 : 工作单
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月15日 下午11:03:53 
  * @version 版本 : V1.0  
  */
@Data
@Entity
@Table(name="qp_workordermanage")
public class Workordermanage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name = "id",strategy = "assigned")
	@GeneratedValue(generator = "id")
	private String id;
	private String arrivecity;//到达城市
	private String product;//产品
	private Integer num;//数量
	
	@Column(name="weight", precision=22, scale=0)
	private Double weight;//重量
	private String floadreqr;//配置要求
	private String prodtimelimit;//产品时限
	private String prodtype;//产品类型
	private String sendername; //寄件人姓名
	private String senderphone;//寄件人电话
	private String senderaddr; //寄件人地址
	private String receivername;  //收件人姓名
	private String receiverphone; //收件人电话
	private String receiveraddr;  //收件人地址
	private Integer feeitemnum;//计费件数
	
	@Column(name="actlweit", precision=22, scale=0)
	private Double actlweit;//实际重量
	private String vol;//体积
	private String managerCheck;//是否审核配送
	private Date updatetime;//系统时间
}
