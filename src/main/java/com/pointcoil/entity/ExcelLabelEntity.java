package com.pointcoil.entity;

import com.pointcoil.util.ExcelColumn;
import lombok.Data;

/**
 * Created by WuShuang on 2019/12/13.
 */
@Data
public class ExcelLabelEntity {
    @ExcelColumn(value = "labelName",col = 1)
    private String labelName;
    @ExcelColumn(value = "labelStyleId",col = 2)
    private String labelStyleId;
    @ExcelColumn(value = "latAndLng",col = 3)
    private String latAndLng;


}
