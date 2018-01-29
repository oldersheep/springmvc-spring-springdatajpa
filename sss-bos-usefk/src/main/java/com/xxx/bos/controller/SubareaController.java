package com.xxx.bos.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import com.xxx.bos.entity.Region;
import com.xxx.bos.entity.Subarea;
import com.xxx.bos.service.SubareaService;
import com.xxx.bos.utils.FileUtil;

/** 
  * @className   类名 : SubareaController
  * @description 作用 : 区域操作的表现层
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月13日 下午3:24:36 
  * @version 版本 : V1.0  
  */
@Controller
@RequestMapping("/subarea")
public class SubareaController {

	private static final Logger LOG = Logger.getLogger(SubareaController.class);
	@Autowired
	private SubareaService subareaService;
	
	@RequestMapping("/list")
	@ResponseBody
	public EasyUIResult list(int page, int rows, Subarea subarea){
		// 分页
		Pageable pageable = new PageRequest(page-1, rows);
		// 条件
		Specification<Subarea> specification = new Specification<Subarea>(){
			// root其实就是要查询的实体类型
			// query添加查询条件
			// criteriaBuilder 构建一个Predicate
			@Override
			public Predicate toPredicate(Root<Subarea> root,
										 CriteriaQuery<?> query,
										 CriteriaBuilder criteriaBuilder) {
				// 如果条件为空，直接返回
				if(subarea == null)
					return null;
					
				List<Predicate> predicates = new ArrayList<Predicate>();
				String addresskey = subarea.getAddresskey();
				if(StringUtils.isNotBlank(addresskey)){
					predicates.add(criteriaBuilder.like(root.get("addresskey").as(String.class), "%" + addresskey + "%"));
				}
				Region region = subarea.getRegion();
				// 如果区域为空，条件拼接到此结束
				if(region == null)
					return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
				
				String province = region.getProvince();
				String city = region.getCity();
				String district = region.getDistrict();
				if(StringUtils.isNotBlank(province)){
					predicates.add(criteriaBuilder.like(root.<String> get("region").<String> get("province"), "%" + province + "%"));
				}
				if(StringUtils.isNotBlank(city)){
					predicates.add(criteriaBuilder.like(root.<String> get("region").<String> get("city"), "%" + city + "%"));
				}
				if(StringUtils.isNotBlank(district)){
					predicates.add(criteriaBuilder.like(root.<String> get("region").<String> get("district"), "%" + district + "%"));
				}
				
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		
		Page<Subarea> subareaPage = subareaService.findAll(specification, pageable);
		
		return new EasyUIResult(subareaPage.getTotalElements(), subareaPage.getContent());
	}
	
	@RequestMapping(value = "/listAjax", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String listAjax(){
		// 条件
		Specification<Subarea> specification = new Specification<Subarea>(){
			@Override
			public Predicate toPredicate(Root<Subarea> root,
										 CriteriaQuery<?> query,
										 CriteriaBuilder criteriaBuilder) {
				
				Path<String> path = root.get("decidedzone");
				
				return criteriaBuilder.isNull(path);
			}
		};
		List<Subarea> subareaList = subareaService.findAll(specification, null).getContent();
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Subarea.class, "subareaid", "addresskey", "position");
		LOG.info("查询定区为空的地区完成，转json成功！！");
		return JSONObject.toJSONString(subareaList, filter);
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public SysResult save(Subarea subarea){
		try{
			
			subareaService.save(subarea);
			LOG.info("保存成功，不知是新增还是修改！");
			return SysResult.ok();
		}catch(Exception e){
			e.printStackTrace();
			LOG.error(e.getMessage());
			return SysResult.build(201, "保存出错，请联系管理员！");
		}
	}
	
	/**
	 * 此方法描述的是：导出Excel，并提供下载  
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月13日 下午10:32:26 
	 * @version: V1.0
	 */
	@RequestMapping("/exportXls")
	public String exportXls(HttpServletRequest request, HttpServletResponse response){
		
		List<Subarea> list = subareaService.findAll(null, null).getContent();
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("分区数据");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("分区编号");
		headRow.createCell(1).setCellValue("区域编号");
		headRow.createCell(2).setCellValue("地址关键字");
		headRow.createCell(3).setCellValue("位置信息");
		headRow.createCell(4).setCellValue("省市区");
		
		for(Subarea subarea : list) {
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum()+1);
			Region region = subarea.getRegion();
			dataRow.createCell(0).setCellValue(subarea.getId());
			dataRow.createCell(1).setCellValue(region.getId());
			dataRow.createCell(2).setCellValue(subarea.getAddresskey());
			dataRow.createCell(3).setCellValue(subarea.getPosition());
			dataRow.createCell(4).setCellValue(region.getProvince() + region.getCity() + region.getDistrict());
		}
		
		try {
			String fileName = "分区数据.xls";
			String agent = request.getHeader("User-Agent");
			fileName = FileUtil.encodeDownloadFilename(fileName, agent);
		
			response.reset();
			
			ServletOutputStream out = response.getOutputStream();
			
			response.setHeader("content-disposition", "attchment;filename="+fileName);
			workbook.write(out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "base/subarea";
	}
	
}
