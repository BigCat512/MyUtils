package org.example.common.enums.impl;

import org.example.common.enums.TypeRef;

/**
 * <p>
 * 操作状态
 * </p>
 *
 * @author XJH
 * @version 1.0
 * @since 2023/4/26
 */
public enum LogStatusEnum implements TypeRef {

    /**
     * 操作成功
     */
    START(1, "开始操作"),

    /**
     * 操作成功
     */
    SUCCESS(1, "操作成功"),

    /**
     * 操作失败
     */
    FAILURE(0, "操作失败"),

    ;

    private final Integer code;
    private final String message;

    LogStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
