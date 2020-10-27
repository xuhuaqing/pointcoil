package com.pointcoil.util;

import com.pointcoil.dto.map.ThermodynamicDTO;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author:WuShuang
 * @date:2020/4/12
 * @ver:1.0
 **/
public class ExcelReader {

    private static Logger logger = Logger.getLogger(ExcelReader.class.getName()); // 日志打印类

    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";

    public static List<ThermodynamicDTO> readExcel(String fileName) {

        Workbook workbook = null;
        FileInputStream inputStream = null;

        try {
            // 获取Excel后缀名
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            // 获取Excel文件
            File excelFile = new File(fileName);
            if (!excelFile.exists()) {
                logger.warning("指定的Excel文件不存在！");
                return null;
            }
            // 获取Excel工作簿
            inputStream = new FileInputStream(excelFile);
            workbook = getWorkbook(inputStream, fileType);

            // 读取excel中的数据
            List<ThermodynamicDTO> resultDataList = parseExcel(workbook);

            return resultDataList;
        } catch (Exception e) {
            logger.warning("解析Excel失败，文件名：" + fileName + " 错误信息：" + e.getMessage());
            return null;
        } finally {
            try {
                if (null != workbook) {
                    workbook.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (Exception e) {
                logger.warning("关闭数据流出错！错误信息：" + e.getMessage());
                return null;
            }
        }
    }


    public static Workbook getWorkbook(InputStream inputStream, String fileType) throws IOException {
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        return workbook;
    }



    /**
     * 解析Excel数据
     * @param workbook Excel工作簿对象
     * @return 解析结果
     */
    private static List<ThermodynamicDTO> parseExcel(Workbook workbook) {
        List<ThermodynamicDTO> resultDataList = new ArrayList<>();
        // 解析sheet
        for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
            Sheet sheet = workbook.getSheetAt(sheetNum);

            // 校验sheet是否合法
            if (sheet == null) {
                continue;
            }

            // 获取第一行数据
            int firstRowNum = sheet.getFirstRowNum();
            Row firstRow = sheet.getRow(firstRowNum);
            if (null == firstRow) {
                logger.warning("解析Excel失败，在第一行没有读取到任何数据！");
            }

            // 解析每一行的数据，构造数据对象
            int rowStart = firstRowNum + 1;
            int rowEnd = sheet.getPhysicalNumberOfRows();
            for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
                Row row = sheet.getRow(rowNum);

                if (null == row) {
                    continue;
                }

                ThermodynamicDTO resultData = convertRowToData(row);
                if (null == resultData) {
                    logger.warning("第 " + row.getRowNum() + "行数据不合法，已忽略！");
                    continue;
                }
                resultDataList.add(resultData);
            }
        }

        return resultDataList;
    }
    private static ThermodynamicDTO convertRowToData(Row row) {
        ThermodynamicDTO resultData = new ThermodynamicDTO();


        Cell cell;
        int cellNum = 0;
        // 获取ID
        cell = row.getCell(cellNum++);
        String id = convertCellValueToString(cell);
        resultData.setId(id);
        // 获取店名
        cell = row.getCell(cellNum++);
        String shopName = convertCellValueToString(cell);
        if (null == shopName || "".equals(shopName)) {
            // 年龄为空
            resultData.setShopName(null);
        } else {
            resultData.setShopName(shopName);
        }
        // 获取星际
        cell = row.getCell(cellNum++);
        String location = convertCellValueToString(cell);
        resultData.setStarClass(location);
        // 获取一级
        cell = row.getCell(cellNum++);
        String firstClassClassification = convertCellValueToString(cell);
        resultData.setFirstClassClassification(firstClassClassification);

        // 获取二级
        cell = row.getCell(cellNum++);
        String twoLevelClassification = convertCellValueToString(cell);
        resultData.setTwoLevelClassification(twoLevelClassification);

        // 获取三级
        cell = row.getCell(cellNum++);
        String threeLevelClassification = convertCellValueToString(cell);
        resultData.setThreeLevelClassification(threeLevelClassification);

        //时间
        cell = row.getCell(cellNum++);
        String time = convertCellValueToString(cell);
        resultData.setTime(time);

        //精度
        cell = row.getCell(cellNum++);
        String wgs_lng = convertCellValueToString(cell);
        resultData.setWgs_lng(wgs_lng);
        //维度
        cell = row.getCell(cellNum++);
        String wgs_lat = convertCellValueToString(cell);
        resultData.setWgs_lat(wgs_lat);
        return resultData;
}


    private static String convertCellValueToString(Cell cell) {
        if(cell==null){
            return null;
        }
        String returnValue = null;
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC:   //数字
                Double doubleValue = cell.getNumericCellValue();

                // 格式化科学计数法，取一位整数
                DecimalFormat df = new DecimalFormat("0");
                returnValue = df.format(doubleValue);
                break;
            case HSSFCell.CELL_TYPE_STRING:    //字符串
                returnValue = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:   //布尔
                Boolean booleanValue = cell.getBooleanCellValue();
                returnValue = booleanValue.toString();
                break;
            case HSSFCell.CELL_TYPE_BLANK:     // 空值
                break;
            case HSSFCell.CELL_TYPE_FORMULA:   // 公式
                returnValue = cell.getCellFormula();
                break;
            default:
                break;
        }
        return returnValue;
    }

}
