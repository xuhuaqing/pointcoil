package com.pointcoil.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * @Description 管理员页面获取控制器
 * @Author 元稹
 * @Date 17/12/2018 20:30
 * @Version V1.0
 */
@Controller
@RequestMapping(value = "/admin/page/")
public class AdminController {
    @GetMapping("login")
    private String login() {
        return "user/login";
    }
    @GetMapping("index")
    private String index() {
        return "index/index";
    }

    @GetMapping("carousel")
    private String carousel() {
        return "official/carousel";
    }

    @GetMapping("officialdata")
    private String officialdata() {
        return "official/officialdata";
    }

    @GetMapping("carouselAdd")
    private String carouselAdd() {
        return "official/carouselAdd";
    }
    @GetMapping("carouselUpdate")
    private String carouselUpdate() {
        return "official/carouselUpdate";
    }

    @GetMapping("officialDataAdd")
    private String officialDataAdd() {
        return "official/officialDataAdd";
    }

    @GetMapping("officialDataUpdate")
    private String officialDataUpdate() {
        return "official/officialDataUpdate";
    }
    @GetMapping("productIntroduction")
    private String productIntroduction() {
        return "official/productIntroduction";
    }
    @GetMapping("productIntroductionAdd")
    private String productIntroductionAdd() {
        return "official/productIntroductionAdd";
    }
    @GetMapping("productIntroductionUpdate")
    private String productIntroductionUpdate() {
        return "official/productIntroductionUpdate";
    }
    @GetMapping("businessRadar")
    private String businessRadar() {
        return "official/businessRadar";
    }
    @GetMapping("businessRadarUpdate")
    private String businessRadarUpdate() {
        return "official/businessRadarUpdate";
    }
    @GetMapping("businessRadarAdd")
    private String businessRadarAdd() {
        return "official/businessRadarAdd";
    }
    @GetMapping("productCharacteristics")
    private String productCharacteristics() {
        return "official/productCharacteristics";
    }
    @GetMapping("productCharacteristicsAdd")
    private String productCharacteristicsAdd() {
        return "official/productCharacteristicsAdd";
    }
    @GetMapping("productCharacteristicsUpdate")
    private String productCharacteristicsUpdate() {
        return "official/productCharacteristicsUpdate";
    }
    @GetMapping("aboutUs")
    private String aboutUs() {
        return "official/aboutUs";
    }
    @GetMapping("aboutUsUpdate")
    private String aboutUsUpdate() {
        return "official/aboutUsUpdate";
    }
    @GetMapping("development")
    private String development() {
        return "official/development";
    }
    @GetMapping("developmentAdd")
    private String developmentAdd() {
        return "official/developmentAdd";
    }
    @GetMapping("developmentUpdate")
    private String developmentUpdate() {
        return "official/developmentUpdate";
    }
    @GetMapping("backgroundImg")
    private String backgroundImg() {
        return "official/backgroundImg";
    }
    @GetMapping("cooperationPartner")
    private String cooperationPartner() {
        return "official/cooperationPartner";
    }
    @GetMapping("cooperationPartnerAdd")
    private String cooperationPartnerAdd() {
        return "official/cooperationPartnerAdd";
    }
    @GetMapping("cooperationPartnerUpdate")
    private String cooperationPartnerUpdate() {
        return "official/cooperationPartnerUpdate";
    }
    @GetMapping("newsAdd")
    private String newsAdd() {
        return "official/newsAdd";
    }

    @GetMapping("newsUpdate")
    private String newsUpdate() {
        return "official/newsUpdate";
    }
    @GetMapping("newsData")
    private String newsData() {
        return "official/newsData";
    }

    @GetMapping("password")
    private String password() {
        return "official/password";
    }

    @GetMapping("industryDynamicsAdd")
    private String industryDynamicsAdd() {
        return "official/industryDynamicsAdd";
    }

    @GetMapping("industryDynamics")
    private String industryDynamics() {
        return "official/industryDynamics";
    }



















    private interface Defaulable {
        // Interfaces now allow default methods, the implementer may or
        // may not implement (override) them.
        default String notRequired() {
            int s = 1;
            s = ++s;
            return s+"";
        }

        default String required() {
            int s = 3;
            s = ++s;
            return s+"";
        }
    }

    private static class DefaultableImpl implements Defaulable {

    }

    private static class OverridableImpl implements Defaulable {
        @Override
        public String notRequired() {
            return "Overridden implementation";
        }

    }

    private interface DefaulableFactory {
        // Interfaces now allow static methods
        static Defaulable create( Supplier< Defaulable > supplier ) {
            return supplier.get();
        }
    }

    public static void main( String[] args ) {
        Defaulable defaulable = DefaulableFactory.create( DefaultableImpl::new );
        System.out.println( defaulable.notRequired() );

        defaulable = DefaulableFactory.create( OverridableImpl::new );
        System.out.println( defaulable.notRequired() );
    }











}