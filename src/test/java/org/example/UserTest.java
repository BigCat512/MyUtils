package org.example;

import org.example.dao.entity.User;
import org.example.dao.mapper.UserMapper;
import org.example.domain.dto.UserDTO;
import org.example.manager.UserManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
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
        userManager.save1(new UserDTO().toBuilder().name("baba").age(23).build());
    }

    @Test
    public void testSaveEvent() {
        userManager.save2(UserDTO.builder().name("testSaveEvent20230516").age(23).build());
    }

    @Test
    public void testSaveByManualTransactional() {
        userManager.save3(UserDTO.builder().name("testSaveByManualTransactional").age(23).build());
    }

    @Test
    public void testSaveBatch() {
        userManager.saveBatch(new ArrayList<UserDTO>() {{
            add(UserDTO.builder().name("testSaveBatch-1").build());
            add(UserDTO.builder().name("testSaveBatch-2").build());
            add(UserDTO.builder().name("testSaveBatch-3").build());
            add(UserDTO.builder().name("testSaveBatch-4").build());
        }});
    }


}
