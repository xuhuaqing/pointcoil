package com.pointcoil.service.adminmap;

import com.pointcoil.dto.admin.AdminPageCommon;
import com.pointcoil.entity.ExcelBusinessEntity;
import com.pointcoil.entity.ExcelLabelEntity;

import java.util.List;

/**
 * Created by WuShuang on 2019/12/12.
 */
public interface MapAdminCustService {
    /**
     * 定制服务申请
     *
     * @Author: WuShuang on 2019/12/12  14:20
     * @param: [adminPageCommon]
     * @return: java.lang.String
     * @Description:
     */
    String findCustomized(AdminPageCommon adminPageCommon);

    /**
     * 删除定制信息
     *
     * @Author: WuShuang on 2019/12/12  14:35
     * @param: [id]
     * @return: java.lang.String
     * @Description:
     */
    String delCustomized(String id);

    /**
     * 创建定制商圈
     *
     * @Author: WuShuang on 2019/12/12  17:11
     * @param: [excelBusinessEntities, brandId]
     * @return: java.lang.String
     * @Description:
     */
    String updateExcel(List<ExcelBusinessEntity> excelBusinessEntities, String brandId, String userId);

    /**
     * 创建定制标签
     *
     * @Author: WuShuang on 2019/12/13  13:49
     * @param: [excelLabelEntities, brandId]
     * @return: java.lang.String
     * @Description:
     */
    String updateExcelByLabel(List<ExcelLabelEntity> excelLabelEntities, String brandId, String userId);
}
