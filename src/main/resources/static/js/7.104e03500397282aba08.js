webpackJsonp([7],{N3vg:function(e,t,o){e.exports=o.p+"static/img/background.6e5705d.png"},QlWu:function(e,t,o){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var s=o("lwI8"),n={render:function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("div",[o("top"),e._v(" "),o("div",{staticClass:"login-back"},[o("div",{staticClass:"register"},[e._m(0),e._v(" "),o("div",{staticClass:"phone-login"},[o("p",[e._v("欢迎登陆")]),e._v(" "),o("input",{directives:[{name:"model",rawName:"v-model",value:e.telephone,expression:"telephone"}],staticClass:"phone",attrs:{type:"text",placeholder:"请输入手机号码登陆",maxlength:"11",autocomplete:"on"},domProps:{value:e.telephone},on:{input:function(t){t.target.composing||(e.telephone=t.target.value)}}}),e._v(" "),o("div",{staticClass:"get-code"},[o("input",{directives:[{name:"model",rawName:"v-model",value:e.securityCode,expression:"securityCode"}],staticClass:"auth-code",attrs:{type:"text",placeholder:"请输入验证码"},domProps:{value:e.securityCode},on:{input:function(t){t.target.composing||(e.securityCode=t.target.value)}}}),e._v(" "),e.show?o("div",{staticClass:"send-code",on:{click:function(t){e.sendCode()}}},[e._v("发送验证码")]):e._e(),e._v(" "),e.show?e._e():o("div",{staticClass:"send-code",staticStyle:{background:"#dcdcdc",cursor:"not-allowed"}},[e._v(e._s(e.count)+"s后重试")])]),e._v(" "),o("div",{staticClass:"logn",on:{click:function(t){e.login()}}},[e._v("登陆")]),e._v(" "),o("div",{staticClass:"apply-now",on:{click:function(t){e.applyNow()}}},[e._v("立即申请")])])])])],1)},staticRenderFns:[function(){var e=this.$createElement,t=this._self._c||e;return t("div",{staticClass:"login-left"},[t("p",[this._v("logo")]),this._v(" "),t("img",{attrs:{src:o("N3vg"),alt:""}})])}]};var a=function(e){o("w5TW")},i=o("VU/8")(s.a,n,!1,a,"data-v-531adb38",null);t.default=i.exports},lwI8:function(e,t,o){"use strict";(function(e){var s=o("mvHQ"),n=o.n(s),a=o("XSTL"),i=o("1w/Q"),c=o("RtM3");t.a={data:function(){return{show:!0,count:"",timer:null,telephone:"",securityCode:""}},components:{top:a.a,bottom:i.a},mounted:function(){var t=this;if(e(".login").remove(),e(".show-hide").remove(),e(".trade").remove(),console.log(this.$cookies.get("phone")),this.$cookies.get("phone")&&(this.telephone=this.$cookies.get("phone"),"13989838220"==this.telephone||"17612714215"==this.telephone||"13967693028"==this.telephone)){var o={phoneNumber:this.telephone};Object(c.q)(o).then(function(e){if(console.log(e,"是否登录"),"000000"==e.code&&"1"==e.data.flag){t.$router.push({path:"/businessIndex"}),localStorage.setItem("sonId",e.data.sonId);var o={userId:e.data.userId,token:e.data.token,memberLevel:e.data.memberLevel,isSubAccount:e.data.isSubAccount,starTime:e.data.starTime,endTime:e.data.endTime,admin:e.data.admin,userName:e.data.userName};t.$cookies.set("userInfo",o,"7d"),t.$cookies.set("phone",t.telephone,"7d")}})}},methods:{login:function(){var e=this;if(this.telephone)if(this.securityCode){var t=n()({phoneNumber:this.telephone,code:this.securityCode});console.log(this.telephone),console.log(this.securityCode),console.log(t),Object(c.x)(t).then(function(t){if(console.log(t),"000000"==t.code){e.$router.push({path:"/businessIndex"}),localStorage.setItem("sonId",t.data.sonId);var o={userId:t.data.userId,token:t.data.token,memberLevel:t.data.memberLevel,isSubAccount:t.data.isSubAccount,starTime:t.data.starTime,endTime:t.data.endTime,admin:t.data.admin,userName:t.data.userName};e.$cookies.set("userInfo",o,"7d"),e.$cookies.set("phone",e.telephone,"7d")}else"000001"==t.code?alert(t.msg):"000004"==t.code&&alert("验证码输入错误")})}else alert("请填写验证码");else alert("请先填写手机号")},sendCode:function(){var e=this;if(/^1[3456789]\d{9}$/.test(this.telephone)){var t=this,o=n()({phoneNumber:this.telephone});Object(c.I)(o).then(function(o){if(console.log(o),"000003"==o.code)alert("手机号码有误，请重填");else if("001002"==o.code)alert("手机号还未审核通过,即将跳转注册页"),setTimeout(function(){e.$router.push({path:"/register"})},1500);else if("000000"==o.code){e.show=!1;var s=60;e.count=s-1,e.timer=setInterval(function(){t.count>1&&t.count<=s?t.count--:(t.show=!0,clearInterval(t.timer),t.timer=null)},1e3)}})}else alert("手机号码有误，请重填")},applyNow:function(){this.$router.push({path:"/register"})}}}}).call(t,o("7t+N"))},w5TW:function(e,t){}});
//# sourceMappingURL=7.104e03500397282aba08.js.map