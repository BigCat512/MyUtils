package com.example.flowable.config;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class MyJavaDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        // 在这里编写你的逻辑
    }

    public boolean shouldExecute(String flowId, DelegateExecution execution) {
        // 在这里编写自定义的判断逻辑，根据流程ID和执行上下文来确定是否执行对应的sequenceFlow
        // 返回true表示执行，返回false表示不执行
        return Boolean.TRUE;
    }
}
