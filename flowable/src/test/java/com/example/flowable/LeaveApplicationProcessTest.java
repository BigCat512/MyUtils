package com.example.flowable;

import cn.hutool.core.collection.CollUtil;
import com.example.flowable.config.MyConExprService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
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
        var cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=-1")
                .setJdbcUsername("sa")
                .setJdbcPassword("")
                .setJdbcDriver("org.h2.Driver")
                // 如果数据库中的表结构不在则新建
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        cfg.setBeans(beans);
        // 部署流程定义
        var processEngine = cfg.buildProcessEngine();
        var repositoryService = processEngine.getRepositoryService();
        var deployment = repositoryService.createDeployment()
                // 单个ServiceTask + 包容网关(结束)（条件流固定取值布尔类型）
                // .addClasspathResource("inclusiveGateway/LeaveApplicationProcess2.bpmn20.xml")
                // 单个ServiceTask + 包容网关(结束)（条件流自由取值）
                // .addClasspathResource("inclusiveGateway/LeaveApplicationProcess2-1.bpmn20.xml")
                // 多个ServiceTask + 包容网关(结束)：必须要所有分支都能执行，不然会报错没有输出流
                // .addClasspathResource("LeaveApplicationProcess3.bpmn20.xml")
                // 包容网关(开始-结束) + 多个ServiceTask：必须要所有分支都能执行，不然会报错没有输出流
                // .addClasspathResource("LeaveApplicationProcess4.bpmn20.xml")
                // 包容网关(开始-结束)
                // .addClasspathResource("inclusiveGateway/LeaveApplicationProcess5.bpmn20.xml")
                // 包容网关(结束)
                // .addClasspathResource("LeaveApplicationProcess6.bpmn20.xml")
                // 无包容网关(不行，必须要由网关汇聚分支)
                // .addClasspathResource("LeaveApplicationProcess7.bpmn20.xml")
                .addClasspathResource("exclusiveGateway/LeaveApplicationProcess.bpmn20.xml")
                .tenantId("FIM1234567890")
                // .name("LeaveApplicationProcess2开始-结束")
                .deploy();
        log.error("deployment = " + deployment);

        // 启动新流程实例
        var runtimeService = processEngine.getRuntimeService();
        var definition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId())
                .singleResult();
        var processInstance = runtimeService.startProcessInstanceById(definition.getId());
        var processInstanceId = processInstance.getId();
        var taskService = processEngine.getTaskService();
        var process = repositoryService.getBpmnModel(definition.getId()).getMainProcess();
        // 领导任务列表：无条件执行
        var leaderTasks = taskService.createTaskQuery().taskCandidateUser("领导").list();
        log.error("leaderTasks = " + leaderTasks);
        if (CollUtil.isNotEmpty(leaderTasks)) taskService.complete(leaderTasks.get(0).getId());

        // HR助理审批 任务列表：初级员工并且假期小于3天
        var hrAssistantTasks = taskService.createTaskQuery().taskCandidateUser("HR助理").list();
        log.error("hrAssistantTasks = " + hrAssistantTasks);
        if (CollUtil.isNotEmpty(hrAssistantTasks)) taskService.complete(hrAssistantTasks.get(0).getId());


        // HR审批 任务列表：假期小于等于5天
        var HRTasks = taskService.createTaskQuery().taskCandidateUser("HR小王").list();
        log.error("HRTasks = " + HRTasks);
        if (CollUtil.isNotEmpty(HRTasks)) taskService.complete(HRTasks.get(0).getId());

        // HR总监审批 任务列表：假期大于五天
        var HRDirectorTasks = taskService.createTaskQuery().taskCandidateUser("HR大王").list();
        log.error("HRDirectorTasks = " + HRDirectorTasks);
        if (CollUtil.isNotEmpty(HRDirectorTasks)) taskService.complete(HRDirectorTasks.get(0).getId());

        // 最后报批王经理
        var managerTasks = taskService.createTaskQuery().taskCandidateUser("王经理").list();
        log.error("managerTasks = " + managerTasks);
        if (CollUtil.isNotEmpty(managerTasks)) taskService.complete(managerTasks.get(0).getId());

        var variableInstances = ((ExecutionEntityImpl) processInstance).getVariableInstances();

        // runtimeService.suspendProcessInstanceById(processInstanceId);
        // 获取历史流程实例查询对象
        var historyService = processEngine.getHistoryService();
        var query = historyService.createHistoricProcessInstanceQuery();
        // 设置查询条件，根据流程实例 ID 查询
        query.processInstanceId(processInstanceId);
        // 判断流程实例是否已经结束
        var historicProcessInstance = query.singleResult();
        if (historicProcessInstance != null && historicProcessInstance.getEndTime() != null) {
            // 流程已结束
            // 执行查询，获取历史流程实例
            log.error("流程已结束，结束时间为：" + historicProcessInstance.getEndTime());
        } else {
            // 流程未结束
            log.error("流程未结束");
        }
    }

}
