clientModule.controller('fullTextCtrl',function($scope,$cookies,req,md5,$state,$rootScope,layerIfShow,$stateParams,getFullText){
    var vm = this;
    //搜索名
    vm.searchName = $stateParams.searchAllName;
    //获取路由传过来的搜索对象
    vm.searchObj = $stateParams.searchObj;
    //将要搜索对象
    vm.willSearchObj = angular.fromJson(vm.searchObj);
    //选中图片id
    vm.selPicIds = {};

    //获取检索结果
    vm.req_fullText = function (page) {
        var fullParams = {
            page: page,
            rows: 30,
            strWhere: vm.searchName
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
        getFullText.req_getFullText(fullParams,function (resp) {
            if(resp.code == 211){
                vm.photoList = resp.data;
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

    //全选
    vm.checkAll = function () {
        angular.forEach(vm.photoList, function (item, index) {
            if (vm.selectedAll) {
                vm.selPicIds[item.id] = true;
            } else {
                vm.selPicIds[item.id] = false;
            }
        });
    };


    //判断是否选择了数据
    function judgeIfSelData() {
        vm.selKeyArr = [];
        for (var key in vm.selPicIds) {
            if (vm.selPicIds[key]) {
                vm.selKeyArr.push(key);
                console.log(vm.selKeyArr.length);
            }
        }
        console.log(vm.selKeyArr.length);
    }

    //获取选中图片ID
    function getPicIds() {
        vm.finalPicIds = '';
        for (var key in vm.selPicIds) {
            if (vm.selPicIds[key]) {
                vm.finalPicIds += key + ',';
            }
        }
        vm.picIds = vm.finalPicIds.slice(0, vm.finalPicIds.length - 1);
    }

    //加入购物车
    vm.addCart = function () {
        judgeIfSelData();
        if (vm.selKeyArr.length < 1) {
            layer.alert('请至少选择一个图片进行操作');
            return;
        } else {
            getPicIds();
            req_onCartPicClick();
        }

    };

    //加入购物车请求
    function req_onCartPicClick() {
        req.post("car/add.do", {
            pictureId: vm.picIds,
        }).success(function (resp) {
            if (resp.code == '211') {
                contacts=resp.data.contacts
                notSales=resp.data.notSales
                if(contacts.length!=0 && notSales.length!=0 ){
                    layer.msg("包含不可出售图，张数用户不能添加定价图片")
                }else if(contacts.length!=0  ){
                    layer.msg("张数用户不能添加定价图片")
                }else if( notSales.length!=0 ){
                    layer.msg("包含不可出售图")
                }else{
                    layer.msg("加入购物车成功");
                }
                // layer.msg("加入购物车成功");
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    };

});
