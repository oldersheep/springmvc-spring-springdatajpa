package com.xxx.bos.service.impl;

import java.sql.Timestamp;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xxx.bos.entity.Decidedzone;
import com.xxx.bos.entity.Noticebill;
import com.xxx.bos.entity.Staff;
import com.xxx.bos.entity.Workbill;
import com.xxx.bos.repository.DecidedzoneRepository;
import com.xxx.bos.repository.NoticebillRepository;
import com.xxx.bos.repository.WorkbillRepository;
import com.xxx.bos.service.NoticebillService;
import com.yyy.crm.service.CustomerService;

/** 
  * @className   类名 : NoticebillServiceImpl
  * @description 作用 : 业务通知单Service层实现类
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月15日 下午11:52:54 
  * @version 版本 : V1.0  
  */
@Service
public class NoticebillServiceImpl implements NoticebillService {
	
	@Autowired
	private NoticebillRepository noticebillRepository;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private DecidedzoneRepository decidedzoneRepository;
	@Autowired
	private WorkbillRepository workbillRepository;

	@Override
	@Transactional
	public Noticebill save(Noticebill noticebill) {
		
		String address = noticebill.getPickaddress();
		String decidedzoneId = customerService.findDecidedzoneIdByAddress(address);
		if(null!=decidedzoneId){
			// 自动分单
			Decidedzone decidedzone = decidedzoneRepository.findOne(decidedzoneId);
			Staff staff = decidedzone.getStaff();
			noticebill.setStaff(staff);
			noticebill.setOrdertype("自动");
			// 为取派员创建一个工单
			Workbill workbill = new Workbill();
			workbill.setAttachbilltimes(0);//追单次数
			workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));//工单创建的时间
			workbill.setNoticebill(noticebill);//工单关联业务通知单
			workbill.setPickstate("未取件");//取件状态
			workbill.setRemark(noticebill.getRemark());//备注
			workbill.setStaff(staff);//工单关联取派员
			workbill.setType("新单");
			
			workbillRepository.save(workbill);
			// TODO 调用短信服务接口，给取派员发送短信
		} else {
			// 手工分单
			noticebill.setOrdertype("人工");
		}
		
		return noticebillRepository.save(noticebill);
	}

}
