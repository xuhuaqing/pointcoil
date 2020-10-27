package com.pointcoil.service.map;

import com.pointcoil.dto.map.BrandDTO;
import com.pointcoil.dto.map.LabelDTO;
import com.pointcoil.dto.map.MapCommonDTO;

/**
 * Created by WuShuang on 2019/11/8.
 */
public interface MapLabelService {
    /**
     *     *创建标签时回显的商圈名称
     *
     * @Author: WuShuang on 2019/11/11  9:20
     * @param: [mapCommonDTO]
     * @return: java.lang.String
     * @Description:
     */
    String createLabelShowBusinessName(BrandDTO.BrandId mapCommonDTO);

    /**
     * 标签展示
     *
     * @Author: WuShuang on 2019/11/11  11:30
     * @param: [mapCommonDTO]
     * @return: java.lang.String
     * @Description:
     */
    String showLabelStyle(MapCommonDTO mapCommonDTO);

    /**
     *创建标签
     *
     * @Author: WuShuang on 2019/11/11  13:27
     * @param: [labelDTO]
     * @return: java.lang.String
     * @Description:
     */
    String createLabel(LabelDTO labelDTO);

    /**
     * 展示标签
     *
     * @Author: WuShuang on 2019/11/11  15:18
     * @param: [showLabel]
     * @return: java.lang.String
     * @Description:
     */
    String showLabel(LabelDTO.ShowLabel showLabel);

    /**
     * 删除标签
     *
     * @Author: WuShuang on 2019/11/11  17:29
     * @param: [labelId]
     * @return: java.lang.String
     * @Description:
     */
    String deleteLabel(LabelDTO.LabelId labelId);

    /**
     *修改前的 回显数据
     *
     * @Author: WuShuang on 2019/11/11  17:34
     * @param: [labelId]
     * @return: java.lang.String
     * @Description:
     */
    String selectLabelById(LabelDTO.LabelId labelId);

    /**
     *修改标签
     *
     * @Author: WuShuang on 2019/11/11  17:57
     * @param: [updateLabel]
     * @return: java.lang.String
     * @Description:
     */
    String updateLabelById(LabelDTO.UpdateLabel updateLabel);

    String clear(LabelDTO labelDTO);
}
