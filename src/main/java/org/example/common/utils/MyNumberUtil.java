package org.example.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author XJH
 * @version 1.0
 * @since 2023/8/8
 */
public class MyNumberUtil extends DateUtil {

    /**
     * 支持范围参考 {@link BigDecimal}
     *
     * @param str 字符串值
     * @return 是否为数字
     */
    public static boolean isNumber(String str) {
        if (StrUtil.isBlank(str)) {
            return Boolean.FALSE;
        }
        try {
            new BigDecimal(str);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }
}
