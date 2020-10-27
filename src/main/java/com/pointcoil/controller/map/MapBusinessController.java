package com.pointcoil.controller.map;

import com.pointcoil.conf.DataValidation;
import com.pointcoil.constant.MsgConstant;
import com.pointcoil.dto.map.BrandDTO;
import com.pointcoil.dto.map.MapCommonDTO;
import com.pointcoil.service.map.MapBusinessService;
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
import java.util.Map;

/**
 * Created by WuShuang on 2019/11/4.
 */
@RestController
@RequestMapping("/api/mapBusiness/")
@SuppressWarnings("ALL")
@Slf4j
public class MapBusinessController {


    @Autowired
    private MapBusinessService mapBusinessService;

    @Autowired
    private TokenCheck tokenCheck;



    /**
     *商圈首页的初始化
     *
     * @Author: WuShuang on 2019/11/4  15:34
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("businessInit")
    public String businessInit(@RequestBody @Valid MapCommonDTO mapCommonDTO , BindingResult result){
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        Object redisData = tokenCheck.checkTokenByShopId(mapCommonDTO.getUserId(),mapCommonDTO.getToken());
        if (null == redisData) {
            return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
        }
        return mapBusinessService.businessInit(mapCommonDTO);
    }


    /**
     *行业初始化
     *
     * @Author: WuShuang on 2019/11/4  10:55
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("industryInit")
    public String industryInit(@RequestBody @Valid MapCommonDTO mapCommonDTO, BindingResult result){
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        Object redisData = tokenCheck.checkTokenByShopId(mapCommonDTO.getUserId(),mapCommonDTO.getToken());
        if (null == redisData) {
            return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
        }
        return mapBusinessService.industryInit();
    }


    /**
     *创建品牌
     *
     * @Author: WuShuang on 2019/11/4  13:40
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("addBrand")
    public String addBrand(@RequestBody @Valid BrandDTO brandDTO,BindingResult result){
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        Object redisData = tokenCheck.checkTokenByShopId(brandDTO.getUserId(),brandDTO.getToken());
        if (null == redisData) {
            return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
        }
        return mapBusinessService.addBrand(brandDTO);
    }

    /**
     *修改品牌
     *
     * @Author: WuShuang on 2019/11/4  14:35
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("updateBrand")
    public String updateBrand(@RequestBody @Valid BrandDTO.UpdateBrandDTO brandDTO, BindingResult result){
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        Object redisData = tokenCheck.checkTokenByShopId(brandDTO.getUserId(),brandDTO.getToken());
        if (null == redisData) {
            return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
        }
        return mapBusinessService.updateBrand(brandDTO);
    }


    /**
     *回显要修改的
     *
     * @Author: WuShuang on 2019/11/4  14:51
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("selectBrand")
    public String selectBrand(@RequestBody @Valid BrandDTO.BrandId brandDTO,BindingResult result){
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        Object redisData = tokenCheck.checkTokenByShopId(brandDTO.getUserId(),brandDTO.getToken());
        if (null == redisData) {
            return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
        }
        return mapBusinessService.selectBrand(brandDTO);
    }

    /**
     *删除品牌
     *
     * @Author: WuShuang on 2019/11/4  16:45
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("deleteBrand")
    public String deleteBrand(@RequestBody @Valid BrandDTO.BrandId brandDTO,BindingResult result){
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        Object redisData = tokenCheck.checkTokenByShopId(brandDTO.getUserId(),brandDTO.getToken());
        if (null == redisData) {
            return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
        }
        return mapBusinessService.deleteBrand(brandDTO);
    }
}
