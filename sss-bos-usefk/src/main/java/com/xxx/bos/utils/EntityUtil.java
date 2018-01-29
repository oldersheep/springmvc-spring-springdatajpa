package com.xxx.bos.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * @className 类名 : EntityUtil
 * @description 作用 : 针对实体操作的工具类
 * @author 作者 : Lilg
 * @date 创建时间 : 2017年12月31日 下午11:21:43
 * @version 版本 : V1.0
 */
public class EntityUtil {

	/**
	 * 将source中属性值不为null的属性更新到target对应的属性上
	 * 此方法是为“spring data jpa中更新单个实体时只更新不为null的字段”量身定做，保存时依旧保存dest
	 * 究竟要不要考虑值为""时呢
	 * 
	 * @param source : 带有要更新的字段的值的源对象
	 * @param target : 从数据库根据id查询出来的目标对象
	 */
	public static void copyNonNullProperties(Object source, Object target) {
		// 将为空的properties给找出来,放在Set中
		BeanWrapper srcBean = new BeanWrapperImpl(source);
		PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
		Set<String> emptyName = new HashSet<>();
		for (PropertyDescriptor p : pds) {
			// 如果某个某个属性的属性值为null，就记录到Set中
			if (null == srcBean.getPropertyValue(p.getName()))
				emptyName.add(p.getName());
		}
		
		BeanUtils.copyProperties(source, target, emptyName.toArray(new String[emptyName.size()]));
	}
	
}
