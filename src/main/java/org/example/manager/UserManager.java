package org.example.manager;

import org.example.domain.dto.UserDTO;

import java.util.Collection;
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
public interface UserManager {

    void saveBatch(List<UserDTO> users);

    /**
     * 新增
     * @param user {@link UserDTO}
     * @return {@link Integer}
     * @author XJH
     * @since 2023/4/25
     **/
    Integer save1(UserDTO user);

    /**
     * 新增
     * @param user {@link UserDTO}
     * @return {@link Integer}
     * @author XJH
     * @since 2023/4/25
     **/
    Integer save2(UserDTO user);

    /**
     * 新增
     * @param user {@link UserDTO}
     * @return {@link Integer}
     * @author XJH
     * @since 2023/4/25
     **/
    Integer save3(UserDTO user);
}
