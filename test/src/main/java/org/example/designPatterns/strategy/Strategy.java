package org.example.designPatterns.strategy;

/**
 * 策略模式统一接口，以下情况均要实现它
 */
public interface Strategy {
   public int doOperation(int num1, int num2);
}