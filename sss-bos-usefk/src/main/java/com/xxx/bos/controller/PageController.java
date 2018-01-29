package com.xxx.bos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/** 
  * @className   类名 : PageController
  * @description 作用 : 某些需要直接返回的页面统一走这个controller的方法
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月10日 下午8:21:47 
  * @version 版本 : V1.0  
  */
@Controller
public class PageController {

	@RequestMapping("/page/{pageName}")
	public String page(@PathVariable String pageName){
		
		if(pageName.contains("_")){
			pageName = pageName.replaceAll("_", "/");
		}
		
		return pageName;
	}
}
