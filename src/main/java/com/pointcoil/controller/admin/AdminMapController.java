package com.pointcoil.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by WuShuang on 2019/12/4.
 */
@Controller
@RequestMapping(value = "/admin/map/")
public class AdminMapController {



    @GetMapping("thermodynamicByCustomized_user")
    private String thermodynamicByCustomized_user() {
        return "map/thermodynamicByCustomized_user";
    }

    @GetMapping("customizedThermodynamic_01")
    private String customizedThermodynamic_01() {
        return "map/customizedThermodynamic_01";
    }

    @GetMapping("MemberCityList")
    private String MemberCityList() {
        return "map/MemberCityList";
    }
    @GetMapping("updateMemberCityTime")
    private String updateMemberCityTime() {
        return "map/updateMemberCityTime";
    }


    @GetMapping("customizedThermodynamic")
    private String customizedThermodynamic() {
        return "map/customizedThermodynamic";
    }
    @GetMapping("login")
    private String login() {
        return "usermap/login";
    }
    @GetMapping("updateRule")
    private String updateRule() {
        return "map/updateRule";
    }

    @GetMapping("index")
    private String index() {
        return "indexmap/index";
    }

    @GetMapping("essentialInfo")
    private String essentialInfo() {
        return "map/essentialInfo";
    }
    @GetMapping("thermodynamicUserAdd")
    private String thermodynamicUserAdd() {
        return "map/thermodynamicUserAdd";
    }@GetMapping("thermodynamicUser")
    private String thermodynamicUser() {
        return "map/thermodynamicUser";
    }

    @GetMapping("seeBrandList")
    private String seeBrandList() {
        return "map/brandList";
    }

    @GetMapping("businessList")
    private String businessList() {
        return "map/businessList";
    }

    @GetMapping("labelList")
    private String labelList() {
        return "map/labelList";
    }

    @GetMapping("posterManage")
    private String posterManage() {
        return "map/posterManage";
    }

    @GetMapping("subAccount")
    private String subAccount() {
        return "map/subAccount";
    }

    @GetMapping("userAdd")
    private String userAdd() {
        return "map/userAdd";
    }

    @GetMapping("userAudit")
    private String userAudit() {
        return "map/userAudit";
    }

    @GetMapping("updUserAudit")
    private String updUserAudit() {
        return "map/updUserAudit";
    }

    @GetMapping("resultList")
    private String resultList() {
        return "map/resultList";
    }

    @GetMapping("customized")
    private String customized() {
        return "map/customized";
    }

    @GetMapping("customizedUser")
    private String customizedUser() {
        return "map/customizedUser";
    }

    @GetMapping("customizedBrandList")
    private String customizedBrandList() {
        return "map/customizedBrandList";
    }

    @GetMapping("addCustomizedBusiness")
    private String addCustomizedBusiness() {
        return "map/addCustomizedBusiness";
    }

    @GetMapping("industryManage")
    private String industryManage() {
        return "map/industryManage";
    }

    @GetMapping("industryManageTwo")
    private String industryManageTwo() {
        return "map/industryManageTwo";
    }

    @GetMapping("industryAdd")
    private String industryAdd() {
        return "map/industryAdd";
    }

    @GetMapping("industryTwoAdd")
    private String industryTwoAdd() {
        return "map/industryTwoAdd";
    }

    @GetMapping("labelManage")
    private String labelManage() {
        return "map/labelManage";
    }

    @GetMapping("labelAdd")
    private String labelAdd() {
        return "map/labelAdd";
    }

    @GetMapping("labelUpdate")
    private String labelUpdate() {
        return "map/labelUpdate";
    }
    @GetMapping("password")
    private String password() {
        return "map/password";
    }
    @GetMapping("emailList")
    private String emailList() {
        return "map/emailList";
    }
    @GetMapping("emailAdd")
    private String emailAdd() {
        return "map/emailAdd";
    }

    @GetMapping("mapPosterManage")
    private String mapPosterManage() {
        return "map/mapPosterManage";
    }

    @GetMapping("memberManager")
    private String memberManager() {
        return "map/memberManager";
    }

    @GetMapping("thermodynamicData")
    private String thermodynamicData() {
        return "map/thermodynamicData";
    }

    @GetMapping("cityList")
    private String cityList() {
        return "map/cityList";
    }
    @GetMapping("areYouHungryData")
    private String areYouHungryData() {
        return "map/areYouHungryData";
    }@GetMapping("areCityList")
    private String areCityList() {
        return "map/areCityList";
    }

    @GetMapping("chainCityList")
    private String chainCityList() {
        return "map/chainCityList";
    }
    @GetMapping("chainFamily")
    private String chainFamily() {
        return "map/chainFamily";
    }
    @GetMapping("rule")
    private String rule() {
        return "map/rule";
    }
    @GetMapping("officeRule")
    private String officeRule() {
        return "map/officeRule";
    }

    @GetMapping("ruleAdd")
    private String ruleAdd() {
        return "map/ruleAdd";
    }

    @GetMapping("adminBrandList")
    private String adminBrandList() {
        return "map/adminBrandList";
    }

    @GetMapping("gould")
    private String gould() {
        return "map/gould";
    } @GetMapping("console")
    private String console() {
        return "map/console";
    }

    @GetMapping("userMember")
    private String userMember() {
        return "map/userMember";
    }
    @GetMapping("gouldCityData")
    private String gouldCityData() {
        return "map/gouldCityData";
    }
    @GetMapping("order")
    private String order() {
        return "map/order";
    }

    @GetMapping("customApplet")
    private String customApplet() {
        return "map/customApplet";
    }
}
