package com.xxx.bos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xxx.bos.entity.User;

/**
 * @className 类名 : UserRepository
 * @description 作用 : 针对用户操作的DAO层接口
 * @author 作者 : Lilg
 * @date 创建时间 : 2017年12月31日 下午11:21:43
 * @version 版本 : V1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

	/**
	 * 此方法描述的是： 根据用户名和密码查找唯一用户，未加权限时用
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月10日 下午8:40:40 
	 * @version: V1.0
	 */
	User findByUsernameAndPassword(String username, String password);

	/**
	 * 此方法描述的是： 根据用户id更新密码
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月10日 下午8:41:00 
	 * @version: V1.0
	 */
	@Modifying
	@Query("update User u set u.password = :password where u.id = :id")
	void updatePasswordById(@Param("id") String id, @Param("password") String password);

	/**
	 * 此方法描述的是：根据用户名查找用户
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月17日 下午9:13:03 
	 * @version: V1.0
	 */
	User findByUsername(String username);

	/**
	 * 此方法描述的是：保存用户与角色的关联关系，保存在关联表中
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月21日 下午8:28:31 
	 * @version: V1.0
	 */
	@Modifying
	@Query(nativeQuery = true, value = "insert auth_user_role(user_id, role_id) values (:userId, :roleId)")
	Integer saveUserAndRole(@Param("userId") String userId, @Param("roleId") String roleId);

}
