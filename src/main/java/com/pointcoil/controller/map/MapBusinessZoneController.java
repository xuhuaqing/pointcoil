package com.pointcoil.controller.map;

import com.pointcoil.conf.DataValidation;
import com.pointcoil.constant.MsgConstant;
import com.pointcoil.dto.map.BrandDTO;
import com.pointcoil.dto.map.BusinessZoneDTO;
import com.pointcoil.dto.map.MapCommonDTO;
import com.pointcoil.service.map.MapBusinessZoneService;
import com.pointcoil.util.ResponseUtil;
import com.pointcoil.util.TokenCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by WuShuang on 2019/11/5.
 */
@RestController
@RequestMapping("/api/mapBusinessZone/")
@SuppressWarnings("ALL")
@Slf4j
public class MapBusinessZoneController {

    @Autowired
    private MapBusinessZoneService mapBusinessZoneService;

    @Autowired
    private TokenCheck tokenCheck;

    /**
     * 删除商圈
     *
     * @Author: WuShuang on 2020/1/8  17:08
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("deleteBusinessZone")
    public String deleteBusinessZone(@Valid @RequestBody BusinessZoneDTO.DeleteBusiness businessZoneDTO , BindingResult result){
        log.info("入参：{}",businessZoneDTO);
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        Object redisData = tokenCheck.checkTokenByShopId(businessZoneDTO.getUserId(),businessZoneDTO.getToken());
        if (null == redisData) {
            return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
        }
        return mapBusinessZoneService.deleteBusinessZone(businessZoneDTO);
    }

    /**
     * 创建商圈
     *
     * @Author: WuShuang on 2019/11/5  20:09
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("createBusinessZone")
    public String createBusinessZone(@Valid @RequestBody BusinessZoneDTO businessZoneDTO , BindingResult result){
        log.info("入参：{}",businessZoneDTO);
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        Object redisData = tokenCheck.checkTokenByShopId(businessZoneDTO.getUserId(),businessZoneDTO.getToken());
        if (null == redisData) {
            return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
        }
        return mapBusinessZoneService.createBusinessZone(businessZoneDTO);
    }


    /**
     * 商圈初始化
     *
     * @Author: WuShuang on 2019/11/7  14:07
     * @param: [showBusinessZoneDTO, result]
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("initBusinessZone")
    public String initBusinessZone(@Valid @RequestBody BrandDTO.BrandId showBusinessZoneDTO , BindingResult result){
        log.info("入参：{}",showBusinessZoneDTO);
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        Object redisData = tokenCheck.checkTokenByShopId(showBusinessZoneDTO.getUserId(),showBusinessZoneDTO.getToken());
        if (null == redisData) {
            return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
        }
        return mapBusinessZoneService.initBusinessZone(showBusinessZoneDTO);
    }

    /**
     *设为默认收获地址
     *
     * @Author: WuShuang on 2019/11/7  16:42
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("defaultAddress")
    public String defaultAddress(@Valid @RequestBody BusinessZoneDTO.BusinessAddress mapCommonDTO , BindingResult result){
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        Object redisData = tokenCheck.checkTokenByShopId(mapCommonDTO.getUserId(),mapCommonDTO.getToken());
        if (null == redisData) {
            return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
        }
        return mapBusinessZoneService.defaultAddress(mapCommonDTO);
    }

    /**
     * 商圈筛选
     *
     * @Author: WuShuang on 2019/11/7  14:07
     * @param: [showBusinessZoneDTO, result]
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("showBusinessZone")
    public String showBusinessZone(@Valid @RequestBody BusinessZoneDTO.ShowBusinessZoneDTO showBusinessZoneDTO , BindingResult result){
        log.info("入参：{}",showBusinessZoneDTO);
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        Object redisData = tokenCheck.checkTokenByShopId(showBusinessZoneDTO.getUserId(),showBusinessZoneDTO.getToken());
        if (null == redisData) {
            return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
        }
        return mapBusinessZoneService.showBusinessZone(showBusinessZoneDTO);
    }


    /**
     *查看商圈报告
     *
     * @Author: WuShuang on 2019/11/26  10:21
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("showBusinessReport")
    public String showBusinessReport(@Valid @RequestBody BusinessZoneDTO.BusinessReport businessReport, BindingResult result){
        log.info("入参：{}",businessReport);
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        Object redisData = tokenCheck.checkTokenByShopId(businessReport.getUserId(),businessReport.getToken());
        if (null == redisData) {
            return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
        }
        return mapBusinessZoneService.showBusinessReport(businessReport);
    }


    /**
     * 修改商圈回显数据
     *
     * @Author: WuShuang on 2019/12/5  18:48
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("showBusinessZoneById")
    public String showBusinessZoneById(@Valid @RequestBody BusinessZoneDTO.ShowBusinessById showBusinessById, BindingResult result){
        log.info("入参：{}",showBusinessById);
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        Object redisData = tokenCheck.checkTokenByShopId(showBusinessById.getUserId(),showBusinessById.getToken());
        if (null == redisData) {
            return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
        }
        return mapBusinessZoneService.showBusinessZoneById(showBusinessById);
    }


    /**
     * 修改标签
     *
     * @Author: WuShuang on 2019/12/6  9:49
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("updateBusinessZone")
    public String updateBusinessZone(@Valid @RequestBody BusinessZoneDTO.UpdateBusiness updateBusiness ,BindingResult result){
        log.info("入参：{}",updateBusiness);
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        Object redisData = tokenCheck.checkTokenByShopId(updateBusiness.getUserId(),updateBusiness.getToken());
        if (null == redisData) {
            return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
        }
        return mapBusinessZoneService.updateBusinessZone(updateBusiness);
    }


    /**
     * 滚轮缩放商圈
     *
     * @Author: WuShuang on    16:00
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("zoomBusiness")
    public String zoomBusiness(@Valid @RequestBody BusinessZoneDTO.ZoomBusiness businessAddress,BindingResult result){
        log.info("入参：{}",businessAddress);
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        Object redisData = tokenCheck.checkTokenByShopId(businessAddress.getUserId(),businessAddress.getToken());
        if (null == redisData) {
            return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
        }
        return mapBusinessZoneService.zoomBusiness(businessAddress);
    }
    /**
     * 热力图截图
     *
     * @Author: WuShuang on 2019/12/23  11:07
     * @param: [pictureChar, result]
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("updThermodynamicChart")
    public String updThermodynamicChart(@Valid @RequestBody BusinessZoneDTO.PictureChar pictureChar,BindingResult result){
        log.info("入参：{}",pictureChar);
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        Object redisData = tokenCheck.checkTokenByShopId(pictureChar.getUserId(),pictureChar.getToken());
        if (null == redisData) {
            return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
        }
        return mapBusinessZoneService.updThermodynamicChart(pictureChar);
    }



}
