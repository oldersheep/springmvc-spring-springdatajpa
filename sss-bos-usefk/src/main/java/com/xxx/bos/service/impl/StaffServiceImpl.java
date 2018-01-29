package com.xxx.bos.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.xxx.bos.entity.Staff;
import com.xxx.bos.repository.StaffRepository;
import com.xxx.bos.service.StaffService;
import com.xxx.bos.utils.EntityUtil;

/** 
  * @className   类名 : StaffServiceImpl
  * @description 作用 : TODO(这里用一句话描述这个类的作用) 
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月10日 下午9:45:43 
  * @version 版本 : V1.0  
  */
@Service
public class StaffServiceImpl implements StaffService {
	
	@Autowired
	private StaffRepository staffRepository;

	@Override
	public Page<Staff> findAll(Specification<Staff> specification, Pageable pageable) {
		
		return staffRepository.findAll(specification, pageable);
	}

	@Override
	@Transactional
	public Staff save(Staff staff) {
		
		return staffRepository.save(staff);
	}

	@Override
	@Transactional
	public void deletBatch(String ids) {
		String[] idArr = ids.split(",");
		for(String id : idArr){
			// 这一步可以不判断，但是为了代码的严谨性，走一下这个过程
			// 这个代码其实不需要用那个工具类，只是更改了一个字段而已，这里只是演示一下如何使用
			if(staffRepository.exists(id)){
				Staff staff = new Staff("1");
				Staff _staff = staffRepository.findOne(id);
				EntityUtil.copyNonNullProperties(staff, _staff);
				staffRepository.save(_staff);
			}
		}
		
	}
	
}
