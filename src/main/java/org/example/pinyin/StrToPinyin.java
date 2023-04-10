package org.example.pinyin;


import cn.hutool.extra.pinyin.PinyinUtil;

/**
 * 中文转拼音首字母
 *
 * @author XJH
 * @since 2023/4/10
 **/
public class StrToPinyin {

    public static void main(String[] args) {
        System.out.println(PinyinUtil.getFirstLetter('淦'));
        System.out.println(PinyinUtil.getPinyin("燚").substring(0, 1));
    }
}