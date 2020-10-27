package com.pointcoil.util;

/**
 * Created by WuShuang on 2020/4/9.
 */

    import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

    /**
     * 通用excel导入导出工具类,支持大数据量
     *
     * <p>
     * Title: TaExcelUtils
     * </p>
     *
     * <p>
     * Description:
     * </p>
     *
     * @author zjhua
     *
     * @date 2018年12月6日
     */
    public class test {

        private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        private static final DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        private static final int WINDOW_SIZE = 1000;
        /**
         * 写出到excel
         *
         * @param fileName
         *            目标文件名
         * @param list
         *            数据源
         * @param keys2titlesMap
         * @param skipTitle
         *            是否跳过标题行 标题
         * @throws Exception
         */
        @SuppressWarnings("resource")
        public static void exportExcel(String fileName, String sheetName, List<Map<String, Object>> list,
                                       LinkedHashMap<String, String> keys2titlesMap, boolean skipTitle) throws Exception {
            SXSSFWorkbook wb = new SXSSFWorkbook(WINDOW_SIZE);
            Sheet sh = wb.createSheet(sheetName);
            Iterator<String> keys = keys2titlesMap.keySet().iterator();
            OutputStream out = new FileOutputStream(fileName);
            int startRow = 1;
            int i = 0;
            List<String> keyList = new ArrayList<String>();
            Row rowHeader = null;
            if (!skipTitle) {
                rowHeader = sh.createRow(0);
                startRow = 0;
            }

            while (keys.hasNext()) {
                String key = keys.next();
                keyList.add(key);
                String title = keys2titlesMap.get(key);
                if (!skipTitle) {
                    Cell cellHeader = rowHeader.createCell(i++);
                    cellHeader.setCellValue(title);
                }
            }

            for (int rownum = 1; rownum <= list.size(); rownum++) {
                Row row = sh.createRow(rownum - startRow);
                Map<String, Object> dataMap = list.get(rownum - 1);
                i = 0;

                for (String key : keyList) {
                    Cell cell = row.createCell(i++);
                    if (dataMap.get(key) instanceof String) {
                        cell.setCellValue(MapUtils.getString(dataMap, key));
                    } else if (dataMap.get(key) instanceof Date) {
                        cell.setCellValue(simpleDateFormat.format((Date) dataMap.get(key)));
                    } else if (dataMap.get(key) instanceof Number) {
                        cell.setCellValue(MapUtils.getDouble(dataMap, key));
                    } else if (dataMap.get(key) instanceof Boolean) {
                        cell.setCellValue(MapUtils.getBooleanValue(dataMap, key));
                    }
                }

                if ((rownum - startRow) % WINDOW_SIZE == 0) {
                    ((SXSSFSheet) sh).flushRows();
                }
            }
            wb.write(out);
            out.flush();
            out.close();
            // dispose of temporary files backing this workbook on disk
            wb.dispose();
        }

        public static <T> Object getValue(T t, String key) {
            Method readMethod = org.springframework.beans.BeanUtils.getPropertyDescriptor(t.getClass(), key)
                    .getReadMethod();
            try {
                return readMethod.invoke(t);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 写bean列表到excel
         *
         * @param fileName
         * @param sheetName
         * @param list
         * @param keys2titlesMap
         * @param skipTitle
         * @throws Exception
         */
        @SuppressWarnings("resource")
        public static <T> void exportExcelForBean(String fileName, String sheetName, List<T> list,
                                                  LinkedHashMap<String, String> keys2titlesMap, boolean skipTitle) throws Exception {
            SXSSFWorkbook wb = new SXSSFWorkbook(WINDOW_SIZE);
            Sheet sh = wb.createSheet(sheetName);
            Iterator<String> keys = keys2titlesMap.keySet().iterator();
            OutputStream out = new FileOutputStream(fileName);

            int i = 0;
            List<String> keyList = new ArrayList<String>();
            Row rowHeader = null;
            if (!skipTitle) {
                rowHeader = sh.createRow(0);
            }

            while (keys.hasNext()) {
                String key = keys.next();
                keyList.add(key);
                String title = keys2titlesMap.get(key);
                if (!skipTitle) {
                    Cell cellHeader = rowHeader.createCell(i++);
                    cellHeader.setCellValue(title);
                }
            }

            for (int rownum = 1; rownum <= list.size(); rownum++) {
                Row row = sh.createRow(rownum);
                T dataMap = list.get(rownum - 1);
                i = 0;

                for (String key : keyList) {
                    Cell cell = row.createCell(i++);
                    if (org.springframework.beans.BeanUtils.findPropertyType(key, dataMap.getClass()) == String.class) {
                        cell.setCellValue(BeanUtils.getProperty(dataMap, key));
                    } else if (org.springframework.beans.BeanUtils.findPropertyType(key,
                            dataMap.getClass()) == Date.class) {
                        cell.setCellValue(simpleDateFormat.format((Date) getValue(dataMap, key)));
                    } else if (org.springframework.beans.BeanUtils.findPropertyType(key,
                            dataMap.getClass()) == Number.class) {
                        cell.setCellValue((double) getValue(dataMap, key));
                    } else if (org.springframework.beans.BeanUtils.findPropertyType(key,
                            dataMap.getClass()) == Boolean.class) {
                        cell.setCellValue((boolean) getValue(dataMap, key));
                    }
                }

                if (rownum % WINDOW_SIZE == 0) {
                    ((SXSSFSheet) sh).flushRows();
                }
            }
            wb.write(out);
            out.close();
        }

        /**
         * 加载excel到map, 3M以内文件适合使用
         *
         * @param fileName
         *            excel文件名
         * @param keys
         *            每个列对应的key
         * @return
         * @throws Exception
         */
        public static List<Map<String, Object>> importExcel(String fileName, String[] keys, boolean skipTitle)
                throws Exception {
            Workbook wb = WorkbookFactory.create(new File(fileName));
            Sheet sheet = wb.getSheetAt(0);

            int startRow = skipTitle ? 2 : 1;
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            for (int rownum = startRow; rownum <= sheet.getLastRowNum(); rownum++) {
                Row row = sheet.getRow(rownum);
                if (row == null) {
                    continue;
                }
                Map<String, Object> map = new HashMap<String, Object>();
                for (int cellnum = 0; cellnum < row.getLastCellNum(); cellnum++) {
                    Cell cell = row.getCell(cellnum);
                    int valType = cell.getCellType();
                    if (valType == Cell.CELL_TYPE_STRING) {
                        map.put(keys[cellnum], cell.getStringCellValue());
                    } else if (valType == Cell.CELL_TYPE_BOOLEAN) {
                        map.put(keys[cellnum], cell.getBooleanCellValue());
                    } else if (valType == Cell.CELL_TYPE_NUMERIC) {
                        if (HSSFDateUtil.isCellDateFormatted(cell)) {
                            // 用于转化为日期格式
                            Date d = cell.getDateCellValue();
                            map.put(keys[cellnum], formater.format(d));
                        } else {
                            map.put(keys[cellnum], cell.getNumericCellValue());
                        }
                    }
                }
                list.add(map);
            }
            return list;
        }

        /**
         * 加载excel到pojo对象,大数据量模式,比如几十万、上百万
         *
         * @param path
         *            excel文件路径
         * @param sheetName
         *            sheet页名称
         * @param keys
         *            每列的key,不允许为空,例如["id","name"]
         * @param clz
         *            bean类
         * @param skipTitle 是否跳过标题行, true:跳过, false:不跳过
         * @return
         * @throws IOException
         * @throws OpenXML4JException
         * @throws ParserConfigurationException
         * @throws SAXException
         */
        public static <T> List<T> readerExcel(String path, String sheetName, String[] keys, Class<T> clz, boolean skipTitle)
                throws IOException, OpenXML4JException, ParserConfigurationException, SAXException {
            return XLSXCovertCSVReader.readerExcel(path, sheetName, keys, clz, skipTitle);
        }

        /**
         * 加载excel为hashmap列表,大数据量模式,比如几十万、上百万
         *
         * @param path
         *            excel文件路径
         * @param sheetName
         *            sheet页名称
         * @param keys
         *            每列的key,不允许为空
         * @param skipTitle 是否跳过标题行, true:跳过, false:不跳过
         * @return List<Map<String, String>>
         * @throws IOException
         * @throws OpenXML4JException
         * @throws ParserConfigurationException
         * @throws SAXException
         */
        public static List<Map<String, String>> readerExcel(String path, String sheetName, String[] keys, boolean skipTitle)
                throws IOException, OpenXML4JException, ParserConfigurationException, SAXException {
            return XLSXCovertCSVReader.readerExcel(path, sheetName, keys, skipTitle);
        }

        /**
         * 加载excel到bean, 从第一个sheet页加载, 3M以内文件适合使用
         *
         * @param fileName
         *            excel文件名
         * @param keys
         *            每个列的key
         * @param clz
         *            bean类名
         * @return bean列表
         * @throws Exception
         */
        public static <T> List<T> importExcelForBean(String fileName, String[] keys, Class<T> clz, boolean skipTitle)
                throws Exception {
            return importExcelForBean(fileName, 0, keys, clz, skipTitle);
        }

        /**
         *      指定要加载的sheet页面
         * @param fileName
         * @param sheetNo
         * @param keys
         * @param clz
         * @return
         * @throws Exception
         */
        public static <T> List<T> importExcelForBean(String fileName, int sheetNo, String[] keys, Class<T> clz,
                                                     boolean skipTitle) throws Exception {
            Workbook wb = WorkbookFactory.create(new File(fileName));
            Sheet sheet = wb.getSheetAt(0);

            List<T> list = new ArrayList<T>();
            int startRow = skipTitle ? 2 : 1;
            for (int rownum = startRow; rownum <= sheet.getLastRowNum(); rownum++) {
                Row row = sheet.getRow(rownum);
                if (row == null) {
                    continue;
                }
                T obj = clz.newInstance();
                for (int cellnum = 0; cellnum < row.getLastCellNum(); cellnum++) {
                    Cell cell = row.getCell(cellnum);
                    int valType = cell.getCellType();
                    if (valType == Cell.CELL_TYPE_STRING) {
                        BeanUtils.setProperty(obj, keys[cellnum], cell.getStringCellValue());
                    } else if (valType == Cell.CELL_TYPE_BOOLEAN) {
                        BeanUtils.setProperty(obj, keys[cellnum], cell.getBooleanCellValue());
                    } else if (valType == Cell.CELL_TYPE_NUMERIC) {
                        if (HSSFDateUtil.isCellDateFormatted(cell)) {
                            // 用于转化为日期格式
                            Date d = cell.getDateCellValue();
                            BeanUtils.setProperty(obj, keys[cellnum], formater.format(d));
                        } else {
                            BeanUtils.setProperty(obj, keys[cellnum], cell.getNumericCellValue());
                        }
                    }
                }
                list.add(obj);
            }
            return list;
        }

        /*public static void main(String[] args) {
            LinkedHashMap<String, String> keys2titlesMap = new LinkedHashMap<>();
            keys2titlesMap.put("id", "序号");
            keys2titlesMap.put("value", "值");
            keys2titlesMap.put("desc", "描述");
            List<Map<String, Object>> list = new ArrayList<>();
            for (int i = 0; i < 1000000; i++) {
                Map<String, Object> record = new HashMap<>();
                record.put("id", i);
                record.put("value", "值" + i);
                record.put("desc", UuidUtil.getTimeBasedUuid() + String.valueOf(i));
                list.add(record);
            }
            String fileName = "d:\\big_excel.xlsx";

            try {
                ExcelUtils.exportExcel(fileName, "sheet1", list, keys2titlesMap, true);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            String[] keys = new String[] { "id", "name","desc" };
            // try {
            // List<Map<String, Object>> result = ExcelUtils.importExcel(fileName, keys);
            // System.out.println("map: " + JsonUtils.toJson(result.subList(0, 100)));
            // } catch (Exception e) {
            // e.printStackTrace();
            // }

            // try {
            // List<Bean> result = ExcelUtils.importExcelForBean(fileName, 0, keys,
            // Bean.class, false);
            // System.out.println("bean: " + JsonUtils.toJson(result.subList(0, 100)));
            // } catch (Exception e) {
            // e.printStackTrace();
            // }

            try {
                List<Bean> list2 = XLSXCovertCSVReader.readerExcel(fileName, "sheet1", keys, Bean.class, true);
                System.out.println(JsonUtils.toJson(list2.subList(0, 100)));
                System.out.println(list2.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/

        public static final class Bean {
            private int id;
            private String name;
            private String desc;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }
    }

    /**
     * 使用CVS模式解决XLSX文件，可以有效解决用户模式内存溢出的问题
     * 该模式是POI官方推荐的读取大数据的模式，在用户模式下，数据量较大、Sheet较多、或者是有很多无用的空行的情况
     * ，容易出现内存溢出,用户模式读取Excel的典型代码如下： FileInputStream file=new
     * FileInputStream("c:\\test.xlsx"); Workbook wb=new XSSFWorkbook(file);
     *
     *
     * @author zjhua
     */
    class XLSXCovertCSVReader {

        /**
         * The type of the data value is indicated by an attribute on the cell. The
         * value is usually in a "v" element within the cell.
         */
        enum xssfDataType {
            BOOL, ERROR, FORMULA, INLINESTR, SSTINDEX, NUMBER,
        }

        /**
         * 使用xssf_sax_API处理Excel,请参考：
         * http://poi.apache.org/spreadsheet/how-to.html#xssf_sax_api
         * <p/>
         * Also see Standard ECMA-376, 1st edition, part 4, pages 1928ff, at
         * http://www.ecma-international.org/publications/standards/Ecma-376.htm
         * <p/>
         * A web-friendly version is http://openiso.org/Ecma/376/Part4
         */
        class MyXSSFSheetHandler extends DefaultHandler {

            /**
             * Table with styles
             */
            private StylesTable stylesTable;

            /**
             * Table with unique strings
             */
            private ReadOnlySharedStringsTable sharedStringsTable;

            /**
             * Destination for data
             */
            private final PrintStream output;

            /**
             * Number of columns to read starting with leftmost
             */
            private final int minColumnCount;

            // Set when V start element is seen
            private boolean vIsOpen;

            // Set when cell start element is seen;
            // used when cell close element is seen.
            private xssfDataType nextDataType;

            // Used to format numeric cell values.
            private short formatIndex;
            private String formatString;
            private final DataFormatter formatter;

            private int thisColumn = -1;
            private int rowNum = 0;
            // The last column printed to the output stream
            private int lastColumnNumber = -1;

            // Gathers characters as they are seen.
            private StringBuffer value;
            private Map<String, String> record;
            private List<Map<String, String>> rows = new ArrayList<>();
            private boolean isCellNull = false;

            /**
             * Accepts objects needed while parsing.
             *
             * @param styles
             *            Table of styles
             * @param strings
             *            Table of shared strings
             * @param cols
             *            Minimum number of columns to show
             * @param target
             *            Sink for output
             */
            public MyXSSFSheetHandler(StylesTable styles, ReadOnlySharedStringsTable strings, int cols,
                                      PrintStream target) {
                this.stylesTable = styles;
                this.sharedStringsTable = strings;
                this.minColumnCount = cols;
                this.output = target;
                this.value = new StringBuffer();
                this.nextDataType = xssfDataType.NUMBER;
                this.formatter = new DataFormatter();
                record = new HashMap<>(this.minColumnCount);
                rows.clear();// 每次读取都清空行集合
            }

            public <T> List<T> getDataList(Class<T> clz) {
                List<T> list = new ArrayList<>();
                try {
                    T obj = clz.newInstance();
                    rows.forEach((map) -> {
                        try {
                            BeanUtils.populate(obj, map);
                            list.add(obj);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                return list;
            }

            /*
             * (non-Javadoc)
             *
             * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
             * java.lang.String, java.lang.String, org.xml.sax.Attributes)
             */
            public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {

                if ("inlineStr".equals(name) || "v".equals(name) || "t".equals(name)) {
                    vIsOpen = true;
                    // Clear contents cache
                    value.setLength(0);
                }
                // c => cell
                else if ("c".equals(name)) {
                    // Get the cell reference
                    String r = attributes.getValue("r");
                    int firstDigit = -1;
                    for (int c = 0; c < r.length(); ++c) {
                        if (Character.isDigit(r.charAt(c))) {
                            firstDigit = c;
                            break;
                        }
                    }
                    thisColumn = nameToColumn(r.substring(0, firstDigit));

                    // Set up defaults.
                    this.nextDataType = xssfDataType.NUMBER;
                    this.formatIndex = -1;
                    this.formatString = null;
                    String cellType = attributes.getValue("t");
                    String cellStyleStr = attributes.getValue("s");
                    if ("b".equals(cellType))
                        nextDataType = xssfDataType.BOOL;
                    else if ("e".equals(cellType))
                        nextDataType = xssfDataType.ERROR;
                    else if ("inlineStr".equals(cellType))
                        nextDataType = xssfDataType.INLINESTR;
                    else if ("s".equals(cellType))
                        nextDataType = xssfDataType.SSTINDEX;
                    else if ("str".equals(cellType))
                        nextDataType = xssfDataType.FORMULA;
                    else if (cellStyleStr != null) {
                        // It's a number, but almost certainly one
                        // with a special style or format
                        int styleIndex = Integer.parseInt(cellStyleStr);
                        XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
                        this.formatIndex = style.getDataFormat();
                        this.formatString = style.getDataFormatString();
                        if (this.formatString == null)
                            this.formatString = BuiltinFormats.getBuiltinFormat(this.formatIndex);
                    }
                }

            }

            /*
             * (non-Javadoc)
             *
             * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
             * java.lang.String, java.lang.String)
             */
            public void endElement(String uri, String localName, String name) throws SAXException {

                String thisStr = null;

                // v => contents of a cell
                if ("v".equals(name) || "t".equals(name)) {
                    // Process the value contents as required.
                    // Do now, as characters() may be called more than once
                    switch (nextDataType) {

                        case BOOL:
                            char first = value.charAt(0);
                            thisStr = first == '0' ? "FALSE" : "TRUE";
                            break;

                        case ERROR:
                            thisStr = "\"ERROR:" + value.toString() + '"';
                            break;

                        case FORMULA:
                            // A formula could result in a string value,
                            // so always add double-quote characters.
                            // thisStr = '"' + value.toString() + '"';
                            thisStr = value.toString();
                            break;

                        case INLINESTR:
                            // TODO: have seen an example of this, so it's untested.
                            XSSFRichTextString rtsi = new XSSFRichTextString(value.toString());
                            // thisStr = '"' + rtsi.toString() + '"';
                            thisStr = rtsi.toString();
                            break;

                        case SSTINDEX:
                            String sstIndex = value.toString();
                            try {
                                int idx = Integer.parseInt(sstIndex);
                                XSSFRichTextString rtss = new XSSFRichTextString(sharedStringsTable.getEntryAt(idx));
                                // thisStr = '"' + rtss.toString() + '"';
                                thisStr = rtss.toString();
                            } catch (NumberFormatException ex) {
                                output.println("Failed to parse SST index '" + sstIndex + "': " + ex.toString());
                            }
                            break;

                        case NUMBER:
                            String n = value.toString();
                            // 判断是否是日期格式
                            if (HSSFDateUtil.isADateFormat(this.formatIndex, n)) {
                                Double d = Double.parseDouble(n);
                                Date date = HSSFDateUtil.getJavaDate(d);
                                thisStr = formateDateToString(date);
                            } else if (this.formatString != null)
                                thisStr = formatter.formatRawCellContents(Double.parseDouble(n), this.formatIndex,
                                        this.formatString);
                            else {
                                thisStr = StringUtils.trimTrailingCharacter(StringUtils.trimTrailingCharacter(n, '0'), '.');
                            }
                            break;

                        default:
                            thisStr = "(TODO: Unexpected type: " + nextDataType + ")";
                            break;
                    }

                    // Output after we've seen the string contents
                    // Emit commas for any fields that were missing on this row
                    if (lastColumnNumber == -1) {
                        lastColumnNumber = 0;
                    }
                    // 判断单元格的值是否为空
                    if (thisStr == null || "".equals(thisStr)) {
                        isCellNull = true;// 设置单元格是否为空值
                    }
                    record.put(keys[thisColumn], thisStr);
                    // Update column
                    if (thisColumn > -1)
                        lastColumnNumber = thisColumn;

                } else if ("row".equals(name)) {

                    // Print out any missing commas if needed
                    if (minColumns > 0) {
                        // Columns are 0 based
                        if (lastColumnNumber == -1) {
                            lastColumnNumber = 0;
                        }
                        // if (isCellNull == false && record[0] != null
                        // && record[1] != null)// 判断是否空行
                        if (isCellNull == false)// 判断是否空行
                        {
                            if (rowNum > 0 || !skipTitle) {
                                Map<String, String> map = new HashMap<>();
                                map.putAll(record);
                                rows.add(map);
                            }
                            rowNum++;
                            isCellNull = false;
                            record.clear();
                        }
                    }
                    lastColumnNumber = -1;
                }
            }

            public List<Map<String, String>> getRows() {
                return rows;
            }

            public void setRows(List<Map<String, String>> rows) {
                this.rows = rows;
            }

            /**
             * Captures characters only if a suitable element is open. Originally was just
             * "v"; extended for inlineStr also.
             */
            public void characters(char[] ch, int start, int length) throws SAXException {
                if (vIsOpen)
                    value.append(ch, start, length);
            }

            /**
             * Converts an Excel column name like "C" to a zero-based index.
             *
             * @param name
             * @return Index corresponding to the specified name
             */
            private int nameToColumn(String name) {
                int column = -1;
                for (int i = 0; i < name.length(); ++i) {
                    int c = name.charAt(i);
                    column = (column + 1) * 26 + c - 'A';
                }
                return column;
            }

            private String formateDateToString(Date date) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期
                return sdf.format(date);

            }

        }

        // /////////////////////////////////////

        private OPCPackage xlsxPackage;
        private int minColumns;
        private String[] keys;
        private PrintStream output;
        private String sheetName;
        boolean skipTitle;

        /**
         * Creates a new XLSX -> CSV converter
         *
         * @param pkg
         *            The XLSX package to process
         * @param output
         *            The PrintStream to output the CSV to
         * @param
         */
        public XLSXCovertCSVReader(OPCPackage pkg, PrintStream output, String sheetName, String[] keys,boolean skipTitle) {
            this.xlsxPackage = pkg;
            this.output = output;
            this.skipTitle = skipTitle;
            this.keys = keys;
            this.minColumns = keys.length;
            this.sheetName = sheetName;
        }

        /**
         * Parses and shows the content of one sheet using the specified styles and
         * shared-strings tables.
         *
         * @param styles
         * @param strings
         * @param sheetInputStream
         */
        public List<Map<String, String>> processSheet(StylesTable styles, ReadOnlySharedStringsTable strings,
                                                      InputStream sheetInputStream) throws IOException, ParserConfigurationException, SAXException {

            InputSource sheetSource = new InputSource(sheetInputStream);
            SAXParserFactory saxFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxFactory.newSAXParser();
            XMLReader sheetParser = saxParser.getXMLReader();
            MyXSSFSheetHandler handler = new MyXSSFSheetHandler(styles, strings, this.minColumns, this.output);
            sheetParser.setContentHandler(handler);
            sheetParser.parse(sheetSource);
            return handler.getRows();
        }

        /**
         * 初始化这个处理程序 将
         *
         * @throws IOException
         * @throws OpenXML4JException
         * @throws ParserConfigurationException
         * @throws SAXException
         */
        public List<Map<String, String>> process()
                throws IOException, OpenXML4JException, ParserConfigurationException, SAXException {

            ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(this.xlsxPackage);
            XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
            List<Map<String, String>> list = null;
            StylesTable styles = xssfReader.getStylesTable();
            XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
            while (iter.hasNext()) {
                InputStream stream = iter.next();
                String sheetNameTemp = iter.getSheetName();
                if (this.sheetName.equals(sheetNameTemp)) {
                    list = processSheet(styles, strings, stream);
                    stream.close();
                }
            }
            return list;
        }

        /**
         * 读取Excel
         *
         * @param path
         *            文件路径
         * @param sheetName
         *            sheet名称
         * @param keys
         *            字段列表
         * @return
         * @throws SAXException
         * @throws ParserConfigurationException
         * @throws OpenXML4JException
         * @throws IOException
         */
        public static List<Map<String, String>> readerExcel(String path, String sheetName, String[] keys, boolean skipTitle)
                throws IOException, OpenXML4JException, ParserConfigurationException, SAXException {
            Assert.notEmpty(keys, "字段列表不能为空!");
            OPCPackage p = OPCPackage.open(path);
            XLSXCovertCSVReader xlsx2csv = new XLSXCovertCSVReader(p, System.out, sheetName, keys,skipTitle);
            List<Map<String, String>> list = xlsx2csv.process();
            p.close();
            return list;
        }

        public static <T> List<T> readerExcel(String path, String sheetName, String[] keys, Class<T> clz, boolean skipTitle)
                throws IOException, OpenXML4JException, ParserConfigurationException, SAXException {
            List<Map<String, String>> list = readerExcel(path, sheetName, keys, skipTitle);
            List<T> beans = new ArrayList<>();
            list.forEach((map) -> {
                try {
                    T obj = clz.newInstance();
                    BeanUtils.populate(obj, map);
                    beans.add(obj);
                } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                    e.printStackTrace();
                }
            });
            return beans;
        }
    }

