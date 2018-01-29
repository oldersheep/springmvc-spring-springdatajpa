package com.xxx.bos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xxx.bos.entity.Function;

/** 
  * @className   类名 : FunctionRepository
  * @description 作用 : TODO(这里用一句话描述这个类的作用) 
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月17日 下午10:01:38 
  * @version 版本 : V1.0  
  */
@Repository
public interface FunctionRepository extends JpaRepository<Function, String>, JpaSpecificationExecutor<Function> {

	/**
	 * 此方法描述的是：根据用户id查询当前用户的权限
	 * 这个hql的方式有问题，问题在于好像有个中间表没有按照预期的来
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月21日 下午10:28:44 
	 * @version: V1.0
	 */
	// @Query(value = "SELECT DISTINCT f FROM Function f LEFT OUTER JOIN f.roles r LEFT OUTER JOIN r.users u WHERE u.id=:userId")
	@Query(nativeQuery = true, value = "select f.* from auth_function f left join auth_role_function rf on f.id = rf.function_id left join auth_user_role ur on rf.role_id = ur.role_id where ur.user_id=:userId")
	List<Function> findListByUserId(@Param("userId") String userId);

}
