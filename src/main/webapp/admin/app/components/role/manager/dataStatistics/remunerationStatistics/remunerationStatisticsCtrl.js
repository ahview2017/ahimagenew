/**
 * Created by Sun on 2016/12/5.
 */
adminModule.controller('remunerationStatisticsCtrl',function($scope,$cookies,req,md5,$state,$rootScope){
    var vm = this;

    vm.tab = 1; //默认显示 图表统计

    vm.statisticsTypeClick = function(statisticsType){
        switch(parseInt(statisticsType)){
            case 1 :
                vm.tab = 1;  //图表统计
                break;
            case 2:
                vm.tab = 2;  //列表统计
                break;
            case 3:
             vm.tab = 3;  //详情
             break;
        }
    };
});