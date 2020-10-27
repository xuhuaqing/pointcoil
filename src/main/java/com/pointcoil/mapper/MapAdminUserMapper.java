package com.pointcoil.mapper;

import com.github.pagehelper.Page;
import com.pointcoil.dto.admin.AdminCommon;
import com.pointcoil.dto.admin.AdminPageCommon;
import com.pointcoil.dto.map.BrandDTO;
import com.pointcoil.dto.map.RegisterDTO;
import com.pointcoil.entity.BusinessEntity;
import com.pointcoil.entity.LabelEntity;
import com.pointcoil.entity.MapUserEntity;
import com.pointcoil.entity.SubAccountEntity;
import org.apache.ibatis.annotations.Param;

/**
 * Created by WuShuang on 2019/12/4.
 */
public interface MapAdminUserMapper {
    /**
     * 点线圈后台登陆
     *
     * @Author: WuShuang on 2019/12/4  10:33
     * @param: [adminCommon]
     * @return: com.pointcoil.dto.admin.AdminCommon
     * @Description:
     */
    AdminCommon adminLogin(@Param("adminCommon") AdminCommon adminCommon);

    /**
     * 查询全部用户
     *
     * @param adminPageCommon
     * @Author: WuShuang on 2019/12/4  16:44
     * @param: []
     * @return: com.github.pagehelper.Page<com.pointcoil.entity.MapUserEntity>
     * @Description:
     */
    Page<MapUserEntity> findUser(@Param("adminPageCommon") AdminPageCommon adminPageCommon);

    /**
     * 品牌列表
     *
     * @Author: WuShuang on 2019/12/5  15:26
     * @param: [userId]
     * @return: com.github.pagehelper.Page<com.pointcoil.dto.map.BrandDTO>
     * @Description:
     */
    Page<BrandDTO.SelectBrandDTO> findBrandList(@Param("userId") String userId);

    /**
     * 商圈列表
     *
     * @Author: WuShuang on 2019/12/5  16:59
     * @param: [brandId]
     * @return: com.github.pagehelper.Page<com.pointcoil.entity.BusinessEntity>
     * @Description:
     */
    Page<BusinessEntity> findBusinessList(@Param("brandId") String brandId);

    /**
     * 标签查询
     *
     * @Author: WuShuang on 2019/12/10  13:33
     * @param: [brandId]
     * @return: com.github.pagehelper.Page<com.pointcoil.entity.LabelEntity>
     * @Description:
     */
    Page<LabelEntity> findLabelList(@Param("brandId") String brandId);

    /**
     * 子账号查询
     *
     * @Author: WuShuang on 2019/12/11  10:02
     * @param: [id]
     * @return: com.github.pagehelper.Page<com.pointcoil.entity.SubAccountEntity>
     * @Description:
     */
    Page<SubAccountEntity> findSubAccount(@Param("id") String id);

    /**
     * 添加账号
     *
     * @Author: WuShuang on 2019/12/11  14:18
     * @param: [registerDTO]
     * @return: void
     * @Description:
     */
    void userAdd(@Param("registerDTO") RegisterDTO registerDTO);

    /**
     * 审核信息
     *
     * @Author: WuShuang on 2019/12/11  15:27
     * @param: []
     * @return: com.github.pagehelper.Page<com.pointcoil.entity.MapUserEntity>
     * @Description:
     */
    Page<MapUserEntity> findUserByExamine();

    /**
     * 审核
     *
     * @Author: WuShuang on 2019/12/11  16:46
     * @param: [type, registerDTO]
     * @return: void
     * @Description:
     */
    void updUser(@Param("type") String type, @Param("registerDTO") RegisterDTO registerDTO, @Param("id") String id);

    /**
     * 用户审核
     *
     * @Author: WuShuang on 2019/12/12  10:33
     * @param: []
     * @return: com.github.pagehelper.Page<com.pointcoil.entity.MapUserEntity>
     * @Description:
     */
    Page<MapUserEntity> findResultList();

    /**
     * 删除审核列表信息
     *
     * @Author: WuShuang on 2019/12/12  10:38
     * @param: [id]
     * @return: void
     * @Description:
     */
    void delUserAudit(@Param("id") String id);

    /**
     * @Author: WuShuang on 2020/1/7  13:53
     * @param: [id, isMembers, date]
     * @return: void
     * @Description:
     */
    void userMemberAdd(@Param("id") String id, @Param("isMembers") String isMembers, @Param("date") String date, @Param("s") String s);

    /**
     * 删除热力图
     *
     * @Author: WuShuang on 2020/4/14  9:10
     * @param: [id]
     * @return: void
     * @Description:
     */
    void deleteRule(@Param("id") String id);
}
