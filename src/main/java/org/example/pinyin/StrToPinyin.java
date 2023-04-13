package org.example.pinyin;


import cn.hutool.extra.pinyin.PinyinUtil;

import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;

/**
 * 中文转拼音首字母
 *
 * @author XJH
 * @since 2023/4/10
 **/
public class StrToPinyin {

    public static void main(String[] args) {
        // 获取首字母
        System.out.println(PinyinUtil.getFirstLetter('淦'));
        System.out.println(PinyinUtil.getPinyin("燚").charAt(0));
        Comparator cmp = Collator.getInstance(java.util.Locale.CHINA);
        System.out.println("==================================================");
        // 中文列表按照首字母排序
        String[] arr = {"张三", "李四", "王五", "刘六", "周濤", "戴笠", "戴阿"};
        String[] arr1 = {"生活", "文教", "政治", "自然", "宗教", "經貿", "軍事", "经贸"};
        // 對簡體字有效，戴阿、戴笠在第一個字相同的情況下按拼音比較第二個字，很智能哦
        Arrays.sort(arr, cmp);
        for (int i = 0; i < arr.length; i++)
            // 輸出：戴阿、戴笠、李四、刘六、王五、张三、周濤
            System.out.println(arr[i]);
        System.out.println("==================================================");

        // 對繁體字無效，繁體的“軍事”被拍到了最后
        Arrays.sort(arr1, cmp);
        for (int i = 0; i < arr1.length; i++)
            // 輸出：经贸、生活、文教、政治、自然、宗教、經貿、軍事
            System.out.println(arr1[i]);
    }
}