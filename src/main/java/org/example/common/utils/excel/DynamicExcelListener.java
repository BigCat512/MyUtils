package org.example.common.utils.excel;

import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * EasyExcel监听器
 *
 * @author Administrator
 */
public class DynamicExcelListener extends AnalysisEventListener<Map<Integer, String>> {

    private static final Logger log = LoggerFactory.getLogger(DynamicExcelListener.class);

    /**
     * 表头数据（存储所有的表头数据）
     */
    private final List<Map<Integer, String>> headList = Lists.newArrayList();

    /**
     * 数据体
     */
    private final List<Map<Integer, String>> dataList = Lists.newArrayList();

    /**
     * 这里会一行行的返回头
     *
     * @param headMap 表头
     * @param context context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info("解析到一条头数据:{}", headMap);
        //存储全部表头数据
        headList.add(headMap);
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context context
     */
    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        log.info("解析到一条数据:{}", data);
        dataList.add(data);
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        log.info("所有数据解析完成！");
    }

    public List<Map<Integer, String>> getHeadList() {
        return headList;
    }

    public List<Map<Integer, String>> getDataList() {
        return dataList;
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
