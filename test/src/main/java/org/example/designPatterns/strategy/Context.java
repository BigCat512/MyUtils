package org.example.designPatterns.strategy;

/**
 * 策略模式中间处理类，后续使用时直接调用该方法即可
 */
public class Context {
   private Strategy strategy;
 
   public Context(Strategy strategy){
      this.strategy = strategy;
   }
 
   public int executeStrategy(int num1, int num2){
      return strategy.doOperation(num1, num2);
   }
}