clientModule.controller('topicPlayBackCtrl',function($scope,$cookies,req,md5,$state,$rootScope){
    var vm = this;
    //页面初始化
    function init(){
        req.post('topic/showTopicToClient.do',{
        }).success(function (resp) {
            if(resp.code == '211'){
                vm.topicList = resp.data;
            }else{
                console.info(resp.msg || '获取专题失败');
            }
        })
    }
    /**专题展示
     * type 签发类型 0 人工 1 外部链接
     * url 外部链接地址
     * id 专题id
     * */
    vm.toShowTopic = function (type, url, id) {
        if(type == 1){
            window.open(url);
        }else if(type == 0){
            $state.go('roots.special',{'id':id});
        }else {
            layer.alert('该专题存在错误，请联系管理员');
            return;
        }
    }
    init();
});