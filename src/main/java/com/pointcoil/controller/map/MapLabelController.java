package com.pointcoil.controller.map;

import com.pointcoil.conf.DataValidation;
import com.pointcoil.constant.MsgConstant;
import com.pointcoil.dto.map.BrandDTO;
import com.pointcoil.dto.map.LabelDTO;
import com.pointcoil.dto.map.MapCommonDTO;
import com.pointcoil.service.map.MapLabelService;
import com.pointcoil.util.ResponseUtil;
import com.pointcoil.util.TokenCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by WuShuang on 2019/11/8.
 */
@RestController
@RequestMapping("/api/mapLabel/")
@SuppressWarnings("ALL")
@Slf4j
public class MapLabelController {

    @Autowired
    private MapLabelService mapLabelService;

    @Autowired
    private TokenCheck tokenCheck;

    /**
     *创建标签时回显的名 称
     *
     * @Author: WuShuang on 2019/11/11  9:18
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("createLabelShowBusinessName")
    public String createLabelShowBusinessName(@RequestBody @Valid BrandDTO.BrandId mapCommonDTO , BindingResult result){
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        Object redisData = tokenCheck.checkTokenByShopId(mapCommonDTO.getUserId(),mapCommonDTO.getToken());
        if (null == redisData) {
            return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
        }
        return mapLabelService.createLabelShowBusinessName(mapCommonDTO);
    }


    /**
     *展示标签
     *
     * @Author: WuShuang on 2019/11/11  11:36
     * @param: [mapCommonDTO, result]
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("showLabelStyle")
    public String showLabelStyle(@RequestBody @Valid MapCommonDTO mapCommonDTO,BindingResult result){
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        Object redisData = tokenCheck.checkTokenByShopId(mapCommonDTO.getUserId(),mapCommonDTO.getToken());
        if (null == redisData) {
            return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
        }
        return mapLabelService.showLabelStyle(mapCommonDTO);
    }

    /**
     * 创建标签
     *
     * @Author: WuShuang on 2019/11/11  13:25
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("createLabel")
    public String createLabel(@RequestBody @Valid LabelDTO labelDTO,BindingResult result){
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        Object redisData = tokenCheck.checkTokenByShopId(labelDTO.getUserId(),labelDTO.getToken());
        if (null == redisData) {
            return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
        }
        return mapLabelService.createLabel(labelDTO);
    }


    /**
     *展示标签
     *
     * @Author: WuShuang on 2019/11/11  15:03
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("showLabel")
    public String showLabel(@RequestBody @Valid LabelDTO.ShowLabel showLabel,BindingResult result){
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        Object redisData = tokenCheck.checkTokenByShopId(showLabel.getUserId(),showLabel.getToken());
        if (null == redisData) {
            return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
        }
        return mapLabelService.showLabel(showLabel);
    }


    /**
     *删除标签
     *
     * @Author: WuShuang on 2019/11/11  17:26
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("deleteLabel")
    public String deleteLabel(@RequestBody @Valid LabelDTO.LabelId labelId,BindingResult result){
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        Object redisData = tokenCheck.checkTokenByShopId(labelId.getUserId(),labelId.getToken());
        if (null == redisData) {
            return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
        }
        return mapLabelService.deleteLabel(labelId);
    }

    /**
     *修改回显数据
     *
     * @Author: WuShuang on 2019/11/11  17:43
     * @param: [labelId, result]
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("selectLabelById")
    public String selectLabelById(@RequestBody @Valid LabelDTO.LabelId labelId,BindingResult result){
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        Object redisData = tokenCheck.checkTokenByShopId(labelId.getUserId(),labelId.getToken());
        if (null == redisData) {
            return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
        }
        return mapLabelService.selectLabelById(labelId);
    }


    /**
     * 修改标签
     *
     * @Author: WuShuang on 2019/11/11  17:53
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("updateLabelById")
    public String updateLabelById(@RequestBody @Valid LabelDTO.UpdateLabel updateLabel,BindingResult result){
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        Object redisData = tokenCheck.checkTokenByShopId(updateLabel.getUserId(),updateLabel.getToken());
        if (null == redisData) {
            return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
        }
        return mapLabelService.updateLabelById(updateLabel);
    }


    /**
     * 1111
     *
     * @param
     * @return java.lang.String
     * @methodName clear
     * @author WuShunag
     * @date 17:43
     */
    @PostMapping("clear")
    public String clear(@RequestBody @Valid LabelDTO labelDTO,BindingResult result){

         return    mapLabelService.clear(labelDTO);
    }

}
