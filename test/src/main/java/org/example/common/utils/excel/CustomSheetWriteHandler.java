package org.example.common.utils.excel;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.example.common.constant.SymbolConstants;

import java.util.Map;

/**
 * 下拉框
 * 参考：https://blog.csdn.net/qq_47605710/article/details/124341796?spm=1001.2101.3001.6661.1&utm_medium=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRselectMap%7ERate-1-124341796-blog-122327881.t5_refersearch_landing&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRselectMap%7ERate-1-124341796-blog-122327881.t5_refersearch_landing&utm_relevant_index=1
 *
 * @author XieJiaHao
 * @since 2022/4/1
 **/
public class CustomSheetWriteHandler implements SheetWriteHandler {
    //动态下拉框的数据：列-下拉数据
    private Map<Integer, String[]> selectMap;

    public CustomSheetWriteHandler(Map<Integer, String[]> selectMap) {
        this.selectMap = selectMap;
    }

    public CustomSheetWriteHandler() {
    }

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        /*
         * 创建sheet，突破下拉框255的限制
         * 获取一个workbook
         */
        DataValidationHelper helper = writeSheetHolder.getSheet().getDataValidationHelper();
        //2.循环赋值（为了防止下拉框的行数与隐藏域的行数相对应，将隐藏域加到结束行之后）
        selectMap.forEach((keyRow, listOfValues) -> {
            //定义sheet的名称
            String hiddenName = "hidden" + keyRow;
            //1.创建一个隐藏的sheet 名称为 hidden
            Workbook workbook = writeWorkbookHolder.getWorkbook();
            Sheet hidden = workbook.createSheet(hiddenName);
            Name category1Name = workbook.createName();
            category1Name.setNameName(hiddenName);
            //下拉框的起始行,结束行,起始列,结束列
            CellRangeAddressList addressList = new CellRangeAddressList(1, 1000, keyRow, keyRow);
            //2.循环赋值
            for (int i = 0, length = listOfValues.length; i < length; i++) {
                // 3:表示你开始的行数  3表示 你开始的列数
                Row row = hidden.getRow(i);
                if (row == null) {
                    row = hidden.createRow(i);
                }
                row.createCell(SymbolConstants.ZERO).setCellValue(listOfValues[i]);
            }
            //4.设置引用公式
            String refers = String.format("= %s!A:A", hiddenName);
            //5 将刚才设置的sheet引用到下拉列表中
            DataValidationConstraint constraint = helper.createFormulaListConstraint(refers);
            DataValidation dataValidation = helper.createValidation(constraint, addressList);
            // 阻止输入非下拉选项的值
            dataValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            dataValidation.setShowErrorBox(Boolean.TRUE);
            dataValidation.setSuppressDropDownArrow(Boolean.TRUE);
            dataValidation.createErrorBox("提示", "请输入下拉选项中的内容");
            writeSheetHolder.getSheet().addValidationData(dataValidation);
            //设置列为隐藏
            int hiddenIndex = workbook.getSheetIndex(hiddenName);
            if (!workbook.isSheetHidden(hiddenIndex)) {
                workbook.setSheetHidden(hiddenIndex, Boolean.TRUE);
            }
        });
    }
}
