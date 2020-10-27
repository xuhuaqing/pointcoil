package com.pointcoil.controller.map;

import com.pointcoil.conf.DataValidation;
import com.pointcoil.constant.MsgConstant;
import com.pointcoil.dto.map.HeatDTO;
import com.pointcoil.service.map.ThermodynamicService;
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
 * Created by WuShuang on 2019/11/29.
 */
@RestController
@RequestMapping("/api/thermodynamic/")
@SuppressWarnings("ALL")
@Slf4j
public class ThermodynamicController {

    @Autowired
    private ThermodynamicService thermodynamicService;


    @Autowired
    private TokenCheck tokenCheck;



    /**
     * 展示热力图
     *
     * @Author: WuShuang on 2019/11/29  15:44
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("showThermodynamic")
    public String showThermodynamic(@RequestBody @Valid HeatDTO heatDTO, BindingResult result){
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        Object redisData = tokenCheck.checkTokenByShopId(heatDTO.getUserId(),heatDTO.getToken());
        if (null == redisData) {
            return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
        }
        return thermodynamicService.selectThermodynamic(heatDTO);
    }


    /**
     * 删除热力图点位
     *
     * @Author: WuShuang on 2019/12/28  10:46
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("deleteThermodynamic")
    public String deleteThermodynamic(@RequestBody @Valid HeatDTO.HeatMapDeleteDTO heatMapDeleteDTO, BindingResult result){
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        Object redisData = tokenCheck.checkTokenByShopId(heatMapDeleteDTO.getUserId(),heatMapDeleteDTO.getToken());
        if (null == redisData) {
            return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
        }
        return thermodynamicService.deleteThermodynamic(heatMapDeleteDTO);
    }

    /**
     * t添加热力图点位和热度
     *
     * @Author: WuShuang on 2020/1/7  15:53
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("addThermodynamic")
    public String addThermodynamic(@RequestBody @Valid HeatDTO.AddHeatMapDataDTO addHeatMapDataDTO, BindingResult result){
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        Object redisData = tokenCheck.checkTokenByShopId(addHeatMapDataDTO.getUserId(),addHeatMapDataDTO.getToken());
        if (null == redisData) {
            return ResponseUtil.errorToClient(MsgConstant.MSG_001005);
        }
        return thermodynamicService.addThermodynamic(addHeatMapDataDTO);
    }

}
