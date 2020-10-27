package com.pointcoil.controller.adminmap;

import cn.hutool.core.lang.ObjectId;
import com.pointcoil.dto.admin.AdminCommon;
import com.pointcoil.dto.admin.AdminPageCommon;
import com.pointcoil.dto.map.RegisterDTO;
import com.pointcoil.service.adminmap.MapAdminUserService;
import com.pointcoil.util.RedisUtil;
import com.pointcoil.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import static com.pointcoil.constant.Constants.SUCCESS_ADMIN_CODE;
import static com.pointcoil.constant.Constants.SUCCESS_MSG;

/**
 * Created by WuShuang on 2019/12/4.
 */
@RestController
@SuppressWarnings("ALL")
@RequestMapping("/mapAdminUser/")
@Slf4j
public class MapAdminUserController {

    @Autowired
    private MapAdminUserService mapAdminUserService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 后台登陆
     *
     * @param
     * @return java.lang.String
     * @methodName adminLogin
     * @author WuShunag
     * @date 17:10
     */
    @PostMapping("adminLogin")
    public String adminLogin(AdminCommon adminCommon){
        log.info("入参{}：",adminCommon);
        return mapAdminUserService.adminLogin(adminCommon);
    }


    /**
     * 查询用户
     *
     * @Author: WuShuang on 2019/12/4  16:34
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("findUser")
    public String findUser(AdminPageCommon adminPageCommon){
        log.info("入参{}：",adminPageCommon);
        return mapAdminUserService.findUser(adminPageCommon);
    }


    /**
     * 查询用户下的品牌
     *
     * @Author: WuShuang on 2019/12/5  15:18
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("findBrandList")
    public String findBrandList(@RequestParam("page") Integer page,@RequestParam("pageSize")  Integer pageSize,@RequestParam("userId") String userId){
        log.info("入参{}：",page,pageSize,userId);
        return mapAdminUserService.findBrandList(page,pageSize,userId);
    }

    /**
     * 商圈列表
     *
     * @Author: WuShuang on 2019/12/5  16:53
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("findBusinessList")
    public String findBusinessList(@RequestParam("page") Integer page,@RequestParam("pageSize")  Integer pageSize,@RequestParam("brandId") String brandId){
        log.info("入参{}：",page,pageSize,brandId);
        return mapAdminUserService.findBusinessList(page,pageSize,brandId);
    }

    /**
     * 标签列表
     *
     * @Author: WuShuang on 2019/12/10  13:29
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("findLabelList")
    public String findLabelList(@RequestParam("page") Integer page,@RequestParam("pageSize")  Integer pageSize,@RequestParam("brandId") String brandId){
        log.info("入参{}：",page,pageSize,brandId);
        return mapAdminUserService.findLabelList(page,pageSize,brandId);
    }

    /**
     * 拿广告图片
     *
     * @Author: WuShuang on 2019/12/10  14:06
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("findPostManage")
    public String findPostManage(@RequestParam("id") String id){
        Object o = redisUtil.get("advertisement" + id);
        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        if(StringUtils.isEmpty(o)){
            String advertisement = redisUtil.get("advertisement").toString();
            objectObjectHashMap.put("src",advertisement);
            return ResponseUtil.successToClient(objectObjectHashMap);
        }else {
            objectObjectHashMap.put("src",o.toString());
            return ResponseUtil.successToClient(objectObjectHashMap);
        }
    }

    /**
     * 修改广告
     *
     * @Author: WuShuang on 2019/12/10  14:32
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("updPosterManage")
    public String updPosterManage(@RequestParam("carouselImageUrl")String carouselImageUrl,@RequestParam("id") String id){
         redisUtil.set("advertisement"+id,carouselImageUrl);
         return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }

    /**
     * 子账号查询
     *
     * @Author: WuShuang on 2019/12/11  9:59
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("findSubAccount")
    public String findSubAccount(@RequestParam("page") Integer page,@RequestParam("pageSize")  Integer pageSize,@RequestParam("id") String id){
        log.info("入参{}：",page,pageSize,id);
        return mapAdminUserService.findSubAccount(page,pageSize,id);
    }

    /**
     * 添加用户
     *
     * @Author: WuShuang on 2019/12/11  14:11
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("userAdd")
    public String userAdd(RegisterDTO registerDTO){
        log.info("入参{}：",registerDTO);
        return mapAdminUserService.userAdd(registerDTO);
    }

    /**
     * 用户审核
     *
     * @Author: WuShuang on 2019/12/11  15:24
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("findUserByExamine")
    public String findUserByExamine(AdminPageCommon adminPageCommon){
        log.info("入参{}：",adminPageCommon);
        return mapAdminUserService.findUserByExamine(adminPageCommon);
    }


    /**
     * 审核
     *
     * @Author: WuShuang on 2019/12/11  16:33
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("audit")
    public String audit(RegisterDTO registerDTO,@RequestParam("type") String type,String id){
        log.info("入参{}：",registerDTO,type,id);
        log.info("入参1{}：",type);
        return mapAdminUserService.audit(registerDTO,type,id);
    }


    /**
     * 审核列表
     *
     * @Author: WuShuang on 2019/12/12  10:31
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("findResultList")
    public String findResultList(AdminPageCommon adminPageCommon){
        log.info("入参{}：",adminPageCommon);
        return mapAdminUserService.findResultList(adminPageCommon);
    }

    /**
     * 删除审核列表信息
     *
     * @Author: WuShuang on 2019/12/12  10:36
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("delUserAudit")
    public String delUserAudit(String id){
        return mapAdminUserService.delUserAudit(id);
    }

    /**
     * 添加用户会员
     *
     * @Author: WuShuang on 2020/1/7  11:20
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("userMemberAdd")
    public String userMemberAdd(String id,String isMembers,String date){
        log.info(id);
        log.info(isMembers);
        log.info(date);
        return mapAdminUserService.userMemberAdd(id,isMembers,date);
    }


    /**
     * 删除热力图规则
     *
     * @Author: WuShuang on 2020/4/14  9:08
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("deleteRule")
    public String deleteRule(String id){
        return mapAdminUserService.deleteRule(id);
    }
}
