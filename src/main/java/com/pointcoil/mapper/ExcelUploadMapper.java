package com.pointcoil.mapper;

import com.pointcoil.dto.map.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by WuShuang on 2019/11/13.
 */
public interface ExcelUploadMapper {


    /**
     * 添加百度excel
     *
     * @param s
     * @Author: WuShuang on 2019/11/13  16:09
     * @param: [s]
     * @return: void
     * @Description:
     */
    void addBaidu(@Param("s") List<BaiDuDTO> s);

    /**
     * 店铺样式
     *
     * @Author: WuShuang on 2019/11/14  13:22
     * @param: [list]
     * @return: void
     * @Description:
     */
    void addShop(@Param("list") List<ShopDTO> list);

    /**
     * 饿了吗数据
     *
     * @Author: WuShuang on 2019/11/14  15:09
     * @param: [list]
     * @return: void
     * @Description:
     */
    void addAreYouHungry(@Param("list") List<AreYouHungryDTO> list, @Param("cityName") String cityName);

    /**
     * 添加房天下
     *
     * @Author: WuShuang on 2019/11/15  10:10
     * @param: [list]
     * @return: void
     * @Description:
     */
    void addHousingWorld(@Param("list") List<HousingWorldDTO> list);

    /**
     * 房天下商铺数据
     *
     * @Author: WuShuang on 2019/11/15  11:16
     * @param: [list]
     * @return: void
     * @Description:
     */
    void addShopHousingWorld(@Param("list") List<ShopHousingWorldDTO> list);

    /**
     * 高德地图数据
     *
     * @Author: WuShuang on 2019/11/15  13:31
     * @param: [list]
     * @return: void
     * @Description:
     */
    void addGould(@Param("list") List<GouldDTO> list);

    /**
     * 链接二手房数据
     *
     * @Author: WuShuang on 2019/11/15  15:52
     * @param: [list]
     * @return: void
     * @Description:
     */
    void addChainFamilyTwoRoom(@Param("list") List<ChainFamilyTwoRoomDTO> list);

    /**
     * 添加 链接小区数据
     *
     * @Author: WuShuang on 2019/11/15  16:49
     * @param: [list]
     * @return: void
     * @Description:
     */
    void addChainFamily(@Param("list") List<ChainFamilyDTO> list, @Param("city") String city);

    /**
     * 链家小区新房
     *
     * @Author: WuShuang on 2019/11/16  9:34
     * @param: [list]
     * @return: void
     * @Description:
     */
    void addChainFamilyNewHouseUpload(@Param("list") List<ChainFamilyNewHouseDTO> list);

    /**
     * 链家 成交数据
     *
     * @Author: WuShuang on 2019/11/16  11:22
     * @param: [list]
     * @return: void
     * @Description:
     */
    void addChainFamilyTurnoverUpload(@Param("list") List<ChainFamilyTurnoverDTO> list);

    /**
     * 美团外卖 数据
     *
     * @Author: WuShuang on 2019/11/16  14:06
     * @param: [list]
     * @return: void
     * @Description:
     */
    void addMeiTuanWMUpload(@Param("list") List<MeiTuanWmDTO> list);

    /**
     * 城市添加
     *
     * @Author: WuShuang on 2019/11/20  11:42
     * @param: [list]
     * @return: void
     * @Description:
     */
    void addCityExcelUpload(@Param("list") List<CityExcelDTO> list);

    /**
     * 热力图数据
     *
     * @Author: WuShuang on 2019/11/27  10:19
     * @param: [list]
     * @return: void
     * @Description:
     */
    void uploadCityThermodynamic(@Param("list") List<ThermodynamicDTO> list, @Param("cityName") String cityName);

    /**
     * 插入数据
     *
     * @Author: WuShuang on 2019/12/6  13:42
     * @param: [list]
     * @return: void
     * @Description:
     */
    void industryUpload(@Param("list") List<IndustryDTO.IndustryBrandList> list);

    /**
     * 二级行业数据插入
     *
     * @Author: WuShuang on 2019/12/6  14:20
     * @param: [list]
     * @return: void
     * @Description:
     */
    void twoIndustryUpload(@Param("list") List<IndustryDTO.IndustryBrandList> list);

    /**
     * s
     *
     * @Author: WuShuang on 2019/12/6  16:01
     * @param: [list]
     * @return: void
     * @Description:
     */
    void twoIndustryUpload1(@Param("list") List<IndustryDTO.IndustryBrandList> list);

    /**
     * @Author: WuShuang on 2019/12/20  10:27
     * @param: [cityName]
     * @return: java.lang.Integer
     * @Description:
     */
    Integer isCount(@Param("cityName") String cityName);

    /**
     * shacnhu
     *
     * @Author: WuShuang on 2019/12/20  10:31
     * @param: [cityName]
     * @return: void
     * @Description:
     */
    void deleteThermodynamic(@Param("cityName") String cityName);

    Integer isChainCount(@Param("city") String city);

    /**
     * shanchu
     *
     * @Author: WuShuang on 2019/12/20  14:21
     * @param: [city]
     * @return: void
     * @Description:
     */
    void deleteChainData(@Param("city") String city);

    Integer isGouldCount(@Param("city") String cityName);

    void deleteGouldData(@Param("city") String cityName);

    Integer isAreyouHungrry(@Param("city") String cityName);

    void deleteAreyouHungry(@Param("city") String cityName);

    void uploadThermodynamicByUser(@Param("list") List<GouldDTO> areYouHungryDTOS, @Param("type") String type, @Param("userId") String userId);
}
