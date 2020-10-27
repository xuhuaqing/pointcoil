package com.pointcoil.service.map;

import com.pointcoil.dto.map.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Created by WuShuang on 2019/11/1.
 */
public interface MapUserService {

    /**
     *注册
     *
     * @Author: WuShuang on 2019/11/1  16:49
     * @param: [registerDTO]
     * @return: java.lang.String
     * @Description:
     */
    String register(RegisterDTO registerDTO);

    /**
     *登陆
     *
     * @Author: WuShuang on 2019/11/4  9:41
     * @param: [loginDTO]
     * @return: java.lang.String
     * @Description:
     */
    MapCommonDTO login(ISPhoneDTO.LoginDTO loginDTO);

    /**
     *会员中心
     *
     * @Author: WuShuang on 2019/11/4  20:36
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    String memberInit();

    /**
     * 我的商圈
     *
     * @Author: WuShuang on 2019/11/18  10:37
     * @param: [mapCommonDTO]
     * @return: java.lang.String
     * @Description:
     */
    String myBusinessZone(MapPageDTO mapCommonDTO);

    /**
     * 创建子账号
     *
     * @Author: WuShuang on 2019/11/18  14:44
     * @param: [mapSubAccountDTO]
     * @return: java.lang.String
     * @Description:
     */
    String createSubAccount(MapSubAccountDTO mapSubAccountDTO);

    /**
     * 我的子账号
     *
     * @Author: WuShuang on 2019/11/18  15:49
     * @param: [mapSubAccountDTO]
     * @return: java.lang.String
     * @Description:
     */
    String mySubAccount(MapPageDTO mapSubAccountDTO);

    /**
     *删除子账号
     *
     * @Author: WuShuang on 2019/11/18  16:24
     * @param: [subAccountDTO]
     * @return: java.lang.String
     * @Description:
     */
    String deleteSubAccount(MapPageDTO.SubAccountDTO subAccountDTO);

    /**
     *添加定制服务信息
     *
     * @Author: WuShuang on 2019/11/18  18:12
     * @param: [customizedServiceDTO]
     * @return: java.lang.String
     * @Description:
     */
    String customizedService(CustomizedServiceDTO customizedServiceDTO);

    /**
     * 我的关注
     *
     * @Author: WuShuang on 2019/12/2  16:25
     * @param: [mapCommonDTO]
     * @return: java.lang.String
     * @Description:
     */
    String myConcern(MapPageDTO mapCommonDTO);

    /**
     * 男女饼状图
     *
     * @Author: WuShuang on 2019/12/3  9:41
     * @param: [mapCommonDTO]
     * @return: java.lang.String
     * @Description:
     */
    String pieChartBySix(BrandDTO.BrandId mapCommonDTO);

    /**
     *用户关注品牌的地理分布图
     *
     * @Author: WuShuang on 2019/12/3  15:58
     * @param: [mapCommonDTO]
     * @return: java.lang.String
     * @Description:
     */
    String distributionMap(BrandDTO.BrandId mapCommonDTO);

    /**
     *
     *
     * @Author: WuShuang on 2019/12/4  14:14
     * @param: [userId]
     * @return: java.lang.String
     * @Description:
     */
    String selectParentId(String userId);

    /**
     * 会员时间
     *
     * @Author: WuShuang on 2019/12/27  14:13
     * @param: [userId]
     * @return: java.util.Map<java.lang.String,java.time.LocalDateTime>
     * @Description:
     */
    TimeDTO selectTime(String userId);

    /**
     *  城市初始化
     *
     * @param mapCommonDTO
     * @return java.lang.String
     * @methodName memberCityInit
     * @author WuShunag
     * @date 22:08
     */
    String memberCityInit(MapCommonDTO mapCommonDTO);
}
