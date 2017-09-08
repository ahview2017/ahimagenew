photo_enModule.controller('fullTextCtrl',function($scope,$cookies,req,md5,$state,$rootScope,layerIfShow,$stateParams,getFullText){
    var vm = this;
    var client_width=$(document).height()-309+'px';
    $('.photolist-container').css('min-height',client_width);
    
    //搜索名
    vm.searchName = $stateParams.searchAllName;
    //获取路由传过来的搜索对象
    vm.searchObj = $stateParams.searchObj;
    //将要搜索对象
    vm.willSearchObj = angular.fromJson(vm.searchObj);
console.log(vm.willSearchObj);
    //获取检索结果
    vm.req_fullText = function (page) {
        var fullParams = {
            page: page,
            rows: 30,
            strWhere: vm.searchName,
            langType: window.localStorage.lang
        }
        if(vm.willSearchObj && vm.willSearchObj.people){
            fullParams.people = vm.willSearchObj.people;
        }
        if(vm.willSearchObj && vm.willSearchObj.place){
            fullParams.place =  vm.willSearchObj.place;
        }
        if(vm.willSearchObj && vm.willSearchObj.authorName){
            fullParams.authorName = vm.willSearchObj.authorName;
        }
        if(vm.willSearchObj && vm.willSearchObj.memo){
            fullParams.memo = vm.willSearchObj.memo;
        }
        if(vm.willSearchObj && vm.willSearchObj.keyWords){
            fullParams.keyWords = vm.willSearchObj.keyWords;
        }
        if(vm.willSearchObj && vm.willSearchObj.category_id){
            fullParams.category_id = vm.willSearchObj.category_id;
        }
        if(vm.willSearchObj && vm.willSearchObj.fileName){
            fullParams.fileName =  vm.willSearchObj.fileName;
        }
        if(vm.willSearchObj && vm.willSearchObj.gtitle){
            fullParams.gtitle = vm.willSearchObj.gtitle;
        }
        if(vm.willSearchObj && vm.willSearchObj.properties){
            fullParams.properties = vm.willSearchObj.properties;
        }
        if(vm.willSearchObj && vm.willSearchObj.staTime){
            fullParams.staTime = vm.willSearchObj.staTime;
        }
        if(vm.willSearchObj && vm.willSearchObj.endTime){
            fullParams.endTime = vm.willSearchObj.endTime;
        }
        console.log(fullParams);
        getFullText.req_getFullText(fullParams,function (resp) {
            if(resp.code == 211){
                vm.photoList = resp.data;console.log(resp.data);
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
                            vm.req_fullText(num);
                            vm.pageNum = num;
                        }
                    });
                }
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    vm.req_fullText(1);
});