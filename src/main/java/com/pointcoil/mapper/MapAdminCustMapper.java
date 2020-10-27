package com.pointcoil.mapper;

import com.github.pagehelper.Page;
import com.pointcoil.entity.CustomizedEntity;
import com.pointcoil.entity.ExcelBusinessEntity;
import com.pointcoil.entity.ExcelLabelEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by WuShuang on 2019/12/12.
 */
public interface MapAdminCustMapper {
    /**
     * 查询定制服务信息
     *
     * @Author: WuShuang on 2019/12/12  14:26
     * @param: []
     * @return: com.github.pagehelper.Page<com.pointcoil.entity.CustomizedEntity>
     * @Description:
     */
    Page<CustomizedEntity> findCustomized();

    /**
     * 删除定制信息
     *
     * @Author: WuShuang on 2019/12/12  14:36
     * @param: [id]
     * @return: void
     * @Description:
     */
    void delCustomized(@Param("id") String id);

    void updateExcel(@Param("list") List<ExcelBusinessEntity> excelBusinessEntities, @Param("brandId") String brandId);

    /**
     * @Author: WuShuang on 2019/12/13  13:52
     * @param: [excelLabelEntities, brandId]
     * @return: void
     * @Description:
     */
    void updateExcelByLabel(@Param("list") List<ExcelLabelEntity> excelLabelEntities, @Param("brandId") String brandId,@Param("userId") String userId);
}
