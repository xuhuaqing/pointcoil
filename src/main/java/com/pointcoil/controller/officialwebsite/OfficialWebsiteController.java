package com.pointcoil.controller.officialwebsite;

import com.pointcoil.conf.DataValidation;
import com.pointcoil.dto.official.NewsDTO;
import com.pointcoil.dto.official.PageCommon;
import com.pointcoil.service.officialwebsite.OfficialWebsiteService;
import com.pointcoil.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author:WuShuang
 * @date:2019/10/28
 * @ver:1.0
 **/
@RestController
@SuppressWarnings("ALL")
@RequestMapping("/api/officialWebsite/")
public class OfficialWebsiteController {

    @Resource
    private OfficialWebsiteService officialWebsiteService;

    @Autowired
    private RedisUtil redisUtil;


    /**
     *官网首页初始化
     *
     * @Author: WuShuang on 2019/10/31  16:35
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @GetMapping("init")
    public String init(){
        return officialWebsiteService.init();
    }


    /**
     *新闻列表
     *
     * @Author: WuShuang on 2019/11/1  12:39
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("initNews")
    public String initNews(@Valid @RequestBody PageCommon pageCommon, BindingResult result){
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        return officialWebsiteService.initNews(pageCommon);
    }




    /**
     *行业列表
     *
     * @Author: WuShuang on 2019/11/1  12:39
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("initIndustry")
    public String initIndustry(@Valid @RequestBody PageCommon pageCommon, BindingResult result){
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        return officialWebsiteService.initIndustry(pageCommon);
    }



    /**
     *查看新闻详情
     *
     * @Author: WuShuang on 2019/11/1  14:15
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("newsDetails")
    public String newsDetails(@Valid @RequestBody NewsDTO newsDTO, BindingResult result){
        if (result.hasErrors()) {
            return DataValidation.validation(result);
        }
        return officialWebsiteService.newsDetails(newsDTO);
    }
}
