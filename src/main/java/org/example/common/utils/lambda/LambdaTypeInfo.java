package org.example.common.utils.lambda;


import cn.hutool.core.lang.Assert;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


/**
 * <p>
 * 通过 Lambda 获取对象属性名
 * 使用方法： LambdaTypeInfo.convertToFieldName(Test::getName)
 * 参考：{@link <a href="https://blog.csdn.net/qq_41463655/article/details/106043085">...</a>}
 * </p>
 *
 * @author XJH
 * @version 1.0
 * @since 2023/5/25
 */
public class LambdaTypeInfo {

    /**
     * 序列化信息缓存
     */
    private final static Map<Class<?>, SerializedLambda> CLASS_LAMBDA_CACHE = new ConcurrentHashMap<>();

    /**
     * 转换方法引用为属性名
     *
     * @param fn {@link SFunction<T>}
     * @return {@link String}
     * @author XJH
     * @since 2023/5/25
     **/
    public static <T> String getFieldName(SFunction<T, ?> fn) {
        SerializedLambda lambda = getSerializedLambda(fn);
        // 获取方法名
        String methodName = lambda.getImplMethodName();
        String prefix = null;
        if (methodName.startsWith("get")) {
            prefix = "get";
        } else if (methodName.startsWith("is")) {
            prefix = "is";
        }
        Assert.notNull(prefix, String.format("无效的getter方法: %s", methodName));
        // 截取get/is之后的字符串并转换首字母为小写
        return toLowerCaseFirstOne(methodName.replace(prefix, ""));
    }

    /**
     * 首字母转小写
     *
     * @param s {@link String}
     * @return {@link String}
     * @author XJH
     * @since 2023/5/25
     **/
    private static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return Character.toLowerCase(s.charAt(0)) + s.substring(1);
        }
    }


    /**
     * 获取序列化信息
     *
     * @param fn {@link Serializable}
     * @return {@link SerializedLambda}
     * @author XJH
     * @since 2023/5/25
     **/
    private static SerializedLambda getSerializedLambda(Serializable fn) {
        SerializedLambda lambda = CLASS_LAMBDA_CACHE.get(fn.getClass());
        // 先检查缓存中是否已存在
        if (Objects.nonNull(lambda)) {
            return lambda;
        }
        try {
            // 提取SerializedLambda并缓存
            Method method = fn.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            lambda = (SerializedLambda) method.invoke(fn);
            CLASS_LAMBDA_CACHE.put(fn.getClass(), lambda);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lambda;
    }

}

