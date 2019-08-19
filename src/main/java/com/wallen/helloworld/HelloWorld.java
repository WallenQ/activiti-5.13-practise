package com.wallen.helloworld;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

/**
 * @Author: Wallen
 * @Date: 2019/8/18 22:11
 */
public class HelloWorld {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 流程部署定义
	 */
	@Test
	public void deploymentProcessDefinition() {
		Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的service
				.createDeployment()//创建一个部署对象
				.name("helloworld入门程序")//添加部署名称
				.addClasspathResource("helloWorld.bpmn")//从classpath的资源中加载，一次只能加载一个
				.addClasspathResource("helloWorld1.png")
				.deploy();//完成部署
		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
	}
}
