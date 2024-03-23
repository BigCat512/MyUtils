package org.example.common.utils.parallel;

import java.util.List;

/**
 * <p>
 * 自定义函数式接口
 * </p>
 *
 * @author XJH
 * @version 1.0
 * @since 2023/9/27
 */
@FunctionalInterface
public interface ParallelFunction<E, R> {

    /**
     * 对给定的索引、集合执行此操作。
     *
     * @param index {@link int}
     * @param list  {@link List<E>}
     * @return {@link R}
     **/
    R accept(int index, List<E> list);

}
