clientModule.controller('footerCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, layerIfShow) {
    var vm = this;

    /**
     * ��ȡ��վ��Ϣ
     */
    function getWebMsgData() {
        req.post('webmsg/showToHomePage.do', {}).success(function (resp) {
            if (resp.code == '211') {
                vm.webMsgArray = resp.data;
            }else if(resp.msg != 'δ��¼'){
                layer.alert(resp.msg);
            }
        });
    }

    // ����
    getWebMsgData();
});