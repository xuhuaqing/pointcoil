package com.pointcoil.service.adminmap.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pointcoil.conf.PointCoilProperties;
import com.pointcoil.dto.admin.AdminPageCommon;
import com.pointcoil.dto.map.*;
import com.pointcoil.entity.BusClick;
import com.pointcoil.entity.ProvincesEntity;
import com.pointcoil.entity.RuleEntity;
import com.pointcoil.mapper.*;
import com.pointcoil.service.adminmap.MapAdminSystemService;
import com.pointcoil.util.Md5Util;
import com.pointcoil.util.RedisUtil;
import com.pointcoil.util.Response;
import com.pointcoil.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.pointcoil.constant.Constants.*;

/**
 * Created by WuShuang on 2019/12/13.
 */
@Service
@Slf4j
@SuppressWarnings("all")
public class MapAdminSystemServiceImpl implements MapAdminSystemService
{

    @Autowired
    private Thermodynamic_01Mapper thermodynamicMapper_01;

    @Autowired
    private Thermodynamic_02Mapper thermodynamicMapper_02;
    @Autowired
    private MapAdminSystemMapper mapAdminSystemMapper;

    @Autowired
    private MapLabelMapper mapLabelMapper;
    @Autowired
    private ThermodynamicMapper thermodynamicMapper;


    @Override
    public String findIndustryOne(AdminPageCommon adminPageCommon) {

        PageHelper.startPage(adminPageCommon.getPage(),adminPageCommon.getPageSize());
        Page<MapIndustryDTO> mapUserEntities =  mapAdminSystemMapper.findIndustryOne();
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, mapUserEntities, new HashMap<String,Object>(1) {
            {
                put("count", mapUserEntities.getTotal());
            }
        });
    }

    @Override
    public String findIndustryTwo(AdminPageCommon adminPageCommon, String industryId) {
        PageHelper.startPage(adminPageCommon.getPage(),adminPageCommon.getPageSize());
        Page<MapIndustryDTO.IndustryLevelTwo> mapUserEntities =  mapAdminSystemMapper.findIndustryTwo(industryId);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, mapUserEntities, new HashMap<String,Object>(1) {
            {
                put("count", mapUserEntities.getTotal());
            }
        });
    }

    @Override
    public String addIndustry(String industryName, String parentId) {
        mapAdminSystemMapper.addIndustry(industryName,parentId);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }

    @Override
    public String deleteIndustry(String industryName) {
        mapAdminSystemMapper.deleteIndustry(industryName);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }


    @Override
    public String updIndustry(String industryId, String industryName) {
        mapAdminSystemMapper.updIndustry(industryId,industryName);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }

    @Override
    public String findLabelImg(AdminPageCommon adminPageCommon) {
        PageHelper.startPage(adminPageCommon.getPage(),adminPageCommon.getPageSize());
        Page<LabelStyleDTO> mapUserEntities =  mapAdminSystemMapper.findLabelImg();
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, mapUserEntities, new HashMap<String,Object>(1) {
            {
                put("count", mapUserEntities.getTotal());
            }
        });
    }

    @Override
    public String labelAdd(String carouselImageUrl) {
        mapAdminSystemMapper.labelAdd(carouselImageUrl);
        //mapLabelMapper.labelAdd(carouselImageUrl);
        mapLabelMapper.clear();
        mapAdminSystemMapper.clear();
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }

    @Override
    public String deleteLabel(String labelStyleId) {
        mapAdminSystemMapper.deleteLabel(labelStyleId);
        mapLabelMapper.clear();
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }

    @Override
    public String findLabelById(String labelStyleId) {
        LabelStyleDTO mapUserEntities =  mapAdminSystemMapper.findLabelById(labelStyleId);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG,mapUserEntities);
    }

    @Override
    public String updLabel(String labelStyleId, String carouselImageUrl) {
        mapAdminSystemMapper.updLabel(labelStyleId,carouselImageUrl);
        mapLabelMapper.clear();
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }

    @Override
    public String changePassword(String oldPassword, String password, String rePassword) {

       Integer i = mapAdminSystemMapper.selectOldPas(Md5Util.getMD5(oldPassword));

       if(i == 0){
           return ResponseUtil.toClient(ERROR_CODE+"","原密码错误！");
       }

       if(!password.equals(rePassword)){
           return ResponseUtil.toClient(ERROR_CODE+"","两次密码不一致！");
       }
        mapAdminSystemMapper.changePassword(Md5Util.getMD5(password));
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);

    }

    @Override
    public String findMember() {
        Page<MemberDTO> memberDTOS = mapAdminSystemMapper.findMember();
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, memberDTOS, new HashMap<String,Object>(1) {
            {
                put("count", memberDTOS.getTotal());
            }
        });
    }

    /**
     * 修改会员数据
     *
     * @Author: WuShuang on 2019/12/16  14:19
     * @param: [memberId, key, value]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String updateMember(String memberId, String key, String value) {
        mapAdminSystemMapper.updateMember(memberId,key,value);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }

    /**
     * 点评杭州热力图数据
     *
     * @Author: WuShuang on 2019/12/16  15:28
     * @param: [adminPageCommon]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String findThermodynamic(AdminPageCommon adminPageCommon, String city) {
        PageHelper.startPage(adminPageCommon.getPage(),adminPageCommon.getPageSize());
        Page<ProvincesEntity> mapUserEntities =  mapAdminSystemMapper.findThermodynamic(city);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, mapUserEntities, new HashMap<String,Object>(1) {
            {
                put("count", mapUserEntities.getTotal());
            }
        });
    }

    @Override
    public String findCityName(AdminPageCommon adminPageCommon, String cityName) {
        PageHelper.startPage(adminPageCommon.getPage(),adminPageCommon.getPageSize());
        Page<ThermodynamicDTO.ThermodynamicDTO1> mapUserEntities =  mapAdminSystemMapper.findCityName(cityName);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, mapUserEntities, new HashMap<String,Object>(1) {
            {
                put("count", mapUserEntities.getTotal());
            }
        });
    }

    @Override
    public String findAreCityName(AdminPageCommon adminPageCommon, String cityName) {
        PageHelper.startPage(adminPageCommon.getPage(),adminPageCommon.getPageSize());
        Page<AreYouHungryDTO> mapUserEntities =  mapAdminSystemMapper.findAreCityName(cityName);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, mapUserEntities, new HashMap<String,Object>(1) {
            {
                put("count", mapUserEntities.getTotal());
            }
        });
    }

    @Override
    public String findChainCityName(AdminPageCommon adminPageCommon, String cityName) {
        PageHelper.startPage(adminPageCommon.getPage(),adminPageCommon.getPageSize());
        Page<ChainFamilyDTO> mapUserEntities =  mapAdminSystemMapper.findChainCityName(cityName);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, mapUserEntities, new HashMap<String,Object>(1) {
            {
                put("count", mapUserEntities.getTotal());
            }
        });
    }

    @Override
    public String findRule(AdminPageCommon adminPageCommon, String cityName, String type) {
        PageHelper.startPage(adminPageCommon.getPage(),adminPageCommon.getPageSize());
        Page<RuleEntity> mapUserEntities =  mapAdminSystemMapper.findRule(cityName,type);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, mapUserEntities, new HashMap<String,Object>(1) {
            {
                put("count", mapUserEntities.getTotal());
            }
        });
    }

    @Override
    public String ruleAdd(RuleEntity ruleEntity) {
        mapAdminSystemMapper.ruleAdd(ruleEntity);
        thermodynamicMapper_02.upd();
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }

    /**
     * 导出商圈
     *
     * @Author: WuShuang on 2019/12/23  19:07
     * @param: [businessId]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public Response exportExcel(String[] businessId) {
        List<BusClick.ExportExcel> exportExcel = mapAdminSystemMapper.exportExcel(businessId);
        return new Response(SUCCESS_ADMIN_CODE,exportExcel);
        //return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG,exportExcel);
    }

    /**
     * 高德数据
     *
     * @Author: WuShuang on 2019/12/30  13:56
     * @param: [adminPageCommon, cityName]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String findGouldCityData(AdminPageCommon adminPageCommon, String cityName) {
        PageHelper.startPage(adminPageCommon.getPage(),adminPageCommon.getPageSize());
        Page<GouldDTO> mapUserEntities =  mapAdminSystemMapper.findGouldCityData(cityName);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, mapUserEntities, new HashMap<String,Object>(1) {
            {
                put("count", mapUserEntities.getTotal());
            }
        });
    }

    /**
     * 导出用户数据
     *
     * @Author: WuShuang on 2020/3/11  16:31
     * @param: [id]
     * @return: com.pointcoil.util.Response
     * @Description:
     */
    @Override
    public Response exportExcelByUser(String[] id) {
        List<BusClick.ExportExcelByUser> exportExcel = mapAdminSystemMapper.exportExcelByUser(id);
        return new Response(SUCCESS_ADMIN_CODE,exportExcel);
    }

    @Override
    public String order(AdminPageCommon adminPageCommon, String createTime) {
        PageHelper.startPage(adminPageCommon.getPage(),adminPageCommon.getPageSize());
        Page<OrderDTO> mapUserEntities =  mapAdminSystemMapper.order(createTime);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, mapUserEntities, new HashMap<String,Object>(1) {
            {
                put("count", mapUserEntities.getTotal());
            }
        });
    }

    @Override
    public String updateCustom(String id, String appId, String secret) {
        mapAdminSystemMapper.updateCustom(id,appId,secret);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }

    @Override
    public String findCustomId(String id) {
        Map<String,String> stringStringMap = mapAdminSystemMapper.findCustomId(id);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, stringStringMap
        );
    }

    @Override
    public String updateRule(String id, String firstClassClassification, String twoLevelClassification, String threeLevelClassification1, String minCount) {
        mapAdminSystemMapper.updateRule(id,firstClassClassification,twoLevelClassification,threeLevelClassification1,minCount);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG
        );
    }

    @Override
    public String findThermodynamicByUserId(String id) {
        List<Map<String,String>> list = mapAdminSystemMapper.findThermodynamicByUserId(id);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG,list);
    }

    @Override
    public String getSearchCity(String cityName) {
       List<Map<String,String>> strings =  mapAdminSystemMapper.getSearchCity(cityName);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG,strings);
    }


    @Autowired
    private MapUserMapper mapUserMapper;
    @Override
    public String thermodynamicUserAdd(String[] cityName, String date, String userId) {

        if(cityName[0].equals("0")){
            ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
            cachedThreadPool.execute(() -> {
                List<String> strings =   mapAdminSystemMapper.findCityNameAll();
                List<String> city = new ArrayList<>();
                for (String o :strings){
                    Integer i = mapAdminSystemMapper.isCity(userId,o);
                    if(i>0){
                        continue;
                    }else {
                        city.add(o);
                    }
                }
                if(!city.isEmpty()){
                    String[] split = date.split(" - ");
                    String startTime = split[0];
                    String endTime = split[1];
                    mapAdminSystemMapper.thermodynamicUserAdd(userId,city,startTime,endTime);
                    /*Integer memberById = mapAdminSystemMapper.findMemberById(userId) ;
                    if(memberById == 0){
                        //todo 因为 热力图前端不熊修改  要走后台  所以只能给他添加会员等级
                        mapUserMapper.updateMember(userId,startTime,endTime);
                    }*/
                }
            });
            return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
        }



        List<String> strings = mapAdminSystemMapper.findCityNameById(cityName);
        List<String> city = new ArrayList<>();
        for (String o :strings){
            Integer i = mapAdminSystemMapper.isCity(userId,o);
            if(i>0){
                continue;
            }else {
                city.add(o);
            }
        }
        if(!city.isEmpty()){
            String[] split = date.split(" - ");
            String startTime = split[0];
            String endTime = split[1];
            mapAdminSystemMapper.thermodynamicUserAdd(userId,city,startTime,endTime);
            Integer memberById = mapAdminSystemMapper.findMemberById(userId) ;
            if(memberById == 0){
                //todo 因为 热力图前端不熊修改  要走后台  所以只能给他添加会员等级
                mapUserMapper.updateMember(userId,startTime,endTime);
            }
        }
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }

    @Override
    public String deleteCity(String id) {
        mapAdminSystemMapper.deleteCity(id);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }


    @Override
    public String updateMemberCityTime(String id, String userId, String date) {
        String[] split = date.split(" - ");
        String startTime = split[0];
        String endTime = split[1];
        mapAdminSystemMapper.updateMemberCityTime(id,userId,startTime,endTime);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }

    /**
     *  删除所有数据
     *
     * @param cityName
     * @return java.lang.String
     * @methodName deleteCityData
     * @author WuShunag
     * @date 22:39
     */
    @Override
    public String deleteCityData(String cityName) {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(() -> {
            mapAdminSystemMapper.deleteCityData(cityName);
        });

        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }


    @Autowired
    private PointCoilProperties pointCoilProperties;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public String delUser(String id, String userPhone) {
        //redis 删用户审核
        String phoneSearch = pointCoilProperties.getRedis().getPhoneSearch();
        redisUtil.deleteHash(phoneSearch, userPhone);
        String businessInit = pointCoilProperties.getRedis().getBusinessInit();
        businessInit = businessInit+id;
        redisUtil.remove(businessInit);
        //删除用户
        mapAdminSystemMapper.deleteUser(id,userPhone);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }

    @Override
    public Response exportExcelByLabel(String[] labelId) {
        List<LabelDTO.SelectLabelById> exportExcel = mapAdminSystemMapper.exportExcelByLabel(labelId);
        return new Response(SUCCESS_ADMIN_CODE,exportExcel);
    }

    @Override
    public String findCustomizedThermodynamic(String userId, String type, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        Page<GouldDTO> gouldDTOS =  mapAdminSystemMapper.findCustomizedThermodynamic(userId,type);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, gouldDTOS, new HashMap<String,Object>(1) {
            {
                put("count", gouldDTOS.getTotal());
            }
        });
    }

    @Override
    public String deleteCustomizedThermodynamic(String userId, String type) {
        mapAdminSystemMapper.deleteCustomizedThermodynamic(userId,type);
        return ResponseUtil.successToClient();
    }
}
