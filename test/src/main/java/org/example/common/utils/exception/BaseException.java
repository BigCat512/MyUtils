package org.example.common.utils.exception;

import cn.hutool.extra.spring.SpringUtil;

/**
 * <p>
 * 基础自定义异常
 * </p>
 *
 * @author XJH
 * @version 1.0
 * @since 2023/6/13
 */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BaseException(String message) {
        super(String.format("%s：%s", SpringUtil.getApplicationName(), message));
    }
}
