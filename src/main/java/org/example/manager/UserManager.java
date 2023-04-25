package org.example.manager;

import org.example.entity.User;

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
    /**
     * 新增
     * @param user {@link User}
     * @return {@link Integer}
     * @author XJH
     * @since 2023/4/25
     **/
    Integer save(User user);

    /**
     * 新增
     * @param user {@link User}
     * @return {@link Integer}
     * @author XJH
     * @since 2023/4/25
     **/
    Integer save2(User user);

    /**
     * 新增
     * @param user {@link User}
     * @return {@link Integer}
     * @author XJH
     * @since 2023/4/25
     **/
    Integer save3(User user);
}
