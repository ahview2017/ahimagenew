/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('projectManageEditCtrl1',function($scope,$cookies,req,md5,$state,$rootScope,$stateParams){
    var vm = this;
    //栏目id
    vm.columnId = $stateParams.id;
    vm.tableShow1 = false;
    vm.tableShow2 = false;

    console.log(vm);
});