package org.example.designPatterns.strategy;

/**
 * 除法
 */
public class OperationDivide implements Strategy{
    @Override
    public int doOperation(int num1, int num2) {
        if (num2==0){
            return 0;
        }
        return num1/num2;
    }
}
