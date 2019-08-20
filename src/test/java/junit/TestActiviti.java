package junit;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

/**
 * @Author: Wallen
 * @Date: 2019/8/17 17:21
 */
public class TestActiviti {

	/**
	 * 使用代码创建工作流需要的23张表
	 */
	@Test
	public void createTableByCode() {
		ProcessEngineConfiguration processEngineConfiguration
				= ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
		processEngineConfiguration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
		processEngineConfiguration.setJdbcUrl("jdbc:mysql://47.105.243.180:3306/activiti_practise?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8");
		processEngineConfiguration.setJdbcUsername("root");
		processEngineConfiguration.setJdbcPassword("ntchst@3306");

//		public static final String DB_SCHEMA_UPDATE_FALSE = "false";不能自动创建表，必须表先存在
//		public static final String DB_SCHEMA_UPDATE_CREATE_DROP = "create-drop";先删除再创建
//		public static final String DB_SCHEMA_UPDATE_TRUE = "true";表不存在则自动先创建表
		processEngineConfiguration.setDatabaseSchemaUpdate(processEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

		//工作流的核心对象，processEnginee对象
		ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();

		System.out.println("processEngineByCode:" + processEngine);

	}

	/**
	 * 使用配置文件创建工作流需要的23张表
	 */
	@Test
	public void createTableByConfiguration() {
		//工作流的核心对象，processEnginee对象
		ProcessEngine processEngine
				= ProcessEngineConfiguration
					.createProcessEngineConfigurationFromResource("activiti.cfg.xml")
					.buildProcessEngine();

		System.out.println("processEngineByConfiguration:" + processEngine);
	}


}
