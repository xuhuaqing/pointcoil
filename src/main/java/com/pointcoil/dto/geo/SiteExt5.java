package com.pointcoil.dto.geo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by WuShuang on 2019/11/21.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tbl_industry")
//注意这里使用了2dsphere索引
@CompoundIndexes({
        @CompoundIndex(name = "location_index", def = "{'location': '2dsphere'}"),
})
public class SiteExt5 {
    @Id
    private String plateNo;

    private String oneIndustry;

    private String twoIndustry;

    private String city;

    private GeoJsonPoint location;

}