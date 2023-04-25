package org.example;

import org.example.entity.User;
import org.example.manager.UserManager;
import org.example.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author XJH
 * @version 1.0
 * @since 2023/4/25
 */
@SpringBootTest
public class UserTest {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserManager userManager;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assertions.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }

    @Test
    public void testSave() {
        userManager.save(new User().setName("baba").setAge(23));
    }
    @Test
    public void testSaveEvent() {
        userManager.save2(new User().setName("testSaveEvent").setAge(23));
    }

    @Test
    public void testSaveByManualTransactional() {
        userManager.save3(new User().setName("testSaveByManualTransactional").setAge(23));
    }
}
