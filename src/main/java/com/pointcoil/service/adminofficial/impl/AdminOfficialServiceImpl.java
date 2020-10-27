package com.pointcoil.service.adminofficial.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pointcoil.conf.PointCoilProperties;
import com.pointcoil.dto.admin.AdminCommon;
import com.pointcoil.dto.admin.AdminPageCommon;
import com.pointcoil.mapper.AdminOfficialMapper;
import com.pointcoil.service.adminofficial.AdminOfficialService;
import com.pointcoil.util.Md5Util;
import com.pointcoil.util.RedisUtil;
import com.pointcoil.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.pointcoil.constant.Constants.*;

/**
 * @author:WuShuang
 * @date:2019/10/28
 * @ver:1.0
 **/
@Service
@SuppressWarnings("ALL")
@Slf4j
public class AdminOfficialServiceImpl implements AdminOfficialService {

    @Autowired
    private AdminOfficialMapper adminOfficialMapper;
    @Autowired
    private PointCoilProperties pointCoilProperties;
    @Autowired
    private RedisUtil redisUtil;
    /**
     * 后台登陆
     *
     * @param adminCommon
     * @return java.lang.String
     * @methodName adminLogin
     * @author WuShunag
     * @date 17:15
     */
    @Override
    public String adminLogin(AdminCommon adminCommon) {
        adminCommon.setPassWord(Md5Util.getMD5(adminCommon.getPassWord()));
        adminCommon =  adminOfficialMapper.adminLogin(adminCommon);
        if(StringUtils.isEmpty(adminCommon)){
            return ResponseUtil.errorMsgToClient(PASSWORD_ERROR_CODE+"", PASSWORD_ERROR_MSG);
        }else {
            return ResponseUtil.toClient(SUCCESS_ADMIN_CODE+"",SUCCESS_MSG);
        }
    }

    /**
     * 轮播图查询
     *
     * @Author: WuShuang on 2019/10/30  10:40
     * @param: [adminPageCommon]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String findAllCarousel(AdminPageCommon adminPageCommon) {
        PageHelper.startPage(adminPageCommon.getPage(),adminPageCommon.getPageSize());
        Page<AdminPageCommon.OfficialWebsite> officialWebsitePage =  adminOfficialMapper.findAllCarousel(SINGLE);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, officialWebsitePage, new HashMap<String,Object>(1) {
            {
                put("count", officialWebsitePage.getTotal());
            }
        });
    }

    /**
     *
     * 添加轮播图
     * @Author: WuShuang on 2019/10/30  11:26
     * @param: [adminPageCommon]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String addCarousel(AdminPageCommon.OfficialWebsite adminPageCommon) {
        adminOfficialMapper.addCarousel(adminPageCommon);
        String homePageInit = pointCoilProperties.getRedis().getHomePageInit();
        redisUtil.remove(homePageInit);

        String homePageInitNews = pointCoilProperties.getRedis().getHomePageInitNews();
        Set<String> keys = redisUtil.keys(homePageInitNews + "*");
        for(String s :keys){
            redisUtil.remove(s);
        }
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE+"",SUCCESS_MSG);
    }

    /**
     *删除轮播图
     *
     * @Author: WuShuang on 2019/10/30  14:22
     * @param: [adminPageCommon]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String deleteCarousel(AdminPageCommon.OfficialWebsite adminPageCommon) {
        adminOfficialMapper.deleteCarousel(adminPageCommon);
        String homePageInit = pointCoilProperties.getRedis().getHomePageInit();
        redisUtil.remove(homePageInit);

        String homePageInitNews = pointCoilProperties.getRedis().getHomePageInitNews();
        Set<String> keys = redisUtil.keys(homePageInitNews + "*");
        for(String s :keys){
            redisUtil.remove(s);
        }

        if(redisUtil.exists(adminPageCommon.getId())){
            redisUtil.remove(adminPageCommon.getId());
        }
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE+"",SUCCESS_MSG);
    }

    /**
     *查询轮播图 根据id
     *
     * @Author: WuShuang on 2019/10/30  14:30
     * @param: [adminPageCommon]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String showCarousel(AdminPageCommon.OfficialWebsite adminPageCommon) {
        adminPageCommon =  adminOfficialMapper.showCarousel(adminPageCommon);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE+"",SUCCESS_MSG,adminPageCommon);
    }

    /**
     *官网数据管理
     *
     * @Author: WuShuang on 2019/10/30  14:46
     * @param: [adminPageCommon]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String findOfficialData(AdminPageCommon adminPageCommon) {
        PageHelper.startPage(adminPageCommon.getPage(),adminPageCommon.getPageSize());
        Page<AdminPageCommon.OfficialWebsite> officialWebsites = adminOfficialMapper.findOfficialData(MATCHMAKER_NUM);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, officialWebsites, new HashMap<String,Object>(1) {
            {
                put("count", officialWebsites.getTotal());
            }
        });
    }

    /**
     * 修改数据
     *
     * @Author: WuShuang on 2019/10/30  15:06
     * @param: [adminPageCommon]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String updCarousel(AdminPageCommon.OfficialWebsite adminPageCommon) {
        adminOfficialMapper.updCarousel(adminPageCommon);
        String homePageInit = pointCoilProperties.getRedis().getHomePageInit();
        redisUtil.remove(homePageInit);

        String homePageInitNews = pointCoilProperties.getRedis().getHomePageInitNews();
        Set<String> keys = redisUtil.keys(homePageInitNews + "*");
        for(String s :keys){
            redisUtil.remove(s);
        }

        if(redisUtil.exists(adminPageCommon.getId())){
            redisUtil.remove(adminPageCommon.getId());
        }

        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE+"",SUCCESS_MSG);
    }

    @Override
    public String findProductIntroduction(AdminPageCommon adminPageCommon) {
        PageHelper.startPage(adminPageCommon.getPage(),adminPageCommon.getPageSize());
        Page<AdminPageCommon.OfficialWebsite> officialWebsites = adminOfficialMapper.findOfficialData(SINGLE_NUM);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, officialWebsites, new HashMap<String,Object>(1) {
            {
                put("count", officialWebsites.getTotal());
            }
        });
    }



    @Override
    public String findBusinessRadar(AdminPageCommon adminPageCommon) {
        PageHelper.startPage(adminPageCommon.getPage(),adminPageCommon.getPageSize());
        Page<AdminPageCommon.OfficialWebsite> officialWebsites = adminOfficialMapper.findOfficialData(THREE_NUM);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, officialWebsites, new HashMap<String,Object>(1) {
            {
                put("count", officialWebsites.getTotal());
            }
        });
    }

    @Override
    public String findProductCharacteristics(AdminPageCommon adminPageCommon) {
        PageHelper.startPage(adminPageCommon.getPage(),adminPageCommon.getPageSize());
        Page<AdminPageCommon.OfficialWebsite> officialWebsites = adminOfficialMapper.findOfficialData(MATCHMAKER);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, officialWebsites, new HashMap<String,Object>(1) {
            {
                put("count", officialWebsites.getTotal());
            }
        });
    }

    @Override
    public String findAboutUs(AdminPageCommon adminPageCommon) {
        PageHelper.startPage(adminPageCommon.getPage(),adminPageCommon.getPageSize());
        Page<AdminPageCommon.OfficialWebsite> officialWebsites = adminOfficialMapper.findOfficialData(SEVEN_INT);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, officialWebsites, new HashMap<String,Object>(1) {
            {
                put("count", officialWebsites.getTotal());
            }
        });
    }

    @Override
    public String findDevelopment(AdminPageCommon adminPageCommon) {
        PageHelper.startPage(adminPageCommon.getPage(),adminPageCommon.getPageSize());
        Page<AdminPageCommon.OfficialWebsite> officialWebsites = adminOfficialMapper.findOfficialData(FIVE_INT);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, officialWebsites, new HashMap<String,Object>(1) {
            {
                put("count", officialWebsites.getTotal());
            }
        });
    }

    @Override
    public String findCooperationPartner(AdminPageCommon adminPageCommon) {
        PageHelper.startPage(adminPageCommon.getPage(),adminPageCommon.getPageSize());
        Page<AdminPageCommon.OfficialWebsite> officialWebsites = adminOfficialMapper.findOfficialData(SIX_STRING);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, officialWebsites, new HashMap<String,Object>(1) {
            {
                put("count", officialWebsites.getTotal());
            }
        });
    }

    @Override
    public String findNewsData(AdminPageCommon adminPageCommon) {
        PageHelper.startPage(adminPageCommon.getPage(),adminPageCommon.getPageSize());
        Page<AdminPageCommon.OfficialWebsite> officialWebsites = adminOfficialMapper.findOfficialData(NICE_NUM);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, officialWebsites, new HashMap<String,Object>(1) {
            {
                put("count", officialWebsites.getTotal());
            }
        });
    }

    @Override
    public String changePassword(String oldPassword, String password, String rePassword) {

        Integer i = adminOfficialMapper.selectOldPas(Md5Util.getMD5(oldPassword));

        if(i == 0){
            return ResponseUtil.toClient(ERROR_CODE+"","原密码错误！");
        }

        if(!password.equals(rePassword)){
            return ResponseUtil.toClient(ERROR_CODE+"","两次密码不一致！");
        }
        adminOfficialMapper.changePassword(Md5Util.getMD5(password));
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }

    @Override
    public String findIndustryDynamics(AdminPageCommon adminPageCommon) {
        PageHelper.startPage(adminPageCommon.getPage(),adminPageCommon.getPageSize());
        Page<AdminPageCommon.OfficialWebsite> officialWebsites = adminOfficialMapper.findOfficialData(TEN_NUM);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, officialWebsites, new HashMap<String,Object>(1) {
            {
                put("count", officialWebsites.getTotal());
            }
        });
    }
}
