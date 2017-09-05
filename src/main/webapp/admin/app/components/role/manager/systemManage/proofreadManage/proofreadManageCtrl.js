adminModule.controller('proofreadManageCtrl', function($scope, $cookies, req, $state, $rootScope, modalOperate, $window, allModalMove){
    var vm = this;

    //校审配置模态框隐藏
    vm.proofreadModalHide = function(modalId){
        modalOperate.modalHide(modalId);
        vm.selProofUnames = {};
    }
    //高级检索模态显示
    vm.ModalShow = function (modalId) {
        modalOperate.modalShow(modalId);
    };
    //高级检索模态框隐藏
    vm.ModalHide = function (modalId) {
        modalOperate.modalHide(modalId);
    };
    //校审添加配置模态框隐藏
    vm.addProofreadModalHide  = function(modalId){
        modalOperate.modalHide(modalId);
        vm.selProofUnames = {};
        initSetting();
    }
    //校审配置添加模态框显示
    vm.proofreadAddModalShow = function(modalId){
        modalOperate.modalShow(modalId);
        vm.pagefirstGrade = '';
        vm.pagesecondGrade = '';
        vm.pagethreeGrade = '';
    }
    //移动模态框
    vm.moveModal = function(dragDiv,tagDiv) {
        allModalMove.modalMove(dragDiv,tagDiv);
    }
    //校审配置选择值班编辑模态框显示
    vm.proofEditorModalShow = function(modalId,type){
        vm.dutyType = type;
        vm.selProofUnames = {};
        //默认选择的值班编辑在页面上展示
        if(vm.willUpdateDutys){
            for(var i = 0;i < vm.willUpdateDutys.length; i++) {
                if(type == 1 && vm.willUpdateDutys[i].type == 1){
                    for(var j = 0; j < vm.dutyEditorList.length; j++){
                        if(vm.willUpdateDutys[i].userName == vm.dutyEditorList[j].USER_NAME){
                            vm.selProofUnames[vm.dutyEditorList[j].USER_NAME] = true;
                        }
                    }
                }
                if(type == 2 && vm.willUpdateDutys[i].type == 2){
                    for(var j = 0; j < vm.dutyEditorList.length; j++){
                        if(vm.willUpdateDutys[i].userName == vm.dutyEditorList[j].USER_NAME){
                            vm.selProofUnames[vm.dutyEditorList[j].USER_NAME] = true;
                        }
                    }
                }
                if(type == 3 && vm.willUpdateDutys[i].type == 3){
                    for(var j = 0; j < vm.dutyEditorList.length; j++){
                        if(vm.willUpdateDutys[i].userName == vm.dutyEditorList[j].USER_NAME){
                            vm.selProofUnames[vm.dutyEditorList[j].USER_NAME] = true;
                        }
                    }
                }

            }
        }
        getDutyUser();
        modalOperate.modalShow(modalId);
    }
    //校审配置修改模态框显示
    vm.proofreadUpdateModalShow = function(modalId){
        vm.selKeyArr = [];
        vm.pagefirstGrade = '';
        vm.pagesecondGrade = '';
        vm.pagethreeGrade = '';
        judgeIfSelData();
        console.log(typeof vm.selKeyArr);
        if(vm.selKeyArr.length != 1){
            layer.alert('请选择一条数据进行操作');
            return;
        }
        if(vm.selKeyArr.length == 1){
            angular.forEach(vm.proofreadList,function(item,index){
                if(item.id == vm.selKeyArr[0]){
                    vm.modify.starttime = item.beginTime;
                    vm.modify.endtime = item.endTime;
                    vm.willUpdateDutys = item.dutys;
                    vm.add.ifProofEnable = item.isenable + '';
                    console.log(vm.willUpdateDutys);
                }
            });
            handleUpdateDutys(vm.willUpdateDutys);
            modalOperate.modalShow(modalId);
        }
    }
    //处理校审-获取校审用户名供页面上展示
    function handleUpdateDutys(willUpdateDutys){
        angular.forEach(willUpdateDutys,function(item,index){
            if(item.type == 1){
                vm.pagefirstGrade += item.userName + '、';
                vm.prevfirstGrade += item.userName + '%';
            }else if(item.type == 2){
                vm.pagesecondGrade += item.userName + '、';
                vm.prevsecondGrade += item.userName + '%';
            }else if(item.type == 3){
                vm.pagethreeGrade += item.userName + '、';
                vm.prevthreeGrade += item.userName + '%';
            }
        });
        vm.pagefirstGrade =  vm.pagefirstGrade.slice(0,vm.pagefirstGrade.length - 1);
        vm.pagesecondGrade =  vm.pagesecondGrade.slice(0,vm.pagesecondGrade.length - 1);
        vm.pagethreeGrade =  vm.pagethreeGrade.slice(0,vm.pagethreeGrade.length - 1);
        vm.prevfirstGrade =  vm.prevfirstGrade.slice(0,vm.prevfirstGrade.length - 1);
        vm.prevsecondGrade =  vm.prevsecondGrade.slice(0,vm.prevsecondGrade.length - 1);
        vm.prevthreeGrade =  vm.prevthreeGrade.slice(0,vm.prevthreeGrade.length - 1);
    }
    //校审配置修改模态框显示-只更新一项
    vm.proofUpdateItemModalShow = function(modalId,proofItem){
        vm.pagefirstGrade = '';
        vm.pagesecondGrade = '';
        vm.pagethreeGrade = '';
        vm.proofItemId = proofItem.id;
        vm.modify.starttime = proofItem.beginTime;
        vm.modify.endtime = proofItem.endTime;
        vm.willUpdateDutys = proofItem.dutys;
        vm.add.ifProofEnable = proofItem.isenable + '';
        handleUpdateDutys(vm.willUpdateDutys);
        modalOperate.modalShow(modalId);
    }
    //校审配置删除模态框显示
    vm.proofreadDelModalShow = function(modalId){
        vm.selKeyArr = [];
        judgeIfSelData();
        if(vm.selKeyArr.length == 0){
            layer.alert('请选择一条数据进行操作');
            return;
        }
        if(vm.selKeyArr.length > 0){
            modalOperate.modalShow(modalId);
        }
    }
    //校审配置删除模态框显示-直接删除一条记录
    vm.proofDelItemModalShow = function(modalId,proofItem){
        modalOperate.modalShow(modalId);
        vm.proofItemId = proofItem.id;
    }
    //初始化设置
    function initSetting(){
        vm.add = {
            ifProofEnable : '1',
            starttime:'',
            endtime:''
        }
        vm.modify = {
            ifProofEnable : '1',
            starttime:'',
            endtime:''
        }
        vm.selProofIds = {};
        vm.proofItemId = '',
        vm.pagefirstGrade = '';
        vm.pagesecondGrade = '';
        vm.pagethreeGrade = '';
        vm.prevfirstGrade = '';
        vm.prevsecondGrade = '';
        vm.prevthreeGrade = '';
        vm.selProofUnames = {};
        vm.firstGrade = '';
        vm.secondGrade = '';
        vm.threeGrade = '';
        //存放校审信息数组
        vm.proofreadList = [];
        //校审总条数
        vm.proofList_total = 0;
        //检索标识 1:快速 2：高级
        vm.show=1;
        //默认当前页1
        vm.pagination = {
            current: 1
        };
        //默认搜索名称为空
        vm.specialSearchName = '';
        //默认每页10条
        vm.selPageRows = '10';
    }
    //初始化
    function init(){
        initSetting();
        getProofreadList(vm.pagination.current, vm.specialSearchName);
        getDutyUser();
    }
    init();
    /**
     * 快速检索
     */
    vm.searchSpecial = function (){
        getProofreadList(1, vm.specialSearchName);
    }
    /**
     * 高级检索
     */
    vm.advancedSearch = function (){
        serachList(1);
    }
    //获取校审配置列表
    function getProofreadList(pageNum,userName){
        req.post('proofreadCtro/getProofreadByQuery.do',{
            userName:userName,
            beginTime:'',
            endTime:'',
            page: pageNum,
            rows: vm.selPageRows,
            langType:window.localStorage.lang
        }).success(function(resp){
            if(resp.code == '211'){
                vm.proofreadList = resp.data;
                vm.totalPages = resp.page;
                vm.proofList_total = resp.other;
                vm.show=1;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //获取高级检索校审配置列表
    function serachList(pageNum){
        var param={};
        param={
            beginTime:vm.search.beginTime,
            endTime:vm.search.endTime,
            proofreadingOne:vm.search.proofreadingOne,
            proofreadingTwo:vm.search.proofreadingTwo,
            proofreadingThree:vm.search.proofreadingThree,
            isenable:vm.search.isenable,
            page:pageNum,
            rows:vm.selPageRows,
            langType:window.localStorage.lang

        }
        req.post('proofreadCtro/proofreadSearch.do',param).success(function(resp){
            if(resp.code == '211'){
                vm.proofreadList = resp.data;
                vm.totalPages = resp.page;
                vm.proofList_total = resp.other;
                vm.show=2;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    //查询拥有值班权限人员-编辑
    function getDutyUser(){
        req.post('proofreadCtro/getDutyUser.do',{
            dutyType: vm.dutyType,
            langType: window.localStorage.lang
        }).success(function(resp){
            if(resp.code == '211'){
                vm.dutyEditorList = resp.data;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //页数变化
    vm.pageChanged = function (pageNumber) {
        if(vm.show==1){
            getProofreadList(pageNumber,vm.specialSearchName);
        }else{
            serachList(pageNumber);
        }

    };


    //保存所选值班编辑
    vm.saveSelDutyEditor = function(modalId){
        getproofreadUname();
        modalOperate.modalHide(modalId);
    }
    //获取选中用户名
    function getproofreadUname(){
        vm.finalProofUnames = '';
        vm.finalShowProofUnames = '';
        console.log(vm.selProofUnames);
        for(var key in vm.selProofUnames){
            if(vm.selProofUnames[key]){
                vm.finalShowProofUnames += key + '、';
                vm.finalProofUnames += key + '%';
            }
        }
        vm.pageShowProofUnames = vm.finalShowProofUnames.slice(0,vm.finalShowProofUnames.length - 1);
        vm.proofUnames = vm.finalProofUnames.slice(0,vm.finalProofUnames.length - 1);
        if(vm.dutyType == 1){
            vm.pagefirstGrade = vm.pageShowProofUnames;
            vm.firstGrade = vm.proofUnames;
        }
        if(vm.dutyType == 2){
            vm.pagesecondGrade = vm.pageShowProofUnames;
            vm.secondGrade= vm.proofUnames;
        }
        if(vm.dutyType == 3){
            vm.pagethreeGrade = vm.pageShowProofUnames;
            vm.threeGrade = vm.proofUnames;
        }
        //处理修改时未选择值班编辑的情况，此时一审二审三审人员就是上次选择的人员
        if(!vm.firstGrade){
            vm.firstGrade = vm.prevfirstGrade;
        }
        if(!vm.secondGrade){
            vm.secondGrade = vm.prevsecondGrade;
        }
        if(!vm.threeGrade){
            vm.threeGrade = vm.prevthreeGrade;
        }
        console.log(vm.pageShowProofUnames);
    }

    //添加校审配置
    vm.addproofreadConfig = function(modalId){
        req_addProofreadConfig(modalId);
    }
    //添加校审配置请求
    function req_addProofreadConfig(modalId){
        if(vm.add.starttime > vm.add.endtime){
            layer.alert('开始时间不能大于结束时间!');
            return;
        }
        if(vm.add.starttime == vm.add.endtime){
            layer.alert('日期不能重叠!');
            return;
        }
        if(!vm.pagefirstGrade){
            layer.alert('请选择1级校审值班编辑!');
            return;
        }
        if(!vm.pagesecondGrade){
            layer.alert('请选择2级校审值班编辑!');
            return;
        }
        if(!vm.pagethreeGrade){
            layer.alert('请选择3级校审值班编辑!');
            return;
        }
        req.post('proofreadCtro/addProofread.do',{
            firstGrade: vm.firstGrade,
            secondGrade: vm.secondGrade,
            threeGrade: vm.threeGrade,
            isEnable: vm.add.ifProofEnable,
            bTime: vm.add.starttime,
            eTime: vm.add.endtime,
            langType: window.localStorage.lang
        }).success(function(resp){
            if(resp.code == '211'){
                getProofreadList(vm.pagination.current);
                modalOperate.modalHide(modalId);
                initSetting();
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    //判断是否选择了数据
    function judgeIfSelData(){
        vm.selKeyArr = [];
        for(var key in vm.selProofIds){
            if(vm.selProofIds[key]){
                vm.selKeyArr.push(key);
                console.log(vm.selKeyArr.length);
            }
        }
        console.log(vm.selKeyArr.length);
    }
    //获取校审配置id
    function getProofreadIds(){
        vm.finalProofIds = '';
        for(var key in vm.selProofIds){
            if(vm.selProofIds[key]){
                vm.finalProofIds += key + ',';
            }
        }
        if(vm.proofItemId){
            vm.proofIds = vm.proofItemId;
        }else{
            vm.proofIds = vm.finalProofIds.slice(0,vm.finalProofIds.length - 1);
        }
    }
    //修改校审配置
    vm.updateproofreadConfig = function(modalId){
        req_updateProofreadConfig(modalId);
    }
    //修改校审配置请求
    function req_updateProofreadConfig(modalId){
        getProofreadIds();
        getproofreadUname();
        req.post('proofreadCtro/updateProofread.do',{
            firstGrade: vm.firstGrade,
            secondGrade: vm.secondGrade,
            threeGrade: vm.threeGrade,
            isEnable: vm.add.ifProofEnable,
            pfId: vm.proofIds
        }).success(function(resp){
            if(resp.code == '211'){
                vm.pagefirstGrade = '';
                vm.pagesecondGrade = '';
                vm.pagethreeGrade = '';
                getProofreadList(vm.pagination.current);
                modalOperate.modalHide(modalId);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    //删除校审配置
    vm.delProofreadConfig = function(modalId){
        req_delProofreadConfig(modalId);
    }
    //删除校审配置请求
    function req_delProofreadConfig(modalId){
        getProofreadIds();
        req.post('proofreadCtro/delProofreadByIds.do',{
            proofreadIds: vm.proofIds
        }).success(function(resp){
            if(resp.code == '211'){
                getProofreadList(vm.pagination.current);
                modalOperate.modalHide(modalId);
                initSetting();
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

});