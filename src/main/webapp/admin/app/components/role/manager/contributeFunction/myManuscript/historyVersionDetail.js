/**
 * Created by Sun on 2016/12/13.
 */
adminModule.controller('myHistoryVersionDetail', function ($scope, $cookies, req, md5, $state, $rootScope, $stateParams, modalOperate) {
    var vm = this;

    //获取从路由传过来的稿件id
    vm.groupId = $stateParams.groupId;

    //获取从路由传过来的历史版本id
    vm.historyId = $stateParams.id;

    //获取从路由传过来的稿件类别
    vm.gType = $stateParams.gType;


    //页面初始化相关配置
    function initSetting() {
    }

    //页面初始化
    function init() {
        initSetting();
        getManuscriptDetails();
    }

    init();

    //获取稿件详情
    function getManuscriptDetails() {
        req.post('groupPicCtro/getGroupPics.do', {
            groupId: vm.historyId
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.manuscriptDetail = resp.data;
                vm.manuscriptProperties = resp.data.properties;
                vm.manuscriptCates = resp.data.cates;
                console.log('success');
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

});