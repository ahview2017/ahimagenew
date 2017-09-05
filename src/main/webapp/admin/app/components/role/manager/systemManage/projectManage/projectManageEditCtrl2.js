/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('projectManageEditCtrl2',function($scope,$cookies,req,md5,$state,$rootScope,$stateParams){
    var vm = this;
    //专题id
    vm.specialId = $stateParams.specialId;
    //栏目id
    vm.columnId = $stateParams.id;
    /**
     * 控制栏目稿件table显示
     * */
    vm.tableShow1 = false;
    vm.tableShow2 = false;
    /**
     * 根据子栏目模板显示稿件数量
     * type 子栏目模板类型 4,5,6,8,10
     * contribute 现有稿件
     * */
    function verdict (type,contribute) {
        contribute = contribute ? contribute : [];
        var num  = parseInt(type);
        var arr = [];
        if(num === 4){
            arr.length = 4;
        }else if (num === 5){
            arr.length = 5;
        }else if (num === 6){
            arr.length = 6;
        }else if(num === 8){
            arr.length = 8;
        }else if(num === 10){
            arr.length = 10;
        }
        for(var i = 0; i < contribute.length; i++){
            arr[(contribute[i].potision - 1)] = contribute[i];
        }
        return arr;
    }
    /**
     * 请求栏目数据
     * */
    vm.reqColumnInfo = function () {
        req.post('lanmu/showLanmuWithAdv.do',{
            lanmuid: vm.columnId,
            topicId: vm.specialId
        }).success(function(resp){
            if(resp.code == '211'){
                vm.specialName = resp.data.topicName;
                vm.columnName = resp.data.name;
                vm.typeId = resp.data.typeId;
                //栏目稿件
                //根据栏目模板显示稿件数量,轮播显示5张，他的类型id为2；
                vm.manuscript = verdict((parseInt(resp.data.typeId) + 3), resp.data.cpLanmuPictures);
            }
        });
        //栏目更多稿件
        req.post('lanmu/lanMuPicMoreDetail.do', {
            lanmuid: vm.columnId
        }).success(function (resp) {
            if(resp.code == '211'){
                var arr = [];
                arr.length = 3;
                for(var i = 0; i < resp.data.length; i++){
                    if( i < 3 ){
                        arr[i] = resp.data[i];
                    }
                }
                vm.moreManuscript = arr;
            }
        });
    };
    //请求公告
    vm.req_gg = function (page,searchname) {
        req.post('lanmu/showMoreLanmuWithAdv.do',{
            topicId: vm.specialId
        }).success(function (resp) {
            if(resp.code == '211'){
                vm.ggList = resp.data ? resp.data : [];
                var specialPageNum = resp.page;
                if (specialPageNum && specialPageNum >= 1) {
                    // 处理分页
                    $.jqPaginator('#paginationImgId1', {
                        totalPages: specialPageNum,
                        visiblePages: 4,
                        currentPage: page,
                        onPageChange: function (mineNum, pageType) {
                            if (pageType === 'change') {
                                if(searchname != undefined && searchname != ''){
                                    vm.req_gg(mineNum, searchname);
                                }else{
                                    vm.req_gg(mineNum);
                                }
                            }
                        }
                    });
                }
            }
        })
    }
    /**
     * 保存页面数据
     * */
    vm.saveProjectManageEdit = function () {
        if(!vm.columnName){
            layer.alert('请输入栏目名称');
            return;
        }
        req.post('lanmu/editLanmu.do',{
            "cpLanmu[0].topicId": vm.specialId,
            "cpLanmu[0].name": vm.columnName,
            "cpLanmu[0].id": vm.columnId,
            "cpLanmu[0].typeId": vm.typeId
        }).success(function (resp) {
            if(resp.code == '211'){
                vm.reqColumnInfo();
                layer.msg(resp.msg ? resp.msg : "保存成功", {time: 2000});
            }else{
                console.info(resp.msg ? resp.msg : '保存失败');
            }
        });
    };
    /**
     * 选择稿件
     * colId 栏目id或者子栏目id
     * columnTemplate 模板id
     * num 稿件位置
     * id 原稿件id  在更多里面选择稿件需要
     * */
    vm.selectManuscript = function (colId, num , id) {
        var columnTemplate = $state.current.name.substring($state.current.name.indexOf('Edit')+4);
        layer.confirm('请您先保存页面数据，以防数据丢失',{
            btn: ['前往选择稿件', '返回保存']
        }, function () {
            layer.closeAll('dialog');
            $cookies.put('columnFlag', true);
            $state.go('role.manager.database',{columnId: colId, columnTemplate: columnTemplate, num: num, oldId: id, topicId: vm.specialId,lanmuId: vm.columnId});
        }, function () {
            //取消
        });
    };
    /**
     * 添加更多稿件,多选
     * colId 子栏目id 或者栏目id
     * num 栏目模板id
     * */
    vm.addMoreManuscript = function (colId, num) {
        var columnTemplate = $state.current.name.substring($state.current.name.indexOf('Edit')+4);
        layer.confirm('请您先保存页面数据，以防数据丢失',{
            btn: ['前往选择稿件', '返回保存']
        }, function () {
            layer.closeAll('dialog');
            $cookies.put('columnMoreFlag', true);
            $state.go('role.manager.database',{columnId: colId, columnTemplate: columnTemplate, num: num, topicId: vm.specialId,lanmuId: vm.columnId});
        }, function () {
            //取消
        });
    };
    /**
     * 获取更多稿件table
     * num  table1/table2
     * page 页数
     * type
     * searchname 搜索名
     * */
    vm.showManuscriptTable = function (page, searchname) {
        req.post('lanmu/lanMuPicMoreDetail.do', {
            page: page,
            rows: 10,
            name: searchname,
            lanmuid: vm.columnId
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.tableShow1 = true;
                vm.moreContribute = resp.data;
                var specialPageNum = resp.page;
                if (specialPageNum && specialPageNum >= 1) {
                    // 处理分页
                    $.jqPaginator('#paginationImgId', {
                        totalPages: specialPageNum,
                        visiblePages: 4,
                        currentPage: page,
                        onPageChange: function (mineNum, pageType) {
                            if (pageType === 'change') {
                                if(searchname != undefined && searchname != ''){
                                    vm.showManuscriptTable(mineNum, searchname);
                                }else{
                                    vm.showManuscriptTable(mineNum);
                                }
                            }
                        }
                    });
                }
            } else {
                console.log(resp.msg);
            }
        });
    };
    /**
     * 监听每一个选项的改变
     */
    vm.isHadAllCheck = false;
    vm.checkBoxArray = [];
    $scope.$watchCollection('projectManageEdit0.checkBoxArray', function (newC) {
        if(newC != undefined){
            if( newC.every(function (item) { return item != false; }) ){
                vm.isHadAllCheck = true;
            }else {
                vm.isHadAllCheck = false;
            }
        }
    });
    /**
     * 处理全选
     */
    vm.onCheckAllClick = function () {
        if (vm.isHadAllCheck) {
            vm.checkBoxArray = vm.moreContribute.map(function (item) {
                return item.id
            });
        } else {
            vm.checkBoxArray = vm.moreContribute.map(function (item) {
                return false
            });
        }
    };
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
            vm.delMoreManuscript();
        } else {
            layer.alert("请选择要删除的专题");
        }
    };
    /**
     * 删除更多稿件
     * id 稿件id
     * */
    vm.delMoreManuscript = function (id) {
        var id = id ? id : vm.deleteDataParamsId;
        layer.confirm('您确定要删除这些稿件吗？',{
            btn: ['确定','取消']
        },function() {
            req.post('lanmu/deletePIc.do', {
                id: id
            }).success(function (resp){
                if(resp.code == '211'){
                    vm.isHadAllCheck = false;
                    vm.showManuscriptTable(1);
                    layer.msg(resp.msg ? resp.msg : '删除成功', {time: 2000});
                }else{
                    console.info(resp.msg);
                }
            });
        });
    };
    
    /*---------------------------------------------------公告开始----------------------------------------------------------------*/
    /**
     * 请求公告
     * page 页数
     * searchname 搜索名
     * */
    vm.req_gg = function (page,searchname) {
        req.post('lanmu/showMoreLanmuWithAdv.do',{
            topicId: vm.specialId,
            row: 10,
            page:page
        }).success(function (resp) {
            if(resp.code == '211'){
                vm.ggList = resp.data ? resp.data : [];
                var specialPageNum = resp.page;
                if (specialPageNum && specialPageNum >= 1) {
                    // 处理分页
                    $.jqPaginator('#paginationImgId1', {
                        totalPages: specialPageNum,
                        visiblePages: 4,
                        currentPage: page,
                        onPageChange: function (mineNum, pageType) {
                            if (pageType === 'change') {
                                if(searchname != undefined && searchname != ''){
                                    vm.req_gg(mineNum, searchname);
                                }else{
                                    vm.req_gg(mineNum);
                                }
                            }
                        }
                    });
                }
            }
        })
    }
    /**
     * 富文本编辑器配置
     * 初始化
     * */
    var configParams = {
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
    }
    //实例化一个编辑器
    var ue1 = new UE.ui.Editor(configParams);
    ue1.render('addEditWeb');
    var ue2 = new UE.ui.Editor(configParams);
    ue2.render('updateEditWeb');
    /**
     * 添加公告
     * */
    vm.ggAddModal = function () {
        $('#ggAddModal').modal('show');
    };
    /**
     * 保存公告富文本编辑器
     * */
    vm.ggAddSave = function (num, id, position) {
        vm.superText = ue1.getContent();
        var regu = /^[0-9]*[1-9][0-9]*$/;
        var re = new RegExp(regu);
        if(!vm.ggAddName){
            layer.alert('请输入公告标题');
            return;
        }
        if(!vm.superText){
            layer.alert('请输入公告内容');
            return;
        }
        if(!re.test(vm.ggAddPx)){
            layer.alert('请输入正确的排序号');
            return;
        }
        req.post('notice/add.do',{
            "notice_title": vm.ggAddName,
            "notice_content": vm.superText,
            "topic_id": vm.specialId,
            "position": vm.ggAddPx
        }).success(function (resp){
            if(resp.code == '211'){
                vm.req_gg(1);
                vm.superText = vm.ggAddName = vm.ggAddPx = '';
                ue1.execCommand('cleardoc');
                ue1.setContent('');
                $('#ggAddModal').modal('hide');
                layer.msg(resp.msg ? resp.msg : '保存成功');
            }else{
                layer.alert(resp.msg ? resp.msg : '保存失败');
            }
        });
    };
    /**
     * 修改公告
     * id 公告id
     * */
    vm.ggUpdateModal = function (id) {
        $('#ggUpdateModal').modal('show');
        var list = vm.ggList ? vm.ggList : [];
        ue2.ready(function() {//编辑器初始化完成再赋值
            for(var i = 0; i < list.length; i++){
                if(vm.ggList[i].id == id){
                    ue2.setContent(list[i].noticeContent);  //赋值给UEditor
                    vm.ggUpdateName = list[i].noticeTitle;
                    vm.ggUpdatePx = list[i].position;
                    vm.oldPisition = list[i].position;
                    vm.editId = id;
                }
            }
        });
        
    };
    /**
     * 修改公告富文本编辑器
     * */
    vm.ggUpdateSave = function () {
        vm.updateSupertext = ue2.getContent();
        var regu = /^[0-9]*[1-9][0-9]*$/;
        var re = new RegExp(regu);
        if(!vm.ggUpdateName){
            layer.alert('请输入公告标题');
            return;
        }
        if(!vm.updateSupertext){
            layer.alert('请输入公告内容');
            return;
        }
        if(!re.test(vm.ggUpdatePx)){
            layer.alert('请输入正确的排序号');
            return;
        }
        req.post('notice/edit.do',{
            "id": vm.editId,
            "topic_id": vm.specialId,
            "notice_title": vm.ggUpdateName,
            "notice_content": vm.updateSupertext,
            "position": vm.ggUpdatePx,
            "yposition": vm.oldPisition
        }).success(function (resp){
            if(resp.code == '211'){
                vm.req_gg(1);
                vm.ggUpdateName = vm.updateSupertext = vm.ggUpdatePx = '';
                ue2.execCommand('cleardoc');
                $('#ggUpdateModal').modal('hide');
                layer.msg(resp.msg ? resp.msg : '修改成功');
            }else{
                layer.alert(resp.msg ? resp.msg : '修改失败');
            }
        });
    };
    /**
     * 监听每一个选项的改变
     */
    vm.isHadAllCheck1 = false;
    vm.checkBoxArray1 = [];
    $scope.$watchCollection('projectManageEdit2.checkBoxArray1', function (newC) {
        if(newC != undefined){
            if( newC.every(function (item) { return item != false; }) ){
                vm.isHadAllCheck1 = true;
            }else {
                vm.isHadAllCheck1 = false;
            }
        }
    });
    /**
     * 处理全选
     */
    vm.onCheckAllClick1 = function () {
        if (vm.isHadAllCheck1) {
            vm.checkBoxArray1 = vm.ggList.map(function (item) {
                return item.id
            });
        } else {
            vm.checkBoxArray1 = vm.ggList.map(function (item) {
                return false
            });
        }
    };
    /**
     * 点击删除
     */
    vm.deleteDataParamsId1 = "";
    vm.onShowDeleteModelClick1 = function () {
        var paramsId = "";
        for (var c = 0; c < vm.checkBoxArray1.length; c++) {
            var checkBoxItem = vm.checkBoxArray1[c];
            if (checkBoxItem != false) {
                paramsId += checkBoxItem + ",";
            }
        }
        if (paramsId != "") {
            vm.deleteDataParamsId1 = paramsId.substr(0, paramsId.length - 1);
            vm.delGg();
        } else {
            layer.alert("请选择要删除的专题");
        }
    };
    /**
     * 删除公告
     * id 公告id
     * */
    vm.delGg = function (id) {
        id = id ? id : vm.deleteDataParamsId1;
        layer.confirm('您确定要删除这些公告?',{
            btn: ['确定','取消']
        },function() {
            req.post('notice/delete.do',{
                id: id
            }).success(function (resp) {
                if(resp.code == '211'){
                    vm.req_gg(1);
                    layer.msg(resp.msg ? resp.data : '删除成功');
                }else{
                    console.info(resp.msg ? resp.msg : '删除失败');
                }
            });
        });
    };
    /**初始化页面数据*/
    vm.reqColumnInfo();
    vm.req_gg(1);
});