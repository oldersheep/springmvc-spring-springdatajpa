package com.xxx.bos.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.xxx.bos.dto.EasyUIResult;
import com.xxx.bos.dto.SysResult;
import com.xxx.bos.entity.Staff;
import com.xxx.bos.service.StaffService;

/**
 * @className 类名 : StaffController
 * @description 作用 : 取派员
 * @author 作者 : Lilg
 * @date 创建时间 : 2018年1月10日 下午9:48:28
 * @version 版本 : V1.0
 */
@Controller
@RequestMapping("/staff")
public class StaffController {

	private static final Logger LOG = LoggerFactory.getLogger(StaffController.class);

	@Autowired
	private StaffService staffService;

	/**
	 * 此方法描述的是： 分页查询类表，应该按倒序查
	 * 
	 * @author: Lilg
	 * @date 创建时间 : 2018年1月10日 下午10:58:15
	 * @version: V1.0
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyUIResult list(Staff staff, int page, int rows) {

		Pageable pageable = new PageRequest(page - 1, rows);

		Specification<Staff> specification = new Specification<Staff>(){
			// root其实就是要查询的实体类型
			// query添加查询条件
			// criteriaBuilder 构建一个Predicate
			@Override
			public Predicate toPredicate(Root<Staff> root,
										 CriteriaQuery<?> query,
										 CriteriaBuilder criteriaBuilder) {
				
				List<Predicate> predicates = new ArrayList<Predicate>();
				
				String name = staff.getName();
				String telephone = staff.getTelephone();
				String station = staff.getStation();
				if(StringUtils.isNotBlank(name))
					predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
				if(StringUtils.isNotBlank(telephone))
					predicates.add(criteriaBuilder.like(root.get("telephone"), "%" + telephone + "%"));
				if(StringUtils.isNotBlank(station))
					predicates.add(criteriaBuilder.like(root.get("station"), "%" + station + "%"));
				
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};

		Page<Staff> staffPage = staffService.findAll(specification, pageable);
		
		return new EasyUIResult(staffPage.getTotalElements(), staffPage.getContent());
	}
	
	/**
	 * 此方法描述的是：ajax请求未删除的派送员信息
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月14日 上午1:12:26 
	 * @version: V1.0
	 */
	@RequestMapping(value = "/listAjax", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String listAjax(){
		// 条件
		Specification<Staff> specification = new Specification<Staff>(){
			@Override
			public Predicate toPredicate(Root<Staff> root,
										 CriteriaQuery<?> query,
										 CriteriaBuilder criteriaBuilder) {
				
				Path<String> path = root.get("deltag");
				
				return criteriaBuilder.equal(path, "0");
			}
		};
		List<Staff> staffList = staffService.findAll(specification, null).getContent();
		
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Staff.class, "id", "name");
		LOG.info("查询完成，转json成功！！");
		return JSONObject.toJSONString(staffList, filter);
	}

	/**
	 * 此方法描述的是： 新增取派员信息，这一块页面设计的不好，所以这里并没有什么返回DTO，而是直接将页面返回
	 * 
	 * @author: Lilg
	 * @date 创建时间 : 2018年1月10日 下午11:07:00
	 * @version: V1.0
	 */
	@RequestMapping("/save")
	@ResponseBody
	public SysResult save(Staff staff) {
		try{
			staffService.save(staff);
			LOG.info("保存成功，不知是新增还是修改！");
			return SysResult.ok();
		}catch(Exception e){
			e.printStackTrace();
			LOG.error(e.getMessage());
			return SysResult.build(201, "保存出错，请联系管理员！");
		}
	}

	/**
	 * 此方法描述的是： 作废取派员信息，修改状态，可以多条
	 * 
	 * @author: Lilg
	 * @date 创建时间 : 2018年1月11日 下午8:52:14
	 * @version: V1.0
	 */
	@RequestMapping("/delete")
	public String delete(String ids) {
		staffService.deletBatch(ids);
		LOG.info("删除成功！");
		return "base/staff";
	}

}
