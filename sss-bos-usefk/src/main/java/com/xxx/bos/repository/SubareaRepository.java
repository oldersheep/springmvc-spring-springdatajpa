package com.xxx.bos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.xxx.bos.entity.Subarea;

/** 
  * @className   类名 : SubareaRepository
  * @description 作用 : 分区操作DAO
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月13日 下午3:21:53 
  * @version 版本 : V1.0  
  */
@Repository
public interface SubareaRepository extends JpaRepository<Subarea, String>,JpaSpecificationExecutor<Subarea> {

}
