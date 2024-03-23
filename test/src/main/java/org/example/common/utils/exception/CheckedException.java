package org.example.common.utils.exception;

/**
 * <p>
 * 自定义断言
 * </p>
 *
 * @author XJH
 * @version 1.0
 * @since 2023/6/13
 */
public class CheckedException extends BaseException {
    private static final long serialVersionUID = 1L;

    public CheckedException(String message) {
        super(message);
    }
}
