webpackJsonp([5],{"8zp9":function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var s=a("mvHQ"),i=a.n(s),o=a("RtM3"),n={data:function(){return{isShow:!1,userName:"",trueName:!1,userPhone:"",truePhone:!1,userEmail:"",trueEmail:!1,company:"",trueCompany:!1,brand:"",trueBrand:!1,Images:[]}},mounted:function(){},methods:{upload:function(t){console.log(t);for(var e=t.target.files,a=new FormData,s=0;s<=e.length;s++)a.append("file",e[s]);this.upLoad(a)},upLoad:function(t){var e=this;this.$axios({method:"post",url:"https://www.hzdxq.cn/pointcoil/admin/adminUpload/mapUpload",headers:{"Content-Type":"multipart/form-data"},data:t}).then(function(t){console.log(t.data),e.Images.push(t.data.data)}).catch(function(t){console.log(t)})},register:function(){var t=this;if(this.trueName&&this.truePhone&&this.trueEmail&&this.trueCompany&&this.trueBrand){var e=i()({userName:this.userName,userPhone:this.userPhone,userEmail:this.userEmail,enterpriseName:this.company,makeName:this.brand,businessLicense:this.Images.join(",")});Object(o.F)(e).then(function(e){console.log(e),"000000"==e.code?(t.userName="",t.userPhone="",t.userEmail="",t.company="",t.brand="",t.trueName=!1,t.truePhone=!1,t.trueEmail=!1,t.trueCompany=!1,t.trueBrand=!1,t.Images=[],t.isShow=!0):alert(e.msg)})}else alert("请将信息填写完整")},login:function(){this.$router.push({path:"/login"})},sure:function(){this.isShow=!1},isName:function(){1==/^[\u4e00-\u9fa5][\u4e00-\u9fa5]{1,5}$/.test(this.userName)?this.trueName=!0:this.trueName=!1},isPhone:function(){1==/^1(3|4|5|6|7|8|9)\d{9}$/.test(this.userPhone)?this.truePhone=!0:this.truePhone=!1},isEmail:function(){1==/^\w+\@+[0-9a-zA-Z]+\.(com|com.cn|edu|hk|cn|net)$/.test(this.userEmail)?this.trueEmail=!0:this.trueEmail=!1},isCompany:function(){1==/^(([\u4e00-\u9fff]{2,50})|([a-z\.\s\,]{2,50}))$/i.test(this.company)?this.trueCompany=!0:this.trueCompany=!1},isBrand:function(){this.brand?this.trueBrand=!0:this.trueBrand=!1}}},r={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",[t._m(0),t._v(" "),s("div",{staticClass:"message"},[s("div",{staticClass:"user-inpt"},[s("div",{staticClass:"inpt-list"},[s("input",{directives:[{name:"model",rawName:"v-model",value:t.userName,expression:"userName"}],attrs:{type:"text",placeholder:"请输入真实姓名",autocomplete:"off",id:"name"},domProps:{value:t.userName},on:{blur:t.isName,input:function(e){e.target.composing||(t.userName=e.target.value)}}}),t._v(" "),1==t.trueName?s("img",{attrs:{src:a("DB3y"),alt:""}}):t._e(),t._v(" "),0==t.trueName?s("p",[t._v("*")]):t._e()]),t._v(" "),s("div",{staticClass:"inpt-list"},[s("input",{directives:[{name:"model",rawName:"v-model",value:t.userPhone,expression:"userPhone"}],attrs:{type:"text",placeholder:"请输入联系电话",autocomplete:"off",id:"phone",maxlength:"11"},domProps:{value:t.userPhone},on:{blur:t.isPhone,input:function(e){e.target.composing||(t.userPhone=e.target.value)}}}),t._v(" "),1==t.truePhone?s("img",{attrs:{src:a("DB3y"),alt:""}}):t._e(),t._v(" "),0==t.truePhone?s("p",[t._v("*")]):t._e()]),t._v(" "),s("div",{staticClass:"inpt-list"},[s("input",{directives:[{name:"model",rawName:"v-model",value:t.userEmail,expression:"userEmail"}],attrs:{type:"text",placeholder:"请输入邮箱",autocomplete:"off",id:"e-mail"},domProps:{value:t.userEmail},on:{blur:t.isEmail,input:function(e){e.target.composing||(t.userEmail=e.target.value)}}}),t._v(" "),1==t.trueEmail?s("img",{attrs:{src:a("DB3y"),alt:""}}):t._e(),t._v(" "),0==t.trueEmail?s("p",[t._v("*")]):t._e()]),t._v(" "),s("div",{staticClass:"inpt-list"},[s("input",{directives:[{name:"model",rawName:"v-model",value:t.company,expression:"company"}],attrs:{type:"text",placeholder:"请输入企业名称",autocomplete:"off",id:"company"},domProps:{value:t.company},on:{blur:t.isCompany,input:function(e){e.target.composing||(t.company=e.target.value)}}}),t._v(" "),1==t.trueCompany?s("img",{attrs:{src:a("DB3y"),alt:""}}):t._e(),t._v(" "),0==t.trueCompany?s("p",[t._v("*")]):t._e()]),t._v(" "),s("div",{staticClass:"inpt-list"},[s("input",{directives:[{name:"model",rawName:"v-model",value:t.brand,expression:"brand"}],attrs:{type:"text",placeholder:"请输入品牌名称",autocomplete:"off",id:"brand"},domProps:{value:t.brand},on:{blur:t.isBrand,input:function(e){e.target.composing||(t.brand=e.target.value)}}}),t._v(" "),1==t.trueBrand?s("img",{attrs:{src:a("DB3y"),alt:""}}):t._e(),t._v(" "),0==t.trueBrand?s("p",[t._v("*")]):t._e()])]),t._v(" "),s("div",{staticClass:"upload-img"},[s("div",{staticClass:"upload"},[t._v("上传营业执照")]),t._v(" "),s("div",{staticClass:"img-list"},[s("div",{staticClass:"upload-btn"},[s("input",{attrs:{type:"file",accept:"image/*"},on:{change:function(e){t.upload(e)}}}),t._v(" "),s("img",{attrs:{src:a("toRb"),alt:""}})]),t._v(" "),t._l(t.Images,function(t,e){return s("div",{key:e,staticClass:"imgs"},[s("img",{attrs:{src:t,alt:""}})])})],2)]),t._v(" "),s("div",{staticClass:"register"},[s("div",{staticClass:"register-btn",on:{click:function(e){t.register()}}},[t._v("注册")]),t._v(" "),s("div",{staticClass:"login"},[t._v("\n        已有账号，立即\n        "),s("span",{on:{click:function(e){t.login()}}},[t._v("登录")])])])]),t._v(" "),1==t.isShow?s("div",{staticClass:"back"}):t._e(),t._v(" "),1==t.isShow?s("div",{staticClass:"pop-up"},[s("div",{staticClass:"hint"},[t._v("温馨提示")]),t._v(" "),s("div",{staticClass:"success"},[t._v("账号申请成功，平台会线下联系您！")]),t._v(" "),s("div",{staticClass:"contact"},[t._v("公司联系方式：19941207730")]),t._v(" "),s("div",{staticClass:"yes",on:{click:function(e){t.sure()}}},[t._v("好的")])]):t._e()])},staticRenderFns:[function(){var t=this.$createElement,e=this._self._c||t;return e("div",{staticClass:"logo"},[e("p",{staticClass:"logo-title"},[this._v("logo")]),this._v(" "),e("div",{staticClass:"user-register"},[e("p"),this._v(" "),e("div",[this._v("用户注册")]),this._v(" "),e("p")])])}]};var u=a("VU/8")(n,r,!1,function(t){a("xw2O")},"data-v-490a28ca",null);e.default=u.exports},DB3y:function(t,e){t.exports="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAAXNSR0IArs4c6QAAAbJJREFUOBGNUztLw1AUPuc2qQj+AQsVERSKSgr6BwS7iA4VnBUUFNFBBRc7ZOgoFDo4qH+iuigt2ElwaxEFR7v4D7RtHsdzriakL0ggyX18j3seF6HvyTftBcd39xAoB4hTAEQA2AKkqjLMm8q8/R6lYDDZerOT7a5TYtIBEalgPfpnsMf7V5NW+uwa9x3Z0wKa7DgP7LUSJYweYy2VTa+JiHYS5/hkkaXVr2brUkYoMbvkNkcdW0Dhg9BRiCc+0SESZAwDF5UkLC45oXATxowKxz3BmU24Huwqne3QYsSAnYVMSaPht50nDndaI4ly6q9U/0RWG5DoIzNiLsRwmXvKhaiOlIIdRPQ1KEr+ces9ZAYQ94gIfIaKPl2YJj4j4Da/39FjM3w2xAUDwhaHQNVwDpTqdqBuJukFjfEZHfMQ5wDPJlUl7clZ9YJFPpYWAa+d84U8zFmD0UVFt8wF2GgUylzKY70e88N5Kt1ni6c6idLb3FO1mFy5AI9LlnEueC0gPS29jQjlaDiDgnxsdl62zHUbbVf2dQhRYP61kJEO4wrp6yylAs62JExivrOKH1H8L2txv5Ch/wJ1AAAAAElFTkSuQmCC"},toRb:function(t,e){t.exports="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAKgAAACoCAYAAAB0S6W0AAAFG0lEQVR4Xu3cTW6TQRAEUHwC3/+YPgFILFg4/HSrpseN9FjHlcmbcn0kCz9er9f3b/4RWCrwUNClN+NYPwUUVBFWCyjo6utxuC8FfT6fDywEPiXw/l9OBf3UTfi+vxVQUMVYLaCgq6/H4RRUB1YLKOjq63E4BdWB1QIKuvp6HE5BdWC1gIKuvh6HU1AdWC2goKuvx+EUVAdWCyjo6utxOAXVgdUCCrr6ehxOQXVgtYCCrr4eh1NQHVgtoKCrr8fhFFQHVgso6OrrcTgF1YHVAgq6+nocTkF1YLWAgq6+HodTUB1YLaCgq6/H4RRUB1YLKOjq63E4BdWB1QIKuvp6HE5BBzvwjuuzVvvYCto3K79CQctUf/xCBc0Ny7gWtI+toH2z8issaJmq/Cb3EeC56a8EBc0xLWhuWH73e8T3sRW0b1Z+hQUtU5Xf5B7xualH/KChgg7iesT3cT3i+2blV3jEl6k84nOqfoKC9s3eX2FBc8Pyu98jvo+toH2z8issaJmq/Cb3S1Ju6rf4QUMFHcT1iO/jesT3zcqv8IgvU3nE51T9BAXtm/ktPjcrJyhomcqC5lT9BAXtm1nQ3KycoKBlKguaU/UTFLRvZkFzs3KCgpapLGhO1U9Q0L6ZBc3NygkKWqayoDlVP0FB+2YWNDcrJyhomcqC5lT9BAXtm1nQ3KycoKBlKguaU/UTFLRvZkFzs3KCgpapLGhO1U9Q0L6ZBc3NygkKWqayoDlVP0FB+2YWNDcrJyhomcqC5lT9BAXtm1nQ3KycoKBlKguaU/UTFLRvZkFzs3KCgpapLGhO1U9Q0L6ZBc3NygkKWqayoDlVP0FB+2YWNDcrJyhomcqC5lT9BAXtm1nQ3KycoKBlKguaU/UTFLRvZkFzs3KCgpapLGhO1U9Q0L7Zf7eg75ec/8gSugKf/ODd9/tf9wnLCtqt0/mvV9C/mCro+cJ1ExVUQbudufr1CnqV+94380tSbr3+/6D5j/i5BAXN7RU0Nyz/De+Tj8rBH3M0WkEHeS1ojquguaEFvWi47u+ggz/7eLQFzYktaG5oQS8aWtCD2BY0x7SguaEFvWhoQQ9iW9Ac04Lmhhb0oqEFPYhtQXNMC5obWtCLhhb0ILYFzTEtaG5oQS8aWtCD2BY0x7SguaEFvWhoQQ9iW9Ac04Lmhhb0oqEFPYhtQXNMC5obWtCLhhb0ILYFzTEtaG5oQS8aWtCD2BY0x7SguaEFvWhoQQ9iW9Ac04Lmhhb0oqEFPYhtQXNMC5obWtCLhhb0ILYFzTEtaG5oQS8aWtCD2BY0x7SguaEFvWhoQQ9iW9Ac04Lmhhb0oqEFPYhtQXNMC5obWtCLhhb0ILYFzTEtaG5oQS8aWtCD2BY0x7SguaEFvWhoQQ9iW9Ac04Lmhhb0oqEFPYhtQXNMC5obWtCLhhb0ILYFzTEtaG4oYVBAQQdxRecCCpobShgUUNBBXNG5gILmhhIGBRR0EFd0LqCguaGEQQEFHcQVnQsoaG4oYVBAQQdxRecCCpobShgUUNBBXNG5gILmhhIGBRR0EFd0LqCguaGEQQEFHcQVnQsoaG4oYVBAQQdxRecCCpobShgUUNBBXNG5gILmhhIGBRR0EFd0LqCguaGEQQEFHcQVnQsoaG4oYVBAQQdxRecCCpobShgUUNBBXNG5gILmhhIGBRR0EFd0LqCguaGEQYF/FnTwe4sm0Bb48gG27QQvIDAooKCDuKJzAQXNDSUMCvwAQbCG00coHzYAAAAASUVORK5CYII="},xw2O:function(t,e){}});
//# sourceMappingURL=5.74e0daec54696efbf592.js.map