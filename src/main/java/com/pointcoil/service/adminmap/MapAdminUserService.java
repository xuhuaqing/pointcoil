package com.pointcoil.service.adminmap;

import com.pointcoil.dto.admin.AdminCommon;
import com.pointcoil.dto.admin.AdminPageCommon;
import com.pointcoil.dto.map.RegisterDTO;

/**
 * Created by WuShuang on 2019/12/4.
 */
public interface MapAdminUserService {
    /**
     * 点线圈后台登陆
     *
     * @Author: WuShuang on 2019/12/4  9:56
     * @param: [adminCommon]
     * @return: java.lang.String
     * @Description:
     */
    String adminLogin(AdminCommon adminCommon);

    /**
     * 查询用户
     *
     * @Author: WuShuang on 2019/12/4  16:37
     * @param: [adminPageCommon]
     * @return: java.lang.String
     * @Description:
     */
    String findUser(AdminPageCommon adminPageCommon);

    /**
     * 查询品牌
     *
     * @Author: WuShuang on 2019/12/5  15:22
     * @param: [page, pageSize, userId]
     * @return: java.lang.String
     * @Description:
     */
    String findBrandList(Integer page, Integer pageSize, String userId);

    /**
     * 查询商圈列表
     *
     * @Author: WuShuang on 2019/12/5  16:54
     * @param: [page, pageSize, brandId]
     * @return: java.lang.String
     * @Description:
     */
    String findBusinessList(Integer page, Integer pageSize, String brandId);

    /**
     * 查询标签
     *
     * @Author: WuShuang on 2019/12/10  13:31
     * @param: [page, pageSize, brandId]
     * @return: java.lang.String
     * @Description:
     */
    String findLabelList(Integer page, Integer pageSize, String brandId);

    /**
     * 子账号查询
     *
     * @Author: WuShuang on 2019/12/11  10:00
     * @param: [page, pageSize, id]
     * @return: java.lang.String
     * @Description:
     */
    String findSubAccount(Integer page, Integer pageSize, String id);

    /**
     * 添加用户
     *
     * @Author: WuShuang on 2019/12/11  14:13
     * @param: [registerDTO]
     * @return: java.lang.String
     * @Description:
     */
    String userAdd(RegisterDTO registerDTO);

    /**
     *
     *用户审核
     * @Author: WuShuang on 2019/12/11  15:25
     * @param: [adminPageCommon]
     * @return: java.lang.String
     * @Description:
     */
    String findUserByExamine(AdminPageCommon adminPageCommon);

    /**
     * 审核
     *
     * @Author: WuShuang on 2019/12/11  16:39
     * @param: [registerDTO, type]
     * @return: java.lang.String
     * @Description:
     */
    String audit(RegisterDTO registerDTO, String type, String id);

    /**
     *审核列表
     *
     * @Author: WuShuang on 2019/12/12  10:32
     * @param: [adminPageCommon]
     * @return: java.lang.String
     * @Description:
     */
    String findResultList(AdminPageCommon adminPageCommon);

    /**
     * 审核列表
     *
     * @Author: WuShuang on 2019/12/12  10:36
     * @param: [id]
     * @return: java.lang.String
     * @Description:
     */
    String delUserAudit(String id);

    /**
     * 修改会员等级
     *
     * @Author: WuShuang on 2020/1/7  13:52
     * @param: [id, isMembers, date]
     * @return: java.lang.String
     * @Description:
     */
    String userMemberAdd(String id, String isMembers, String date);

    /**
     * 删除热力图规则
     *
     * @Author: WuShuang on 2020/4/14  9:09
     * @param: [id]
     * @return: java.lang.String
     * @Description:
     */
    String deleteRule(String id);
}
