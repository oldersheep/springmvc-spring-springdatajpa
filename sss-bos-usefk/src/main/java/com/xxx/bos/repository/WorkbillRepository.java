package com.xxx.bos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.xxx.bos.entity.Workbill;

/** 
  * @className   类名 : WorkbillRepository
  * @description 作用 : 工单的DAO层操作
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月16日 下午8:48:25 
  * @version 版本 : V1.0  
  */

public interface WorkbillRepository extends JpaRepository<Workbill, String>, JpaSpecificationExecutor<Workbill> {

}
