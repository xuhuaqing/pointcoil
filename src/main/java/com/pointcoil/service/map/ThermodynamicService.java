package com.pointcoil.service.map;

import com.pointcoil.dto.map.HeatDTO;

/**
 * Created by WuShuang on 2019/11/29.
 */
public interface ThermodynamicService {
    /**
     *根据id 查询出热力图
     *
     * @Author: WuShuang on 2019/11/29  16:32
     * @param: [heatDTO]
     * @return: java.lang.String
     * @Description:
     */
    String selectThermodynamic(HeatDTO heatDTO);

    /**
     *删除热力图坐标点
     *
     * @Author: WuShuang on 2019/12/28  11:17
     * @param: [heatMapDeleteDTO]
     * @return: java.lang.String
     * @Description:
     */
    String deleteThermodynamic(HeatDTO.HeatMapDeleteDTO heatMapDeleteDTO);

    /**
     *添加热力图点位
     *
     * @Author: WuShuang on 2020/1/7  16:05
     * @param: [addHeatMapDataDTO]
     * @return: java.lang.String
     * @Description:
     */
    String addThermodynamic(HeatDTO.AddHeatMapDataDTO addHeatMapDataDTO);
}
