/**
 * 收藏夹详情
 */
clientModule.controller('favoriteDetailCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, $stateParams) {
    var vm = this;

    //获取从路由传过来的id
    vm.collectId = $stateParams.id;
    vm.collectName = $stateParams.name;

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
     * 查看收藏夹图片
     */
    function getCollectPicData(mineId, page, type) {
        req.post("favoriteFolderPic/showFavoriteFolderPic.do", {
            folderId: mineId,
            page: page,
            rows: 10
        }).success(function (resp) {
            if (resp.code == '211' && resp.data != [] && resp.data.length > 0) {
                vm.allPicArray = resp.data;
                var allPicPageNum = resp.page;
                if (type == 1) {
                    // 处理分页
                    $.jqPaginator('#paginationAllPicId', {
                        totalPages: allPicPageNum,
                        visiblePages: 4,
                        currentPage: 1,
                        onPageChange: function (mineNum, pageType) {
                            if (pageType === 'change') {
                                getCollectPicData(mineId, mineNum, 0);
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
     * 处理全选
     */
    vm.checkBoxArray = [];
    vm.isHadAllCheck = false;
    vm.onCheckAllClick = function () {
        if (vm.isHadAllCheck) {
            vm.checkBoxArray = vm.allPicArray.map(function (item) {
                return item.id + '_' + item.pictureId
            });
        } else {
            vm.checkBoxArray = vm.allPicArray.map(function (item) {
                return false
            });
        }
    };

    /**
     * 监听每一个选项的改变
     */
    $scope.$watchCollection('detail.checkBoxArray', function (newC) {
        if (newC.every(function (item) {
                return item != false;
            })) {
            vm.isHadAllCheck = true;
        } else {
            vm.isHadAllCheck = false;
        }
    });

    /**
     * 显示删除框
     */
    vm.deleteDataParamsId = "";
    vm.onShowDeleteModelClick = function (deleteType, mineDeleteId) {
        if (deleteType == -1) {
            vm.deleteDataParamsId = mineDeleteId;
            vm.favoriteModalShow('picDeleteModal');
        } else {
            var paramsId = "";
            for (var c = 0; c < vm.checkBoxArray.length; c++) {
                var checkBoxItem = vm.checkBoxArray[c];
                if (checkBoxItem != false) {
                    var checkItemIdArray = checkBoxItem.split("_");
                    paramsId += checkItemIdArray[0] + ",";
                }
            }
            if (paramsId != "") {
                vm.deleteDataParamsId = paramsId.substr(0, paramsId.length - 1);
                vm.favoriteModalShow('picDeleteModal');
            } else {
                layer.alert("请选择要删除的图片");
            }
        }
    };

    /**
     * 删除收藏夹图片
     */
    vm.onDeleteCollectPicData = function () {
        req.post('favoriteFolderPic/delFavoriteFolderPic.do', {
            id: vm.deleteDataParamsId
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.favoriteModalHide('picDeleteModal');
                layer.alert('删除图片成功');
                getCollectPicData(vm.collectId, 1, 1);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    };

    /**
     * 显示购物车框
     */
    vm.hadSelectPayId = "";
    vm.onShowHadSelectModelClick = function (editType, mineEditId) {
        if (editType == -1) {
            vm.hadSelectPayId = mineEditId;
            vm.favoriteModalShow('hadSelectPayModal');
        } else {
            var paramsId = "";
            for (var c = 0; c < vm.checkBoxArray.length; c++) {
                var checkBoxItem = vm.checkBoxArray[c];
                if (checkBoxItem != false) {
                    var checkItemIdArray = checkBoxItem.split("_");
                    paramsId += checkItemIdArray[1] + ",";
                }
            }
            if (paramsId != "") {
                vm.hadSelectPayId = paramsId.substr(0, paramsId.length - 1);
                vm.favoriteModalShow('hadSelectPayModal');
            } else {
                layer.alert("请选择要加入购物车的图片");
            }
        }
    };

    /**
     * 添加购物车
     */
    vm.onHadSelectPayClick = function () {
        req.post('car/add.do', {
            pictureId: vm.hadSelectPayId
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.favoriteModalHide('hadSelectPayModal');
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
                // layer.msg('添加购物车成功');
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    };

    // 请求
    getCollectPicData(vm.collectId, 1, 1);


    //下载图片
    vm.downPic = function () {
        req_genereateOrder();
    };
    //订户下载生成订单请求
    function req_genereateOrder() {
        req.post('downloadPicture/downForOrder.do', {
            picIds: vm.hadSelectPayId
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.orderList = resp.other;
                vm.orderId = resp.other.id;
                vm.pics= resp.data;
                vm.orderDetailId='';
                angular.forEach(vm.pics,function (iterator) {
                    vm.orderDetailId += iterator.id+",";
                });
                $('#down-pic-order-modal').modal('show');
            }else if(resp.msg != '未登录'){
               layer.msg(resp.msg);
            }
        });
    }

    //确定下载图片
    vm.sureDownPic = function (orderId, downWay) {
        req_sureDownPic(orderId, downWay);
    };
    //确定下载图片请求
    function req_sureDownPic(orderId, downWay) {
        var params =
            '?orderId=' + orderId +
            '&type=' + downWay;
        window.open('/photo/downloadPicture/downByOrder.do' + params, '_blank');
    }

    //根据订单详情id下载图片
    vm.sureDownPicByOrderDetail = function (details, downWay) {
        req_sureDownPicByOrderDetailId(details, downWay);
    };
    //根据订单详情id下载图片
    function req_sureDownPicByOrderDetailId(details, downWay) {
        var params =
            '?picIds=' + details +
            '&type=' + downWay;
        window.open('/photo/downloadPicture/downByPicId.do' + params, '_blank');
    }

});