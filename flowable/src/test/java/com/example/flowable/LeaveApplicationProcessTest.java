package com.example.flowable;

import com.example.flowable.config.MyConExprService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class LeaveApplicationProcessTest {
    @Resource
    private MyConExprService myService;

    /**
     * 分支流程测试
     *
     * @author author
     * @since 2023/11/22
     **/
    @Test
    public void test() {
        Map<Object, Object> beans = new HashMap<>();
        beans.put("myConExprService", myService);
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=-1")
                .setJdbcUsername("sa")
                .setJdbcPassword("")
                .setJdbcDriver("org.h2.Driver")
                // 如果数据库中的表结构不在则新建
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        cfg.setBeans(beans);
        // 部署流程定义
        ProcessEngine processEngine = cfg.buildProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                // 单个ServiceTask + 包容网关(开始-结束)
                .addClasspathResource("LeaveApplicationProcess5.bpmn20.xml")
                // 单个ServiceTask + 包容网关(结束)
                // .addClasspathResource("LeaveApplicationProcess2.bpmn20.xml")
                // 多个ServiceTask + 包容网关(结束)：必须要所有分支都能执行，不然会报错没有输出流
                // .addClasspathResource("LeaveApplicationProcess3.bpmn20.xml")
                // 包容网关(开始-结束) + 多个ServiceTask：必须要所有分支都能执行，不然会报错没有输出流
                // .addClasspathResource("LeaveApplicationProcess4.bpmn20.xml")
                .tenantId("FIM1234567890")
                // .name("LeaveApplicationProcess2开始-结束")
                .deploy();
        System.out.println("deployment = " + deployment);

        // 启动新流程实例
        var runtimeService = processEngine.getRuntimeService();
        // Map<String, Object> variables = new HashMap<String, Object>();
        // variables.put("leaveDays", 2);
        // variables.put("leaveDays", 4);
        // variables.put("leaveDays", 108);
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId())
                .singleResult();
        // ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("InclusiveGatewayTest", variables);
        // ProcessInstance processInstance = runtimeService.startProcessInstanceById("FIM1234567890");
        // ProcessInstance processInstance = runtimeService.startProcessInstanceById("InclusiveGatewayTest");
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(definition.getId());
        var processInstanceId = processInstance.getId();
        var taskService = processEngine.getTaskService();
        var process = repositoryService.getBpmnModel(definition.getId()).getMainProcess();
        // 领导任务列表：无条件执行
        List<Task> leaderTasks = taskService.createTaskQuery().taskCandidateUser("领导").list();
        System.out.println("leaderTasks = " + leaderTasks);
        // taskService.complete(leaderTasks.get(0).getId());

        // HR审批 任务列表：大于三天
        List<Task> HRTasks = taskService.createTaskQuery().taskCandidateUser("HR小王").list();
        System.out.println("HRTasks = " + HRTasks);

        // HR总监审批 任务列表：大于五天
        List<Task> HRDirectorTasks = taskService.createTaskQuery().taskCandidateUser("HR大王").list();
        System.out.println("HRDirectorTasks = " + HRDirectorTasks);

        // taskService.complete("22")
        runtimeService.suspendProcessInstanceById(processInstanceId);
        // 获取历史流程实例查询对象
        var historyService = processEngine.getHistoryService();
        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery();
        // 设置查询条件，根据流程实例 ID 查询
        query.processInstanceId(processInstanceId);
        // 判断流程实例是否已经结束
        var historicProcessInstance = query.singleResult();
        if (historicProcessInstance != null && historicProcessInstance.getEndTime() != null) {
            // 流程已结束
            // 执行查询，获取历史流程实例
            System.out.println("流程已结束，结束时间为：" + historicProcessInstance.getEndTime());
        } else {
            // 流程未结束
            System.out.println("流程未结束");
        }
    }

}
