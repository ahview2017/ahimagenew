/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('sortManageCtrl', function ($scope, $cookies, req, md5, $state, $rootScope) {
    var vm = this;

    function initSetting() {
        vm.classifyIfShowFlag = true;
        vm.detailSortIfShowFlag = true;
    }

    function init() {
        initSetting();
        getSortList();
    }

 	init();

    //用户分类列表显示切换
    vm.userClassifyShowToggle = function () {
        vm.classifyIfShowFlag = !vm.classifyIfShowFlag;
    };

    //具体的用户分类显示切换
    vm.detailSortToggle = function () {
        vm.detailSortIfShowFlag = !vm.detailSortIfShowFlag;
    };

    //模态框显示
    function modalShow(modalId) {
        $('#' + modalId).show();
        //模态框遮罩层显示
        $rootScope.layerIfShow = true;
    }

    //模态框隐藏
    function modalHide(modalId) {
        $('#' + modalId).hide();
        //模态框遮罩层隐藏
        $rootScope.layerIfShow = false;
    }

    //分类管理模态框显示
    vm.sortModalShow = function (modalId) {
        modalShow(modalId);
    };

    //分类管理模态框隐藏
    vm.sortModalHide = function (modalId) {
        modalHide(modalId);
    };

    //获取分类列表
    vm.sortDetailsArray = [];
    function getSortList() {
        req.post('classification/selCpCategories.do', {langType:window.localStorage.lang}).success(function (resp) {
            if (resp.code == '211') {
                vm.sortDetailsArray = resp.data;
                jQuery.fn.zTree.destroy("tree");
                var setting = {
                    view: {
                        showLine: false,
                        showIcon: false,
                        fontCss: {color: "#333333"}
                    },
                    check: {
                        enable: false,
                        chkStyle: "checkbox"
                    },
                    data: {
                        key: {
                            checked: "isSelect",
                            children: "categories",
                            name: "categoryName"
                        }
                    },
                    callback: {
                        beforeClick: getCurrentNode,
                        onClick: zTreeOnClick
                    }
                };
                // 默认选中第一个
                jQuery.fn.zTree.init($("#tree"), setting, vm.sortDetailsArray);
                var treeShowObj = jQuery.fn.zTree.getZTreeObj("tree");
                var oneParentNode = treeShowObj.getNodes()[0];
                treeShowObj.selectNode(oneParentNode);
                zTreeOnClick(oneParentNode);
            } else {
                vm.sortDetailsArray = [];
                console.log(resp.msg);
            }
        });
    }

    /**
     * 树的回调事件
     */
    function getCurrentNode(treeId, treeNode) {
        zTreeOnClick(treeNode);
    }

    //判断是否是空对象
    var a = {};
    function isEmptyObject(O){
        for (var x in O){
            return false;
        }
        return true;
    }
    //vm.currentClickNodeId = "";
    //vm.addSortGrade = "";
    //vm.addSortParentId = "";
    $("#showEditSortInformId").hide();
    function zTreeOnClick(treeNode) {
        if (treeNode['type'] != 'click') {
            $("#showEditSortInformId").show();
            vm.currentClickNodeId = treeNode['id'];
            vm.addSortGrade = treeNode['categoryGrade'];
            vm.addSortParentId = treeNode['parentId'];
            $("#editSortIdId").val(treeNode['id']);
            $("#editSortNumId").val(treeNode['categoryNumber']);
            vm.caNumberEditModel = treeNode['categoryNumber'];
            $("#editSortNameId").val(treeNode['categoryName']);
            vm.caNameEditModel = treeNode['categoryName'];
            $("#editSortOrderId").val(treeNode['categoryOrder']);
            vm.caOrderEditModel = treeNode['categoryOrder'];
            $("#editSortTypeId").val(treeNode['showType'] + "");
            $("#editSortRecordId").val(treeNode['memo']);
            if(isEmptyObject(treeNode['image'])){
                $('#editSortImageId').hide();
                $('#noUpImgsDesc').show();
            }else{
                $("#editSortImageId").attr("src", treeNode['image']);
                $('#editSortImageId').show();
                $('#noUpImgsDesc').hide();
            }

        }
    }

    /**
     * 编辑分类信息
     */
    vm.onEditSortClick = function () {
        if (!vm.caNumberEditModel) {
            layer.alert('请输入编号');
            return;
        }
        if (vm.caNumberEditModel && !(/\d/.exec(vm.caNumberEditModel))) {
            layer.alert('编号须为数字');
            return;
        }
        if (!vm.caNameEditModel) {
            layer.alert('请输入名称');
            return;
        }
        if (!vm.caOrderEditModel) {
            layer.alert('请输入排序号');
            return;
        }
        if (vm.caOrderEditModel && !(/\d/.exec(vm.caOrderEditModel))) {
            layer.alert('排序号须为数字');
            return;
        }
        //$("#editSortFormId").submit();
        /*$("#editSortFormId").ajaxSubmit(function (resp) {
            if (resp.code == '211') {
                //vm.currentClickNodeId = "";
                //vm.addSortGrade = "";
                //vm.addSortParentId = "";
                getSortList();
                layer.msg('保存成功');
            } else {
                layer.alert("保存失败");
            }
        });*/
	       $("#editSortFormId").ajaxSubmit({
	            xhrFields: {
	                withCredentials: true
	            },
	            success: function (resp) {
	            if (resp.code == '211') {
	                //vm.currentClickNodeId = "";
	                //vm.addSortGrade = "";
	                //vm.addSortParentId = "";
	                getSortList();
	                layer.msg('保存成功');
	            } else {
	                layer.alert("保存失败");
	            }
	        }});
        return false;
    };

    /**
     * 保存编辑提交返回处理，放在点击事件外面避免多次绑定load事件，重复调用
     */
    //$('#editSortFrameId').on('load', function () {
    //    //获取到iFrame里面的html元素
    //    var frameValue = $('#editSortFrameId').contents();
    //    var str = $(frameValue).find('pre').html() || $(frameValue).find('body').html();
    //    if (str) {
    //        try {
    //            var resp = $.parseJSON(str);
    //            if (resp.code == '211') {
    //                vm.currentClickNodeId = "";
    //                vm.addSortGrade = "";
    //                vm.addSortParentId = "";
    //                getSortList();
    //                layer.alert('保存成功');
    //            } else {
    //                layer.alert("保存失败");
    //            }
    //        } catch (error) {
    //            layer.alert("保存失败");
    //        }
    //    }
    //});

    /**
     * 弹出新增框
     * @param type 0：新增同级，1：添加子节点
     */
    vm.onAddNodeModalClick = function (type) {
        $('#addSortImage').val('');
        $('#addSortImageId').hide();
        if (type === 0) {
            vm.addNodeTitleTip = "新增同级";
        } else {
            vm.addNodeTitleTip = "添加子节点";
            vm.addSortParentId = vm.currentClickNodeId;
            vm.addSortGrade++;
        }
        vm.sortModalShow('sort-add-chidnode-modal');
    };

    /**
     * 新增分类信息
     */
    vm.onAddSortClick = function () {
        if (!vm.caNumberAddModel) {
            layer.alert('请输入编号');
            return;
        }
        if (vm.caNumberAddModel && !(/\d/.exec(vm.caNumberAddModel))) {
            layer.alert('编号须为数字');
            return;
        }
        if (!vm.caNameAddModel) {
            layer.alert('请输入名称');
            return;
        }
        if (!vm.caOrderAddModel) {
            layer.alert('请输入排序号');
            return;
        }
        if (vm.caOrderAddModel && !(/\d/.exec(vm.caOrderAddModel))) {
            layer.alert('排序号须为数字');
            return;
        }
        //$("#addSortFormId").submit();
        $("#addSortFormId").ajaxSubmit({
        	xhrFields: {
	                withCredentials: true
	        },
        	success:function (resp) {
            if (resp.code == '211') {
                vm.sortModalHide('sort-add-chidnode-modal');
                vm.caNumberAddModel = "";
                vm.caNameAddModel = "";
                vm.caOrderAddModel = "";
                vm.caRecordAddModel = "";
                //vm.currentClickNodeId = "";
                //vm.addSortGrade = "";
                //vm.addSortParentId = "";
                getSortList();
                layer.msg('添加成功');
            }else{
                layer.alert(resp.msg);
            }
        }});
        return false;
    };

    /**
     * 保存编辑提交返回处理，放在点击事件外面避免多次绑定load事件，重复调用
     */
    //$('#addSortFrameId').on('load', function () {
    //    //获取到iFrame里面的html元素
    //    var frameValue = $('#addSortFrameId').contents();
    //    var str = $(frameValue).find('pre').html() || $(frameValue).find('body').html();
    //    if (str) {
    //        try {
    //            var resp = $.parseJSON(str);
    //            if (resp.code == '211') {
    //                vm.sortModalHide('sort-add-chidnode-modal');
    //                vm.currentClickNodeId = "";
    //                vm.addSortGrade = "";
    //                vm.addSortParentId = "";
    //                getSortList();
    //                layer.alert('添加成功');
    //            } else {
    //                layer.alert("添加失败");
    //            }
    //        } catch (error) {
    //            layer.alert("添加失败");
    //        }
    //    }
    //});

    /**
     * 删除分类
     */
    vm.onDeleteSortClick = function () {
        req.post('classification/delCpCategory.do', {
            id: vm.currentClickNodeId,
            parentId: vm.addSortParentId,
            langType: window.localStorage.lang
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.sortModalHide('sort-del-modal');
                //vm.currentClickNodeId = "";
                //vm.addSortGrade = "";
                //vm.addSortParentId = "";
                getSortList();
                layer.alert('删除成功');
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    };

    /**
     * 传入拖拽发生id和被拖拽id，如果相同，则本体就是按钮和拖拽对象
     * @param dragDiv 拖拽发生id
     * @param tagDiv  被拖拽id
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
    }
});