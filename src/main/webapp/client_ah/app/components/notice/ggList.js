clientModule.controller('ggListCtrl',function($scope,$cookies,req,$state,$rootScope,$stateParams){
    var vm = this;
    vm.topicId = $stateParams.id;
    vm.req_notice = function (page) {
        req.post('notice/selTopicAdv.do',{
            rows:20,
            page: page,
            topicId: vm.topicId
        }).success(function (resp) {
            if(resp.code == '211'){
                vm.noticeList = resp.data || [];
                vm.totalPage = resp.page || 1;
                vm.totalNum = resp.other || 0;
                if(resp.page && resp.page >=  1){
                    $('#jq-page').pagination({
                        totalData:resp.other,
                        pageCount:resp.page,
                        current: page,
                        jump:true,
                        coping:true,
                        homePage:'首页',
                        endPage:'末页',
                        prevContent:'上一页',
                        nextContent:'下一页',
                        showData:20,
                        jumpIptCls: 'toPageNum',
                        jumpBtnCls: 'toPageBtn',
                        callback: function (num) {
                            vm.req_notice(num);
                            vm.pageNum = num;
                        }
                    });
                }else if(resp.msg != '未登录'){
                    layer.alert(resp.msg);
                }
            }
        });
    }
    vm.req_notice(1);
});