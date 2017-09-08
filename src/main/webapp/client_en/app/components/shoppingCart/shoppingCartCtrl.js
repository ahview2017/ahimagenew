/**
 * Created by Sun on 2017/2/10.
 */
photo_enModule.controller('shoppingCartCtrl',function($scope, $cookies, req, md5, $state, $rootScope){
    var vm = this;
    
    var client_width=$(document).height()-309+'px';
    $('.shoppingCart').css('min-height',client_width);
    //模态框遮罩层显示
    function maskShow() {
        $rootScope.layerIfShow = true;
    }

    //模态框遮罩层隐藏
    function maskHide() {
        $rootScope.layerIfShow = false;
    }
    //移动模态框
    /*vm.moveModal = function(dragDiv, tagDiv) {
        allModalMove.modalMove(dragDiv, tagDiv);
    }*/

    //购物车模态框显示
    vm.cartModalShow = function (modalId) {
        $('#' + modalId).show();
        maskShow();
    };

    //购物车模态框隐藏
    vm.cartModalHide = function (modalId) {
        $('#' + modalId).hide();
        maskHide();
    };

    //列表
    function req_list(pageNum, callback) {
        req.post('car/show.do', {
            page: pageNum,
            rows: 10,
            langType:window.localStorage.lang
        }).success(function (resp) {
            if (resp.code == 211) {
                var sendPage = resp.page; //总页数
                if (sendPage) {
                    $.jqPaginator('#sendpaginationId', {
                        totalPages: sendPage,
                        visiblePages: 10,
                        currentPage: pageNum,
                        onPageChange: function (pageNum, pageType) {
                            if (pageType === 'change') {
                                req_list(pageNum, function (listData) {
                                    vm.listData = listData.map(function (item) {
                                        item.Checked = false;
                                        return item;
                                    })

                                })
                            }
                        }
                    });
                }
                if (callback) { callback(resp.data); }
            }else if(resp.msg != '未登录'){
                layer.alert('请登录查看购物车');
                $state.go('root.login');
            }
        });
    }

    function req_delete(callback) {
        req.post('car/delete.do', {
            pictureId: vm.deleteIdStr,
            langType:window.localStorage.lang
        }).success(function (resp) {
            if (resp.code && resp.code == 211) {
                callback(resp.data)
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        })
    }

    vm.delPic = function () {
        req_delete(function (resp) {
            renderList();
            vm.cartModalHide("cart-del-modal");
        })
    };
    vm.showDelModel = function (picId) {
        vm.deleteIdStr = picId;
        vm.cartModalShow("cart-del-modal");
    };

    //批量删除
    vm.multiDelete = function () {
        vm.deleteIdStr = vm.listData.filter(function (item) {
            return item.Checked;
        }).map(function (item) {
            return item.picId;
        }).join(',');
        if (vm.deleteIdStr.length == 0) {
            layer.alert('请至少选择一条数据进行操作！');
        } else {
            vm.cartModalShow("cart-del-modal");
        }
    };

    vm.selectedNum = function(){
        /*vm.deleteIdStr = vm.listData.filter(function (item) {
            return item.Checked;
        }).map(function (item) {
            return item.picId;
        }).join(',');*/
        vm.selPicInfo = {
            selPicLen: '',
            selPicPrice: 0
        };
        vm.selPicArr = vm.listData.filter(function (item) {
            return item.Checked;
        });
        vm.selPicInfo.selPicLen = vm.selPicArr.length;
        for(var i = 0,len = vm.selPicArr.length; i < len; i++){
            vm.selPicInfo.selPicPrice += vm.selPicArr[i].price*1;
        }

        return vm.selPicInfo;
    }

    //全选
    vm.selectAll = function () {
        var selectedAll = vm.listData.every(function (item) {
            return item.Checked;
        });
        vm.listData.map(function (item) {
            item.Checked = selectedAll ? false : true;
            return item;
        })
        vm.selectedNum();
    };
    function resetSelectAll() {
        vm.SelectedAll = false;
    }

    function hasSelectedAll() {
        return vm.listData.every(function (item) {
            return item.Checked;
        })
    }

    vm.selectItem = function () {
        vm.SelectedAll = hasSelectedAll() ? true : false
    };

    function renderList() {
        req_list(1, function (listData) {
            vm.listData = listData.map(function (item) {
                item.Checked = false;
                return item;
            })

        });
        resetSelectAll();
    }

    function resetData(){
        vm.selPicInfo = {
            selPicLen: 0,
            selPicPrice: 0
        };
    }

    function init() {
        resetData();
        renderList();
    }

    init();

    //去结算
    vm.settleCounts = function(){
        vm.deleteIdStr = vm.listData.filter(function (item) {
            return item.Checked;
        }).map(function (item) {
            return item.picId;
        }).join(',');
        vm.selPicLen = vm.listData.filter(function (item) {
            return item.Checked;
        }).length;
        if(vm.selPicLen == 0){
            layer.alert('请至少选择一张图片操作！')
        }else{
            //$('#down-pic-order-modal').modal('show');
            req_genereateOrder();
        }
    }
    function req_genereateOrder(callback) {
        req.post('downloadPicture/downForOrder.do', {
            picIds: vm.deleteIdStr,
            langType:window.localStorage.lang
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.orderList = resp.other;
                vm.orderId = resp.other.id;
                $('#down-pic-order-modal').modal('show');
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
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
        window.open('photo/downloadPicture/downByOrder.do' + params, '_blank');
        req_delete(function (resp) {
            renderList();
        })
        resetData();
    }

});