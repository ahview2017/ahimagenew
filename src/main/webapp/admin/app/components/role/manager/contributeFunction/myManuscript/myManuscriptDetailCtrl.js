/**
 * Created by Sun on 2016/12/13.
 */
adminModule.controller('myManuscriptDetailCtrl', function ($scope, $cookies, req, md5, $state, $window, $rootScope, $stateParams, modalOperate, getMyDuty) {
    var vm = this;

    //获取用户名
    vm.uName = $cookies.get('admin_uname');


    //从cookie里取得角色id
    vm.adminRoleId = $cookies.get('admin_roleId');

    //获取从路由传过来的稿件id
    vm.groupId = $stateParams.id;

    //获取从路由传过来的稿件类型
    vm.gType = $stateParams.gType;

    //签发tree
    vm.tree = [
        {
            "id": "1",
            "pid": "0",
            "name": "家用电器",
            "children": [
                {
                    "id": "4",
                    "pid": "1",
                    "name": "大家电",
                    "children": [
                        {
                            "id": "7",
                            "pid": "4",
                            "name": "空调",
                            "children": [
                                {
                                    "id": "15",
                                    "pid": "7",
                                    "name": "海尔空调"
                                },
                                {
                                    "id": "16",
                                    "pid": "7",
                                    "name": "美的空调"
                                }
                            ]
                        },
                        {
                            "id": "8",
                            "pid": "4",
                            "name": "冰箱"
                        },
                        {
                            "id": "9",
                            "pid": "4",
                            "name": "洗衣机"
                        },
                        {
                            "id": "10",
                            "pid": "4",
                            "name": "热水器"
                        }
                    ]
                },
                {
                    "id": "5",
                    "pid": "1",
                    "name": "生活电器",
                    "children": [
                        {
                            "id": "19",
                            "pid": "5",
                            "name": "加湿器"
                        },
                        {
                            "id": "20",
                            "pid": "5",
                            "name": "电熨斗"
                        }
                    ]
                }
            ]
        },
        {
            "id": "2",
            "pid": "0",
            "name": "服饰",
            "children": [
                {
                    "id": "13",
                    "pid": "2",
                    "name": "男装"
                },
                {
                    "id": "14",
                    "pid": "2",
                    "name": "女装"
                }
            ]
        },
        {
            "id": "3",
            "pid": "0",
            "name": "化妆",
            "children": [
                {
                    "id": "11",
                    "pid": "3",
                    "name": "面部护理"
                },
                {
                    "id": "12",
                    "pid": "3",
                    "name": "口腔护理"
                }
            ]
        }
    ];


    //页面初始化相关配置
    function initSetting() {
        //点击管理详情div是否展示的标识
        vm.mangeOperateFlag = false;

        //存储签发参数的数组
        vm.signReqParamData = [];
    }

    //页面初始化
    function init() {
        initSetting();
        getManuscriptDetails();
        initSignTree();
        //从service里取得我的值班级别的数据，实现数据持久化
        getMyDuty.req_getMyDuty(function(type){
            vm.mydutyType = type;
            console.log(typeof vm.mydutyType);
        })
    }

    init();


    var IDMark_A = "_a";

    var setting = {
        view: {
            addDiyDom: addDiyDom
        },
        check: {
            enable: true         //设置是否显示checkbox复选框
        },
        callback: {
            //onClick: treeClick,
            onCheck: treeCheckEvent
        }
    };
    //树复选框选中回调事件
    function treeCheckEvent(event, treeId, treeNode, clickFlag){
        console.log(treeNode);
    }
    //获取签发的参数
    function getSignParams(){
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = treeObj.getCheckedNodes(true);
        for(var i = 0; i < nodes.length; i++){
            var position = $("#diyBtn_" + nodes[i].id).prop('value');
            if(nodes[i].type == '0'){
                vm.signReqParamData.push({
                    type: '0',
                    signId: nodes[i].signId,
                    position: position
                });
            }
            if(nodes[i].type == '1'){
                //todo 专题暂时这样
                vm.signReqParamData.push({
                    type: '1',
                    topicId: 1
                });
            }

        }
        console.log(vm.signReqParamData);

    }
    //自定义签发的数据
    var zNodes = [
        {
            id: 1, name: "网站", nocheck:true, open: true,
            children: [
                {
                    id: 11, name: "中国新闻社图片网",nocheck:true,open: true,
                    children: [
                        {id: 1, name: "资料图片", signId: '1', type:'0'},
                        {id: 2, name: "最新发布", signId: '2', type:'0'},
                        {id: 3, name: "今日头条", signId: '3', type:'0'},
                        {id: 4, name: "每日推荐", signId: '4', type:'0'},
                        {id: 5, name: "一周最佳采用", signId: '5', type:'0'},
                        {id: 6, name: "娱乐风尚", signId: '6', type:'0'},
                        {id: 7, name: "财富经济", signId: '7', type:'0'},
                        {id: 8, name: "台湾视角", signId: '8', type:'0'},
                        {id: 9, name: "国际风采", signId: '9', type:'0'},
                        {id: 10, name: "限价图片", signId: '10', type:'0'},
                        {id: 11, name: "漫画图表", signId: '11', type:'0'},
                        {id: 12, name: "两会", signId: '12', type:'0'}
                    ]
                },
            ]
        },
        {
            id: 2, name: "专题",nocheck:true, open: true,
            children: [
                {id: 25, name: "专题",type:'1'}
            ]
        },
    ];



    //用于在节点上固定显示用户自定义控件
    function addDiyDom(treeId, treeNode) {
        if (treeNode.parentNode && treeNode.parentNode.id != 2) return;
        var aObj = $("#" + treeNode.tId + IDMark_A);
        if(treeNode.id == 3) {
            if ($("#diyBtn_" + treeNode.id).length > 0) return;
            var aLinkStr = "<a id='diyBtn1_" + treeNode.id + "' onclick='alert(1);return false;' style='margin:0 0 0 5px;'>浏览位置</a>";
            var editStr = "<span id='diyBtn_space_" + treeNode.id + "' >&nbsp;</span><select class='selDemo ' id='diyBtn_" + treeNode.id + "'><option value=''>不显示在首页</option><option value=1>首页第1位</option><option value=2>首页第2位</option><option value=3>首页第3位</option><option value=4>首页第4位</option><option value=5>首页第5位</option></select>";
            aObj.after(aLinkStr);
            aObj.after(editStr);
        }else if(treeNode.id == 4){
            if ($("#diyBtn_" + treeNode.id).length > 0) return;
            var aLinkStr = "<a id='diyBtn1_" + treeNode.id + "' onclick='alert(1);return false;' style='margin:0 0 0 5px;'>浏览位置</a>";
            var editStr = "<span id='diyBtn_space_" + treeNode.id + "' >&nbsp;</span><select class='selDemo ' id='diyBtn_" + treeNode.id + "'><option  value=''>不显示在首页</option><option value=1>首页第1位</option><option value=2>首页第2位</option><option value=3>首页第3位</option><option value=4>首页第4位</option><option value=5>首页第5位</option><option value=6>首页第6位</option><option value=7>首页第7位</option><option value=8>首页第8位</option><option value=9>首页第9位</option><option value=10>首页第10位</option></select>";
            aObj.after(aLinkStr);
            aObj.after(editStr);
        }else if(treeNode.id == 5){
            if ($("#diyBtn_" + treeNode.id).length > 0) return;
            var aLinkStr = "<a id='diyBtn1_" + treeNode.id + "' onclick='alert(1);return false;' style='margin:0 0 0 5px;'>浏览位置</a>";
            var editStr = "<span id='diyBtn_space_" + treeNode.id + "' >&nbsp;</span><select class='selDemo ' id='diyBtn_" + treeNode.id + "'><option  value=''>不显示在首页</option><option value=1>首页第1位</option><option value=2>首页第2位</option><option value=3>首页第3位</option></select>";
            aObj.after(aLinkStr);
            aObj.after(editStr);
        }
        else if(treeNode.id == 6){
            if ($("#diyBtn_" + treeNode.id).length > 0) return;
            var aLinkStr = "<a id='diyBtn1_" + treeNode.id + "' onclick='alert(1);return false;' style='margin:0 0 0 5px;'>浏览位置</a>";
            var editStr = "<span id='diyBtn_space_" + treeNode.id + "' >&nbsp;</span><select class='selDemo ' id='diyBtn_" + treeNode.id + "'><option  value=''>不显示在首页</option><option value=1>首页第1位</option><option value=2>首页第2位</option></select>";
            aObj.after(aLinkStr);
            aObj.after(editStr);
        }
        else if(treeNode.id == 7){
            if ($("#diyBtn_" + treeNode.id).length > 0) return;
            var aLinkStr = "<a id='diyBtn1_" + treeNode.id + "' onclick='alert(1);return false;' style='margin:0 0 0 5px;'>浏览位置</a>";
            var editStr = "<span id='diyBtn_space_" + treeNode.id + "' >&nbsp;</span><select class='selDemo ' id='diyBtn_" + treeNode.id + "'><option  value=''>不显示在首页</option><option value=1>首页第1位</option><option value=2>首页第2位</option></select>";
            aObj.after(aLinkStr);
            aObj.after(editStr);
        }
        else if(treeNode.id == 8){
            if ($("#diyBtn_" + treeNode.id).length > 0) return;
            var aLinkStr = "<a id='diyBtn1_" + treeNode.id + "' onclick='alert(1);return false;' style='margin:0 0 0 5px;'>浏览位置</a>";
            var editStr = "<span id='diyBtn_space_" + treeNode.id + "' >&nbsp;</span><select class='selDemo ' id='diyBtn_" + treeNode.id + "'><option  value=''>不显示在首页</option><option value=1>首页第1位</option><option value=2>首页第2位</option></select>";
            aObj.after(aLinkStr);
            aObj.after(editStr);
        }else if(treeNode.id == 9){
            if ($("#diyBtn_" + treeNode.id).length > 0) return;
            var aLinkStr = "<a id='diyBtn1_" + treeNode.id + "' onclick='alert(1);return false;' style='margin:0 0 0 5px;'>浏览位置</a>";
            var editStr = "<span id='diyBtn_space_" + treeNode.id + "' >&nbsp;</span><select class='selDemo ' id='diyBtn_" + treeNode.id + "'><option  value=''>不显示在首页</option><option value=1>首页第1位</option><option value=2>首页第2位</option></select>";
            aObj.after(aLinkStr);
            aObj.after(editStr);
        }else if(treeNode.id == 10){
            if ($("#diyBtn_" + treeNode.id).length > 0) return;
            var aLinkStr = "<a id='diyBtn1_" + treeNode.id + "' onclick='alert(1);return false;' style='margin:0 0 0 5px;'>浏览位置</a>";
            var editStr = "<span id='diyBtn_space_" + treeNode.id + "' >&nbsp;</span><select class='selDemo ' id='diyBtn_" + treeNode.id + "'><option  value=''>不显示在首页</option><option value=1>首页第1位</option><option value=2>首页第2位</option></select>";
            aObj.after(aLinkStr);
            aObj.after(editStr);
        }else if(treeNode.id == 11){
            if ($("#diyBtn_" + treeNode.id).length > 0) return;
            var aLinkStr = "<a id='diyBtn1_" + treeNode.id + "' onclick='alert(1);return false;' style='margin:0 0 0 5px;'>浏览位置</a>";
            var editStr = "<span id='diyBtn_space_" + treeNode.id + "' >&nbsp;</span><select class='selDemo ' id='diyBtn_" + treeNode.id + "'><option  value=''>不显示在首页</option><option value=1>首页第1位</option><option value=2>首页第2位</option></select>";
            aObj.after(aLinkStr);
            aObj.after(editStr);
        }else if(treeNode.id == 12){
            if ($("#diyBtn_" + treeNode.id).length > 0) return;
            var aLinkStr = "<a id='diyBtn1_" + treeNode.id + "' onclick='alert(1);return false;' style='margin:0 0 0 5px;'>浏览位置</a>";
            var editStr = "<span id='diyBtn_space_" + treeNode.id + "' >&nbsp;</span><select class='selDemo ' id='diyBtn_" + treeNode.id + "'><option  value=''>不显示在首页</option></select>";
            aObj.after(aLinkStr);
            aObj.after(editStr);
        }
    }
    //初始化签发树
    function initSignTree(){
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    }



    //获取稿件详情
    function getManuscriptDetails() {
        req.post('groupPicCtro/getGroupPics.do', {
            groupId: vm.groupId
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.manuscriptDetail = resp.data;
                vm.manuscriptProperties = resp.data.properties;
                vm.manuscriptCates = resp.data.cates;
                vm.fristPfdUser = resp.data.fristPfdUser;
                console.log('success');
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //稿件详情模态框隐藏
    vm.manuscriptDetailModalHide = function (modalId) {
        modalOperate.modalHide(modalId);
    }
    //确认提交校审提交模态框显示
    vm.commitModalShow = function (modalId, groupStatus) {
        vm.groupStatus = groupStatus;
        modalOperate.modalShow(modalId);

    }
    //删除模态框显示
    vm.delModalShow = function (modalId) {
        modalOperate.modalShow(modalId);
    }
    //签发模态框显示
    vm.signModalShow = function (modalId) {
        if(vm.manuscriptCates.length == 0){
            layer.alert('签发必须有稿件类别');
            return;
        }
        initSignTree();
        modalOperate.modalShow(modalId);
    }
    //确认提交校审
    vm.confirmCommit = function (modalId) {
        req_commitDraft(modalId);
    }
    //提交
    function req_commitDraft(modalId){
        req.post('groupPicCtro/submitGroups.do',{
            groupIds: vm.groupId,
            roleId: vm.adminRoleId,
            langType: window.localStorage.lang
        }).success(function(resp){
            if(resp.code == '211'){
                layer.alert('操作成功');
                modalOperate.modalHide(modalId);
                getManuscriptDetails();
                $state.go('role.manager.myManuscript');
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }


    //点击管理控制下面详情div切换
    vm.toggleMangeOperateCon = function () {
        vm.mangeOperateFlag = !vm.mangeOperateFlag;
    }

    //退稿模态框显示
    vm.backManuscriptModalShow = function (modalId) {
        modalOperate.modalShow(modalId);
    }
    //确认退稿
    vm.confirmBackManuscript = function (modalId) {
        req_confirmBackManuscript(modalId);
    }

    //确认退稿请求
    function req_confirmBackManuscript(modalId) {
        if (!vm.backManuscriptReson) {
            layer.alert('请填写退稿评语!');
            return;
        }
        req.post('groupPicCtro/retreatGroupPic.do', {
            groupId: vm.groupId,
            content: vm.backManuscriptReson
        }).success(function (resp) {
            if (resp.code == '211') {
                console.log('success');
                modalOperate.modalHide(modalId);
                layer.alert('操作成功');
                $state.go('role.manager.sendManuscript');
                getManuscriptDetails();
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }



    //确认删除稿子
    vm.confirmDelDraftMs = function (modalId) {
        req_delManuscript(modalId);
    }

    //确认删除稿子请求
    function req_delManuscript(modalId) {
        req.post('groupPicCtro/delDraftsGroups.do',{
            groupIds: vm.groupId
        }).success(function(resp){
            if(resp.code == '211'){
                layer.alert('操作成功');
                modalOperate.modalHide(modalId);
                getManuscriptDetails();
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }




    //内部留资
    vm.innerLeaveInfo = function () {
        req.post('groupPicCtro/keepGroupPic.do', {
            groupId: vm.groupId
        }).success(function (resp) {
            if (resp.code == '211') {
                console.log('success');
                layer.alert('操作成功');
                vm.mangeOperateFlag = !vm.mangeOperateFlag;
                getManuscriptDetails();
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //开始编辑：编辑前锁定稿件
    vm.editSendManuscript = function () {
        req_beginEditSendManuscript();
    }
    //开始编辑：编辑前锁定稿件请求
    function req_beginEditSendManuscript() {
        req.post('groupPicCtro/editBegin.do', {
            groupId: vm.groupId
        }).success(function (resp) {
            if (resp.code == '211') {
                console.log('success');
                $state.go('role.manager.myManuscriptEdit',{
                    id: vm.groupId,
                    gType: vm.gType
                });
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //签发
    vm.signManuscript = function (modalId) {
        getSignParams();
        req_signManuscript(modalId);
    }
    //签发请求
    function req_signManuscript(modalId) {
        req.post('groupPicCtro/threeSubmitGroupPic.do', {
            groupId: vm.groupId,
            cateData: angular.toJson(vm.signReqParamData)
        }).success(function (resp) {
            if (resp.code == '211') {
                console.log('success');
                layer.alert('操作成功');
                getManuscriptDetails();
                modalOperate.modalHide(modalId);
                initSetting();
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //强制解锁
    vm.unlockManuscript = function () {
        req_unlockManuscript();
    }
    //强制解锁请求
    function req_unlockManuscript() {
        req.post('groupPicCtro/unLockGroup.do', {
            groupId: vm.groupId,
        }).success(function (resp) {
            if (resp.code == '211') {
                layer.alert('操作成功');
                getManuscriptDetails();
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
});