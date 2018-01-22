/**
 * Created by Sun on 2016/12/2.
 */
adminModule.controller('contributeStatisticsCtrl', function ($scope, $cookies, req, md5, $state, $rootScope) {
    var vm = this;

    vm.tab = 1; //默认显示图表统计
    vm.statisticsTypeClick = function (statisticsType) {
        switch (parseInt(statisticsType)) {
            case 1 :
                vm.tab = 1;  //图表统计
                break;
            case 2:
                vm.tab = 2;  //列表统计
                break;
            case 3:
                vm.tab = 3;  //地域-统计详情
                break;
            case 4:
                vm.tab = 4;  //类别-统计详情
                break;
        }
    };

    /**
     * 获取投稿统计表格数据
     */
    function getContributorTableData(searchType, searchTitle, page, type) {
        var searchUrl = "";
        var paramsObj = {
            page: page,
            rows: 10
        };
        if (!searchTitle) {
            searchType = false;
        }
        if (searchType) {
            searchUrl = "groupStatistical/searchGroupStatistical.do";
            paramsObj['author'] = searchTitle;
        } else {
            searchUrl = "groupStatistical/showAllGroupStatistical.do";
        }
        req.post(searchUrl, paramsObj).success(function (resp) {
            if (resp.code == '211') {
                vm.contributorTableArray = resp.data;
                var contributorPageNum = resp.page;
                if (type == 1 && contributorPageNum) {
                    // 处理分页
                    $.jqPaginator('#paginationCbId', {
                        totalPages: contributorPageNum,
                        visiblePages: 4,
                        currentPage: 1,
                        onPageChange: function (mineNum, pageType) {
                            if (pageType === 'change') {
                                getContributorTableData(searchType, searchTitle, mineNum, 0);
                            }
                        }
                    });
                }
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    /**
     * 获取投稿统计地域表格数据
     */
    function getConAreaTableData(searchType, searchTitle, page, type) {
        var paramsObj = {
            page: page,
            rows: 10
        };
        if (!searchTitle) {
            searchType = false;
        }
        if (searchType) {
            paramsObj['place'] = searchTitle;
        }
        req.post("groupStatistical/GroupStatisticalForPlaceList.do", paramsObj).success(function (resp) {
            if (resp.code == '211') {
                vm.conAreaTableArray = resp.data;
                var conAreaPageNum = resp.page;
                if (type == 1 && conAreaPageNum) {
                    // 处理分页
                    $.jqPaginator('#paginationCbAreaId', {
                        totalPages: conAreaPageNum,
                        visiblePages: 4,
                        currentPage: 1,
                        onPageChange: function (mineNum, pageType) {
                            if (pageType === 'change') {
                                getConAreaTableData(searchType, searchTitle, mineNum, 0);
                            }
                        }
                    });
                }
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    /**
     * 获取投稿统计类别表格数据
     */
    function getConTypeTableData(searchType, searchTitle, page, type) {
        var paramsObj = {
            page: page,
            rows: 10
        };
        if (!searchTitle) {
            searchType = false;
        }
        if (searchType) {
            paramsObj['categaryName'] = searchTitle;
        }
        req.post("groupStatistical/GroupStatisticalForTypeList.do", paramsObj).success(function (resp) {
            if (resp.code == '211') {
                vm.conTypeTableArray = resp.data;
                var conTypePageNum = resp.page;
                if (type == 1 && conTypePageNum) {
                    // 处理分页
                    $.jqPaginator('#paginationCbTypeId', {
                        totalPages: conTypePageNum,
                        visiblePages: 4,
                        currentPage: 1,
                        onPageChange: function (mineNum, pageType) {
                            if (pageType === 'change') {
                                getConTypeTableData(searchType, searchTitle, mineNum, 0);
                            }
                        }
                    });
                }
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }


    //回车查询
    vm.enterQuery = function (e) {
        var e = e || window.event;
        var code = e.keyCode ? e.keyCode : e.which;
        if (code == 13) {
            vm.onSearchContributorClick();
        }
    };

    /**
     * 点击搜索
     */
    vm.onSearchContributorClick = function () {
        getContributorTableData(true, vm.contributorSearchModel, 1, 1);
    };

    /**
     * 点击搜索地域
     */
    vm.onSearchConAreaClick = function () {
        getConAreaTableData(true, vm.conAreaSearchModel, 1, 1);
    };

    /**
     * 点击搜索类别
     */
    vm.onSearchConTypeClick = function () {
        getConTypeTableData(true, vm.conTypeSearchModel, 1, 1);
    };

    /**
     * 初始化投稿统计-地域
     */
    function initConArea() {
        vm.xConAreaArray = [];
        vm.yConAreaArray = [];
    }

    /**
     * 获取投稿统计-地域数据
     */
    initConArea();
    function getConAreaData() {
        req.post("groupStatistical/GroupStatisticalForPlace.do", {}).success(function (resp) {
            if (resp.code == '211') {
                initConArea();
                vm.conAreaObj = resp.data;
                if (vm.conAreaObj) {
                    for (var area in vm.conAreaObj) {
                        vm.xConAreaArray.push(area);
                        vm.yConAreaArray.push(vm.conAreaObj[area]);
                    }
                }
                canvasConBar('#divConAreaBarId', '投稿统计-地域', vm.xConAreaArray, vm.yConAreaArray);
            } else {
                initConArea();
                canvasConBar('#divConAreaBarId', '投稿统计-地域', vm.xConAreaArray, vm.yConAreaArray);
            }
        });
    }

    /**
     * 初始化投稿统计-类别
     */
    function initConType() {
        vm.xConTypeArray = [];
        vm.yConTypeArray = [];
    }

    /**
     * 获取投稿统计-类别数据
     */
    initConType();
    function getConTypeData() {
        req.post("groupStatistical/GroupStatisticalForType.do", {}).success(function (resp) {
            if (resp.code == '211') {
                initConType();
                vm.conTypeObj = resp.data;
                if (vm.conTypeObj) {
                    for (var type in vm.conTypeObj) {
                        vm.xConTypeArray.push(type);
                        vm.yConTypeArray.push(vm.conTypeObj[type]);
                    }
                }
                canvasConBar('#divConTypeBarId', '投稿统计-类别', vm.xConTypeArray, vm.yConTypeArray);
            } else {
                initConType();
                canvasConBar('#divConTypeBarId', '投稿统计-类别', vm.xConTypeArray, vm.yConTypeArray);
            }
        });
    }

    /**
     * 投稿统计柱状图绘制
     */
    function canvasConBar(showId, titleText, xArray, yArray) {
        var conChart = echarts.init($(showId)[0]);
        var conOption = {
            title: {
                text: titleText,
                show: false
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow'
                }
            },
            grid: {
                top: '10%',
                left: '8%',
                right: '4%',
                bottom: '4%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    data: xArray,
                    axisLine: {
                        show: false
                    },
                    axisTick: {
                        show: false
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    offset: 60,
                    splitNumber: 6,
                    axisLine: {
                        show: false
                    },
                    axisTick: {
                        show: false
                    },
                    axisLabel: {
                        inside: true
                    },
                    splitLine: {
                        lineStyle: {
                            color: '#F0F0F0'
                        }
                    }
                }
            ],
            series: [
                {
                    name: "统计数",
                    type: 'bar',
                    barWidth: 14,
                    itemStyle: {
                        normal: {
                            color: "#15ACDA",
                            barBorderRadius: [25, 25, 0, 0]
                        }
                    },
                    data: yArray
                }
            ]
        };
        conChart.setOption(conOption);
    }

    // 初始绘制
    canvasConBar('#divConAreaBarId', '投稿统计-地域', vm.xConAreaArray, vm.yConAreaArray);
    canvasConBar('#divConTypeBarId', '投稿统计-类别', vm.xConTypeArray, vm.yConTypeArray);

    // 请求
    getContributorTableData(false, "", 1, 1);
    getConAreaTableData(false, "", 1, 1);
    getConTypeTableData(false, "", 1, 1);
    getConAreaData();
    getConTypeData();
});