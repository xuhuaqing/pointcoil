package com.pointcoil.mapper;

import com.pointcoil.dto.map.BusinessZoneDTO;
import com.pointcoil.entity.UserEntity;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by WuShuang on 2019/11/5.
 */
public interface MapBusinessZoneMapper {

    /**
     * 创建了多少商圈
     *
     * @Author: WuShuang on 2019/11/5  21:22
     * @param: [userId]
     * @return: java.lang.Integer
     * @Description:
     */
    Integer selectCreateBusiness(@Param("userId") String userId);

    /**
     * 存入数据库
     *
     * @Author: WuShuang on 2019/11/6  9:22
     * @param: [businessZoneDTO]
     * @return: void
     * @Description:
     */
    void insertBusinessZone(@org.apache.ibatis.annotations.Param("businessZoneDTO") BusinessZoneDTO businessZoneDTO, @org.apache.ibatis.annotations.Param("s") String s, @org.apache.ibatis.annotations.Param("replace1") String replace1);

    /**
     * 查询商圈
     *
     * @Author: WuShuang on 2019/11/7  14:27
     * @param: [showBusinessZoneDTO]
     * @return: java.util.List<com.pointcoil.dto.map.BusinessZoneDTO.SBusinessZoneDTO>
     * @Description:
     */
    List<BusinessZoneDTO.SBusinessZoneDTO> showBusinessZone(@org.apache.ibatis.annotations.Param("showBusinessZoneDTO") BusinessZoneDTO.ShowBusinessZoneDTO showBusinessZoneDTO);

    /**
     * 商圈初始化
     *
     * @Author: WuShuang on 2019/11/8  9:46
     * @param: [brandId]
     * @return: java.util.List<com.pointcoil.dto.map.BusinessZoneDTO.SBusinessZoneDTO>
     * @Description:
     */
    List<BusinessZoneDTO.SBusinessZoneDTO> showBusinessZoneInit(@org.apache.ibatis.annotations.Param("brandId") String brandId,@org.apache.ibatis.annotations.Param("cityName")  String cityName);

    /**
     * 报告
     *
     * @Author: WuShuang on 2019/11/26  11:08
     * @param: [businessReport]
     * @return: java.util.List<java.util.Map < j a va.lang.String, j a va.lang.Object>>
     * @Description:
     */
    Map<String, Object> showBusinessReport(@org.apache.ibatis.annotations.Param("businessReport") BusinessZoneDTO.BusinessReport businessReport);

    /**
     * 次数+1
     *
     * @Author: WuShuang on 2019/11/30  14:24
     * @param: [reportId]
     * @return: void
     * @Description:
     */
    void updateBusinessCount(@org.apache.ibatis.annotations.Param("reportId") String reportId);

    /**
     * 查看次数 +1
     *
     * @Author: WuShuang on 2019/11/30  14:30
     * @param: [reportId]
     * @return: void
     * @Description:
     */
    void businessCountByLook(@org.apache.ibatis.annotations.Param("reportId") String reportId);

    /**
     * 保存记录
     *
     * @Author: WuShuang on 2019/11/30  14:38
     * @param: [businessReport]
     * @return: void
     * @Description:
     */
    void insertBusinessRecord(@org.apache.ibatis.annotations.Param("businessReport") BusinessZoneDTO.BusinessReportApi businessReport, @org.apache.ibatis.annotations.Param("reportId") String reportId);

    /**
     * 查询是否第一次关注
     *
     * @Author: WuShuang on 2019/12/2  9:14
     * @param: [businessReport]
     * @return: java.lang.Integer
     * @Description:
     */
    Integer isFollow(@org.apache.ibatis.annotations.Param("openId") String openId, @org.apache.ibatis.annotations.Param("reportId") String businessReport);

    /**
     * 我的关注商圈
     *
     * @Author: WuShuang on 2019/12/2  9:45
     * @param: [userEntity]
     * @return: java.util.Map<java.lang.String, j a va.lang.String>
     * @Description:
     */
    List<Map<String, String>> showMyFollowBusiness(@org.apache.ibatis.annotations.Param("userEntity") UserEntity userEntity);

    /**
     * 查看关注商圈的人
     *
     * @Author: WuShuang on 2019/12/2  13:23
     * @param: [businessReport]
     * @return: java.util.Map<java.lang.St ng, j a va.lang.String>
     * @Description:
     */
    List<Map<String, String>> businessFollowPeo(@org.apache.ibatis.annotations.Param("businessReport") BusinessZoneDTO.BusinessReportApi businessReport);

    /**
     * 回显商圈数据
     *
     * @Author: WuShuang on 2019/12/5  18:52
     * @param: [showBusinessById]
     * @return: com.pointcoil.dto.map.BusinessZoneDTO.SBusinessZoneDTO
     * @Description:
     */
    List<BusinessZoneDTO.UpdateBusiness> showBusinessZoneById(@org.apache.ibatis.annotations.Param("showBusinessById") BusinessZoneDTO.ShowBusinessById showBusinessById);

    void upd();

    /**
     * 修改商圈
     *
     * @Author: WuShuang on 2019/12/6  9:52
     * @param: [updateBusiness]
     * @return: void
     * @Description:
     */
    void updateBusinessZone(@org.apache.ibatis.annotations.Param("updateBusiness") BusinessZoneDTO.UpdateBusiness updateBusiness);

    /**
     * 区的地图缩放
     *
     * @Author: WuShuang on 2019/12/10  16:56
     * @param: [businessAddress]
     * @return: java.util.List<com.pointcoil.dto.map.BusinessZoneDTO.ZoomBusinessDTO>
     * @Description:
     */
    List<BusinessZoneDTO.ZoomBusinessDTO> zoomBusiness(@org.apache.ibatis.annotations.Param("businessAddress") BusinessZoneDTO.ZoomBusiness businessAddress);

    /**
     * 市的地图缩放
     *
     * @Author: WuShuang on 2019/12/10  16:58
     * @param: [businessAddress]
     * @return: java.util.List<com.pointcoil.dto.map.BusinessZoneDTO.ZoomBusinessDTO>
     * @Description:
     */
    List<BusinessZoneDTO.ZoomBusinessDTO> zoomBusinessBy20(@org.apache.ibatis.annotations.Param("businessAddress") BusinessZoneDTO.ZoomBusiness businessAddress, @org.apache.ibatis.annotations.Param("i") String i);

    /**
     * 查询出 属于那个省
     *
     * @Author: WuShuang on 2019/12/11  13:28
     * @param: [city]
     * @return: java.lang.String
     * @Description:
     */
    String selectProvince(@org.apache.ibatis.annotations.Param("city") String city);

    /**
     * 查询定制商圈
     *
     * @Author: WuShuang on 2019/12/12  17:41
     * @param: [brandId]
     * @return: java.util.List<com.pointcoil.dto.map.BusinessZoneDTO.SBusinessZoneDTO>
     * @Description:
     */
    List<BusinessZoneDTO.I1> selectCustomized(@org.apache.ibatis.annotations.Param("brandId") String brandId);

    /**
     * 删除定制id
     *
     * @Author: WuShuang on 2019/12/13  14:21
     * @param: [customizedId]
     * @return: void
     * @Description:
     */
    void deleteCustomized(@org.apache.ibatis.annotations.Param("customizedId") String customizedId);

    void updThermodynamicChart(@org.apache.ibatis.annotations.Param("pictureChar") BusinessZoneDTO.PictureChar pictureChar);

    /**
     * @Author: WuShuang on 2020/1/3  17:53
     * @param: [pictureChar]
     * @return: void
     * @Description:
     */
    void updateBusiness(@org.apache.ibatis.annotations.Param("pictureChar") BusinessZoneDTO.PictureChar pictureChar);

    /**
     * 删除商圈
     *
     * @Author: WuShuang on 2020/1/9  16:43
     * @param: [businessId]
     * @return: void
     * @Description:
     */
    void deleteBusiness(@org.apache.ibatis.annotations.Param("businessId") String businessId);

    /**
     * 查询商圈状态
     *
     * @param reportId
     * @return java.lang.Integer
     * @methodName selectBusinessOpen
     * @author WuShunag
     * @date 17:19
     */
    Integer selectBusinessOpen(@org.apache.ibatis.annotations.Param("reportId") String reportId);

    /**
     * 查询id
     *
     * @Author: WuShuang on 2020/3/23  18:19
     * @param: [reportId]
     * @return: java.lang.String
     * @Description:
     */
    String selectReportId(@org.apache.ibatis.annotations.Param("reportId") String reportId);
}
