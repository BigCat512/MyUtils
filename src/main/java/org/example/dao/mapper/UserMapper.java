package org.example.dao.mapper;

import org.example.dao.entity.User;
import org.example.common.config.CommonMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Xjh
 * @since 2023-05-19
 */
public interface UserMapper extends CommonMapper<User> {

    List<User> findAllLazy();

}
