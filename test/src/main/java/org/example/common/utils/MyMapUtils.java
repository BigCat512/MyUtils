package org.example.common.utils;

import java.util.*;

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

    /**
     * 遍历Map处理
     *
     * @param map        {@link Map<String, ?>}
     * @param parentKeys {@link List<String>}
     * @param keys       {@link List<String>}
     * @author author
     * @since 2024/7/26
     **/
    public static void iterateNestedMap(Map<String, ?> map, List<String> parentKeys, List<String> keys) {
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            String currentKey = entry.getKey();
            Object value = entry.getValue();
            // 构造当前层级的TierKey
            List<String> tierKey = new ArrayList<>(parentKeys);
            tierKey.add(currentKey);
            // 收集当前key的List集合
            keys.add(currentKey);

            // 打印当前key、value、TierKey
            System.out.println("Current Key: " + currentKey);
            System.out.println("Value: " + value);
            System.out.println("TierKey: " + String.join(".", tierKey));
            // 业务操作，如对每层键值对做规则验证...
            // 根据值的类型进行递归处理
            if (value instanceof Map) {
                iterateNestedMap((Map<String, ?>) value, tierKey, keys);
            } else if (value instanceof List) {
                iterateList((List<?>) value, tierKey, keys);
            } else if (value != null && value.getClass().isArray()) {
                iterateArray(value, tierKey, keys);
            }
        }
    }

    /**
     * 集合处理
     *
     * @param list       {@link List<?>}
     * @param parentKeys {@link List<String>}
     * @param keys       {@link List<String>}
     * @author author
     * @since 2024/7/26
     **/
    public static void iterateList(List<?> list, List<String> parentKeys, List<String> keys) {
        for (int i = 0; i < list.size(); i++) {
            Object element = list.get(i);
            if (element instanceof Map) {
                iterateNestedMap((Map<String, ?>) element, parentKeys, keys);
            }
        }
    }

    /**
     * 数组处理
     *
     * @param array      {@link Object}
     * @param parentKeys {@link List<String>}
     * @param keys       {@link List<String>}
     * @author author
     * @since 2024/7/26
     **/
    public static void iterateArray(Object array, List<String> parentKeys, List<String> keys) {
        // 处理数组的方法示例，这里假设数组元素是String
        int length = java.lang.reflect.Array.getLength(array);
        for (int i = 0; i < length; i++) {
            Object element = java.lang.reflect.Array.get(array, i);
            if (element instanceof Map) {
                iterateNestedMap((Map<String, ?>) element, parentKeys, keys);
            }
        }
    }


    public static void main(String[] args) {
        // 示例的多层嵌套Map，包含数组嵌套
        Map<String, Object> nestedMap = new HashMap<>();
        nestedMap.put("key1", "value1");
        nestedMap.put("key2", Arrays.asList("item1", "item2", "item3"));

        var item1 = new HashMap() {{
            put("item1", "itemValue1");
        }};
        var item2 = new HashMap() {{
            put("item2", "itemValue2");
        }};
        var item3 = new HashMap() {{
            put("item3", "itemValue3");
        }};
        nestedMap.put("key3", Arrays.asList(item1, item2, item3));


        Map<String, Object> innerMap = new HashMap<>();
        innerMap.put("innerKey1", "innerValue1");
        innerMap.put("innerKey2", "innerValue2");
        nestedMap.put("key3", innerMap);

        // 数组嵌套示例
        Map<String, Object> arrayMap = new HashMap<>();
        arrayMap.put("arrayKey", new String[]{"element1", "element2"});
        nestedMap.put("key4", arrayMap);

        // 调用迭代方法并收集当前key的List集合
        List<String> collectedLists = new ArrayList<>();
        iterateNestedMap(nestedMap, new ArrayList<>(), collectedLists);
        System.out.println("==================================================");
        // 打印收集到的List集合
        for (String list : collectedLists) {
            System.out.println("Collected List: " + list);
        }
    }

}
