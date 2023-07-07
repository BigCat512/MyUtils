package org.example;

import org.apache.commons.compress.utils.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *
 * </p>
 *
 * @author XJH
 * @version 1.0
 * @since 2023/7/7
 */
@SpringBootTest
public class RedisTest {
    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void test() {
        redisTemplate.opsForZSet().add("testZSet", "three", 3);
        redisTemplate.opsForZSet().add("testZSet", "one", 10);
        redisTemplate.opsForZSet().add("testZSet", "two", 2);
        System.out.println("redisTemplate.opsForZSet().size(\"testZSet\") = " + redisTemplate.opsForZSet().size("testZSet"));
        List<String> stringList = Lists.newArrayList();
        stringList.add("one");
        stringList.add("two");
        stringList.add("three");
        Set testZSet = redisTemplate.opsForZSet().range("testZSet", 0, -1);
        System.out.println("testZSet = " + testZSet);
        List<String> list = Lists.newArrayList();
        list.addAll(testZSet);
        System.out.println(list);
    }

}
