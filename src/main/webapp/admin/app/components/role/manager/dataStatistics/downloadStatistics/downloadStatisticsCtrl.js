/**
 * Created by Sun on 2016/12/5.
 */
adminModule.controller('downloadStatisticsCtrl',function($scope,$cookies,req,md5,$state,$rootScope){
    var vm = this;

    vm.downloadType = 1; //Ĭ����ʾ ͼƬ����ͳ��
    vm.tab = 1; //Ĭ����ʾ ͼ��ͳ��

    vm.downloadTypeClick = function(downloadType){
        switch(parseInt(downloadType)){
            case 1 :
                vm.downloadType = 1;  //ͼƬ����ͳ��
                vm.tab = 1;
                break;
            case 2:
                vm.downloadType = 2;  //�û�����ͳ��
                vm.tab = 1;
                break;
        }
    };

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