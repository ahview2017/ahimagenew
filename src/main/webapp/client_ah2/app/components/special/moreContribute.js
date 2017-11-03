clientModule.controller('moreContributeCtrl',function($scope,$cookies,req,$state,$rootScope,$stateParams){
    var vm = this;
    //栏目id
    vm.columnId = $stateParams.id;
    vm.req_moreContribute = function (page) {
        req.post('lanmu/lanMuPicMoreDetailToClient.do',{
            lanmuid:vm.columnId,
            rows: 30,
            page: page
        }).success(function (resp) {
            if(resp.code == '211'){
                vm.content = resp.data || [];
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
                        showData:30,
                        jumpIptCls: 'toPageNum',
                        jumpBtnCls: 'toPageBtn',
                        callback: function (num) {
                            vm.req_moreContribute(num);
                            vm.pageNum = num;
                        }
                    });
                }
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        })
    }
    vm.req_moreContribute(1);
});