package com.pointcoil.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by WuShuang on 2019/10/30.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminPageCommon {

    private Integer page;
    private Integer pageSize;
    private String userPhone;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OfficialWebsite{
        private String id;
        private String pictureImg;
        private String pictureType;
        private String pictureConnect;
        private String pictureWords;
        private String pictureText;
        private String createTime;
        private String updateTime;
    }
}
