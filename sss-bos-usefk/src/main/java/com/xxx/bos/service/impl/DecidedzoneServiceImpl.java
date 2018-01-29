package com.xxx.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xxx.bos.entity.Decidedzone;
import com.xxx.bos.entity.Subarea;
import com.xxx.bos.repository.DecidedzoneRepository;
import com.xxx.bos.repository.SubareaRepository;
import com.xxx.bos.service.DecidedzoneService;

/** 
  * @className   类名 : DecidedzoneServiceImpl
  * @description 作用 : 定区的service层实现类
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月13日 下午2:29:56 
  * @version 版本 : V1.0  
  */
@Service
public class DecidedzoneServiceImpl implements DecidedzoneService {

	@Autowired
	private DecidedzoneRepository decidedzoneRepository;
	
	@Autowired
	private SubareaRepository subareaRepository;

	@Override
	public Page<Decidedzone> findAll(Specification<Decidedzone> specification, Pageable pageable) {
		
		return decidedzoneRepository.findAll(specification, pageable);
	}

	@Override
	@Transactional
	public Decidedzone save(Decidedzone decidedzone, String[] subareaid) {
		Decidedzone _decidedzone = decidedzoneRepository.save(decidedzone);
		for(String id : subareaid){
			// 这里写的估计不对，但是还没找到合适的方法
			Subarea subarea = subareaRepository.findOne(id);
			subarea.setDecidedzone(decidedzone);
			subareaRepository.save(subarea);
		}
		
		return _decidedzone;
	}

	@Override
	public Decidedzone findById(String id) {
		
		return decidedzoneRepository.findOne(id);
	}
	
	
}
