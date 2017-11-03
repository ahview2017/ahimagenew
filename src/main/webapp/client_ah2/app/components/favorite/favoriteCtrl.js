/**
 * Created by Sun on 2017/2/9.
 */
clientModule.controller('favoriteCtrl', function ($scope, $cookies, req, md5, $state, $rootScope) {
    var vm = this;
    //模态框遮罩层显示
    function maskShow() {
        $rootScope.layerIfShow = true;
    }

    //模态框遮罩层隐藏
    function maskHide() {
        $rootScope.layerIfShow = false;
    }

    //收藏夹模态框显示
    vm.favoriteModalShow = function (modalId) {
        $('#' + modalId).show();
        maskShow();
    };
    //收藏夹模态框隐藏
    vm.favoriteModalHide = function (modalId) {
        $('#' + modalId).hide();
        maskHide();
    };

    /**
     * 查看我的所有收藏夹
     */
    function getMineAllCollectData(page, type) {
        req.post("favorite/showAllFavoriteFolder.do", {
            page: page,
            rows: 7
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.allCollectArray = resp.data;
                var allCollectPageNum = resp.page;
                if (type == 1 && allCollectPageNum) {
                    // 处理分页
                    $.jqPaginator('#paginationAllCollectId', {
                        totalPages: allCollectPageNum,
                        visiblePages: 4,
                        currentPage: 1,
                        onPageChange: function (mineNum, pageType) {
                            if (pageType === 'change') {
                                getMineAllCollectData(mineNum, 0);
                            }
                        }
                    });
                }
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    /**
     * 点击编辑
     */
    vm.editCollectFlag = false;
    vm.onEditCollectClick = function () {
        vm.editCollectFlag = true;
    };

    /**
     * 点击确认编辑
     */
    vm.onConfirmNameClick = function (id, name) {
        req.post("favorite/upFavoriteFolder.do", {
            id: id,
            folderName: name
        }).success(function (resp) {
            if (resp.code == '211') {
                layer.alert("编辑成功");
                vm.editCollectFlag = false;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    };

    /**
     * 取消编辑名字
     */
    vm.onCancelNameClick = function () {
        vm.editCollectFlag = false;
    };

    /**
     * 点击删除
     */
    vm.onShowDeleteModalClick = function (id) {
        vm.deleteCollectId = id;
        vm.favoriteModalShow('favoriteDeleteModal');
    };

    /**
     * 点击删除
     */
    vm.onDeleteCollectClick = function () {
        req.post("favorite/delAllFavoriteFolder.do", {
            id: vm.deleteCollectId
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.favoriteModalHide('favoriteDeleteModal');
                getMineAllCollectData(1, 1);
                layer.alert("删除成功");
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    };

    /**
     * 添加收藏夹
     */
    vm.onAddCollectConClick = function () {
        if (!vm.addCollectNameModel) {
            layer.alert('请输入收藏夹名');
            return;
        }
        req.post("favorite/addFavoriteFolder.do", {
            folderName: vm.addCollectNameModel
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.addCollectNameModel = "";
                vm.favoriteModalHide('favoriteAddModal');
                getMineAllCollectData(1, 1);
                layer.alert("添加成功");
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    };

    getMineAllCollectData(1, 1);
});