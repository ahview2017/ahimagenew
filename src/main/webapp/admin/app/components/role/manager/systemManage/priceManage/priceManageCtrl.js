/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('priceManageCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, modalOperate, allModalMove) {
    var vm = this;

    //价格管理模态框隐藏
    vm.priceModalHide = function (modalId) {
        modalOperate.modalHide(modalId);
    };
    //模态框遮罩层显示
    function maskShow() {
        $rootScope.layerIfShow = true;
    }

    //模态框遮罩层隐藏
    function maskHide() {
        $rootScope.layerIfShow = false;
    }
    //移动模态框
    vm.moveModal = function(dragDiv,tagDiv) {
        allModalMove.modalMove(dragDiv,tagDiv);
    }

    //价格模态框显示
    vm.priceModalShow = function (modalId) {
        $('#' + modalId).show();
        maskShow();
    };
    //价格模态框隐藏
    vm.priceModalHide = function (modalId) {
        $('#' + modalId).hide();
        maskHide();
    };


    //初始化相关配置
    function initSetting(){
        //存放价格管理数组
        vm.imgPriceArray = [];
        //价格管理总条数
        vm.priceList_total = 0;
        //默认当前页1
        vm.pagination = {
            current: 1
        };
        //默认每页10条
        vm.selPageRows = '10';
    }
    //初始化
    function init(){
        initSetting();
        getImgTableData(1, 1);
    }
    init();

    /**
     * 获取图片价格表格数据
     */
    function getImgTableData(page, type) {
        req.post('pictureprice/show.do', {
            page: page,
            rows: vm.selPageRows
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.imgPriceArray = resp.data;
                vm.totalPages = resp.page;
                vm.priceList_total = resp.other;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }


    //页数变化
    vm.pageChanged = function (pageNumber) {
        getImgTableData(pageNumber, 1);
    };



    /**
     * 处理全选
     */
    vm.checkBoxArray = [];
    vm.isHadAllCheck = false;
    vm.onCheckAllClick = function () {
        if (vm.isHadAllCheck) {
            vm.checkBoxArray = vm.imgPriceArray.map(function (item) {
                return item.id
            });
        } else {
            vm.checkBoxArray = vm.imgPriceArray.map(function (item) {
                return false
            });
        }
    };

    /**
     * 监听每一个选项的改变
     */
    $scope.$watchCollection('priceManage.checkBoxArray', function (newC) {
        if (newC.every(function (item) {
                return item != false;
            })) {
            vm.isHadAllCheck = true;
        } else {
            vm.isHadAllCheck = false;
        }
    });

    /**
     * 点击删除
     */
    vm.deleteDataParamsId = "";
    vm.onShowDeleteModelClick = function (deleteType, mineDeleteId) {
        if (deleteType == -1) {
            vm.deleteDataParamsId = mineDeleteId;
            vm.priceModalShow('price-del-modal');
        } else {
            var paramsId = "";
            for (var c = 0; c < vm.checkBoxArray.length; c++) {
                var checkBoxItem = vm.checkBoxArray[c];
                if (checkBoxItem != false) {
                    paramsId += checkBoxItem + ",";
                }
            }
            if (paramsId != "") {
                vm.deleteDataParamsId = paramsId.substr(0, paramsId.length - 1);
                vm.priceModalShow('price-del-modal');
            } else {
                layer.alert("请选择要删除的图片");
            }
        }
    };

    /**
     * 删除图片价格表格数据
     */
    vm.onDeleteImgTableData = function () {
        req.post('pictureprice/delete.do', {
            id: vm.deleteDataParamsId
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.priceModalHide('price-del-modal');
                layer.alert('删除成功');
                getImgTableData(1, 1);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    };

    /**
     * 打开添加模态框
     */
    vm.onAddPriceModelClick = function () {
        vm.imgTypeModel = "";
        vm.imgFinishPriceModel = "";
        vm.imgPayPriceModel = "";
        vm.priceModalShow('price-add-modal');
    };

    /**
     * 添加图片价格
     */
    vm.onAddImagePrice = function () {
        var imgPriceExp = /^(\-|\+)?\d+(\.\d+)?$/;
        if(vm.imgTypeModel == ''){
           layer.alert('请选择图片类型');
            return;
        }
        if(!vm.imgFinishPriceModel){
            layer.alert('请填写成交价格');
            return;
        }
        if(vm.imgFinishPriceModel && !(imgPriceExp.test(vm.imgFinishPriceModel))){
            layer.alert('请填写正确格式的成交价格');
            return;
        }
        if(!vm.imgPayPriceModel){
            layer.alert('请填写支付价格');
            return;
        }
        if(vm.imgPayPriceModel && !(imgPriceExp.test(vm.imgPayPriceModel))){
            layer.alert('请填写正确格式的支付价格');
            return;
        }
        req.post('pictureprice/add.do', {
            pictureType: vm.imgTypeModel,
            setPrice: vm.imgFinishPriceModel,
            payPrice: vm.imgPayPriceModel
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.priceModalHide('price-add-modal');
                layer.alert('添加图片价格成功');
                getImgTableData(1, 1);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    };

    /**
     * 展示编辑框
     */
    vm.updateEditId = "";
    vm.onShowEditModelClick = function (editType, mineEditId, minePosition) {
        if (editType == -1) {
            vm.updateEditId = mineEditId;
            var mineEditItemData = vm.imgPriceArray[minePosition];
            vm.imgEditTypeModel = mineEditItemData["pictureType"] + "";
            vm.imgEditFinishPriceModel = mineEditItemData["setPrice"];
            vm.imgEditPayPriceModel = mineEditItemData["payPrice"];
            vm.priceModalShow('price-modify-modal');
        } else {
            var editId = "";
            var positionStr = "";
            for (var c = 0; c < vm.checkBoxArray.length; c++) {
                var checkBoxItem = vm.checkBoxArray[c];
                if (checkBoxItem != false) {
                    editId += checkBoxItem + ",";
                    positionStr += c + ",";
                }
            }
            if (editId != "") {
                var editIdArray = editId.split(',');
                if (editIdArray.length > 2) {
                    layer.alert("只能选择一个图片价格进行编辑");
                } else {
                    vm.updateEditId = editIdArray[0];
                    var position = parseInt(positionStr.split(",")[0]);
                    var editItemData = vm.imgPriceArray[position];
                    vm.imgEditTypeModel = editItemData["pictureType"] + "";
                    vm.imgEditFinishPriceModel = editItemData["setPrice"];
                    vm.imgEditPayPriceModel = editItemData["payPrice"];
                    vm.priceModalShow('price-modify-modal');
                }
            } else {
                layer.alert("请选择要编辑的图片");
            }
        }
    };

    /**
     * 编辑图片价格
     */
    vm.onEditImagePrice = function () {
        var imgPriceExp = /^(\-|\+)?\d+(\.\d+)?$/;
        if(vm.imgEditTypeModel == ''){
            layer.alert('请选择图片类型');
            return;
        }
        if(!vm.imgEditFinishPriceModel){
            layer.alert('请填写成交价格');
            return;
        }
        if(vm.imgEditFinishPriceModel && !(imgPriceExp.test(vm.imgEditFinishPriceModel))){
            layer.alert('请填写正确格式的成交价格');
            return;
        }
        if(!vm.imgEditPayPriceModel){
            layer.alert('请填写支付价格');
            return;
        }
        if(vm.imgEditPayPriceModel && !(imgPriceExp.test(vm.imgEditPayPriceModel))){
            layer.alert('请填写正确格式的支付价格');
            return;
        }
        req.post('pictureprice/edit.do', {
            id: vm.updateEditId,
            pictureType: vm.imgEditTypeModel,
            setPrice: vm.imgEditFinishPriceModel,
            payPrice: vm.imgEditPayPriceModel
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.priceModalHide('price-modify-modal');
                layer.alert('编辑图片价格成功');
                getImgTableData(1, 1);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    };

});