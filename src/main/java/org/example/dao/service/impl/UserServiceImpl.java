package org.example.dao.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import org.example.dao.entity.User;
import org.example.dao.mapper.UserMapper;
import org.example.dao.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import org.example.domain.dto.UserDTO;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Xjh
 * @since 2023-05-19
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public boolean insert(UserDTO user) {
        return this.save(BeanUtil.toBean(user, User.class));
    }

    @Override
    public Integer insertBatch(List<UserDTO> dto) {
        if (CollUtil.isEmpty(dto)) {
            return 0;
        }
         return ListUtil.split(dto, 500)
                .stream()
                .mapToInt(e -> baseMapper.insertBatch(BeanUtil.copyToList(e, User.class)))
                .sum();
 }

}
