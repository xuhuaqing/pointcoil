package com.pointcoil.controller.map;

import com.pointcoil.conf.DataValidation;
import com.pointcoil.constant.MsgConstant;
import com.pointcoil.dto.map.MapCommonDTO;
import com.pointcoil.service.map.HeatMapService;
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

import static com.pointcoil.constant.Constants.STATUS;
import static com.pointcoil.constant.MsgConstant.MSG_001013;

/**
 * Created by WuShuang on 2019/11/13.
 */
@RestController
@RequestMapping("/api/heatMap/")
@SuppressWarnings("ALL")
@Slf4j
public class HeatMapController {


    @Autowired
    private HeatMapService heatMapService;

    @Autowired
    private TokenCheck tokenCheck;



    /**
     *  热力图初始化
     *
     * @Author: WuShuang on 2019/11/13  10:35
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("initHeatMap")
    public String initHeatMap(@RequestBody @Valid MapCommonDTO mapCommonDTO, BindingResult result){
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        Object redisData = tokenCheck.checkTokenByShopId(mapCommonDTO.getUserId(),mapCommonDTO.getToken());
        if (null == redisData) {
            return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
        }
    /*    if(STATUS.equals(mapCommonDTO.getMemberLevel())){
            return ResponseUtil.toClient(MSG_001013);
        }*/
        return heatMapService.initHeatMap();
    }

}
