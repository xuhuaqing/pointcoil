package com.pointcoil.service.officialwebsite;

import com.pointcoil.dto.official.NewsDTO;
import com.pointcoil.dto.official.PageCommon;

@SuppressWarnings("ALL")
/**
 * @author:WuShuang
 * @date:2019/10/28
 * @ver:1.0
 **/
public interface OfficialWebsiteService {
    /**
     *官网首页初始化
     *
     * @Author: WuShuang on 2019/10/31  16:34
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    String init();

    /**
     *新闻列表
     *
     * @Author: WuShuang on 2019/11/1  12:40
     * @param: []
     * @return: java.lang.String
     * @Description:
     * @param pageCommon
     */
    String initNews(PageCommon pageCommon);

    /**
     *新闻详情
     *
     * @Author: WuShuang on 2019/11/1  14:21
     * @param: [newsDTO]
     * @return: java.lang.String
     * @Description:
     */
    String newsDetails(NewsDTO newsDTO);

    /**
     * 行业初始化
     *
     * @Author: WuShuang on 2020/3/11  15:29
     * @param: [pageCommon]
     * @return: java.lang.String
     * @Description:
     */
    String initIndustry(PageCommon pageCommon);
}
