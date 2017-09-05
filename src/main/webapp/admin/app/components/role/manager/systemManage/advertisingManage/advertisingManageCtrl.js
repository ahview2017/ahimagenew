/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('advertisingManageCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, modalOperate) {
    var vm = this;
    vm.langType=window.localStorage.lang;
    //页面初始化相关设置
    function initSetting(){
        //存放广告位数组
        vm.adInformArray = [];
        //广告位总条数
        vm.adverList_total = 0;
        //默认当前页1
        vm.pagination = {
            current: 1
        };
        //默认每页10条
        vm.selPageRows = '10';
    }

    //页面初始化
    function init(){
        initSetting();
        // 请求
        getAdTableData("", 1);
        getTopicListData();
    }
    init();

    //页数变化
    vm.pageChanged = function (pageNumber) {
        getAdTableData("", pageNumber);
    };
    //模态框显示
    vm.modalShow = function (modalId) {
        $('#' + modalId).show();
        //模态框遮罩层显示
        $rootScope.layerIfShow = true;
    };

    //模态框隐藏
    vm.modalHide = function (modalId) {
        $('#' + modalId).hide();
        //模态框遮罩层隐藏
        $rootScope.layerIfShow = false;
    };
    //广告位模态框隐藏
    vm.advertiseModalHide = function (modalId) {
        modalOperate.modalHide(modalId);
    };

    /**
     * 获取广告表格数据
     */
    function getAdTableData(searchName, page) {
        var paramsObj = {
            page: page,
            rows: vm.selPageRows,
            langType:window.localStorage.lang,
            strWhere:searchName
        };
        req.post("adver/show.do", paramsObj).success(function (resp) {
            if (resp.code == '211') {
                vm.adInformArray = resp.data;
                vm.totalPages = resp.page;
                vm.adverList_total = resp.other;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    /**
     * 获取添加文件名
     */
    $(document).on('change', '#addInputFileId', function (e) {
        var addName = e.currentTarget.files[0].name;
        vm.addAdImageModel = addName;
    });

    /**
     * 获取编辑文件名
     */
    $(document).on('change', '#editInputFileId', function (e) {
        //e.currentTarget.files 是一个数组，如果支持多个文件，则需要遍历
        var name = e.currentTarget.files[0].name;
        vm.editAdImageModel = name;
    });

    /**
     * 处理全选
     */
    vm.checkBoxArray = [];
    vm.isHadAllCheck = false;
    vm.onCheckAllClick = function () {
        if (vm.isHadAllCheck) {
            vm.checkBoxArray = vm.adInformArray.map(function (item) {
                return item.id
            });
        } else {
            vm.checkBoxArray = vm.adInformArray.map(function (item) {
                return false
            });
        }
    };

    /**
     * 监听每一个选项的改变
     */
    $scope.$watchCollection('advertisingManage.checkBoxArray', function (newC) {
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
        getAdTableData(vm.adSearchModel, 1);
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
     * 打开添加
     */
    vm.onAddAdModelClick = function () {
        showAddInformFun();
        vm.modalShow('advertisement-add-modal');
    };

    /**
     * 点击删除
     */
    vm.deleteDataParamsId = "";
    vm.onShowDeleteModelClick = function (deleteType, mineDeleteId) {
        if (deleteType == -1) {
            vm.deleteDataParamsId = mineDeleteId;
            vm.modalShow('advertisement-del-modal');
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
                vm.modalShow('advertisement-del-modal');
            } else {
                layer.alert("请选择要删除的广告位");
            }
        }
    };

    /**
     * 删除广告位信息表格数据
     */
    vm.onDeleteAdTableData = function () {
        req.post('adver/delete.do', {
            id: vm.deleteDataParamsId
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.modalHide('advertisement-del-modal');
                layer.alert('删除成功');
                getAdTableData("", 1);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    };

    /**
     * 添加广告位信息
     */
    vm.onAddAdInformClick = function () {
        //$("#addAdInformFormId").submit();
        vm.langType=$scope.langType;
        if (!vm.addAdNameModel) {
            layer.msg('请输入广告名称');
            return;
        }
        if (!vm.addAdClassModel) {
            layer.msg('请选择广告分类');
            return;
        }
        if(!vm.addAdPositionModel){
           layer.alert('请选择位置');
            return;
        }
        if(!vm.addAdTopicModel && !vm.addAdUrlModel){
            layer.alert('请在专题页和链接中选择一个');
            return;
        }
        if(vm.addAdTopicModel && vm.addAdUrlModel){
            layer.alert('专题页和链接只能选择一个');
            return;
        }
        /*if (!vm.addAdWidthModel) {
            layer.msg('请输入缩略图宽度');
            return;
        }
        if (!vm.addAdHeightModel) {
            layer.msg('请输入缩略图高度');
            return;
        }*/
        if (!vm.addAdImageModel) {
            layer.msg('请选择上传文件');
            return;
        }
        if (!vm.addAdRemarkModel) {
            layer.msg('请输入备注');
            return;
        }
        if (!vm.addAdDisplayModel) {
            layer.msg('请选择是否显示');
            return;
        }
        if (!vm.addAdOrderModel) {
            layer.msg('请输入排序号');
            return;
        }
        $("#addAdInformFormId").ajaxSubmit(function (resp) {
            if (resp.code == '211') {
                vm.modalHide('advertisement-add-modal');
                layer.msg('添加广告位成功');
                //showAddInformFun();
                getAdTableData( "", 1);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
        return false;
    };

    /**
     * 提交返回处理，放在点击事件外面避免多次绑定load事件，重复调用
     */
    //$('#addAdInformFrameId').on('load', function () {
    //    //获取到iFrame里面的html元素
    //    var frameValue = $('#addAdInformFrameId').contents();
    //    var str = $(frameValue).find('pre').html() || $(frameValue).find('body').html();
    //    if (str) {
    //        try {
    //            var resp = $.parseJSON(str);
    //            if (resp.code == '211') {
    //                vm.modalHide('advertisement-add-modal');
    //                layer.alert('添加广告位成功');
    //                //showAddInformFun();
    //                getAdTableData(false, "", 1, 1);
    //            } else {
    //                layer.alert("添加广告位失败");
    //            }
    //        } catch (error) {
    //            layer.alert("添加广告位失败");
    //        }
    //    }
    //});

    /**
     * 置空添加信息
     */
    function showAddInformFun() {
        vm.addAdNameModel = "";
        vm.addAdClassModel = "";
        vm.addAdPositionModel = "";
        vm.addAdTopicModel = "";
        vm.addAdUrlModel = "";
        vm.addAdWidthModel = "";
        vm.addAdHeightModel = "";
        var mineAddFile = $('#addInputFileId');
        mineAddFile.after(mineAddFile.clone().val(""));
        mineAddFile.remove();
        vm.addAdImageModel = "";
        vm.addAdRemarkModel = "";
        vm.addAdDisplayModel = "";
        vm.addAdOrderModel = "";
    }

    /**
     * 展示编辑框
     */
    vm.updateEditId = "";
    vm.onShowEditModelClick = function (editType, mineEditId, minePosition) {
        if (editType == -1) {
            vm.updateEditId = mineEditId;
            var mineEditItemData = vm.adInformArray[minePosition];
            showEditInformFun(mineEditItemData);
            vm.modalShow('advertisement-modify-modal');
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
                    layer.alert("只能选择一个广告位信息进行编辑");
                } else {
                    vm.updateEditId = editIdArray[0];
                    var position = parseInt(positionStr.split(",")[0]);
                    var editItemData = vm.adInformArray[position];
                    showEditInformFun(editItemData);
                    vm.modalShow('advertisement-modify-modal');
                }
            } else {
                layer.alert("请选择要编辑的广告位信息");
            }
        }
    };

    /**
     * 编辑信息
     */
    function showEditInformFun(editItemData) {

        vm.editAdNameModel = editItemData["name"];
        vm.editAdClassModel = editItemData["style"] + "";
        if(editItemData["position"]){
            vm.editAdPositionModel = editItemData["position"] + "";
        }else{
            vm.editAdPositionModel = '';
        }
        if(editItemData["topicId"]){
            vm.editAdTopicModel = editItemData["topicId"] + '';
        }else{
            vm.editAdTopicModel = '';
        }
        vm.editAdUrlModel = editItemData["url"];
        //vm.editAdWidthModel = editItemData["bwidth"];
        //vm.editAdHeightModel = editItemData["bheight"];
        vm.editAdImageModel = editItemData["pic"];
        vm.editAdRemarkModel = editItemData["remarks"];
        vm.editAdDisplayModel = editItemData["display"] + "";
        vm.editAdOrderModel = editItemData["orderno"];
    }

    /**
     * 编辑广告信息
     */
    vm.onEditAdInformClick = function () {
        if(vm.editAdTopicModel && vm.editAdUrlModel){
            layer.alert('专题页和链接只能选择一个');
            return;
        }
        //$("#editAdInformFormId").submit();
        $("#editAdInformFormId").ajaxSubmit(function (resp) {
            if (resp.code == '211') {
                vm.modalHide('advertisement-modify-modal');
                layer.msg('编辑广告位信息成功');
                getAdTableData("", 1);
            } else {
                layer.alert('编辑广告位信息失败');
            }
        });
        return false;
    };

    /**
     * 提交返回处理，放在点击事件外面避免多次绑定load事件，重复调用
     */
    //$('#editAdInformFrameId').on('load', function () {
    //    //获取到iFrame里面的html元素
    //    var frameValue = $('#editAdInformFrameId').contents();
    //    var str = $(frameValue).find('pre').html() || $(frameValue).find('body').html();
    //    if (str) {
    //        try {
    //            var resp = $.parseJSON(str);
    //            if (resp.code == '211') {
    //                vm.modalHide('advertisement-modify-modal');
    //                layer.alert('编辑广告位信息成功');
    //                getAdTableData(false, "", 1, 1);
    //            } else {
    //                layer.alert('编辑广告位信息失败');
    //            }
    //        } catch (error) {
    //            layer.alert('编辑广告位信息失败');
    //        }
    //    }
    //});

    /**
     * 传入拖拽发生id和被拖拽id，如果相同，则本体就是按钮和拖拽对象
     */
    vm.modalMove = function (dragDiv, tagDiv) {

        var dragDiv = document.getElementById(dragDiv);
        var tagDiv = document.getElementById(tagDiv);
        var tagContainer = document;
        var e, offsetT, offsetL, downX, downY, moveX, moveY;

        dragDiv.onmouseover = function (e) {
            this.style.cursor = "move";
        };

        dragDiv.onmousedown = function (e) {
            e = e || window.event;
            offsetT = tagDiv.offsetTop;
            offsetL = tagDiv.offsetLeft;
            downX = e.clientX;
            downY = e.clientY;

            dragDiv.onmouseup = function (e) {
                tagContainer.onmousemove = function () {
                    return false;
                }
            };

            tagContainer.onmousemove = function (e) {
                e = e || window.event;
                moveX = e.clientX;
                moveY = e.clientY;
                tagDiv.style.top = offsetT + (moveY - downY) + "px";
                tagDiv.style.left = offsetL + (moveX - downX) + "px";
            }
        }
    };

    /**
     * 获取专题页数据
     */
    function getTopicListData() {
        req.post("topic/showTopicToAdv.do", {}).success(function (resp) {
            if (resp.code == '211') {
                vm.topicListArray = resp.data;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }


});