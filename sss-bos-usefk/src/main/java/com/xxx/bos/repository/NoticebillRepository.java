package com.xxx.bos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.xxx.bos.entity.Noticebill;

/** 
  * @className   类名 : NoticebillRepository
  * @description 作用 : 业务通知单DAO层操作
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月15日 下午11:51:19 
  * @version 版本 : V1.0  
  */
@Repository
public interface NoticebillRepository extends JpaRepository<Noticebill, String>, JpaSpecificationExecutor<Noticebill> {

}
