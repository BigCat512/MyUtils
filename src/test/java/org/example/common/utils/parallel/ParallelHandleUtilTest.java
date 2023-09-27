package org.example.common.utils.parallel;

import org.apache.commons.compress.utils.Lists;
import org.example.dao.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ParallelHandleUtilTest {
    @Test
    void test(){
        List<User> userList = Lists.newArrayList();
        List<Object> objectList = ParallelHandleUtil.handle(userList, (i, list) -> {
            User user = list.get(i);
            // do something...
            System.out.println(user);
            return new Object();
        });
        System.out.println(objectList);
    }

}
