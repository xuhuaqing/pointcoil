package com.pointcoil.service.adminmap.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pointcoil.dto.admin.AdminPageCommon;
import com.pointcoil.dto.map.BusinessZoneDTO;
import com.pointcoil.entity.CustomizedEntity;
import com.pointcoil.entity.ExcelBusinessEntity;
import com.pointcoil.entity.ExcelLabelEntity;
import com.pointcoil.mapper.MapAdminCustMapper;
import com.pointcoil.service.adminmap.MapAdminCustService;
import com.pointcoil.service.map.MapBusinessZoneService;
import com.pointcoil.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static com.pointcoil.constant.Constants.SUCCESS_ADMIN_CODE;
import static com.pointcoil.constant.Constants.SUCCESS_MSG;

/**
 * Created by WuShuang on 2019/12/12.
 */
@Service
@SuppressWarnings("ALL")
@Slf4j
public class MapAdminCustServiceImpl implements MapAdminCustService {

    @Autowired
    private MapAdminCustMapper mapAdminCustMapper;


    @Override
    public String findCustomized(AdminPageCommon adminPageCommon) {
        PageHelper.startPage(adminPageCommon.getPage(),adminPageCommon.getPageSize());
        Page<CustomizedEntity> mapUserEntities =  mapAdminCustMapper.findCustomized();
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, mapUserEntities, new HashMap<String,Object>(1) {
            {
                put("count", mapUserEntities.getTotal());
            }
        });
    }

    /**
     *删除定制信息
     *
     * @Author: WuShuang on 2019/12/12  14:35
     * @param: [id]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String delCustomized(String id) {
        mapAdminCustMapper.delCustomized(id);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }

    @Autowired
    private MapBusinessZoneService mapBusinessZoneService;
    /**
     * 创建定制商圈
     *
     * @Author: WuShuang on 2019/12/12  17:11
     * @param: [excelBusinessEntities, brandId]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String updateExcel(List<ExcelBusinessEntity> excelBusinessEntities, String brandId, String userId) {
        //mapAdminCustMapper.updateExcel(excelBusinessEntities,brandId);
        for (ExcelBusinessEntity excelBusinessEntity : excelBusinessEntities) {
            BusinessZoneDTO businessZoneDTO = new BusinessZoneDTO();

            businessZoneDTO.setBrandId(brandId);
            businessZoneDTO.setBusinessArea(excelBusinessEntity.getBusinessArea());
            businessZoneDTO.setBusinessDescribe(excelBusinessEntity.getBusinessDescribe());
            businessZoneDTO.setBusinessLevel(Integer.valueOf(excelBusinessEntity.getBusinessLevel()));
            businessZoneDTO.setBusinessName(excelBusinessEntity.getBusinessName());
            businessZoneDTO.setBusinessOpen(Integer.valueOf(excelBusinessEntity.getBusinessOpen()));
            businessZoneDTO.setBusinessType(Integer.valueOf(excelBusinessEntity.getBusinessType()));
            businessZoneDTO.setBusinessUnsold(Integer.valueOf(excelBusinessEntity.getBusinessUnsold()));
            businessZoneDTO.setCity(excelBusinessEntity.getCity());
            List<BusinessZoneDTO.Coordinate> coordinateList = JSONObject.parseArray(excelBusinessEntity.getCoordinates(), BusinessZoneDTO.Coordinate.class);
            businessZoneDTO.setCoordinates(coordinateList);
            businessZoneDTO.setCustomizedId(excelBusinessEntity.getCustomizedId());
            businessZoneDTO.setDistrict(excelBusinessEntity.getDistrict());
            businessZoneDTO.setProvince(excelBusinessEntity.getProvince());
            businessZoneDTO.setRadius(excelBusinessEntity.getRadius());
            businessZoneDTO.setScreenshotImg(excelBusinessEntity.getScreenshotImg());
            businessZoneDTO.setThermodynamicChart(excelBusinessEntity.getThermodynamicChart());
            businessZoneDTO.setUserId(userId);
            businessZoneDTO.setXxx("1");
            mapBusinessZoneService.createBusinessZone(businessZoneDTO);
        }
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }

    /**
     * 创建定制标签
     *
     * @Author: WuShuang on 2019/12/13  13:50
     * @param: [excelLabelEntities, brandId]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String updateExcelByLabel(List<ExcelLabelEntity> excelLabelEntities, String brandId, String userId) {
        mapAdminCustMapper.updateExcelByLabel(excelLabelEntities,brandId,userId);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }
}
