package com.pointcoil.controller.adminofficial;

import com.pointcoil.dto.admin.AdminCommon;
import com.pointcoil.dto.admin.AdminPageCommon;
import com.pointcoil.service.adminofficial.AdminOfficialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:WuShuang
 * @date:2019/10/28
 * @ver:1.0
 **/
@RestController
@SuppressWarnings("ALL")
@RequestMapping("/adminOfficial/")
@Slf4j
public class AdminOfficialController {


    @Autowired
    private AdminOfficialService adminOfficialService;

    /**
     * 后台登陆
     *
     * @param
     * @return java.lang.String
     * @methodName adminLogin
     * @author WuShunag
     * @date 17:10
     */
    @PostMapping("user/adminLogin")
    public String adminLogin(AdminCommon adminCommon){
        log.info("入参{}：",adminCommon);
        return adminOfficialService.adminLogin(adminCommon);
    }


    /**
     * 轮播图查询
     *
     * @Author: WuShuang on 2019/10/30  10:39
     * @param: [adminPageCommon]
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("findAllCarousel")
    public String findAllCarousel(AdminPageCommon adminPageCommon){
        log.info("入参{}：",adminPageCommon);
        return adminOfficialService.findAllCarousel(adminPageCommon);
    }


    /**
     * 添加轮播图
     *
     * @Author: WuShuang on 2019/10/30  11:25
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("addCarousel")
    public String addCarousel(AdminPageCommon.OfficialWebsite adminPageCommon){
        log.info("入参{}：",adminPageCommon);
        return adminOfficialService.addCarousel(adminPageCommon);
    }

    /**
     *删除轮播图
     *
     * @Author: WuShuang on 2019/10/30  14:21
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("deleteCarousel")
    public String deleteCarousel(AdminPageCommon.OfficialWebsite adminPageCommon){
        log.info("入参{}：",adminPageCommon);
        return adminOfficialService.deleteCarousel(adminPageCommon);
    }

    /**
     *根据id 查询轮播图
     *
     * @Author: WuShuang on 2019/10/30  14:29
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("showCarousel")
    public String showCarousel(AdminPageCommon.OfficialWebsite adminPageCommon){
        log.info("入参{}：",adminPageCommon);
        return adminOfficialService.showCarousel(adminPageCommon);
    }

    /**
     *官网数据管理
     *
     * @Author: WuShuang on 2019/10/30  14:46
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("findOfficialData")
    public String findOfficialData(AdminPageCommon adminPageCommon){
        log.info("入参{}：",adminPageCommon);
        return adminOfficialService.findOfficialData(adminPageCommon);
    }

    /**
     *修改
     *
     * @Author: WuShuang on 2019/10/30  15:04
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("updCarousel")
    public String updCarousel(AdminPageCommon.OfficialWebsite adminPageCommon){
        log.info("入参{}：",adminPageCommon);
        return adminOfficialService.updCarousel(adminPageCommon);
    }


    /**
     *产品介绍
     *
     * @Author: WuShuang on 2019/10/30  16:06
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("findProductIntroduction")
    public String findProductIntroduction(AdminPageCommon adminPageCommon){
        log.info("入参{}：",adminPageCommon);
        return adminOfficialService.findProductIntroduction(adminPageCommon);
    }

    /**
     *商圈雷达产品图
     *
     * @Author: WuShuang on 2019/10/30  16:45
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("findBusinessRadar")
    public String findBusinessRadar(AdminPageCommon adminPageCommon){
        log.info("入参{}：",adminPageCommon);
        return adminOfficialService.findBusinessRadar(adminPageCommon);

    }

    /**
     *商圈雷达特点展示
     *
     * @Author: WuShuang on 2019/10/30  17:22
     * @param: [adminPageCommon]
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("findProductCharacteristics")
    public String findProductCharacteristics(AdminPageCommon adminPageCommon){
        log.info("入参{}：",adminPageCommon);
        return adminOfficialService.findProductCharacteristics(adminPageCommon);
    }

    /**
     * 查询关于我们
     *
     * @param
     * @return java.lang.String
     * @methodName findAboutUs
     * @author WuShunag
     * @date 21:53
     */
    @PostMapping("findAboutUs")
    public String findAboutUs(AdminPageCommon adminPageCommon){
        log.info("入参{}：",adminPageCommon);
        return adminOfficialService.findAboutUs(adminPageCommon);
    }

    /**
     * 发展历史的管理
     *
     * @param
     * @return java.lang.String
     * @methodName findDevelopment
     * @author WuShunag
     * @date 22:09
     */
    @PostMapping("findDevelopment")
    public String findDevelopment(AdminPageCommon adminPageCommon){
        log.info("入参{}：",adminPageCommon);
        return adminOfficialService.findDevelopment(adminPageCommon);
    }

    /**
     *
     * 合作伙伴
     * @param
     * @return java.lang.String
     * @methodName findCooperationPartner
     * @author WuShunag
     * @date 22:31
     */
    @PostMapping("findCooperationPartner")
    public String findCooperationPartner(AdminPageCommon adminPageCommon){
        log.info("入参{}：",adminPageCommon);
        return adminOfficialService.findCooperationPartner(adminPageCommon);
    }

    /**
     *新闻管理
     *
     * @Author: WuShuang on 2019/10/31  13:13
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("findNewsData")
    public String findNewsData(AdminPageCommon adminPageCommon){
        log.info("入参{}：",adminPageCommon);
        return adminOfficialService.findNewsData(adminPageCommon);
    }

    /**
     * 行业管理
     *
     * @Author: WuShuang on 2020/3/11  14:54
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("findIndustryDynamics")
    public String findIndustryDynamics(AdminPageCommon adminPageCommon){
        return adminOfficialService.findIndustryDynamics(adminPageCommon);
    }


    /**
     * 官网修改密码
     *
     * @Author: WuShuang on 2019/12/26  16:24
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("changePassword")
    public String changePassword(String oldPassword,String password,String rePassword){
        return adminOfficialService.changePassword(oldPassword,password,rePassword);
    }
}
