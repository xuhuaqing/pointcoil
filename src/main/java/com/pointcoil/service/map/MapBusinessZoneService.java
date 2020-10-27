package com.pointcoil.service.map;

import com.pointcoil.dto.map.BrandDTO;
import com.pointcoil.dto.map.BusinessZoneDTO;
import com.pointcoil.dto.map.MapCommonDTO;

/**
 * Created by WuShuang on 2019/11/5.
 */
public interface MapBusinessZoneService {

    /**
     *创建商圈
     *
     * @Author: WuShuang on 2019/11/5  21:07
     * @param: [businessZoneDTO]
     * @return: java.lang.String
     * @Description:
     */
    String createBusinessZone(BusinessZoneDTO businessZoneDTO);

    /**
     *展示商圈
     *
     * @Author: WuShuang on 2019/11/7  14:07
     * @param: [showBusinessZoneDTO]
     * @return: java.lang.String
     * @Description:
     */
    String initBusinessZone(BrandDTO.BrandId showBusinessZoneDTO);

    /**
     * 设置默认地址
     *
     * @Author: WuShuang on 2019/11/7  16:43
     * @param: [mapCommonDTO]
     * @return: java.lang.String
     * @Description:
     */
    String defaultAddress(BusinessZoneDTO.BusinessAddress mapCommonDTO);

    /**
     *商圈筛选
     *
     * @Author: WuShuang on 2019/11/8  10:06
     * @param: [showBusinessZoneDTO]
     * @return: java.lang.String
     * @Description:
     */
    String showBusinessZone(BusinessZoneDTO.ShowBusinessZoneDTO showBusinessZoneDTO);

    /**
     * 报告
     *
     * @Author: WuShuang on 2019/11/26  10:33
     * @param: [businessReport]
     * @return: java.lang.String
     * @Description:
     */
    String showBusinessReport(BusinessZoneDTO.BusinessReport businessReport);

    /**
     * 回显商圈数据
     *
     * @Author: WuShuang on 2019/12/5  18:51
     * @param: [showBusinessById]
     * @return: java.lang.String
     * @Description:
     */
    String showBusinessZoneById(BusinessZoneDTO.ShowBusinessById showBusinessById);

    /**
     * 修改商圈
     *
     * @Author: WuShuang on 2019/12/6  9:50
     * @param: [updateBusiness]
     * @return: java.lang.String
     * @Description:
     */
    String updateBusinessZone(BusinessZoneDTO.UpdateBusiness updateBusiness);

    /**
     * 缩放地图
     *
     * @Author: WuShuang on 2019/12/10  16:18
     * @param: [businessAddress]
     * @return: java.lang.String
     * @Description:
     */
    String zoomBusiness(BusinessZoneDTO.ZoomBusiness businessAddress);

    /**
     * 添加热力图截图
     *
     * @Author: WuShuang on 2019/12/23  11:04
     * @param: [pictureChar]
     * @return: java.lang.String
     * @Description:
     */
    String updThermodynamicChart(BusinessZoneDTO.PictureChar pictureChar);

    /**
     * 删除商圈
     *
     * @Author: WuShuang on 2020/1/9  16:24
     * @param: [businessZoneDTO]
     * @return: java.lang.String
     * @Description:
     */
    String deleteBusinessZone(BusinessZoneDTO.DeleteBusiness businessZoneDTO);
}
