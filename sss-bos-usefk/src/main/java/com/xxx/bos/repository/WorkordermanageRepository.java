package com.xxx.bos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.xxx.bos.entity.Workordermanage;

/** 
  * @className   类名 : WorkordermanageRepository
  * @description 作用 : 工作单DAO层操作
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月16日 下午9:47:16 
  * @version 版本 : V1.0  
  */
@Repository
public interface WorkordermanageRepository extends JpaRepository<Workordermanage, String>, JpaSpecificationExecutor<Workordermanage> {

}
