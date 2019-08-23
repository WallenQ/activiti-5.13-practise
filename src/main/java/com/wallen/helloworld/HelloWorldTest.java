package com.wallen.helloworld;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

/**
 * @Author: Wallen
 * @Date: 2019/8/18 22:11
 */
public class HelloWorldTest {
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
		//1
		System.out.println("部署id：" + deployment.getId());
		//helloword入门程序
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
		//101
		System.out.println("流程实例Id" + processInstance.getId());
		//helloword:1:5
		System.out.println("流程定义ID：" + processInstance.getProcessDefinitionId());
	}

	/**
	 * 查询当前的个人任务
	 */
	@Test
	public void findMypersonalTaskTest() {
		String assignee = "李四";
		//与正在执行的任务相关的service
		List<Task> list = processEngine.getTaskService()
				//创建任务查询对象
				.createTaskQuery()
				//指定个人任务查询
				.taskAssignee(assignee)
				.list();
		if (null != list && list.size() > 0) {
			for (Task task : list) {
				System.out.println("任务ID：" + task.getId());
				System.out.println("任务名称：" + task.getName());
				System.out.println("任务创建时间：" + task.getCreateTime());
				System.out.println("任务办理人：" + task.getAssignee());
				System.out.println("流程实例ID：" + task.getProcessInstanceId());
				System.out.println("执行对象ID：" + task.getExecutionId());
				System.out.println("流程定义ID：" + task.getProcessDefinitionId());
				System.out.println("#########");
			}
		}
	}

	/**
	 * 完成我的任务
	 */
	@Test
	public void completeMyPersonalTaskTest () {
		//任务ID
		String taskId = "104";
		//与正在执行的任务相关的service
		processEngine.getTaskService()
				.complete(taskId);
		System.out.println("完成任务，任务ID：" + taskId);
	}
}
