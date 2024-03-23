package org.example.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.dao.entity.User;
import org.example.domain.dto.UserDTO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Xjh
 * @since 2023-05-19
 */
public interface UserService extends IService<User> {

    /**
     * 保存
     *
     * @param user {@link UserDTO}
     * @return {@link int}
     * @author XJH
     * @since 2023/5/19
     **/
    boolean insert(UserDTO user);


}
