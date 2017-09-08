/**
 * Created by Sun on 2016/12/13.
 */
adminModule.controller('mInternalRetentionCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, layerIfShow, allModalMove, modalOperate, getMyDuty, $window) {
    var vm = this;
    //模态框遮罩层显示
    function maskShow() {
        $rootScope.layerIfShow = true;
    }
    //模态框遮罩层隐藏
    function maskHide() {
        $rootScope.layerIfShow = false;
    }

    //模态框显示
    vm.projectModalShow = function (modalId) {
        $('#' + modalId).show();
        maskShow();
    };
    //模态框隐藏
    vm.projectModalHide = function (modalId) {
        $('#' + modalId).hide();
        maskHide();
    };

    //移动模态框
    vm.moveModal = function (dragDiv, tagDiv) {
        allModalMove.modalMove(dragDiv, tagDiv);
    };

    //初始化数据
    function initSetting() {
        //搜索对象
        vm.search = {
            id: '',
            title: '',
            author: '',
            place: '',
            editor: '',
            memo: '',
            fileName: '',
            beginTime: '',
            endTime: '',
            paramStr: ''
        }
        //绑定复选框的状态
        vm.selCheckback = [];
        // 默认图表排列
        vm.activeArrayTit = 1;
        //绑定复选框
        vm.selCheckback = [];
        vm.selCpCategories = [];
        //存放内部留资数组
        vm.inInformRowDataArray = [];
        //内部留资总条数
        vm.innerInfoList_total = 0;
        //默认当前页1
        vm.pagination = {
            current: 1
        };
        //中英文标记
        vm.langTypeFlag = window.localStorage.lang;
        //
        vm.internalSearchType = 0;
        //默认每页10条
        vm.selPageRows = '10';
        getMyDuty.req_getMyDuty(function(type){
            console.log(type);
            //存储角色拥有的值班级别
            if($window.localStorage['lang'] == 0) {
                if(!$window.localStorage['userDutyLevelZh']){
                    $window.localStorage['userDutyLevelZh']=JSON.stringify(type);
                }
                vm.variedtypeArr = JSON.parse($window.localStorage['userDutyLevelZh'] || '[]');
            } else if($window.localStorage['lang'] == 1){
                if(!$window.localStorage['userDutyLevelEn']){
                    $window.localStorage['userDutyLevelEn']=JSON.stringify(type);
                }
                vm.variedtypeArr = JSON.parse($window.localStorage['userDutyLevelEn'] || '[]');
            }
        })

    }
    //下载模态显示
    vm.ModalShow = function (modalId) {
        modalOperate.modalShow(modalId);
    };
    //下载模态框隐藏
    vm.ModalHide = function (modalId) {
        modalOperate.modalHide(modalId);
    };
    function init() {
        initSetting();
        getSelCpCategories(function (category) {
            angular.forEach(category, function (item, index) {
                if (item.categoryName == '新闻图片') {
                    vm.categories = item.categories;
                }
            });
        });
        getInInformData(1,vm.internalSearchType);
        hide()
    }

    /**
     * 获取稿件分类信息
     */
    function getSelCpCategories(callback) {

        req.post('classification/selCpCategories.do', {langType: window.localStorage.lang}).success(function (resp) {
            if (resp.code == '211') {
                //vm.selCpCategories = resp.data;
                var classDataArray = resp.data;
                if (classDataArray.length > 0) {
                    for (var item = 0; item < classDataArray.length; item++) {
                        var itemObject = classDataArray[item];
                        if (itemObject.categoryName == "新闻图片" || itemObject.categoryName == "专题图片") {
                            vm.selCpCategories.push(itemObject);
                        }
                    }
                }
                if (callback) callback(classDataArray);
                //var subId = vm.selCpCategories[0]['categories'][0]['id'];
                //vm.navFunc(vm.selCpCategories[0]['categoryName']);
                //vm.onShowHadPubDetailClick(subId, 0);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    /**
     * 点击展开或收缩菜单
     */
    vm.navFunc = function (name) {
        vm.navActiveMenu = name;
    };
    // 初始化
    init();
    function hide(){
        if(window.localStorage.lang==1){
            $("#gallery").hide();
            $(".check").hide();
            $("input[name='all']").hide();
            $("[name='checkbox_th']").hide();
        }
    }
    //全选
    $("input[name='all']").click(function(){
        var xz = $(this).prop("checked"); //判断全选按钮的选中状态
        $(this).prop("checked",xz);
         $(".check").prop("checked", xz);

    });
    /**
     * 选择稿件排列类型
     */
    vm.chooseArrayType = function (activeArrayTit) {
        vm.activeArrayTit = activeArrayTit;
        getInInformData(1,vm.internalSearchType);
    };

    //回车搜索
    vm.enterSearchData = function(e){
        var e = e || window.event;
        var code = e.keyCode ? e.keyCode : e.which;
        if (code == 13) {
            vm.onSearchDataClick();
        }
    }


    /**
     * 获取内部留资列表
     * @param page      第几页
     * @param showType  展示类型：0列展示，1表格展示
     */
    function getInInformData(page,searchType) {

        var paramsObj = {
            page: page,
            rows: vm.selPageRows,
            cateId: vm.category
        }
        if(searchType==1){
            paramsObj['paramStr'] = vm.search.paramStr;
        }else if(searchType==2){
            paramsObj['id'] = vm.search.id;
            paramsObj['title'] = vm.search.title;
            paramsObj['author'] = vm.search.author;
            paramsObj['place'] = vm.search.place;
            paramsObj['editor'] = vm.search.editor;
            paramsObj['memo'] = vm.search.memo;
            paramsObj['fileName'] = vm.search.fileName;
            paramsObj['beginTime'] = vm.search.beginTime;
            paramsObj['endTime'] = vm.search.endTime;
            paramsObj['langType'] = window.localStorage.lang;
        }
        req.post('groupPicCtro/getKeepGroups.do', paramsObj).success(function (resp) {
            if (resp.code == '211') {
                vm.inInformRowDataArray = resp.data;
                vm.totalPages = resp.page;
                vm.innerInfoList_total = resp.other;
                if(searchType==1){
                    vm.internalSearchType = 1;
                }else if(searchType==2){
                    vm.internalSearchType = 2;
                    vm.projectModalHide('internalRetentionModalId');
                }
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
  vm.downloadGroupPic = function(type){
    var id_array=new Array();
    if( vm.activeArrayTit==1){
        $('input[name="check"]:checked').each(function(){
            id_array.push($(this).val());//向数组中添加元素
        });
    }else{
        $('input[name="checked"]:checked').each(function(){
            id_array.push($(this).val());//向数组中添加元素
        });
    }
    var idstr=id_array.join(',');//将数组元素连接起来以构建一个字符串
    if(id_array.length==0){
        layer.alert("请选择推送的稿件");
        return;
        }
        req.post('enSign/signFigureGallery.do', {
            columnId:194,
            groupId:idstr,
            langType: 0
        }).success(function (resp) {
            if (resp.code == '211') {
                layer.alert("推送成功");
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    var groupIds=id_array.join(',');//将数组元素连接起来以构建一个字符串
      if(id_array.length==0){
          layer.alert("请选择下载的稿件");
            return;
}
    document.location = "/photo/enGroupPicDown/downGrouplePic.do?groupIds=" + groupIds+"&type="+type+"&langType="+vm.langTypeFlag;
    modalOperate.modalHide("internalRetention-type-modal");

}

    //页数变化
    vm.pageChanged = function (pageNumber) {
        getInInformData(pageNumber,vm.internalSearchType);
    };

    /**
     * 点击展示已发布分类详情
     */
    vm.hadPubPosition = -1;
    vm.onShowHadPubDetailClick = function (itemId, position) {
        vm.hadPubPosition = position;
        vm.category=itemId;
        // getSignGroups(1, itemId, 1, 0, false);
        // getSignGroups(1, itemId, 1, 1, false);
        getInInformData(1,vm.internalSearchType);
    };

    //简单检索
    vm.onSearchDataClick = function () {
        getInInformData(1,1);
    };

    /**
     * 点击高级搜索
     */
    vm.onAdvancedSearchClick = function () {
        getInInformData(1,2);
    };

});