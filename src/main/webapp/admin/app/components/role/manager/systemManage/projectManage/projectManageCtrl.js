/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('projectManageCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, Upload, modalOperate) {
    var vm = this;

    //模态框移动
    $(".modal-dialog").draggable({
        handle: ".modal-header"
    });

    //高级检索模态显示
    vm.ModalShow = function (modalId) {
        modalOperate.modalShow(modalId);
    };
    //高级检索模态框隐藏
    vm.ModalHide = function (modalId) {
        modalOperate.modalHide(modalId);
    };

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
        //方法标识 1：检索2：高级检索
        vm.show=1;
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

    /**
     * 获取专题表格数据
     */
    function getSpecialTableData(page, searchname) {
        if (searchname != undefined && searchname != '') {
            var url = "topic/searchTopic.do";
        } else {
            url = "topic/showTopic.do";
        }
        req.post(url, {
            page: page,
            rows: vm.selPageRows,
            name: searchname,
            langType:window.localStorage.lang
        }).success(function (resp) {
        	console.info(resp.data)
            if (resp.code == '211') {
                vm.SpecialArray = resp.data;
                vm.totalPages = resp.page;
                vm.specialList_total = resp.other;
                vm.show=1
            } else {
                console.log(resp.msg);
            }
        });
    }

    //页数变化
    vm.pageChanged = function (pageNumber) {
        if(vm.show==2){
            topicSearch(pageNumber);
        }else{
            getSpecialTableData(pageNumber,vm.specialSearchName);
        }

    };


    /**
     * 搜索
     * */
    vm.searchSpecial = function () {
        getSpecialTableData(1, vm.specialSearchName);
    };
    /**
     * 高级检索
     */
    vm.advancedSearch= function () {
        topicSearch(1);
    };
    /**
     * 回车搜索
     */
    vm.onEnterSearchClick = function (e) {
        var keyCode = window.event ? e.keyCode : e.which;
        if (keyCode == 13) {
            vm.searchSpecial();
        }
    };

    /**
     * 处理全选
     */
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
    /**
     * 监听每一个选项的改变
     */
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
            vm.delSpecial();
        } else {
            layer.alert("请选择要删除的专题");
        }
    }

    /**
     * 删除专题
     */
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

    /**
     * 添加专题
     */
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
                layer.alert('请输入以http://或者https://开头的专题链接');
                return;
            }
        }
        if (!vm.superText) {
            layer.alert('请输入专题说明');
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
                wmFile: vm.file,
                langType:window.localStorage.lang
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
    /**
     * 展示编辑框
     */

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
    /**
     * 编辑专题
     */
    vm.updateSpecialSave = function () {
        vm.superText1 = ue1.getContent();
        if (!vm.specialName1) {
            layer.alert('请输入专题名称');
            return;
        }
        if (vm.handleType1 == 1){
            if (!vm.specialLink1) {
                layer.alert('请输入以http://或者https://开头的专题链接');
                return;
            }
        }
        if (!vm.superText1) {
            layer.alert('请输入专题说明');
            return;
        }
        Upload.upload({
            url: '/photo/topic/editTopic.do',
            data: {
                id: vm.updateEditId,
                name: vm.specialName1,
                type: vm.handleType1,
                url: vm.specialLink1,
                qianfa: vm.signShow1,
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
    /**
     * 点击专题名或者专题图片进行前台预览
     * type 操作类型
     * _url 外部链接
     * url 人工签发专题ip地址
     * id 专题id
     * */
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

    /**
     * 高级检索
     */
      function topicSearch(page){
         var params = {};
         params['beginTime']=vm.search.beginTime;
         params['endTime']=vm.search.endTime;
         params['createUser']=vm.search.createUser;
         params['signShow']=vm.search.signShow;
         params['siteShow']=vm.search.siteShow;
         params['topicName']=vm.search.topicName;
         params['page']=page;
         params['rows']=vm.selPageRows;
         params['langType']=window.localStorage.lang;

         req.post('topic/getTopicByQuery.do', params
         ).success(function (resp) {
             if (resp.code == '211') {
                 vm.SpecialArray = resp.data;
                 vm.totalPages = resp.page;
                 vm.specialList_total = resp.other;
                 modalOperate.modalHide('user-search-modal');
                 vm.show=2;
             }else if(resp.msg != '未登录'){
                 layer.alert(resp.msg);
             }

         });
  }

});