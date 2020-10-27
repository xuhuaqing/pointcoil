package com.pointcoil.mapper;

import com.alibaba.fastjson.JSONObject;
import com.pointcoil.dto.map.*;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by WuShuang on 2019/11/4.
 */
public interface MapBusinessMapper {


    /**
     * 全部行业
     *
     * @Author: WuShuang on 2019/11/4  11:47
     * @param: []
     * @return: java.util.List<com.pointcoil.dto.map.MapIndustryDTO>
     * @Description:
     */
    List<MapIndustryDTO> industryInit();

    List<MapIndustryDTO.IndustryLevelTwo> selectLevelTwoIndustry(@Param("industryId") String industryId);

    /**
     * 创建品牌
     *
     * @Author: WuShuang on 2019/11/4  13:59
     * @param: [brandDTO]
     * @return: void
     * @Description:
     */
    void addBrand(@Param("brandDTO") BrandDTO brandDTO);

    /**
     * 修改品牌
     *
     * @Author: WuShuang on 2019/11/4  14:36
     * @param: [brandDTO]
     * @return: void
     * @Description:
     */
    void updateBrand(@Param("brandDTO") BrandDTO.UpdateBrandDTO brandDTO);

    /**
     * 回显要修改的
     *
     * @Author: WuShuang on 2019/11/4  14:53
     * @param: [brandDTO]
     * @return: com.pointcoil.dto.map.BrandDTO
     * @Description:
     */
    BrandDTO.SelectBrandDTO selectBrand(@Param("brandDTO") BrandDTO.BrandId brandDTO);


    String selectIndustryName(@Param("industryId") String industryId);

    /**
     * 展示商圈首页
     *
     * @Author: WuShuang on 2019/11/4  15:45
     * @param: [mapCommonDTO]
     * @return: java.util.List<com.pointcoil.dto.map.BrandDTO.BrandInit>
     * @Description:
     */
    List<BrandDTO.BrandInit> businessInit(@Param("mapCommonDTO") MapCommonDTO mapCommonDTO);

    /**
     * 删除商圈
     *
     * @Author: WuShuang on 2019/11/4  16:47
     * @param: [brandDTO]
     * @return: void
     * @Description:
     */
    void deleteBrand(@Param("brandDTO") BrandDTO.BrandId brandDTO);

    /**
     * 查询建了多少商圈
     *
     * @Author: WuShuang on 2019/11/4  17:21
     * @param: [userId]
     * @return: java.lang.Integer
     * @Description:
     */
    Integer selectCreateBrand(@Param("userId") String userId);

    /**
     * 查询能建多少个
     *
     * @Author: WuShuang on 2019/11/4  20:22
     * @param: [memberLevel]
     * @return: java.lang.Integer
     * @Description:
     */
    MemberDTO selectCanCreateBrand(@Param("memberLevel") String memberLevel);

    /**
     * 拿城市数据
     *
     * @Author: WuShuang on 2019/11/20  13:36
     * @param: [city]
     * @return: com.pointcoil.dto.map.CityExcelDTO
     * @Description:
     */
    CityExcelDTO selectCityData(@Param("city") String city);

    /**
     * 添加数据库
     *
     * @Author: WuShuang on 2019/11/25  17:23
     * @param: [jsonObject]
     * @return: void
     * @Description:
     */
    Integer createPresentation(@Param("replace1") String replace1, @Param("jsonObject") JSONObject jsonObject);

    /**
     * 过滤一级
     *
     * @Author: WuShuang on 2019/12/6  16:16
     * @param: [oneIndustry]
     * @return: java.lang.String
     * @Description:
     */
    String screenIndustry(@Param("oneIndustry") String oneIndustry);

    /**
     * 查询品牌的一级行业
     *
     * @Author: WuShuang on 2019/12/9  10:36
     * @param: [brandId]
     * @return: java.lang.String
     * @Description:
     */
    String selectBrandByIndustry(@Param("brandId") String brandId);

    /**
     * 二级行业筛选
     *
     * @Author: WuShuang on 2019/12/9  13:59
     * @param: [pointInPolygon, i]
     * @return: java.util.List<java.lang.String>
     * @Description:
     */
    String searchTwoIndustry(@Param("pointInPolygon") String pointInPolygon, @Param("i") String i);

    /**
     * 查询定制商圈
     *
     * @Author: WuShuang on 2019/12/12  17:41
     * @param: [brandId]
     * @return: java.util.List<com.pointcoil.dto.map.BusinessZoneDTO.SBusinessZoneDTO>
     * @Description:
     */
    List<BusinessZoneDTO.SBusinessZoneDTO> selectCustomized(@Param("brandId") String brandId, @Param("cityName") String cityName);

    /**
     * 判断是不是父账号
     *
     * @Author: WuShuang on 2019/12/24  11:13
     * @param: [accountPhone]
     * @return: java.lang.Integer
     * @Description:
     */
    Integer isParent(@Param("accountPhone") String accountPhone);

    /**
     * 删除品牌下的商圈
     *
     * @param brandDTO
     * @return void
     * @methodName deleteBusiness
     * @author WuShunag
     * @date 16:09
     */
    void deleteBusiness(@Param("brandDTO") BrandDTO.BrandId brandDTO);

    /**
     * 删除扫码的小程序报告
     *
     * @param brandDTO
     * @return void
     * @methodName deleteReportUser
     * @author WuShunag
     * @date 16:12
     */
    void deleteReportUser(@Param("brandDTO") BrandDTO.BrandId brandDTO);

    /**
     * 删除报告
     *
     * @param brandDTO
     * @return void
     * @methodName deleteReport
     * @author WuShunag
     * @date 16:16
     */
    void deleteReport(@Param("brandDTO") BrandDTO.BrandId brandDTO);

    /**
     * qrCode地址
     *
     * @Author: WuShuang on 2020/3/23  18:07
     * @param: [codeId, replace1, sonId, userId]
     * @return: void
     * @Description:
     */
    void insertQRcodeParam(@Param("codeId") String codeId, @Param("replace1") String replace1, @Param("sonId") String sonId, @Param("userId") String userId);

    /**
     * 查询appId
     *
     * @Author: WuShuang on 2020/3/25  16:21
     * @param: [userId]
     * @return: java.util.Map<java.lang.String , java.lang.String>
     * @Description:
     */
    Map<String, String> selectAppId(String userId);

    /**
     * 查询城市价格
     *
     * @param
     * @return java.util.List<java.math.BigDecimal>
     * @methodName selectPrice
     * @author WuShunag
     * @date 22:35
     */
    List<BigDecimal> selectPrice();
}
