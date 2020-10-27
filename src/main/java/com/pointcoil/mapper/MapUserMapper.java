package com.pointcoil.mapper;

import com.github.pagehelper.Page;
import com.pointcoil.dto.map.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by WuShuang on 2019/11/1.
 */
public interface MapUserMapper {
    /**
     * 注册用户
     *
     * @Author: WuShuang on 2019/11/1  16:59
     * @param: [registerDTO]
     * @return: void
     * @Description:
     */
    void register(@Param("registerDTO") RegisterDTO registerDTO);

    /**
     * 登陆
     *
     * @Author: WuShuang on 2019/11/4  9:41
     * @param: [loginDTO]
     * @return: java.lang.String
     * @Description:
     */
    MapCommonDTO login(@Param("loginDTO") ISPhoneDTO.LoginDTO loginDTO);

    /**
     * 判断是否注册
     *
     * @Author: WuShuang on 2019/11/4  9:47
     * @param: [registerDTO]
     * @return: java.lang.Integer
     * @Description:
     */
    Integer isRegister(@Param("registerDTO") RegisterDTO registerDTO);

    /**
     * 会员中心
     *
     * @Author: WuShuang on 2019/11/4  20:40
     * @param: []
     * @return: java.util.List<com.pointcoil.dto.map.MemberDTO>
     * @Description:
     */
    List<MemberDTO> selectMember();

    /**
     * 我的商圈
     *
     * @Author: WuShuang on 2019/11/18  13:19
     * @param: [mapCommonDTO]
     * @return: java.util.List<com.pointcoil.dto.map.MyBusinessZoneDTO.MyBusinessZone>
     * @Description:
     */
    Page<MyBusinessZoneDTO.MyBusinessZone> myBusinessZone(@Param("mapCommonDTO") MapPageDTO mapCommonDTO);

    /**
     * 我的商圈城市
     *
     * @Author: WuShuang on 2019/11/18  13:23
     * @param: [mapCommonDTO]
     * @return: java.util.List<com.pointcoil.dto.map.MyBusinessZoneDTO.MyBusinessZoneCity>
     * @Description:
     */
    List<MyBusinessZoneDTO.MyBusinessZoneCity> myBusinessZoneCity(@Param("mapCommonDTO") MapPageDTO mapCommonDTO);

    /**
     * 创建子账号
     *
     * @Author: WuShuang on 2019/11/18  14:46
     * @param: [mapSubAccountDTO]
     * @return: void
     * @Description:
     */
    void createSubAccount(@Param("mapSubAccountDTO") MapSubAccountDTO mapSubAccountDTO, @Param("timeDTO") TimeDTO timeDTO);

    /**
     * 我的子账号
     *
     * @Author: WuShuang on 2019/11/18  16:01
     * @param: [mapSubAccountDTO]
     * @return: java.util.List<com.pointcoil.dto.map.MapSubAccountDTO.SubAccountDTO>
     * @Description:
     */
    Page<MapSubAccountDTO.SubAccountDTO> selectMySubAccount(@Param("mapSubAccountDTO") MapPageDTO mapSubAccountDTO);

    /**
     * 删除子账号
     *
     * @Author: WuShuang on 2019/11/18  16:25
     * @param: [subAccountDTO]
     * @return: void
     * @Description:
     */
    void deleteSubAccount(@Param("subAccountDTO") MapPageDTO.SubAccountDTO subAccountDTO);

    /**
     * 添加定制服务
     *
     * @Author: WuShuang on 2019/11/18  18:18
     * @param: [customizedServiceDTO]
     * @return: void
     * @Description:
     */
    void customizedService(@Param("customizedServiceDTO") CustomizedServiceDTO customizedServiceDTO);

    /**
     * 我的关注
     *
     * @Author: WuShuang on 2019/12/2  16:27
     * @param: [mapCommonDTO]
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.String>>
     * @Description:
     */
    Page<Map<String, String>> myConcern(@Param("mapCommonDTO") MapPageDTO mapCommonDTO);

    /**
     * 女性多少
     *
     * @Author: WuShuang on 2019/12/3  9:51
     * @param: [mapCommonDTO]
     * @return: java.lang.Integer
     * @Description:
     */
    Map<String, String> pieChartByWoMan(@Param("mapCommonDTO") BrandDTO.BrandId mapCommonDTO);

    /**
     * 男性多少
     *
     * @Author: WuShuang on 2019/12/3  9:51
     * @param: [mapCommonDTO]
     * @return: java.lang.Integer
     * @Description:
     */
    Map<String, String> pieChartByMan(@Param("mapCommonDTO") BrandDTO.BrandId mapCommonDTO);

    /**
     * 查询城市 和数量
     *
     * @Author: WuShuang on 2019/12/3  16:01
     * @param: [mapCommonDTO]
     * @return: java.util.List<com.pointcoil.dto.map.DistributionMapDTO>
     * @Description:
     */
    List<DistributionMapDTO> distributionMap(@Param("mapCommonDTO") BrandDTO.BrandId mapCommonDTO);

    /**
     * 城市地理位置
     *
     * @Author: WuShuang on 2019/12/3  16:08
     * @param: [name]
     * @return: java.util.Map<java.lang.String, java.util.List>
     * @Description:
     */
    DistributionMapDTO.DistributionMapCity selectCity(@Param("name") String name);

    /**
     * 取出父类id
     *
     * @Author: WuShuang on 2019/12/4  14:15
     * @param: [userId]
     * @return: java.lang.String
     * @Description:
     */
    String selectParentId(@Param("userId") String userId);

    /**
     * 会员时间
     *
     * @Author: WuShuang on 2019/12/27  14:13
     * @param: [userId]
     * @return: java.util.Map<java.lang.String, java.time.LocalDateTime>
     * @Description:
     */
    TimeDTO selectTime(@Param("userId") String userId);

    /**
     * 删除子账号
     *
     * @Author: WuShuang on 2019/12/28  11:13
     * @param: [subAccountDTO]
     * @return: java.lang.String
     * @Description:
     */
    String selectAccountPhone(@Param("subAccountDTO") MapPageDTO.SubAccountDTO subAccountDTO);

    /**
     * 查询所有会员
     *
     * @Author: WuShuang on 2019/12/31  16:39
     * @param: []
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.String>>
     * @Description:
     */
    List<TaskDTO> selectAllUser();

    /**
     * 删除会员
     *
     * @Author: WuShuang on 2019/12/31  17:03
     * @param: [id]
     * @return: void
     * @Description:
     */
    void deleteMembers(@Param("id") String id);

    /**
     * shijian
     *
     * @Author: WuShuang on 2020/1/2  14:15
     * @param: [userId]
     * @return: com.pointcoil.dto.map.TimeDTO
     * @Description:
     */
    TimeDTO selectMemberByUserId(@Param("userId") String userId);

    /**
     * 子账号的列表
     *
     * @Author: WuShuang on 2020/3/24  10:03
     * @param: [mapCommonDTO]
     * @return: com.github.pagehelper.Page<java.util.Map < java.lang.String, java.lang.String>>
     * @Description:
     */
    Page<Map<String, String>> myConcernBySonId(@Param("mapCommonDTO") MapPageDTO mapCommonDTO);

    /**
     * 更改用户等级 和 时间
     *
     * @Author: WuShuang on 2020/4/20  10:42
     * @param: [id, startTime, endTime]
     * @return: void
     * @Description:
     */
    void updateMember(@Param("id") String id, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     *  查询一线城市
     *
     * @param
     * @return java.util.List<java.lang.String>
     * @methodName findOneCity
     * @author WuShunag
     * @date 22:09
     */
    List<String> findOneCity(@Param("level") String level);

    /**
     *  城市列表
     *
     * @param
     * @return java.util.List<com.pointcoil.dto.map.CityDTO>
     * @methodName memberCityInit
     * @author WuShunag
     * @date 22:21
     */
    List<CityDTO> memberCityInit();

    /**
     *  根据城市查询
     *
     * @param
     * @return java.util.List<java.lang.String>
     * @methodName selectCityByProvince
     * @author WuShunag
     * @date 22:28
     */
    List<String> selectCityByProvince(@Param("province") String province);
}
