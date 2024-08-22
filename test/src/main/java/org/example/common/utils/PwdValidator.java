package org.example.common.utils;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * <p>
 * 密码校验
 * AI生成工具类
 * </p>
 *
 * @author XJH
 * @version 1.0
 * @since 2024/8/22
 */
public class PwdValidator {

    /**
     * 校验字符串是否存在三位连续相同或递增递减的字母
     *
     * @param input {@link String}
     * @return {@link boolean}
     * @author author
     * @since 2024年8月22
     **/
    private static boolean validConsecutiveLetters(String input) {
        input = input.toLowerCase(); // 统一转换为小写字母
        char[] chars = input.toCharArray();

        for (int i = 0; i < chars.length - 2; i++) {
            if (Character.isLetter(chars[i]) && Character.isLetter(chars[i + 1]) && Character.isLetter(chars[i + 2])) {
                int first = chars[i];
                int second = chars[i + 1];
                int third = chars[i + 2];

                // 检查三个字母是否全部相等，或者是否构成递增或递减的连续序列
                if ((first == second && second == third) || // 全部相等的情况，例如 aaa
                        (first + 1 == second && second + 1 == third) || // 递增的连续序列，例如 abc
                        (first - 1 == second && second - 1 == third)) { // 递减的连续序列，例如 cba
                    return false; // 发现符合条件的连续字母，返回 false
                }
            }
        }
        return true; // 没有发现符合条件的连续字母，返回 true
    }

    /**
     * 校验字符串是否存在三位连续相同或递增递减的数组
     *
     * @param input {@link String}
     * @return {@link boolean}
     * @author author
     * @since 2024年8月22
     **/
    private static boolean validConsecutiveNumbers(String input) {
        char[] chars = input.toCharArray(); // 将输入字符串转换为字符数组
        for (int i = 0; i < chars.length - 2; i++) {
            if (Character.isDigit(chars[i]) && Character.isDigit(chars[i + 1]) && Character.isDigit(chars[i + 2])) {
                int first = chars[i];
                int second = chars[i + 1];
                int third = chars[i + 2];

                // 检查三个数字是否全部相等，或者是否构成递增或递减的连续序列
                if ((first == second && second == third) || // 全部相等的情况，例如 777
                        (first + 1 == second && second + 1 == third) || // 递增的连续序列，例如 789
                        (first - 1 == second && second - 1 == third)) { // 递减的连续序列，例如 987
                    return false; // 发现符合条件的连续数字，返回 false
                }
            }
        }
        return true; // 没有发现符合条件的连续数字，返回 true
    }

    /**
     * 校验密码是否符合规则
     *
     * @param pwd   {@link String}
     * @param regex {@link String}
     * @return {@link boolean}
     * @author author
     * @since 2024年8月22
     **/
    private static boolean validRegex(String pwd, String regex) {
        if (Objects.isNull(regex) || regex.isEmpty()) return true;
        var pattern = Pattern.compile(regex);
        // 使用matcher方法来匹配密码
        return pattern.matcher(pwd).matches();
    }

    /**
     * 密码中不能存在三位连续或相同字母、三位连续或相同数字
     *
     * @param input {@link String}
     * @return {@link Boolean}
     * @author author
     * @since 2024年8月22
     **/
    public static Boolean valid(String input) {
        if (Objects.isNull(input) || input.isEmpty()) {
            return false;
        }
        return validConsecutiveLetters(input) && validConsecutiveNumbers(input);
    }

    /**
     * 密码中不能存在三位连续或相同字母、三位连续或相同数字
     *
     * @param input {@link String}
     * @return {@link Boolean}
     * @author author
     * @since 2024年8月22
     **/
    public static Boolean valid(String input, String regex) {
        if (Objects.isNull(input) || input.isEmpty()) {
            return false;
        }
        return validRegex(input, regex) && validConsecutiveLetters(input) && validConsecutiveNumbers(input);
    }

    public static void main(String[] args) {
        System.out.println("密码例子【Aa@123456】校验: " + PwdValidator.valid("Aa@123456"));
        System.out.println("密码例子【Jiahe@12345】校验: " + PwdValidator.valid("Jiahe@12345"));
        System.out.println("密码例子【Jiahe@202408】校验: " + PwdValidator.valid("Jiahe@202408"));
        System.out.println("密码例子【778】校验: " + PwdValidator.valid("778"));
        System.out.println("密码例子【456】校验: " + PwdValidator.valid("456"));
        System.out.println("密码例子【aab】校验: " + PwdValidator.valid("aab"));
        System.out.println("密码例子【efg】校验: " + PwdValidator.valid("efg"));
    }

}