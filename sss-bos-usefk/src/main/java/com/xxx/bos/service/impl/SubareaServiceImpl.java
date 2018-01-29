package com.xxx.bos.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.xxx.bos.entity.Subarea;
import com.xxx.bos.repository.SubareaRepository;
import com.xxx.bos.service.SubareaService;

/** 
  * @className   类名 : SubareaServiceImpl
  * @description 作用 : 区域操作的Service层实现类
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月13日 下午3:23:22 
  * @version 版本 : V1.0  
  */
@Service
public class SubareaServiceImpl implements SubareaService {
	
	@Autowired
	private SubareaRepository subareaRepository;

	@Override
	public Page<Subarea> findAll(Specification<Subarea> specification, Pageable pageable) {
		
		return subareaRepository.findAll(specification, pageable);
	}

	@Override
	@Transactional
	public Subarea save(Subarea subarea) {
		
		return subareaRepository.save(subarea);
	}

}
