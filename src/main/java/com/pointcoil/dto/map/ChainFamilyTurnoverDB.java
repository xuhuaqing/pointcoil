package com.pointcoil.dto.map;

import com.pointcoil.util.ExcelColumn;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by WuShuang on 2019/11/20.
 */
@Data
@Document("tbl_chainfamily_turnover")
public class ChainFamilyTurnoverDB {
    @ExcelColumn(value = "卧室数量",col = 1)
    private String bedrooms_num;
    @ExcelColumn(value = "经度",col = 1)
    private Double lon;
    @ExcelColumn(value = "纬度",col = 1)
    private Double lat;
}
