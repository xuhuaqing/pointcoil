package com.pointcoil.dto.map;

import com.pointcoil.util.ExcelColumn;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

/**
 * Created by WuShuang on 2019/11/20.
 */
@Data
@Document(value = "tbl_chainfamily_tworoom")
public class ChainFamilyTwoRoomDB {
    @ExcelColumn(value = "卧室数量",col = 17)
    private Integer bedroomsNum;
    @ExcelColumn(value = "经度",col = 26)
    private Double lon;
    @ExcelColumn(value = "纬度",col = 28)
    private Double lat;
}
