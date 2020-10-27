package com.pointcoil.util;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import com.pointcoil.dto.map.ThermodynamicDTO;
import com.pointcoil.service.adminmap.excelUp;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;


@Component
public class ExcelEventParser {


    public void processFirstSheet(String filename) throws Exception {
        try(OPCPackage pkg = OPCPackage.open(filename,PackageAccess.READ);){
            XSSFReader r = new XSSFReader( pkg );
            SharedStringsTable sst = r.getSharedStringsTable();
            XMLReader parser = fetchSheetParser(sst);
            //process the first sheet
            try(InputStream sheet = r.getSheetsData().next()){
                InputSource sheetSource = new InputSource(sheet);
                parser.parse(sheetSource);
            }
        }

    }


    public void processAllSheets(String filename) throws Exception {
        try (OPCPackage pkg = OPCPackage.open(filename, PackageAccess.READ)) {
            XSSFReader r = new XSSFReader(pkg);
            SharedStringsTable sst = r.getSharedStringsTable();

            XMLReader parser = fetchSheetParser(sst);

            Iterator<InputStream> sheets = r.getSheetsData();
            while (sheets.hasNext()) {
                System.out.println("Processing new sheet:\n");
                try (InputStream sheet = sheets.next()) {
                    InputSource sheetSource = new InputSource(sheet);
                    parser.parse(sheetSource);
                }
                System.out.println("");
            }
        }
    }

    public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException, ParserConfigurationException {
        XMLReader parser =SAXHelper.newXMLReader();
        ContentHandler handler = new SheetHandler(sst);
        parser.setContentHandler(handler);
        return parser;
    }


    public static List<ThermodynamicDTO> getThermodynamicDTO() throws Exception {
            return SheetHandler.getThermodynamicDTOS();
    }

    public static void removeThredList(){
        SheetHandler.removeThredList();
    }






    /**
     * See org.xml.sax.helpers.DefaultHandler javadocs 重写 startElement characters endElements方法 
     *
     * SheetHandler类说明：程序依次调用重写的startElement，characters，endElement方法
     *
     */
    private static class SheetHandler extends DefaultHandler {
        private SharedStringsTable sst;
        private String lastContents;
        private boolean nextIsString; //是否为string格式标识
        private final LruCache<Integer,String> lruCache = new LruCache<>(60);
		private int sheetIndex = -1;
		private int curRow = 0;
		private int curCol = 0;
		static final ThreadLocal<List<ThermodynamicDTO>> threadLocal = new ThreadLocal<List<ThermodynamicDTO>>();


		public static void removeThredList(){
            threadLocal.remove();
        }

        public static List<ThermodynamicDTO> getThermodynamicDTOS() throws Exception {
            List<ThermodynamicDTO> list = threadLocal.get();
            try {
                if (list == null) {
                    list = new ArrayList<ThermodynamicDTO>();
                    threadLocal.set(list);
                }
            } catch (Exception ex) {
                throw new Exception(ex);
            }

            return list;
        }

        private List<String> rowlist = new ArrayList<String>();


        /**
         * 缓存
         * @author Administrator
         *
         * @param <A>
         * @param <B>
         */
        private static class LruCache<A,B> extends LinkedHashMap<A, B> {
            private final int maxEntries;

            public LruCache(final int maxEntries) {
                super(maxEntries + 1, 1.0f, true);
                this.maxEntries = maxEntries;
            }
            @Override
            protected boolean removeEldestEntry(final Map.Entry<A, B> eldest) {
                return super.size() > maxEntries;
            }
        }

        private SheetHandler(SharedStringsTable sst) {
            this.sst = sst;
        }

        @Override
        public void startElement(String uri, String localName, String name,
                                 Attributes attributes) throws SAXException {
            // c => cell 代表单元格
            if(name.equals("c")) {
                // Print the cell reference
                //获取单元格的位置，如A1,B1
            //    System.err.print(attributes.getValue("r") + " - ");
                // Figure out if the value is an index in the SST 如果下一个元素是 SST 的索引，则将nextIsString标记为true
                //单元格类型
                String cellType = attributes.getValue("t");
                //cellType值 s:字符串 b:布尔 e:错误处理

                if(cellType != null && cellType.equals("s")) {
                    //标识为true 交给后续endElement处理
                    nextIsString = true;
                } else {
                    nextIsString = false;
                }

            }
            // Clear contents cache
            lastContents = "";
            }

        /**
         * 得到单元格对应的索引值或是内容值
         * 如果单元格类型是字符串、INLINESTR、数字、日期，lastIndex则是索引值
         * 如果单元格类型是布尔值、错误、公式，lastIndex则是内容值
         */
        @Override
        public void characters(char[] ch, int start, int length)
                throws SAXException {
            lastContents += new String(ch, start, length);
        }

        @Override
        public void endElement(String uri, String localName, String name)
                throws SAXException {
            // Process the last contents as required.
            // Do now, as characters() may be called more than once
            if(nextIsString) {
                int idx = Integer.parseInt(lastContents);
                lastContents = lruCache.get(idx);
                //如果内容为空 或者Cache中存在相同key 不保存到Cache中
                if(lastContents == null &&!lruCache.containsKey(idx)){
                    lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
                    lruCache.put(idx, lastContents);
                }

                nextIsString = false;
            }

            // v => contents of a cell
            // Output after we've seen the string contents
            if(name.equals("v")) {
                rowlist.add(lastContents);
            }else{
                //如果标签名称为 row , 已到行尾
                if(name.equals("row")){
                    try {
                        optRow(sheetIndex, curRow, rowlist);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    lruCache.clear();
                    rowlist.clear();
                }
            }
           /* if(name.equals("c")){
                rowlist.add(lastContents);
               *//* if(lastContents.equals("")){
                    lastContents = " ";

                }*//*
            }*/

        }

        public void optRow(int sheetIndex, int curRow, List<String> rowlist) throws Exception {
            ThermodynamicDTO thermodynamicDTO
                    = new ThermodynamicDTO
                    (UUID.randomUUID().toString().replace("-",""),rowlist.get(0),rowlist.get(1),rowlist.get(2),rowlist.get(3),rowlist.get(4),rowlist.get(5),rowlist.get(6),rowlist.get(7));
            List<ThermodynamicDTO> thermodynamicDTOS = getThermodynamicDTOS();
            thermodynamicDTOS.add(thermodynamicDTO);
        }




    }
}