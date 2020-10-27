package com.pointcoil.mapper;

import com.pointcoil.dto.map.BrandDTO;
import com.pointcoil.dto.map.LabelDTO;
import com.pointcoil.dto.map.LabelStyleDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by WuShuang on 2019/11/8.
 */
public interface MapLabelMapper {

    /**
     * 展示表情名
     *
     * @Author: WuShuang on 2019/11/11  10:56
     * @param: [mapCommonDTO]
     * @return: java.util.List<java.lang.String>
     * @Description:
     */
    List<Map<String, Object>> createLabelShowBusinessName(@Param("mapCommonDTO") BrandDTO.BrandId mapCommonDTO);

    /**
     * 标签展示
     *
     * @Author: WuShuang on 2019/11/11  11:34
     * @param: []
     * @return: com.pointcoil.dto.map.LabelStyleDTO
     * @Description:
     */
    List<LabelStyleDTO> showLabelStyle();

    /**
     * 创建了多少标签
     *
     * @Author: WuShuang on 2019/11/11  13:32
     * @param: [labelDTO]
     * @return: java.lang.Integer
     * @Description:
     */
    Integer selectCreateLabelNum(@Param("labelDTO") LabelDTO labelDTO);

    /**
     * 创建标签
     *
     * @Author: WuShuang on 2019/11/11  13:48
     * @param: [labelDTO, toJSONString]
     * @return: void
     * @Description:
     */
    void createLabel(@Param("labelDTO") LabelDTO labelDTO, @Param("toJSONString") String toJSONString, @Param("replace") String replace);

    /**
     * 展示所有的标签
     *
     * @Author: WuShuang on 2019/11/11  15:50
     * @param: [showLabel]
     * @return: java.util.List<com.pointcoil.dto.map.LabelDTO.ShowLabelDTO>
     * @Description:
     */
    List<LabelDTO.ShowLabelDTO> showLabel(@Param("showLabel") LabelDTO.ShowLabel showLabel);

    /**
     * 删除标签
     *
     * @Author: WuShuang on 2019/11/11  17:30
     * @param: [labelId]
     * @return: void
     * @Description:
     */
    void deleteLabel(@Param("labelId") LabelDTO.LabelId labelId);

    /**
     * 修改前的 数据回显
     *
     * @Author: WuShuang on 2019/11/11  17:41
     * @param: [labelId]
     * @return: com.pointcoil.dto.map.LabelDTO.SelectLabelById
     * @Description:
     */
    LabelDTO.SelectLabelById selectLabelById(@Param("labelId") LabelDTO.LabelId labelId);

    /**
     * 修改标签
     *
     * @Author: WuShuang on 2019/11/11  17:58
     * @param: [updateLabel]
     * @return: void
     * @Description:
     */
    void updateLabelById(@Param("updateLabel") LabelDTO.UpdateLabel updateLabel);

    /**
     * 添加标签
     *
     * @Author: WuShuang on 2019/11/12  10:01
     * @param: [s]
     * @return: void
     * @Description:
     */
    void addLabel(@Param("s") String s);

    /**
     * 清空
     *
     * @Author: WuShuang on 2019/12/5  9:32
     * @param: []
     * @return: void
     * @Description:
     */
    void upd();

    /**
     * 添加表情
     *
     * @Author: WuShuang on 2019/12/14  14:31
     * @param: [carouselImageUrl]
     * @return: void
     * @Description:
     */
    void labelAdd(@Param("carouselImageUrl") String carouselImageUrl);

    void clear();

    /**
     * 查询样式
     *
     * @param labelStyleId
     * @return java.lang.String
     * @methodName selectLabelStyleById
     * @author WuShunag
     * @date 13:56
     */
    String selectLabelStyleById(@Param("labelStyleId") String labelStyleId);

    /**
     * 热力图
     *
     * @Author: WuShuang on 2020/4/3  11:05
     * @param: []
     * @return: java.util.List<java.util.Map <  j a va.lang.String,  j a va.lang.String>>
     * @Description:
     */
    List<Map<String, String>> selectHeatMap(@Param("id") String id);

    /**
     * 城市列表
     *
     * @Author: WuShuang on 2020/4/3  17:18
     * @param: []
     * @return: java.util.List<java.lang.String>
     * @Description:
     */
    List<String> selectCity();

    /**
     * 添加热力图
     *
     * @Author: WuShuang on 2020/4/3  17:21
     * @param: [m, s]
     * @return: void
     * @Description:
     */
    void insertRule(@Param("m") Map<String, String> m, @Param("s") List<String> s, @Param("id") String id);

    /**
     * 判断是否重名
     *
     * @param labelDTO
     * @return java.lang.Integer
     * @methodName selectLabelName
     * @author WuShunag
     * @date 22:53
     */
    Integer selectLabelName(@Param("labelDTO") LabelDTO labelDTO);
}
