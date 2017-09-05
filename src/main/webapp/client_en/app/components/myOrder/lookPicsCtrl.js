/**
 * Created by Sun on 2017/1/20.
 */
cnsphoto_enModule.controller('lookPicsCtrl',function($scope, $cookies, req, md5, $state, $rootScope, modalOperate, $stateParams){
    var vm = this;
    //从路由获得订单ID
    vm.orderId = $stateParams.orderId;
    //初始化设置
    function initSetting(){

    }
    //页面初始化
    function init() {
        initSetting();
        getMyOrders();
    }

    init();
    //获取我的订单
    function getMyOrders(){
        req.post('downloadPicture/getMyOrderPics.do',{
            orderId: vm.orderId
        }).success(function(resp){
            if(resp.code == '211'){
                    vm.lookPicList = resp.data;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }



});