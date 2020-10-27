package com.pointcoil.dto.map;

import com.pointcoil.util.ExcelColumn;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by WuShuang on 2019/11/20.
 */
@Data
@Document(collection = "tbl_housingworld")
public class HousingWorldDB {

    @ExcelColumn(value = "房屋总数",col = 12)
    private String houseNum;
    @ExcelColumn(value = "wgs_lon",col = 24)
    private Double wgsLon;
    @ExcelColumn(value = "wgs_lat",col = 25)
    private Double wgsLat;

}
