/**
 * Created by Sun on 2016/12/5.
 */
adminModule.controller('signDraftStatisticsCtrl',function($scope,$cookies,req,md5,$state,$rootScope){
    var vm = this;

    vm.tab = 1; //Ĭ����ʾ ͼ��ͳ��
    vm.durationType = 1;

    vm.statisticsTypeClick = function(statisticsType){
        switch(parseInt(statisticsType)){
            case 1 :
                vm.tab = 1;  //ͼ��ͳ��
                vm.durationType = 1;
                break;
            case 2:
                vm.tab = 2;  //�б�ͳ��
                break;
            /*case 3:
                vm.tab = 3;  //����
                break;*/
        }
    };

    vm.signDraftDurationClick = function(durationType){
        switch(parseInt(durationType)){
            case 1 :
                vm.durationType = 1;  //����
                break;
            case 2:
                vm.durationType = 2;  //����
                break;
            case 3:
                vm.durationType = 3;  //����
                break;
        }
    };
});