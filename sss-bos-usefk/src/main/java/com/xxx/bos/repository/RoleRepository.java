package com.xxx.bos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xxx.bos.entity.Role;

/** 
  * @className   类名 : RoleRepository
  * @description 作用 : TODO(这里用一句话描述这个类的作用) 
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月21日 下午4:08:09 
  * @version 版本 : V1.0  
  */
@Repository
public interface RoleRepository extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role> {

	@Modifying
	@Query(nativeQuery = true, value = "insert auth_role_function(role_id, function_id) values (:roleId, :functionId)")
	Integer saveRoleAndFunction(@Param("roleId") String roleId, @Param("functionId") String functionId);
}
