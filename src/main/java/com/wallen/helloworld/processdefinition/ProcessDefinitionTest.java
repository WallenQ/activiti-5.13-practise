package com.wallen.helloworld.processdefinition;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
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

	/**
	 * 查询流程定义
	 */
	@Test
	public void findProcessDefinitionTest() {
		//与流程定义和部署对象相关的service
		List<ProcessDefinition> processDefinitions = processEngine.getRepositoryService()
				//创建一个流程定义的查询
				.createProcessDefinitionQuery()

				/*指定查询条件，where条件*/
				//使用部署对象id查询
				//.deploymentId(deploymentId)
				//使用流程定义id查询
				//.processDefinitionId(processDefinitionId)
				//使用流程定义的key查询
				//.processDefinitionKey(processDefinitionKey)
				//使用流程定义的名称模糊查询
				//.processDefinitionNameLike(processDefinitionNameLike)

				/*排序*/
				//按照版本升序排列
				.orderByProcessDefinitionVersion().asc()
				//按照流程定义名称降序排列
				.orderByProcessDefinitionName().desc()

				/*返回的结果集*/
				//返回一个集合列表，封装流程定义
				.list();
				//返回唯一结果集
				//.singleResult();
				//返回结果集数量
				//.count();
				//分页查询
				//.listPage(firstResult , maxResults);
		if (null != processDefinitions && processDefinitions.size() > 0) {
			for (ProcessDefinition processDefinition : processDefinitions) {
				System.out.println("流程定义id：" + processDefinition.getId());
				System.out.println("流程定义的名称：" + processDefinition.getName());
				System.out.println("流程定义的key：" + processDefinition.getKey());
				System.out.println("流程定义的版本：" + processDefinition.getVersion());
				System.out.println("资源名称bpmn文件：" + processDefinition.getResourceName());
				System.out.println("资源名称png文件：" + processDefinition.getDiagramResourceName());
				System.out.println("部署对象id：" + processDefinition.getDeploymentId());
				System.out.println("#########################################");
			}
		}

	}

	/**
	 * 删除流程定义
	 */
	@Test
	public void deleteProcessDefinitionTest () {
		//使用部署id，完成删除
		String deploymentId  = "601";
		/**
		 * 不带级联的删除
		 * 只能删除没有启动的流程，如果流程启动，会抛出异常
		 */
		/*processEngine.getRepositoryService()
				.deleteDeployment(deploymentId);*/
		/**
		 * 级联删除
		 * 不管流程是否启动，都能删除
		 */
		processEngine.getRepositoryService()
				.deleteDeployment(deploymentId , true);

		System.out.println("删除成功！");
	}

	/**
	 * 查看流程图
	 */
	@Test
	public void viewPictureTest () throws IOException {
		//将生成的图片放到文件夹下
		String deploymentId  = "501";
		//获取图片资源的名称
		List<String> nameList = processEngine.getRepositoryService()
				.getDeploymentResourceNames(deploymentId);
		//定义图片资源的名称
		String resourceName = "";
		if (null != nameList && nameList.size() > 0) {
			for (String name : nameList) {
				if (name.contains(".png")) {
					resourceName = name;
				}
			}
		}

		//获取图片的输入流
		InputStream inputStream = processEngine.getRepositoryService()
				.getResourceAsStream(deploymentId , resourceName);
		//将图片生成到D盘的目录
		File file = new File("D:/" + resourceName);
		// 将输入流的图片写到D盘下
		FileUtils.copyInputStreamToFile(inputStream , file);

	}

}
