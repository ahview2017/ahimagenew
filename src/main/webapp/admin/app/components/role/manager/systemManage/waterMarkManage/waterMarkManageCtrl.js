/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('waterMarkManageCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, modalOperate, allModalMove) {
    var vm = this;
    vm.checkedID = -1; // 用于保存单一选中时的item的id

    //水印管理模态框隐藏
    vm.waterMarkModalHide = function (modalId) {
        modalOperate.modalHide(modalId);
    }
    //移动模态框
    vm.moveModal = function (dragDiv, tagDiv) {
        allModalMove.modalMove(dragDiv, tagDiv);
    }
    //水印管理添加模态框显示
    vm.waterMarkAddModalShow = function (modalId) {
        //initSetting();
        modalOperate.modalShow(modalId);
    }
    //水印管理修改模态框显示
    vm.waterMarkModifyModalShow = function (modalId, modifyID) {

        vm.checkedID = -1;

        if (modifyID != null) { // 直接点击item后的编辑
            vm.checkedID = modifyID;
        } else {  // 点击表格上面的编辑按钮
            //判断当前选中的item有几个
            var checkedNum = 0;
            for (var ii = 0; ii < vm.checkBoxArray.length; ii++) {
                if (vm.checkBoxArray[ii]) {
                    checkedNum += 1;
                    vm.checkedID = vm.checkBoxArray[ii];
                }
            }
            if (checkedNum == 0) {
                vm.checkedID = -1;
                layer.alert("请选择一个水印信息进行编辑");
            } else if (checkedNum != 1) {
                vm.checkedID = -1;
                layer.alert("只能选择一个水印信息进行编辑");
            }
        }

        // 数据回显
        if (vm.checkedID != -1) {
            for (var jj = 0; jj < vm.waterMarkList.length; jj++) {
                if (vm.waterMarkList[jj].id == vm.checkedID) {
                    //水印名称
                    vm.modify.waterMarkTitle = vm.waterMarkList[jj].title;
                    //水印描述
                    vm.modify.waterMarkDes = vm.waterMarkList[jj].description;

                    //图片回显
                    $('#modifyWaterMarkImageId').attr("src", vm.waterMarkList[jj].wmPath);

                    //是否是默认水印图片回显
                    var sel = document.getElementById("isDefaultWmpicSelect");
                    sel.options[(vm.waterMarkList[jj].isDefaultWmpic + 1)].selected = true;
                }
            }
            modalOperate.modalShow(modalId);
        }
    }
    //水印管理删除模态框显示
    vm.waterMarkDelModalShow = function (modalId, delID) {

        vm.checkedID = -1;

        if (delID != null) { // 直接点击一个item上面的删除
            vm.checkedID = delID;
        } else { // 点击表格上方的删除
            //判断当前选中的item有几个
            var checkedNum = 0;
            for (var ii = 0; ii < vm.checkBoxArray.length; ii++) {
                if (vm.checkBoxArray[ii]) {
                    checkedNum += 1;
                    if (vm.checkedID == -1) {
                        vm.checkedID = vm.checkBoxArray[ii];
                    } else {
                        vm.checkedID = vm.checkedID + "," + vm.checkBoxArray[ii];
                    }
                }
            }
        }

        if (vm.checkedID == -1) {
            layer.alert("请选择要删除的水印信息");
        } else {
            modalOperate.modalShow(modalId);
        }

    }
    //初始化设置
    function initSetting() {
        vm.add = {
            id: '',
            createTime: '',
            createUser: '',
            description: '',
            filename: '',
            isDefaultWmpic: '',
            siteId: '',
            title: '',
            watermarkLocation:'1'
        }
        vm.modify = {
            id: '',
            createTime: '',
            createUser: '',
            description: '',
            filename: '',
            isDefaultWmpic: '',
            siteId: '',
            title: '',
            watermarkLocation:''
        }
        //存放水印信息数组
        vm.waterMarkList = [];
        //水印总条数
        vm.waterMarkList_total = 0;
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
        getWaterMarkList('', 1, 1);
    }

    init();
    //获取水印列表
    function getWaterMarkList(searchTitle, currentPage, type) {
        req.post('waterMarkCtro/getWaterMarkByQuery.do', {
            title: searchTitle,
            page: currentPage,
            rows: vm.selPageRows
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.waterMarkList = resp.data;
                vm.totalPages = resp.page;
                vm.waterMarkList_total = resp.other;
                vm.pagination.current = currentPage;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //页数变化
    vm.pageChanged = function (pageNumber) {
        getWaterMarkList('', pageNumber, 1);
    };

    //添加水印
    vm.addWaterMark = function (modalId) {
        req_addWaterMark(modalId);
    };
    //添加水印请求
    function req_addWaterMark(modalId) {
        if (!vm.add.waterMarkTitle) {
            layer.alert('请填写水印名称');
            return;
        }
        if (!vm.add.waterMarkDes) {
            layer.alert('请填写水印说明');
            return;
        }
        //var form = document.getElementById("addWaterMarkForm");
        //form.submit();
        $("#addWaterMarkForm").ajaxSubmit(function (resp) {
            if (resp.code == '211') {
                vm.waterMarkModalHide('water-mark-add-modal');
                layer.msg("添加水印信息成功");
                getWaterMarkList('', 1, 1);
            } else {
                layer.alert("添加水印信息失败");
            }
        });
        return false;

        // var options = {
        //     target: '#add_wmFile_success',
        //     url: 'waterMarkCtro/addWaterMark.do',
        //     type: 'post',
        //     dataType: 'text/html',
        //     success: function (responseText, statusText, xhr, $form) {
        //         if (responseText.code == '211') {
        //             modalOperate.modalHide(modalId);
        //             getWaterMarkList('');
        //         } else {
        //             console.log(responseText.msg);
        //         }
        //     }
        // };
        // form.submit(function () {
        //     $scope.ajaxSubmit(options);
        //     return false;// 阻止表单自动提交事件
        // });
    }

    /**
     * 添加水印，提交返回处理，放在点击事件外面避免多次绑定load事件，重复调用
     */
        //$('#add_wmFile_success').on('load', function () {
        //    if (vm.addWaterMarkFormFlag) {
        //        //获取到iFrame里面的html元素
        //        var frameValue = $('#add_wmFile_success').contents();
        //        var str = $(frameValue).find('pre').html() || $(frameValue).find('body').html();
        //        try {
        //            vm.checkBoxArray = [];
        //            vm.isHadAllCheck = false;
        //            var resp = $.parseJSON(str);
        //            if (resp.code == '211') {
        //                vm.waterMarkModalHide('water-mark-add-modal');
        //                layer.alert("添加水印信息成功");
        //                getWaterMarkList('', 1, 1);
        //            } else {
        //                layer.alert("添加水印信息失败");
        //            }
        //        } catch (error) {
        //            layer.alert("添加水印信息失败");
        //        }
        //    }
        //});

        //修改水印
    vm.updateWaterMark = function (modalId) {
        req_updateWaterMark(modalId);
    };
    //修改水印请求
    function req_updateWaterMark(modalId) {
        if (!vm.modify.waterMarkTitle) {
            layer.alert('请填写水印名称');
            return;
        }
        if (!vm.modify.waterMarkDes) {
            layer.alert('请填写水印说明');
            return;
        }
        //var modifyForm = document.getElementById("modifyWaterMarkForm");
        //modifyForm.submit();
        $("#modifyWaterMarkForm").ajaxSubmit(function (resp) {
            if (resp.code == '211') {
                layer.msg("修改水印信息成功");
                vm.waterMarkModalHide('water-mark-modify-modal');
                getWaterMarkList('', 1, 1);
            } else {
                layer.alert("修改水印信息失败");
            }
        });
        return false;
        // var modifyOptions = {
        //     target: '#modify_wmFile_success',
        //     url: 'waterMarkCtro/updateWaterMark.do',
        //     type: 'post',
        //     dataType: 'text/html',
        //     success: function (resp, statusText, xhr, $form) {
        //         if (resp.code == '211') {
        //             modalOperate.modalHide(modalId);
        //             getWaterMarkList('');
        //         } else {
        //             console.log(resp.msg);
        //         }
        //     }
        // };
        // modifyForm.submit(function () {
        //     $scope.ajaxSubmit(modifyOptions);
        //     return false;// 阻止表单自动提交事件
        // });
    }

    /**
     * 修改水印，提交返回处理，放在点击事件外面避免多次绑定load事件，重复调用
     */
        //$('#modify_wmFile_success').on('load', function () {
        //    if (vm.modifyWaterMarkFormFlag) {
        //        //获取到iFrame里面的html元素
        //        var frameValue = $('#modify_wmFile_success').contents();
        //        var str = $(frameValue).find('pre').html() || $(frameValue).find('body').html();
        //        try {
        //            vm.checkBoxArray = [];
        //            vm.isHadAllCheck = false;
        //            var resp = $.parseJSON(str);
        //            if (resp.code == '211') {
        //                layer.alert("修改水印信息成功");
        //                vm.waterMarkModalHide('water-mark-modify-modal');
        //                getWaterMarkList('', 1, 1);
        //            } else {
        //                layer.alert("修改水印信息失败");
        //            }
        //        } catch (error) {
        //            layer.alert("修改水印信息失败");
        //        }
        //    }
        //});

        //删除水印
    vm.delWaterMark = function (modalId) {
        req_delWaterMark(modalId);
    }
    //删除水印请求
    function req_delWaterMark(modalId) {

        req.post('waterMarkCtro/delWaterMark.do', {
            wmIds: vm.checkedID
        }).success(function (resp) {
            vm.checkBoxArray = [];
            vm.isHadAllCheck = false;
            if (resp.code == '211') {
                getWaterMarkList('', 1, 1);
                modalOperate.modalHide(modalId);
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
            vm.checkBoxArray = vm.waterMarkList.map(function (item) {
                return item.id
            });
        } else {
            vm.checkBoxArray = vm.waterMarkList.map(function (item) {
                return false
            });
        }
    };

    /**
     * 监听每一个选项的改变
     */
    $scope.$watchCollection('waterMarkManage.checkBoxArray', function (newC) {
        if (newC.every(function (item) {
                return item != false;
            })) {
            vm.isHadAllCheck = true;
        } else {
            vm.isHadAllCheck = false;
        }
    });

    //回车搜索
    vm.enterSearch = function(){
        var e = e || window.event;
        var code = e.keyCode ? e.keyCode : e.which;
        if (code == 13) {
            vm.onSearchDataClick();
        }
    }
    /**
     * 点击搜索
     */
    vm.onSearchDataClick = function () {
        var searchTtle = $(" #water-mark-search-input ").val().replace(/[]/g, "");
        if (searchTtle == "" || undefined || null) {
            // layer.alert("请输入关键字");
            getWaterMarkList('', 1, 1);
        } else {
            getWaterMarkList(searchTtle, 1, 1);
        }
    };

});