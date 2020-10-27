package com.pointcoil.service.adminofficial;

import com.pointcoil.dto.admin.AdminCommon;
import com.pointcoil.dto.admin.AdminPageCommon;

/**
 * @author:WuShuang
 * @date:2019/10/28
 * @ver:1.0
 **/
public interface AdminOfficialService {
    /**
     * 后台登陆
     *
     * @param adminCommon
     * @return java.lang.String
     * @methodName adminLogin
     * @author WuShunag
     * @date 17:15
     */
    String adminLogin(AdminCommon adminCommon);

    /**
     * 轮播图查询
     * @Author: WuShuang on 2019/10/30  10:40
     * @param: [adminPageCommon]
     * @return: java.lang.String
     * @Description:
     */
    String findAllCarousel(AdminPageCommon adminPageCommon);

    /**
     * 添加轮播图
     * @Author: WuShuang on 2019/10/30  11:25
     * @param: [adminPageCommon]
     * @return: java.lang.String
     * @Description:
     */
    String addCarousel(AdminPageCommon.OfficialWebsite adminPageCommon);

    /**
     *删除轮播图
     *
     * @Author: WuShuang on 2019/10/30  14:22
     * @param: [adminPageCommon]
     * @return: java.lang.String
     * @Description:
     */
    String deleteCarousel(AdminPageCommon.OfficialWebsite adminPageCommon);

    /**
     *根据id 查询轮播图
     *
     * @Author: WuShuang on 2019/10/30  14:29
     * @param: [adminPageCommon]
     * @return: java.lang.String
     * @Description:
     */
    String showCarousel(AdminPageCommon.OfficialWebsite adminPageCommon);

    /**
     *官网数据管理
     *
     * @Author: WuShuang on 2019/10/30  14:46
     * @param: [adminPageCommon]
     * @return: java.lang.String
     * @Description:
     */
    String findOfficialData(AdminPageCommon adminPageCommon);

    /**
     * 修改数据
     *
     * @Author: WuShuang on 2019/10/30  15:06
     * @param: [adminPageCommon]
     * @return: java.lang.String
     * @Description:
     */
    String updCarousel(AdminPageCommon.OfficialWebsite adminPageCommon);

    /**
     *产品类型
     *
     * @Author: WuShuang on 2019/10/30  16:06
     * @param: [adminPageCommon]
     * @return: java.lang.String
     * @Description:
     */
    String findProductIntroduction(AdminPageCommon adminPageCommon);

    /**
     *商圈雷达产品图
     *
     * @Author: WuShuang on 2019/10/30  16:46
     * @param: [adminPageCommon]
     * @return: java.lang.String
     * @Description:
     */
    String findBusinessRadar(AdminPageCommon adminPageCommon);

    /**
     * 商圈雷达特点展示
     *
     * @Author: WuShuang on 2019/10/30  17:22
     * @param: [adminPageCommon]
     * @return: java.lang.String
     * @Description:
     */
    String findProductCharacteristics(AdminPageCommon adminPageCommon);

    /**
     * 关于我们
     *
     * @param adminPageCommon
     * @return java.lang.String
     * @methodName findAboutUs
     * @author WuShunag
     * @date 21:53
     */
    String findAboutUs(AdminPageCommon adminPageCommon);

    /**
     * 发展历史的管理
     *
     * @param adminPageCommon
     * @return java.lang.String
     * @methodName findDevelopment
     * @author WuShunag
     * @date 22:09
     */
    String findDevelopment(AdminPageCommon adminPageCommon);

    /**
     * 合作伙伴
     *
     * @param adminPageCommon
     * @return java.lang.String
     * @methodName findCooperationPartner
     * @author WuShunag
     * @date 22:31
     */
    String findCooperationPartner(AdminPageCommon adminPageCommon);

    /**
     *新闻管理
     *
     * @Author: WuShuang on 2019/10/31  13:13
     * @param: [adminPageCommon]
     * @return: java.lang.String
     * @Description:
     */
    String findNewsData(AdminPageCommon adminPageCommon);

    /**
     * 修改密码
     *
     * @Author: WuShuang on 2019/12/26  16:26
     * @param: [oldPassword, password, rePassword]
     * @return: java.lang.String
     * @Description:
     */
    String changePassword(String oldPassword, String password, String rePassword);

    /**
     * 行业管理
     *
     * @Author: WuShuang on 2020/3/11  14:54
     * @param: [adminPageCommon]
     * @return: java.lang.String
     * @Description:
     */
    String findIndustryDynamics(AdminPageCommon adminPageCommon);
}
