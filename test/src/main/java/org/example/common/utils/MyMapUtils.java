package org.example.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * Map工具类
 * </p>
 *
 * @author XJH
 * @version 1.0
 * @since 2024/7/24
 */
public class MyMapUtils {

    public static void main(String[] args) {
        Map<String, Object> nestedMap = new LinkedHashMap<>();
        nestedMap.put("key1", "value1");

        Map<String, Object> innerMap = new LinkedHashMap<>();
        innerMap.put("key2", "value2");
        innerMap.put("key3", "value3");

        nestedMap.put("key4", innerMap);
        nestedMap.put("key5", "value5");

        // 展平后的有序 Map 结果
        Map<String, Object> flattenedMap = flatten(nestedMap);
        flattenedMap.forEach((key, value) -> System.out.println(key + " = " + value));
    }

    /**
     * 将嵌套的 Map 结构展开为一层的有序 Map 结构。
     *
     * @param inputMap {@link Map<String, Object>}
     * @return {@link  Map<String, Object>}
     * @author author
     * @since 2024/7/24
     **/
    public static Map<String, Object> flatten(Map<String, Object> inputMap) {
        return flatten(inputMap, "");
    }

    /**
     * 递归地将嵌套的 Map 结构展开为一层的有序 Map 结构
     *
     * @param inputMap {@link Map<String, Object>} 要展开的嵌套 Map
     * @param prefix   {@link  String} 当前层级的键前缀
     * @return {@link Map<String, Object>}
     * @author author
     * @since 2024/7/24
     **/
    private static Map<String, Object> flatten(Map<String, Object> inputMap, String prefix) {
        Map<String, Object> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : inputMap.entrySet()) {
            // 先将当前键值对加入结果中，使用带前缀的新键
            result.put(prefix + entry.getKey(), entry.getValue());
            if (entry.getValue() instanceof Map) {
                // 如果值是 Map，则递归调用 flatten 方法，并更新前缀
                Map<String, Object> innerMap = flatten((Map<String, Object>) entry.getValue(), prefix + entry.getKey() + ".");
                result.putAll(innerMap);
            }
        }
        return result;
    }

}