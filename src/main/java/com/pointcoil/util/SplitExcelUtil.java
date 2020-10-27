package com.pointcoil.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.impl.ServletContextCleaner;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * @FileName SplitExcelUtil.java
 * @Description: 分割文件excel工具类，将excel分割成多个
 * @Date 2018年7月27日 上午10:34:30
 * @version 1.0
 */
public class SplitExcelUtil {
    /**
     * @Title: splitExcels
     * @Description:切割Excel文件方法
     * @param filePath
     * @return
     * @date 2018年7月27日 下午2:27:57
     */
    public static List<String> splitExcels(String filePath, int FileMaxNum){
        //拆分文件存储路径
          String splitPath="/code/point2/excel";
        // String splitPath="D:\\testExcel";
        ArrayList<String> splitExcelPath = new ArrayList<>();
        try {
            //前期工作：清空分割存储文件夹
            File[] splitFiles=new File(splitPath).listFiles();;
            if(null!=splitFiles){
                for(File f2: splitFiles){
                    f2.delete();
                }
                System.err.println("删除切分文件");
            }
            //获取分割原文件
            File file = new File(filePath);
            File[] files = file.listFiles();
            for (File f : files) {
                Map<String, HSSFWorkbook> map=getSplitMap(f.getAbsolutePath(),FileMaxNum);//得到拆分后的子文件存储对象
                createSplitHSSFWorkbook(map, splitPath, f.getName(),splitExcelPath);//遍历对象生成的拆分文件
                System.out.println("文件已被拆分为"+map.size()+"个文件.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return splitExcelPath;
    }

    /**
     * @Title: getSplitMap
     * @Description:将第一列的值作为键值,将一个文件拆分为多个文件
     * @param fileName
     * @param FileMaxNum
     * @return
     * @throws Exception
     * @date 2018年7月27日 下午2:29:30
     */
    public static Map<String,HSSFWorkbook> getSplitMap(String fileName,int FileMaxNum) throws Exception{

        Map<String,HSSFWorkbook> map=new HashMap<String,HSSFWorkbook>();
        InputStream is = new FileInputStream(new File(fileName));
        //根据输入流创建Workbook对象   
        Workbook wb = WorkbookFactory.create(is);
        //get到Sheet对象   
        Sheet sheet = wb.getSheetAt(0);
        //获取文件行数
        int MaxNum=sheet.getLastRowNum();
        //文件限定行数
        if(FileMaxNum<=0){
            FileMaxNum=2000;//每个文件默认2000条数据
        }
        //FileMaxNum=10;//用于测试
        //分割文件数量
        int fileNum=MaxNum/FileMaxNum;
        Row titleRow=null;
        //这个必须用接口   
        int i=0;

        for(Row row : sheet){//遍历每一行
            int currentFile=1;
            int currentRowNum=row.getRowNum();
            for (int k=0;k<fileNum;k++) {
                if((currentRowNum>=k*FileMaxNum)&&(currentRowNum<(k+1)*FileMaxNum)){
                    currentFile=k+1;
                }
            }
            if(i==0){
                titleRow=row;//得到标题行   
            }else{
                Cell keyCell=row.getCell(0);
                if(keyCell!=null){
                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                }
                String key=String.valueOf(currentFile);
                HSSFWorkbook tempWorkbook=map.get(key);
                if(tempWorkbook==null){//如果以当前行第一列值作为键值取不到工作表   
                    tempWorkbook= new HSSFWorkbook();
                    Sheet tempSheet=tempWorkbook.createSheet();
                    Row firstRow=tempSheet.createRow(0);
                    for(short k=0;k<titleRow.getLastCellNum();k++){//为每个子文件创建标题   
                        Cell c=titleRow.getCell(k);
                        Cell newcell=firstRow.createCell(k);
                        newcell.setCellValue(c.getStringCellValue());
                    }
                    map.put(key,tempWorkbook);
                }
                Sheet secSheet=tempWorkbook.getSheetAt(0);
                Row secRow=secSheet.createRow(secSheet.getLastRowNum()+1);
                HSSFCellStyle cellStyle =tempWorkbook.createCellStyle();
                for(short m=0;m<row.getLastCellNum();m++){
                    Cell newcell=secRow.createCell(m);
                    setCellValue(newcell,row.getCell(m),tempWorkbook,cellStyle);
                }
                map.put(key,tempWorkbook);
            }
            i=i+1;//行数加一      
        }
        return map;
    }

    /**
     * @Title: createSplitHSSFWorkbook
     * @Description:创建文件
     * @param map
     * @param savePath
     * @param fileName
     * @param splitExcelPath
     * @throws IOException
     * @date 2018年7月27日 下午2:28:34
     */
    public static void createSplitHSSFWorkbook(Map<String, HSSFWorkbook> map, String savePath, String fileName, ArrayList<String> splitExcelPath)
            throws IOException {
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            HSSFWorkbook val = (HSSFWorkbook) entry.getValue();
            File filePath=new File(savePath);
            if(!filePath.exists()){
                //存放目录不存在,自动为您创建存放目录
                filePath.mkdir();
            }
            if(!filePath.isDirectory()){
                //无效文件目录
                return ;
            }
            File file=new File(savePath+"/"+key+"_"+fileName);
            FileOutputStream fOut;// 新建输出文件流
            try {
                fOut = new FileOutputStream(file);
                splitExcelPath.add(file.getAbsolutePath());
                val.write(fOut); // 把相应的Excel工作薄存盘
                fOut.flush();
                fOut.close(); // 操作结束，关闭文件   
            } catch (FileNotFoundException e) {
                System.out.println("找不到文件");
            }
        }
    }

    /**
     * @Title: setCellValue
     * @Description:将一个单元格的值赋给另一个单元格
     * @param newCell
     * @param cell
     * @param wb
     * @param cellStyle
     * @date 2018年7月27日 上午2:28:54
     */
    public static void setCellValue(Cell newCell,Cell cell,HSSFWorkbook wb,HSSFCellStyle cellStyle){

        if(cell==null){
            return;
        }
        switch(cell.getCellType()){
            case Cell.CELL_TYPE_BOOLEAN:
                newCell.setCellValue(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if(DateUtil.isCellDateFormatted(cell)){
                    HSSFDataFormat format= wb.createDataFormat();
                    cellStyle.setDataFormat(format.getFormat("yyyy/m/d"));
                    newCell.setCellStyle(cellStyle);
                    newCell.setCellValue(cell.getDateCellValue());
                }else{
                    //读取数字
                    newCell.setCellValue(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_FORMULA:
                newCell.setCellValue(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_STRING:
                newCell.setCellValue(cell.getStringCellValue());
                break;
        }
    }
   /* public static void main(String[] arg){
        //导入设备文件地址
        String filePath = "export/deviceExcel";
        //每批导入excel总行数
        int FileMaxNum = 2000;
        String splitPath=SplitExcelUtil.splitExcels(filePath,FileMaxNum);
        System.out.println("已分割好文件，文件位置："+splitPath);
    }*/
} 