clientModule.controller('footerCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, layerIfShow) {
    var vm = this;

    /**
     * 获取网站信息
     */
    function getWebMsgData() {
        req.post('webmsg/showToHomePage.do', {}).success(function (resp) {
            if (resp.code == '211') {
                vm.webMsgArray = resp.data;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    // 请求
    getWebMsgData();
});