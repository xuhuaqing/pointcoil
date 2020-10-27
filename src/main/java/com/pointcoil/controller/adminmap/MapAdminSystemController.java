package com.pointcoil.controller.adminmap;

import com.pointcoil.conf.PointCoilProperties;
import com.pointcoil.dto.admin.AdminPageCommon;
import com.pointcoil.dto.map.GouldDTO;
import com.pointcoil.entity.RuleEntity;
import com.pointcoil.mapper.*;
import com.pointcoil.service.adminmap.MapAdminSystemService;
import com.pointcoil.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.pointcoil.constant.Constants.SUCCESS_ADMIN_CODE;
import static com.pointcoil.constant.Constants.SUCCESS_MSG;

/**
 * Created by WuShuang on 2019/12/13.
 */
@RestController
@SuppressWarnings("ALL")
@RequestMapping("/system/")
@Slf4j
public class MapAdminSystemController {

    @Autowired
    private MapAdminSystemService mapAdminSystemService;

    @Autowired
    private Thermodynamic_01Mapper thermodynamicMapper_01;

    @Autowired
    private Thermodynamic_02Mapper thermodynamicMapper_02;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private PointCoilProperties pointCoilProperties;
    /**
     * 查询一级行业
     *
     * @Author: WuShuang on 2019/12/13  16:47
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("findIndustryOne")
    public String findIndustryOne(AdminPageCommon adminPageCommon){
        return mapAdminSystemService.findIndustryOne(adminPageCommon);
    }

    /**
     * 查询二级
     *
     * @Author: WuShuang on 2019/12/13  17:22
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("findIndustryTwo")
    public String findIndustryTwo(AdminPageCommon adminPageCommon,String industryId){
        return mapAdminSystemService.findIndustryTwo(adminPageCommon,industryId);
    }


    /**
     * 添加一级
     *
     * @Author: WuShuang on 2019/12/13  17:34
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("addIndustry")
    public String addIndustry(String industryName,@RequestParam(value = "industryId",required = false,defaultValue = "0") String parentId){
        System.err.println(industryName+","+parentId);
        return mapAdminSystemService.addIndustry(industryName,parentId);
    }

    /**
     * 删除
     *
     * @Author: WuShuang on 2019/12/14  13:16
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("deleteIndustry")
    public String deleteIndustry(@RequestParam(value = "industryId") String industryName){
        return mapAdminSystemService.deleteIndustry(industryName);
    }


    /**
     * 修改
     *
     * @Author: WuShuang on 2019/12/14  13:39
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("updIndustry")
    public String updIndustry(@RequestParam(value = "industryId") String industryId,@RequestParam(value = "industryName") String industryName){
        return mapAdminSystemService.updIndustry(industryId,industryName);
    }


    /**
     * 标签图片
     *
     * @Author: WuShuang on 2019/12/14  14:14
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("findLabelImg")
    public String findLabelImg(AdminPageCommon adminPageCommon){
        return mapAdminSystemService.findLabelImg(adminPageCommon);
    }


    /**
     * 添加标签
     *
     * @Author: WuShuang on 2019/12/14  14:30
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("labelAdd")
    public String labelAdd(String carouselImageUrl){
        return mapAdminSystemService.labelAdd(carouselImageUrl);
    }

    /**
     * 删除标签
     *
     * @Author: WuShuang on 2019/12/14  14:34
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("deleteLabel")
    public String deleteLabel(String labelStyleId){
        return mapAdminSystemService.deleteLabel(labelStyleId);
    }


    /**
     * 根据id 查询
     *
     * @Author: WuShuang on 2019/12/14  14:38
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("findLabelById")
    public String findLabelById(String labelStyleId){
        return mapAdminSystemService.findLabelById(labelStyleId);
    }

    /**
     * 修改
     *
     * @Author: WuShuang on 2019/12/14  14:41
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("updLabel")
    public String updLabel(String labelStyleId,String carouselImageUrl){
        return mapAdminSystemService.updLabel(labelStyleId,carouselImageUrl);
    }


    /**
     *修改密码
     *
     * @Author: WuShuang on 2019/12/16  9:45
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("changePassword")
    public String changePassword(String oldPassword,String password,String rePassword){
        return mapAdminSystemService.changePassword(oldPassword,password,rePassword);
    }

    /**
     * 邮箱列表
     *
     * @Author: WuShuang on 2019/12/16  9:58
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("findEmailList")
    public String findEmailList(){
        String toExamineEmail = pointCoilProperties.getRedis().getToExamineEmail();
        List<String> strings = redisUtil.lRange(toExamineEmail);
        List<Map<String,Object>> list = new ArrayList<>();

        for(String s:strings){
            HashMap<String, Object> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("email",s);
            list.add(objectObjectHashMap);
        }
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG,list);
    }

    /**
     * 删除邮箱
     *
     * @Author: WuShuang on 2019/12/16  10:23
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("deleteEmail")
    public String deleteEmail(String email){
        String toExamineEmail = pointCoilProperties.getRedis().getToExamineEmail();
        redisUtil.deleteList(toExamineEmail,1,email);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }

    /**
     * 添加邮箱
     *
     * @Author: WuShuang on 2019/12/16  10:34
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("addEmail")
    public String addEmail(String industryName){
        String toExamineEmail = pointCoilProperties.getRedis().getToExamineEmail();
        redisUtil.lPush(toExamineEmail,industryName);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }

    /**
     * 修改邮箱
     *
     * @Author: WuShuang on 2019/12/16  10:49
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("updateEmail")
    public String updateEmail(String oldEmail,String industryName){
        System.err.println(oldEmail+","+industryName);
        return null;
    }

    /**
     * 平台广告
     *
     * @Author: WuShuang on 2019/12/16  10:56
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("findMapPosterManage")
    public String findMapPosterManage(){
        String advertisement = redisUtil.get("advertisement").toString();
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG,advertisement);
    }

    /**
     * 修改平台广告
     *
     * @Author: WuShuang on 2019/12/16  10:58
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("updateMapPosterManage")
    public String updateMapPosterManage(String carouselImageUrl){
        redisUtil.set("advertisement",carouselImageUrl);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);

    }


    /**
     *会员查询
     *
     * @Author: WuShuang on 2019/12/16  13:52
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("findMember")
    public String findMember(){
        return mapAdminSystemService.findMember();
    }


    /**
     * 修改会员
     *
     * @Author: WuShuang on 2019/12/16  14:14
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("updateMember")
    public String updateMember(String memberId,String key,String value){
        return mapAdminSystemService.updateMember(memberId,key,value);
    }


    /**
     * 点评杭州的数据
     *
     * @Author: WuShuang on 2019/12/16  15:27
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("findThermodynamic")
    public String findThermodynamic(AdminPageCommon adminPageCommon,String city){
        return mapAdminSystemService.findThermodynamic(adminPageCommon,city);
    }


    /**
     *查询城市数据
     *
     * @Author: WuShuang on 2019/12/19  14:32
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("findCityName")
    public String findCityName(AdminPageCommon adminPageCommon,String cityName){
        return mapAdminSystemService.findCityName(adminPageCommon,cityName);
    }

    /**
     * 饿了吗数据
     *
     * @Author: WuShuang on 2019/12/19  16:51
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("findAreCityName")
    public String findAreCityName(AdminPageCommon adminPageCommon,String cityName){
        return mapAdminSystemService.findAreCityName(adminPageCommon,cityName);
    }


    /**
     * 查询链家城市数据
     *
     * @Author: WuShuang on 2019/12/20  13:50
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("findChainCityName")
    public String findChainCityName(AdminPageCommon adminPageCommon,String cityName){
        return mapAdminSystemService.findChainCityName(adminPageCommon,cityName);
    }

    /**
     * 高德数据
     *
     * @Author: WuShuang on 2019/12/30  13:56
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("findGouldCityData")
    public String findGouldCityData(AdminPageCommon adminPageCommon,String cityName){
        return mapAdminSystemService.findGouldCityData(adminPageCommon,cityName);
    }

    /**
     * 热力图规则
     *
     * @Author: WuShuang on 2019/12/20  15:53
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("findRule")
    public String findRule(AdminPageCommon adminPageCommon,String cityName,String type){
        return mapAdminSystemService.findRule(adminPageCommon,cityName,type);
    }

    /**
     * 添加规则
     *
     * @Author: WuShuang on 2019/12/23  10:44
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("ruleAdd")
    public String ruleAdd(RuleEntity ruleEntity){
        return mapAdminSystemService.ruleAdd(ruleEntity);
    }


    /**
     * 导出商圈
     *
     * @Author: WuShuang on 2019/12/23  19:05
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("exportExcel")
    public Response exportExcel(String [] businessId){
        return mapAdminSystemService.exportExcel(businessId);
    }


    /**
     * 导出标签
     *
     * @param
     * @return com.pointcoil.util.Response
     * @methodName exportExcelByLabel
     * @author WuShunag
     * @date 22:09
     */
    @PostMapping("exportExcelByLabel")
    public Response exportExcelByLabel(String [] labelId){
        return mapAdminSystemService.exportExcelByLabel(labelId);
    }
    /**
     * 导出用户信息
     *
     * @Author: WuShuang on 2020/3/11  16:28
     * @param: []
     * @return: com.pointcoil.util.Response
     * @Description:
     */
    @PostMapping("exportExcelByUser")
    public Response exportExcelByUser(String [] id){
        System.err.println(id);
        return mapAdminSystemService.exportExcelByUser(id);
    }

    /**
     *收入情况
     *
     * @Author: WuShuang on 2020/1/3  10:51
     * @param: []
     * @return: com.pointcoil.util.Response
     * @Description:
     */
    @GetMapping("getIncome")
    public Response getIncome(String endTime){
        return null;
    }

    /**
     * 订单
     *
     * @Author: WuShuang on 2020/3/23  10:53
     * @param: []
     * @return: com.pointcoil.util.Response
     * @Description:
     */
    @PostMapping("order")
    public String order(AdminPageCommon adminPageCommon,@RequestParam(required = false) String createTime){
        return mapAdminSystemService.order(adminPageCommon,createTime);
    }

    /**
     * 修改appId
     *
     * @Author: WuShuang on 2020/3/25  16:55
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("updateCustom")
    public String updateCustom(String id,String appId,String secret){
        return mapAdminSystemService.updateCustom(id,appId,secret);
    }

    /**
     * 查询appid
     *
     * @Author: WuShuang on 2020/3/25  17:07
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("findCustomId")
    public String findCustomId(String id){
        return mapAdminSystemService.findCustomId(id);
    }



    /**
     * 修改规则
     *
     * @Author: WuShuang on 2020/4/14  16:20
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("updateRule")
    public String updateRule(String id,String firstClassClassification,String twoLevelClassification,String threeLevelClassification,String minCount){
        return mapAdminSystemService.updateRule(id,firstClassClassification,twoLevelClassification,threeLevelClassification,minCount);
    }

    /**
     * 查询定制热力图列表
     *
     * @Author: WuShuang on 2020/4/17  11:07
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("findThermodynamicByUserId")
    public String findThermodynamicByUserId(String id){
        return mapAdminSystemService.findThermodynamicByUserId(id);
    }

    /**
     * 查询城市名称
     *
     * @Author: WuShuang on 2020/4/17  11:20
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @GetMapping("getSearchCity")
    public String getSearchCity(String cityName){
        return mapAdminSystemService.getSearchCity(cityName);
    }


    /**
     *
     *
     * @Author: WuShuang on 2020/4/20  10:00
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("deleteCity")
    public String deleteCity(String id){
        return mapAdminSystemService.deleteCity(id);
    }


    /**
     * 添加城市
     *
     * @Author: WuShuang on 2020/4/17  11:38
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("thermodynamicUserAdd")
    public String thermodynamicUserAdd(String [] cityName,String date,String userId){
          return mapAdminSystemService.thermodynamicUserAdd(cityName,date,userId);
    }

    /**
     * 修改时间
     *
     * @Author: WuShuang on 2020/4/21  17:27
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("updateMemberCityTime")
    public String updateMemberCityTime(String id,String userId,String date){
        return mapAdminSystemService.updateMemberCityTime(id,userId,date);
    }

    /**
     *  删除点评数据
     *
     * @param
     * @return java.lang.String
     * @methodName deleteCityData
     * @author WuShunag
     * @date 22:37
     */
    @PostMapping("deleteCityData")
    public String deleteCityData(String cityName){
        return mapAdminSystemService.deleteCityData(cityName);
    }

    /**
     *  删除用户
     *
     * @param
     * @return java.lang.String
     * @methodName delUser
     * @author WuShunag
     * @date 21:50
     */
    @PostMapping("delUser")
    public String delUser(String id,String userPhone){
        return mapAdminSystemService.delUser(id,userPhone);
    }



    /**
     *  定制热力图数据
     *
     * @param
     * @return java.lang.String
     * @methodName findCustomizedThermodynamic
     * @author WuShunag
     * @date 1:22
     */
    @PostMapping("findCustomizedThermodynamic")
    public String findCustomizedThermodynamic(String userId, String type,Integer page,Integer pageSize){
        return mapAdminSystemService.findCustomizedThermodynamic(userId,type,page,pageSize);
    }

    @Autowired
    private ExcelEventParserFive excelEventParserFive;
    @Autowired
    private OfficialWebsiteServiceMapper officialWebsiteServiceMapper;
    @Autowired
    private ExcelUploadMapper excelUploadMapper;
    @Autowired
    private Thermodynamic_03Mapper thermodynamicMapper_03;
    /**
     *  上传定制热力图数据
     *
     * @param
     * @return java.lang.String
     * @methodName uploadThermodynamicByUser
     * @author WuShunag
     * @date 1:51
     */
    @RequestMapping("uploadThermodynamicByUser")
    public String uploadThermodynamicByUser(@RequestParam("file") MultipartFile file, @RequestParam("type") String type, @RequestParam("userId") String userId){
        String file1 = file(file);

        try {
            excelEventParserFive.processFirstSheet(file1);
            List<GouldDTO> thermodynamicDTOS1 = ExcelEventParserFive.getThermodynamicDTOS();
            for (GouldDTO gouldDTO : thermodynamicDTOS1) {
                System.err.println(gouldDTO);
            }
            thermodynamicDTOS1.remove(0);
            int insertLength = thermodynamicDTOS1.size();
            /**
             * 首页数据
             */
            officialWebsiteServiceMapper.addBrand("4a2ed265fae911e9a2500242ac110003",insertLength);
            String homePageInit = pointCoilProperties.getRedis().getHomePageInit();
            redisUtil.remove(homePageInit);

            int i = 0;
            while (insertLength > 5000){
                List<GouldDTO> areYouHungryDTOS = thermodynamicDTOS1.subList(i, i + 5000);
                thermodynamicMapper_03.uploadThermodynamicByUser(areYouHungryDTOS,type,userId);
                i = i + 5000;
                insertLength = insertLength - 5000;
            }
            if (insertLength > 0) {
                thermodynamicMapper_03.uploadThermodynamicByUser(thermodynamicDTOS1.subList(i, i + insertLength),type,userId);
            }
            System.gc();
            thermodynamicDTOS1.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }

    public String file(MultipartFile file){
        String headName = file.getOriginalFilename();
        String headLastName = headName.substring(headName.lastIndexOf("."), headName.length());
        String headNewName = UUID.randomUUID() + headLastName;
        String path = pointCoilProperties.getImageUploadUrl();

        File file3 = new File(path + File.separator + headNewName);

        try {
            file.transferTo(file3);
            return file3.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 删除定制热力图
     *
     * @param
     * @return java.lang.String
     * @methodName deleteCustomizedThermodynamic
     * @author WuShunag
     * @date 23:32
     */
    @PostMapping("deleteCustomizedThermodynamic")
    public String deleteCustomizedThermodynamic(String userId,String type){
        return mapAdminSystemService.deleteCustomizedThermodynamic(userId,type);
    }
}
