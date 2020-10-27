package com.pointcoil.controller;

import com.alibaba.fastjson.JSONArray;
import com.pointcoil.conf.PointCoilProperties;
import com.pointcoil.dto.geo.*;
import com.pointcoil.dto.map.*;
import com.pointcoil.mapper.MapBusinessMapper;
import com.pointcoil.mapper.MapLabelMapper;
import com.pointcoil.mapper.PayMapper;
import com.pointcoil.mapper.ThermodynamicMapper;
import com.pointcoil.mongo.MongoDbDao;
import com.pointcoil.util.*;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import static com.pointcoil.constant.Constants.*;


/**
 * @Date: 2019-04-17 09:28
 * @Version: 1.0.0
 **/
@RestController
public class TestController {


  @Autowired
  private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    private long time =   30 * 60 * 1000;

  @Autowired
  private PointCoilProperties pointCoilProperties;

    @Autowired
    private MapLabelMapper mapLabelMapper;

    @Autowired
    private MongoDbDao mongoDbDao;
    @Autowired
    private ThermodynamicMapper thermodynamicMapper;

    @Autowired
    private PayMapper payMapper;

    @PostMapping("deleteCach")
    public void deleteCach(){
        thermodynamicMapper.upd();
    }

    @PostMapping("stes1")
    public void stes1(){
       String  s = payMapper.get("957632622760430295");
        List<String> list = JSONArray.parseArray(s, String.class);
        for (String e:list
             ) {
            System.err.println(e);
        }
    }

    @PostMapping("stes")
    public void stes(String trad_no){
        payMapper.pay(trad_no);
        String userId = payMapper.selectUserId(trad_no);

        Map<String,String> map1 = payMapper.findCityAndTime(trad_no);
        String cityName = map1.get("cityName");
        List<String> list = JSONArray.parseArray(cityName, String.class);
        String time = map1.get("time");
        list.stream().forEach(
                s -> {
                    Integer i = payMapper.purchased(s,userId);
                    if(i == 0){
                        payMapper.updateMember(userId,s,time);
                    }else {
                        payMapper.updaMemberTime(userId,time,s);
                    }
                }
        );


    }
    @PostMapping("splitExcel")
    public String splitExcel(@RequestParam("file") MultipartFile file){
        String headName = file.getOriginalFilename();
        String headLastName = headName.substring(headName.lastIndexOf("."), headName.length());
        String headNewName = UUID.randomUUID() + headLastName;
        String path = "/code/point2/pointexcel";

        File file3 = new File(path + File.separator + headNewName);

        try {
            file.transferTo(file3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //String absolutePath = file3.getAbsolutePath();
        //System.err.println("上传路径："+absolutePath);
        //每批导入excel总行数
        int FileMaxNum = 2000;
        List<String> splitExcelPath = SplitExcelUtil.splitExcels(path, FileMaxNum);
        for (String s: splitExcelPath
             ) {
            System.err.println(s);
        }

        //上传完成 删除原文件
        //前期工作：清空分割存储文件夹
        File[] splitFiles=new File(path).listFiles();
        if(null!=splitFiles){
            for(File f2: splitFiles){
                f2.delete();
            }
            System.err.println("源文件删除");
        }

        return "ok";
    }

    private static List<String> s = new ArrayList<String>();

    @GetMapping("addRule")
    public String addRule(String id){
       /*List<String> list = mapLabelMapper.selectHeatMap();
       for(String s : list){

       }*/
     /*  List<Map<String,String>> list = mapLabelMapper.selectHeatMap(id);
       List<String> s =  mapLabelMapper.selectCity();
        for (Map<String,String> m:list
             ) {
            mapLabelMapper.insertRule(m,s,id);
        }*/

        for (int i = 0; i <3 ; i++) {
            s.add(i+"");
        }
        System.err.println(s);

        return "ok";
    }


    public  String GetUrlS() throws ClientProtocolException, IOException  {
        HttpGet httpGet = new HttpGet(
                "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                        + "wx6d93cbc58407ef48" + "&secret=" + "96333b1335e9f4e829663bd90b1694d0" );
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse res = httpClient.execute(httpGet);
        HttpEntity entity = res.getEntity();
        String result = EntityUtils.toString(entity, "UTF-8");
        JSONObject jsons = JSONObject.fromObject(result);
        String expires_in = jsons.getString("expires_in");

        //缓存
        if(Integer.parseInt(expires_in)==7200){
            //ok
            String access_token = jsons.getString("access_token");
            System.out.println("access_token:"+access_token);
            return access_token;
        }else{
            System.out.println("出错获取token失败！");
            return null;
        }

    }

    @PostMapping("test111")
    public String test111(String replace1){
        String path = pointCoilProperties.getImageUploadUrl();
        String iPurl = pointCoilProperties.getUrl();
        try
        {
            String s = GetUrlS();
            URL url = new URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+s);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            net.sf.json.JSONObject paramJson = new net.sf.json.JSONObject();
            paramJson.put("id", replace1);
            paramJson.put("page", "pages/business-report/business-report");
            paramJson.put("width", 430);
            paramJson.put("auto_color", true);
            /**
             * line_color生效
             * paramJson.put("auto_color", false);
             * JSONObject lineColor = new JSONObject();
             * lineColor.put("r", 0);
             * lineColor.put("g", 0);
             * lineColor.put("b", 0);
             * paramJson.put("line_color", lineColor);
             * */

            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            String replace = UUID.randomUUID().toString().replace("-", "");
            //OutputStream os = new FileOutputStream(new File(path+"/"+replace+".png"));
            OutputStream os = new FileOutputStream(new File("D:/2345/abc.png"));
            int len;
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1)
            {
                os.write(arr, 0, len);
                os.flush();
            }
            os.close();
            return iPurl+replace+".png";
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }



   @PostMapping("test")
    public void test(String userId) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    String homePageInitKey = pointCoilProperties.getRedis().getToExamineEmail();
    redisUtil.lPush(homePageInitKey,"1615955944@qq.com");
    redisUtil.lPush(homePageInitKey,"961338825@qq.com");
       String phoneSearch = pointCoilProperties.getRedis().getPhoneSearch();
       redisUtil.hSet(phoneSearch,"17612714215",System.currentTimeMillis()+"");
       redisUtil.hSet(phoneSearch,"13967693028",System.currentTimeMillis()+"");
       redisUtil.hSet(phoneSearch,"13989838220",System.currentTimeMillis()+"");
       redisUtil.hSet(phoneSearch,"17608789931",System.currentTimeMillis()+"");
       redisUtil.hSet(phoneSearch,"13967693028",System.currentTimeMillis()+"");
       redisUtil.hSet(phoneSearch,"15973753005",System.currentTimeMillis()+"");
       redisUtil.hSet(phoneSearch,"17681847956",System.currentTimeMillis()+"");
       redisUtil.hSet(phoneSearch,"18969255185",System.currentTimeMillis()+"");
       redisUtil.hSet(phoneSearch,"18657111815",System.currentTimeMillis()+"");
       redisUtil.hSet(phoneSearch,"18857166730",System.currentTimeMillis()+"");
       redisUtil.hSet(phoneSearch,"18657161828",System.currentTimeMillis()+"");
//
//       List features = Arrays.asList("Lambdas", "Default Method", "Stream API",
//               "Date and Time API");
//
//       features.forEach(n -> System.out.println(n));
     /*
*/

   /*    List<Point> geopos = redisUtil.geopos("18827f7e0e78430fae95fa08f91af5d3", 0,1);
       System.err.println(geopos.toString());*/

     /*  */
  /*     */

     /*  String phoneSearch = pointCoilProperties.getRedis().getPhoneSearch();
       redisUtil.remove(phoneSearch + userId);*/
/*    net.sf.json.JSONObject city = MapUtils.getCity("116.307852", "40.057031");
    String city1 =(String)city.get("city");
       System.err.println(city);*/
    /*   BusinessZoneDTO.Coordinate redisDta;

       Object oq = redisUtil.get(userId + defaultAddress);
       JSONObject parse = (JSONObject)JSON.parse(oq.toString());
       String lat = (String) parse.get("lat");
       String lng =(String) parse.get("lng");
       System.err.println(parse);
       net.sf.json.JSONObject city = MapUtils.getCity(lng, lat);
           String city1 =(String)city.get("city");
           System.err.println(city1);*/
/*
       String businessZonePageInit = pointCoilProperties.getRedis().getBusinessZonePageInit();
       businessZonePageInit =  businessZonePageInit+userId;
       redisUtil.remove(businessZonePageInit);*/
/*       String isAccountSub = pointCoilProperties.getRedis().getIsAccountSub();
        redisUtil.remove(isAccountSub);*/

    /*   net.sf.json.JSONObject city = MapUtils.getCity("116.307852", "40.057031");
       //String city1 =(String)city.get("province");
       System.err.println(city);*/
}
    private static Random random = new Random();


 /*   @Autowired
    private MapBusinessMapper mapBusinessMapper;
    @PostMapping("createPresentation")
    public Future<String> createPresentation(String replace1, BusinessZoneDTO businessZoneDTO) {
        System.err.println("开始执行异步");
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
        //品牌名称
        BrandDTO.SelectBrandDTO  selectBrandDTO = mapBusinessMapper.selectBrand(new BrandDTO.BrandId(businessZoneDTO.getBrandId()));
        jsonObject.put("businessZoneName",selectBrandDTO.getBrandTitle()+businessZoneDTO.getBusinessName()+"商圈报告");

        //今日关注数量
        jsonObject.put("followNum",random.nextInt(5)+1);
        // TODO 拼接小程序地址
        String s = QRCode.qrCode(replace1);
        jsonObject.put("QRCode",s);
        //商圈截图
        jsonObject.put("screenshotImg",businessZoneDTO.getScreenshotImg());
        //城市
        jsonObject.put("city",businessZoneDTO.getCity());
        //省份
        jsonObject.put("province",businessZoneDTO.getProvince());
        //拿城市数据
        CityExcelDTO cityExcelDTO = mapBusinessMapper.selectCityData(businessZoneDTO.getCity());
        //城市数据
        jsonObject.put("cityData",JSON.toJSONString(cityExcelDTO));
        //商圈面积
        jsonObject.put("area",businessZoneDTO.getBusinessArea());
        //常驻人口，小区，办公，聚客设施
        //如果是圆
        if(businessZoneDTO.getBusinessType() == 2){
            Integer garden = getGarden(businessZoneDTO.getCoordinates().get(0), businessZoneDTO.getRadius());
            //常驻人口
            jsonObject.put("totalPopulation",garden);
            GouldDTODB.Gould gould = gould1(businessZoneDTO.getCoordinates(), businessZoneDTO.getRadius());
            //小区数量
            jsonObject.put("residentialQuartersNum",gould.getResidentialQuartersNum());
            //小区名称
            jsonObject.put("residentialQuartersName",JSON.toJSONString(gould.getResidentialQuartersName()));
            //写字楼数量
            jsonObject.put("officeBuildingNum",gould.getOfficeBuildingNum());
            //写字楼名称
            jsonObject.put("officeBuilding",JSON.toJSONString(gould.getOfficeBuilding()));
            //其他聚客数量
            jsonObject.put("otherNum",gould.getOtherNum());
            //其他聚客名称
            jsonObject.put("otherName",JSON.toJSONString(gould.getOtherName()));
            //一级行业 占比
            List<SiteExt6> siteExt6s = industrySearch1(businessZoneDTO.getCoordinates(), businessZoneDTO.getRadius());
            //总数
            int sum = siteExt6s.stream().mapToInt(p -> p.getValue()).sum();
            //有多少一级行业
            jsonObject.put("oneIndustryCount",siteExt6s.size());
            //行业之间的占比  和 行业名称
            List<IndustryDTO.IndustryList> lists = new ArrayList<>();
            //有多少一级行业
            for(SiteExt6 sq :siteExt6s){
                int value = sq.getValue();
                double div = ArithmeticUtil.div(Double.valueOf(value),Double.valueOf(sum));
                lists.add(new IndustryDTO.IndustryList(sq.getOneIndustry(),div));
            }
            jsonObject.put("oneIndustry",JSON.toJSONString(lists));
            //二级行业的圆
            List<SiteExt6> siteExt6s1 = industrySearch3(businessZoneDTO.getCoordinates(), businessZoneDTO.getRadius());
            //总数
            int sum1 = siteExt6s1.stream().mapToInt(p -> p.getValue()).sum();
            //有多少一级行业
            jsonObject.put("twoIndustryCount",siteExt6s1.size());
            //行业之间的占比  和 行业名称
            List<IndustryDTO.IndustryList> q = new ArrayList<>();
            //有多少er级行业
            for(SiteExt6 sq :siteExt6s1){
                int value = sq.getValue();
                double div = ArithmeticUtil.div(Double.valueOf(value),Double.valueOf(sum1));
                q.add(new IndustryDTO.IndustryList(sq.getTwoIndustry(),div));
            }
            jsonObject.put("twoIndustry",JSON.toJSONString(q));
        }else {
            //如果不是圆
            Integer population = getPopulation(businessZoneDTO.getCoordinates());
            jsonObject.put("totalPopulation",population);
            GouldDTODB.Gould gould = gould(businessZoneDTO.getCoordinates());
            //小区数量
            jsonObject.put("residentialQuartersNum",gould.getResidentialQuartersNum());
            //小区名称
            jsonObject.put("residentialQuartersName",JSON.toJSONString(gould.getResidentialQuartersName()));
            //写字楼数量
            jsonObject.put("officeBuildingNum",gould.getOfficeBuildingNum());
            //写字楼名称
            jsonObject.put("officeBuilding",JSON.toJSONString(gould.getOfficeBuilding()));
            //其他聚客数量
            jsonObject.put("otherNum",gould.getOtherNum());
            //其他聚客名称
            jsonObject.put("otherName",JSON.toJSONString(gould.getOtherName()));
            //一级行业 占比
            List<SiteExt6> siteExt6s = industrySearch(businessZoneDTO.getCoordinates());
            //总数
            int sum1 = siteExt6s.stream().mapToInt(p -> p.getValue()).sum();
            //有多少一级行业
            jsonObject.put("oneIndustryCount",siteExt6s.size());
            //行业之间的占比  和 行业名称
            List<IndustryDTO.IndustryList> lists = new ArrayList<>();
            //有多少一级行业
            for(SiteExt6 sq :siteExt6s){
                int value = sq.getValue();
                double div = ArithmeticUtil.div(Double.valueOf(value),Double.valueOf(sum1));
                lists.add(new IndustryDTO.IndustryList(sq.getOneIndustry(),div));
            }
            jsonObject.put("oneIndustry",JSON.toJSONString(lists));
            //二级行业
            List<SiteExt6> siteExt6s1 = industrySearch2(businessZoneDTO.getCoordinates());
            //总数
            int sum2 = siteExt6s1.stream().mapToInt(p -> p.getValue()).sum();
            //有多少er级行业
            jsonObject.put("twoIndustryCount",siteExt6s1.size());
            //行业之间的占比  和 行业名称
            List<IndustryDTO.IndustryList> lists1 = new ArrayList<>();
            //有多少er级行业
            for(SiteExt6 sq :siteExt6s1){
                int value = sq.getValue();
                double div = ArithmeticUtil.div(Double.valueOf(value),Double.valueOf(sum2));
                lists1.add(new IndustryDTO.IndustryList(sq.getTwoIndustry(),div));
            }
            jsonObject.put("twoIndustry",JSON.toJSONString(lists1));
        }
        //商圈等级
        jsonObject.put("businessLevel",businessZoneDTO.getBusinessLevel());
        //商圈热力图图片
        jsonObject.put("thermodynamicChart",businessZoneDTO.getThermodynamicChart());
        //广告
        jsonObject.put("advertisement",(String)redisUtil.get("advertisement"));
        System.err.println("商圈报告 = "+jsonObject.toJSONString());
        Integer iq =  mapBusinessMapper.createPresentation(replace1,jsonObject);
        System.err.println("是否插入成功===="+iq);
        redisUtil.set(replace1,jsonObject.toJSONString());
        return null;
    }
*/

    /**
     * 二级行业圆
     *
     * @Author: WuShuang on 2019/11/25  16:00
     * @param: [coordinates, radius]
     * @return: java.util.List<com.pointcoil.dto.geo.SiteExt6>
     * @Description:
     */
    public List<SiteExt6> industrySearch3(List<BusinessZoneDTO.Coordinate> coordinates,String radius){
        List<SiteExt6> pointInPolygon = mongoDbDao.industrySearch3(coordinates.get(0),radius,"tbl_industry");
        System.err.println(pointInPolygon.toString());
        return pointInPolygon;
    }

    /**
     * 二级行业数据  多边形
     *
     * @Author: WuShuang on 2019/11/25  14:48
     * @param: [coordinates]
     * @return: java.util.List<com.pointcoil.dto.geo.SiteExt6>
     * @Description:
     */
    public List<SiteExt6> industrySearch2(List<BusinessZoneDTO.Coordinate> coordinates){
        List<Point> list = new ArrayList<>();
        for(BusinessZoneDTO.Coordinate s : coordinates){
            Point p1 = new Point(s.getLng(),s.getLat());
            list.add(p1);
        }
        GeoJsonPolygon geoJsonPolygon = new GeoJsonPolygon(list);
        List<SiteExt6> pointInPolygon = mongoDbDao.industrySearch2(geoJsonPolygon,"tbl_industry");
        return pointInPolygon;
    }





    @RequestMapping(value = "upload", headers = "content-type=multipart/*", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String userUploadAndSave(@RequestParam("file") MultipartFile[] businessHeadFile) throws IOException {
        JSONObject jsonObjectOut = new JSONObject();
        String path = pointCoilProperties.getImageUploadUrl();
        String iPurl = pointCoilProperties.getUrl();
        //  String realPath = REALPATH;
        FileInputStream fis = null;
        String headNewName = null;
        StringBuffer sb = new StringBuffer();
        StringBuilder sbs = new StringBuilder();
        Map map = new HashMap(EIGHT_NUM);
        for (int i = 0; i < businessHeadFile.length; i++) {
            if (businessHeadFile[i] != null) {
                //获取到文件的名
                String headName = businessHeadFile[i].getOriginalFilename();
                String headLastName = headName.substring(headName.lastIndexOf("."), headName.length());
                headNewName = UUID.randomUUID() + headLastName;
                File file3 = new File(path + File.separator + headNewName);

                try {
                    businessHeadFile[i].transferTo(file3);

                    mapLabelMapper.addLabel(iPurl  + headNewName);



                    if (sb.length() > 0) {
                        sb.append(",");
                    }
                    sb.append(iPurl + file3.toString());
                    sbs.append(iPurl  + headNewName);

                    map.put("src", sbs.toString());
                } catch (Exception e) {
                    jsonObjectOut.put("code", ERROR_CODE);
                    jsonObjectOut.put("msg", "上传失败");
                    return jsonObjectOut.toString();
                }
            } else {
                jsonObjectOut.put("code", ERROR_CODE);
                jsonObjectOut.put("msg", NO_DATA_MSG);
                return jsonObjectOut.toString();
            }
        }
        jsonObjectOut.put("code", SUCCESS_ADMIN_CODE);
        jsonObjectOut.put("msg", SUCCESS_MSG);
        jsonObjectOut.put("data", map);
        return jsonObjectOut.toString();
    }



    @RequestMapping(value = "test3")
    public void test3(@RequestBody BusinessZoneDTO businessZoneDTO) throws Exception {

        GeoJsonPoint geoJsonPoint1 = new GeoJsonPoint(new Point(113.330908,23.155678));
        SiteExt siteExt1 = new SiteExt("A","3户",geoJsonPoint1);
        GeoJsonPoint geoJsonPoint2 = new GeoJsonPoint(new Point(113.33831,23.137335));
        SiteExt siteExt2 = new SiteExt("B","4户",geoJsonPoint2);
        GeoJsonPoint geoJsonPoint3 = new GeoJsonPoint(new Point(113.33832,23.137336));
        SiteExt siteExt3 = new SiteExt("C","4户",geoJsonPoint3);

        mongoDbDao.insert(siteExt1,"SiteExt");
        mongoDbDao.insert(siteExt2,"SiteExt");
        mongoDbDao.insert(siteExt3,"SiteExt");

       /* System.err.println(businessZoneDTO.getCoordinates());
        LocationPO.TestLocation locationPOS = mongoDbDao.insertCollectionByCoordinates(businessZoneDTO.getCoordinates(), UUID.randomUUID().toString().replace("-",""));
*/
    }

    private   Integer num  = 0;


    @RequestMapping(value = "test4")
    public void test4(@RequestBody BusinessZoneDTO businessZoneDTO){

        List<BusinessZoneDTO.Coordinate> coordinates = businessZoneDTO.getCoordinates();
        List<Point> list = new ArrayList<>();
        for(BusinessZoneDTO.Coordinate s : coordinates){
            Point p1 = new Point(s.getLng(),s.getLat());
            list.add(p1);
        }

        //用9个点围成一个区域，首尾两个点p1和p9要相同，才能构成一个区域
        GeoJsonPolygon geoJsonPolygon = new GeoJsonPolygon(list);

        //传入区域和数据库表名
        List<SiteExt4> pointInPolygon = mongoDbDao.findPointInPolygon4(geoJsonPolygon,"tbl_gould");

        pointInPolygon.forEach(
                s -> {
                    System.err.println(s.toString());
                    System.err.println(s.getName());

                }
        );
    }


    @RequestMapping(value = "test6")
    public void test6(@RequestBody BusinessZoneDTO businessZoneDTO){
        String tableName  = "tbl_gould";
        List<SiteExt4> siteExt2s = mongoDbDao.geoNear1(businessZoneDTO.getCoordinates().get(0),businessZoneDTO.getRadius(),tableName);
        System.err.println(siteExt2s.toString());
        System.err.println(siteExt2s.size());
    }



    @RequestMapping(value = "test5")
    public void test5(@RequestBody BusinessZoneDTO businessZoneDTO){
       /* BusinessZoneDTO.Coordinate coordinate = ;

        Point location = new Point(coordinate.getLng(), coordinate.getLat());
        NearQuery query = NearQuery.near(location).num(1000).query(new Query()).maxDistance(new Distance(20, Metrics.KILOMETERS));

        //20是半径
      //NearQuery nearQuery = NearQuery.near(location).maxDistance(new Distance(20, Metrics.KILOMETERS));

        List<AuctionPo> geoResults = mongoDbDao.geoNear(coordinate,query);

        System.err.println(geoResults.toString());*/

        String tableName  = "";
        List<SiteExt2> siteExt2s = mongoDbDao.geoNear(businessZoneDTO.getCoordinates().get(0),businessZoneDTO.getRadius(),tableName);
        System.err.println(siteExt2s.toString());
        System.err.println(siteExt2s.size());
    }






  /*  @RequestMapping(value = "test7")
    public void test7(@RequestBody BusinessZoneDTO businessZoneDTO){
        GouldDTODB.Gould gould = gould(businessZoneDTO.getCoordinates());
        System.err.println("小区名："+gould.getResidentialQuartersName());
        System.err.println("小区数量："+gould.getResidentialQuartersNum());
        System.err.println("写字楼名："+gould.getOfficeBuilding());
        System.err.println("写字楼数量："+gould.getOfficeBuildingNum());
        System.err.println("其他名称："+gould.getOtherName());
        System.err.println("其他数量："+gould.getOtherNum());
    }




    public GouldDTODB.Gould gould(List<BusinessZoneDTO.Coordinate> coordinates){
        List<Point> list = new ArrayList<>();
        for(BusinessZoneDTO.Coordinate s : coordinates){
            Point p1 = new Point(s.getLng(),s.getLat());
            list.add(p1);
        }
        //用9个点围成一个区域，首尾两个点p1和p9要相同，才能构成一个区域
        GeoJsonPolygon geoJsonPolygon = new GeoJsonPolygon(list);
        List<SiteExt4> pointInPolygon = mongoDbDao.findPointInPolygon4(geoJsonPolygon,"tbl_gould");
        List<String> residentialQuartersName = new ArrayList<>();
        List<String> officeBuilding = new ArrayList<>();
        List<String> otherName = new ArrayList<>();

        pointInPolygon.forEach(
                s -> {
                    if(!StringUtils.isEmpty(s.getClassification())){
                        String classification = s.getClassification();
                        if(classification.indexOf("小区") > -1){
                            residentialQuartersName.add(s.getName());
                        }else if (classification.indexOf("写字楼") > -1){
                                    //TODO 名字前四个字 去重
                            officeBuilding.add(s.getName());
                        }else {
                            otherName.add(s.getName());
                        }
                    }
                }
        );

        return new GouldDTODB.Gould(residentialQuartersName.size()>9?residentialQuartersName.subList(0,10):residentialQuartersName
                ,officeBuilding.size()>9?officeBuilding.subList(0,10):officeBuilding
                ,otherName.size()>9?otherName.subList(0,10):otherName
                ,residentialQuartersName.size(),officeBuilding.size(),otherName.size());
    }
*/




   /* @RequestMapping(value = "test8")
    public void test8(@RequestBody BusinessZoneDTO businessZoneDTO){
        GouldDTODB.Gould gould = gould1(businessZoneDTO.getCoordinates(),businessZoneDTO.getRadius());
        System.err.println("小区名："+gould.getResidentialQuartersName());
        System.err.println("小区数量："+gould.getResidentialQuartersNum());
        System.err.println("写字楼名："+gould.getOfficeBuilding());
        System.err.println("写字楼数量："+gould.getOfficeBuildingNum());
        System.err.println("其他名称："+gould.getOtherName());
        System.err.println("其他数量："+gould.getOtherNum());
    }

    public GouldDTODB.Gould gould1(List<BusinessZoneDTO.Coordinate> coordinates,String radius){
        List<SiteExt4> pointInPolygon = mongoDbDao.geoNear1(coordinates.get(0),radius,"tbl_gould");
        List<String> residentialQuartersName = new ArrayList<>();
        List<String> officeBuilding = new ArrayList<>();
        List<String> otherName = new ArrayList<>();
        pointInPolygon.forEach(
                s -> {
                    if(!StringUtils.isEmpty(s.getClassification())){
                        String classification = s.getClassification();
                        if(classification.indexOf("小区") > -1){
                            residentialQuartersName.add(s.getName());
                        }else if (classification.indexOf("写字楼") > -1){
                            officeBuilding.add(s.getName());
                        }else {
                            otherName.add(s.getName());
                        }
                    }
                }
        );
        return new GouldDTODB.Gould(residentialQuartersName.size()>9?residentialQuartersName.subList(0,10):residentialQuartersName
                ,officeBuilding.size()>9?officeBuilding.subList(0,10):officeBuilding
                ,otherName.size()>9?otherName.subList(0,10):otherName
                ,residentialQuartersName.size(),officeBuilding.size(),otherName.size());
    }*/


    @PostMapping("test10")
    public void test10(@RequestBody BusinessZoneDTO businessZoneDTO){
        List<SiteExt6> siteExt6s = industrySearch(businessZoneDTO.getCoordinates());
        System.err.println(siteExt6s);
        int sum1 = siteExt6s.stream().mapToInt(p -> p.getValue()).sum();
        List<IndustryDTO.IndustryList> lists = new ArrayList<>();
        //有多少一级行业
        for(SiteExt6 s :siteExt6s){
            int value = s.getValue();
            double div = ArithmeticUtil.div(Double.valueOf(value),Double.valueOf(sum1));
            lists.add(new IndustryDTO.IndustryList(s.getOneIndustry(),div));
        }
    }

    public List<SiteExt6> industrySearch(List<BusinessZoneDTO.Coordinate> coordinates){
        List<Point> list = new ArrayList<>();
        for(BusinessZoneDTO.Coordinate s : coordinates){
            Point p1 = new Point(s.getLng(),s.getLat());
            list.add(p1);
        }
        GeoJsonPolygon geoJsonPolygon = new GeoJsonPolygon(list);
        List<SiteExt6> pointInPolygon = mongoDbDao.findPointInIndustry(geoJsonPolygon,"tbl_industry");
        return pointInPolygon;
    }

    public List<SiteExt6> industrySearch1(List<BusinessZoneDTO.Coordinate> coordinates,String radius){
        List<SiteExt6> pointInPolygon = mongoDbDao.industrySearch1(coordinates.get(0),radius,"tbl_industry");
        return pointInPolygon;
    }





    @RequestMapping(value = "test9")
    public void test9(@RequestBody BusinessZoneDTO businessZoneDTO){
        Integer garden = getGarden(businessZoneDTO.getCoordinates().get(0), businessZoneDTO.getRadius());
        System.err.println(garden);
    }


    /**
     * 圆的时候
     * @return
     */
    public Integer getGarden(BusinessZoneDTO.Coordinate businessZoneDTO, String radius){

        List<SiteExt2> siteExt = mongoDbDao.geoNear(businessZoneDTO,radius,"tbl_housingworld");
        int sum2 = siteExt.stream()
                .mapToInt(p -> Integer.parseInt(p.getNum()))
                .sum();

        List<SiteExt2> siteExt1 = mongoDbDao.geoNear(businessZoneDTO,radius,"tbl_chainfamily_turnover");
        int sum = siteExt1.stream()
                .mapToInt(p -> Integer.parseInt(p.getNum()))
                .sum();
        List<SiteExt2> siteExt2 = mongoDbDao.geoNear(businessZoneDTO,radius,"tbl_chainfamily_tworoom");
        int sum1 = siteExt2.stream()
                .mapToInt(p -> Integer.parseInt(p.getNum()))
                .sum();
        sum = sum+sum1+sum2;

        return sum*3;
    }


    /**
     * 多边形的时候 范围
     * @param coordinates
     * @return
     */
    public Integer getPopulation(List<BusinessZoneDTO.Coordinate> coordinates){
        List<Point> list = new ArrayList<>();
        for(BusinessZoneDTO.Coordinate s : coordinates){
            Point p1 = new Point(s.getLng(),s.getLat());
            list.add(p1);
        }

        //用9个点围成一个区域，首尾两个点p1和p9要相同，才能构成一个区域
        GeoJsonPolygon geoJsonPolygon = new GeoJsonPolygon(list);

        //传入区域和数据库表名
        List<SiteExt> pointInPolygon = mongoDbDao.findPointInPolygon(geoJsonPolygon,"tbl_housingworld");
        int sum = pointInPolygon.stream()
                .mapToInt(p -> Integer.parseInt(p.getNum()))
                .sum();


        List<SiteExt> tbl_chainfamily_tworoom = mongoDbDao.findPointInPolygon(geoJsonPolygon, "tbl_chainfamily_tworoom");

        int sum1 = tbl_chainfamily_tworoom.stream()
                .mapToInt(p -> Integer.parseInt(p.getNum()))
                .sum();

        List<SiteExt> tbl_chainfamily_turnover = mongoDbDao.findPointInPolygon(geoJsonPolygon, "tbl_chainfamily_turnover");

        int sum2 = tbl_chainfamily_turnover.stream()
                .mapToInt(p -> Integer.parseInt(p.getNum()))
                .sum();
        System.err.println(sum+sum1+sum2);


        return sum*3;
    }

    @PostMapping("test11")
    public String test11(){
        redisUtil.set("advertisement","https://statics6.casiostore.com.cn/vendor/casioclub/she-4058/images/pc/PC_01.jpg");
        return "ok";
    }

    @Autowired
    private MapBusinessMapper mapBusinessMapper;

    @PostMapping("test12")
    public List<SiteExt6> industrySearch90(@RequestBody BusinessZoneDTO businessZoneDTO){
       List<BusinessZoneDTO.Coordinate> coordinates = businessZoneDTO.getCoordinates();
        List<Point> list = new ArrayList<>();
        for(BusinessZoneDTO.Coordinate s : coordinates){
            Point p1 = new Point(s.getLng(),s.getLat());
            list.add(p1);
        }
        GeoJsonPolygon geoJsonPolygon = new GeoJsonPolygon(list);
        List<SiteExt6> pointInPolygon = mongoDbDao.findPointInIndustry(geoJsonPolygon,"tbl_industry");
        System.err.println("筛选数量="+pointInPolygon.size());
        List<String> list1 = new ArrayList<>();
        for(SiteExt6 s:pointInPolygon){
            String oneIndustry = s.getOneIndustry();
            String i = mapBusinessMapper.screenIndustry(oneIndustry);
            if(StringUtils.isEmpty(i)){
                list1.add(oneIndustry);
            }else {
                list1.add(i);
            }
        }
        System.err.println(list1.toString());
        List<SiteExt6> list2 = new ArrayList<>();

        Map<String,Integer> map = new HashMap<>();
        for(String str:list1){
            Integer i = 1; //定义一个计数器，用来记录重复数据的个数
            if(map.get(str) != null){
                i=map.get(str)+1;
            }
            map.put(str,i);
        }
        Set<String> strings = map.keySet();
        for(String s :strings){
            SiteExt6 siteExt6 = new SiteExt6();
            siteExt6.setValue(map.get(s));
            siteExt6.setOneIndustry(s);
            list2.add(siteExt6);
        }
        return list2;
    }


    @PostMapping("test13")
    public String test13(@RequestBody BusinessZoneDTO businessZoneDTO){
        List<BusinessZoneDTO.Coordinate> coordinates = businessZoneDTO.getCoordinates();
        List<Point> list = new ArrayList<>();
        for(BusinessZoneDTO.Coordinate s : coordinates){
            Point p1 = new Point(s.getLng(),s.getLat());
            list.add(p1);
        }
        GeoJsonPolygon geoJsonPolygon = new GeoJsonPolygon(list);
        List<SiteExt6> pointInPolygon = mongoDbDao.industrySearch2(geoJsonPolygon,"tbl_industry");
        String i  = mapBusinessMapper.selectBrandByIndustry(businessZoneDTO.getBrandId());
        Map<String,Integer> map = new HashMap<>();
        for(SiteExt6 s:pointInPolygon){
            String q =  mapBusinessMapper.searchTwoIndustry(s.getTwoIndustry(),i);
            if(!StringUtils.isEmpty(q)){
                Integer value = s.getValue();
                if(map.get(q) != null){
                    value=map.get(q)+value;
                }
                map.put(q,value);
            }
        }
        List<SiteExt6> list2 = new ArrayList<>();

    /*    Map<String,Integer> map = new HashMap<>();
        for(String str:list1){
            Integer iq = 1; //定义一个计数器，用来记录重复数据的个数
            if(map.get(str) != null){
                iq=map.get(str)+1;
            }
            map.put(str,iq);
        }*/
        Set<String> strings = map.keySet();
        for(String s :strings){
            SiteExt6 siteExt6 = new SiteExt6();
            siteExt6.setValue(map.get(s));
            siteExt6.setOneIndustry(s);
            list2.add(siteExt6);
        }
        return ResponseUtil.successToClient(list2);
    }


    @PostMapping("test14")
    public String test14(){
        try {
            new ExcelEventParser().processFirstSheet("C:/Users/78912/Desktop/数据样例 - 整理/百度坐标点评结果.xlsx");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
  @PostMapping("test15")
    public String test15(){
      mongoDbDao.delete("05e9f735cab94d7483ed18151df3580d","tbl_industry");
        return "ok";
    }  @PostMapping("test16")
    public String test16(){
        Map<String,String> map = mapBusinessMapper.selectAppId("21f5f2762b7411eaa8e20242ac110002");
        String appId = "";
        String secret = "";
        if(map ==null){
            appId = "wx6d93cbc58407ef48";
            secret = "96333b1335e9f4e829663bd90b1694d0";
        }else {
            appId = map.get("appid");
            secret =  map.get("secret");
        }

        System.err.println(appId);
        System.err.println(secret);
        return "ok";
    }



    public static void main(String[] args) {
        List<Map<Object, Object>> objects = new ArrayList<>();
        Map<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("1","1");
        objects.add(objectObjectHashMap);

        List<Map<Object, Object>> objects1 = new ArrayList<>();

        Map<Object, Object> objectObjectHashMap1 = new HashMap<>();
        objectObjectHashMap1.put("2","2");
        objects1.add(objectObjectHashMap1);

        objects.addAll(objects1);
        System.err.println(objects);
    }

}
