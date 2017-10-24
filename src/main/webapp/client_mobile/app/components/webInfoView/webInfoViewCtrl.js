clientModule.controller('webInfoViewCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, layerIfShow
    , $stateParams) {
    var vm = this;

    vm.webReqId = $stateParams.id;
    vm.webSelectName = $stateParams.name;
    vm.webSelectType = $stateParams.type;

    /**
     * 获取网站公告和留言详情
     * @param detailId
     * @param type 1：网站公告，2：网站消息
     */
    function getWebDetailData(detailId, type) {
        var reqUrl = "";
        if (type == 1) {
            reqUrl = "notice/showtoedit.do";
        } else {
            reqUrl = "webmsg/showtoedit.do";
        }
        req.post(reqUrl, {
            id: detailId
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.webDetailObj = resp.data;
                vm.webDetailContent = type == 1 ?
                    vm.webDetailObj['noticeContent'] : vm.webDetailObj['detail'];
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    // 请求
    getWebDetailData(vm.webReqId, vm.webSelectType);
});