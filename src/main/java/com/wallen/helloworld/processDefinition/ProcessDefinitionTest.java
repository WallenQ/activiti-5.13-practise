package com.wallen.helloworld.processDefinition;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

/**
 * 流程定义
 *
 * @author Wallen
 * 2019/8/28 19:11
 */
public class ProcessDefinitionTest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 流程部署定义-从classpath
	 */
	@Test
	public void deploymentProcessDefinitionClassPathTest() {
		//与流程定义和部署对象相关的service
		Deployment deployment = processEngine.getRepositoryService()
				//创建一个部署对象
				.createDeployment()
				//添加部署名称
				.name("流程定义")
				//从classpath的资源中加载，一次只能加载一个
				.addClasspathResource("helloWorld.bpmn")
				.addClasspathResource("helloWorld1.png")
				.deploy();//完成部署
		//1
		System.out.println("部署id：" + deployment.getId());
		//helloword入门程序
		System.out.println("部署名称：" + deployment.getName());
	}

	/**
	 * 流程部署定义-从zip
	 */
	@Test
	public void deploymentProcessDefinitionZipTest() {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("helloworld.zip");
		ZipInputStream zipInputStream = new ZipInputStream(inputStream);
		//与流程定义和部署对象相关的service
		Deployment deployment = processEngine.getRepositoryService()
				//创建一个部署对象
				.createDeployment()
				//添加部署名称
				.name("流程定义")
				//指定zip格式的文件完成部署
				.addZipInputStream(zipInputStream)
				.deploy();//完成部署
		//1
		System.out.println("部署id：" + deployment.getId());
		//helloword入门程序
		System.out.println("部署名称：" + deployment.getName());
	}
}
