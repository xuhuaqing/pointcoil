package com.pointcoil.mapper;

import com.pointcoil.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by WuShuang on 2019/11/30.
 */
public interface SmallProMapper {
    /**
     * 保存用户信息
     *
     * @Author: WuShuang on 2019/11/30  13:49
     * @param: [user]
     * @return: void
     * @Description:
     */
    void insertPreservationUser(@Param("user") UserEntity user);

    /**
     * 查询有没有openId
     *
     * @Author: WuShuang on 2019/11/30  13:55
     * @param: [openId]
     * @return: java.lang.Integer
     * @Description:
     */
    Integer selectOpenId(@Param("openId") String openId);

    /**
     * 查询所有的appId
     *
     * @Author: WuShuang on 2020/3/28  10:25
     * @param: []
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     * @Description:
     */
    List<Map<String, String>> findAppId();
}
