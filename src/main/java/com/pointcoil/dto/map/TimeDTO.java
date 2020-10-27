package com.pointcoil.dto.map;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * Created by WuShuang on 2019/12/27.
 */
@Data
public class TimeDTO {


    private String starTime;

    private String endTime;
}
