package com.pointcoil.service.map.impl;

import com.alibaba.fastjson.JSONObject;
import com.pointcoil.conf.PointCoilProperties;
import com.pointcoil.dto.map.BrandDTO;
import com.pointcoil.dto.map.MapCommonDTO;
import com.pointcoil.dto.map.MapIndustryDTO;
import com.pointcoil.dto.map.MemberDTO;
import com.pointcoil.mapper.MapBusinessMapper;
import com.pointcoil.mapper.OfficialWebsiteServiceMapper;
import com.pointcoil.service.map.MapBusinessService;
import com.pointcoil.util.RedisUtil;
import com.pointcoil.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.pointcoil.constant.MsgConstant.MSG_001009;

/**
 * Created by WuShuang on 2019/11/4.
 */
@Service
@Slf4j
@SuppressWarnings("ALL")
public class MapBusinessServiceImpl implements MapBusinessService {

    @Autowired
    private MapBusinessMapper mapBusinessMapper;
    @Autowired
    private PointCoilProperties pointCoilProperties;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private OfficialWebsiteServiceMapper officialWebsiteServiceMapper;

    /**
     * 行业初始化
     *
     * @Author: WuShuang on 2019/11/4  13:59
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String industryInit() {
        //全部的行业
        List<MapIndustryDTO> mapIndustryDTO = mapBusinessMapper.industryInit();

        return ResponseUtil.successToClient(mapIndustryDTO);
    }

    /**
     *创建品牌
     *
     * @Author: WuShuang on 2019/11/4  13:59
     * @param: [brandDTO]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String addBrand(BrandDTO brandDTO) {
        /**
         * 判断是否会员
         */
        //查询建了多少品牌
        Integer i = mapBusinessMapper.selectCreateBrand(brandDTO.getUserId());
        MemberDTO memberDTO = mapBusinessMapper.selectCanCreateBrand(brandDTO.getMemberLevel());
        if(i>=memberDTO.getBrandNum()){
            return ResponseUtil.toClient(MSG_001009);
        }
        String businessInit = pointCoilProperties.getRedis().getBusinessInit();
        businessInit = businessInit+brandDTO.getUserId();
        redisUtil.remove(businessInit);
        mapBusinessMapper.addBrand(brandDTO);

        officialWebsiteServiceMapper.addBrand("f0cb9bba1bdc11ea97920242ac110003", 1);
        String homePageInit = pointCoilProperties.getRedis().getHomePageInit();
        redisUtil.remove(homePageInit);
        return ResponseUtil.successToClient();
    }

    /**
     *修改品牌
     *x
     * @Author: WuShuang on 2019/11/4  14:36
     * @param: [brandDTO]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String updateBrand(BrandDTO.UpdateBrandDTO brandDTO) {
        mapBusinessMapper.updateBrand(brandDTO);
        String businessInit = pointCoilProperties.getRedis().getBusinessInit();
        businessInit = businessInit+brandDTO.getUserId();
        redisUtil.remove(businessInit);
        return ResponseUtil.successToClient();
    }

    /**
     *回显要修改的
     *
     * @Author: WuShuang on 2019/11/4  14:52
     * @param: [brandDTO]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String selectBrand(BrandDTO.BrandId brandDTO) {
        BrandDTO.SelectBrandDTO  selectBrandDTO = mapBusinessMapper.selectBrand(brandDTO);
        selectBrandDTO.setMapIndustryDTO(mapBusinessMapper.industryInit());
        return ResponseUtil.successToClient(selectBrandDTO);
    }

    /**
     *首页初始化
     *
     * @Author: WuShuang on 2019/11/4  15:38
     * @param: [mapCommonDTO]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String businessInit(MapCommonDTO mapCommonDTO) {
        String businessInit = pointCoilProperties.getRedis().getBusinessInit();
        businessInit = businessInit+mapCommonDTO.getUserId();
        if(redisUtil.exists(businessInit)) {
            JSONObject resultJson = (JSONObject) JSONObject.parse(redisUtil.get(businessInit).toString());
            return ResponseUtil.successToClient(resultJson);
        }else {
            List<BrandDTO.BrandInit> brandInit = mapBusinessMapper.businessInit(mapCommonDTO);
            JSONObject resultJson = new JSONObject();
            resultJson.put("brandInit",brandInit);
            redisUtil.set(businessInit, resultJson.toJSONString(), 30 * 60);
            return ResponseUtil.successToClient(resultJson);
        }
    }

    /**
     *删除商圈
     *
     * @Author: WuShuang on 2019/11/4  16:47
     * @param: [brandDTO]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    @Transactional
    public String deleteBrand(BrandDTO.BrandId brandDTO) {
        String businessInit = pointCoilProperties.getRedis().getBusinessInit();
        businessInit = businessInit+brandDTO.getUserId();
        mapBusinessMapper.deleteBrand(brandDTO);
        mapBusinessMapper.deleteReportUser(brandDTO);
        mapBusinessMapper.deleteReport(brandDTO);
        mapBusinessMapper.deleteBusiness(brandDTO);
        redisUtil.remove(businessInit);
        return ResponseUtil.successToClient();
    }
}
