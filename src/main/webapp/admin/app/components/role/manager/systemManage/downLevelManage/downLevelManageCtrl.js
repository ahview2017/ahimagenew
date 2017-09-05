/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('downLevelManageCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, modalOperate, allModalMove) {
    var vm = this;
    //下载级别模态框隐藏
    vm.downLevelModalHide = function (modalId) {
        modalOperate.modalHide(modalId);
    };
    //下载级别添加模态框显示
    vm.downLevelAddModalShow = function (modalId) {
       /* initSetting();*/
        modalOperate.modalShow(modalId);
    };
    //下载级别修改模态框显示
    vm.downLevelModifyModalShow = function (modalId) {
        modalOperate.modalShow(modalId);
    };
    //下载级别删除模态框显示
    vm.downLevelDelModalShow = function (modalId) {
        modalOperate.modalShow(modalId);
    };
    //移动模态框
    vm.moveModal = function(dragDiv,tagDiv) {
        allModalMove.modalMove(dragDiv,tagDiv);
    }
    //初始化设置
    function initSetting() {
        vm.add = {
            levelName: '',
            limitPxH: '',
            limitPxW: '',
            isWatermark: '',
            watermarkLocation: '',
            watermarkPicId: ''
        };
        vm.modify = {
            levelName: '',
            limitPxH: '',
            limitPxW: '',
            isWatermark: '',
            watermarkLocation: '',
            watermarkPicId: ''
        }
        //存放下载级别数组
        vm.downloadLevelList = [];
        //下载级别总条数
        vm.downLevelList_total = 0;
        //默认当前页1
        vm.pagination = {
            current: 1
        };
        //默认每页10条
        vm.selPageRows = '10';
    }

    //初始化
    function init() {
        initSetting();
        getDownLevelList("", 1, 1);
    }

    init();

    /**
     * 获取下载级别信息表格数据
     */
    function getDownLevelList(levelName, page, type) {
        req.post("downloadLevelCtro/getDownLevelByQuery.do", {
            page: page,
            rows: vm.selPageRows,
            levelName: levelName
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.downloadLevelList = resp.data;
                vm.totalPages = resp.page;
                vm.downLevelList_total = resp.other;
                vm.pagination.current = page;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }


    //页数变化
    vm.pageChanged = function (pageNumber) {
        getDownLevelList(vm.downSearchModel, pageNumber, 1);
    };


    /**
     * 处理全选
     */
    vm.checkBoxArray = [];
    vm.isHadAllCheck = false;
    vm.onCheckAllClick = function () {
        if (vm.isHadAllCheck) {
            vm.checkBoxArray = vm.downloadLevelList.map(function (item) {
                return item.ID
            });
        } else {
            vm.checkBoxArray = vm.downloadLevelList.map(function (item) {
                return false
            });
        }
    };

    /**
     * 监听每一个选项的改变
     */
    $scope.$watchCollection('downLevelManage.checkBoxArray', function (newC) {
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
        getDownLevelList(vm.downSearchModel, 1, 1);
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
            modalOperate.modalShow('down-level-del-modal');
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
                modalOperate.modalShow('down-level-del-modal');
            } else {
                layer.alert("请选择要删除的下载级别");
            }
        }
    };

    /**
     * 删除下载级别信息表格数据
     */
    vm.onDeleteDownTableData = function () {
        req.post('downloadLevelCtro/delLevelByIds.do', {
            levelIds: vm.deleteDataParamsId
        }).success(function (resp) {
            if (resp.code == '211') {
                modalOperate.modalHide('down-level-del-modal');
                layer.alert('删除成功');
                getDownLevelList("", 1, 1);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    };

    /**
     * 添加下载级别
     */
    vm.addDownloadLevel = function (modalId) {
        req_addDownloadLevel(modalId);
    };

    /**
     * 添加下载级别请求
     */
    function req_addDownloadLevel(modalId) {
        if (!vm.add.levelName) {
            layer.alert('请填写级别名称');
            return;
        }
        if (!vm.add.limitPxH) {
            layer.alert('请填写最长边限定-像素');
            return;
        }
       /* if (!vm.add.limitPxW) {
            layer.alert('请填写限制像素-宽');
            return;
        }*/
        req.post('downloadLevelCtro/addDownloadLevel.do', {
            levelName: vm.add.levelName,
            limitPxH: vm.add.limitPxH,
            limitPxW: vm.add.limitPxW,
            isWatermark: vm.add.isWatermark,
            watermarkLocation: vm.add.watermarkLocation
        }).success(function (resp) {
            if (resp.code == '211') {
                modalOperate.modalHide(modalId);
                layer.alert('添加下载级别成功');
                getDownLevelList("", 1, 1);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    /**
     * 展示编辑框
     */
    vm.updateEditId = "";
    vm.onShowEditModelClick = function (editType, mineEditId, minePosition) {
        if (editType == -1) {
            vm.updateEditId = mineEditId;
            var mineEditItemData = vm.downloadLevelList[minePosition];
            showEditFun(mineEditItemData);
            modalOperate.modalShow('down-level-modify-modal');
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
                    layer.alert("只能选择一个下载级别进行编辑");
                } else {
                    vm.updateEditId = editIdArray[0];
                    var position = parseInt(positionStr.split(",")[0]);
                    var editItemData = vm.downloadLevelList[position];
                    showEditFun(editItemData);
                    modalOperate.modalShow('down-level-modify-modal');
                }
            } else {
                layer.alert("请选择要编辑的下载级别");
            }
        }
    };

    /**
     * 显示编辑信息
     */
    function showEditFun(mineEditItemData) {
        vm.modify.levelName = mineEditItemData["LEVEL_NAME"];
        vm.modify.limitPxH = mineEditItemData["LIMIT_PX_H"];
        vm.modify.limitPxW = mineEditItemData["LIMIT_PX_W"];
        vm.modify.isWatermark = mineEditItemData["IS_WATERMARK"] + "";
        vm.modify.watermarkLocation = mineEditItemData["WATERMARK_LOCATION"] + "";
    }

    /**
     * 编辑下载管理
     */
    vm.onEditDownClick = function () {
        req.post('downloadLevelCtro/updateDownloadLevel.do', {
            levelName: vm.modify.levelName,
            limitPxH: vm.modify.limitPxH,
            limitPxW: vm.modify.limitPxW,
            isWatermark: vm.modify.isWatermark,
            watermarkLocation: vm.modify.watermarkLocation,
            id: vm.updateEditId
        }).success(function (resp) {
            if (resp.code == '211') {
                modalOperate.modalHide('down-level-modify-modal');
                layer.alert('编辑下载级别成功');
                getDownLevelList("", 1, 1);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    };

});