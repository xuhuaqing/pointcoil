package com.pointcoil.mapper;

import com.github.pagehelper.Page;
import com.pointcoil.dto.official.HomePageDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OfficialWebsiteServiceMapper {
    /**
     * 轮播图
     *
     * @Author: WuShuang on 2019/10/31  16:49
     * @param: [single]
     * @return: com.pointcoil.dto.official.HomePageDTO
     * @Description:
     */
    List<String> selectByOne(@Param("single") String single);

    /**
     * 四张图 和 里面的数据
     *
     * @Author: WuShuang on 2019/10/31  16:53
     * @param: [matchmakerNum]
     * @return: com.pointcoil.dto.official.HomePageDTO.FourPicture
     * @Description:
     */
    List<HomePageDTO.FourPicture> selectByTwo(@Param("matchmakerNum") String matchmakerNum);

    /**
     * 产品介绍图
     *
     * @Author: WuShuang on 2019/11/1  9:00
     * @param: [singleNum]
     * @return: java.lang.String
     * @Description:
     */
    String selectByThree(@Param("singleNum") String singleNum);

    /**
     * 产品特点
     *
     * @Author: WuShuang on 2019/11/1  9:19
     * @param: [matchmaker]
     * @return: java.util.List<com.pointcoil.dto.official.HomePageDTO.ProductCharacteristics>
     * @Description:
     */
    List<HomePageDTO.ProductCharacteristics> selectByFour(@Param("matchmaker") String matchmaker);

    /**
     * 新闻中心
     *
     * @Author: WuShuang on 2019/11/1  10:01
     * @param: [niceNum]
     * @return: java.util.List<com.pointcoil.dto.official.HomePageDTO.News>
     * @Description:
     */
    List<HomePageDTO.News> selectByNews(@Param("niceNum") String niceNum);

    /**
     * 关于我们
     *
     * @Author: WuShuang on 2019/11/1  10:12
     * @param: [sevenInt]
     * @return: com.pointcoil.dto.official.HomePageDTO.AboutUs
     * @Description:
     */
    HomePageDTO.AboutUs selectByAboutUs(@Param("sevenInt") String sevenInt);

    /**
     * 发展历史
     *
     * @Author: WuShuang on 2019/11/1  10:19
     * @param: [fiveInt]
     * @return: java.util.List<com.pointcoil.dto.official.HomePageDTO.History>
     * @Description:
     */
    List<HomePageDTO.History> selectByHistory(@Param("fiveInt") String fiveInt);

    /**
     * 合作伙伴
     *
     * @Author: WuShuang on 2019/11/1  10:23
     * @param: [sixString]
     * @return: java.util.List<com.pointcoil.dto.official.HomePageDTO.Partner>
     * @Description:
     */
    List<HomePageDTO.Partner> selectByPartner(@Param("sixString") String sixString);

    /**
     * 新闻列表
     *
     * @param niceNum
     * @Author: WuShuang on 2019/11/1  12:44
     * @param: []
     * @return: java.util.List<com.pointcoil.dto.official.HomePageDTO.News>
     * @Description:
     */
    Page<HomePageDTO.NewsByLimit> selectNewsAll(@Param("niceNum") String niceNum);

    /**
     * 新闻详情
     *
     * @Author: WuShuang on 2019/11/1  14:38
     * @param: [newsId]
     * @return: java.lang.String
     * @Description:
     */
    String newsDetails(@Param("newsId") String newsId);

    /**
     * 官网品牌数量+1
     *
     * @param f0cb9bba1bdc11ea97920242ac110003
     * @param insertLength
     * @Author: WuShuang on 2020/3/11  10:33
     * @param: []
     * @return: void
     * @Description:
     */
    void addBrand(@Param("f0cb9bba1bdc11ea97920242ac110003") String f0cb9bba1bdc11ea97920242ac110003, @Param("insertLength") int insertLength);
}
