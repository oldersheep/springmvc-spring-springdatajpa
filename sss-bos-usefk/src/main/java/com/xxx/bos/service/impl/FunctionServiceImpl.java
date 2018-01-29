package com.xxx.bos.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xxx.bos.entity.Function;
import com.xxx.bos.repository.FunctionRepository;
import com.xxx.bos.service.FunctionService;

/** 
  * @className   类名 : FunctionServiceImpl
  * @description 作用 : 权限的Service实现类
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月17日 下午10:02:52 
  * @version 版本 : V1.0  
  */

@Service
public class FunctionServiceImpl implements FunctionService {
	
	@Autowired
	private FunctionRepository functionRepository;

	@Override
	public Page<Function> findAll(Pageable pageable) {
		
		return functionRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public Function save(Function function) {
		
		return functionRepository.save(function);
	}

	@Override
	public List<Function> findListByUserId(String userId) {
		
		return functionRepository.findListByUserId(userId);
	}

	
}
