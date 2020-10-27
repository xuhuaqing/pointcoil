package com.pointcoil.service.map.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pointcoil.conf.PointCoilProperties;
import com.pointcoil.dto.geo.SiteExt;
import com.pointcoil.dto.geo.SiteExt2;
import com.pointcoil.dto.geo.SiteExt4;
import com.pointcoil.dto.geo.SiteExt6;
import com.pointcoil.dto.map.*;
import com.pointcoil.mapper.MapBusinessMapper;
import com.pointcoil.mapper.ThermodynamicMapper;
import com.pointcoil.mapper.Thermodynamic_01Mapper;
import com.pointcoil.mongo.MongoDbDao;
import com.pointcoil.service.map.ThermodynamicService;
import com.pointcoil.util.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.jms.*;
import javax.jms.Queue;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Future;

import static com.pointcoil.constant.MsgConstant.MSG_000000;

/**
 * Created by WuShuang on 2019/11/19.
 */
@Component
public class AsyncTask {




    @Autowired
    private PointCoilProperties pointCoilProperties;
    @Autowired
    private MapBusinessMapper mapBusinessMapper;

    private static Random random = new Random();


    @Autowired
    private MongoDbDao mongoDbDao;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 创建商圈报告
     *
     * replace1 商圈id
     *
     * businessZoneDTO 商圈信息
     *
     * @Author: WuShuang on 2019/11/19  16:12
     * @param: [replace1, businessZoneDTO]
     * @return: java.util.concurrent.Future<java.lang.String>
     * @Description:
     */
    @Async
   // @Transactional(propagation= Propagation.REQUIRES_NEW)
    public Future<String> createPresentation(String replace1, BusinessZoneDTO businessZoneDTO) {
        System.err.println("开始执行异步");
        JSONObject jsonObject = new JSONObject();
        //品牌名称
        BrandDTO.SelectBrandDTO  selectBrandDTO = mapBusinessMapper.selectBrand(new BrandDTO.BrandId(businessZoneDTO.getBrandId(),""));
        jsonObject.put("businessZoneName",selectBrandDTO.getBrandTitle()+businessZoneDTO.getBusinessName()+"商圈报告");

        //今日关注数量
        jsonObject.put("followNum",random.nextInt(5)+1);


     /* *//*  net.sf.json.JSONObject city = MapUtils.getCity(String.valueOf(businessZoneDTO.getCoordinates().get(0).getLng()), String.valueOf(businessZoneDTO.getCoordinates().get(0).getLat()));
        String province =(String)city.get("province");
        String city1 = (String)city.get("city");*//*
        try {
            province = new String(province.getBytes(), "utf-8");
            city1 = new String(city1.getBytes(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        jsonObject.put("province",businessZoneDTO.getProvince());
        //二维码 TODO 拼接小程序地址
        //String s = QRCode.qrCode("qr.api.cli.im/qr?data=http%253A%252F%252Fwww.hzdxq.cn/pointcoil%252Fpointcoil%252Fcode%252Fpoint%252F&level=H&transparent=false&bgcolor=%23FFFFFF&forecolor=%23000000&blockpixel=12&marginblock=1&logourl=&size=280&kid=cliim&key=1bfebdaa522b0397b2d126b7e82f02e5&id="+replace1);
        String codeId = UUID.randomUUID().toString().replace("-", "");
        mapBusinessMapper.insertQRcodeParam(codeId,replace1,businessZoneDTO.getSonId(),businessZoneDTO.getUserId());
        String s1 = test111(codeId,businessZoneDTO.getUserId());
        jsonObject.put("QRCode",s1);
        //商圈截图
        jsonObject.put("screenshotImg",businessZoneDTO.getScreenshotImg());
        //城市
        jsonObject.put("city",businessZoneDTO.getCity());
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
            List<SiteExt6> siteExt6s1 = industrySearch3(businessZoneDTO.getCoordinates(), businessZoneDTO.getRadius(),businessZoneDTO.getBrandId());
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
            List<SiteExt6> siteExt6s1 = industrySearch2(businessZoneDTO.getCoordinates(),businessZoneDTO.getBrandId());
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


    /**
     * 多边形的时候 范围 人口
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

      /*  //传入区域和数据库表名
        List<SiteExt> pointInPolygon = mongoDbDao.findPointInPolygon(geoJsonPolygon,"tbl_housingworld");
        int sum = pointInPolygon.stream()
                .mapToInt(p -> Integer.parseInt(p.getNum()))
                .sum();*/


/*        List<SiteExt> tbl_chainfamily_tworoom = mongoDbDao.findPointInPolygon(geoJsonPolygon, "tbl_chainfamily_tworoom");

        int sum1 = tbl_chainfamily_tworoom.stream()
                .mapToInt(p -> Integer.parseInt(p.getNum()))
                .sum();*/

        List<SiteExt> tbl_chainfamily_turnover = mongoDbDao.findPointInPolygon(geoJsonPolygon, "tbl_chainfamily_turnover");

        int sum2 = tbl_chainfamily_turnover.stream()
                .mapToInt(p -> Integer.parseInt(p.getNum()))
                .sum();


        return sum2*3;
    }

    /**
     * 圆的时候 人口
     * @return
     */
    public Integer getGarden(BusinessZoneDTO.Coordinate businessZoneDTO, String radius){

       /* List<SiteExt2> siteExt = mongoDbDao.geoNear(businessZoneDTO,radius,"tbl_housingworld");
        int sum2 = siteExt.stream()
                .mapToInt(p -> Integer.parseInt(p.getNum()))
                .sum();*/

        List<SiteExt2> siteExt1 = mongoDbDao.geoNear(businessZoneDTO,radius,"tbl_chainfamily_turnover");
        int sum = siteExt1.stream()
                .mapToInt(p -> Integer.parseInt(p.getNum()))
                .sum();
      /*  List<SiteExt2> siteExt2 = mongoDbDao.geoNear(businessZoneDTO,radius,"tbl_chainfamily_tworoom");
        int sum1 = siteExt2.stream()
                .mapToInt(p -> Integer.parseInt(p.getNum()))
                .sum();
        System.err.println(sum+sum1+sum2);*/


        return sum*3;
    }



    /**
     * 多边形的时候  小区写字楼
     *
     * @Author: WuShuang on 2019/11/22  16:24
     * @param: [coordinates]
     * @return: com.pointcoil.dto.map.GouldDTODB.Gould
     * @Description:
     */
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
        List<BusinessZoneDTO.OtherDTO> otherName = new ArrayList<>();
        pointInPolygon.forEach(
                s -> {
                    if(!StringUtils.isEmpty(s.getClassification())){
                        String classification = s.getClassification();
                        if(classification.indexOf("小区") > -1){
                            residentialQuartersName.add(s.getName());
                        }else if (classification.indexOf("写字楼") > -1 || classification.indexOf("产业园区")> -1){
                            officeBuilding.add(s.getName());
                        }else {
                          /*  BusinessZoneDTO.OtherDTO otherDTO = new BusinessZoneDTO.OtherDTO();
                            otherDTO.setOtherName(s.getName());
                            String[] split = classification.split(";");
                            otherDTO.setOtherType(split[2]);
                            otherName.add(otherDTO);*/
                            if(classification.indexOf("医院") > -1 ){
                                String name = s.getName();
                                if(name.indexOf("楼") == -1&&name.indexOf("住院") == -1&&name.indexOf("诊") == -1&&name.indexOf("中心") == -1&&name.indexOf("科") == -1&&name.indexOf("幢") == -1&&name.indexOf("室") == -1){
                                    BusinessZoneDTO.OtherDTO otherDTO = new BusinessZoneDTO.OtherDTO();
                                    otherDTO.setOtherName(s.getName());
                                    String[] split = classification.split(";");
                                    otherDTO.setOtherType(split[2]);
                                    otherName.add(otherDTO);
                                }
                            }else if(classification.indexOf("中学") > -1 || classification.indexOf("小学") > -1 ||classification.indexOf("职业技术学校") > -1 ||classification.indexOf("高等院校") > -1){
                                String name = s.getName();
                                if(name.indexOf("楼") == -1&&name.indexOf("馆") == -1&&name.indexOf("与") == -1&&name.indexOf("厅") == -1&&name.indexOf("部") == -1&&name.indexOf("教") == -1&&name.indexOf("学生") == -1&&name.indexOf("系") == -1&&name.indexOf("室") == -1&&name.indexOf("0") == -1
                                        && name.indexOf("1") == -1&& name.indexOf("2") == -1&& name.indexOf("3") == -1&& name.indexOf("4") == -1&& name.indexOf("5") == -1&& name.indexOf("6") == -1&& name.indexOf("7") == -1&& name.indexOf("8") == -1&& name.indexOf("9") == -1){
                                    BusinessZoneDTO.OtherDTO otherDTO = new BusinessZoneDTO.OtherDTO();
                                    otherDTO.setOtherName(s.getName());
                                    String[] split = classification.split(";");
                                    otherDTO.setOtherType(split[2]);
                                    otherName.add(otherDTO);
                                }
                            }else if(classification.indexOf("火车站") > -1 || classification.indexOf("长途汽车站") > -1||classification.indexOf("地铁站") > -1){
                                String name = s.getName();
                                if(name.indexOf("站口") == -1&&name.indexOf("到达") == -1&&name.indexOf("出发") == -1){
                                    BusinessZoneDTO.OtherDTO otherDTO = new BusinessZoneDTO.OtherDTO();
                                    otherDTO.setOtherName(s.getName());
                                    String[] split = classification.split(";");
                                    otherDTO.setOtherType(split[2]);
                                    otherName.add(otherDTO);
                                }
                            }else if(classification.indexOf("商场") > -1){
                                String name = s.getName();
                                if(name.indexOf("南区") == -1&&name.indexOf("北区") == -1&&name.indexOf("东区") == -1&&name.indexOf("西区") == -1&&name.indexOf("入口") == -1&&name.indexOf("团购") == -1&&name.indexOf("A馆") == -1&&name.indexOf("B馆") == -1&&name.indexOf("C馆") == -1&&name.indexOf("D馆") == -1
                                        && name.indexOf("E馆") == -1&& name.indexOf("F馆") == -1&& name.indexOf("A座") == -1&& name.indexOf("B座") == -1&& name.indexOf("C座") == -1&& name.indexOf("D座") == -1){
                                    BusinessZoneDTO.OtherDTO otherDTO = new BusinessZoneDTO.OtherDTO();
                                    otherDTO.setOtherName(s.getName());
                                    String[] split = classification.split(";");
                                    otherDTO.setOtherType(split[2]);
                                    otherName.add(otherDTO);
                                }
                            }
                        }
                    }
                }
        );


        return new GouldDTODB.Gould(residentialQuartersName.size()>9?residentialQuartersName.subList(0,9):residentialQuartersName
                ,officeBuilding.size()>9?officeBuilding.subList(0,9):officeBuilding
                ,otherName.size()>9?otherName.subList(0,9):otherName
                ,residentialQuartersName.size(),officeBuilding.size(),otherName.size());
    }




    /**
     *圆的时候 小区 写字楼
     *
     * @Author: WuShuang on 2019/11/22  16:23
     * @param: [coordinates, radius]
     * @return: com.pointcoil.dto.map.GouldDTODB.Gould
     * @Description:
     */
    public GouldDTODB.Gould gould1(List<BusinessZoneDTO.Coordinate> coordinates,String radius){
        List<SiteExt4> pointInPolygon = mongoDbDao.geoNear1(coordinates.get(0),radius,"tbl_gould");
        List<String> residentialQuartersName = new ArrayList<>();
        List<String> officeBuilding = new ArrayList<>();
        List<BusinessZoneDTO.OtherDTO> otherName = new ArrayList<>();
        pointInPolygon.forEach(
                s -> {
                    if(!StringUtils.isEmpty(s.getClassification())){
                        String classification = s.getClassification();
                        if(classification.indexOf("小区") > -1){
                            residentialQuartersName.add(s.getName());
                        }else if (classification.indexOf("写字楼") > -1 || classification.indexOf("产业园区")> -1){
                            officeBuilding.add(s.getName());
                        }else {
                        //    || classification.indexOf("学校") > -1||classification.indexOf("火车站") > -1||classification.indexOf("长途汽车站") > -1||classification.indexOf("地铁站") > -1||classification.indexOf("商场") > -1
                            if(classification.indexOf("医院") > -1 ){
                                String name = s.getName();
                                if(name.indexOf("楼") == -1&&name.indexOf("住院") == -1&&name.indexOf("诊") == -1&&name.indexOf("中心") == -1&&name.indexOf("科") == -1&&name.indexOf("幢") == -1&&name.indexOf("室") == -1){
                                    BusinessZoneDTO.OtherDTO otherDTO = new BusinessZoneDTO.OtherDTO();
                                    otherDTO.setOtherName(s.getName());
                                    String[] split = classification.split(";");
                                    otherDTO.setOtherType(split[2]);
                                    otherName.add(otherDTO);
                                }
                            }else if(classification.indexOf("学校") > -1){
                                String name = s.getName();
                                if(name.indexOf("楼") == -1&&name.indexOf("馆") == -1&&name.indexOf("与") == -1&&name.indexOf("厅") == -1&&name.indexOf("部") == -1&&name.indexOf("教") == -1&&name.indexOf("学生") == -1&&name.indexOf("系") == -1&&name.indexOf("室") == -1&&name.indexOf("0") == -1
                                && name.indexOf("1") == -1&& name.indexOf("2") == -1&& name.indexOf("3") == -1&& name.indexOf("4") == -1&& name.indexOf("5") == -1&& name.indexOf("6") == -1&& name.indexOf("7") == -1&& name.indexOf("8") == -1&& name.indexOf("9") == -1){
                                    BusinessZoneDTO.OtherDTO otherDTO = new BusinessZoneDTO.OtherDTO();
                                    otherDTO.setOtherName(s.getName());
                                    String[] split = classification.split(";");
                                    otherDTO.setOtherType(split[2]);
                                    otherName.add(otherDTO);
                                }
                            }else if(classification.indexOf("火车站") > -1 || classification.indexOf("长途汽车站") > -1||classification.indexOf("地铁站") > -1){
                                String name = s.getName();
                                if(name.indexOf("站口") == -1&&name.indexOf("到达") == -1&&name.indexOf("出发") == -1){
                                    BusinessZoneDTO.OtherDTO otherDTO = new BusinessZoneDTO.OtherDTO();
                                    otherDTO.setOtherName(s.getName());
                                    String[] split = classification.split(";");
                                    otherDTO.setOtherType(split[2]);
                                    otherName.add(otherDTO);
                                }
                            }else if(classification.indexOf("商场") > -1){
                                String name = s.getName();
                                if(name.indexOf("南区") == -1&&name.indexOf("北区") == -1&&name.indexOf("东区") == -1&&name.indexOf("西区") == -1&&name.indexOf("入口") == -1&&name.indexOf("团购") == -1&&name.indexOf("A馆") == -1&&name.indexOf("B馆") == -1&&name.indexOf("C馆") == -1&&name.indexOf("D馆") == -1
                                        && name.indexOf("E馆") == -1&& name.indexOf("F馆") == -1&& name.indexOf("A座") == -1&& name.indexOf("B座") == -1&& name.indexOf("C座") == -1&& name.indexOf("D座") == -1){
                                    BusinessZoneDTO.OtherDTO otherDTO = new BusinessZoneDTO.OtherDTO();
                                    otherDTO.setOtherName(s.getName());
                                    String[] split = classification.split(";");
                                    otherDTO.setOtherType(split[2]);
                                    otherName.add(otherDTO);
                                }
                            }
                        }
                    }
                }
        );
        return new GouldDTODB.Gould(residentialQuartersName.size()>9?residentialQuartersName.subList(0,10):residentialQuartersName
                ,officeBuilding.size()>9?officeBuilding.subList(0,10):officeBuilding
                ,otherName.size()>9?otherName.subList(0,10):otherName
                ,residentialQuartersName.size(),officeBuilding.size(),otherName.size());
    }



    /**
     * 一级行业数据  多边形
     *
     * @Author: WuShuang on 2019/11/25  14:48
     * @param: [coordinates]
     * @return: java.util.List<com.pointcoil.dto.geo.SiteExt6>
     * @Description:
     */
    public List<SiteExt6> industrySearch(List<BusinessZoneDTO.Coordinate> coordinates){
        List<Point> list = new ArrayList<>();
        for(BusinessZoneDTO.Coordinate s : coordinates){
            Point p1 = new Point(s.getLng(),s.getLat());
            list.add(p1);
        }
        GeoJsonPolygon geoJsonPolygon = new GeoJsonPolygon(list);
        List<SiteExt6> pointInPolygon = mongoDbDao.findPointInIndustry(geoJsonPolygon,"tbl_industry");
        Map<String,Integer> map = new HashMap<>();
        for(SiteExt6 s:pointInPolygon){
            String oneIndustry = s.getOneIndustry();
            String q =  mapBusinessMapper.screenIndustry(oneIndustry);
            if(!StringUtils.isEmpty(q)){
                Integer value = s.getValue();
                if(map.get(q) != null){
                    value=map.get(q)+value;
                }
                map.put(q,value);
            }/*else {
                Integer value = s.getValue();
                if(map.get(oneIndustry) != null){
                    value=map.get(oneIndustry)+value;
                }
                map.put(oneIndustry,value);
            }*/
        }
      /*  List<String> list1 = new ArrayList<>();

        for(SiteExt6 s:pointInPolygon){
            String i = mapBusinessMapper.screenIndustry(oneIndustry);
            if(StringUtils.isEmpty(i)){
                list1.add(oneIndustry);
            }else {
                list1.add(i);
            }
        }*/
        List<SiteExt6> list2 = new ArrayList<>();

       /* Map<String,Integer> map = new HashMap<>();
        for(String str:list1){
            Integer i = 1; //定义一个计数器，用来记录重复数据的个数
            if(map.get(str) != null){
                i=map.get(str)+1;
            }
            map.put(str,i);
        }*/
        Set<String> strings = map.keySet();
        for(String s :strings){
            SiteExt6 siteExt6 = new SiteExt6();
            siteExt6.setValue(map.get(s));
            siteExt6.setOneIndustry(s);
            list2.add(siteExt6);
        }
        return list2;
    }
    /**
     * 二级行业数据  多边形
     *
     * @Author: WuShuang on 2019/11/25  14:48
     * @param: [coordinates]
     * @return: java.util.List<com.pointcoil.dto.geo.SiteExt6>
     * @Description:
     */
    public List<SiteExt6> industrySearch2(List<BusinessZoneDTO.Coordinate> coordinates,String o){
        List<Point> list = new ArrayList<>();
        for(BusinessZoneDTO.Coordinate s : coordinates){
            Point p1 = new Point(s.getLng(),s.getLat());
            list.add(p1);
        }
        GeoJsonPolygon geoJsonPolygon = new GeoJsonPolygon(list);
        List<SiteExt6> pointInPolygon = mongoDbDao.industrySearch2(geoJsonPolygon,"tbl_industry");

        String i  = mapBusinessMapper.selectBrandByIndustry(o);
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

      /*  Map<String,Integer> map = new HashMap<>();
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
            siteExt6.setTwoIndustry(s);
            list2.add(siteExt6);
        }
        return list2;
    }



    /**
     * 一级行业数据 圆
     *
     * @Author: WuShuang on 2019/11/25  14:57
     * @param: [coordinates, radius]
     * @return: java.util.List<com.pointcoil.dto.geo.SiteExt6>
     * @Description:
     */
    public List<SiteExt6> industrySearch1(List<BusinessZoneDTO.Coordinate> coordinates,String radius){
        List<SiteExt6> pointInPolygon = mongoDbDao.industrySearch1(coordinates.get(0),radius,"tbl_industry");

        Map<String,Integer> map = new HashMap<>();
        for(SiteExt6 s:pointInPolygon){
            String oneIndustry = s.getOneIndustry();
            String q =  mapBusinessMapper.screenIndustry(oneIndustry);
            if(!StringUtils.isEmpty(q)){
                Integer value = s.getValue();
                if(map.get(q) != null){
                    value=map.get(q)+value;
                }
                map.put(q,value);
            }/*else {
                Integer value = s.getValue();
                if(map.get(oneIndustry) != null){
                    value=map.get(oneIndustry)+value;
                }
                map.put(oneIndustry,value);
            }*/
        }



 /*       List<String> list1 = new ArrayList<>();
        for(SiteExt6 s:pointInPolygon){
            String oneIndustry = s.getOneIndustry();
            String i = mapBusinessMapper.screenIndustry(oneIndustry);
            if(!StringUtils.isEmpty(i)){
                list1.add(i);
            }
        }
*/






        List<SiteExt6> list2 = new ArrayList<>();

    /*    Map<String,Integer> map = new HashMap<>();
        for(String str:list1){
            Integer i = 1; //定义一个计数器，用来记录重复数据的个数
            if(map.get(str) != null){
                i=map.get(str)+1;
            }
            map.put(str,i);
        }*/
        Set<String> strings = map.keySet();
        for(String s :strings){
            SiteExt6 siteExt6 = new SiteExt6();
            siteExt6.setValue(map.get(s));
            siteExt6.setOneIndustry(s);
            list2.add(siteExt6);
        }
        return list2;
    }

    /**
     * 二级行业数据  圆
     *
     * @Author: WuShuang on 2019/11/25  16:00
     * @param: [coordinates, radius]
     * @return: java.util.List<com.pointcoil.dto.geo.SiteExt6>
     * @Description:
     */
    public List<SiteExt6> industrySearch3(List<BusinessZoneDTO.Coordinate> coordinates,String radius,String o){
        //取出圆里面有多少行业
        List<SiteExt6> pointInPolygon = mongoDbDao.industrySearch3(coordinates.get(0),radius,"tbl_industry");

        //取出点线圈一级行业名称
        String i  = mapBusinessMapper.selectBrandByIndustry(o);
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
     /*   Map<String,Integer> map = new HashMap<>();
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
            siteExt6.setTwoIndustry(s);
            list2.add(siteExt6);
        }
        return list2;
    }

    public  String GetUrlS(String appId, String secret) throws ClientProtocolException, IOException  {
        HttpGet httpGet = new HttpGet(
                "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                        + appId + "&secret=" + secret );
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse res = httpClient.execute(httpGet);
        HttpEntity entity = res.getEntity();
        String result = EntityUtils.toString(entity, "UTF-8");
        net.sf.json.JSONObject jsons = net.sf.json.JSONObject.fromObject(result);
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

    public String test111(String replace1,String userId){
        Map<String,String> map = mapBusinessMapper.selectAppId(userId);
        String path = pointCoilProperties.getImageUploadUrl();
        String iPurl = pointCoilProperties.getUrl();

        String appId = "";
        String secret = "";
        if(map == null){
            appId = "wx6d93cbc58407ef48";
            secret = "96333b1335e9f4e829663bd90b1694d0";
        }else {
            appId = map.get("appid");
            secret =  map.get("secret");
        }
        try
        {
            String s = GetUrlS(appId,secret);
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
            paramJson.put("scene", replace1);
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
            OutputStream os = new FileOutputStream(new File(path+"/"+replace+".png"));
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



    @Autowired
    private JmsTemplate jmsTemplate;
    //注入JmsTemplate
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    @Async
    public void sendMes(String path,String cityName){
        //每批导入excel总行数
        int FileMaxNum = 2000;
        List<String> splitExcelPath = SplitExcelUtil.splitExcels(path, FileMaxNum);
        //上传完成 删除原文件
        //前期工作：清空分割存储文件夹
        File[] splitFiles=new File(path).listFiles();
        if(null!=splitFiles){
            for(File f2: splitFiles){
                f2.delete();
            }
            System.err.println("源文件删除");
        }
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
            for (String s: splitExcelPath
            ) {
                String queue =
                        s+","+cityName;
                TextMessage textMessage =
                        session.createTextMessage(queue);
                producer.send(textMessage);
                //jmsMessagingTemplate.convertAndSend(this.queue,queue);

                session.commit();
            }
            producer.close();
            session.close();
            connection.close();
            splitExcelPath.clear();
        }catch (JMSException e){
            e.printStackTrace();
            try{
                session.rollback();
            }catch(JMSException e1){
                e1.printStackTrace();
            }
        }

    }

/*
    @Autowired
    private ThermodynamicService thermodynamicService;

    @Resource
    private ThermodynamicMapper thermodynamicMapper;

    @Resource
    private Thermodynamic_01Mapper thermodynamicMapper_01;
      @Async
      public Future<String> deleteThermodynamic(HeatDTO.HeatMapDeleteDTO heatMapDeleteDTO){
          HeatDTO heatDTO = new HeatDTO();
          heatDTO.setCity(heatMapDeleteDTO.getCity());
          heatDTO.setHeatMapId(heatMapDeleteDTO.getHeatMapId());
          heatDTO.setHeatType(heatMapDeleteDTO.getType()+"");
          heatDTO.setUserId(heatMapDeleteDTO.getUserId());
          String q = thermodynamicService.selectThermodynamic(heatDTO);
          JSONObject parse = (JSONObject)JSONObject.parse(q);
          String code = parse.getString("code");
          if(MSG_000000.equals(code)){
              JSONArray data = parse.getJSONArray("data");
              List<HeatDTO.HeatMapDataDTO> heatMapDataDTOS = JSONObject.parseArray(data.toJSONString(),HeatDTO.HeatMapDataDTO.class);
              List<HeatDTO.HeatMapDataDTO> sqq = new ArrayList<>();
              heatMapDataDTOS.stream().forEach(
                      s ->{
                          boolean inCircle = isInCircle(heatMapDeleteDTO.getRadius()*1000, heatMapDeleteDTO.getLat(), heatMapDeleteDTO.getLng(), s.getLat(), s.getLng());
                          if (inCircle){
                              //点在圆内
                              sqq.add(s);
                          }
                      }
              );
              sqq.stream().forEach(
                      sf ->{
                          System.err.println(sf.toString());
                      }
              );

              if(heatMapDeleteDTO.getType() == 4){
                  thermodynamicMapper.deleteAreYou(sqq);
                  thermodynamicMapper_01.deleteNewAreYou(sqq);
              }else {
                  thermodynamicMapper.deleteThermodynamic(sqq);
                  thermodynamicMapper_01.deleteNewAreYou(sqq);
              }
          }
        return null;
      }
    *//**
     * 地球半径
     *//*
    private static double EARTH_RADIUS = 6378138.0;

    private static double rad(double d)
    {
        return d * Math.PI / 180.0;
    }

    public static boolean isInCircle(double radius,double lat1, double lng1, double lat2, double lng2)
    {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        if(s > radius) {//不在圆上
            return false;
        }else {
            return true;
        }
    }*/
}
