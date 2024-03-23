package org.example.studydemo.strategy;

import org.example.designPatterns.strategy.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class StrategyTests {

    @Test
    void demo01() {
        Context context = new Context(new OperationAdd());
        System.out.println("10 + 5 = " + context.executeStrategy(10, 5));

        context = new Context(new OperationSubtract());
        System.out.println("10 - 5 = " + context.executeStrategy(10, 5));

        context = new Context(new OperationMultiply());
        System.out.println("10 * 5 = " + context.executeStrategy(10, 5));

        context = new Context(new OperationDivide());
        System.out.println("10 / 5 = " + context.executeStrategy(10, 5));
    }

}
