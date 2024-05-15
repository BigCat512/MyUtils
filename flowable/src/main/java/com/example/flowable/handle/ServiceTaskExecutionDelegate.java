package com.example.flowable.handle;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class ServiceTaskExecutionDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {

        var currentFlowElement = execution.getCurrentFlowElement();
        var tenantId = execution.getTenantId();
        var processDefinitionId = execution.getProcessDefinitionId();
        var processInstanceId = execution.getProcessInstanceId();
        var flowElementName = currentFlowElement.getName();
        var flowElementId = currentFlowElement.getId();
        // 根据标识查询任务节点配置，调取业务远程接口
        // Integer leaveDays = (Integer) execution.getVariable("leaveDays");
        // 模拟业务返回
        Integer leaveDays = 4;
        if (leaveDays < 3) {
            // 三天内领导审批就好了 只执行task_1分支
            execution.setVariable("task_1_condition", true);
            execution.setVariable("task_2_condition", false);
            execution.setVariable("task_3_condition", false);
        } else if (leaveDays <= 5) {
            // 大于三天小于五天还需要HR审批一下 执行task_1和task_2分支
            execution.setVariable("task_1_condition", true);
            execution.setVariable("task_2_condition", true);
            execution.setVariable("task_3_condition", false);
        } else {
            // 大于三五天还需要HR总监审批一下执行task_1、task_2和task_3分支
            execution.setVariable("task_1_condition", true);
            execution.setVariable("task_2_condition", true);
            execution.setVariable("task_3_condition", true);
        }
    }
}
