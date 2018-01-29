package com.xxx.bos.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xxx.bos.entity.Region;
import com.xxx.bos.repository.RegionRepository;
import com.xxx.bos.service.RegionService;

/** 
  * @className   类名 : RegionServiceImpl
  * @description 作用 : TODO(这里用一句话描述这个类的作用) 
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月11日 下午9:31:42 
  * @version 版本 : V1.0  
  */
@Service
public class RegionServiceImpl implements RegionService {
	
	@Autowired
	private RegionRepository regionRepository;

	@Override
	public Page<Region> findAll(Pageable pageable) {
		
		return regionRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public List<Region> saveBatch(List<Region> list) {
		
		return regionRepository.save(list);
	}

	@Override
	@Transactional
	public Region save(Region region) {
		
		return regionRepository.save(region);
	}

	@Override
	@Transactional
	public void deletBatch(String ids) {
		String[] idArr = ids.split(",");
		for(String id : idArr){
			
			regionRepository.delete(id);
		}
	}

}
