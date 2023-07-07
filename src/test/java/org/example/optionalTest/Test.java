package org.example.optionalTest;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
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
       String str = "[]";
        List<JSONObject> list = JSONUtil.toList(str, JSONObject.class);
        System.out.println(list);

    }

}
