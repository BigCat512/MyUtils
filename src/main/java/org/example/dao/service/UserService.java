package org.example.dao.service;

import org.example.dao.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import org.example.domain.dto.UserDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Xjh
 * @since 2023-05-19
 */
public interface UserService extends IService<User> {

    /**
     * 批量保存
     *
     * @param dto {@link List<UserDTO>}
     * @return {@link Integer}
     * @author Xjh
     * @since 2023-05-19
     **/
    Integer insertBatch(List<UserDTO> dto);

    /**
     * 保存
     * @param user {@link UserDTO}
     * @return {@link int}
     * @author XJH
     * @since 2023/5/19
     **/
    int insert(UserDTO user);
}
