package com.pointcoil.dto.map;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by WuShuang on 2019/11/4.
 */
@Data
public class MemberDTO {

    private String memberId;
    private Integer memberLevel;
    private Integer businessNum;
    private Integer brandNum;
    private Integer labelNum;
    private Integer subAccountNum;
    private BigDecimal twoElement;
    private BigDecimal oneElement;
    private String customizedServer;
}
