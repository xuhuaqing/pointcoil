package com.pointcoil.service.map.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pointcoil.conf.PointCoilProperties;
import com.pointcoil.dto.map.*;
import com.pointcoil.mapper.MapBusinessMapper;
import com.pointcoil.mapper.MapBusinessZoneMapper;
import com.pointcoil.mapper.OfficialWebsiteServiceMapper;
import com.pointcoil.mongo.MongoDbDao;
import com.pointcoil.service.map.MapBusinessZoneService;
import com.pointcoil.util.RedisUtil;
import com.pointcoil.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;

import static com.pointcoil.constant.Constants.*;
import static com.pointcoil.constant.MsgConstant.MSG_001010;
import static com.pointcoil.constant.MsgConstant.MSG_001011;

/**
 * Created by WuShuang on 2019/11/5.
 */
@Service
@Slf4j
@SuppressWarnings("all")
public class MapBusinessZoneServiceImpl implements MapBusinessZoneService {


    @Autowired
    private MapBusinessZoneMapper mapBusinessZoneMapper;
    @Autowired
    private MapBusinessMapper mapBusinessMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private PointCoilProperties pointCoilProperties;
    @Autowired
    private AsyncTask asyncTask;

    @Autowired
    private MongoDbDao mongoDbDao;
    @Autowired
    private OfficialWebsiteServiceMapper officialWebsiteServiceMapper;

    /**
     *删除商圈
     *
     * @Author: WuShuang on 2020/1/9  16:25
     * @param: [businessZoneDTO]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String deleteBusinessZone(BusinessZoneDTO.DeleteBusiness businessZoneDTO) {
        mapBusinessZoneMapper.deleteBusiness(businessZoneDTO.getBusinessId());
        /**
         * 删除原来的首页key
         */
        String businessZonePageInit = pointCoilProperties.getRedis().getBusinessZonePageInit();
        businessZonePageInit =  businessZonePageInit+businessZoneDTO.getBrandId();
        redisUtil.remove(businessZonePageInit);
        return ResponseUtil.successToClient();
    }

    /**
     *创建商圈
     *
     * @Author: WuShuang on 2019/11/5  21:07
     * @param: [businessZoneDTO]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String createBusinessZone(BusinessZoneDTO businessZoneDTO) {
        if(!StringUtils.isEmpty(businessZoneDTO.getCustomizedId())){
            mapBusinessZoneMapper.deleteCustomized(businessZoneDTO.getCustomizedId());
        }
        /**
         * 折线没有商圈报告
         */
        String replace1 = null;
        if(businessZoneDTO.getBusinessType() != THREE_INT){
            //报告ID
            replace1 =  UUID.randomUUID().toString().replace("-", "");
            //把商圈纬度存入到mongodb
            LocationPO.TestLocation testLocation = mongoDbDao.insertCollectionByCoordinates(businessZoneDTO.getCoordinates(), UUID.randomUUID().toString().replace("-", ""));
            //异步去生成测试报告
            asyncTask.createPresentation(replace1,businessZoneDTO);
        }
        System.err.println("执行");
        /**
         * 当前会员等级 允许创建多少
         */
        if(org.apache.commons.lang3.StringUtils.isEmpty(businessZoneDTO.getXxx())){
            MemberDTO memberDTO = mapBusinessMapper.selectCanCreateBrand(businessZoneDTO.getMemberLevel());
            Integer i = mapBusinessZoneMapper.selectCreateBusiness(businessZoneDTO.getUserId());
            if(i>=memberDTO.getBusinessNum()){
                return ResponseUtil.errorToClient(MSG_001010);
            }
        }
        /**
         * 存入数据库
         */
        String replace = UUID.randomUUID().toString().replace("-", "");
        businessZoneDTO.setXxx(replace);

        //添加区
/*        BusinessZoneDTO.Coordinate coordinate = businessZoneDTO.getCoordinates().get(0);

        net.sf.json.JSONObject city = MapUtils.getCity(String.valueOf(coordinate.getLng()),String.valueOf(coordinate.getLat()));

        String district =city.get("district").toString();
        businessZoneDTO.setDistrict(unicodeToUtf8(district));*/
        /**
         * 创建商圈 添加数据库
         */
        mapBusinessZoneMapper.insertBusinessZone(businessZoneDTO,JSON.toJSONString(businessZoneDTO.getCoordinates()),replace1);
        /**
         * 存入redisgeo里面
         */
        List<BusinessZoneDTO.Coordinate> coordinates = businessZoneDTO.getCoordinates();
        for(int q = 0 ;q<coordinates.size();q++){
            double lat = coordinates.get(q).getLat();
            double lng = coordinates.get(q).getLng();
            redisUtil.geoadd(replace,new Point(lng,lat),q);
        }
        officialWebsiteServiceMapper.addBrand("ffb56f9ffae811e9a2500242ac110003", 1);
        String homePageInit = pointCoilProperties.getRedis().getHomePageInit();
        redisUtil.remove(homePageInit);
        /**
         * 删除原来的首页key
         */
        String businessZonePageInit = pointCoilProperties.getRedis().getBusinessZonePageInit();
        businessZonePageInit =  businessZonePageInit+businessZoneDTO.getBrandId();

        redisUtil.remove(businessZonePageInit);



        return ResponseUtil.successToClient();
    }
    public String  unicodeToUtf8 (String s) {
        try {
            return new String(s.getBytes("ISO8859-1") , "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 展示商圈
     *
     * @Author: WuShuang on 2019/11/7  14:08
     * @param: [showBusinessZoneDTO]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String initBusinessZone(BrandDTO.BrandId showBusinessZoneDTO) {
        String businessZonePageInit = pointCoilProperties.getRedis().getBusinessZonePageInit();
        businessZonePageInit =  businessZonePageInit+showBusinessZoneDTO.getBrandId();
        /*Object o = redisUtil.get(businessZonePageInit);
        if(o!=null){
            JSONObject resultJson = (JSONObject) JSONObject.parse(o.toString());
            return ResponseUtil.successToClient(resultJson);
        }*/
        JSONObject jsonObject = new JSONObject();
        List<BusinessZoneDTO.SBusinessZoneDTO> sBusinessZoneDTOS = mapBusinessZoneMapper.showBusinessZoneInit(showBusinessZoneDTO.getBrandId(),showBusinessZoneDTO.getCityName());
        if(sBusinessZoneDTOS.isEmpty()){
            jsonObject.put("businessZone","");
            Object redisDta = redisUtil.get(showBusinessZoneDTO.getBrandId()+defaultAddress);
            if(redisDta!=null){
                jsonObject.put("defaultAddress",JSONObject.parse(redisDta.toString()));
            }else {

                jsonObject.put("defaultAddress","");
            }
            redisUtil.set(businessZonePageInit,jsonObject.toJSONString());
            return ResponseUtil.toClient(MSG_001011, (Object) jsonObject);
        }
        Object redisDta = redisUtil.get(showBusinessZoneDTO.getBrandId()+defaultAddress);
        if(redisDta!=null){
            jsonObject.put("defaultAddress",JSONObject.parse(redisDta.toString()));
        }else {
            jsonObject.put("defaultAddress","");
        }
        jsonObject.put("businessZone",sBusinessZoneDTOS);
        redisUtil.set(businessZonePageInit,jsonObject.toJSONString());
        return ResponseUtil.successToClient(jsonObject);
    }

    /**
     *设置默认地址
     *
     * @Author: WuShuang on 2019/11/7  16:43
     * @param: [mapCommonDTO]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String defaultAddress(BusinessZoneDTO.BusinessAddress mapCommonDTO) {
        /**
         * 删除原来的首页key
         */
        String businessZonePageInit = pointCoilProperties.getRedis().getBusinessZonePageInit();
        businessZonePageInit =  businessZonePageInit+mapCommonDTO.getBrandId();
        redisUtil.remove(businessZonePageInit);
        BusinessZoneDTO.Coordinate c = new BusinessZoneDTO.Coordinate(mapCommonDTO.getLng(),mapCommonDTO.getLat());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("address",c);
        jsonObject.put("city",mapCommonDTO.getCity());
        redisUtil.set(mapCommonDTO.getBrandId()+defaultAddress,jsonObject.toJSONString());
        System.err.println(jsonObject.toJSONString());
        return ResponseUtil.successToClient();
    }

    /**
     * 商圈筛选
     *
     * @Author: WuShuang on 2019/11/8  10:06
     * @param: [showBusinessZoneDTO]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String showBusinessZone(BusinessZoneDTO.ShowBusinessZoneDTO showBusinessZoneDTO) {
        List<BusinessZoneDTO.SBusinessZoneDTO> sBusinessZoneDTOS = mapBusinessZoneMapper.showBusinessZone(showBusinessZoneDTO);

       //List<BusinessZoneDTO.I1> sBusinessZoneDTOS1 = mapBusinessZoneMapper.selectCustomized(showBusinessZoneDTO.getBrandId());
        List<BusinessZoneDTO.SBusinessZoneDTO> sBusinessZoneDTOS1 = mapBusinessMapper.selectCustomized(showBusinessZoneDTO.getBrandId(),showBusinessZoneDTO.getCityName());

        if(sBusinessZoneDTOS.isEmpty() && sBusinessZoneDTOS1.isEmpty()){
            return ResponseUtil.toClient(MSG_001011);
        }
        if(!sBusinessZoneDTOS1.isEmpty()){
            for(BusinessZoneDTO.SBusinessZoneDTO i1 :sBusinessZoneDTOS1){
                i1.setBusinessLevel("0");
                i1.setBusinessName("定制商圈");
                i1.setBusinessOpen("0");
                i1.setBusinessUnsold("0");
            }
            sBusinessZoneDTOS.addAll(sBusinessZoneDTOS1);
        }
        return ResponseUtil.successToClient(sBusinessZoneDTOS);
    }

    /**
     * 获取报告
     *
     * @Author: WuShuang on 2019/11/26  10:37
     * @param: [businessReport]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String showBusinessReport(BusinessZoneDTO.BusinessReport businessReport) {

        mapBusinessZoneMapper.businessCountByLook(businessReport.getReportId());

        Map<String,Object> map = mapBusinessZoneMapper.showBusinessReport(businessReport);
        if(map == null){return ResponseUtil.errorToClient();}
        map.put("cityData", JSONObject.parse(map.get("cityData")!=null ? map.get("cityData").toString():""));
        map.put("twoLevelIndustry", JSONArray.parse(map.get("twoLevelIndustry")!=null ? map.get("twoLevelIndustry").toString():""));
        map.put("gatherGuests", JSONArray.parse(map.get("gatherGuests")!=null ?map.get("gatherGuests").toString():""));
        map.put("gatherGuestsName", JSONArray.parse(map.get("gatherGuestsName")!=null ?map.get("gatherGuestsName").toString():""));
        map.put("officeName", JSONArray.parse(map.get("officeName") !=null ? map.get("officeName").toString():""));
        map.put("villageName", JSONArray.parse(map.get("villageName") !=null ? map.get("villageName").toString():""));
        //广告
        Object s = redisUtil.get("advertisement" + businessReport.getUserId());
        Object advertisement = redisUtil.get("advertisement");
        map.put("advertisement", StringUtils.isEmpty(s)?StringUtils.isEmpty(advertisement)?"":advertisement.toString():s.toString());
        return ResponseUtil.successToClient(map);
    }

    /**
     *  回显商圈数据
     *
     * @Author: WuShuang on 2019/12/5  18:51
     * @param: [showBusinessById]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String showBusinessZoneById(BusinessZoneDTO.ShowBusinessById showBusinessById) {
        List<BusinessZoneDTO.UpdateBusiness> sBusinessZoneDTO =  mapBusinessZoneMapper.showBusinessZoneById(showBusinessById);
       // mapBusinessZoneMapper.upd();
        return ResponseUtil.successToClient(sBusinessZoneDTO.get(0));
    }

    /**
     * 修改商圈
     *
     * @Author: WuShuang on 2019/12/6  9:51
     * @param: [updateBusiness]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String updateBusinessZone(BusinessZoneDTO.UpdateBusiness updateBusiness) {

        mapBusinessZoneMapper.updateBusinessZone(updateBusiness);
        mapBusinessZoneMapper.upd();

        return ResponseUtil.successToClient();
    }

    /**
     * 缩放地图
     *
     * @Author: WuShuang on 2019/12/10  16:18
     * @param: [businessAddress]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String zoomBusiness(BusinessZoneDTO.ZoomBusiness businessAddress) {
        Map<String, Object> objectObjectHashMap = new HashMap<>();
        String i = mapBusinessZoneMapper.selectProvince(businessAddress.getCity());
        List<BusinessZoneDTO.ZoomBusinessDTO> zoomBusinessDTO = mapBusinessZoneMapper.zoomBusinessBy20(businessAddress,i);
        List<BusinessZoneDTO.ZoomBusinessDTO> zoomBusinessDTO1 = mapBusinessZoneMapper.zoomBusiness(businessAddress);
        objectObjectHashMap.put("bigSize",zoomBusinessDTO);
        objectObjectHashMap.put("smallSize",zoomBusinessDTO1);
        // mapBusinessZoneMapper.upd();
        return ResponseUtil.successToClient(objectObjectHashMap);
    }

    /**
     * 添加热力图截图
     *
     * @Author: WuShuang on 2019/12/23  11:04
     * @param: [pictureChar]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String updThermodynamicChart(BusinessZoneDTO.PictureChar pictureChar) {
        mapBusinessZoneMapper.updThermodynamicChart(pictureChar);
        mapBusinessZoneMapper.updateBusiness(pictureChar);
        mapBusinessZoneMapper.upd();
        return ResponseUtil.successToClient();
    }




}
