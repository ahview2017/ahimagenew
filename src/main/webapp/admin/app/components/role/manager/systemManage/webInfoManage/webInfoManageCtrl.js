/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('webInfoManageCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, allModalMove) {
    var vm = this;

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

    //网站信息模态框显示
    vm.webInfoModalShow = function (modalId) {
        $('#' + modalId).show();
        maskShow();
    };
    //网站信息模态框隐藏
    vm.webInfoModalHide = function (modalId) {
        $('#' + modalId).hide();
        maskHide();
    };

    function initSetting(){
        //存放网站信息数组
        vm.webInformArray = [];
        //网站信息总条数
        vm.webInfoList_total = 0;
        //默认当前页1
        vm.pagination = {
            current: 1
        };
        //查询类型
        vm.showType = false;
        //默认每页10条
        vm.selPageRows = '10';
    }
    function init(){
        initSetting();
        getWebTableData(vm.showType, vm.webSearchModel, 1, 1);
    }
    init();
    // 实例化编辑器
    var ue = new UE.ui.Editor({
        //focus时自动清空初始化时的内容
        autoClearinitialContent: true,
        //关闭字数统计
        wordCount: false,
        //关闭elementPath
        elementPathEnabled: false,
        //默认的编辑区域高度
        initialFrameHeight: 300,
        autoHeightEnabled: false,
        autoFloatEnabled: false
    });
    var uEdit = new UE.ui.Editor({
        //focus时自动清空初始化时的内容
        autoClearinitialContent: true,
        //关闭字数统计
        wordCount: false,
        //关闭elementPath
        elementPathEnabled: false,
        //默认的编辑区域高度
        initialFrameHeight: 300,
        autoHeightEnabled: false,
        autoFloatEnabled: false
    });
    ue.render('editWebAddId');
    uEdit.render('editWebEditId');
    //var ue = UE.getEditor('editWebAddId', {
    //    //focus时自动清空初始化时的内容
    //    autoClearinitialContent: true,
    //    //关闭字数统计
    //    wordCount: false,
    //    //关闭elementPath
    //    elementPathEnabled: false,
    //    //默认的编辑区域高度
    //    initialFrameHeight: 300,
    //    autoHeightEnabled: false,
    //    autoFloatEnabled: false
    //});
    //var uEdit = UE.getEditor('editWebEditId', {
    //    //focus时自动清空初始化时的内容
    //    autoClearinitialContent: true,
    //    //关闭字数统计
    //    wordCount: false,
    //    //关闭elementPath
    //    elementPathEnabled: false,
    //    //默认的编辑区域高度
    //    initialFrameHeight: 300,
    //    autoHeightEnabled: false,
    //    autoFloatEnabled: false
    //});
    /**
     * 获取网站信息表格数据
     */
    function getWebTableData(searchType, searchTitle, page, type) {
        var searchUrl = "";
        var paramsObj = {
            page: page,
            rows: vm.selPageRows
        };
        if (searchType) {
            searchUrl = "webmsg/search.do";
            paramsObj['search'] = searchTitle;
        } else {
            searchUrl = "webmsg/show.do";
        }
        req.post(searchUrl, paramsObj).success(function (resp) {
            if (resp.code == '211') {
                vm.webInformArray = resp.data;
                vm.totalPages = resp.page;
                vm.webInfoList_total = resp.other;
                vm.pagination.current = page;
                if(searchType){
                    vm.showType = true;
                }else{
                    vm.showType = false;
                }
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //页数变化
    vm.pageChanged = function (pageNumber) {
        getWebTableData(vm.showType, vm.webSearchModel, pageNumber, 1);
    };


    /**
     * 处理全选
     */
    vm.checkBoxArray = [];
    vm.isHadAllCheck = false;
    vm.onCheckAllClick = function () {
        if (vm.isHadAllCheck) {
            vm.checkBoxArray = vm.webInformArray.map(function (item) {
                return item.id
            });
        } else {
            vm.checkBoxArray = vm.webInformArray.map(function (item) {
                return false
            });
        }
    };

    /**
     * 监听每一个选项的改变
     */
    $scope.$watchCollection('webInfoManage.checkBoxArray', function (newC) {
        if (newC.every(function (item) {
                return item != false;
            })) {
            vm.isHadAllCheck = true;
        } else {
            vm.isHadAllCheck = false;
        }
    });

    /**
     * 点击搜索
     */
    vm.onSearchDataClick = function () {
        getWebTableData(true, vm.webSearchModel, 1, 1);
    };

    /**
     * 回车搜索
     */
    vm.onEnterSearchClick = function (e) {
        var keyCode = window.event ? e.keyCode : e.which;
        if (keyCode == 13) {
            vm.onSearchDataClick();
        }
    };

    /**
     * 点击删除
     */
    vm.deleteDataParamsId = "";
    vm.onShowDeleteModelClick = function (deleteType, mineDeleteId) {
        if (deleteType == -1) {
            vm.deleteDataParamsId = mineDeleteId;
            vm.webInfoModalShow('webinfo-del-modal');
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
                vm.webInfoModalShow('webinfo-del-modal');
            } else {
                layer.alert("请选择要删除的网站信息");
            }
        }
    };

    /**
     * 删除网站信息表格数据
     */
    vm.onDeleteWebTableData = function () {
        req.post('webmsg/delete.do', {
            id: vm.deleteDataParamsId
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.webInfoModalHide('webinfo-del-modal');
                layer.alert('删除成功');
                getWebTableData(vm.showType, vm.webSearchModel, 1, 1);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    };

    /**
     * 添加网站信息
     */
    vm.onAddWebInformClick = function () {
        var mineAddContent = UE.getEditor('editWebAddId').getContent();
        req.post('webmsg/add.do', {
            title: vm.webTitleModel,
            detail: mineAddContent
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.webInfoModalHide('webinfo-add-modal');
                layer.alert('添加网站信息成功');
                vm.webTitleModel = "";
                UE.getEditor('editWebAddId').setContent("", false);
                getWebTableData(vm.showType, vm.webSearchModel, 1, 1);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    };

    /**
     * 取消模态框
     * @param typeFlag true:添加框，false:编辑框
     */
    vm.onCancelWebInformModalClick = function (typeFlag) {
        if (typeFlag) {
            vm.webTitleModel = "";
            UE.getEditor('editWebAddId').setContent("", false);
            vm.webInfoModalHide('webinfo-add-modal');
        } else {
            vm.webEditTitleModel = "";
            UE.getEditor('editWebEditId').setContent("", false);
            vm.webInfoModalHide('webinfo-modify-modal');
        }
    };

    /**
     * 展示编辑框
     */
    vm.updateEditId = "";
    vm.onShowEditModelClick = function (editType, mineEditId, minePosition) {
        if (editType == -1) {
            vm.updateEditId = mineEditId;
            var mineEditItemData = vm.webInformArray[minePosition];
            vm.webEditTitleModel = mineEditItemData["title"];
            vm.webEditContainerModel = mineEditItemData["detail"];
            var mineEditDetail = mineEditItemData["detail"];
            if (mineEditDetail != null) {
                UE.getEditor('editWebEditId').setContent(mineEditDetail, false);
            }
            vm.webInfoModalShow('webinfo-modify-modal');
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
                    layer.alert("只能选择一个网站信息进行编辑");
                } else {
                    vm.updateEditId = editIdArray[0];
                    var position = parseInt(positionStr.split(",")[0]);
                    var editItemData = vm.webInformArray[position];
                    vm.webEditTitleModel = editItemData["title"];
                    var itemEditContent = editItemData["detail"];
                    if (itemEditContent != null) {
                        UE.getEditor('editWebEditId').setContent(itemEditContent, false);
                    }
                    vm.webInfoModalShow('webinfo-modify-modal');
                }
            } else {
                layer.alert("请选择要编辑的网站信息");
            }
        }
    };

    /**
     * 编辑网站信息
     */
    vm.onEditWebInformClick = function () {
        var mineEditContent = UE.getEditor('editWebEditId').getContent();
        req.post('webmsg/edit.do', {
            id: vm.updateEditId,
            title: vm.webEditTitleModel,
            detail: mineEditContent
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.webInfoModalHide('webinfo-modify-modal');
                layer.alert('编辑网站信息成功');
                vm.webEditTitleModel = "";
                UE.getEditor('editWebEditId').setContent("", false);
                getWebTableData(vm.showType, vm.webSearchModel, vm.pagination.current, 1);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    };

});