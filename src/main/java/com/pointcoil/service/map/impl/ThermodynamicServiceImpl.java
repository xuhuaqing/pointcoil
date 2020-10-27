package com.pointcoil.service.map.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pointcoil.dto.geo.SiteExt4;
import com.pointcoil.dto.map.BusinessZoneDTO;
import com.pointcoil.dto.map.HeatDTO;
import com.pointcoil.mapper.*;
import com.pointcoil.mongo.MongoDbDao;
import com.pointcoil.service.map.ThermodynamicService;
import com.pointcoil.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.pointcoil.constant.MsgConstant.MSG_000000;
import static com.pointcoil.constant.MsgConstant.MSG_001005;
import static com.pointcoil.constant.MsgConstant.MSG_001013;

/**
 * Created by WuShuang on 2019/11/29.
 */
@Service
@Slf4j
@SuppressWarnings("ALL")
public class ThermodynamicServiceImpl implements ThermodynamicService {

    @Autowired
    private ThermodynamicMapper thermodynamicMapper;

    @Autowired
    private Thermodynamic_01Mapper thermodynamicMapper_01;

    @Autowired
    private Thermodynamic_02Mapper thermodynamicMapper_02;

    @Autowired
    private Thermodynamic_03Mapper thermodynamicMapper_03;
    @Autowired
    private MongoDbDao mongoDbDao;

    @Autowired
    private MapAdminSystemMapper mapAdminSystemMapper;

    /**
     * 根据热力图 查询出点位
     *
     * @Author: WuShuang on 2019/11/29  16:32
     * @param: [heatDTO]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String selectThermodynamic(HeatDTO heatDTO) {

        if(heatDTO.getHeatType().equals("5") || heatDTO.getHeatType().equals("6") || heatDTO.getHeatType().equals("7")){
            List<HeatDTO.HeatMapDataDTO> heatMapDataDTOS1 =  thermodynamicMapper_03.findThermodynamic(heatDTO);
            return ResponseUtil.successToClient(heatMapDataDTOS1);
        }
        String city1 = "";
        if(heatDTO.getCity().equals("吉林")){
            city1  = "吉林市";
        }else {
            String provinces = mapAdminSystemMapper.findProvinces(heatDTO.getCity());
            //这个肯定是 凉山彝族自治州
            city1 = mapAdminSystemMapper.selectProvinces(heatDTO.getCity(),provinces);
            //有可能是 凉山州 也有可能是 凉山彝族自治州
        }
        List<String> city = mapAdminSystemMapper.isThermodynamicByUserId(heatDTO,city1);


        if(!city.isEmpty()){
          heatDTO.setCity(city1);
          if("4".equals(heatDTO.getHeatType())){
              List<HeatDTO.HeatMapDataDTO> heatMapDataDTOS =  thermodynamicMapper.selectAreYouHeatMap(heatDTO);
              List<HeatDTO.HeatMapDataDTO> heatMapDataDTOS1 =   thermodynamicMapper_01.selectNewAreYou(heatDTO);
              heatMapDataDTOS.addAll(heatMapDataDTOS1);
              return ResponseUtil.successToClient(heatMapDataDTOS);
          }
          List<Map<String,String>> map = thermodynamicMapper_02.selectHeatRule(heatDTO);
          List<List<HeatDTO.HeatDataDTO>> lists = new ArrayList<>();
          for(Map<String,String > map1 :map){
              List<HeatDTO.HeatDataDTO> heatDataDTO = thermodynamicMapper_02.selectThermodynamic(map1,heatDTO);
              lists.add(heatDataDTO);
          }
         /**
           * 查询出数据
           */
          List<HeatDTO.HeatMapDataDTO> heatMapDataDTOS = thermodynamicMapper.selectThermodynamicData(lists,heatDTO);
          List<HeatDTO.HeatMapDataDTO> heatMapDataDTOS1 =  thermodynamicMapper_01.selectNewAreYou(heatDTO);
          heatMapDataDTOS.addAll(heatMapDataDTOS1);
          //清空缓存用
          //thermodynamicMapper.upd();
          return ResponseUtil.successToClient(heatMapDataDTOS);
        }

      return ResponseUtil.errorMsgToClient(MSG_001013,"您没有开通城市权限/或开通时间没到！");


       /* if("4".equals(heatDTO.getHeatType())){
            List<HeatDTO.HeatMapDataDTO> heatMapDataDTOS =  thermodynamicMapper.selectAreYouHeatMap(heatDTO);
            List<HeatDTO.HeatMapDataDTO> heatMapDataDTOS1 =   thermodynamicMapper.selectNewAreYou(heatDTO);
            heatMapDataDTOS.addAll(heatMapDataDTOS1);
            return ResponseUtil.successToClient(heatMapDataDTOS);
        }

        List<Map<String,String>> map = thermodynamicMapper.selectHeatRule(heatDTO);

        List<List<HeatDTO.HeatDataDTO>> lists = new ArrayList<>();

        for(Map<String,String > map1 :map){
            List<HeatDTO.HeatDataDTO> heatDataDTO = thermodynamicMapper.selectThermodynamic(map1,heatDTO);
            lists.add(heatDataDTO);
        }
    *//*首页数据
    * *//*
                List<HeatDTO.HeatMapDataDTO> heatMapDataDTOS = thermodynamicMapper.selectThermodynamicData(lists);
        List<HeatDTO.HeatMapDataDTO> heatMapDataDTOS1 =   thermodynamicMapper.selectNewAreYou(heatDTO);
        heatMapDataDTOS.addAll(heatMapDataDTOS1);
        //清空缓存用
        //thermodynamicMapper.upd();
        return ResponseUtil.successToClient(heatMapDataDTOS);*/
    }
/*    @Autowired
    private AsyncTask asyncTask;*/
    /**
     * 删除热力图坐标点
     *
     * @Author: WuShuang on 2019/12/28  11:17
     * @param: [heatMapDeleteDTO]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String deleteThermodynamic(HeatDTO.HeatMapDeleteDTO heatMapDeleteDTO) {
        if(heatMapDeleteDTO.getType().equals("5") || heatMapDeleteDTO.getType().equals("6") || heatMapDeleteDTO.getType().equals("7")) {
            return ResponseUtil.errorMsgToClient("请删除正确点位！");
        }

        new Thread(
                () -> {
                    HeatDTO heatDTO = new HeatDTO();
                    heatDTO.setCity(heatMapDeleteDTO.getCity());
                    heatDTO.setHeatMapId(heatMapDeleteDTO.getHeatMapId());
                    heatDTO.setHeatType(heatMapDeleteDTO.getType()+"");
                    heatDTO.setUserId(heatMapDeleteDTO.getUserId());
                    String q = selectThermodynamic(heatDTO);
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
                }
        ).start();


        /*if(heatMapDeleteDTO.getType() == 4){
            Integer x = 0;
            Integer i = 0;
           List<Map<String,String>> map =  thermodynamicMapper.selectAreYouByDel(heatMapDeleteDTO);
           if(!map.isEmpty()){
               i =  thermodynamicMapper.deleteAreYou(map);
           }
           List<Map<String,String>> map1 = thermodynamicMapper.selectNewAreYouByDel(heatMapDeleteDTO);
           if(!map1.isEmpty()){
               x =  thermodynamicMapper.deleteNewAreYou(map1);
           }

           if(i>0 || x>0){
               return ResponseUtil.successToClient();
           }else {
               return ResponseUtil.successToClient("请删除正确点位！");
           }
        }else {
            Integer x = 0;
            Integer i = 0;
            List<Map<String,String>> map =  thermodynamicMapper.selectThermodynamicByDel(heatMapDeleteDTO);
            map.stream().forEach(
                    stringStringMap -> System.err.println(stringStringMap.toString())
            );
            System.err.println();
            if(!map.isEmpty()){
                i =  thermodynamicMapper.deleteThermodynamic(map);
            }
            List<Map<String,String>> map1 = thermodynamicMapper.selectNewAreYouByDel1(heatMapDeleteDTO);
            if(!map1.isEmpty()){
                x =  thermodynamicMapper.deleteNewAreYou(map1);
            }
            if(i>0 || x>0){
                return ResponseUtil.successToClient();
            }else {
                return ResponseUtil.successToClient("请删除正确点位！");
            }
        }*/
        return ResponseUtil.successToClient();

    }





    /**
     * 地球半径
     */
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
    }


    /**
     *添加点位经纬度
     *
     * @Author: WuShuang on 2020/1/7  16:07
     * @param: [addHeatMapDataDTO]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String addThermodynamic(HeatDTO.AddHeatMapDataDTO addHeatMapDataDTO) {
        if(addHeatMapDataDTO.getType().equals("5") || addHeatMapDataDTO.getType().equals("6") || addHeatMapDataDTO.getType().equals("7")) {
            return ResponseUtil.errorMsgToClient("请删除正确点位！");
        }
        String provinces = mapAdminSystemMapper.findProvinces(addHeatMapDataDTO.getCity());
        //这个肯定是 凉山彝族自治州
        String city1 = mapAdminSystemMapper.selectProvinces(addHeatMapDataDTO.getCity(),provinces);
        addHeatMapDataDTO.setCity(city1);
        Integer integer = thermodynamicMapper_01.addThermodynamic(addHeatMapDataDTO);
        if(integer > 0){
            return ResponseUtil.successToClient();
        }
        return ResponseUtil.errorToClient();
    }
}
