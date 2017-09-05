adminModule.controller('subpoolManageCtrl',function($scope,$cookies,req,md5,$state,$rootScope){
    var vm = this;

    //模态框遮罩层显示
    function maskShow(){
        $rootScope.layerIfShow = true;
    }
    //模态框遮罩层隐藏
    function maskHide(){
        $rootScope.layerIfShow = false;
    }
    //子库模态框显示
    vm.subpoolModalShow = function(modalId){
        $('#' + modalId).show();
        maskShow();
    }
    //子库模态框隐藏
    vm.subpoolModalHide = function(modalId){
        $('#' + modalId).hide();
        maskHide();
    }

    //添加的时候初始化
    vm.addResetSubpool = function(){
        resetTempSubpoolData();
        vm.subpoolModalShow("subpool-add-modal");
    }
    vm.addSubpool = function(){
        req_add(function(resp){
            renderList('', 1, 1);
            vm.subpoolModalHide("subpool-add-modal");
        })
    }

    vm.showUpdateModel = function(item){
        resetTempSubpoolData();
        vm.updateId = item.ID;
        vm.tempSubpoolData.siteName = item.SITE_NAME;
        vm.tempSubpoolData.memo = item.SITE_SUMMARY;
        vm.subpoolModalShow("subpool-modify-modal");
    }
    vm.updateSubpool = function(){
        req_update(function(resp){
            renderList('', 1, 1);
            vm.subpoolModalHide("subpool-modify-modal");
        })
    }
    //选择checkbox进行修改
    vm.oneUpdate = function(){
        resetTempSubpoolData();
        vm.updateId = vm.listData.filter(function(item){
            return item.Checked;
        }).map(function(item){
            return item.ID;
        }).join(',');
        vm.tempSubpoolData.siteName = vm.listData.filter(function(item){
            return item.Checked;
        }).map(function(item){
            return item.SITE_NAME;
        }).join(',');
        vm.tempSubpoolData.memo = vm.listData.filter(function(item){
            return item.Checked;
        }).map(function(item){
            return item.SITE_SUMMARY;
        }).join(',');

        if(((vm.updateId.length == 0) && (vm.updateId.split(',').length == 1)) || ((vm.updateId.length > 0) && (vm.updateId.split(',').length > 1))){
            layer.alert('请选择一条数据进行操作！');
        }else if((vm.updateId.length != 0) && (vm.updateId.split(',').length == 1)){
            vm.subpoolModalShow("subpool-modify-modal");
        }
    }

    vm.showDelModel = function(wordId){
        vm.deleteIdStr = wordId;
        vm.subpoolModalShow("subpool-del-modal");
    }
    vm.delWord = function () {
        req_delete(function(resp){
            renderList('', 1, 1);
            vm.subpoolModalHide("subpool-del-modal");
        })
    }
    //批量删除
    vm.multiDelete = function (){
        vm.deleteIdStr = vm.listData.filter(function(item){
            return item.Checked;
        }).map(function(item){
            return item.ID;
        }).join(',');
        if(vm.deleteIdStr.length == 0){
            layer.alert('请至少选择一条数据进行操作！');
        }else{
            vm.subpoolModalShow("subpool-del-modal");
        }
    }

    //全选
    vm.selectAll = function(){
        var selectedAll = vm.listData.every(function(item){
            return item.Checked;
        })
        vm.listData.map(function(item){
            item.Checked = selectedAll ? false : true;
            return item;
        })
    }
    function resetSelectAll (){
        vm.SelectedAll = false;
    }
    function hasSelectedAll () {
        return vm.listData.every(function(item){
            return item.Checked;
        })
    }
    vm.selectItem = function (){
        vm.SelectedAll = hasSelectedAll() ? true : false
    }

    function req_add(callback) {
        req.post('siteCtro/addSite.do', {
            siteName: vm.tempSubpoolData.siteName,
            memo: vm.tempSubpoolData.memo
        }).success(function(resp) {
            if(resp.code && resp.code == 211){
                callback(resp.data)
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        })
    }

    function req_update(callback) {
        req.post('siteCtro/updateSite.do', {
            siteId: vm.updateId,
            siteName: vm.tempSubpoolData.siteName,
            memo: vm.tempSubpoolData.memo
        }).success(function(resp) {
            if(resp.code && resp.code == 211){
                callback(resp.data)
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        })
    }

    function req_delete(callback) {
        req.post('siteCtro/delSiteByIds.do', {
            siteIds: vm.deleteIdStr
        }).success(function(resp) {
            if(resp.code && resp.code == 211){
                callback(resp.data)
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        })
    }

    function req_list(callback, inputSiteName, curPage, type) {
        req.post('siteCtro/getSiteByQuery.do', {
            siteName: inputSiteName,
            siteId: '',
            page: curPage,
            row:10
        }).success(function (resp) {
            if (resp.code && resp.code == 211) {
                callback(resp.data);

                var adPageNum = resp.page;
                // 处理分页
                if (type == 1) {
                    $.jqPaginator('#paginationAdId', {
                        totalPages: adPageNum,
                        visiblePages: 4,
                        currentPage: 1,
                        onPageChange: function (mineNum, pageType) {
                            if (pageType === 'change') {
                                renderList(inputSiteName, mineNum, 0);
                            }
                        }
                    });
                }

            }
        });
    }

    function renderList(inputSiteName, curPage, type) {
        req_list(function(listData){
            vm.listData = listData.map(function (item) {
                item.Checked = false;
                return item;
            })
        }, inputSiteName, curPage, type)
        resetSelectAll();
    }

    /**
     * 点击搜索
     */
    vm.onSearchDataClick = function () {
        var searchTitle = $(" #sub-pool-search-input").val().replace(/[]/g, "");
        if (searchTitle == "" || undefined || null) {
            // layer.alert("请输入关键字");
            renderList('', 1, 1);
        } else {
            renderList(searchTitle, 1, 1);
        }
    };

    function resetTempSubpoolData(){
        vm.tempSubpoolData = {
            siteName: '',
            memo: ''
        }
    }

    function init() {
        renderList('', 1, 1);
        resetTempSubpoolData();
    }

    init();


});