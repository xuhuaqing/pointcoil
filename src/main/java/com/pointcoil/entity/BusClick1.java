package com.pointcoil.entity;

import com.pointcoil.util.ExcelColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by WuShuang on 2019/12/13.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusClick1 {


    @ExcelColumn(value = "标签名称", col = 1)
    private String 标签名称;

    @ExcelColumn(value = "标签描述", col = 2)
    private String 标签描述;

    @ExcelColumn(value = "标签样式ID", col = 3)
    private String 标签样式ID;

    @ExcelColumn(value = "纬度", col = 4)
    private String 纬度;

    @ExcelColumn(value = "经度", col = 5)
    private String 经度;

}
