package com.pointcoil.service.SmallPro;

import com.pointcoil.dto.map.BusinessZoneDTO;
import com.pointcoil.entity.UserEntity;

/**
 * Created by WuShuang on 2019/11/30.
 */
public interface SmallProService {
    /**
     *保存信息
     *
     * @Author: WuShuang on 2019/11/30  13:48
     * @param: [user]
     * @return: java.lang.String
     * @Description:
     */
    String preservationUser(UserEntity user);

    /**
     *  查看商圈报告
     *
     * @Author: WuShuang on 2019/11/30  14:00
     * @param: [businessReport]
     * @return: java.lang.String
     * @Description:
     */
    String showBusinessReport(BusinessZoneDTO.BusinessReportApi businessReport);

    /**
     *我关注的商圈
     *
     * @Author: WuShuang on 2019/12/2  9:43
     * @param: [userEntity]
     * @return: java.lang.String
     * @Description:
     */
    String showMyFollowBusiness(UserEntity userEntity);

    /**
     * 查看商圈关注的人
     *
     * @Author: WuShuang on 2019/12/2  13:21
     * @param: [businessReport]
     * @return: java.lang.String
     * @Description:
     */
    String businessFollowPeo(BusinessZoneDTO.BusinessReportApi businessReport);
}
