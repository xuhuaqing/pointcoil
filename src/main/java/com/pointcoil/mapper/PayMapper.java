package com.pointcoil.mapper;

import com.pointcoil.dto.map.AliPayDTO;
import com.pointcoil.dto.map.MapCommonDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Created by WuShuang on 2019/12/26.
 */
public interface PayMapper {
    /**
     * 支付记录
     *
     * @Author: WuShuang on 2019/12/26  13:57
     * @param: [aliPayDTO, card]
     * @return: void
     * @Description:
     */
    void aliPay(@Param("aliPayDTO") AliPayDTO aliPayDTO, @Param("card") String card, @Param("s") String s,@Param("i") int i);

    /**
     * 支付了
     *
     * @Author: WuShuang on 2019/12/26  14:17
     * @param: [trad_no]
     * @return: void
     * @Description:
     */
    void pay(@Param("trad_no") String trad_no);

    /**
     * 改变权限
     *
     * @Author: WuShuang on 2019/12/26  14:19
     * @param: [trad_no]
     * @return: void
     * @Description:
     */
    void updateMember(@Param("userId") String userId, @Param("cityName") String cityName, @Param("s") String s);

    /**
     * 查询是否支付
     *
     * @Author: WuShuang on 2019/12/26  15:05
     * @param: [mapCommonDTO]
     * @return: void
     * @Description:
     */
    Integer select(@Param("mapCommonDTO") MapCommonDTO mapCommonDTO);

    /**
     * 查询userId
     *
     * @Author: WuShuang on 2019/12/27  9:48
     * @param: [trad_no]
     * @return: java.lang.String
     * @Description:
     */
    String selectUserId(@Param("trad_no") String trad_no);

    /**
     * 查询金额
     *
     * @Author: WuShuang on 2020/3/11  18:41
     * @param: [memberId]
     * @return: java.lang.Double
     * @Description:
     */
    Double selectMember(@Param("memberId") String memberId, @Param("year") String year);

    /**
     * 查询时间 和 城市
     *
     * @param trad_no
     * @return java.util.Map<java.lang.String , java.lang.String>
     * @methodName findCityAndTime
     * @author WuShunag
     * @date 0:03
     */
    Map<String, String> findCityAndTime(@Param("trad_no") String trad_no);

    /**
     * 用户是否购买过
     *
     * @param s
     * @param userId
     * @return java.lang.Integer
     * @methodName purchased
     * @author WuShunag
     * @date 0:19
     */
    Integer purchased(@Param("s") String s, @Param("userId") String userId);

    void updaMemberTime(@Param("userId") String userId, @Param("time") String time, @Param("s") String s);

    String get(@Param("trad_no") String trad_no);
}
