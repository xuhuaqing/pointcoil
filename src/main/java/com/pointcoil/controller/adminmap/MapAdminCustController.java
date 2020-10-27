package com.pointcoil.controller.adminmap;

import com.pointcoil.dto.admin.AdminPageCommon;
import com.pointcoil.dto.map.BaiDuDTO;
import com.pointcoil.entity.BusClick;
import com.pointcoil.entity.BusClick1;
import com.pointcoil.entity.ExcelBusinessEntity;
import com.pointcoil.entity.ExcelLabelEntity;
import com.pointcoil.service.adminmap.MapAdminCustService;
import com.pointcoil.util.ExcelColumn;
import com.pointcoil.util.ExcelUtils;
import com.pointcoil.util.ResponseUtil;
import com.pointcoil.util.ZipUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by WuShuang on 2019/12/12.
 */
@RestController
@SuppressWarnings("ALL")
@RequestMapping("/cust/")
@Slf4j
public class MapAdminCustController {
    @Autowired
    private MapAdminCustService mapAdminCustService;


    /**
     * 查询定制服务申请
     *
     * @Author: WuShuang on 2019/12/12  14:19
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("findCustomized")
    public String findCustomized(AdminPageCommon adminPageCommon) {
        log.info("入参：{}", adminPageCommon);
        return mapAdminCustService.findCustomized(adminPageCommon);
    }


    /**
     * 删除定制服务信息
     *
     * @Author: WuShuang on 2019/12/12  14:35
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("delCustomized")
    public String delCustomized(@RequestParam("id") String id) {
        return mapAdminCustService.delCustomized(id);
    }

    private final static String EXCEL2003 = "xls";
    private final static String EXCEL2007 = "xlsx";

    /**
     * 上传excel  回显数据
     *
     * @Author: WuShuang on 2019/12/12  15:59
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("updateExcel")
    public String updateExcel(@RequestParam("file") MultipartFile file, @RequestParam("brandId") String brandId,@RequestParam("userId") String userId) {
        List<ExcelBusinessEntity> excelBusinessEntities = ExcelUtils.readExcel("", ExcelBusinessEntity.class, file);
       return mapAdminCustService.updateExcel(excelBusinessEntities,brandId,userId);
    }

    /**
     * 上传excel 标签
     *
     * @Author: WuShuang on 2019/12/13  13:25
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("updateExcelByLabel")
    public String updateExcelByLabel(@RequestParam("file") MultipartFile file, @RequestParam("brandId") String brandId,@RequestParam("userId") String userId){
        List<ExcelLabelEntity> excelLabelEntities = ExcelUtils.readExcel("", ExcelLabelEntity.class, file);
        return mapAdminCustService.updateExcelByLabel(excelLabelEntities,brandId,userId);

    }

    /**
     * 下载商圈模板
     *
     * @Author: WuShuang on 2019/12/13  15:42
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @RequestMapping(value = "/exportExcelByBus", method = RequestMethod.GET)
    public void exportExcelByBus(HttpServletResponse response){
        long t1 = System.currentTimeMillis();
        List<BusClick> list = new ArrayList<>();
        list.add(new BusClick("多边形","","30.389657,120.135201;30.389657,120.284105;30.241008, 120.284105;30.241008,120.135201; 30.389657,120.135201;","星巴克"));
        list.add(new BusClick("矩形","","30.389657,120.135201;30.389657,120.284105;30.241008, 120.284105;30.241008,120.135201; 30.389657,120.135201;","星巴克"));
        list.add(new BusClick("圆形","4003.78500320228","30.485684,120.083782;","星巴克"));
        ExcelUtils.writeExcel(response, BusClick.class,list);
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("write over! cost:%sms", (t2 - t1)));
    }


    @RequestMapping(value = "/thermodynamic", method = RequestMethod.GET)
    public void thermodynamic(HttpServletResponse response){
        long t1 = System.currentTimeMillis();
        List<BusClick> list = new ArrayList<>();
        list.add(new BusClick("99","30.2084","120.21201","星巴克"));
        list.add(new BusClick("99","30.2384","120.22201","星巴克"));
        list.add(new BusClick("99","30.2484","120.23201","星巴克"));
        ExcelUtils.writeExcel(response, BusClick.class,list);
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("write over! cost:%sms", (t2 - t1)));
    }

    @RequestMapping(value = "/downloadLocal", method = RequestMethod.GET)
    public void downloadLocal(HttpServletResponse response) throws IOException {
        ZipUtil.downloadLocal(response);
    }

    @RequestMapping(value = "/downloadWord", method = RequestMethod.GET)
    public void downloadWord(HttpServletResponse response) throws IOException {
        ZipUtil.downloadWord(response);
    }



    @RequestMapping(value = "/exportExcelByLabel", method = RequestMethod.GET)
    public void exportExcelByLabel(HttpServletResponse response){
        long t1 = System.currentTimeMillis();
        List<BusClick1> list = new ArrayList<>();
        list.add(new BusClick1("好佛我徽","海上覅奥我我核","d475821f04f111eaaf0d0242ac110003","34.7232","106.8256"));
        list.add(new BusClick1("核是奥","好好否否安爱丢","d4745d3c04f111eaaf0d0242ac110003","34.6079","106.4594"));
        list.add(new BusClick1("讲哦我好","我教我我会哦","d473771b04f111eaaf0d0242ac110003","34.8387","106.9188"));
        ExcelUtils.writeExcel(response, BusClick1.class,list);
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("write over! cost:%sms", (t2 - t1)));
    }


    /** 总行数 */

    private int totalRows = 0;

    /** 总列数 */

    private int totalCells = 0;

    /** 错误信息 */

    private String errorInfo;


    public int getTotalCells()
    {

        return totalCells;

    }


    /**
     * 按行读取
     *
     * @Author: WuShuang on 2019/12/12  16:41
     * @param: [wb]
     * @return: java.util.List<java.util.List<java.lang.String>>
     * @Description:
     */
    private List<List<String>> read(Workbook wb)
    {

        List<List<String>> dataLst = new ArrayList<List<String>>();

        /** 得到第一个shell */

        Sheet sheet = wb.getSheetAt(0);

        /** 得到Excel的行数 */

        this.totalRows = sheet.getPhysicalNumberOfRows();

        /** 得到Excel的列数 */

        if (this.totalRows >= 1 && sheet.getRow(0) != null)
        {

            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();

        }
        System.err.println("列数："+totalCells);

        /** 循环Excel的行 */

        for (int r = 0; r < this.totalRows; r++)
        {

            Row row = sheet.getRow(r);

            if (row == null)
            {

                continue;

            }

            List<String> rowLst = new ArrayList<String>();

            /** 循环Excel的列 */

            for (int c = 0; c < this.getTotalCells(); c++)
            {

                Cell cell = row.getCell(c);

                String cellValue = "";

                if (null != cell)
                {
                    // 以下是判断数据的类型
                    switch (cell.getCellType())
                    {
                        case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                            cellValue = cell.getNumericCellValue() + "";
                            break;

                        case HSSFCell.CELL_TYPE_STRING: // 字符串
                            cellValue = cell.getStringCellValue();
                            break;

                        case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                            cellValue = cell.getBooleanCellValue() + "";
                            break;

                        case HSSFCell.CELL_TYPE_FORMULA: // 公式
                            cellValue = cell.getCellFormula() + "";
                            break;

                        case HSSFCell.CELL_TYPE_BLANK: // 空值
                            cellValue = "";
                            break;

                        case HSSFCell.CELL_TYPE_ERROR: // 故障
                            cellValue = "非法字符";
                            break;

                        default:
                            cellValue = "未知类型";
                            break;
                    }
                }

                rowLst.add(cellValue);

            }

            /** 保存第r行的第c列 */

            dataLst.add(rowLst);

        }

        return dataLst;

    }


}
