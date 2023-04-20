package org.example.date;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.example.common.MyDatePattern;

/**
 * <p>
 * 日期计算
 * </p>
 *
 * @author XJH
 * @version 1.0
 * @since 2023/4/14
 */
public class DateCalculate {

    public static void main(String[] args) {
        String dateStr = "2022-07";
        // 2022年1-7月的环比是指跟2021年6-12月比
        DateTime endTimeTemp = DateUtil.parse(dateStr, DatePattern.NORM_MONTH_PATTERN);
        DateTime endTime = DateUtil.endOfMonth(endTimeTemp);
        DateTime startTime = DateUtil.parse(String.valueOf(endTime.year()), MyDatePattern.Year);
        System.out.println(startTime);
        System.out.println(endTime);
        System.out.println("==========================");
        long betweenedMonth = DateUtil.betweenMonth(startTime, endTime, true);
        System.out.println(betweenedMonth);
        System.out.println("==========================");
        DateTime offsetEndTemp = DateUtil.offset(endTime, DateField.YEAR, -1);
        DateTime offsetEnd = DateUtil.offset(offsetEndTemp, DateField.MONTH, -1);
        DateTime offsetStart = DateUtil.offset(offsetEnd, DateField.MONTH, Integer.valueOf("-" + betweenedMonth));
        System.out.println(DateUtil.beginOfMonth(offsetEnd));
        System.out.println(offsetStart);

    }
}
