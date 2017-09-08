clientModule.controller('ggDisplayCtrl',function($scope,$cookies,req,$state,$rootScope,$stateParams){
    var vm = this;
    //公告id
    vm.noticeId = $stateParams.id;
    function init() {
        req.post('notice/showtoedit.do',{
            id:vm.noticeId
        }).success(function (resp) {
            if(resp.code == '211'){
                vm.title = resp.data.noticeTitle || '';
                vm.content = resp.data.noticeContent;
                
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        })
    }
    init();
});