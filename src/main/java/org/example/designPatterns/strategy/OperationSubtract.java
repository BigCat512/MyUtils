package org.example.designPatterns.strategy;

/**
 * 减法
 */
public class OperationSubtract implements Strategy{
   @Override
   public int doOperation(int num1, int num2) {
      return num1 - num2;
   }
}