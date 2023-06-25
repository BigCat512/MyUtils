package org.example.optionalTest;

import lombok.Data;

import java.util.Optional;

@Data
public class Test {
    private String age;

    public Test() {
        System.out.println("test构造函数被执行");
    }

    public Test(String age) {
        this.age = age;
    }

    public static void main(String[] args) {
        Test qqq = new Test("qqq");
        System.out.println("============================");
        Test tset = Optional.ofNullable(qqq)
                .orElse(new Test());
        System.out.println(tset);
        System.out.println("============================");
        Test tset2 = Optional.ofNullable(qqq)
                .orElseGet(() -> new Test());

    }

}
