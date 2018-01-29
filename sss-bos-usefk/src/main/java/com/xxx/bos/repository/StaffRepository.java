package com.xxx.bos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.xxx.bos.entity.Staff;

/** 
  * @className   类名 : StaffRepository
  * @description 作用 : 取派员操作DAO
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月10日 下午9:45:10 
  * @version 版本 : V1.0  
  */
@Repository
public interface StaffRepository extends JpaRepository<Staff, String>, JpaSpecificationExecutor<Staff>{

}
