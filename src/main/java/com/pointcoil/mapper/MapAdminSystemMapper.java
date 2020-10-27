package com.pointcoil.mapper;

import com.github.pagehelper.Page;
import com.pointcoil.dto.map.*;
import com.pointcoil.entity.BusClick;
import com.pointcoil.entity.ProvincesEntity;
import com.pointcoil.entity.RuleEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

/**
 * Created by WuShuang on 2019/12/13.
 */
public interface MapAdminSystemMapper {
    /**
     * 查询一级
     *
     * @Author: WuShuang on 2019/12/13  17:17
     * @param: []
     * @return: com.github.pagehelper.Page<com.pointcoil.dto.map.MapIndustryDTO>
     * @Description:
     */
    Page<MapIndustryDTO> findIndustryOne();

    /**
     * 查询二级
     *
     * @Author: WuShuang on 2019/12/13  17:23
     * @param: [industryId]
     * @return: com.github.pagehelper.Page<com.pointcoil.dto.map.MapIndustryDTO.IndustryLevelTwo>
     * @Description:
     */
    Page<MapIndustryDTO.IndustryLevelTwo> findIndustryTwo(@Param("industryId") String industryId);

    /**
     * 添加 一二级
     *
     * @Author: WuShuang on 2019/12/14  9:47
     * @param: [industryName, parentId]
     * @return: void
     * @Description:
     */
    void addIndustry(@Param("industryName") String industryName, @Param("parentId") String parentId);

    /**
     * 删除行业
     *
     * @Author: WuShuang on 2019/12/14  13:16
     * @param: [industryName]
     * @return: void
     * @Description:
     */
    void deleteIndustry(@Param("industryName") String industryName);

    /**
     * 修改标签名称
     *
     * @Author: WuShuang on 2019/12/14  13:41
     * @param: [industryId, industryName]
     * @return: void
     * @Description:
     */
    void updIndustry(@Param("industryId") String industryId, @Param("industryName") String industryName);

    /**
     * 标签图片
     *
     * @Author: WuShuang on 2019/12/14  14:17
     * @param: []
     * @return: com.github.pagehelper.Page<com.pointcoil.dto.map.LabelStyleDTO>
     * @Description:
     */
    Page<LabelStyleDTO> findLabelImg();

    /**
     * 添加表情
     *
     * @Author: WuShuang on 2019/12/14  14:31
     * @param: [carouselImageUrl]
     * @return: void
     * @Description:
     */
    void labelAdd(@Param("carouselImageUrl") String carouselImageUrl);

    /**
     * shanchu
     *
     * @Author: WuShuang on 2019/12/14  14:35
     * @param: [labelStyleId]
     * @return: void
     * @Description:
     */
    void deleteLabel(@Param("labelStyleId") String labelStyleId);

    /**
     * 回显
     *
     * @Author: WuShuang on 2019/12/14  14:39
     * @param: [labelStyleId]
     * @return: com.pointcoil.dto.map.LabelStyleDTO
     * @Description:
     */
    LabelStyleDTO findLabelById(@Param("labelStyleId") String labelStyleId);

    /**
     * xiugai
     *
     * @Author: WuShuang on 2019/12/14  14:42
     * @param: [labelStyleId, carouselImageUrl]
     * @return: void
     * @Description:
     */
    void updLabel(@Param("labelStyleId") String labelStyleId, @Param("carouselImageUrl") String carouselImageUrl);

    /**
     * 修改密码
     *
     * @Author: WuShuang on 2019/12/16  9:46
     * @param: [oldPassword, password, rePassword]
     * @return: void
     * @Description:
     */
    void changePassword(@Param("password") String password);

    Integer selectOldPas(@Param("md5") String md5);

    /**
     * 清空缓存
     *
     * @Author: WuShuang on 2019/12/16  13:34
     * @param: []
     * @return: void
     * @Description:
     */
    void clear();

    /**
     * 会员中心
     *
     * @Author: WuShuang on 2019/12/16  13:54
     * @param: []
     * @return: void
     * @Description:
     */
    Page<MemberDTO> findMember();

    /**
     * 修改会员数据
     *
     * @Author: WuShuang on 2019/12/16  14:20
     * @param: [memberId, key, value]
     * @return: void
     * @Description:
     */
    void updateMember(@Param("memberId") String memberId, @Param("key") String key, @Param("value") String value);

    /**
     * 城市数据
     *
     * @param city
     * @Author: WuShuang on 2019/12/16  15:29
     * @param: []
     * @return: com.github.pagehelper.Page<com.pointcoil.dto.map.LabelStyleDTO>
     * @Description:
     */
    Page<ProvincesEntity> findThermodynamic(@Param("city") String city);

    /**
     * 查询热力图城市数据
     *
     * @Author: WuShuang on 2019/12/19  14:33
     * @param: [cityName]
     * @return: com.github.pagehelper.Page<com.pointcoil.dto.map.ThermodynamicDTO>
     * @Description:
     */
    Page<ThermodynamicDTO.ThermodynamicDTO1> findCityName(@Param("cityName") String cityName);

    /**
     * 饿了吗城市数据
     *
     * @Author: WuShuang on 2019/12/19  16:52
     * @param: [cityName]
     * @return: com.github.pagehelper.Page<com.pointcoil.dto.map.AreYouHungryDTO>
     * @Description:
     */
    Page<AreYouHungryDTO> findAreCityName(@Param("cityName") String cityName);

    /**
     * 链家数据
     *
     * @Author: WuShuang on 2019/12/20  13:54
     * @param: [cityName]
     * @return: com.github.pagehelper.Page<com.pointcoil.dto.map.ChainFamilyTurnoverDTO>
     * @Description:
     */
    Page<ChainFamilyDTO> findChainCityName(@Param("cityName") String cityName);

    /**
     * 查询规则
     *
     * @Author: WuShuang on 2019/12/20  16:22
     * @param: [cityName, type]
     * @return: com.github.pagehelper.Page<com.pointcoil.entity.RuleEntity>
     * @Description:
     */
    Page<RuleEntity> findRule(@Param("cityName") String cityName, @Param("type") String type);

    /**
     * 添加热力图规则
     *
     * @Author: WuShuang on 2019/12/23  10:47
     * @param: [ruleEntity]
     * @return: void
     * @Description:
     */
    void ruleAdd(@Param("ruleEntity") RuleEntity ruleEntity);

    /**
     * 导出excel
     *
     * @Author: WuShuang on 2019/12/23  19:11
     * @param: [businessId]
     * @return: java.util.List<com.pointcoil.entity.BusClick.ExportExcel>
     * @Description:
     */
    List<BusClick.ExportExcel> exportExcel(@Param("businessId") String[] businessId);

    /**
     * 高德数据
     *
     * @Author: WuShuang on 2019/12/30  13:57
     * @param: [cityName]
     * @return: com.github.pagehelper.Page<com.pointcoil.dto.map.GouldDTO>
     * @Description:
     */
    Page<GouldDTO> findGouldCityData(@Param("cityName") String cityName);

    /**
     * 查询用户信息
     *
     * @Author: WuShuang on 2020/3/11  16:49
     * @param: [id]
     * @return: java.util.List<com.pointcoil.entity.BusClick.ExportExcelByUser>
     * @Description:
     */
    List<BusClick.ExportExcelByUser> exportExcelByUser(@Param("id") String[] id);

    /**
     * 订单
     *
     * @Author: WuShuang on 2020/3/23  13:10
     * @param: [createTime]
     * @return: com.github.pagehelper.Page<com.pointcoil.dto.map.OrderDTO>
     * @Description:
     */
    Page<OrderDTO> order(@Param("createTime") String createTime);

    /**
     * 修改
     *
     * @Author: WuShuang on 2020/3/25  16:58
     * @param: [id, appId, secret]
     * @return: void
     * @Description:
     */
    void updateCustom(@Param("id") String id, @Param("appId") String appId, @Param("secret") String secret);

    /**
     * @Author: WuShuang on 2020/3/25  17:08
     * @param: [id]
     * @return: java.util.Map<java.lang.String ,   j   a   v a . l   a   n   g . S t   r i n g>   *   @ D e s c   r   i   p   t   i   o n :
     */
    Map<String, String> findCustomId(@Param("id") String id);

    /**
     * 修改
     *
     * @Author: WuShuang on 2020/4/14  16:23
     * @param: [id, firstClassClassification, twoLevelClassification, threeLevelClassification1]
     * @return: void
     * @Description:
     */
    void updateRule
    (@Param("id") String id, @Param("firstClassClassification") String firstClassClassification, @Param("twoLevelClassification") String twoLevelClassification, @Param("threeLevelClassification1") String threeLevelClassification1, @Param("minCount") String minCount);

    /**
     * 定制热力图
     *
     * @Author: WuShuang on 2020/4/17  11:08
     * @param: [id]
     * @return: java.util.List<java.util.Map     <       j   a        v   a   .   l   a   n       g   .   S   t   r   i   n   g   ,       j   a       v   a   .   l   a   n       g   .   S   t   r   i   n   g>               >               *               @       D       e       s       c               r               i               p               t               i               o          n   :
     */
    List<Map<String,String>> findThermodynamicByUserId(@Param("id") String id);

    /**
     * 查询城市
     *
     * @Author: WuShuang on 2020/4/17  11:24
     * @param: [cityName]
     * @return: java.util.List<java.lang.String>
     * @Description:
     */
    List<Map<String, String>> getSearchCity(@Param("cityName") String cityName);

    /**
     * 添加城市
     *
     * @Author: WuShuang on 2020/4/17  11:39
     * @param: [id, linkUrl]
     * @return: void
     * @Description:
     */
    void thermodynamicUserAdd(@Param("id") String id, @Param("linkUrl") List<String> linkUrl, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 删除添加城市
     *
     * @Author: WuShuang on 2020/4/20  10:06
     * @param: [id]
     * @return: void
     * @Description:
     */
    void deleteCity(@Param("id") String id);

    /**
     * 查询用户
     *
     * @Author: WuShuang on 2020/4/20  10:48
     * @param: [heatDTO]
     * @return: java.util.Map<java.lang.String   ,           j       a           v       a       .       l       a       n       g       .   Stri       n       g> * @Desc r i p t i o     n         : */
    List<String> isThermodynamicByUserId(@Param("heatDTO") HeatDTO heatDTO, @Param("city1") String city1);

    /**
     * 查询城市
     * * @Author: WuShuang on 2020/4/20  10:55
     *
     * @param: [id, linkUrl]
     * @return: java.lang.Integer
     * @Description:
     */
    Integer isCity(@Param("id") String id, @Param("linkUrl") String linkUrl);

    /**
     * 根据id 查询会员等级
     *
     * @Author: WuShuang on 2020/4/21  10:04
     * @param: [id]
     * @return: java.lang.Integer
     * @Description:
     */
    Integer findMemberById(@Param("id") String id);

    /**
     * 根据id 查询所有的城市名称
     *
     * @Author: WuShuang on 2020/4/21  16:09
     * @param: [cityName]
     * @return: java.util.List<java.lang.String>
     * @Description:
     */
    List<String> findCityNameById(@Param("cityName") String[] cityName);

    /**
     * 所有的城市
     *
     * @Author: WuShuang on 2020/4/21  16:40
     * @param: []
     * @return: java.util.List<java.lang.String>
     * @Description:
     */
    List<String> findCityNameAll();


    /**
     * 修改时间
     *
     * @Author: WuShuang on 2020/4/21  17:31
     * @param: [id, userId, startTime, endTime]
     * @return: void
     * @Description:
     */
    void updateMemberCityTime(@Param("id") String id, @Param("userId") String userId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 删除数据
     *
     * @param cityName
     * @return void
     * @methodName deleteCityData
     * @author WuShunag
     * @date 22:40
     */
    void deleteCityData(@Param("cityName") String cityName);

    /**
     * 删除用户
     *
     * @param id
     * @param userPhone
     * @return void
     * @methodName deleteUser
     * @author WuShunag
     * @date 22:01
     */
    void deleteUser(@Param("id") String id, @Param("userPhone") String userPhone);

    /**
     * 哪个省的
     *
     * @param city
     * @return java.lang.String
     * @methodName findProvinces
     * @author WuShunag
     * @date 0:29
     */
    String findProvinces(@Param("city") String city);

    /**
     * 查询全名
     *
     * @param city
     * @param provinces
     * @return java.lang.String
     * @methodName selectProvinces
     * @author WuShunag
     * @date 0:35
     */
    String selectProvinces(@Param("city") String city, @Param("provinces") String provinces);

    /**
     * 导出用户标签
     *
     * @param labelId
     * @return java.util.List<com.pointcoil.dto.map.LabelDTO>
     * @methodName exportExcelByLabel
     * @author WuShunag
     * @date 22:14
     */
    List<LabelDTO.SelectLabelById> exportExcelByLabel(@Param("labelId") String[] labelId);

    /**
     * 定制数据
     *
     * @param userId
     * @param type
     * @return com.github.pagehelper.Page<com.pointcoil.dto.map.GouldDTO>
     * @methodName findCustomizedThermodynamic
     * @author WuShunag
     * @date 1:32
     */
    Page<GouldDTO> findCustomizedThermodynamic(@Param("userId") String userId, @Param("type") String type);

    /** 删除定制热力图
     *
     *
     * @param userId
    * @param type
     * @return void
     * @methodName deleteCustomizedThermodynamic
     * @author WuShunag
     * @date 23:36
     */
    void deleteCustomizedThermodynamic(@Param("userId")String userId, @Param("type")String type);
}
