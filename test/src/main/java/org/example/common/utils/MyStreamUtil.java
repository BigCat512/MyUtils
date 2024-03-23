package org.example.common.utils;

import cn.hutool.core.stream.StreamUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * <p>
 * stream 流工具类
 * </p>
 *
 * @author XJH
 * @version 1.0
 * @since 2023/7/3
 */
public class MyStreamUtil extends StreamUtil {

    public static <T> Stream<T> of(Collection<T> collection) {
        return Optional.ofNullable(collection)
                .orElse(Collections.emptyList())
                .stream();
    }
}
