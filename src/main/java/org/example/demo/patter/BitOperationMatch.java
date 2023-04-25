package org.example.demo.patter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 模式匹配是通过位运算来匹配
 *
 * java.util.regex.Pattern#addFlag
 * </p>
 *
 * @author XJH
 * @version 1.0
 * @since 2023/3/31
 */
public class BitOperationMatch {
    /**
     * 比如忽略大小写、多行匹配等
     * @param args  args
     * @author XJH
     * @since 2023/3/31
     **/
    public static void main(String[] args) {
        // 大小写匹配
        // int flag = Pattern.CASE_INSENSITIVE;
        // String regx = "Test";
        // String input = "tEsT";
        // Pattern compile = Pattern.compile(regx);
        // System.out.println(compile.matcher(input).find());
        // Pattern compile2 = Pattern.compile(regx, flag);
        // System.out.println(compile2.matcher(input).find());
        // 大小写忽略 + 多行匹配
        int flag = Pattern.CASE_INSENSITIVE | Pattern.MULTILINE;
        String regx = "^dog$";
        String input = "cat\nDog\nTasmanian devil";
        Pattern p1 = Pattern.compile(regx);
        System.out.println(p1.matcher(input).find());
        Pattern p2 = Pattern.compile(regx,Pattern.MULTILINE);
        System.out.println(p2.matcher(input).find());
        Pattern p3 = Pattern.compile(regx, flag);
        System.out.println(p3.matcher(input).find());

    }
}
