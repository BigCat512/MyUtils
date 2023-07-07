package org.example.common.utils.excel;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.IoUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.example.common.utils.exception.Asserts;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 编写导入工具类
 *
 * @author Administrator
 */
public class DynamicExcelImportUtil {

    /**
     * 动态获取全部列和数据体，默认从第一行开始解析数据
     *
     * @param file excel文件流
     * @return 数据
     */
    public static List<JSONObject> parseExcel(MultipartFile file) {
        return parseExcel(file, 1);
    }

    /**
     * 动态获取全部列和数据体
     *
     * @param file           excel文件流
     * @param parseRowNumber 指定读取行, 从0开始
     * @return 数据
     */
    public static List<JSONObject> parseExcel(MultipartFile file, Integer parseRowNumber) {
        DynamicExcelListener readListener = new DynamicExcelListener();
        ExcelTypeEnum excelTypeEnum = null;
        if (Objects.requireNonNull(file.getOriginalFilename()).contains(ExcelTypeEnum.XLSX.getValue())) {
            excelTypeEnum = ExcelTypeEnum.XLSX;
        } else if (file.getOriginalFilename().contains(ExcelTypeEnum.XLS.getValue())) {
            excelTypeEnum = ExcelTypeEnum.XLS;
        } else {
            Asserts.cast("文件上传类型不正确，仅限xls、xlsx文件");
        }
        try (BufferedInputStream inputStream = new BufferedInputStream(file.getInputStream())) {
            doRead(parseRowNumber, readListener, excelTypeEnum, inputStream);
        } catch (Exception e) {
            try (BufferedInputStream inputStream = new BufferedInputStream(file.getInputStream())) {
                excelTypeEnum = Objects.equals(excelTypeEnum, ExcelTypeEnum.XLSX) ? ExcelTypeEnum.XLS : ExcelTypeEnum.XLSX;
                doRead(parseRowNumber, readListener, excelTypeEnum, inputStream);
            } catch (Exception ex) {
                Asserts.cast("网络繁忙，excel文件损坏或类型错误");
            }
        }
        List<Map<Integer, String>> headList = readListener.getHeadList();
        Asserts.isFalse(CollectionUtils.isEmpty(headList), "Excel文件未包含表头");
        List<Map<Integer, String>> dataList = readListener.getDataList();
        Asserts.isFalse(CollectionUtils.isEmpty(dataList), "Excel文件未包含数据");
        //获取头部,取最后一次解析的列头数据
        Map<Integer, String> excelHeadIdxNameMap = headList.get(headList.size() - 1);
        //封装数据体
        List<JSONObject> excelDataList = Lists.newArrayList();
        dataList.forEach(dataRow -> {
            JSONObject rowData = new JSONObject(JSONConfig.create().setIgnoreNullValue(false));
            excelHeadIdxNameMap.forEach((key, value) -> {
                String data = dataRow.get(key);
                rowData.set(value, CharSequenceUtil.isBlank(data) ? "" : data);
            });
            excelDataList.add(rowData);
        });
        return excelDataList;
    }

    private static void doRead(Integer parseRowNumber, DynamicExcelListener readListener, ExcelTypeEnum excelTypeEnum, BufferedInputStream inputStream) throws IOException {
        EasyExcelFactory.read(new ByteArrayInputStream(IoUtils.toByteArray(inputStream)))
                .excelType(excelTypeEnum)
                .registerReadListener(readListener)
                .headRowNumber(parseRowNumber)
                .sheet(0)
                .doRead();
    }

    public static void main(String[] args) throws FileNotFoundException {
        String fileName = Objects.requireNonNull(DynamicExcelImportUtil.class.getResource("/")).getPath() + "动态化表头.xlsx";
        FileInputStream inputStream = new FileInputStream(new File(fileName));
        //初始化一个监听器
        DynamicExcelListener easyExcelListener = new DynamicExcelListener();
        //读取文件数据
        EasyExcel.read(inputStream, easyExcelListener).sheet().doRead();
        System.out.println("表头：" + JSONUtil.toJsonStr(easyExcelListener.getHeadList()));
        System.out.println("数据体：" + JSONUtil.toJsonStr(easyExcelListener.getDataList()));
    }
}
