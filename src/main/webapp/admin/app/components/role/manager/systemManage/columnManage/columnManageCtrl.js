/**
 * Created by fangfang on 2016/11/25.
 */ 
adminModule.controller('columnManageCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, Upload) {
		var vm = this;
	    $scope.langType=window.localStorage.lang;
	    function initSetting() {
	        vm.classifyIfShowFlag = true;
	        vm.detailSortIfShowFlag = true;
			vm.signPosition = 0;
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
	        req.post('enColumn/showUpColumnAll.do', {
				"langType":$scope.langType
	        }).success(function (resp) {
	            if (resp.code == '211') {
	            	console.log(resp.data);
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
	                            children: "children",
	                            name: "name"
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

	    //vm.currentClickNodeId = "";
	    //vm.addSortGrade = "";
	    //vm.addSortParentId = "";
	    $("#showEditSortInformId").hide();
	    function zTreeOnClick(treeNode) {
	        if (treeNode['type'] != 'click') {
	            $("#showEditSortInformId").show();
	            vm.currentClickNodeId = treeNode['id'];
	            vm.addSortGrade = treeNode['position'];
	            vm.addSortParentId = treeNode['pid'];
	            $("#editSortIdId").val(treeNode['id']);
	            $("#editSortNumId").val(treeNode['categoryNumber']);
	            vm.caNumberEditModel = treeNode['categoryNumber'];
	            $("#editSortNameId").val(treeNode['name']);
	            vm.caNameEditModel = treeNode['name'];
	            $("#editSortOrderId").val(treeNode['orderId']);
	            vm.caOrderEditModel = treeNode['orderId'];
	            $("#editSortTypeId").val(treeNode['state'] + "");
				vm.signPosition = treeNode['signPosition'];
				$("#editSignPosition").val(treeNode['signPosition']);
                /*点击子栏目，添加按钮隐藏*/
                /*if(vm.addSortParentId!=0){
                	$('.sort-addnode-btn').css('display','none');
                }else{
                	$('.sort-addnode-btn').css('display','inline-block');
                }*/

	        }
	    }        
        
	    /**
	     * 编辑分类信息
	     */
	    vm.onEditSortClick = function () {	        
	        if (!vm.caNameEditModel) {
	            layer.alert('请输入名称');
	            return;
	        }
	        if (!vm.caOrderEditModel) {
	            layer.alert('请输入排序号');
	            return;
	        }
	        //$("#editSortFormId").submit();
	        $("#editSortFormId").ajaxSubmit(function (resp) {
	            if (resp.code == '211') {
	                //vm.currentClickNodeId = "";
	                //vm.addSortGrade = "";
	                //vm.addSortParentId = "";
	                getSortList();
	                layer.msg('保存成功');
	            } else {
	                layer.alert("保存失败");
	            }
	        });
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
	        if (type === 0) {
	            vm.addNodeTitleTip = "新增父栏目";
	        } else {
	            vm.addNodeTitleTip = "添加子栏目";
	            vm.addSortParentId = vm.currentClickNodeId;
	            vm.addSortGrade++;
	        }
	        vm.sortModalShow('sort-add-chidnode-modal');
	    };

	    /**
	     * 新增分类信息
	     */
	    vm.onAddSortClick = function () {
	        if (!vm.caNameAddModel) {
	            layer.alert('请输入名称');
	            return;
	        }
	        if (!vm.caOrderAddModel) {
	            layer.alert('请输入排序号');
	            return;
	        }
	        //$("#addSortFormId").submit();
	        $("#addSortFormId").ajaxSubmit(function (resp) {
	            if (resp.code == '211') {
	                vm.sortModalHide('sort-add-chidnode-modal');
	                vm.caNameAddModel = "";
	                vm.caOrderAddModel = "";
	                //vm.currentClickNodeId = "";
	                //vm.addSortGrade = "";
	                //vm.addSortParentId = "";
	                getSortList();
	                layer.msg('添加成功');
	            }else if(resp.msg != '未登录'){
	                layer.alert(resp.msg);
	            }
	        });
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
	        req.post('enManagerColumn/deleteEnManagerColumn.do', {
	            id: vm.currentClickNodeId,
	            pid: vm.addSortParentId
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

        
/*    var vm = this;

    //模态框移动
    $(".modal-dialog").draggable({
        handle: ".modal-header"
    });

    //页面初始化相关配置
    function initSetting(){
        //存放在线用户数组
        vm.SpecialArray = [];
        //在线用户总条数
        vm.specialList_total = 0;
        //默认当前页1
        vm.pagination = {
            current: 1
        };
        //默认每页20条
        vm.selPageRows = '10';

        //操作类型
        vm.handleTypeObj = [
            {id: 0, name: '人工签发'},
            {id: 1, name: '外部链接'}
        ]
        //签发是否显示
        vm.signShowObj = [
            {id: 0, name: '否'},
            {id: 1, name: '是'}
        ]
        //网站是否显示
        vm.siteShowObj = [
            {id: 0, name: '否'},
            {id: 1, name: '是'}
        ]
        //默认搜索名称为空
        vm.specialSearchName = '';
    }

    //页面初始化
    function init(){
        initSetting();
        getSpecialTableData(vm.pagination.current,vm.specialSearchName);
    }
    init();

    *//**
     * 获取专题表格数据
     *//*
    function getSpecialTableData(page, searchname) {
        if (searchname != undefined && searchname != '') {
            var url = "topic/searchTopic.do";
        } else {
            url = "topic/showTopic.do";
        }
        req.post(url, {
            page: page,
            rows: vm.selPageRows,
            name: searchname
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.SpecialArray = resp.data;
                vm.totalPages = resp.page;
                vm.specialList_total = resp.other;
            } else {
                console.log(resp.msg);
            }
        });
    }

    //页数变化
    vm.pageChanged = function (pageNumber) {
        getSpecialTableData(pageNumber,vm.specialSearchName);
    };


    *//**
     * 搜索
     * *//*
    vm.searchSpecial = function () {
        getSpecialTableData(1, vm.specialSearchName);
    };

    *//**
     * 回车搜索
     *//*
    vm.onEnterSearchClick = function (e) {
        var keyCode = window.event ? e.keyCode : e.which;
        if (keyCode == 13) {
            vm.searchSpecial();
        }
    };

    *//**
     * 处理全选
     *//*
    vm.checkBoxArray = [];
    vm.isHadAllCheck = false;
    vm.onCheckAllClick = function () {
        if (vm.isHadAllCheck) {
            vm.checkBoxArray = vm.SpecialArray.map(function (item) {
                return item.id
            });
        } else {
            vm.checkBoxArray = vm.SpecialArray.map(function (item) {
                return false
            });
        }
    };
    *//**
     * 监听每一个选项的改变
     *//*
    $scope.$watchCollection('projectManage.checkBoxArray', function (newC) {
        if (newC != undefined) {
            if (newC.every(function (item) {
                    return item != false;
                })) {
                vm.isHadAllCheck = true;
            } else {
                vm.isHadAllCheck = false;
            }
        }
    });

    *//**
     * 点击删除
     *//*
    vm.deleteDataParamsId = "";
    vm.onShowDeleteModelClick = function () {
        var paramsId = "";
        for (var c = 0; c < vm.checkBoxArray.length; c++) {
            var checkBoxItem = vm.checkBoxArray[c];
            if (checkBoxItem != false) {
                paramsId += checkBoxItem + ",";
            }
        }
        if (paramsId != "") {
            vm.deleteDataParamsId = paramsId.substr(0, paramsId.length - 1);
            vm.delSpecial();
        } else {
            layer.alert("请选择要删除的专题");
        }
    }

    *//**
     * 删除专题
     *//*
    vm.delSpecial = function (id) {
        vm.deleteDataParamsId = id ? id : vm.deleteDataParamsId;
        layer.confirm('您确定要删除这些专题吗？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            req.post('topic/deleteTopic.do', {
                id: vm.deleteDataParamsId
            }).success(function (resp) {
                if (resp.code == '211') {
                    vm.isHadAllCheck = false;
                    getSpecialTableData(vm.pagination.current,vm.specialSearchName);
                    layer.msg(resp.msg ? resp.msg : '删除成功', {time: 2000});
                } else {
                    console.log(resp.msg);
                }
            });
        }, function () {

        });

    };

    //添加专题
    //实例化一个编辑器
    var ueditorParams = {
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
    };
    var ue = new UE.ui.Editor(ueditorParams);
    ue.render('addWebEdit');

    vm.specialAddModal = function () {
        vm.specialName = vm.specialLink = vm.superText = vm.file = '';
        $('#specialAddModal').modal('show');
        vm.handleType = vm.signShow = vm.siteShow = 0;
    };

    *//**
     * 添加专题
     *//*
    vm.addSpecial = function () {
        vm.uploading();
    };
    // upload on file select or drop
    vm.uploading = function () {
        vm.superText = ue.getContent();
        if (!vm.specialName) {
            layer.alert('请输入专题名称');
            return;
        }
        if(vm.handleType == 1){
            if (!vm.specialLink) {
                layer.alert('请输入专题链接');
                return;
            }
        }
        if (!vm.superText) {
            layer.alert('请输入专题内容');
            return;
        }
        if (!vm.file) {
            layer.alert('请上传banner');
            return;
        }
        Upload.upload({
            url: '/photo/topic/addTopic.do',
            data: {
                name: vm.specialName,
                type: vm.handleType,
                url: vm.specialLink,
                qianfa: vm.signShow,
                display: vm.siteShow,
                remarks: vm.superText,
                wmFile: vm.file
            }
        }).then(function (resp) {
            if (resp.data.code == '211') {
                getSpecialTableData(vm.pagination.current,vm.specialSearchName);
                vm.specialName = vm.specialLink = vm.superText = vm.file = '';
                ue.execCommand('cleardoc');
                $('#specialAddModal').modal('hide');
                layer.msg(resp.data.msg ? resp.data.msg : '专题保存成功', {time: 2000});
            } else {
                layer.alert(resp.data.msg ? resp.data.msg : '专题保存失败', {time: 2000});
            }
        }, function (resp) {
            // console.log('Error status: ' + resp.status);
        }, function (evt) {
            // var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
        });
    };
    *//**
     * 展示编辑框
     *//*

    vm.updateEditId = "";
    vm.updateSpecial = function () {
        var editId = "";
        for (var c = 0; c < vm.checkBoxArray.length; c++) {
            var checkBoxItem = vm.checkBoxArray[c];
            if (checkBoxItem != false) {
                editId += checkBoxItem + ",";
            }
        }
        if (editId != "") {
            var editIdArray = editId.split(',');
            if (editIdArray.length > 2) {
                layer.alert("只能选择一个专题进行编辑");
            } else {
                vm.updateEditId = editIdArray[0];
                vm.specialUpdateModal();
            }
        } else {
            layer.alert("请选择要编辑的专题");
        }
    };

    //实例化一个编辑器
    var ue1 = new UE.ui.Editor(ueditorParams);
    ue1.render('updateWebEdit');
    //修改专题
    vm.specialUpdateModal = function (id) {
        vm.updateEditId = id ? id : vm.updateEditId;
        req.post('topic/showToEditTopic.do', {
            id: vm.updateEditId
        }).success(function (resp) {
            if (resp.code == '211') {
                $('#specialUpdateModal').modal('show');
                ue1.ready(function () {//编辑器初始化完成再赋值
                    ue1.setContent(resp.data.remarks);  //赋值给UEditor
                });
                vm.signShow1 = parseInt(resp.data.qianfa);
                vm.specialName1 = resp.data.name;
                vm.handleType1 = parseInt(resp.data.type);
                vm.specialLink1 = resp.data.url;
                vm.siteShow1 = parseInt(resp.data.display);
                vm.file2 = resp.data.emage;
            }
        });
    }
    *//**
     * 编辑专题
     *//*
    vm.updateSpecialSave = function () {
        vm.superText1 = ue1.getContent();
        if (!vm.specialName1) {
            layer.alert('请输入专题名称');
            return;
        }
        if (vm.handleType1 == 1){
            if (!vm.specialLink1) {
                layer.alert('请输入专题链接');
                return;
            }
        }
        if (!vm.superText1) {
            layer.alert('请输入专题内容');
            return;
        }
        Upload.upload({
            url: '/photo/topic/editTopic.do',
            data: {
                id: vm.updateEditId,
                name: vm.specialName1,
                type: vm.signShow1,
                url: vm.specialLink1,
                qianfa: vm.handleType1,
                display: vm.siteShow1,
                remarks: vm.superText1,
                wmFile: vm.file2
            }

        }).then(function (resp) {
            if (resp.data.code == '211') {
                getSpecialTableData(vm.pagination.current,vm.specialSearchName);
                vm.specialName1 = vm.specialLink1 = vm.superText1 = vm.file2 = '';
                ue1.execCommand('cleardoc');
                $('#specialUpdateModal').modal('hide');
                layer.msg(resp.data.msg ? resp.data.msg : '专题修改成功', {time: 2000});
            } else {
                layer.alert(resp.data.msg ? resp.data.msg : '专题修改失败');
            }
        }, function (resp) {
            // console.log('Error status: ' + resp.status);
        }, function (evt) {
            // var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);

        });
    };
    *//**
     * 点击专题名或者专题图片进行前台预览
     * type 操作类型
     * _url 外部链接
     * url 人工签发专题ip地址
     * id 专题id
     * *//*
    vm.advanceView = function (type, _url, url, id) {
        if(type == 0){
            window.open( url + '/#/special/' + id);
        }else if(type == 1){
            if(!!_url){
                window.open(_url);
            }else{
                layer.alert('该专题的外部链接为无效地址');
            }
        }
    }

    */


});