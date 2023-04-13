package org.example.regex;

import com.mifmif.common.regex.Generex;

import java.util.List;

/**
 * <p>
 * 正则表达式逆向字符串: <a href="https://houbb.github.io/2021/09/05/Generex">...</a>
 * </p>
 *
 * @author XJH
 * @version 1.0
 * @since 2023/4/13
 */
public class RegexToStr {

    // 电话号码正则表达式（支持手机号码，3-4位区号，7-8位直播号码，1－4位分机号）:
    //      ((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)
    // 身份证号(15位、18位数字)，最后一位是校验位，可能为数字或字符X：
    //      (^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)
    // 帐号是否合法(字母开头，允许5-16字节，允许字母数字下划线)：
    //      ^[a-zA-Z][a-zA-Z0-9_]{4,15}$
    // 密码(以字母开头，长度在6~18之间，只能包含字母、数字和下划线)：
    //      ^[a-zA-Z]\w{5,17}$
    // 强密码(必须包含大小写字母和数字的组合，不能使用特殊字符，长度在 8-10 之间)：
    //      ^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{8,10}$
    // 强密码(必须包含大小写字母和数字的组合，可以使用特殊字符，长度在8-10之间)：
    //      ^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$
    public static void main(String[] args) {
        // 正则表达式得去掉 ^ $ 这些匹配标识符号，不然生成的字符串会包含
        Generex generex = new Generex("(\\d{15})|(\\d{18})|(\\d{17}(\\d|X|x))");
        // Generate random String
        String randomStr = generex.random();
        System.out.println(randomStr);
        // a random value from the previous String list
        // generate the second String in lexicographical order that match the given Regex.
        // 这个方法很危险，分分钟爆掉
        // String secondString = generex.getMatchedString(2);
        // System.out.println(secondString);
        // // it print '0b'
        // // Generate all String that matches the given Regex.
        // List<String> matchedStrs = generex.getAllMatchedStrings();
        // // Using Generex iterator
        // com.mifmif.common.regex.util.Iterator iterator = generex.iterator();
        // while(iterator.hasNext()) {
        //     System.out.print(iterator.next() + " ");
        // }

    }
}
