package com.pointcoil.service.adminmap;

import com.pointcoil.dto.admin.AdminPageCommon;
import com.pointcoil.entity.RuleEntity;
import com.pointcoil.util.Response;

/**
 * Created by WuShuang on 2019/12/13.
 */
public interface MapAdminSystemService {

    /**
     * 查询一级行业
     *
     * @Author: WuShuang on 2019/12/13  16:48
     * @param: [adminPageCommon]
     * @return: java.lang.String
     * @Description:
     */
    String findIndustryOne(AdminPageCommon adminPageCommon);

    /**
     * 查询二级行业
     *
     * @Author: WuShuang on 2019/12/13  17:22
     * @param: [adminPageCommon, industryId]
     * @return: java.lang.String
     * @Description:
     */
    String findIndustryTwo(AdminPageCommon adminPageCommon, String industryId);

    /**
     *
     *
     * @Author: WuShuang on 2019/12/14  9:46
     * @param: [industryName, parentId]
     * @return: java.lang.String
     * @Description:
     */
    String addIndustry(String industryName, String parentId);

    /**
     * 删除行业
     *
     * @Author: WuShuang on 2019/12/14  13:16
     * @param: [industryName]
     * @return: java.lang.String
     * @Description:
     */
    String deleteIndustry(String industryName);

    /**
     * 修改
     *
     * @Author: WuShuang on 2019/12/14  13:40
     * @param: [industryId, industryName]
     * @return: java.lang.String
     * @Description:
     */
    String updIndustry(String industryId, String industryName);

    /**
     * 标签图片
     *
     * @Author: WuShuang on 2019/12/14  14:15
     * @param: [adminPageCommon]
     * @return: java.lang.String
     * @Description:
     */
    String findLabelImg(AdminPageCommon adminPageCommon);

    /**
     * 添加标签样式
     *
     * @Author: WuShuang on 2019/12/14  14:31
     * @param: [carouselImageUrl]
     * @return: java.lang.String
     * @Description:
     */
    String labelAdd(String carouselImageUrl);

    /**
     * 删除
     *
     * @Author: WuShuang on 2019/12/14  14:35
     * @param: [labelStyleId]
     * @return: java.lang.String
     * @Description:
     */
    String deleteLabel(String labelStyleId);

    /**
     * genju id
     *
     * @Author: WuShuang on 2019/12/14  14:39
     * @param: [labelStyleId]
     * @return: java.lang.String
     * @Description:
     */
    String findLabelById(String labelStyleId);

    /**
     *xiugai
     *
     * @Author: WuShuang on 2019/12/14  14:42
     * @param: [labelStyleId, carouselImageUrl]
     * @return: java.lang.String
     * @Description:
     */
    String updLabel(String labelStyleId, String carouselImageUrl);

    /**
     *修改密码
     *
     * @Author: WuShuang on 2019/12/16  9:46
     * @param: [oldPassword, password, rePassword]
     * @return: java.lang.String
     * @Description:
     */
    String changePassword(String oldPassword, String password, String rePassword);

    /**
     * 会员中心
     *
     * @Author: WuShuang on 2019/12/16  13:53
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    String findMember();

    /**
     * 修改会员数据
     *
     * @Author: WuShuang on 2019/12/16  14:18
     * @param: [memberId, key, value]
     * @return: java.lang.String
     * @Description:
     */
    String updateMember(String memberId, String key, String value);

    /**
     * 点评杭州热力图数据
     *
     * @Author: WuShuang on 2019/12/16  15:28
     * @param: [adminPageCommon]
     * @return: java.lang.String
     * @Description:
     */
    String findThermodynamic(AdminPageCommon adminPageCommon, String city);

    /**
     *
     *
     * @Author: WuShuang on 2019/12/19  14:32
     * @param: [adminPageCommon, cityName]
     * @return: java.lang.String
     * @Description:
     */
    String findCityName(AdminPageCommon adminPageCommon, String cityName);

    /**
     * 饿了吗城市数据
     *
     * @Author: WuShuang on 2019/12/19  16:51
     * @param: [adminPageCommon, cityName]
     * @return: java.lang.String
     * @Description:
     */
    String findAreCityName(AdminPageCommon adminPageCommon, String cityName);

    /**
     * 链家数据
     *
     * @Author: WuShuang on 2019/12/20  13:50
     * @param: [adminPageCommon, cityName]
     * @return: java.lang.String
     * @Description:
     */
    String findChainCityName(AdminPageCommon adminPageCommon, String cityName);

    /**
     * 添加规则
     *
     * @Author: WuShuang on 2019/12/20  15:53
     * @param: []
     * @return: java.lang.String
     * @Description:
     * @param adminPageCommon
     * @param cityName
     * @param type
     */
    String findRule(AdminPageCommon adminPageCommon, String cityName, String type);

    /**
     * 添加热力图规则
     *
     * @Author: WuShuang on 2019/12/23  10:46
     * @param: [ruleEntity]
     * @return: java.lang.String
     * @Description:
     */
    String ruleAdd(RuleEntity ruleEntity);

    /**
     * 导出商圈
     *
     * @Author: WuShuang on 2019/12/23  19:07
     * @param: [businessId]
     * @return: java.lang.String
     * @Description:
     */
    Response exportExcel(String[] businessId);

    /**
     *高德数据
     *
     * @Author: WuShuang on 2019/12/30  13:56
     * @param: [adminPageCommon, cityName]
     * @return: java.lang.String
     * @Description:
     */
    String findGouldCityData(AdminPageCommon adminPageCommon, String cityName);

    /**
     * 导出用户数据
     *
     * @Author: WuShuang on 2020/3/11  16:30
     * @param: [id]
     * @return: com.pointcoil.util.Response
     * @Description:
     */
    Response exportExcelByUser(String[] id);

    /**
     * 订单列表
     *
     * @Author: WuShuang on 2020/3/23  10:56
     * @param: [adminPageCommon, createTime]
     * @return: com.pointcoil.util.Response
     * @Description:
     */
    String order(AdminPageCommon adminPageCommon, String createTime);

    /**
     * 修改小程序
     *
     * @Author: WuShuang on 2020/3/25  16:56
     * @param: [id, appId, secret]
     * @return: java.lang.String
     * @Description:
     */
    String updateCustom(String id, String appId, String secret);

    /**
     * 查询appid
     *
     * @Author: WuShuang on 2020/3/25  17:07
     * @param: [id]
     * @return: java.lang.String
     * @Description:
     */
    String findCustomId(String id);

    /**
     * 修改规则
     *
     * @Author: WuShuang on 2020/4/14  16:22
     * @param: [id, firstClassClassification, threeLevelClassification, threeLevelClassification1, minCount]
     * @return: java.lang.String
     * @Description:
     */
    String updateRule(String id, String firstClassClassification, String twoLevelClassification, String threeLevelClassification, String minCount);

    /**
     *定制热力图
     *
     * @Author: WuShuang on 2020/4/17  11:07
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    String findThermodynamicByUserId(String id);

    /**
     * 搜索城市
     *
     * @Author: WuShuang on 2020/4/17  11:20
     * @param: [cityName]
     * @return: java.lang.String
     * @Description:
     */
    String getSearchCity(String cityName);

    /**
     * 添加城市
     *
     * @Author: WuShuang on 2020/4/17  11:38
     * @param: [id, linkUrl]
     * @return: java.lang.String
     * @Description:
     */
    String thermodynamicUserAdd(String[] cityName, String date, String userId);

    /**
     *
     *
     * @Author: WuShuang on 2020/4/20  10:04
     * @param: [id]
     * @return: java.lang.String
     * @Description:
     */
    String deleteCity(String id);

    /**
     *  修改城市开放时间
     *
     * @Author: WuShuang on 2020/4/21  17:28
     * @param: [id, userId, date]
     * @return: java.lang.String
     * @Description:
     */
    String updateMemberCityTime(String id, String userId, String date);

    /**
     * 删除所哟数据
     *
     * @param cityName
     * @return java.lang.String
     * @methodName deleteCityData
     * @author WuShunag
     * @date 22:38
     */
    String deleteCityData(String cityName);

    /**
     *  删除用户
     *
     * @param id
     * @param userPhone
     * @return java.lang.String
     * @methodName delUser
     * @author WuShunag
     * @date 21:51
     */
    String delUser(String id, String userPhone);

    /**
     *  导出标签
     *
     * @param labelId
     * @return com.pointcoil.util.Response
     * @methodName exportExcelByLabel
     * @author WuShunag
     * @date 22:11
     */
    Response exportExcelByLabel(String[] labelId);

    /**
     *  定制热力图 数据
     *
     * @param userId
    * @param type
    * @param page
    * @param pageSize
     * @return java.lang.String
     * @methodName findCustomizedThermodynamic
     * @author WuShunag
     * @date 1:27
     */
    String findCustomizedThermodynamic(String userId, String type, Integer page, Integer pageSize);

    /**
     *  删除定制热力图
     *
     * @param userId
    * @param type
     * @return java.lang.String
     * @methodName deleteCustomizedThermodynamic
     * @author WuShunag
     * @date 23:34
     */
    String deleteCustomizedThermodynamic(String userId, String type);
}
