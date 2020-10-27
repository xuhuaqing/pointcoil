package com.pointcoil.service.map;

import com.pointcoil.dto.map.BrandDTO;
import com.pointcoil.dto.map.MapCommonDTO;

/**
 * Created by WuShuang on 2019/11/4.
 */
public interface MapBusinessService {
    /**
     *初始化 行业init
     *
     * @Author: WuShuang on 2019/11/4  11:39
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    String industryInit();

    /**
     *创建品牌
     *
     * @Author: WuShuang on 2019/11/4  13:58
     * @param: [brandDTO]
     * @return: java.lang.String
     * @Description:
     */
    String addBrand(BrandDTO brandDTO);

    /**
     *修改品牌
     *
     * @Author: WuShuang on 2019/11/4  14:35
     * @param: [brandDTO]
     * @return: java.lang.String
     * @Description:
     */
    String updateBrand(BrandDTO.UpdateBrandDTO brandDTO);

    /**
     *回显要修改的
     *
     * @Author: WuShuang on 2019/11/4  14:52
     * @param: [brandDTO]
     * @return: java.lang.String
     * @Description:
     */
    String selectBrand(BrandDTO.BrandId brandDTO);

    /**
     * 商圈首页初始化
     *
     * @Author: WuShuang on 2019/11/4  15:37
     * @param: [mapCommonDTO]
     * @return: java.lang.String
     * @Description:
     */
    String businessInit(MapCommonDTO mapCommonDTO);

    /**
     *删除商圈
     *
     * @Author: WuShuang on 2019/11/4  16:46
     * @param: [brandDTO]
     * @return: java.lang.String
     * @Description:
     */
    String deleteBrand(BrandDTO.BrandId brandDTO);
}
