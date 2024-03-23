package com.example.flowable.handle;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomBranchExecutionDelegate implements JavaDelegate {

    // private FixedValue myParam;

    @Override
    public void execute(DelegateExecution execution) {
        // 流程设计上传入的参数：可以设为
        // var myParam1 = execution.getVariable("myVar");
        // 获取流程设计的标识，进而获取到该环节上配置的参数，进行条件处理
        // var branchFlowFirstUserTaskIds = Arrays.asList(params.split(","));
        // System.out.println("branchFlowFirstUserTaskIds = " + branchFlowFirstUserTaskIds);
        // 根据 gatewayAndUserTaskIds 查询配置，并且将布尔类型结果设置到变量中（变量名为各环节设计标识+condition）
        // 模拟逻辑操作...
        var currentFlowElement = execution.getCurrentFlowElement();
        var tenantId = execution.getTenantId();
        var processDefinitionId = execution.getProcessDefinitionId();
        var processInstanceId = execution.getProcessInstanceId();
        // TODO 确认这里是不是是获取当前环节的标识和名称，如果是，那么可以直接去获取平台自定义的配置
        var flowElementName = currentFlowElement.getName();
        var flowElementId = currentFlowElement.getId();
        List<Object> list = (List<Object>)execution.getVariable("yourVariable");
        // Integer leaveDays = (Integer) execution.getVariable("leaveDays");
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
