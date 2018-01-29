package com.xxx.bos.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/** 
  * @className   类名 : ProcessInstanceController
  * @description 作用 : 流程实例管理 
  * @author  作者 : Lilg
  * @date 创建时间 : 2018年1月24日 下午9:24:03 
  * @version 版本 : V1.0  
  */
@Controller
@RequestMapping("/process/instance")
public class ProcessInstanceController {
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@RequestMapping("/list")
	public String list(Model model){
		ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery();
		query.orderByProcessInstanceId().desc();
		List<ProcessInstance> instanceList = query.list();
		
		model.addAttribute("instanceList", instanceList);
		return "workflow/processinstancelist";
	}
	
	/**
	 * 此方法描述的是：  根据流程实例对象id查询对应的流程变量数据
	 * @author: Lilg 
	 * @date 创建时间 : 2018年1月24日 下午9:50:55 
	 * @version: V1.0
	 */
	@RequestMapping(value = "/findData", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String findData(String id){
		
		
		return JSON.toJSONString(runtimeService.getVariables(id));
	}
	
	@RequestMapping("/showpng")
	public String showpng(Model model, String id){
		// 根据流程实例id查询流程实例对象
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(id).singleResult();
		
		// 根据流程实例对象查询流程定义id
		String processDefinitionId = processInstance.getProcessDefinitionId();
		
		// 根据流程定义id查询流程定义对象
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		
		// 根据流程定义对象查询部署id
		String deploymentId = processDefinition.getDeploymentId();
		String imageName = processDefinition.getDiagramResourceName();
		
		
		// 查询坐标
		// 1、获取当前流程实例执行到哪个节点了
		String activityId = processInstance.getActivityId();
		// 2、加载bpmn文件，获得一个流程定义对象
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
		int x = activityImpl.getX();
		int y = activityImpl.getY();
		int height = activityImpl.getHeight();
		int width = activityImpl.getWidth();
		
		model.addAttribute("deploymentId", deploymentId);
		model.addAttribute("imageName", imageName);
		model.addAttribute("x", x);
		model.addAttribute("y", y);
		model.addAttribute("height", height);
		model.addAttribute("width", width);
		return "workflow/image";
	}
	
	@RequestMapping("/viewimage")
	public void viewimage(String deploymentId, String imageName, HttpServletResponse response) throws Exception{
		InputStream inputStream = repositoryService.getResourceAsStream(deploymentId, imageName);
		
		response.setContentType("image/png");
		
		OutputStream outputStream = response.getOutputStream();
		
		byte[] data = new byte[1024];
        int size = 0;  
        while ((size = inputStream.read(data)) != -1) {  
        	outputStream.write(data, 0, size);  
        	outputStream.flush();
        } 
        
        outputStream.close();
        inputStream.close();
		
	}

}
