/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('logManageCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, $http) {
    var vm = this;

    //模态框遮罩层显示
    function maskShow() {
        $rootScope.layerIfShow = true;
    }

    //模态框遮罩层隐藏
    function maskHide() {
        $rootScope.layerIfShow = false;
    }

    //下载级别管理模态框显示
    vm.logModalShow = function (modalId) {
        $('#' + modalId).show();
        maskShow();
    };
    //下载级别管理模态框隐藏
    vm.logModalHide = function (modalId) {
        $('#' + modalId).hide();
        maskHide();
    };

    $scope.open2 = function () {
        $scope.popup2.opened = true;
    };

    function req_list(searchType,searchStr, pageNum, isDownLoad) {
        var searchUrl = '';
        var paramsObj = {
            page: pageNum,
            rows: vm.selPageRows
        };
        if (searchType == 1) {
            searchUrl = "logCtro/getLogByQuery.do";
            paramsObj['strWhere'] = searchStr;
        } else if (searchType == 2) {
            searchUrl = "logCtro/advancedSearch.do";
            paramsObj['opeIp'] = vm.search.opeIp;
            paramsObj['cpeUser'] = vm.search.cpeUser;
            paramsObj['opeContent'] = vm.search.opeContent;
            paramsObj['beginTime'] = vm.search.beginTime;
            paramsObj['endTime'] = vm.search.endTime;
            paramsObj['logtypeId'] = vm.search.logtypeId;
            paramsObj['opeType'] = vm.search.opeType;
            paramsObj['seachType'] = 1;
        }
        if (isDownLoad) {
            var time = new Date().format("yyyyMMddhhmmss");
            var fileName = "系统日志_" + time + ".xls";//文件名
            return $http({
                url: "/cnsphoto/logCtro/downLoadog.do",
                method: "POST",
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
                },
                data: $.param(paramsObj),
                responseType: 'arraybuffer'
            }).success(function (data) {
                var blob = new Blob([data], {type: "application/vnd.ms-excel"});
                var objectUrl = URL.createObjectURL(blob);
                var a = document.createElement('a');
                document.body.appendChild(a);
                a.setAttribute('style', 'display:none');
                a.setAttribute('href', objectUrl);
                a.setAttribute('download', fileName);
                a.click();
                URL.revokeObjectURL(objectUrl);
            });
        } else {
            req.post(searchUrl, paramsObj).success(function (resp) {
                if (resp.code && resp.code == 211) {
                    vm.listData = resp.data;
                    vm.totalPages = resp.page;
                    vm.logList_total = resp.other;
                    vm.pagination.current = pageNum;
                    if (searchType == 1) {
                        vm.logSearchType = 1;
                    } else if (searchType == 2) {
                        vm.logSearchType = 2;
                        vm.logModalHide('logs-search-modal');
                    }

                } else if (resp.msg != '未登录') {
                    layer.alert(resp.msg);
                }
            });
        }
    }
    //页数变化
    vm.pageChanged = function (pageNumber) {
        req_list(vm.logSearchType,vm.searchLogMsgModel,pageNumber);
    };

    /**
     * 搜索日志
     */
    vm.onSearchLogMsgClick = function () {
        req_list(1,vm.searchLogMsgModel, 1, false);
    };
    /**
     * 日志的高级检索
     */
    vm.logdvancedSearch = function () {
        req_list(2,vm.searchLogMsgModel, 1, false);
    };
    /**
     * 导出日志
     */
    vm.exportLog = function(){
        req_list(vm.logSearchType,vm.searchLogMsgModel, 1, true);
    }
    /**
     * 回车搜索
     */
    vm.onEnterSearchClick = function (e) {
        var keyCode = window.event ? e.keyCode : e.which;
        if (keyCode == 13) {
            vm.onSearchLogMsgClick();
        }
    };
    //查询日志类型信息
    function getLogTypeOption(){
        req.post("logCtro/getAllLogType.do").success(function (resp) {
            if (resp.code == '211') {
                vm.logTypeList = resp.data;
                console.log(vm.logTypeList);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    /**
     * 格式化时间
     * @param format
     * @returns {*}
     */
    Date.prototype.format =function(format)
    {
        var o = {
            "M+" : this.getMonth()+1, //month
            "d+" : this.getDate(), //day
            "h+" : this.getHours(), //hour
            "m+" : this.getMinutes(), //minute
            "s+" : this.getSeconds(), //second
            "q+" : Math.floor((this.getMonth()+3)/3), //quarter
            "S" : this.getMilliseconds() //millisecond
        }
        if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
            (this.getFullYear()+"").substr(4- RegExp.$1.length));
        for(var k in o)if(new RegExp("("+ k +")").test(format))
            format = format.replace(RegExp.$1,
                RegExp.$1.length==1? o[k] :
                    ("00"+ o[k]).substr((""+ o[k]).length));
        return format;
    }
    //初始化页面相关配置
    function initSetting(){
        //日志数组
        vm.listData = [];
        //日志总条数
        vm.logList_total = 0;
        //默认当前页1
        vm.pagination = {
            current: 1
        };
        //默认每页10条
        vm.selPageRows = '10';
        //日志类型信息
        vm.logTypeList = [];
        //检索类型
        vm.logSearchType = 1;
    }
    // 初始化
    function init() {
        initSetting();
        req_list(vm.logSearchType,"", 1);
        getLogTypeOption();
    }

    init();
});