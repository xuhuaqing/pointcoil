webpackJsonp([8],{BvgI:function(t,s,a){"use strict";Object.defineProperty(s,"__esModule",{value:!0});var i=a("mvHQ"),n=a.n(i),e=a("XSTL"),c=a("RtM3"),A={data:function(){return{id:"",newsDetail:""}},components:{top:e.a},created:function(){console.log(this.$route.query.newsId),this.id=this.$route.query.newsId},mounted:function(){this.getDetail()},methods:{getDetail:function(){var t=this,s=n()({newsId:this.id});Object(c.D)(s).then(function(s){console.log(s),t.newsDetail=s.data})}}},o={render:function(){var t=this,s=t.$createElement,a=t._self._c||s;return a("div",{staticStyle:{"min-height":"100vh",position:"relative"}},[a("top"),t._v(" "),a("div",{staticClass:"news-detail",domProps:{innerHTML:t._s(t.newsDetail.newsDetails)}},[t._v("\n    "+t._s(t.newsDetail.newsDetails)+"\n  ")]),t._v(" "),t._e()],1)},staticRenderFns:[function(){var t=this,s=t.$createElement,i=t._self._c||s;return i("div",{staticClass:"bottom"},[i("div",{staticClass:"contact-us"},[i("div",{staticClass:"contact-our"},[t._v("联系我们")]),t._v(" "),i("div",{staticClass:"contact-function"},[i("div",{staticClass:"contact-left"},[i("div",{staticClass:"e-mail"},[t._v("E-mail:  wf@hzdxq.cn")]),t._v(" "),i("div",{staticClass:"address"},[i("div",[i("p",[t._v("公司地址:  ")])]),t._v(" "),i("div",[i("p",[t._v("浙江省杭州市拱墅区花园岗街")]),t._v(" "),i("div",{staticClass:"definite-address"},[t._v("181号崇安中心6层668室")])])])]),t._v(" "),i("img",{staticClass:"contact-right",attrs:{src:a("LRkr"),alt:""}})])]),t._v(" "),i("div",{staticClass:"bottom-logo"},[i("span",[t._v("logo")]),t._v(" "),i("p",[t._v("版权所有©杭州点线圈网络科技有限公司 苏ICP备10041365号-1")])])])}]};var r=a("VU/8")(A,o,!1,function(t){a("bpH/")},"data-v-9cbd0aa4",null);s.default=r.exports},LRkr:function(t,s){t.exports="data:image/png;base64,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"},"bpH/":function(t,s){}});
//# sourceMappingURL=8.930a1211dab29ff3e3f8.js.map