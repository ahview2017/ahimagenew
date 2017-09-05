clientModule.controller('chartListCtrl',function($scope, $cookies, req, md5, $state, $rootScope, layerIfShow, ellipsis, $timeout, jugeGroupPos){
    var vm = this;
    //页面初始化
    function init(){
    }
    init();

    //获取签发到漫画图标的稿件
    vm.getClientChartGroups = function (signId) {
        req_getClientGroups(signId);
    };
    //获取财经频道的稿件请求
    function req_getClientGroups(signId) {
        req.post('getPicture/getClientGroups.do', {
            sginId: signId,
            limit: 10
        }).success(function (resp) {
            if (resp.code == '211') {

                //最新漫画: 134
                if (signId == 134) {
                    vm.latestComic = resp.data;
                    vm.latestComicGroups = jugeGroupPos.jugeGroupPos(134, vm.latestComic);
                }
                //新闻漫画: 213
                if (signId == 213) {
                    vm.newsCartoon = resp.data;
                    vm.newsCartoonGroups = jugeGroupPos.jugeGroupPos(213,vm.newsCartoon);
                }
                //经济漫画: 214
                if (signId == 214) {
                    vm.economicComic = resp.data;
                    vm.economicComicGroups = jugeGroupPos.jugeGroupPos(214,vm.economicComic);
                }

                //法制漫画: 215
                if (signId == 215) {
                    vm.LegalComic = resp.data;
                    vm.LegalComicGroups = jugeGroupPos.jugeGroupPos(215,vm.LegalComic);
                }


                //社会漫画: 216
                if (signId == 216) {
                    vm.socialComic = resp.data;
                    vm.socialComicGroups = jugeGroupPos.jugeGroupPos(216,vm.socialComic);
                }


                //最新图表: 395
                if (signId == 217) {
                    vm. latestChart = resp.data;
                    vm.latestChartGroups = jugeGroupPos.jugeGroupPos(217,vm.latestChart);
                }


                //新闻图表: 396
                if (signId == 218) {
                    vm.newsCharts = resp.data;
                    vm.newsChartsGroups = jugeGroupPos.jugeGroupPos(218,vm.newsCharts);
                }


                //经济图表: 397
                if (signId == 219) {
                    vm.economicChart = resp.data;
                    vm.economicChartGroups = jugeGroupPos.jugeGroupPos(219,vm.economicChart);
                }


                //文体图表: 398
                if (signId == 220) {
                    vm.styleChart = resp.data;
                    vm.styleChartGroups = jugeGroupPos.jugeGroupPos(220,vm.styleChart);
                }


                //插图: 399
                if (signId == 221) {
                    vm.illustration = resp.data;
                    vm.illustrationGroups = jugeGroupPos.jugeGroupPos(221,vm.illustration);
                }


            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }



});