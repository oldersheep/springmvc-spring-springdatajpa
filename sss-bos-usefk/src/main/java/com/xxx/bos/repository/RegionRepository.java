package com.xxx.bos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xxx.bos.entity.Region;

/** 
  * @className   类名 : RegionRepository
  * @description 作用 : 区域操作的DAO 
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月11日 下午9:29:27 
  * @version 版本 : V1.0  
  */
@Repository
public interface RegionRepository extends JpaRepository<Region, String>{

}
