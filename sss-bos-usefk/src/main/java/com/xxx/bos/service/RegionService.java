package com.xxx.bos.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xxx.bos.entity.Region;

/** 
  * @className   类名 : RegionService
  * @description 作用 : TODO(这里用一句话描述这个类的作用) 
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月11日 下午9:31:27 
  * @version 版本 : V1.0  
  */

public interface RegionService {
	
	/**
	 * 此方法描述的是： 分页查询所有区域信息
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月11日 下午9:36:29 
	 * @version: V1.0
	 */
	Page<Region> findAll(Pageable pageable);

	/**
	 * 此方法描述的是： 批量导入
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月11日 下午10:17:52 
	 * @version: V1.0
	 */
	List<Region> saveBatch(List<Region> list);

	/**
	 * 此方法描述的是：新增区域信息
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月12日 下午10:37:58 
	 * @version: V1.0
	 */
	Region save(Region region);

	/**
	 * 此方法描述的是： 根据id批量删除
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月12日 下午11:29:58 
	 * @version: V1.0
	 */
	void deletBatch(String ids);

}
