package com.wallen.helloworld;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
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
	public void deploymentProcessDefinitionTest() {
		//与流程定义和部署对象相关的service
		Deployment deployment = processEngine.getRepositoryService()
				//创建一个部署对象
				.createDeployment()
				//添加部署名称
				.name("helloworld入门程序")
				//从classpath的资源中加载，一次只能加载一个
				.addClasspathResource("helloWorld.bpmn")
				.addClasspathResource("helloWorld1.png")
				.deploy();//完成部署
		System.out.println("部署id：" + deployment.getId());
		System.out.println("部署名称：" + deployment.getName());
	}

	/**
	 * 启动流程实例
	 */
	@Test
	public void startProcessInstanceTest() {
		//流程定义的key
		String processDefinitionKey = "helloword";

		//与正在执行的流程实例和执行对象相关的service
		ProcessInstance processInstance = processEngine.getRuntimeService()
				//使用流程定义的可以启动流程实例，key对应helloworld.bpmn文件中id的属性，使用key值启动，默认是按照最新版本的流程启动
				.startProcessInstanceByKey(processDefinitionKey);
		System.out.println("流程实例id：" + processInstance.getId());
		System.out.println("流程定义id" + processInstance.getProcessDefinitionId());
	}
}
