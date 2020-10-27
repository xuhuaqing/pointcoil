package com.pointcoil.mapper;

import com.github.pagehelper.Page;
import com.pointcoil.dto.admin.AdminCommon;
import com.pointcoil.dto.admin.AdminPageCommon;
import org.apache.ibatis.annotations.Param;

/**
 * @author:WuShuang
 * @date:2019/10/28
 * @ver:1.0
 **/

public interface AdminOfficialMapper {
    /**
     * 用户登陆
     *
     * @param adminCommon
     * @return com.pointcoil.dto.admin.AdminCommon
     * @methodName adminLogin
     * @author WuShunag
     * @date 17:21
     */
    AdminCommon adminLogin(@Param("adminCommon") AdminCommon adminCommon);


    /**
     * 轮播图查询所有
     *
     * @param single
     * @Author: WuShuang on 2019/10/30  10:52
     * @param: []
     * @return: com.github.pagehelper.Page<com.pointcoil.dto.admin.AdminPageCommon.OfficialWebsite>
     * @Description:
     */
    Page<AdminPageCommon.OfficialWebsite> findAllCarousel(@Param("single") String single);

    /**
     * 添加轮播图
     *
     * @param officialWebsite
     * @Author: WuShuang on 2019/10/30  11:26
     * @param: [adminPageCommon]
     * @return: void
     * @Description:
     */
    void addCarousel(@Param("officialWebsite") AdminPageCommon.OfficialWebsite officialWebsite);

    /**
     * 删除轮播图
     *
     * @Author: WuShuang on 2019/10/30  14:23
     * @param: [adminPageCommon]
     * @return: void
     * @Description:
     */
    void deleteCarousel(@Param("adminPageCommon") AdminPageCommon.OfficialWebsite adminPageCommon);

    /**
     * 根据id  查询轮播图
     *
     * @Author: WuShuang on 2019/10/30  14:30
     * @param: [adminPageCommon]
     * @return: com.pointcoil.dto.admin.AdminPageCommon.OfficialWebsite
     * @Description:
     */
    AdminPageCommon.OfficialWebsite showCarousel(@Param("adminPageCommon") AdminPageCommon.OfficialWebsite adminPageCommon);

    /**
     * 官网数据
     *
     * @Author: WuShuang on 2019/10/30  14:48
     * @param: [singleNum]
     * @return: com.github.pagehelper.Page<com.pointcoil.dto.admin.AdminPageCommon.OfficialWebsite>
     * @Description:
     */
    Page<AdminPageCommon.OfficialWebsite> findOfficialData(@Param("singleNum") String singleNum);

    /**
     * 修改数据
     *
     * @Author: WuShuang on 2019/10/30  15:07
     * @param: [adminPageCommon]
     * @return: void
     * @Description:
     */
    void updCarousel(@Param("officialWebsite") AdminPageCommon.OfficialWebsite officialWebsite);

    /**
     * 查询老密码
     *
     * @Author: WuShuang on 2019/12/26  16:27
     * @param: [md5]
     * @return: java.lang.Integer
     * @Description:
     */
    Integer selectOldPas(@Param("md5") String md5);

    /**
     * 修改密码
     *
     * @Author: WuShuang on 2019/12/26  16:29
     * @param: [md5]
     * @return: void
     * @Description:
     */
    void changePassword(@Param("md5") String md5);
}
