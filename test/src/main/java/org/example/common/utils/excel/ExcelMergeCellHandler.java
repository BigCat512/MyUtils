package org.example.common.utils.excel;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

/**
 * <p>
 * 自定义单元格合并处理Handler类
 * </p>
 *
 * @author XJH
 * @version 1.0
 * @since 2022/9/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelMergeCellHandler implements CellWriteHandler {
    /**
     * 需要合并的列，从0开始算
     **/
    private int[] mergeColIndex;
    /**
     * 从指定的行开始合并，从0开始算
     **/
    private int mergeRowIndex;

    /**
     * 在单元格上的所有操作完成后调用，遍历每一个单元格，判断是否需要向上合并
     */
    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        // 获取当前单元格行下标
        int curRowIndex = cell.getRowIndex();
        // 获取当前单元格列下标
        int curColIndex = cell.getColumnIndex();
        // 判断是否大于指定行下标，如果大于则判断列是否也在指定的需要的合并单元列集合中
        if (curRowIndex > mergeRowIndex) {
            for (int colIndex : mergeColIndex) {
                if (curColIndex == colIndex) {
                    // 当前行、前一行
                    Row row = cell.getSheet().getRow(curRowIndex);
                    Row preRow = cell.getSheet().getRow(curRowIndex - 1);
                    // 唯一标识
                    StringBuilder curId = new StringBuilder();
                    StringBuilder preId = new StringBuilder();
                    for (int i = 0; i <= colIndex; i++) {
                        curId.append(row.getCell(i).getStringCellValue());
                        preId.append(preRow.getCell(i).getStringCellValue());
                    }
                    // 唯一标识相同时进行单元格合并
                    if (StrUtil.equals(curId, preId)) {
                        // 如果都符合条件，则向上合并单元格
                        this.mergeWithPrevRow(writeSheetHolder, cell, curRowIndex, curColIndex);
                        break;
                    }
                }
            }
        }
    }

    /**
     * 当前单元格向上合并
     *
     * @param writeSheetHolder 表格处理句柄
     * @param cell             当前单元格
     * @param curRowIndex      当前行
     * @param curColIndex      当前列
     * @author XJH
     * @since 2022/9/22
     */
    private void mergeWithPrevRow(WriteSheetHolder writeSheetHolder, Cell cell, int curRowIndex, int curColIndex) {
        // 获取当前单元格数值
        Object currData = cell.getCellType() == CellType.STRING ? cell.getStringCellValue() : cell.getNumericCellValue();
        // 获取当前单元格正上方的单元格对象
        Cell preCell = cell.getSheet().getRow(curRowIndex - 1).getCell(curColIndex);
        // 获取当前单元格正上方的单元格的数值
        Object preData = preCell.getCellType() == CellType.STRING ? preCell.getStringCellValue() : preCell.getNumericCellValue();

        // 将当前单元格数值与其正上方单元格的数值比较
        if (preData.equals(currData)) {
            Sheet sheet = writeSheetHolder.getSheet();
            List<CellRangeAddress> mergeRegions = sheet.getMergedRegions();
            // 当前单元格的正上方单元格是否是已合并单元格
            boolean isMerged = false;
            for (int i = 0; i < mergeRegions.size() && !isMerged; i++) {
                CellRangeAddress address = mergeRegions.get(i);
                // 若上一个单元格已经被合并，则先移出原有的合并单元，再重新添加合并单元
                if (address.isInRange(curRowIndex - 1, curColIndex)) {
                    sheet.removeMergedRegion(i);
                    address.setLastRow(curRowIndex);
                    sheet.addMergedRegion(address);
                    isMerged = true;
                }
            }
            // 若上一个单元格未被合并，则新增合并单元
            if (!isMerged) {
                CellRangeAddress cellRangeAddress = new CellRangeAddress(curRowIndex - 1, curRowIndex, curColIndex, curColIndex);
                sheet.addMergedRegion(cellRangeAddress);
            }
        }
    }
}
