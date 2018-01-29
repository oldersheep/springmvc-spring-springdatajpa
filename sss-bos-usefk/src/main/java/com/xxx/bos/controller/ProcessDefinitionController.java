package com.xxx.bos.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/** 
  * @className   类名 : ProcessDefinitionController
  * @description 作用 : 流程定义管理
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月23日 下午9:18:40 
  * @version 版本 : V1.0  
  */
@Controller
@RequestMapping("/process/definition")
public class ProcessDefinitionController {
	
	private static final Logger LOG = Logger.getLogger(ProcessDefinitionController.class);
	
	@Autowired
	private RepositoryService repositoryService;

	/**
	 * 此方法描述的是：查询最新的流程版本定义，不得已为之的版本
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月23日 下午10:00:12 
	 * @version: V1.0
	 */
	@RequestMapping("/list")
	public String list(Model model){
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		query.latestVersion();
		query.orderByProcessDefinitionName().desc();
		
		List<ProcessDefinition> definitionList = query.list();
		model.addAttribute("definitionList", definitionList);
		
		return "workflow/processdefinitionlist";
	}
	
	/**
	 * 此方法描述的是： 发布流程定义
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月23日 下午11:30:59 
	 * @version: V1.0
	 */
	@RequestMapping("/deploy")
	public String deploy(@RequestParam CommonsMultipartFile zipFile) throws Exception{
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
		deploymentBuilder.addZipInputStream(new ZipInputStream(zipFile.getInputStream()));
		deploymentBuilder.deploy();
		
		return "redirect:list";
	}
	
	/**
	 * 此方法描述的是： 在浏览器中展示图片
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月23日 下午11:31:12 
	 * @version: V1.0
	 */
	@RequestMapping("/showpng")
	public void showpng(String id, HttpServletResponse response) throws Exception{
		InputStream inputStream = repositoryService.getProcessDiagram(id);
		response.setContentType("image/png");
		
		OutputStream outputStream = response.getOutputStream();
		
		byte[] data = new byte[1024];
        int size = 0;  
        while ((size = inputStream.read(data)) != -1) {  
        	outputStream.write(data, 0, size);  
        } 
        
        outputStream.flush();
        outputStream.close();
        inputStream.close();
	}
	
	/**
	 * 此方法描述的是： 需要传一个标志位，可以使用SysResult，不想改了
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月23日 下午11:35:51 
	 * @version: V1.0
	 */
	@RequestMapping("/delete")
	public String delete(String id){
		try{
			ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
			query.processDefinitionId(id);
			ProcessDefinition processDefinition = query.singleResult();
			String deploymentId = processDefinition.getDeploymentId();
			
			repositoryService.deleteDeployment(deploymentId);
			LOG.info("删除成功！！");
		}catch (Exception e){
			LOG.error(e.getMessage());
		}
		return "redirect:list";
		
	}
}
