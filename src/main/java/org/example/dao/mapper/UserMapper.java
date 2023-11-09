package org.example.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.dao.entity.User;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Xjh
 * @since 2023-05-19
 */
public interface UserMapper extends BaseMapper<User> {

    List<User> findAllLazy();

}
