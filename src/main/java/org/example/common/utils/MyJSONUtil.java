package org.example.common.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

/**
 * <p>
 * JSON工具类
 * </p>
 *
 * @author XJH
 * @version 1.0
 * @since 2023/8/8
 */
public class MyJSONUtil extends JSONUtil {

    /**
     * 校验字符串是否为合法的JSON格式
     *
     * @param str {@link String}
     * @return {@link boolean}
     **/
    public static boolean isJson(String str) {
        boolean jsonObjFlag = isJsonObj(str);
        boolean jsonArrayFlag = isJsonArray(str);
        if (!(jsonObjFlag || jsonArrayFlag)) {
            return Boolean.FALSE;
        }

        if (jsonObjFlag) {
            try {
                parseObj(str);
                jsonObjFlag = Boolean.TRUE;
            } catch (Exception e) {
                jsonObjFlag = Boolean.FALSE;
            }
        }
        if (jsonArrayFlag) {
            try {
                parseArray(str);
                jsonArrayFlag = Boolean.TRUE;
            } catch (Exception e) {
                jsonArrayFlag = Boolean.FALSE;
            }
        }

        return jsonObjFlag || jsonArrayFlag;
    }
}
