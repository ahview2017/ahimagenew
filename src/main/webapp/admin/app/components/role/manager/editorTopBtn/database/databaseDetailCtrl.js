/**
 * Created by Sun on 2016/12/14.
 */
adminModule.controller('mDatabaseDetailCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, $stateParams, allModalMove, modalOperate, $window) {
    var vm = this;

    //移动模态框
    vm.moveModal = function (dragDiv, tagDiv) {
        allModalMove.modalMove(dragDiv, tagDiv);
    };

    /**
     * 栏目编辑选择稿件
     * topicId 专题id
     * lanmuId 栏目id //返回需要
     * columnId 子栏目/栏目id
     * columnTemplate 模板id
     * num 稿件位置
     * oldId 原稿件id ，在更多稿件替换的时候需要
     * isHadOut 0:表示未撤稿件  1:表示已撤稿件
     * */
        //vm.columnFlag = $cookies.get('columnFlag');//单选稿件开关
        //vm.columnMoreFlag = $cookies.get('columnMoreFlag');//多选稿件开关
    vm.topicId = $stateParams.topicId;
    vm.lanmuId = $stateParams.lanmuId;
    vm.columnId = $stateParams.columnId;
    vm.columnTemplate = $stateParams.columnTemplate;
    vm.oldId = $stateParams.oldId;
    vm.num = $stateParams.num;
    vm.showIsHadOut = $stateParams.isHadOut;
    vm.categoryId = null;
    //从路由获取传过来的稿件ID
    vm.dataBankId = $stateParams.id;
    vm.langType = window.localStorage.lang;
    // 获取用户名
    vm.uName = $cookies.get('admin_uname');
    vm.signlanmu = [];
    vm.children = [];
    vm.column = [];
    //模态框遮罩层显示
    function maskShow() {
        $rootScope.layerIfShow = true;
    }

    //模态框遮罩层隐藏
    function maskHide() {
        $rootScope.layerIfShow = false;
    }

    //专题模态框显示
    vm.projectModalShow = function (modalId,categoryId) {
        $('#' + modalId).show();
        vm.categoryId = categoryId;
        maskShow();
    };
    //专题模态框隐藏
    vm.projectModalHide = function (modalId) {
        $('#' + modalId).hide();
        maskHide();
    };
    //下载模态显示
    vm.ModalShow = function (modalId) {
        modalOperate.modalShow(modalId);
    };
    //下载模态框隐藏
    vm.ModalHide = function (modalId) {
        modalOperate.modalHide(modalId);
    };
    //全选
    $(".all").click(function(){
        var xz = $(this).prop("checked");
        $(".check").prop("checked", xz);
    });
    //获取作者信息
    vm.authorinfor = function(authorId) {
        req.post('enGetGroupPicCtro/selCpUserBasicInfo.do', {
            id:authorId
        }).success(
            function(resp) {
                if(resp.code == '211') {
                    $("#authorinformation-modal").show();
                    vm.author = resp.data;
                } else if(resp.msg != '未登录') {
                    layer.alert(resp.msg);
                }
            });
    }
    /**
     * 获取资料库详情
     */
    function getDataBankDetailData(groupId) {
        req.post('groupPicCtro/getGroupPics.do', {
            groupId: groupId
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.dataBankObj = resp.data;
                var cateArray = vm.dataBankObj['cates'];
                vm.dataBankType = "";
                vm.dataBankTypeArr = [];
                if(resp.msg){
                	vm.msSensitiveWord=resp.msg;
                	layer.msg('此稿件存在敏感词：'+resp.msg);
                }
                for (var i = 0; i < cateArray.length; i++) {
                    var cateObj = cateArray[i];
                    vm.dataBankTypeArr.push(cateObj['categoryName']);
                    vm.dataBankType = vm.dataBankTypeArr.join();
                }
                var prosArray = vm.dataBankObj['pros'];
                vm.prosNames = "";
                for (var j = 0; j < prosArray.length; j++) {
                    vm.prosNames += prosArray[j]['operateUsername'] + " ";
                }
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //开始编辑：编辑前锁定稿件
    vm.editSendManuscript = function () {
        req_beginEditSendManuscript();
    };
    //开始编辑：编辑前锁定稿件请求
    function req_beginEditSendManuscript() {
        req.post('groupPicCtro/editBegin.do', {
            groupId: vm.dataBankId
        }).success(function (resp) {
            if (resp.code == '211') {
                console.log('success');
                $state.go('role.manager.databaseEdit', {
                    id: vm.dataBankId
                });
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    //下载图片
    vm.downLoadPic = function (type){
        var id_array=new Array();
            $('input[name="check"]:checked').each(function(){
                id_array.push($(this).val());//向数组中添加元素
            });
        var picIds=id_array.join(',');//将数组元素连接起来以构建一个字符串
        if(id_array.length==0){
            layer.alert("请选择图片");
            return;
        }
        document.location = "/photo/enGroupPicDown/downSinglePic.do?picIds=" + picIds+"&type="+type+"&langType="+vm.langType;
        modalOperate.modalHide('detail-type-modal');
    }
    /**
     * 获取已签栏目数据
     */
    function getHadSignData(groupId) {
        req.post("groupPicCtro/getHasSignCate.do", {
            groupId: groupId,
            langType:vm.langType
        }).success(function (resp) {
            if (resp.code == '211' && resp.data != []) {
                vm.hadSignArray = resp.data;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    /**
     * 撤稿
     */
    vm.onRetractionClick = function () {
        req.post("groupPicCtro/downGroupPic.do", {
            groupId: vm.dataBankId,
            categoryId:vm.categoryId,
            content: vm.retractionContent,
            langType:vm.langType
        }).success(function (resp) {
            if (resp.code == '211' && resp.data != []) {
                vm.projectModalHide('retractionModalId');
                layer.alert("撤稿成功，该签发位为空，请及时签发新的稿件哦");
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    };
    
    
    
    /**
     * 一键撤稿  add by xiayunan@20171009
     */
    vm.onAllRetractionClick = function () {
        req.post("groupPicCtro/downAllGroupPic.do", {
            groupId: vm.dataBankId,
            content: vm.retractionContent,
            langType:vm.langType
        }).success(function (resp) {
            if (resp.code == '211' && resp.data != []) {
                vm.projectModalHide('retractionModalId');
                layer.alert("一键撤稿成功，该签发位为空，请及时签发新的稿件哦",function(){
                	window.location.reload();
                });
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    };

    //存储签发参数的数组
    vm.signReqParamData = [];
    /**
     * 签发处理
     */
    var IDMark_A = "_a";
    var setting = {
        view: {
            addDiyDom: addDiyDom
        },
        check: {
            enable: true,
            chkboxType: {
                "Y": "ps",
                "N": "ps"
            }
        },
        callback: {
            //onCheck: treeCheckEvent,
        	//add by xiayunan@2017-09-24 
        	beforeCheck: zTreeBeforeCheck
        }
    };
    column();
    // 设置
    var settings = {
        view: {
            addDiyDom: addDiyDoms
        },
        check: {
            enable: true,
            chkboxType: {
                "Y": "ps",
                "N": "ps"
            }
        },
        callback: {
            // onClick: treeClick,
            // onCheck: treeCheckEvents
        	//add by xiayunan@2017-09-24 
        	beforeCheck: zTreeBeforeCheck
        }
    };

    /**
     * 树复选框选中回调事件
     */
    function treeCheckEvent(event, treeId, treeNode, clickFlag) {
        console.log(treeNode);
    }
    function treeCheckEvents(event, treeId, treeNode, clickFlag) {
        console.log(treeNode.id);
    }
    //add by xiayunan@2017-09-24 
    function zTreeBeforeCheck(treeId, treeNode) {
        return !treeNode.isParent;//当是父节点 返回false 不让选取
    }

    /**
     * 获取签发的参数
     */
    function getSignParams() {
    	var treeObj = $.fn.zTree.getZTreeObj("tree");
        var nodes = treeObj.getCheckedNodes(true);
        for (var i = 0; i < nodes.length; i++) {
            var position = $("#diyBtn_" + nodes[i].id).prop('value');
            if (nodes[i].type == '0') {
                vm.signReqParamData.push({
                    type: '0',
                    signId: nodes[i].id,
                    position: position
                });
            }
            if (nodes[i].type == '1') {
                vm.signReqParamData.push({
                    type: '1',
                    topicId: 1
                });
            }
        }
    }
    req.post('enColumn/showUpColumnAll.do', {
        "langType":  vm.langType
    }).success(
        function(resp) {
            if(resp.code == '211') {
                $scope.zNodes = resp.data;
               /* vm.column = resp.data;
                console.log(resp.data);
                for(var i = 0; i < vm.column.length; i++) {
                    for(var s = 0; s < vm.column[i].children.length; s++) {
                        vm.column.push(resp.data[i].children[s]);
                    }
                }
                console.log("栏目"+vm.column);*/
            } else if(resp.msg != '未登录') {
                layer.alert(resp.msg);
            }
        });
    function column(){
        req.post('enColumn/showUpColumnAll.do', {
            "langType":  vm.langType
        }).success(
            function(resp) {
                if(resp.code == '211') {
                    vm.column = resp.data;
                    console.log(resp.data);
                    for(var i = 0; i < resp.data.length; i++) {
                        for(var s = 0; s < resp.data[i].children.length; s++) {
                            vm.column.push(resp.data[i].children[s]);
                        }
                    }
                    console.log("栏目"+vm.column);
                } else if(resp.msg != '未登录') {
                    layer.alert(resp.msg);
                }
            });
    }
    //获取专题和子栏目
    var data_a = [];
    req.post('enSpec/showTopic.do', {
        "langType":  vm.langType
    }).success(
        function(resp) {
            if(resp.code == '211') {
                $scope.datas = resp.data;
                for(var i = 0; i < resp.data.length; i++) {
                    if(resp.data[i].type == 0 && resp.data[i].display == 1) {
                        data_a.push(resp.data[i]);
                    }
                }
                $scope.data = data_a;
            } else if(resp.msg != '未登录') {
                layer.alert(resp.msg);
            }
        });
    //用于在节点上固定显示用户自定义控件
    function addDiyDom(treeId, treeNode) {
        if(treeNode.parentNode && treeNode.parentNode.id != 2) return;
        var aObj = $("#" + treeNode.tId + IDMark_A);

        for(var i = 0; i < $scope.zNodes.length; i++) {
            var children = $scope.zNodes[i].children;
            if($scope.zNodes[i].signPosition > 0) {

                if(treeNode.id == $scope.zNodes[i].id) {
                    var aLinkStr = "<a id='diyBtn1_" + treeNode.id + "' style='margin:0 0 0 5px;'>浏览位置</a>";
                    var editStr = "<span id='diyBtn_space_" +
                        $scope.zNodes[i].id +
                        "' >&nbsp;</span><select class='selDemo' id='diyBtn_" +
                        treeNode.id +
                        "'></select>";
                    aObj.after(aLinkStr);
                    aObj.after(editStr);

                    for(var j = 0; j <= treeNode.signPosition; j++) {
                        if(j == 0) {
                            $("#diyBtn_" + treeNode.id).append("<option value=" + j + ">不显示</option>");
                        } else {
                            $("#diyBtn_" + treeNode.id).append("<option value=" + j + ">第" + j + "位</option>");
                        }
                    }
                }
            }
            for(var a = 0; a < children.length; a++) {
                var childrens = children[a].children;
                if(children[a].signPosition > 0) {
                    if(treeNode.id == children[a].id) {
                        var aLinkStr = "<a id='diyBtn1_" + treeNode.id + "' style='margin:0 0 0 5px;'>浏览位置</a>";
                        var editStr = "<span id='diyBtn_space_" +
                            children[a].id +
                            "' >&nbsp;</span><select class='selDemo' id='diyBtn_" +
                            treeNode.id +
                            "'></select>";
                        aObj.after(aLinkStr);
                        aObj.after(editStr);
                        for(var s = 0; s <= treeNode.signPosition; s++) {
                            if(s == 0) {
                                $("#diyBtn_" + treeNode.id).append("<option value=" + s + ">不显示</option>");
                            } else {
                                $("#diyBtn_" + treeNode.id).append("<option value=" + s + ">第" + s + "位</option>");
                            }
                        }
                    }
                }
                for(var x = 0; x < childrens.length; x++) {
                    if(childrens[x].signPosition > 0) {
                        if(treeNode.id == childrens[x].id) {
                            var aLinkStr = "<a id='diyBtn1_" + treeNode.id + "' style='margin:0 0 0 5px;'>浏览位置</a>";
                            var editStr = "<span id='diyBtn_space_" +
                                childrens[x].id +
                                "' >&nbsp;</span><select class='selDemo' id='diyBtn_" +
                                treeNode.id +
                                "'></select>";
                            aObj.after(aLinkStr);
                            aObj.after(editStr);
                            for(var n = 0; n <= childrens[x].signPosition; n++) {
                                if(n == 0) {
                                    $("#diyBtn_" + treeNode.id).append("<option value=" + n + ">不显示</option>");
                                } else {
                                    $("#diyBtn_" + treeNode.id).append("<option value=" + n + ">第" + n + "位</option>");
                                }
                            }
                        }
                    }

                }
            }

        }
        var btn = $("#diyBtn1_" + treeNode.id);

        if(btn) btn.bind("click", function() {
            modalOperate.modalShow('look-sign-pos-modal');
            lookSignedGroupPos(treeNode.id, treeNode.signPosition);
        });
    }
    // 用于在节点上固定显示用户自定义控件--专题
    function addDiyDoms(treeId, treeNode) {
        if(treeNode.parentNode && treeNode.parentNode.id != 2) return;
        var aObj = $("#" + treeNode.tId + IDMark_A);
        for(var i = 0; i < $scope.data.length; i++) {
            var children = $scope.data[i].children;
            for(var a = 0; a < children.length; a++) {
                var childrens = children[a].children;
                if(children[a].zhanshuCount > 0) {
                    if(treeNode.id == children[a].id) {
                        var aLinkStr = "<a id='diyBtn1_" + treeNode.id + "' style='margin:0 0 0 5px;'>浏览位置</a>";
                        var editStr = "<span id='diyBtn_space_" +
                            children[a].id +
                            "' >&nbsp;</span><select class='selDemo' id='diyBtn_" +
                            treeNode.id +
                            "'></select>";
                        aObj.after(aLinkStr);
                        aObj.after(editStr);
                        for(var s = 0; s <= treeNode.zhanshuCount; s++) {
                            if(s == 0) {
                                $("#diyBtn_" + treeNode.id).append("<option value=" + s + ">不显示</option>");
                            } else {
                                $("#diyBtn_" + treeNode.id).append("<option value=" + s + ">第" + s + "位</option>");
                            }
                        }
                    }
                }
            }

        }
        var btn = $("#diyBtn1_" + treeNode.id);

        if(btn) btn.bind("click", function() {
            modalOperate.modalShow('look-sign-pos-modals');
            lookSignedGroupPoss(treeNode.id);
        });

    }
    // 查看已签稿件位置
    function lookSignedGroupPos(sginId, limit) {
        if(vm.langType == 0) {
            req.post('groupPicCtro/getSginGroupsBySginId.do', {
                sginId: sginId,
                limit: limit
            }).success(function(resp) {
                if(resp.code == '211') {
                    vm.signedGroupList = resp.data;
                } else if(resp.msg != '未登录') {
                    layer.alert(resp.msg);
                }
            });
        } else {
            req.post('enSign/browsePosition.do', {
                columnId: sginId
            }).success(function(resp) {
                if(resp.code == '211') {
                    vm.signedGroupList = resp.data;
                } else if(resp.msg != '未登录') {
                    layer.alert(resp.msg);
                }
            });
        }
    }
    function lookSignedGroupPoss(sginId) {
        req.post('lanmu/lanMuPicDetail.do', {
            lanmuid: sginId
        }).success(function(resp) {
            if(resp.code == '211') {
                vm.lanmu_group = resp.data.cpLanmuPictures;
            } else if(resp.msg != '未登录') {
                layer.alert(resp.msg);
            }
        });
    }
    /**
     * 初始化签发树
     */
    function initSignTree() {
        $.fn.zTree.init($("#tree"), setting,$scope.zNodes);
        $.fn.zTree.init($("#treeDemo"), settings, $scope.data);
    }

    /**
     * 显示模态框
     */
    vm.onFillSignShowModalClick = function () {
        initSignTree();
        var treeShowObj = $.fn.zTree.getZTreeObj("tree", setting, $scope.zNodes);
        /*if (vm.hadSignArray != null && vm.hadSignArray.length > 0) {
            var treeNodeArray = treeShowObj.transformToArray(treeShowObj.getNodes());
            for (var s = 0; s < vm.hadSignArray.length; s++) {
                var hadSignItem = vm.hadSignArray[s];
                for (var t = 0; t < treeNodeArray.length; t++) {
                    var treeNodeItem = treeNodeArray[t];
                    if (treeNodeItem.id == hadSignItem.categoryId) {
                        treeShowObj.checkNode(treeNodeItem, true, true);
                        break;
                    }
            }
                //控制下postion为null的情况，也就是不显示在首页的情况
                if(hadSignItem.position != null){
                    $("#diyBtn_" + hadSignItem.categoryId).prop('value', hadSignItem.position);
                }else{
                    $("#diyBtn_" + hadSignItem.categoryId).prop('value','');
                }
            }
        }*/
        vm.projectModalShow('fillSignModalId',null);
    };

    /**
     * 点击补签
     */
    vm.onFillSignClick = function () {
        getSignParams();
        req_signManuscripts();
        if(vm.langType != 0) {
            var treeObj = $.fn.zTree.getZTreeObj("tree");
            var column = new Array(); //定义一数组存放栏目id
            var nodes = treeObj.getCheckedNodes(true); //获取选中的值
            for(var i = 0; i < nodes.length; i++) {
                column.push(nodes[i].id);
            }
            var signColumn = [] //定义一数组存放栏目id和显示的位置
            for(var i = 0; i < column.length; i++) {
                var options = $("#diyBtn_" + column[i] + " " + "option:selected"); //获取选中的项的value
                var signId = options.val();
                signColumn.push({
                    signId: signId,
                    columnId: column[i]
                });
            }
        }
        if($.fn.zTree.getZTreeObj("treeDemo").getCheckedNodes(true).length == 0&&$.fn.zTree.getZTreeObj("tree").getCheckedNodes(true).length == 0){
        	layer.alert('请至少选中一个签发类型');
			return;
        }
        req.post('groupPicCtro/signAgainGroupPic.do', {
            groupId: vm.dataBankId,
            cateData: angular.toJson(vm.signReqParamData),
            "signColumn": angular.toJson(signColumn),
            "langType": vm.langType,
            "userName": vm.uName
        }).success(function(resp) {
            if(resp.code == '211') {
                layer.msg('操作成功');
                getHadSignData(vm.dataBankId);
                vm.projectModalHide('fillSignModalId');
            }else if(resp.msg != '未登录') {
                layer.alert(resp.msg);
            }
        });
      /*  req.post('groupPicCtro/signAgainGroupPic.do', {
            groupId: vm.dataBankId,
            cateData: angular.toJson(vm.signReqParamData)
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.projectModalHide('fillSignModalId');
                vm.signReqParamData = [];
                getHadSignData(vm.dataBankId);
                layer.alert('操作成功');
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });*/
    };
    function req_signManuscripts() {
        if(vm.langType != 0) {
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            var lanmu = new Array(); //定义一数组存放栏目id
            var nodes = treeObj.getCheckedNodes(true); //获取选中的值
            for(var i = 0; i < nodes.length; i++) {
                lanmu.push(nodes[i].id);
            }
            console.log(lanmu);
            var signlanmu = [] //定义一数组存放栏目id和显示的位置
            for(var i = 0; i < lanmu.length; i++) {
                var options = $("#diyBtn_" + lanmu[i] + " " + "option:selected"); //获取选中的项的value
                var signId = options.val();
                vm.signlanmu.push({
                    position: signId,
                    lanmuId: lanmu[i]
                });
            }
            console.log(signlanmu);
        }
        for(var i = 0; i < vm.signlanmu.length; i++) {
            req.post('lanmu/addLanmuPic.do', {
                groupId: vm.dataBankId,
                potision: vm.signlanmu[i].position,
                lanmuId: vm.signlanmu[i].lanmuId,
                "langType": vm.langType
            }).success(function(resp) {
                if(resp.code == '211') {
                    console.log('------success-----');
                    layer.msg('操作成功');
                    getHadSignData(vm.dataBankId);
                    vm.projectModalHide('fillSignModalId');
                } else if(resp.msg != '未登录') {
                    layer.alert(resp.msg);
                }
            });
        }
    }
    /**
     * 点击上架
     */
    vm.onWorkUpClick = function () {
        req.post('groupPicCtro/upOnGroupPic.do', {
            groupId: vm.dataBankId,
            langType: window.localStorage.lang
        }).success(function (resp) {
            if (resp.code == '211') {
                layer.alert('上架操作成功');
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    };

    /**
     * 点击推送
     */
    vm.onWorkPullClick = function () {
        req.post('groupPicCtro/pushGroupPic.do', {
            groupId: vm.dataBankId,
            content: vm.workPullContent,
            langType: window.localStorage.lang
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.projectModalHide('workPullModalId');
                layer.msg('推送操作成功');
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    };

    // 请求
    //initSignTree();
    getDataBankDetailData(vm.dataBankId);
    getHadSignData(vm.dataBankId);

    //页面初始化
    function init(){
        initSignTree();
    }


    //强制解锁
    vm.unlockManuscript = function () {
        req_unlockManuscript();
    }
    //强制解锁请求
    function req_unlockManuscript() {
        req.post('groupPicCtro/unLockGroup.do', {
            groupId: vm.dataBankId,
        }).success(function (resp) {
            if (resp.code == '211') {
                layer.alert('操作成功');
                getDataBankDetailData(vm.dataBankId);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
});