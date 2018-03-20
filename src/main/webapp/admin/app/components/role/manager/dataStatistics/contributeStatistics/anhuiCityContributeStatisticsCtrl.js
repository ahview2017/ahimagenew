/**
 * Created by Sun on 2016/12/2.
 */
adminModule.controller('anhuiCityContributeStatisticsCtrl', function ($scope, $cookies, req, md5, $state, $rootScope) {
    var vm = this;
    //初始化相关配置
	function initSetting() {
		getContributorTableData(1,1);
	}
    
    /**
     * 获取投稿统计表格数据
     */
    function getContributorTableData(page,type) {
        var searchUrl = "groupStatistical/groupStatisticalForAnHuiCityList.do";
        var paramsObj = {
            page: page,
            rows: 10
        };
        if (vm.place) {
            paramsObj['place'] = vm.place;
        } 
        if(vm.orderByCase){
        	paramsObj['orderType'] = vm.orderByCase;
        }
        if((vm.startTime&&!vm.endTime)||(!vm.startTime&&vm.endTime)){
        	layer.alert("开始时间和结束时间都不能为空！");
        	layer.close(vm.loadUpMs);
        	return;
        }else if(vm.startTime&&vm.endTime){
        	paramsObj['startTime'] = vm.startTime;
        	paramsObj['endTime'] = vm.endTime;
        }
        req.post(searchUrl, paramsObj).success(function (resp) {
            if (resp.code == '211') {
            	layer.close(vm.loadUpMs);
                vm.contributorTableArray = resp.data;
                var contributorPageNum = resp.page;
                if (type == 1 &&contributorPageNum) {
                    // 处理分页
                    $.jqPaginator('#paginationCbId', {
                        totalPages: contributorPageNum,
                        visiblePages: 4,
                        currentPage: 1,
                        onPageChange: function (mineNum, pageType) {
                            if (pageType === 'change') {
                            	vm.loadUpMs = layer.load(1);
                                getContributorTableData(mineNum,0);
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
    vm.search = function () {
    	vm.loadUpMs = layer.load(1);
        getContributorTableData(1, 1);
    };

    
});