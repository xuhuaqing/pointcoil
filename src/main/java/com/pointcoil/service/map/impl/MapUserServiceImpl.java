package com.pointcoil.service.map.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pointcoil.conf.PointCoilProperties;
import com.pointcoil.dto.map.*;
import com.pointcoil.mapper.MapBusinessMapper;
import com.pointcoil.mapper.MapUserMapper;
import com.pointcoil.service.map.MapUserService;
import com.pointcoil.util.MailUtils;
import com.pointcoil.util.RedisUtil;
import com.pointcoil.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import static com.pointcoil.constant.Constants.SUCCESS_ADMIN_CODE;
import static com.pointcoil.constant.Constants.SUCCESS_MSG;
import static com.pointcoil.constant.MsgConstant.*;

/**
 * Created by WuShuang on 2019/11/1.
 */
@Service
@Slf4j
@SuppressWarnings("ALL")
public class MapUserServiceImpl implements MapUserService {

    @Autowired
    private MapUserMapper mapUserMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private PointCoilProperties pointCoilProperties;

    @Autowired
    private MapBusinessMapper mapBusinessMapper;

    /**
     * 注册
     *
     * @Author: WuShuang on 2019/11/1  16:51
     * @param: [registerDTO]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String register(RegisterDTO registerDTO) {

        //判断是否注册过
        Integer i =  mapUserMapper.isRegister(registerDTO);
        if(i>0){
            return ResponseUtil.toClient(MSG_001001);
        }

        String toExamineEmail = pointCoilProperties.getRedis().getToExamineEmail();
        //添加数据
        mapUserMapper.register(registerDTO);
        //发送邮件
        List<String> strings = redisUtil.lRange(toExamineEmail);
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                MailUtils instance = MailUtils.getInstance();
                try {
                    instance.sendMultipleEmail(strings,registerDTO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return ResponseUtil.successToClient();
    }

    @Override
    public MapCommonDTO login(ISPhoneDTO.LoginDTO loginDTO) {
        return mapUserMapper.login(loginDTO);
    }

    /**
     *会员中心
     *
     * @Author: WuShuang on 2019/11/4  20:37
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String memberInit() {
        List<MemberDTO> memberDTO = mapUserMapper.selectMember();
        return ResponseUtil.successToClient(memberDTO);
    }

    /**
     * 我的商圈
     *
     * @Author: WuShuang on 2019/11/18  10:37
     * @param: [mapCommonDTO]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String myBusinessZone(MapPageDTO mapCommonDTO) {

        List<MyBusinessZoneDTO.MyBusinessZoneCity> myBusinessZoneCity =  mapUserMapper.myBusinessZoneCity(mapCommonDTO);

        PageHelper.startPage(Integer.valueOf(mapCommonDTO.getPage()),PAGE_SIZE);
        Page<MyBusinessZoneDTO.MyBusinessZone> myBusinessZones = mapUserMapper.myBusinessZone(mapCommonDTO);
        return ResponseUtil.toClient( new MyBusinessZoneDTO(myBusinessZones,myBusinessZoneCity), new HashMap<String,Object>(1) {
            {
                put("count", myBusinessZones.getTotal()/10+1);
            }
        });
    }

    /**
     *创建子账号
     *
     * @Author: WuShuang on 2019/11/18  14:44
     * @param: [mapSubAccountDTO]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String createSubAccount(MapSubAccountDTO mapSubAccountDTO) {

        String phoneSearch = pointCoilProperties.getRedis().getPhoneSearch();
        String isAccountSub = pointCoilProperties.getRedis().getIsAccountSub();

        //判断子账号
        String s = redisUtil.hGet(isAccountSub, mapSubAccountDTO.getUserId());
        if(!StringUtils.isEmpty(s)){
            return ResponseUtil.errorToClient(MSG_001015);
        }

        Integer o = (Integer) redisUtil.get(phoneSearch + mapSubAccountDTO.getUserId());
        if(o == null){
            o = 0;
        }
        System.err.println(o);
        MemberDTO memberDTO = mapBusinessMapper.selectCanCreateBrand(mapSubAccountDTO.getMemberLevel());
        /**
         * 判断子账号是否超出
         */
        if(memberDTO.getSubAccountNum()<=o){
            return ResponseUtil.errorToClient(MSG_001014);
        }

        /**
         * 判断账号是不是父账号
         */
        Integer i = mapBusinessMapper.isParent(mapSubAccountDTO.getAccountPhone());
        if(i>0){
            return ResponseUtil.errorToClient(MSG_001016);
        }

        //短信验证hash
        redisUtil.hSet(phoneSearch,mapSubAccountDTO.getAccountPhone()+"",System.currentTimeMillis()+"");
        //判断子账号 hash
        String replace = UUID.randomUUID().toString().replace("-", "");
        //是否子账号 判断
        redisUtil.hSet(isAccountSub,replace,System.currentTimeMillis()+"");
        // 子账号数量加一
        redisUtil.set(phoneSearch+mapSubAccountDTO.getUserId(),o+1);

        mapSubAccountDTO.setId(replace);
        TimeDTO timeDTO = new TimeDTO();
        if(Integer.valueOf(mapSubAccountDTO.getMemberLevel())>0){
            //查出时间
           timeDTO =  mapUserMapper.selectMemberByUserId(mapSubAccountDTO.getUserId());
        }

        mapUserMapper.createSubAccount(mapSubAccountDTO,timeDTO);

        return ResponseUtil.successToClient();
    }

    /**
     * 我的子账号
     *
     * @Author: WuShuang on 2019/11/18  15:50
     * @param: [mapSubAccountDTO]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String mySubAccount(MapPageDTO mapSubAccountDTO) {
        String isAccountSub = pointCoilProperties.getRedis().getIsAccountSub();

        //判断子账号
        String s = redisUtil.hGet(isAccountSub, mapSubAccountDTO.getUserId());
        if(!StringUtils.isEmpty(s)){
            return ResponseUtil.errorToClient(MSG_001015);
        }

        PageHelper.startPage(Integer.valueOf(mapSubAccountDTO.getPage()),PAGE_SIZE);
        Page<MapSubAccountDTO.SubAccountDTO> mapSubAccountDTOS =  mapUserMapper.selectMySubAccount(mapSubAccountDTO);

        return ResponseUtil.toClient( mapSubAccountDTOS, new HashMap<String,Object>(1) {
            {
                put("count", mapSubAccountDTOS.getTotal()/10+1);
                put("total", mapSubAccountDTOS.getTotal());
            }
        });
    }

    /**
     * 删除子账号
     *
     * @Author: WuShuang on 2019/11/18  16:25
     * @param: [subAccountDTO]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String deleteSubAccount(MapPageDTO.SubAccountDTO subAccountDTO) {
        String phone = mapUserMapper.selectAccountPhone(subAccountDTO);
        mapUserMapper.deleteSubAccount(subAccountDTO);
        String phoneSearch = pointCoilProperties.getRedis().getPhoneSearch();
        Integer o = (Integer) redisUtil.get(phoneSearch + subAccountDTO.getUserId());
        redisUtil.set(phoneSearch+subAccountDTO.getUserId(),o-1);
        //删除短信
        redisUtil.deleteHash(phoneSearch,phone);
        return ResponseUtil.successToClient();
    }

    /**
     * 添加定制信息
     *
     * @Author: WuShuang on 2019/11/18  18:12
     * @param: [customizedServiceDTO]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String customizedService(CustomizedServiceDTO customizedServiceDTO) {
        mapUserMapper.customizedService(customizedServiceDTO);
        return ResponseUtil.successToClient();
    }

    /**
     * 我的关注
     *
     * @Author: WuShuang on 2019/12/2  16:25
     * @param: [mapCommonDTO]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String myConcern(MapPageDTO mapCommonDTO) {
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(mapCommonDTO.getSonId())){
            PageHelper.startPage(Integer.valueOf(mapCommonDTO.getPage()),6);
            Page<Map<String,String>> mapList = mapUserMapper.myConcernBySonId(mapCommonDTO);
            return ResponseUtil.toClient( mapList, new HashMap<String,Object>(1) {
                {
                    put("count", mapList.getTotal()/6+1);
                    put("total", mapList.getTotal());
                }
            });
        }else {
            PageHelper.startPage(Integer.valueOf(mapCommonDTO.getPage()),6);
            Page<Map<String,String>> mapList = mapUserMapper.myConcern(mapCommonDTO);
            return ResponseUtil.toClient( mapList, new HashMap<String,Object>(1) {
                {
                    put("count", mapList.getTotal()/6+1);
                    put("total", mapList.getTotal());
                }
            });
        }
    }

    /**
     * 男女比例
     *
     * @Author: WuShuang on 2019/12/3  9:42
     * @param: [mapCommonDTO]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String pieChartBySix(BrandDTO.BrandId mapCommonDTO) {
        Map<String, String> i =  mapUserMapper.pieChartByWoMan(mapCommonDTO);
        Map<String, String> q =  mapUserMapper.pieChartByMan(mapCommonDTO);
        List<Map<String,String>> mapList = new ArrayList<>();
        mapList.add(i);
        mapList.add(q);
        return ResponseUtil.successToClient(mapList);
    }

    /**
     *用户关注品牌的地理分布图
     *
     * @Author: WuShuang on 2019/12/3  15:58
     * @param: [mapCommonDTO]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String distributionMap(BrandDTO.BrandId mapCommonDTO) {
        JSONObject jsonObject = new JSONObject();
        List<DistributionMapDTO> distributionMapDTOS = mapUserMapper.distributionMap(mapCommonDTO);
       if(distributionMapDTOS.isEmpty()){
           return ResponseUtil.successToClient(distributionMapDTOS);
       }
        List<BrandDTO.DistributionMap> hashMapArrayList1 = new ArrayList<>();
       for(DistributionMapDTO d:distributionMapDTOS){
           BrandDTO.DistributionMap distributionMap = new BrandDTO.DistributionMap();
           DistributionMapDTO.DistributionMapCity distributionMapCity = mapUserMapper.selectCity(d.getName());
           distributionMap.setName(distributionMapCity.getShortName());
           distributionMap.setValue(d.getValue());
           hashMapArrayList1.add(distributionMap);
       }
        return ResponseUtil.successToClient(hashMapArrayList1);
    }

    /**
     * 获取父类id
     *
     * @Author: WuShuang on 2019/12/4  14:15
     * @param: [userId]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String selectParentId(String userId) {
        return mapUserMapper.selectParentId(userId);
    }

    @Override
    public TimeDTO selectTime(String userId) {
        return mapUserMapper.selectTime(userId);
    }


    @Override
    public String memberCityInit(MapCommonDTO mapCommonDTO) {
        List<BigDecimal> bigDecimals = mapBusinessMapper.selectPrice();

        Map<String,Object> map = new HashMap<>(16);
        List<String> oneCity = mapUserMapper.findOneCity("一线");
        BigDecimal bigDecimal = bigDecimals.get(0);
        map.put("price",bigDecimal);
        map.put("city",oneCity);
        Map<String,Object> map1 = new HashMap<>(16);
        List<String> twoCity = mapUserMapper.findOneCity("新一线");
        List<String> twoCity1 = mapUserMapper.findOneCity("二线");
        twoCity.addAll(twoCity1);
        BigDecimal bigDecimal1 = bigDecimals.get(1);
        map1.put("price",bigDecimal1);
        map1.put("city",twoCity);

        List<CityDTO> cityDTOS = mapUserMapper.memberCityInit();
        BigDecimal bigDecimal2 = bigDecimals.get(2);
        cityDTOS.stream().forEach(s -> s.setPrice(bigDecimal2));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("oneCity",map);
        jsonObject.put("twoCity",map1);
        jsonObject.put("cityDTOS",cityDTOS);

        return ResponseUtil.successToClient(jsonObject);
    }
}
