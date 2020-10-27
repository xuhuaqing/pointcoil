package com.pointcoil.controller.map;

import com.pointcoil.conf.PointCoilProperties;
import com.pointcoil.dto.geo.*;
import com.pointcoil.dto.map.*;
import com.pointcoil.mapper.ExcelUploadMapper;
import com.pointcoil.mapper.OfficialWebsiteServiceMapper;
import com.pointcoil.mapper.ThermodynamicMapper;
import com.pointcoil.mongo.MongoDbDao;
import com.pointcoil.service.map.impl.AsyncTask;
import com.pointcoil.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.jms.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.pointcoil.constant.Constants.SUCCESS_ADMIN_CODE;
import static com.pointcoil.constant.Constants.SUCCESS_MSG;

/**
 * Created by WuShuang on 2019/11/13.
 */
@RestController
@RequestMapping("/api/excelUpload/")
public class ExcelUploadController {


    @Autowired
    private ExcelUploadMapper excelUploadMapper;

    /**
     * 百度excel 上传
     *
     * @Author: WuShuang on 2019/11/13  14:46
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("baiDuUpload")
    public String baiDuUpload(@RequestParam("file") MultipartFile file){
        long t1 = System.currentTimeMillis();
        List<BaiDuDTO> list = ExcelUtils.readExcel("", BaiDuDTO.class, file);
        System.err.println(list.toString());
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("read over! cost:%sms", (t2 - t1)));
        excelUploadMapper.addBaidu(list);
        return "ok";
    }


    /**
     *店铺 excel上传
     *
     * @Author: WuShuang on 2019/11/14  10:43
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("shopUpload")
    public String shopUpload(@RequestParam("file") MultipartFile file){
        long t1 = System.currentTimeMillis();
        List<ShopDTO> list = ExcelUtils.readExcel("", ShopDTO.class, file);
        System.err.println(list.toString());
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("read over! cost:%sms", (t2 - t1)));
        excelUploadMapper.addShop(list);
        return "ok";
    }





    /**
     * 房天下数据
     *
     * @Author: WuShuang on 2019/11/15  9:51
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("housingWorldUpload")
    public String housingWorldUpload(@RequestParam("file") MultipartFile file){
        long t1 = System.currentTimeMillis();
        List<HousingWorldDTO> list = ExcelUtils.readExcel("", HousingWorldDTO.class, file);
        System.err.println(list.toString());
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("read over! cost:%sms", (t2 - t1)));
        excelUploadMapper.addHousingWorld(list);
        return "ok";
    }



    /**
     *房天下商铺信息
     *
     * @Author: WuShuang on 2019/11/15  11:09
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("shopHousingWorld")
    public String shopHousingWorldUpload(@RequestParam("file") MultipartFile file){
        long t1 = System.currentTimeMillis();
        List<ShopHousingWorldDTO> list = ExcelUtils.readExcel("", ShopHousingWorldDTO.class, file);
        System.err.println(list.toString());
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("read over! cost:%sms", (t2 - t1)));
        excelUploadMapper.addShopHousingWorld(list);
        return "ok";
    }


    /**
     *高德地图 数据
     *
     * @Author: WuShuang on 2019/11/15  13:18
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("gouldUpload")
    public String gouldUpload(@RequestParam("file") MultipartFile file){
        long t1 = System.currentTimeMillis();
        List<GouldDTO> list = ExcelUtils.readExcel("", GouldDTO.class, file);
        System.err.println(list.toString());
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("read over! cost:%sms", (t2 - t1)));
        excelUploadMapper.addGould(list);


      /*  long t1 = System.currentTimeMillis();
        List<GouldDTODB> list = ExcelUtils.readExcel("", GouldDTODB.class, file);
        System.err.println(list.toString());
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("read over! cost:%sms", (t2 - t1)));

        for(GouldDTODB s:list){
            GeoJsonPoint geoJsonPoint = new GeoJsonPoint(new Point(s.getBaiduLon(),s.getBaiduLat()));
            SiteExt4 siteExt4 = new SiteExt4(UUID.randomUUID().toString().replace("-",""),s.getName(),s.getCity(),s.getClassification(),geoJsonPoint);
            mongoDbDao.insert3(siteExt4,"tbl_gould");
        }
*/
        return "ok";
    }


    /**
     *添加链家二手房
     *
     * @Author: WuShuang on 2019/11/15  15:40
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("chainFamilyTwoRoomUpload")
    public String chainFamilyTwoRoomUpload(@RequestParam("file") MultipartFile file){
        long t1 = System.currentTimeMillis();
        List<ChainFamilyTwoRoomDTO> list = ExcelUtils.readExcel("", ChainFamilyTwoRoomDTO.class, file);
        System.err.println(list.toString());
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("read over! cost:%sms", (t2 - t1)));
        excelUploadMapper.addChainFamilyTwoRoom(list);
        return "ok";
    }




    /**
     *链家小区 新房 上传
     *
     * @Author: WuShuang on 2019/11/16  9:24
     * @param: [file]
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("chainFamilyNewHouseUpload")
    public String chainFamilyNewHouseUpload(@RequestParam("file") MultipartFile file){
        long t1 = System.currentTimeMillis();
        List<ChainFamilyNewHouseDTO> list = ExcelUtils.readExcel("", ChainFamilyNewHouseDTO.class, file);
        System.err.println(list.toString());
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("read over! cost:%sms", (t2 - t1)));
        excelUploadMapper.addChainFamilyNewHouseUpload(list);
        return "ok";
    }


    /**
     * 链家 成交量数据
     *
     * @Author: WuShuang on 2019/11/16  11:12
     * @param: [file]
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("chainFamilyTurnoverUpload")
    public String chainFamilyTurnoverUpload(@RequestParam("file") MultipartFile file){
        long t1 = System.currentTimeMillis();
        List<ChainFamilyTurnoverDTO> list = ExcelUtils.readExcel("", ChainFamilyTurnoverDTO.class, file);
        System.err.println(list.toString());
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("read over! cost:%sms", (t2 - t1)));
        excelUploadMapper.addChainFamilyTurnoverUpload(list);
        return "ok";
    }


    /**
     *美团外卖数据
     *
     * @Author: WuShuang on 2019/11/16  13:48
     * @param: [file]
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("meiTuanWMUpload")
    public String meiTuanWMUpload(@RequestParam("file") MultipartFile file){
        long t1 = System.currentTimeMillis();
        List<MeiTuanWmDTO> list = ExcelUtils.readExcel("", MeiTuanWmDTO.class, file);
        System.err.println(list.toString());
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("read over! cost:%sms", (t2 - t1)));
        excelUploadMapper.addMeiTuanWMUpload(list);
        return "ok";
    }


    /**
     *excel 城市数据
     *
     * @Author: WuShuang on 2019/11/20  11:32
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("cityExcelUpload")
    public String cityExcelUpload(@RequestParam("file") MultipartFile file){
        long t1 = System.currentTimeMillis();
        List<CityExcelDTO> list = ExcelUtils.readExcel("", CityExcelDTO.class, file);
        System.err.println(list.toString());
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("read over! cost:%sms", (t2 - t1)));
        excelUploadMapper.addCityExcelUpload(list);
        return "ok";
    }


    @Autowired
    private MongoDbDao mongoDbDao;


    /**
     * 房天下数据DB
     *
     * @Author: WuShuang on 2019/11/15  9:51
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("housingWorldUploadByDB")
    public String housingWorldUploadByDB(@RequestParam("file") MultipartFile file){
        long t1 = System.currentTimeMillis();
        List<HousingWorldDB> list = ExcelUtils.readExcel("", HousingWorldDB.class, file);
        long t2 = System.currentTimeMillis();
        System.err.println("数据："+list.toString());
        System.out.println(String.format("read over! cost:%sms", (t2 - t1)));
        long start = System.currentTimeMillis();
        for(HousingWorldDB s:list){
            if(!StringUtils.isEmpty(s.getHouseNum()) && !StringUtils.isEmpty(s.getWgsLon()) && !StringUtils.isEmpty(s.getWgsLat())){
                GeoJsonPoint geoJsonPoint = new GeoJsonPoint(new Point(s.getWgsLon(),s.getWgsLat()));
                String q = s.getHouseNum().split("户")[0];
                SiteExt siteExt1 = new SiteExt(UUID.randomUUID().toString().replace("-",""),q,geoJsonPoint);
                mongoDbDao.insert(siteExt1,"tbl_housingworld");
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("批量插入耗时: "+(end-start)+" ms");
        return "ok";
    }


    /**
     *添加链家二手房
     *
     * @Author: WuShuang on 2019/11/15  15:40
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("chainFamilyTwoRoomUploadByDB")
    public String chainFamilyTwoRoomUploadByDB(@RequestParam("file") MultipartFile file){
        long t1 = System.currentTimeMillis();
        List<ChainFamilyTwoRoomDB> list = ExcelUtils.readExcel("", ChainFamilyTwoRoomDB.class, file);
        System.err.println(list.toString());
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("read over! cost:%sms", (t2 - t1)));

        for(ChainFamilyTwoRoomDB s:list){
            if(!StringUtils.isEmpty(s.getBedroomsNum()) && !StringUtils.isEmpty(s.getLon()) && !StringUtils.isEmpty(s.getLat())){
                GeoJsonPoint geoJsonPoint = new GeoJsonPoint(new Point(s.getLon(),s.getLat()));
                SiteExt2 siteExt1 = new SiteExt2(UUID.randomUUID().toString().replace("-",""),s.getBedroomsNum()+"",geoJsonPoint);
                mongoDbDao.insert1(siteExt1,"tbl_chainfamily_tworoom");
            }
        }

      /*  long start = System.currentTimeMillis();
        List<ChainFamilyTwoRoomDB> housingWorldDBS = mongoDbDao.insertCollectionByChainFamilyTwoRoom(list);
        long end = System.currentTimeMillis();
        System.out.println("批量插入耗时: "+(end-start)+" ms");*/

        return "ok";
    }

    /**
     * 链家 成交量数据
     *
     * @Author: WuShuang on 2019/11/16  11:12
     * @param: [file]
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("chainFamilyTurnoverUploadByDB")
    public String chainFamilyTurnoverUploadByDB(@RequestParam("file") MultipartFile file){
        long t1 = System.currentTimeMillis();
        List<ChainFamilyTurnoverDB> list = ExcelUtils.readExcel("", ChainFamilyTurnoverDB.class, file);
        System.err.println(list.toString());
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("read over! cost:%sms", (t2 - t1)));
        long start = System.currentTimeMillis();
        for(ChainFamilyTurnoverDB s:list){
            if(!StringUtils.isEmpty(s.getBedrooms_num()) && !StringUtils.isEmpty(s.getLon()) && !StringUtils.isEmpty(s.getLat())){
                GeoJsonPoint geoJsonPoint = new GeoJsonPoint(new Point(s.getLon(),s.getLat()));
              //  SiteExt3 siteExt1 = new SiteExt3(UUID.randomUUID().toString().replace("-",""),s.getBedrooms_num()+"",geoJsonPoint);
               // mongoDbDao.insert2(siteExt1,"tbl_chainfamily_turnover");
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("批量插入耗时: "+(end-start)+" ms");
        return "ok";
    }







    /**
     * 批量上传行业
     *
     * @Author: WuShuang on 2019/11/25  10:23
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("uploadIndustry")
    public String uploadIndustry(@RequestParam("file") MultipartFile file){
        List<IndustryDTO> list = ExcelUtils.readExcel("", IndustryDTO.class, file);
        System.err.println(list.toString());
        for(IndustryDTO s:list){
            if(!StringUtils.isEmpty(s.getWgsLat()) || !StringUtils.isEmpty(s.getWgsLng())){
                GeoJsonPoint geoJsonPoint = new GeoJsonPoint(new Point(s.getWgsLng(),s.getWgsLat()));
             //   SiteExt5 siteExt5 = new SiteExt5(UUID.randomUUID().toString().replace("-",""),s.getOneIndustry(),s.getTwoIndustry(),geoJsonPoint);
               // mongoDbDao.insert4(siteExt5,"tbl_industry");
            }
        }

        return "ok";

    }

    @Autowired
    private ExcelEventParser excelEventParser;
    @Autowired
    private ExcelEventParserTwo excelEventParserTwo;
    @Autowired
    private ExcelEventParserThree excelEventParserThree;


    /**
     *高德地图 数据 mongoDB
     *
     * @Author: WuShuang on 2019/11/15  13:18
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("gouldUploadByDB")
    public synchronized String gouldUploadByDB(@RequestParam(value = "file",required = false) MultipartFile file,@RequestParam(value = "cityName",required = false) String cityName){
        Integer is = excelUploadMapper.isGouldCount(cityName);
        if(is>0){
            excelUploadMapper.deleteGouldData(cityName);
            mongoDbDao.delete(cityName,"tbl_gould");
        }
        String file1 = file(file);
        try {
            excelEventParserThree.processFirstSheet(file1);
            List<GouldDTO> thermodynamicDTOS1 = ExcelEventParserThree.getThermodynamicDTOS();
            for (GouldDTO gouldDTO : thermodynamicDTOS1) {
                System.err.println(gouldDTO);
            }
            thermodynamicDTOS1.remove(0);
            int insertLength = thermodynamicDTOS1.size();
            /**
             * 首页数据
             */
            officialWebsiteServiceMapper.addBrand("4a2ed265fae911e9a2500242ac110003",insertLength);
            String homePageInit = pointCoilProperties.getRedis().getHomePageInit();
            redisUtil.remove(homePageInit);

            int i = 0;
            while (insertLength > 5000){
                List<GouldDTO> areYouHungryDTOS = thermodynamicDTOS1.subList(i, i + 5000);
                excelUploadMapper.addGould(areYouHungryDTOS);
                mongoDbDao.insert3(areYouHungryDTOS,"tbl_gould");
                i = i + 5000;
                insertLength = insertLength - 5000;
            }
            if (insertLength > 0) {
                excelUploadMapper.addGould(thermodynamicDTOS1.subList(i, i + insertLength));
                mongoDbDao.insert3(thermodynamicDTOS1.subList(i, i + insertLength),"tbl_gould");
            }
            System.gc();
            thermodynamicDTOS1.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }


    /**
     *；链家小区上传
     *
     * @Author: WuShuang on 2019/11/15  16:36
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("chainFamilyUpload")
    public String chainFamilyUpload(@RequestParam("file") MultipartFile file,String cityName){
        Integer is = excelUploadMapper.isChainCount(cityName);
        if(is>0){
            excelUploadMapper.deleteChainData(cityName);
            mongoDbDao.delete(cityName,"tbl_chainfamily_turnover");
        }

       // String file1 = file(file);

        long t1 = System.currentTimeMillis();
        List<ChainFamilyDTO> list = ExcelUtils.readExcel("", ChainFamilyDTO.class, file);
        list.stream().forEach(System.out::println);
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("read over! cost:%sms", (t2 - t1)));


/**
 * 首页数据
 *//*
        officialWebsiteServiceMapper.addBrand("4a2ed265fae911e9a2500242ac110003",list.size());
        String homePageInit = pointCoilProperties.getRedis().getHomePageInit();
        redisUtil.remove(homePageInit);
*/

        int insertLength = list.size();
        int i = 0;
        while (insertLength > 3800){
            List<ChainFamilyDTO> chainFamilyDTOS = list.subList(i, i + 3800);
            excelUploadMapper.addChainFamily(chainFamilyDTOS,cityName);
            mongoDbDao.insert2(chainFamilyDTOS,"tbl_chainfamily_turnover",cityName);
            i = i + 3800;
            insertLength = insertLength - 3800;
        }
        if (insertLength > 0) {
            List<ChainFamilyDTO> chainFamilyDTOS = list.subList(i, i + insertLength);
            excelUploadMapper.addChainFamily(chainFamilyDTOS,cityName);
            mongoDbDao.insert2(chainFamilyDTOS,"tbl_chainfamily_turnover",cityName);
        }
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }

    @Autowired
    private ThermodynamicMapper thermodynamicMapper;
    /**
     *饿了吗数据
     *
     * @Author: WuShuang on 2019/11/14  14:53
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("areYouHungryUpload")
    public String areYouHungry(@RequestParam("file") MultipartFile file,@RequestParam("cityName")String cityName){

        Integer is = excelUploadMapper.isAreyouHungrry(cityName);
        if(is>0){
            excelUploadMapper.deleteAreyouHungry(cityName);
        }
        String file1 = file(file);
        try {
            excelEventParserTwo.processFirstSheet(file1);
            List<AreYouHungryDTO> thermodynamicDTOS = ExcelEventParserTwo.getThermodynamicDTOS();
            thermodynamicDTOS.remove(0);
            int insertLength = thermodynamicDTOS.size();

            /**
             * 首页数据
             */
            officialWebsiteServiceMapper.addBrand("4a2ed265fae911e9a2500242ac110003",insertLength);
            String homePageInit = pointCoilProperties.getRedis().getHomePageInit();
            redisUtil.remove(homePageInit);
            int i = 0;
            while (insertLength > 5000){
                List<AreYouHungryDTO> areYouHungryDTOS = thermodynamicDTOS.subList(i, i + 5000);
                excelUploadMapper.addAreYouHungry(areYouHungryDTOS,cityName);

                i = i + 5000;
                insertLength = insertLength - 5000;
            }
            if (insertLength > 0) {
                excelUploadMapper.addAreYouHungry(thermodynamicDTOS.subList(i, i + insertLength),cityName);
            }
            System.gc();
            thermodynamicDTOS.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
       /* try {
            excelEventParserTwo.processFirstSheet("D:/excel/工作簿2.xlsx");
            List<AreYouHungryDTO> thermodynamicDTOS = ExcelEventParserTwo.getThermodynamicDTOS();
            for(AreYouHungryDTO s:thermodynamicDTOS){
                System.err.println(s.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
*/
        thermodynamicMapper.upd();
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }

   /* long t1 = System.currentTimeMillis();
    List<AreYouHungryDTO> list = ExcelUtils.readExcel("", AreYouHungryDTO.class, file);
        System.err.println(list.toString());
    long t2 = System.currentTimeMillis();
        System.out.println(String.format("read over! cost:%sms", (t2 - t1)));
        /
    */
   @Autowired
   private OfficialWebsiteServiceMapper officialWebsiteServiceMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private JmsTemplate jmsTemplate;
    //注入JmsTemplate
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    @Autowired
    private AsyncTask asyncTask;

    /**
     *城市热力图
     *
     * @Author: WuShuang on 2019/11/27  10:07
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("uploadCityThermodynamic")
    public String uploadCityThermodynamic(@RequestParam("file") MultipartFile file,String cityName) throws Exception {


        String headName = file.getOriginalFilename();
        String headLastName = headName.substring(headName.lastIndexOf("."), headName.length());
        String headNewName = UUID.randomUUID() + headLastName;
         String path = "/code/point2/pointexcel";
        //    String path = "D:\\pointexcel";


        //准备工作 先删掉问及那
       /* File[] splitFiles=new File(path).listFiles();
        if(null!=splitFiles){
            for(File f2: splitFiles){
                f2.delete();
            }
            System.err.println("源文件删除");
        }*/
        File file3 = new File(path + File.separator + headNewName);
        try {
            file.transferTo(file3);
            String queue =
                    file3.getAbsolutePath()+","+cityName;
            jmsMessagingTemplate.convertAndSend(this.queue,queue);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //String absolutePath = file3.getAbsolutePath();
        //System.err.println("上传路径："+absolutePath);


        //异步去生成测试报告
        //asyncTask.sendMes(path,cityName);
        //t(path,cityName,file3.getAbsolutePath());


      /* Integer s= excelUploadMapper.isCount(cityName);
       if(s>0){
           excelUploadMapper.deleteThermodynamic(cityName);
           mongoDbDao.delete(cityName,"tbl_industry");
       }*/

        /*String file1 = file(file);
        excelEventParser.processFirstSheet(file1);
        List<ThermodynamicDTO> thermodynamicDTOS = ExcelEventParser.getThermodynamicDTOS();
        thermodynamicDTOS.remove(0);
        int insertLength = thermodynamicDTOS.size();

        *//**
         * 首页数据
         *//*
        officialWebsiteServiceMapper.addBrand("4a2ed265fae911e9a2500242ac110003",insertLength);
        String homePageInit = pointCoilProperties.getRedis().getHomePageInit();
        redisUtil.remove(homePageInit);


        int i = 0;
        while (insertLength > 3800){
            List<ThermodynamicDTO> thermodynamicDTOS1 = thermodynamicDTOS.subList(i, i + 3800);
            excelUploadMapper.uploadCityThermodynamic(thermodynamicDTOS1,cityName);
            mongoDbDao.insert4(thermodynamicDTOS1,"tbl_industry",cityName);
            i = i + 3800;
            insertLength = insertLength - 3800;
        }
        if (insertLength > 0) {
            List<ThermodynamicDTO> thermodynamicDTOS1 = thermodynamicDTOS.subList(i, i + insertLength);
            excelUploadMapper.uploadCityThermodynamic(thermodynamicDTOS1,cityName);
            mongoDbDao.insert4(thermodynamicDTOS1,"tbl_industry", cityName);
        }
        thermodynamicDTOS.clear();
        System.gc();*/
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }


    private void t(String path,String cityName,String splitExcelPath){
        //每批导入excel总行数
        int FileMaxNum = 3000;
        //List<String> splitExcelPath = SplitExcelUtil.splitExcels(path, FileMaxNum);
        //上传完成 删除原文件
        //前期工作：清空分割存储文件夹
        /*File[] splitFiles=new File(path).listFiles();
        if(null!=splitFiles){
            for(File f2: splitFiles){
                f2.delete();
            }
            System.err.println("源文件删除");
        }*/
        ConnectionFactory connectionFactory = jmsMessagingTemplate.getConnectionFactory();
        Session session = null;
        try {
            Connection connection =
                    connectionFactory.createConnection();
            session =
                    connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            Destination destination =
                    session.createQueue(this.queue.getQueueName());
            MessageProducer producer =
                    session.createProducer(destination);

                String queue =
                        splitExcelPath+","+cityName;
                TextMessage textMessage =
                        session.createTextMessage(queue);
                producer.send(textMessage);
                //jmsMessagingTemplate.convertAndSend(this.queue,queue);
                session.commit();
            producer.close();
            session.close();
            connection.close();
            //splitExcelPath.clear();
        }catch (JMSException e){
            e.printStackTrace();
            try{
                session.rollback();
            }catch(JMSException e1){
                e1.printStackTrace();
            }
        }
    }

    @Autowired
    private PointCoilProperties pointCoilProperties;

    public String file(MultipartFile file){
        String headName = file.getOriginalFilename();
        String headLastName = headName.substring(headName.lastIndexOf("."), headName.length());
        String headNewName = UUID.randomUUID() + headLastName;
        String path = pointCoilProperties.getImageUploadUrl();

        File file3 = new File(path + File.separator + headNewName);

        try {
            file.transferTo(file3);
            return file3.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }




    /**
     * 行业数据 批量插入
     *
     * @Author: WuShuang on 2019/12/6  13:34
     * @param: [file]
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("industryUpload")
    public String industryUpload(@RequestParam("file") MultipartFile file){
        List<IndustryDTO.IndustryBrandList> list = ExcelUtils.readExcel("", IndustryDTO.IndustryBrandList.class, file);
        for (int i = 0; i < list.size()-1; i++) {
            for (int j = list.size()-1; j > i; j--) {
                if(list.get(i).getFirstClassification().equals(list.get(j).getFirstClassification())) {
                    list.remove(j);
                }
            }
        }
        excelUploadMapper.industryUpload(list);
        return "ok";
    }


    /**
     * 二级行业
     *
     * @Author: WuShuang on 2019/12/6  14:19
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("twoIndustryUpload")
    public String twoIndustryUpload(@RequestParam("file") MultipartFile file){
        List<IndustryDTO.IndustryBrandList> list = ExcelUtils.readExcel("", IndustryDTO.IndustryBrandList.class, file);
        excelUploadMapper.twoIndustryUpload(list);
        return "ok";
    }


    /**
     *
     *
     * @Author: WuShuang on 2019/12/6  14:19
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("twoIndustryUpload1")
    public String twoIndustryUpload1(@RequestParam("file") MultipartFile file){
        List<IndustryDTO.IndustryBrandList> list = ExcelUtils.readExcel("", IndustryDTO.IndustryBrandList.class, file);
        excelUploadMapper.twoIndustryUpload1(list);
        return "ok";
    }

}
