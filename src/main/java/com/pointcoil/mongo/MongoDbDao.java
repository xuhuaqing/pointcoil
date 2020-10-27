package com.pointcoil.mongo;

import com.pointcoil.dto.geo.*;
import com.pointcoil.dto.map.*;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;

import java.util.List;

/**
 * Created by WuShuang on 2019/11/20.
 */
public interface MongoDbDao {

    List<HousingWorldDB> insertCollectionByHousingWorld(List<HousingWorldDB> list);

    List<ChainFamilyTwoRoomDB> insertCollectionByChainFamilyTwoRoom(List<ChainFamilyTwoRoomDB> list);

    List<ChainFamilyTurnoverDB> insertCollectionByChainFamilyTurnover(List<ChainFamilyTurnoverDB> list);

    LocationPO.TestLocation insertCollectionByCoordinates(List<BusinessZoneDTO.Coordinate> coordinates, String replace);

    void insert(SiteExt siteExt1, String siteExt);

    List<SiteExt> findPointInPolygon(GeoJsonPolygon geoJsonPolygon, String siteExt);

    void insert1(SiteExt2 siteExt1, String tbl_chainfamily_tworoom);

    void insert2(List<ChainFamilyDTO> list, String tbl_chainfamily_turnover, String cityName);

    List<SiteExt2> geoNear(BusinessZoneDTO.Coordinate businessZoneDTO, String radius,String tableName);

    void insert3(List<GouldDTO> siteExt4, String tbl_gould);

    List<SiteExt4> findPointInPolygon4(GeoJsonPolygon geoJsonPolygon, String tbl_gould);

    List<SiteExt4> geoNear1(BusinessZoneDTO.Coordinate coordinate, String radius, String tableName);

    void insert4(List<ThermodynamicDTO> siteExt5, String tbl_industry, String cityName);

    List<SiteExt6> findPointInIndustry(GeoJsonPolygon geoJsonPolygon, String tbl_industry);

    List<SiteExt6> industrySearch1(BusinessZoneDTO.Coordinate coordinate, String radius, String tbl_industry);

    List<SiteExt6> industrySearch2(GeoJsonPolygon geoJsonPolygon, String tbl_industry);

    List<SiteExt6> industrySearch3(BusinessZoneDTO.Coordinate coordinate, String radius, String tbl_industry);

    void delete(String city,String tbl);
}
