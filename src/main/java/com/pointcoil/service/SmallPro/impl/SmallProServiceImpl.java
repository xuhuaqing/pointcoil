package com.pointcoil.service.SmallPro.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pointcoil.conf.PointCoilProperties;
import com.pointcoil.dto.map.BusinessZoneDTO;
import com.pointcoil.entity.UserEntity;
import com.pointcoil.mapper.MapBusinessZoneMapper;
import com.pointcoil.mapper.OfficialWebsiteServiceMapper;
import com.pointcoil.mapper.SmallProMapper;
import com.pointcoil.service.SmallPro.SmallProService;
import com.pointcoil.util.RedisUtil;
import com.pointcoil.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * Created by WuShuang on 2019/11/30.
 */
@Service
@SuppressWarnings("ALL")
public class SmallProServiceImpl implements SmallProService {

    @Autowired
    private SmallProMapper smallProMapper;

    @Autowired
    private MapBusinessZoneMapper mapBusinessZoneMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private OfficialWebsiteServiceMapper officialWebsiteServiceMapper;

    @Autowired
    private PointCoilProperties pointCoilProperties;
    /**
     *  保存信息
     *
     * @Author: WuShuang on 2019/11/30  13:49
     * @param: [user]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String preservationUser(UserEntity user) {
        Integer i =   smallProMapper.selectOpenId(user.getOpenId());
        if(i>0){
            return ResponseUtil.successToClient();
        }
        user.setUserName(filterEmoji(user.getUserName()));
        smallProMapper.insertPreservationUser(user);
        return ResponseUtil.successToClient();
    }

    public  static  String filterEmoji(String source) {
        if (!containsEmoji(source)) {
            return source;//如果不包含，直接返回
        }

        StringBuilder buf = null;//该buf保存非emoji的字符
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (notisEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }
                buf.append(codePoint);
            }
        }

        if (buf == null) {
            return "";//如果没有找到非emoji的字符，则返回无内容的字符串
        } else {
            if (buf.length() == len) {
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }
    }

    public  static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!notisEmojiCharacter(codePoint)) {
                //判断确认有表情字符
                return true;
            }
        }
        return false;
    }

    private  static boolean notisEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    @Override
    public String showBusinessReport(BusinessZoneDTO.BusinessReportApi businessReport) {
        String reportId = mapBusinessZoneMapper.selectReportId(businessReport.getReportId());
        if(StringUtils.isEmpty(reportId)){
             reportId = businessReport.getReportId();
            //次数+1
            mapBusinessZoneMapper.updateBusinessCount(reportId);
            /**
             * 首页关注次数+1
             */
            officialWebsiteServiceMapper.addBrand("41073ca1fae911e9a2500242ac110003", 1);
            String homePageInit = pointCoilProperties.getRedis().getHomePageInit();
            redisUtil.remove(homePageInit);
            //查询有没有关注过
            Integer i =  mapBusinessZoneMapper.isFollow(businessReport.getOpenId(),reportId);
            if(i == 0){
                //保存查看记录
                System.err.println("保存查看记录");
                mapBusinessZoneMapper.insertBusinessRecord(businessReport,reportId);
            }
            BusinessZoneDTO.BusinessReport businessReport1 =  new BusinessZoneDTO.BusinessReport();
            businessReport1.setReportId(reportId);
            Map<String,Object> map = mapBusinessZoneMapper.showBusinessReport(businessReport1);
            if(map == null){
                return ResponseUtil.successToClient("无数据");
            }
            map.put("businessOpen", mapBusinessZoneMapper.selectBusinessOpen(businessReport1.getReportId()));
            map.put("cityData", JSONObject.parse(map.get("cityData")!=null ? map.get("cityData").toString():""));
            map.put("twoLevelIndustry", JSONArray.parse(map.get("twoLevelIndustry")!=null ? map.get("twoLevelIndustry").toString():""));
            map.put("gatherGuests", JSONArray.parse(map.get("gatherGuests")!=null ?map.get("gatherGuests").toString():""));
            map.put("gatherGuestsName", JSONArray.parse(map.get("gatherGuestsName")!=null ?map.get("gatherGuestsName").toString():""));
            map.put("officeName", JSONArray.parse(map.get("officeName") !=null ? map.get("officeName").toString():""));
            map.put("villageName", JSONArray.parse(map.get("villageName") !=null ? map.get("villageName").toString():""));
            //广告
            map.put("advertisement",(String)redisUtil.get("advertisement"));
            return ResponseUtil.successToClient(map);
        }
        //次数+1
        mapBusinessZoneMapper.updateBusinessCount(reportId);
        /**
         * 首页关注次数+1
         */
        officialWebsiteServiceMapper.addBrand("41073ca1fae911e9a2500242ac110003", 1);
        String homePageInit = pointCoilProperties.getRedis().getHomePageInit();
        redisUtil.remove(homePageInit);
        //查询有没有关注过
        Integer i =  mapBusinessZoneMapper.isFollow(businessReport.getOpenId(),reportId);
        if(i == 0){
            //保存查看记录
            System.err.println("保存查看记录");
            mapBusinessZoneMapper.insertBusinessRecord(businessReport,reportId);
        }
        BusinessZoneDTO.BusinessReport businessReport1 =  new BusinessZoneDTO.BusinessReport();
        businessReport1.setReportId(reportId);
        Map<String,Object> map = mapBusinessZoneMapper.showBusinessReport(businessReport1);
        if(map == null){
            return ResponseUtil.successToClient("无数据");
        }
        map.put("businessOpen", mapBusinessZoneMapper.selectBusinessOpen(businessReport1.getReportId()));
        map.put("cityData", JSONObject.parse(map.get("cityData")!=null ? map.get("cityData").toString():""));
        map.put("twoLevelIndustry", JSONArray.parse(map.get("twoLevelIndustry")!=null ? map.get("twoLevelIndustry").toString():""));
        map.put("gatherGuests", JSONArray.parse(map.get("gatherGuests")!=null ?map.get("gatherGuests").toString():""));
        map.put("gatherGuestsName", JSONArray.parse(map.get("gatherGuestsName")!=null ?map.get("gatherGuestsName").toString():""));
        map.put("officeName", JSONArray.parse(map.get("officeName") !=null ? map.get("officeName").toString():""));
        map.put("villageName", JSONArray.parse(map.get("villageName") !=null ? map.get("villageName").toString():""));
        //广告
        map.put("advertisement",(String)redisUtil.get("advertisement"));
        return ResponseUtil.successToClient(map);
    }

    /**
     * 我关注的商圈
     *
     * @Author: WuShuang on 2019/12/2  9:43
     * @param: [userEntity]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String showMyFollowBusiness(UserEntity userEntity) {
        List<Map<String,String>> map = mapBusinessZoneMapper.showMyFollowBusiness(userEntity);
        return ResponseUtil.successToClient(map);
    }

    /**
     * 查看关注商圈的人
     *
     * @Author: WuShuang on 2019/12/2  13:22
     * @param: [businessReport]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String businessFollowPeo(BusinessZoneDTO.BusinessReportApi businessReport) {
        List<Map<String,String>> map =  mapBusinessZoneMapper.businessFollowPeo(businessReport);
        return ResponseUtil.successToClient(map);
    }
}
