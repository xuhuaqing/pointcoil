package com.pointcoil.dto.map;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by WuShuang on 2019/12/31.
 */
@Data
public class TaskDTO {

    private String id;
    private LocalDateTime endTime;

}
