package org.example.common.enums;


import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 枚举类型约束
 * </p>
 *
 * @author XJH
 * @version 1.0
 * @since 2023/4/26
 */
public interface TypeRef {

    /**
     * 类型编码
     *
     * @return {@link Integer}
     * @author XJH
     * @since 2023/4/26
     **/
    Integer code();

    /**
     * 类型描述信息
     *
     * @return {@link String}
     * @author XJH
     * @since 2023/4/26
     **/
    String message();

    /**
     * 判断code值
     *
     * @param code {@link Integer}
     * @return {@link Boolean}
     * @author XJH
     * @since 2023/4/26
     **/
    default boolean isCode(Integer code) {
        return Objects.nonNull(this.code()) && this.code().equals(code);
    }

    /**
     * 判断code值
     *
     * @param message {@link String}
     * @return {@link boolean}
     * @author XJH
     * @since 2023/4/26
     **/
    default boolean isMessage(String message) {
        return Objects.nonNull(this.message()) && this.message().equals(message);
    }

    /**
     * 判断枚举类型是否存在
     *
     * @param types 类型数组
     * @param code  类型编码
     * @return 返回值，存在为true，反之为false
     */
    static boolean exists(TypeRef[] types, Integer code) {
        if (Objects.isNull(types)) {
            return false;
        }
        for (TypeRef type : types) {
            if (type != null && type.isCode(code)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将枚举类的message和name转成map集合
     *
     * @param types {@link TypeRef}
     * @return {@link List<Map<String, String>>}
     * @author XJH
     * @since 2023/4/26
     **/
    static List<Map<String, String>> listNameMap(TypeRef[] types) {
        return Arrays.stream(types)
                .map(type -> {
                    Map<String, String> temp = new HashMap<>();
                    temp.put("name", ((Enum<?>) type).name());
                    temp.put("message", type.message());
                    return temp;
                }).collect(Collectors.toList());
    }

    /**
     * 将枚举类的message和code转成map集合
     *
     * @param types {@link TypeRef}
     * @return {@link List<Map<String, Object>>}
     * @author XJH
     * @since 2023/4/26
     **/
    static List<Map<String, Object>> listCodeMap(TypeRef[] types) {
        return Arrays.stream(types)
                .map(type -> {
                    Map<String, Object> temp = new HashMap<>();
                    temp.put("code", type.code());
                    temp.put("message", type.message());
                    return temp;
                }).collect(Collectors.toList());
    }

    /**
     * 获取指定的枚举对象
     *
     * @param types {@link E}
     * @param code  {@link Integer}
     * @return {@link E}
     * @author XJH
     * @since 2023/4/26
     **/
    static <E extends TypeRef> E getByCode(E[] types, Integer code) {
        return Arrays.stream(types).filter(t -> t.isCode(code)).findFirst().orElse(null);
    }

    /**
     * 获取指定的枚举对象
     *
     * @param types   {@link E}
     * @param message {@link String}
     * @return {@link E}
     * @author XJH
     * @since 2023/4/26
     **/
    static <E extends TypeRef> E getByMessage(E[] types, String message) {
        return Arrays.stream(types).filter(t -> t.isMessage(message)).findFirst().orElse(null);
    }

    /**
     * 获取 枚举描述信息 message
     *
     * @param types {@link E}
     * @param code  {@link Integer}
     * @return {@link String}
     * @author XJH
     * @since 2023/4/26
     **/
    static <E extends TypeRef> String getMessageByCode(E[] types, Integer code) {
        return Arrays.stream(types).filter(t -> t.isCode(code)).map(TypeRef::message).findFirst().orElse(null);
    }

}
