/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('projectManageDetailCtrl',function($scope,$cookies,req,md5,$state,$rootScope,$stateParams){
    var vm = this;
    vm.projectName = $stateParams.name;

    //模态框移动
    $(".modal-dialog").draggable({
        handle: ".modal-header"
    });

    //初始化相关配置
    function initSetting(){
        //专题id
        vm.topicId = $stateParams.id;

        //添加栏目模板
        vm.columnTemplateObj = [
            {id: 0,name: '无'},
            //todo 暂时不要
            // {id: 1,name: '一图一文'},
            {id: 2,name: '轮播图一'},
            {id: 3,name: '轮播图二'},
            {id: 4,name: '4展'},
            {id: 5,name: '5展'},
            {id: 6,name: '6展'},
            {id: 8,name: '8展'},
            {id: 10,name: '10展'},
        ];
        //子栏目模板
        vm.sonColumnTemplateObj = [
            {id: 4,name: '4展'},
            {id: 5,name: '5展'},
            {id: 6,name: '6展'},
            {id: 8,name: '8展'},
            {id: 10,name: '10展'},
        ];
        //初始化选中的模板
        vm.columnTemplate = vm.columnTemplateObj[0].id;
        vm.sonColumnTemplate1 = vm.sonColumnTemplateObj[0].id;
        vm.sonColumnTemplate2 = vm.sonColumnTemplateObj[0].id;
        vm.columnAddModal = function () {
            $('#columnAddModal').modal('show');
            vm.haveSonColumn();
        }
        //存放栏目数组
        vm.columnArray = [];
        //栏目总条数
        vm.colunmnList_total = 0;
        //默认当前页1
        vm.pagination = {
            current: 1
        };
        //默认每页6条
        vm.selPageRows = '6';

    }

    //初始化
    function init(){
        initSetting();
        getColumnTableData(1);
    }
    init();
    /**
     * 获取栏目表格数据
     */
    function getColumnTableData(page, searchname) {
        if( searchname != undefined && searchname != ''){
            var url = "lanmu/serachLanmu.do";
        }else{
            url = "lanmu/showLanMu.do";
        }
        req.post(url, {
            page: page,
            rows: vm.selPageRows,
            name: searchname,
            topicId: vm.topicId

        }).success(function (resp) {
            if (resp.code == '211') {
                vm.columnArray = resp.data;
                vm.totalPages = resp.page;
                vm.columnList_total = resp.other;
            } else {
                console.log(resp.msg);
            }
        });
    }

    //页数变化
    vm.pageChanged = function (pageNumber) {
        getColumnTableData(pageNumber);
    };

    /**
     * 搜索
     * */
    vm.searchColumn = function () {
        getColumnTableData(1, vm.columnSearchName);
    }
    /**
     * 处理全选
     */
    vm.checkBoxArray = [];
    vm.isHadAllCheck = false;
    vm.onCheckAllClick = function () {
        if (vm.isHadAllCheck) {
            vm.checkBoxArray = vm.columnArray.map(function (item) {
                return item.id
            });
        } else {
            vm.checkBoxArray = vm.columnArray.map(function (item) {
                return false
            });
        }
    };
    /**
     * 监听每一个选项的改变
     */
    $scope.$watchCollection('projectManageDetail.checkBoxArray', function (newC) {
        if(newC != undefined){
            if (newC.every(function (item) {
                    return item != false;
                })) {
                vm.isHadAllCheck = true;
            } else {
                vm.isHadAllCheck = false;
            }
        }
    });

    /**
     * 点击删除
     */
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
            vm.delColumn();
        } else {
            layer.alert("请选择要删除的栏目");
        }
    };

    /**
     * 删除栏目
     */
    vm.delColumn = function (id) {
        vm.deleteDataParamsId = id ? id : vm.deleteDataParamsId;
        layer.confirm('您确定要删除这些栏目吗？', {
            btn: ['确定','取消'] //按钮
        }, function(){
            req.post('lanmu/deleteLanmu.do', {
                "id": vm.deleteDataParamsId
            }).success(function (resp) {
                if (resp.code == '211') {
                    vm.isHadAllCheck = false;
                    getColumnTableData(1);
                    layer.msg(resp.msg ? resp.msg : '删除成功',{time:2000});
                } else {
                    layer.alert(resp.msg ? resp.msg : '删除失败');
                }
            });
        }, function(){

        });

    };

    /**
     * 添加栏目
     */
    vm.addColumn = function () {
        var regu = /^[0-9]*[1-9][0-9]*$/;
        var num = new RegExp(regu);
        if(!vm.columnName){
            layer.alert('请输入栏目名称');
            return;
        }
        if(!num.test(vm.columnNum)){
            layer.alert('请输入正确的排序号');
            return;
        }
        if(vm.columnTemplate == 0){
            if(!vm.sonColumnName1){
                layer.alert('请输入子栏目1名称');
                return;
            }
            if(!vm.sonColumnName2){
                layer.alert('请输入子栏目2名称');
                return;
            }
            var params = {
                "cpLanmu[0].topicId": vm.topicId,
                "cpLanmu[0].lanmuNo": vm.columnNum,
                "cpLanmu[0].typeId": vm.columnTemplate,
                "cpLanmu[0].name": vm.columnName,
                "cpLanmu[1].topicId": vm.topicId,
                "cpLanmu[1].typeId": vm.sonColumnTemplate1,
                "cpLanmu[1].name": vm.sonColumnName1,
                "cpLanmu[2].topicId": vm.topicId,
                "cpLanmu[2].typeId": vm.sonColumnTemplate2,
                "cpLanmu[2].name": vm.sonColumnName2
            }
        }else{
            params = {
                "cpLanmu[0].topicId": vm.topicId,
                "cpLanmu[0].lanmuNo": vm.columnNum,
                "cpLanmu[0].typeId": vm.columnTemplate,
                "cpLanmu[0].name": vm.columnName
            }
        }
        req.post('lanmu/addLanmu.do',params).success(function (resp){
            if(resp.code == '211'){
                getColumnTableData(1);
                vm.columnNum = vm.columnName = vm.sonColumnName1 = vm.sonColumnName2 =null;
                vm.columnTemplate = 0;
                vm.sonColumnTemplate1 = vm.sonColumnTemplate2 = 4;
                $('#columnAddModal').modal('hide');
                layer.msg(resp.msg ? resp.msg : '添加栏目成功',{time:2000});
            }else{
                layer.alert(resp.msg ? resp.msg : '添加栏目失败');
            }
        });
    };
    /**
     * 展示编辑框
     */
    vm.updateEditId = "";
    vm.updateColumn = function () {
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
                layer.alert("只能选择一个栏目进行编辑");
            } else {
                vm.updateEditId = editIdArray[0];
                vm.columnlUpdateModal();
            }
        } else {
            layer.alert("请选择要编辑的栏目");
        }
    };
    /**
     *修改栏目展示内容*/
    vm.columnlUpdateModal = function (id) {
        vm.updateEditId = id ? id : vm.updateEditId;
        angular.forEach(vm.columnArray,function(item,index){
            $('#columnUpdateModal').modal('show');
            if(item.id == vm.updateEditId){
                vm.updateColumnNum = item.lanmuNo;
                vm.updateColumnName = item.name;
                vm.updateColumnTemplate = item.typeId;
                vm.columnPid = item.pid;
                if(item.typeId == 0){
                    vm.sonColumn1 = true;
                    vm.columnTemplateObj = [
                        {id: 0,name: '无'}
                    ];
                    vm.updateSonColumnName1 = item.cpLanmu1.name;
                    vm.updateSonColumnTemplate1 = item.cpLanmu1.typeId;
                    vm.updateSonColumnName2 = item.cpLanmu2.name;
                    vm.updateSonColumnTemplate2 = item.cpLanmu2.typeId;
                    vm.lanmuId1 = item.cpLanmu1.id;
                    vm.lanmuId2 = item.cpLanmu2.id;
                }else{
                    vm.sonColumn1 = false;
                    vm.columnTemplateObj = [
                        // {id: 1,name: '一图一文'},
                        {id: 2,name: '轮播图一'},
                        {id: 3,name: '轮播图二'},
                        {id: 4,name: '4展'},
                        {id: 5,name: '5展'},
                        {id: 6,name: '6展'},
                        {id: 8,name: '8展'},
                        {id: 10,name: '10展'},
                    ];
                    vm.lanmuId1 = vm.lanmuId2 = vm.updateSonColumnTemplate1 = vm.updateSonColumnName1 = vm.updateSonColumnTemplate2 = vm.updateSonColumnName2 = null;
                }
            }
        });
    }
    /**
     * 编辑栏目
     */
    vm.saveUpdateColumn = function () {
        var regu = /^[0-9]*[1-9][0-9]*$/;
        var num = new RegExp(regu);
        if(!vm.updateColumnName){
            layer.alert('请输入栏目名称');
            return;
        }
        if(!num.test(vm.updateColumnNum)){
            layer.alert('请输入正确的排序号');
            return;
        }
        if(vm.updateColumnTemplate == 0){
            if(!vm.updateSonColumnName1){
                layer.alert('请输入子栏目1名称');
                return;
            }
            if(!vm.updateSonColumnName2){
                layer.alert('请输入子栏目2名称');
                return;
            }
            var params = {
                "cpLanmu[0].id": vm.updateEditId,
                "cpLanmu[0].pid": vm.columnPid,
                "cpLanmu[0].lanmuNo": vm.updateColumnNum,
                "cpLanmu[0].topicId": vm.topicId,
                "cpLanmu[0].typeId": vm.updateColumnTemplate,
                "cpLanmu[0].name": vm.updateColumnName,
                "cpLanmu[0].cpLanmu1.topicId": vm.topicId,
                "cpLanmu[0].cpLanmu1.typeId": vm.updateSonColumnTemplate1,
                "cpLanmu[0].cpLanmu1.name": vm.updateSonColumnName1,
                "cpLanmu[0].cpLanmu1.id": vm.lanmuId1,
                "cpLanmu[0].cpLanmu2.topicId": vm.topicId,
                "cpLanmu[0].cpLanmu2.typeId": vm.updateSonColumnTemplate2,
                "cpLanmu[0].cpLanmu2.name": vm.updateSonColumnName2,
                "cpLanmu[0].cpLanmu2.id": vm.lanmuId2
            }
        }else{
            params = {
                "cpLanmu[0].id": vm.updateEditId,
                "cpLanmu[0].lanmuNo": vm.updateColumnNum,
                "cpLanmu[0].topicId": vm.topicId,
                "cpLanmu[0].typeId": vm.updateColumnTemplate,
                "cpLanmu[0].name": vm.updateColumnName
            }
        }
        req.post('lanmu/editLanmu.do', params).success(function (resp) {
            if(resp.code == '211'){
                vm.updateColumnName = vm.updateColumnNum = vm.updateColumnTemplate = vm.updateSonColumnTemplate1 = vm.updateSonColumnName1 = vm.updateSonColumnTemplate2 = vm.updateSonColumnName2 = null;
                getColumnTableData(1);
                $('#columnUpdateModal').modal('hide');
                vm.columnTemplateObj = [
                    {id: 0,name: '无'},
                    // {id: 1,name: '一图一文'},
                    {id: 2,name: '轮播图一'},
                    {id: 3,name: '轮播图二'},
                    {id: 4,name: '4展'},
                    {id: 5,name: '5展'},
                    {id: 6,name: '6展'},
                    {id: 8,name: '8展'},
                    {id: 10,name: '10展'},
                ];
                layer.msg(resp.msg ? resp.msg : '修改栏目成功',{time:2000});
            }else{
                layer.alert(resp.msg ? resp.msg : '修改栏目失败');
            }
        });
    };
    /**
    *添加栏目控制是否有子栏目
     */
    vm.sonColumn = true;
    vm.haveSonColumn = function () {
        if(parseInt(vm.columnTemplate) === 0){
            vm.sonColumn = true;
        }else{
            vm.sonColumnTemplate1 = vm.sonColumnName1 = vm.sonColumnTemplate2 = vm.sonColumnName2 = null;
            vm.sonColumn = false;
        }
    }
    /**
     * 修改栏目控制是否有子栏目
    * */
    // vm.haveSonColumn1 = function () {
    //     if(parseInt(vm.updateColumnTemplate) === 0){
    //         vm.sonColumn1 = true;
    //     }else{
    //         vm.lanmuId1 = vm.lanmuId2 = vm.updateSonColumnName1 = vm.updateSonColumnName2 = null;
    //         vm.updateSonColumnTemplate1 = vm.updateSonColumnTemplate2 = 4;
    //         vm.sonColumn1 = false;
    //     }
    // }
    /**
     *删除子栏目
     * */
    //todo 目前子栏目不让删
    vm.delSonColumn = function (id) {
        console.log(id);
    }

});