/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('projectManageEditCtrl0',function($scope,$cookies,req,md5,$state,$rootScope,$stateParams,$window){
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
        req.post('lanmu/lanMuPicDetail.do',{
            lanmuid: vm.columnId
        }).success(function(resp){
            if(resp.code == '211'){
                vm.topicId = resp.data.topicId;
                vm.specialName = resp.data.topicName;
                vm.columnName = resp.data.name;
                vm.sonColumnName1 = resp.data.cpLanmu1.name;
                vm.sonTemplate1 = resp.data.cpLanmu1.typeId;
                vm.sonColumnName2 = resp.data.cpLanmu2.name;
                vm.sonTemplate2 = resp.data.cpLanmu2.typeId;
                vm.columnPic = resp.data.subAdds || 'admin/assets/img/role/empty.png';
                $('#modifyWaterMarkImageId').attr("src", vm.columnPic);
                //子栏目1稿件
                //根据子栏目模板显示稿件数量
                vm.manuscript1 = verdict(resp.data.cpLanmu1.typeId,resp.data.cpLanmu1.cpLanmuPictures);
                vm.sonColumnId1 = resp.data.cpLanmu1.id;
                //子栏目1更多稿件
                req.post('lanmu/lanMuPicMoreDetail.do', {
                    lanmuid: vm.sonColumnId1
                }).success(function (resp) {
                    if(resp.code == '211'){
                        var arr = [];
                        arr.length = 3;
                        for(var i = 0; i < resp.data.length; i++){
                            if( i < 3){
                                arr[i] = resp.data[i];
                            }
                        }
                        vm.moreManuscript1 = arr;
                    }
                });
                //子栏目2稿件
                //根据子栏目模板显示稿件数量
                vm.manuscript2 = verdict(resp.data.cpLanmu2.typeId,resp.data.cpLanmu2.cpLanmuPictures);
                vm.sonColumnId2 = resp.data.cpLanmu2.id;
                //子栏目2更多稿件
                req.post('lanmu/lanMuPicMoreDetail.do', {
                    lanmuid: vm.sonColumnId2
                }).success(function (resp) {
                    if(resp.code == '211'){
                        var arr = [];
                        arr.length = 3;
                        for(var i = 0; i < resp.data.length; i++){
                            if( i < 3){
                                arr[i] = resp.data[i];
                            }
                        }
                        vm.moreManuscript2 = arr;
                    }
                });
            }
        });
    }
    /**
     * 保存页面数据
     * */
    vm.saveProjectManageEdit = function () {
        localStorage.removeItem('proLanmuSrc');
        if(!vm.columnName){
            layer.alert('请输入栏目名称');
            return;
        }
        if(!vm.sonColumnName1){
            layer.alert('请输入子栏目1名称');
            return;
        }
        if(!vm.sonColumnName2){
            layer.alert('请输入子栏目2名称');
            return;
        }
        function req_uploadManuscript(callback){
            $("#modifyWaterMarkForm").ajaxSubmit(function(resp) {
                if(resp.code == '211'){
                    vm.reqColumnInfo();
                    layer.msg(resp.msg ? resp.msg : '保存成功');
                    if(callback) callback();
                }else{
                    layer.alert(resp.msg ? resp.msg : '保存失败');
                }
            });
        }
        // jquery 表单提交
        req_uploadManuscript();
        return false; // 必须返回false，否则表单会自己再做一次提交操作，并且页面跳转
    };
    /**
     * 选择稿件
     * colId 栏目id或者子栏目id
     * columnTemplate 模板id
     * num 稿件位置
     * id 原稿件id  在更多里面选择稿件需要
     * */
    vm.selectManuscript = function (colId, num , id) {
        //这里获取的是路由名截取到对应的模板id，用来选完稿件之后页面回退。
        var columnTemplate = $state.current.name.substring($state.current.name.indexOf('Edit')+4);
        layer.confirm('请您先保存页面数据，以防数据丢失',{
            btn: ['前往选择稿件', '返回保存']
        }, function () {
            layer.closeAll('dialog');
            $cookies.put('columnFlag', true);
            $state.go('role.manager.database',{columnId: colId, columnTemplate: columnTemplate, num: num, oldId: id, topicId: vm.specialId, lanmuId: vm.columnId});
        }, function () {
            //取消
        });
    }
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
            $state.go('role.manager.database',{columnId: colId, columnTemplate: columnTemplate, num: num, topicId: vm.specialId, lanmuId: vm.columnId});
        }, function () {
            //取消
        });
    }
    /**
     * 获取更多稿件table
     * num  table1/table2
     * page 页数
     * type
     * searchname 搜索名
     * */
    vm.showManuscriptTable = function (num, page, searchname) {
        var id = vm['sonColumnId' + num];
        req.post('lanmu/lanMuPicMoreDetail.do', {
            page: page,
            rows: 10,
            name: searchname,
            lanmuid: id
        }).success(function (resp) {
            if (resp.code == '211') {
                vm['tableShow' + num] = true;
                vm['moreContribute' + num] = resp.data;
                var specialPageNum = resp.page;
                if (specialPageNum && specialPageNum >= 1) {
                    // 处理分页
                    var str = '#paginationImgId' + num;
                    $.jqPaginator(str, {
                        totalPages: specialPageNum,
                        visiblePages: 4,
                        currentPage: page,
                        onPageChange: function (mineNum, pageType) {
                            if (pageType === 'change') {
                                if(searchname != undefined && searchname != ''){
                                    vm.showManuscriptTable(num, mineNum, searchname);
                                }else{
                                    vm.showManuscriptTable(num, mineNum);
                                }
                            }
                        }
                    });
                }
            } else {
            }
        });
    }
    /**
     * 监听每一个选项的改变
     */
    vm.isHadAllCheck1 = false;
    vm.isHadAllCheck2 = false;
    vm.checkBoxArray1 = [];
    vm.checkBoxArray2 = [];
    $scope.$watchCollection('projectManageEdit0.checkBoxArray1', function (newC) {
        if(newC != undefined){
            if( newC.every(function (item) { return item != false; }) ){
                vm.isHadAllCheck1 = true;
            }else {
                vm.isHadAllCheck1 = false;
            }
        }
    });
    $scope.$watchCollection('projectManageEdit0.checkBoxArray2', function (newC) {
        if(newC != undefined){
            if (newC.every(function (item) {return item != false;})) {
                vm.isHadAllCheck2 = true;
            } else {
                vm.isHadAllCheck2 = false;
            }
        }
    });
    /**
     * 处理全选
     */
    vm.onCheckAllClick = function (num) {
        if (vm['isHadAllCheck' + num]) {
            vm['checkBoxArray' + num] = vm['moreContribute' + num].map(function (item) {
                return item.id
            });
        } else {
            vm['checkBoxArray' + num] = vm['moreContribute' + num].map(function (item) {
                return false
            });
        }
    };
    /**
     * 点击删除
     */
    vm.deleteDataParamsId1 = "";
    vm.deleteDataParamsId2 = "";
    vm.onShowDeleteModelClick = function (num) {
        var paramsId = "";
        for (var c = 0; c < vm['checkBoxArray' + num].length; c++) {
            var checkBoxItem = vm['checkBoxArray' + num][c];
            if (checkBoxItem != false) {
                paramsId += checkBoxItem + ",";
            }
        }
        if (paramsId != "") {
            vm['deleteDataParamsId' + num] = paramsId.substr(0, paramsId.length - 1);
            vm.delMoreManuscript(num);
        } else {
            layer.alert("请选择要删除的专题");
        }
    }
    /**
     * 删除更多稿件
     * id 稿件id
     * num table1/table2
     * */
    vm.delMoreManuscript = function (num, id) {
        var id = id ? id : vm['deleteDataParamsId' + num];
        layer.confirm('您确定要删除这些稿件吗？',{
            btn: ['确定','取消']
        },function() {
            req.post('lanmu/deletePIc.do', {
                id: id
            }).success(function (resp){
                if(resp.code == '211'){
                    vm['isHadAllCheck' + num] = false;
                    vm.showManuscriptTable(num, 1, 1);
                    layer.msg(resp.msg ? resp.msg : '删除成功', {time: 2000});
                }else{
                    console.info(resp.msg);
                }
            });
        });
    }
    /**
     * 更多稿件由table转换为list
     * num list1/list2
     * */
    vm.backMoreManuscriptList = function (num) {
        vm['tableShow' + num] = false;
    }
    /**初始化页面数据*/
    vm.reqColumnInfo();
});