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
     * 检查字符串中是否存在连续的字母。
     *
     * @param input 输入字符串
     * @return 如果存在连续字母则返回 true，否则返回 false。
     */
    private static boolean validConsecutiveLetters(String input) {
        input = input.toLowerCase();
        char[] chars = input.toCharArray();

        for (int i = 0; i < chars.length - 1; i++) {
            if (Character.isLetter(chars[i]) && Character.isLetter(chars[i + 1])) {
                int diff = (int) chars[i + 1] - (int) chars[i];
                // 检查是否为连续字母或相同的字母
                if (diff == 1 || diff == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 检查字符串中是否存在至少连续三个数字是连续的。
     *
     * @param input 输入字符串
     * @return 如果存在至少连续三个数字是连续的，则返回 true，否则返回 false。
     */
    private static boolean validConsecutiveNumbers(String input) {
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length - 2; i++) {
            if (Character.isDigit(chars[i]) && Character.isDigit(chars[i + 1]) && Character.isDigit(chars[i + 2])) {
                int diff1 = (int) chars[i + 1] - (int) chars[i];
                int diff2 = (int) chars[i + 2] - (int) chars[i + 1];
                // 检查是否为连续数字或相同的数字
                if ((diff1 == 1 || diff1 == 0) && (diff2 == 1 || diff2 == 0)) {
                    return false;
                }
            }
        }
        return true;
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
     * 密码中不能存在两位连续或相同字母、三位连续或相同数字
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
     * 密码中不能存在两位连续或相同字母、三位连续或相同数字
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
    }

}