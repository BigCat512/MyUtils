package org.example.common.utils.exception;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

import java.util.Collection;

/**
 * <p>
 * 自定义断言
 * </p>
 *
 * @author XJH
 * @version 1.0
 * @since 2023/6/13
 */
public class Asserts {

    /**
     * 断言是否为真
     *
     * @param expression {@link Boolean}
     * @param errorMsg   {@link String}
     **/
    public static void isTrue(boolean expression, String errorMsg) {
        Assert.isTrue(expression, () -> new CheckedException(errorMsg));
    }

    /**
     * 断言是否为假
     *
     * @param expression {@link Boolean}
     * @param errorMsg   {@link String}
     **/
    public static void isFalse(boolean expression, String errorMsg) {
        Assert.isFalse(expression, () -> new CheckedException(errorMsg));
    }


    /**
     * 字符相等断言
     *
     * @param str      {@link String}
     * @param str2     {@link String}
     * @param errorMsg {@link String}
     **/
    public static void equals(String str, String str2, String errorMsg) {
        Assert.isTrue(StrUtil.equals(str, str2), () -> new CheckedException(errorMsg));
    }

    /**
     * 字符串非空断言
     *
     * @param str      {@link CharSequence}
     * @param errorMsg {@link String}
     **/
    public static <T extends CharSequence> void notBlank(T str, String errorMsg) {
        Assert.notBlank(str, () -> new CheckedException(errorMsg));
    }


    /**
     * 非空断言
     *
     * @param t        {@link T}
     * @param errorMsg {@link String}
     **/
    public static <T> void notNull(T t, String errorMsg) {
        Assert.notNull(t, () -> new CheckedException(errorMsg));
    }

    /**
     * 非空断言
     *
     * @param collection {@link Collection}
     * @param errorMsg   {@link String}
     **/
    public static <E> void notEmpty(Collection<E> collection, String errorMsg) {
        Assert.notEmpty(collection, () -> new CheckedException(errorMsg));
    }

    public static void cast(String errorMsg){
        throw new BaseException(errorMsg);
    }

}
