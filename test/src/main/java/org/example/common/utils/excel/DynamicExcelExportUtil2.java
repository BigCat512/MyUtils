package org.example.common.utils.excel;

import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import java.util.*;

/**
 * 动态表头导出
 *
 * @author 林炯潮改版，级联下拉
 * @since 2022/4/1
 **/
@Slf4j
public class DynamicExcelExportUtil2 {

    private static final String DEFAULT_SHEET_NAME = "sheet1";

    /**
     * 动态生成模版(单表头)
     *
     * @param headColumns 列名称
     */
    public static byte[] exportTemplateSingleHeader(List<String> headColumns) {
        List<List<String>> excelHead = Lists.newArrayList();
        headColumns.forEach(columnName -> excelHead.add(Collections.singletonList(columnName)));
        return createExcelFile(excelHead, Lists.newArrayList(), Collections.singletonList(new LongestMatchColumnWidthStyleStrategy()));
    }


    public static byte[] exportTemplateSingleHeaderAndSelect(List<String> headColumns, List<WriteHandler> writeHandlerList) {
        List<List<String>> excelHead = Lists.newArrayList();
        headColumns.forEach(columnName -> excelHead.add(Collections.singletonList(columnName)));
        return createExcelFile(excelHead, Lists.newArrayList(), writeHandlerList);
    }

    /**
     * 动态生成模版(单表头 + 下拉框)
     *
     * @param headColumns 列名称
     */
    public static byte[] exportTemplateSingleHeaderAndSelect(List<String> headColumns, Map<Integer, String[]> selectMap) {
        return exportTemplateSingleHeaderAndSelect(headColumns, Collections.singletonList(new CustomSheetWriteHandler(selectMap)));
    }

    /**
     * 动态生成模版(复杂表头)
     *
     * @param excelHead 列名称
     */
    public static byte[] exportTemplateComplexHeader(List<List<String>> excelHead) {
        return createExcelFile(excelHead, Lists.newArrayList(), Collections.singletonList(new LongestMatchColumnWidthStyleStrategy()));
    }

    /**
     * 动态导出文件
     *
     * @param headColumnMap 有序列头部
     * @param dataList      数据体
     */
    public static byte[] exportExcel(LinkedHashMap<String, String> headColumnMap, List<Map<String, Object>> dataList) {
        return exportExcel(headColumnMap, dataList, Collections.singletonList(new LongestMatchColumnWidthStyleStrategy()));
    }

    /**
     * 动态导出文件
     *
     * @param headColumnMap 有序列头部
     * @param dataList      数据体
     */
    public static byte[] exportExcel(LinkedHashMap<String, String> headColumnMap, List<Map<String, Object>> dataList, List<WriteHandler> writeHandler) {
        // 获取列名称
        List<List<String>> excelHead = Lists.newArrayList();
        // key为匹配符，value为列名，如果多级列名用逗号隔开
        if (MapUtils.isNotEmpty(headColumnMap)) {
            headColumnMap.forEach((key, value) -> excelHead.add(Arrays.asList(value.split(","))));
        }
        List<List<Object>> excelRows = Lists.newArrayList();
        if (MapUtils.isNotEmpty(headColumnMap) && CollectionUtils.isNotEmpty(dataList)) {
            dataList.forEach(dataMap -> {
                List<Object> rows = Lists.newArrayList();
                headColumnMap.forEach((key, value) -> {
                    if (dataMap.containsKey(key)) {
                        Object data = dataMap.get(key);
                        rows.add(data);
                    }
                });
                excelRows.add(rows);
            });
        }
        return createExcelFile(excelHead, excelRows, writeHandler);
    }

    private static byte[] createExcelFile(List<List<String>> excelHead, List<List<Object>> excelRows, List<WriteHandler> writeHandlerList) {
        byte[] data = new byte[0];
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            if (CollectionUtils.isNotEmpty(excelHead)) {
                ExcelWriterSheetBuilder builder = EasyExcelFactory.write(outputStream)
                        .head(excelHead)
                        .sheet(DEFAULT_SHEET_NAME);
                // 注册多个写入处理器
                if (CollectionUtils.isNotEmpty(writeHandlerList)) {
                    writeHandlerList.forEach(builder::registerWriteHandler);
                }
                builder.doWrite(excelRows);
                data = outputStream.toByteArray();
            }
        } catch (Exception e) {
            log.error("动态生成excel文件失败，headColumns：" + JSONUtil.toJsonStr(excelHead) + "，excelRows：" + JSONUtil.toJsonStr(excelRows), e);
        }
        return data;
    }

    /**
     * 将 1 2 3 转为 A B C等
     *
     * @param columnNumber
     * @return
     */
    public static String convertToExcelColumnName(int columnNumber) {
        StringBuilder result = new StringBuilder();
        while (columnNumber > 0) {
            columnNumber--;
            result.insert(0, (char) ('A' + columnNumber % 26));
            columnNumber /= 26;
        }
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println("convertToExcelColumnName(26) = " + convertToExcelColumnName(26));
    }

}
