clientModule.controller('financePicCtrl',function($scope, $cookies, req, md5, $state, $rootScope, layerIfShow, ellipsis, $timeout, jugeGroupPos){
    var vm = this;
    //页面初始化
    function init(){
    }
    init();

    //获取财经频道签发的稿件
    vm.getClientFinanceGroups = function (signId) {
        req_getClientGroups(signId);
    };
    //获取财经频道的稿件请求
    function req_getClientGroups(signId) {
        req.post('getPicture/getClientGroups.do', {
            sginId: signId,
            limit: 10
        }).success(function (resp) {
            if (resp.code == '211') {
                //大头条: 356
                if (signId == 133) {
                    vm.financeHeadlineGroups = resp.data;
                }
                //每日关注: 357
                if (signId == 242) {
                    vm.dailyFocus = resp.data;
                    vm.dailyFocusGroups = jugeGroupPos.jugeGroupPos(242,vm.dailyFocus);
                }
                //财经人物: 358
                if (signId == 243) {
                    vm.finacePersonGroups = resp.data;
                    vm.finalPersonPosGroups = jugeGroupPos.jugeGroupPos(243,vm.finacePersonGroups);
                }
                //金融证券: 359
                if (signId == 244) {
                    vm.finaceFinancial = resp.data;
                    vm.finalFinancialPosGroups = jugeGroupPos.jugeGroupPos(244,vm.finaceFinancial);
                }
                //商业消费: 360
                if (signId == 245) {
                    vm.commercialConsume = resp.data;
                    vm.finalBConsumePosGroups = jugeGroupPos.jugeGroupPos(245,vm.commercialConsume);
                }
                //房产建设: 361
                if (signId == 246) {
                    vm.finaceHouseBuild = resp.data;
                    vm.finalFinaceHouseBuildGroups = jugeGroupPos.jugeGroupPos(246,vm.finaceHouseBuild);
                }
                //会议会展: 362
                if (signId == 247) {
                    vm.finaceMeetinExhibition = resp.data;
                    vm.finaceMeetinExhibitionGroups = jugeGroupPos.jugeGroupPos(247,vm.finaceMeetinExhibition);
                }
                //公司企业: 363
                if (signId == 248) {
                    vm.companyEnterprise = resp.data;
                    vm.companyEnterpriseGroups = jugeGroupPos.jugeGroupPos(248,vm.companyEnterprise);
                }

                //IT通讯: 364
                if (signId == 249) {
                    vm.ITCommunication = resp.data;
                    vm.ITCommunicationGroups = jugeGroupPos.jugeGroupPos(249,vm.ITCommunication);
                }

                //汽车工业: 365
                if (signId == 250) {
                    vm.automobileIndustry = resp.data;
                    vm.automobileIndustryGroups = jugeGroupPos.jugeGroupPos(250,vm.automobileIndustry);
                }

                //重大工程: 366
                if (signId == 251) {
                    vm.keyProject = resp.data;
                    vm.keyProjectGroups = jugeGroupPos.jugeGroupPos(251,vm.keyProject);
                }

                //科技新品: 367
                if (signId == 252) {
                    vm.scienceNproduct = resp.data;
                    vm.scienceNproductGroups = jugeGroupPos.jugeGroupPos(252,vm.scienceNproduct);
                }

                //财经图表: 368
                if (signId == 253) {
                    vm.financeChart = resp.data;
                    vm.financeChartGroups = jugeGroupPos.jugeGroupPos(253,vm.financeChart);
                }

                //财经漫画: 369
                if (signId == 254) {
                    vm.financeCartoons = resp.data;
                    vm.financeCartoonsGroups = jugeGroupPos.jugeGroupPos(254,vm.financeCartoons);
                }

            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //轮播
    $(function () {
        $timeout(function () {
            $('#finance-focus-lb').slideBox({
                duration: 0.4,//滚动持续时间，单位：秒
                easing: 'linear',//swing,linear//滚动特效
                delay: 6,//滚动延迟时间，单位：秒
                hideClickBar: false,//不自动隐藏点选按键
                clickBarRadius: 3,
                width: 560,
                height: 330
            });
        }, 500);
    });

});