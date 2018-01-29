package com.xxx.bos.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.xxx.bos.dto.EasyUIResult;
import com.xxx.bos.dto.SysResult;
import com.xxx.bos.entity.Region;
import com.xxx.bos.service.RegionService;
import com.xxx.bos.utils.PinYin4jUtil;

/** 
  * @className   类名 : RegionController
  * @description 作用 : 区域设置 
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月11日 下午9:28:17 
  * @version 版本 : V1.0  
  */
@Controller
@RequestMapping("/region")
public class RegionController {

	private static final Logger LOG = LoggerFactory.getLogger(RegionController.class);
	
	@Autowired
	private RegionService regionService;
	
	/**
	 * 此方法描述的是：分页展示列表
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月12日 下午10:29:36 
	 * @version: V1.0
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyUIResult list(int page, int rows){
		
		Pageable pageable = new PageRequest(page - 1, rows);
		Page<Region> regionPage = regionService.findAll(pageable);
		LOG.info("查询成功！！");
		return new EasyUIResult(regionPage.getTotalElements(), regionPage.getContent());
	}
	
	/**
	 * 此方法描述的是：定区管理中ajax请求区域信息
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月13日 下午2:45:14 
	 * @version: V1.0
	 */
	// @RequestMapping(value = "/listByAjax", produces = "text/html;charset=UTF-8")
	@RequestMapping("/listByAjax")
	@ResponseBody
	public String listByAjax(){
		List<Region> regionList = regionService.findAll(null).getContent();
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Region.class, "id","name"); 
		return JSONObject.toJSONString(regionList, filter);
	}
	
	/**
	 * 此方法描述的是：新增/修改 区域信息
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月12日 下午10:40:18 
	 * @version: V1.0
	 */
	@RequestMapping("/save")
	@ResponseBody
	public SysResult save(Region region){
		try{
			
			String province = region.getProvince().substring(0, region.getProvince().length() - 1);
			String city = region.getCity().substring(0, region.getCity().length() - 1);
			String district = region.getDistrict().substring(0, region.getDistrict().length() - 1);
			String citycode = StringUtils.join(PinYin4jUtil.stringToPinyin(city), "");

			String info = province + city + district;// 河北石家庄长安
			String shortcode = StringUtils.join(PinYin4jUtil.getHeadByString(info), "");

			region.setShortcode(shortcode);
			region.setCitycode(citycode);
			
			regionService.save(region);
			LOG.info("保存成功，不知是新增还是修改！");
			return SysResult.ok();
		}catch(Exception e){
			e.printStackTrace();
			LOG.error(e.getMessage());
			return SysResult.build(201, "保存出错，请联系管理员！");
		}
	}
	
	/**
	 * 此方法描述的是：根据id删除
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月12日 下午11:28:54 
	 * @version: V1.0
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public SysResult delete(String ids){
		try{
			regionService.deletBatch(ids);
			LOG.info("删除成功！");
			return SysResult.ok();
		}catch(Exception e){
			e.printStackTrace();
			LOG.error(e.getMessage());
			return SysResult.build(201, "删除出错，请联系管理员！");
		}
	}
	
	/**
	 * 此方法描述的是：excel导入，参数只能为CommonsMultipartFile
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月12日 下午10:27:38 
	 * @version: V1.0
	 */
	@RequestMapping("/importXls")
	@ResponseBody
	public String importXls(@RequestParam CommonsMultipartFile myFile) {
		try{
			HSSFWorkbook workbook = new HSSFWorkbook(myFile.getInputStream());

			HSSFSheet sheet = workbook.getSheetAt(0);

			List<Region> list = new ArrayList<Region>();
			for (Row row : sheet) {
				if (row.getRowNum() == 0) {
					continue;
				}

				String id = row.getCell(0).getStringCellValue();
				String province = row.getCell(1).getStringCellValue();
				String city = row.getCell(2).getStringCellValue();
				String district = row.getCell(3).getStringCellValue();
				String postcode = row.getCell(4).getStringCellValue();

				Region region = new Region(id, province, city, district, postcode, null, null, null);

				// 简码---->>HBSJZCA
				province = province.substring(0, province.length() - 1);
				city = city.substring(0, city.length() - 1);
				district = district.substring(0, district.length() - 1);

				String citycode = StringUtils.join(PinYin4jUtil.stringToPinyin(city), "");

				String info = province + city + district;// 河北石家庄长安
				String shortcode = StringUtils.join(PinYin4jUtil.getHeadByString(info), "");

				region.setShortcode(shortcode);
				region.setCitycode(citycode);

				list.add(region);
			}

			regionService.saveBatch(list);
			
			return "1";
		} catch(Exception e){
			e.printStackTrace();
			LOG.error(e.getMessage());
			return "0";
		}
		
	}
	
}
