package com.pointcoil.mongo.Impl;

import com.pointcoil.dto.geo.*;
import com.pointcoil.dto.map.*;
import org.springframework.data.geo.*;
import com.pointcoil.mongo.MongoDbDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by WuShuang on 2019/11/20.
 */
@Service
public class MongoDbDaoImpl implements MongoDbDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void delete(String city,String tbl){
        Query query = new Query(Criteria.where("city").is(city));
        mongoTemplate.remove(query,tbl);
    }

    @Override
    public List<HousingWorldDB> insertCollectionByHousingWorld(List<HousingWorldDB> list) {
        return (List<HousingWorldDB>) mongoTemplate.insert(list, HousingWorldDB.class);
    }

    @Override
    public List<ChainFamilyTwoRoomDB> insertCollectionByChainFamilyTwoRoom(List<ChainFamilyTwoRoomDB> list) {
        return (List<ChainFamilyTwoRoomDB>) mongoTemplate.insert(list, ChainFamilyTwoRoomDB.class);
    }

    @Override
    public List<ChainFamilyTurnoverDB> insertCollectionByChainFamilyTurnover(List<ChainFamilyTurnoverDB> list) {
        return (List<ChainFamilyTurnoverDB>) mongoTemplate.insert(list, ChainFamilyTurnoverDB.class);
    }

    @Override
    public LocationPO.TestLocation insertCollectionByCoordinates(List<BusinessZoneDTO.Coordinate> coordinates, String replace) {

        List<LocationPO> locationList = new ArrayList<>();
        for (BusinessZoneDTO.Coordinate s : coordinates) {
            LocationPO locationPO = new LocationPO(s.getLng(), s.getLat());
            locationList.add(locationPO);
        }
        LocationPO.TestLocation testLocation = new LocationPO.TestLocation(locationList, replace);

        return mongoTemplate.insert(testLocation);
    }


    public List<SiteExt> findPointInPolygon(GeoJsonPolygon geoJsonPolygon, String collectionName) {
        Query query = new Query(Criteria.where("location").within(geoJsonPolygon));
        List<SiteExt> list = mongoTemplate.find(query, SiteExt.class, collectionName);
        return list;
    }

    @Override
    public void insert1(SiteExt2 siteExt1, String tbl_chainfamily_tworoom) {
        mongoTemplate.insert(siteExt1, tbl_chainfamily_tworoom);
    }

    @Override
    public void insert2(List<ChainFamilyDTO> list, String tbl_chainfamily_turnover, String cityName) {

        for(ChainFamilyDTO s:list){
            if(s.getHouseholds() != null ){
                if(s.getHouseholds().equals("0户")){
                    s.setHouseholds("500户");
                }
                if(!StringUtils.isEmpty(s.getLon()) && !StringUtils.isEmpty(s.getLat())){
                    GeoJsonPoint geoJsonPoint = new GeoJsonPoint(new Point(Double.valueOf(s.getLon()),Double.valueOf(s.getLat())));
                    String substring = s.getHouseholds().substring(0, s.getHouseholds().length() - 1);
                    SiteExt3 siteExt1 = new SiteExt3(UUID.randomUUID().toString().replace("-",""),substring,cityName,geoJsonPoint);
                    mongoTemplate.insert(siteExt1, tbl_chainfamily_turnover);
                }
            }
        }

    }

    @Override
    public List<SiteExt2> geoNear(BusinessZoneDTO.Coordinate businessZoneDTO, String radius, String tableName) {
        Point point = new Point(businessZoneDTO.getLng(), businessZoneDTO.getLat());
        Distance distance = new Distance(Double.valueOf(radius) / 1000, Metrics.KILOMETERS);
        //画一个圆
        Circle circle = new Circle(point, distance);
        Criteria cc = Criteria.where("location").withinSphere(circle);
        Query query = new Query(cc);
        List<SiteExt2> list = mongoTemplate.find(query, SiteExt2.class, tableName);
        return list;
    }

    @Override
    public void insert3(List<GouldDTO> siteExt4, String tbl_gould) {
       // mongoDbDao.insert3(siteExt4,"tbl_gould");
        String rex = "^[1-9]\\d*(\\.\\d+)?$";
        for(GouldDTO s:siteExt4){
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(s.getBaiduLon()) &&org.apache.commons.lang3.StringUtils.isNotEmpty(s.getBaiduLat())  ){
                Pattern p = Pattern.compile(rex);
                Matcher lon = p.matcher(s.getBaiduLon());
                Matcher lat = p.matcher(s.getBaiduLat());
                if(lon.find() && lat.find()){
                    GeoJsonPoint geoJsonPoint = new GeoJsonPoint(new Point(Double.parseDouble(s.getBaiduLon()),Double.parseDouble(s.getBaiduLat())));
                    SiteExt4 siteExt = new SiteExt4(UUID.randomUUID().toString().replace("-",""),s.getName(),s.getCity(),s.getClassification(),geoJsonPoint);
                    mongoTemplate.insert(siteExt, tbl_gould);
                }
            }
        }
    }

    @Override
    public List<SiteExt4> findPointInPolygon4(GeoJsonPolygon geoJsonPolygon, String tbl_gould) {
        Query query = new Query(Criteria.where("location").within(geoJsonPolygon));
        List<SiteExt4> list = mongoTemplate.find(query, SiteExt4.class, tbl_gould);
        return list;
    }

    @Override
    public List<SiteExt4> geoNear1(BusinessZoneDTO.Coordinate coordinate, String radius, String tableName) {
        Point point = new Point(coordinate.getLng(), coordinate.getLat());
        Distance distance = new Distance(Double.valueOf(radius) / 1000, Metrics.KILOMETERS);
        //画一个圆
        Circle circle = new Circle(point, distance);
        Criteria cc = Criteria.where("location").withinSphere(circle);
        Query query = new Query(cc);
        List<SiteExt4> list = mongoTemplate.find(query, SiteExt4.class, tableName);
        return list;
    }

    @Override
    public void insert4(List<ThermodynamicDTO> siteExt5, String tbl_industry, String cityName) {
        String rex = "^[1-9]\\d*(\\.\\d+)?$";
        for(ThermodynamicDTO s:siteExt5){
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(s.getWgs_lng()) &&org.apache.commons.lang3.StringUtils.isNotEmpty(s.getWgs_lat())  ) {
                Pattern p = Pattern.compile(rex);
                Matcher lon = p.matcher(s.getWgs_lng());
                Matcher lat = p.matcher(s.getWgs_lat());
                if(lon.find() && lat.find()) {
                        GeoJsonPoint geoJsonPoint = new GeoJsonPoint(new Point(Double.parseDouble(s.getWgs_lng()),Double.parseDouble(s.getWgs_lat())));
                        SiteExt5 siteExt6 = new SiteExt5(UUID.randomUUID().toString().replace("-",""),s.getFirstClassClassification(),s.getTwoLevelClassification(),cityName,geoJsonPoint);
                        mongoTemplate.insert(siteExt6, tbl_industry);
                }
            }
        }
    }


    @Override
    public List<SiteExt6> findPointInIndustry(GeoJsonPolygon geoJsonPolygon, String tbl_industry) {
        Criteria location = Criteria.where("location").within(geoJsonPolygon);
        Aggregation aggregation1 = Aggregation.newAggregation(Aggregation.match(location),
                Aggregation.group("oneIndustry").first("oneIndustry").as("oneIndustry").first("oneIndustry").as("oneIndustry").count().as("value"));

        AggregationResults<SiteExt6> aggregate = mongoTemplate.aggregate(aggregation1, tbl_industry, SiteExt6.class);
        return aggregate.getMappedResults();
    }


    @Override
    public List<SiteExt6> industrySearch1(BusinessZoneDTO.Coordinate coordinate, String radius, String tbl_industry) {
        Point point = new Point(coordinate.getLng(), coordinate.getLat());
        Distance distance = new Distance(Double.valueOf(radius) / 1000, Metrics.KILOMETERS);
        //画一个圆
        Circle circle = new Circle(point, distance);
        Criteria cc = Criteria.where("location").withinSphere(circle);
        Aggregation aggregation1 = Aggregation.newAggregation(Aggregation.match(cc),
                Aggregation.group("oneIndustry").first("oneIndustry").as("oneIndustry").first("oneIndustry").as("oneIndustry").count().as("value"));
        AggregationResults<SiteExt6> list = mongoTemplate.aggregate(aggregation1, tbl_industry, SiteExt6.class);
        return list.getMappedResults();
    }

    @Override
    public List<SiteExt6> industrySearch2(GeoJsonPolygon geoJsonPolygon, String tbl_industry) {
        Criteria location = Criteria.where("location").within(geoJsonPolygon);
        Aggregation aggregation1 = Aggregation.newAggregation(Aggregation.match(location),
                Aggregation.group("twoIndustry").first("twoIndustry").as("twoIndustry").first("twoIndustry").as("twoIndustry").count().as("value"));
        AggregationResults<SiteExt6> aggregate = mongoTemplate.aggregate(aggregation1, tbl_industry, SiteExt6.class);
        return aggregate.getMappedResults();
    }

    @Override
    public List<SiteExt6> industrySearch3(BusinessZoneDTO.Coordinate coordinate, String radius, String tbl_industry) {
        Point point = new Point(coordinate.getLng(), coordinate.getLat());
        Distance distance = new Distance(Double.valueOf(radius) / 1000, Metrics.KILOMETERS);
        //画一个圆
        Circle circle = new Circle(point, distance);
        Criteria cc = Criteria.where("location").withinSphere(circle);
        Aggregation aggregation1 = Aggregation.newAggregation(Aggregation.match(cc),
                Aggregation.group("twoIndustry").first("twoIndustry").as("twoIndustry").first("twoIndustry").as("twoIndustry").count().as("value"));
        AggregationResults<SiteExt6> list = mongoTemplate.aggregate(aggregation1, tbl_industry, SiteExt6.class);
        return list.getMappedResults();
    }

    public void insert(SiteExt siteExt, String collectionName) {
        mongoTemplate.insert(siteExt, collectionName);
    }


    public void insertBatch(List<SiteExt> list, String collectionName) {

        // BulkMode.UNORDERED:表示并行处理，遇到错误时能继续执行不影响其他操作；BulkMode.ORDERED：表示顺序执行，遇到错误时会停止所有执行
        BulkOperations ops = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, collectionName);
        ops.insert(list);

        // 执行操作
        ops.execute();
    }


    public List<SiteExt> findAll(String collectionName) {
        Query query = new Query();
        List<SiteExt> list = mongoTemplate.find(query, SiteExt.class, collectionName);
        return list;
    }
}
