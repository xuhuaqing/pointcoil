package com.pointcoil.mapper;

import com.pointcoil.dto.map.GouldDTO;
import com.pointcoil.dto.map.HeatDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by WuShuang on 2019/11/29.
 */
public interface Thermodynamic_03Mapper {
    void uploadThermodynamicByUser(@Param("list") List<GouldDTO> areYouHungryDTOS, @Param("type") String type, @Param("userId") String userId);


    List<HeatDTO.HeatMapDataDTO> findThermodynamic(@Param("heatDTO") HeatDTO heatDTO);
}
