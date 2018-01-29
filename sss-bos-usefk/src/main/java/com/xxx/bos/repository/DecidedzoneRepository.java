package com.xxx.bos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.xxx.bos.entity.Decidedzone;

/** 
  * @className   类名 : DecidedzoneRepository
  * @description 作用 : 定区操作DAO
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月13日 下午2:27:54 
  * @version 版本 : V1.0  
  */
@Repository
public interface DecidedzoneRepository extends JpaRepository<Decidedzone, String>, JpaSpecificationExecutor<Decidedzone> {

}
