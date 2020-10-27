package com.pointcoil.mapper;

import com.pointcoil.dto.map.HeatDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by WuShuang on 2019/11/29.
 */
public interface ThermodynamicMapper {
    /**
     * 查询热力图规则
     *
     * @Author: WuShuang on 2019/11/29  16:34
     * @param: [heatDTO]
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.String>>
     * @Description:
     */
    List<Map<String, String>> selectHeatRule(@Param("heatDTO") HeatDTO heatDTO);

    /**
     * 热力图赛选
     *
     * @Author: WuShuang on 2019/11/30  9:30
     * @param: [map1, heatDTO]
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.String>>
     * @Description:
     */
    List<HeatDTO.HeatDataDTO> selectThermodynamic(@Param("map1") Map<String, String> map1, @Param("heatDTO") HeatDTO heatDTO);

    void upd();

    /**
     * 查询热力图地理位置
     *
     * @Author: WuShuang on 2019/11/30  11:07
     * @param: [lists]
     * @return: java.util.List<com.pointcoil.dto.map.HeatDTO.HeatMapDataDTO>
     * @Description:
     */
    List<HeatDTO.HeatMapDataDTO> selectThermodynamicData(@Param("lists") List<List<HeatDTO.HeatDataDTO>> lists, @Param("heatDTO") HeatDTO heatDTO);

    /**
     * 外卖热力图
     *
     * @Author: WuShuang on 2019/12/3  18:52
     * @param: [heatDTO]
     * @return: java.util.List<com.pointcoil.dto.map.HeatDTO.HeatMapDataDTO>
     * @Description:
     */
    List<HeatDTO.HeatMapDataDTO> selectAreYouHeatMap(@Param("heatDTO") HeatDTO heatDTO);

    /**
     * 删除热力图坐标点
     *
     * @Author: WuShuang on 2019/12/28  11:17
     * @param: [heatMapDeleteDTO]
     * @return: void
     * @Description:
     */
    Integer deleteThermodynamic(@Param("map") List<HeatDTO.HeatMapDataDTO> sqq);

    /**
     * 删除外卖热力图
     *
     * @Author: WuShuang on 2019/12/28  11:20
     * @param: [heatMapDeleteDTO]
     * @return: void
     * @Description:
     */
    Integer deleteAreYou(@Param("map")List<HeatDTO.HeatMapDataDTO> sqq);

    /**
     * 查询删除条件
     *
     * @Author: WuShuang on 2020/1/7  14:31
     * @param: [heatMapDeleteDTO]
     * @return: java.util.Map<java.lang.String, java.lang.String>
     * @Description:
     */
    List<Map<String, String>> selectAreYouByDel(@Param("heatMapDeleteDTO") HeatDTO.HeatMapDeleteDTO heatMapDeleteDTO);

    /**
     * 查询删除条件
     *
     * @Author: WuShuang on 2020/1/7  14:59
     * @param: [heatMapDeleteDTO]
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.String>>
     * @Description:
     */
    List<Map<String, String>> selectThermodynamicByDel(@Param("heatMapDeleteDTO") HeatDTO.HeatMapDeleteDTO heatMapDeleteDTO);

    /**
     * 添加热力图经纬度
     *
     * @Author: WuShuang on 2020/1/7  16:09
     * @param: [addHeatMapDataDTO]
     * @return: void
     * @Description:
     */
    void addThermodynamic(@Param("addHeatMapDataDTO") HeatDTO.AddHeatMapDataDTO addHeatMapDataDTO);

    /**
     * 新增点位查询
     *
     * @Author: WuShuang on 2020/1/7  16:38
     * @param: [heatMapDeleteDTO]
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.String>>
     * @Description:
     */
    List<Map<String, String>> selectNewAreYouByDel(@Param("heatMapDeleteDTO") HeatDTO.HeatMapDeleteDTO heatMapDeleteDTO);

    /**
     * 新曾点位删除
     *
     * @Author: WuShuang on 2020/1/7  16:47
     * @param: [map1]
     * @return: java.lang.Integer
     * @Description:
     */
    Integer deleteNewAreYou(@Param("map") List<HeatDTO.HeatMapDataDTO> sqq);

    /**
     * 新增点位查询
     *
     * @Author: WuShuang on 2020/1/7  16:38
     * @param: [heatMapDeleteDTO]
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.String>>
     * @Description:
     */
    List<Map<String, String>> selectNewAreYouByDel1(@Param("heatMapDeleteDTO") HeatDTO.HeatMapDeleteDTO heatMapDeleteDTO);

    /**
     * 新增点位查询
     *
     * @Author: WuShuang on 2020/1/7  17:07
     * @param: [heatDTO]
     * @return: java.util.List<com.pointcoil.dto.map.HeatDTO.HeatMapDataDTO>
     * @Description:
     */
    List<HeatDTO.HeatMapDataDTO> selectNewAreYou(@Param("heatDTO") HeatDTO heatDTO);


}
