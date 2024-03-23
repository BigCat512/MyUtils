package org.example.common.utils.lambda;

import java.io.Serializable;
import java.util.function.Function;

/**
 * <p>
 * SFunction
 * 参考：{@link <a href="https://blog.csdn.net/qq_41463655/article/details/106043085">...</a>}
 * </p>
 *
 * @author XJH
 * @version 1.0
 * @since 2023/5/25
 */
@FunctionalInterface
public interface SFunction<T, R> extends Function<T, R>, Serializable  {

}
