package com.pointcoil.service.adminmap.impl;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pointcoil.conf.PointCoilProperties;
import com.pointcoil.dto.admin.AdminCommon;
import com.pointcoil.dto.admin.AdminPageCommon;
import com.pointcoil.dto.map.BrandDTO;
import com.pointcoil.dto.map.RegisterDTO;
import com.pointcoil.entity.BusinessEntity;
import com.pointcoil.entity.LabelEntity;
import com.pointcoil.entity.MapUserEntity;
import com.pointcoil.entity.SubAccountEntity;
import com.pointcoil.mapper.MapAdminUserMapper;
import com.pointcoil.service.adminmap.MapAdminUserService;
import com.pointcoil.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.pointcoil.constant.Constants.*;
import static com.pointcoil.constant.Constants.SUCCESS_MSG;

/**
 * Created by WuShuang on 2019/12/4.
 */
@Service
@SuppressWarnings("ALL")
@Slf4j
public class MapAdminUserServiceImpl implements MapAdminUserService {


    @Autowired
    private MapAdminUserMapper mapAdminUserMapper;

    @Autowired
    private PointCoilProperties pointCoilProperties;

    @Autowired
    private RedisUtil redisUtil;
    /**
     * 后台登陆
     *
     * @Author: WuShuang on 2019/12/4  9:57
     * @param: [adminCommon]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String adminLogin(AdminCommon adminCommon) {
        adminCommon.setPassWord(Md5Util.getMD5(adminCommon.getPassWord()));
        adminCommon =  mapAdminUserMapper.adminLogin(adminCommon);
        if(StringUtils.isEmpty(adminCommon)){
            return ResponseUtil.errorMsgToClient(PASSWORD_ERROR_CODE+"", PASSWORD_ERROR_MSG);
        }else {
            return ResponseUtil.toClient(SUCCESS_ADMIN_CODE+"",SUCCESS_MSG);
        }
    }

    /**
     * 查询用户
     *
     * @Author: WuShuang on 2019/12/4  16:37
     * @param: [adminPageCommon]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String findUser(AdminPageCommon adminPageCommon) {
        PageHelper.startPage(adminPageCommon.getPage(),adminPageCommon.getPageSize());
        Page<MapUserEntity> mapUserEntities =  mapAdminUserMapper.findUser(adminPageCommon);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, mapUserEntities, new HashMap<String,Object>(1) {
            {
                put("count", mapUserEntities.getTotal());
            }
        });
    }

    /**
     * 查询品牌
     *
     * @Author: WuShuang on 2019/12/5  15:22
     * @param: [page, pageSize, userId]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String findBrandList(Integer page, Integer pageSize, String userId) {
        PageHelper.startPage(page,pageSize);
        Page<BrandDTO.SelectBrandDTO> brandDTOS = mapAdminUserMapper.findBrandList(userId);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, brandDTOS, new HashMap<String,Object>(1) {
            {
                put("count", brandDTOS.getTotal());
            }
        });
    }

    /**
     * 商圈列表
     *
     * @Author: WuShuang on 2019/12/5  16:54
     * @param: [page, pageSize, brandId]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String findBusinessList(Integer page, Integer pageSize, String brandId) {
        PageHelper.startPage(page,pageSize);
        Page<BusinessEntity> businessEntities = mapAdminUserMapper.findBusinessList(brandId);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, businessEntities, new HashMap<String,Object>(1) {
            {
                put("count", businessEntities.getTotal());
            }
        });
    }

    @Override
    public String findLabelList(Integer page, Integer pageSize, String brandId) {
        PageHelper.startPage(page,pageSize);
        Page<LabelEntity> businessEntities = mapAdminUserMapper.findLabelList(brandId);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, businessEntities, new HashMap<String,Object>(1) {
            {
                put("count", businessEntities.getTotal());
            }
        });
    }

    /**
     * 子账号查询
     *
     * @Author: WuShuang on 2019/12/11  10:01
     * @param: [page, pageSize, id]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String findSubAccount(Integer page, Integer pageSize, String id) {
        PageHelper.startPage(page,pageSize);
        Page<SubAccountEntity> subAccount = mapAdminUserMapper.findSubAccount(id);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, subAccount, new HashMap<String,Object>(1) {
            {
                put("count", subAccount.getTotal());
            }
        });
    }

    /**
     * 添加用户
     *
     * @Author: WuShuang on 2019/12/11  14:13
     * @param: [registerDTO]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String userAdd(RegisterDTO registerDTO) {

        String phoneSearch = pointCoilProperties.getRedis().getPhoneSearch();

        redisUtil.hSet(phoneSearch,registerDTO.getUserPhone(),System.currentTimeMillis()+"");

        mapAdminUserMapper.userAdd(registerDTO);


        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Date date = new Date();
                SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
                // 初始化client对象
                IAcsClient client = null;
                try {
                    client = SendMsg.initClient();
                } catch (ClientException e) {
                    e.printStackTrace();
                }
                // 短信模板请求对象
                SendSmsRequest request1 =SendMsg.getSMS_195722492(registerDTO.getUserPhone(),dateFormat.format(date));
                // 根据短信模板code来获取不同的短信模板请求对象
                // 发送短信
                SendSmsResponse response = null;
                try {
                    response = client.getAcsResponse(request1);
                } catch (ClientException e) {
                    e.printStackTrace();
                }
                System.out.println("请求的ID:" + response.getRequestId());
                System.out.println("请求的状态码:" + response.getCode());
                System.out.println("请求的状态码描述:" + response.getMessage());
                System.out.println("请求的回执ID:" + response.getBizId());

                MailUtils instance = MailUtils.getInstance();
                try {
                    instance.send(registerDTO.getUserEmail(),registerDTO.getUserName(),dateFormat.format(date));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }

    /**
     * 用户审核
     *
     * @Author: WuShuang on 2019/12/11  15:25
     * @param: [adminPageCommon]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String findUserByExamine(AdminPageCommon adminPageCommon) {
        PageHelper.startPage(adminPageCommon.getPage(),adminPageCommon.getPageSize());
        Page<MapUserEntity> mapUserEntities =  mapAdminUserMapper.findUserByExamine();
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, mapUserEntities, new HashMap<String,Object>(1) {
            {
                put("count", mapUserEntities.getTotal());
            }
        });
    }

    /**
     *
     * 审核
     * @Author: WuShuang on 2019/12/11  16:40
     * @param: [registerDTO, type]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String audit(RegisterDTO registerDTO, String type, String id) {
        if(type.equals("1")){
            String phoneSearch = pointCoilProperties.getRedis().getPhoneSearch();
            redisUtil.hSet(phoneSearch,registerDTO.getUserPhone(),System.currentTimeMillis()+"");


            ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    Date date = new Date();
                    SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
                    // 初始化client对象
                    IAcsClient client = null;
                    try {
                        client = SendMsg.initClient();
                    } catch (ClientException e) {
                        e.printStackTrace();
                    }
                    // 短信模板请求对象
                    SendSmsRequest request1 =SendMsg.getSMS_195722492(registerDTO.getUserPhone(),dateFormat.format(date));
                    // 根据短信模板code来获取不同的短信模板请求对象
                    // 发送短信
                    SendSmsResponse response = null;
                    try {
                        response = client.getAcsResponse(request1);
                    } catch (ClientException e) {
                        e.printStackTrace();
                    }
                    System.out.println("请求的ID:" + response.getRequestId());
                    System.out.println("请求的状态码:" + response.getCode());
                    System.out.println("请求的状态码描述:" + response.getMessage());
                    System.out.println("请求的回执ID:" + response.getBizId());

                    MailUtils instance = MailUtils.getInstance();
                    try {
                        instance.send(registerDTO.getUserEmail(),registerDTO.getUserName(),dateFormat.format(date));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });



        }


        mapAdminUserMapper.updUser(type,registerDTO,id);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }



    /**
     *审核列表
     *
     * @Author: WuShuang on 2019/12/12  10:32
     * @param: [adminPageCommon]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String findResultList(AdminPageCommon adminPageCommon) {
        PageHelper.startPage(adminPageCommon.getPage(),adminPageCommon.getPageSize());
        Page<MapUserEntity> mapUserEntities =  mapAdminUserMapper.findResultList();
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, mapUserEntities, new HashMap<String,Object>(1) {
            {
                put("count", mapUserEntities.getTotal());
            }
        });
    }

    /**
     * 删除审核列表信息
     *
     * @Author: WuShuang on 2019/12/12  10:38
     * @param: [id]
     * @return: java.lang.String
     * @Description:
     */
    @Override
    public String delUserAudit(String id) {
        mapAdminUserMapper.delUserAudit(id);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }

    @Override
    public String userMemberAdd(String id, String isMembers, String date) {
        String[] split = date.split(" - ");
        mapAdminUserMapper.userMemberAdd(id,isMembers,split[0],split[1]);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }

    @Override
    public String deleteRule(String id) {
        mapAdminUserMapper.deleteRule(id);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }


}
