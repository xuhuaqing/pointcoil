package com.pointcoil.service.officialwebsite.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pointcoil.conf.PointCoilProperties;
import com.pointcoil.dto.official.HomePageDTO;
import com.pointcoil.dto.official.NewsDTO;
import com.pointcoil.dto.official.PageCommon;
import com.pointcoil.mapper.OfficialWebsiteServiceMapper;
import com.pointcoil.service.officialwebsite.OfficialWebsiteService;
import com.pointcoil.util.RedisUtil;
import com.pointcoil.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.pointcoil.constant.Constants.*;
import static com.pointcoil.constant.MsgConstant.MSG_001008;
import static com.pointcoil.constant.MsgConstant.PAGE_SIZE;

/**
 * @author:WuShuang
 * @date:2019/10/28
 * @ver:1.0
 **/
@Service
@SuppressWarnings("ALL")
public class OfficialWebsiteServiceImpl implements OfficialWebsiteService {

    @Autowired
    private OfficialWebsiteServiceMapper officialWebsiteServiceMapper;
    @Autowired
    private PointCoilProperties pointCoilProperties;
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
    @Override
    public String init() {
        String homePageInit = pointCoilProperties.getRedis().getHomePageInit();
        /**
         * 如果有
         */
        if(redisUtil.exists(homePageInit)){
            JSONObject resultJson = (JSONObject) JSONObject.parse(redisUtil.get(homePageInit).toString());
            return ResponseUtil.successToClient(resultJson);
        }else {
            //轮播图
             List<String> strings = officialWebsiteServiceMapper.selectByOne(SINGLE);
            //四张图 和 里面的 数据
             List<HomePageDTO.FourPicture> fourPicture =   officialWebsiteServiceMapper.selectByTwo(MATCHMAKER_NUM);
             //产品介绍 一张图
            String selectByThree  =  officialWebsiteServiceMapper.selectByThree(SINGLE_NUM);
            //雷达产品功能
            String selectByThree1 = officialWebsiteServiceMapper.selectByThree(THREE_NUM);
            //商圈雷达产品特点
            List<HomePageDTO.ProductCharacteristics> productCharacteristics = officialWebsiteServiceMapper.selectByFour(MATCHMAKER);
            //新闻中心
            List<HomePageDTO.News> news =  officialWebsiteServiceMapper.selectByNews(NICE_NUM);
            for(HomePageDTO.News news1 : news){
                if(!StringUtils.isEmpty(news1.getNewsText())){
                    List<String> imgSrc = getImgSrc(news1.getNewsText());
                    if(!imgSrc.isEmpty()){
                        news1.setNewsImg(imgSrc.get(0));
                    }
                }
            }
            //关于我们
            HomePageDTO.AboutUs aboutUs   = officialWebsiteServiceMapper.selectByAboutUs(SEVEN_INT);
            //发展历史 背景图
            String backgroundImage  =  officialWebsiteServiceMapper.selectByThree(EIGHT_String);
            //发展历史
            List<HomePageDTO.History> histories =  officialWebsiteServiceMapper.selectByHistory(FIVE_INT);
            //合作伙伴
            List<HomePageDTO.Partner> partner = officialWebsiteServiceMapper.selectByPartner(SIX_STRING);
            JSONObject resultJson = new JSONObject();
            resultJson.put("rotationChart",strings);
            resultJson.put("fourPicture",fourPicture);
            resultJson.put("productIntroduction",selectByThree);
            resultJson.put("function",selectByThree1);
            resultJson.put("characteristics",productCharacteristics);
            resultJson.put("news",news);
            resultJson.put("aboutUs",aboutUs);
            resultJson.put("backgroundImage",backgroundImage);
            resultJson.put("histories",histories);
            resultJson.put("partner",partner);
            redisUtil.set(homePageInit, resultJson.toJSONString(), 30 * 60);
            return ResponseUtil.successToClient(resultJson);
        }
    }

    /**
     * 新闻列表
     *
     * @Author: WuShuang on 2019/11/1  12:40
     * @param: []
     * @return: java.lang.String
     * @Description:
     * @param pageCommon
     */
    @Override
    public String initNews(PageCommon pageCommon) {
        String homePageInitNews = pointCoilProperties.getRedis().getHomePageInitNews();
        String s = String.valueOf(pageCommon.getPage());
        if(redisUtil.exists(homePageInitNews+s)){
            JSONObject resultJson = (JSONObject) JSONObject.parse(redisUtil.get(homePageInitNews+s).toString());
            return ResponseUtil.successToClient(resultJson);
        }else {
            JSONObject resultJson = new JSONObject();
            PageHelper.startPage(pageCommon.getPage(),PAGE_SIZE);
            Page<HomePageDTO.NewsByLimit> news =  officialWebsiteServiceMapper.selectNewsAll(NICE_NUM);
            if(news.isEmpty()){
               return  ResponseUtil.toClient(MSG_001008);
            }

            for(HomePageDTO.NewsByLimit news1 : news){
                if(!StringUtils.isEmpty(news1.getNewsText())){
                    List<String> imgSrc = getImgSrc(news1.getNewsText());
                    if(!imgSrc.isEmpty()){
                        news1.setNewsImg(imgSrc.get(0));
                    }
                }
            }
            long i =news.getTotal()/10+1;
            resultJson.put("news",news);
            resultJson.put("count",i);
            redisUtil.set(homePageInitNews+s, resultJson.toJSONString());
            return ResponseUtil.successToClient(resultJson);
        }
    }

    /**
     * 新闻详情
     *
     * @Author: WuShuang on 2019/11/1  14:21
     * @param: [newsDTO]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String newsDetails(NewsDTO newsDTO) {
        if(redisUtil.exists(newsDTO.getNewsId())){
            JSONObject resultJson = (JSONObject) JSONObject.parse(redisUtil.get(newsDTO.getNewsId()).toString());
            return ResponseUtil.successToClient(resultJson);
        }else {
            String s = officialWebsiteServiceMapper.newsDetails(newsDTO.getNewsId());
            JSONObject resultJson = new JSONObject();
            resultJson.put("newsDetails",s);
            redisUtil.set(newsDTO.getNewsId(), resultJson.toJSONString());
            return ResponseUtil.successToClient(resultJson);
        }
    }

    @Override
    public String initIndustry(PageCommon pageCommon) {
        String homePageInitNews = pointCoilProperties.getRedis().getHomePageInitIndustry();
        String s = String.valueOf(pageCommon.getPage());
        if(redisUtil.exists(homePageInitNews+s)){
            JSONObject resultJson = (JSONObject) JSONObject.parse(redisUtil.get(homePageInitNews+s).toString());
            return ResponseUtil.successToClient(resultJson);
        }else {
            JSONObject resultJson = new JSONObject();
            PageHelper.startPage(pageCommon.getPage(),PAGE_SIZE);
            Page<HomePageDTO.NewsByLimit> news =  officialWebsiteServiceMapper.selectNewsAll(TEN_NUM);
            if(news.isEmpty()){
                return  ResponseUtil.toClient(MSG_001008);
            }

            for(HomePageDTO.NewsByLimit news1 : news){
                if(!StringUtils.isEmpty(news1.getNewsText())){
                    List<String> imgSrc = getImgSrc(news1.getNewsText());
                    if(!imgSrc.isEmpty()){
                        news1.setNewsImg(imgSrc.get(0));
                    }
                }
            }
            long i =news.getTotal()/10+1;
            resultJson.put("news",news);
            resultJson.put("count",i);
            redisUtil.set(homePageInitNews+s, resultJson.toJSONString());
            return ResponseUtil.successToClient(resultJson);
        }
    }


    public static List<String> getImgSrc(String htmlStr) {

        if( htmlStr == null ){

            return null;
        }

        String img = "";
        Pattern p_image;
        Matcher m_image;
        List<String> pics = new ArrayList<String>();

        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            img = img + "," + m_image.group();
            // Matcher m =
            // Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(img); //匹配src
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);

            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }

}
