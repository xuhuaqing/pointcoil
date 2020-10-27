package com.pointcoil.dto.official;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by WuShuang on 2019/10/31.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomePageDTO {

    private String rotationChartImg;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FourPicture{
        private String FourPictureImg;
        private String numericalValue;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AboutUs{
        private String aboutUsTitle;
        private String aboutUsText;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Partner{
        private String partnerImages;
        private String partnerLink;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductCharacteristics{
        private String productImage;
        private String productTitle;
        private String productText;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class News {
        private String newsTitle;
        private String newsImg;
        private String newsText;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NewsByLimit {
        private String id;
        private String newsTitle;
        private String newsImg;
        private String newsText;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class History {
        private String year;
        private String event;
    }
}
