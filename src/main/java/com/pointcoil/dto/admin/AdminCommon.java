package com.pointcoil.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:WuShuang
 * @date:2019/10/28
 * @ver:1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminCommon {
    private String userName;
    private String passWord;
    private String userRole;
}
