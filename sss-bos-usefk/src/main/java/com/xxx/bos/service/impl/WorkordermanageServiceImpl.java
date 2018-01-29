package com.xxx.bos.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xxx.bos.entity.Workordermanage;
import com.xxx.bos.repository.WorkordermanageRepository;
import com.xxx.bos.service.WorkordermanageService;

/** 
  * @className   类名 : WorkordermanageServiceImpl
  * @description 作用 : 工作单Service层实现类
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月16日 下午9:48:55 
  * @version 版本 : V1.0  
  */
@Service
public class WorkordermanageServiceImpl implements WorkordermanageService {
	
	@Autowired
	private WorkordermanageRepository workordermanageRepository;

	@Override
	public Page<Workordermanage> findAll(Pageable pageable) {
		
		return workordermanageRepository.findAll(pageable);
	}
	
	@Override
	@Transactional
	public Workordermanage save(Workordermanage workordermanage) {
		
		return workordermanageRepository.save(workordermanage);
	}

}
