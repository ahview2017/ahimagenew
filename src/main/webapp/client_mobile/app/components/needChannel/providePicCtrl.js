/**
 * Created by Sun on 2017/1/21.
 */
clientModule.controller('providePicCtrl',function($scope, $cookies, req, md5, $state, $rootScope, $stateParams){
    var vm = this;
    //获取从路由传过来的id
    vm.needsId = $stateParams.id;


    //保存提交
    vm.submitProviderInfo = function(needId){
        req_provide();
    }

    function req_provide() {
        if(!vm.tempProviderData.name){
            layer.alert('请填写姓名');
            return;
        }
        if(!vm.tempProviderData.phone){
            layer.alert('请填写联系方式');
            return;
        }
        if(!vm.tempProviderData.picDesc){
            layer.alert('请填写图片说明');
            return;
        }
        req.post('needs/tigong.do', {
            id: vm.needsId,
            name: vm.tempProviderData.name,
            contact: vm.tempProviderData.phone,
            picDesc: vm.tempProviderData.picDesc
        }).success(function(resp) {
            if(resp.code && resp.code == 211){
                //callback(resp.data);
                layer.alert('提交成功！',function(index){
                    $state.go('root.needChannel');
                    layer.close(index);
                });
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        })
    }

    function initSetting(){
        vm.tempProviderData = {
            name: '',
            phone: '',
            picDesc: ''
        }
    }

    function init() {
        initSetting();
    }

    init();



});