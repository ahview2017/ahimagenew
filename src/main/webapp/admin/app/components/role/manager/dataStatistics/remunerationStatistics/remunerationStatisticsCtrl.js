/**
 * Created by Sun on 2016/12/5.
 */
adminModule.controller('remunerationStatisticsCtrl',function($scope,$cookies,req,md5,$state,$rootScope){
    var vm = this;

    vm.tab = 1; //Ĭ����ʾ ͼ��ͳ��

    vm.statisticsTypeClick = function(statisticsType){
        switch(parseInt(statisticsType)){
            case 1 :
                vm.tab = 1;  //ͼ��ͳ��
                break;
            case 2:
                vm.tab = 2;  //�б�ͳ��
                break;
            case 3:
             vm.tab = 3;  //����
             break;
        }
    };
});