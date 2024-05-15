package com.example.flowable.handle;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ServiceTaskExecutionDelegate2 implements JavaDelegate {

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
        var flowElementName = currentFlowElement.getName();
        var flowElementId = currentFlowElement.getId();
        // 根据标识查询任务节点配置，调取业务远程接口
        // ...
        // 模拟业务返回
        var resultDataMap = new HashMap<String, Object>();
        resultDataMap.put("leaveDays", 4);
        resultDataMap.put("rank", "初级");
        // 设值流程变量，条件流取值进行判断
        execution.setVariables(resultDataMap);
    }

}
