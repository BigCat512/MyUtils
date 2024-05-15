package com.example.flowable.config;

import cn.hutool.core.util.StrUtil;
import org.flowable.bpmn.model.ExclusiveGateway;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;

@Service("myConExprService")
public class MyConExprService {

    public boolean execute(String sequenceFlowId, DelegateExecution execution) {
        // 在这里编写自定义的判断逻辑，根据流程ID和执行上下文来确定是否执行对应的sequenceFlow
        // 返回true表示执行，返回false表示不执行
        // 流程设计上传入的参数：可以设为
        // var myParam1 = execution.getVariable("myVar");
        // 获取流程设计的标识，进而获取到该环节上配置的参数，进行条件处理
        // var branchFlowFirstUserTaskIds = Arrays.asList(params.split(","));
        // System.out.println("branchFlowFirstUserTaskIds = " + branchFlowFirstUserTaskIds);
        // 根据 gatewayAndUserTaskIds 查询配置，并且将布尔类型结果设置到变量中（变量名为各环节设计标识+condition）
        // 模拟逻辑操作...

        var processDefinitionId = execution.getProcessDefinitionId();

        var currentActivityId = execution.getCurrentActivityId();

        var currentFlowElement = execution.getCurrentFlowElement();
        var incomingFlows = ((ExclusiveGateway) currentFlowElement).getIncomingFlows();
        var outgoingFlows = ((ExclusiveGateway) currentFlowElement).getOutgoingFlows();

        if (StrUtil.equals("sequenceFlow_task_1", sequenceFlowId)){
            return Boolean.FALSE;
        } else if (StrUtil.equals("sequenceFlow_task_2", sequenceFlowId)){
            return Boolean.TRUE;
        } else if (StrUtil.equals("sequenceFlow_task_3", sequenceFlowId)){
            return Boolean.TRUE;
        }
        return Boolean.TRUE;
    }
}
