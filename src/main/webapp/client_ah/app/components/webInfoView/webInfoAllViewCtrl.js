/**
 * 查询系统公告全部数据
 */
clientModule.controller('webInfoAllViewCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, layerIfShow) {
    var vm = this;

    /**
     * 获取网站公告所有数据
     */
    function getWebPublishAllData(page, type) {
        req.post('notice/showToHomePage.do', {
            page: page,
            rows: 10
        }).success(function (resp) {
            if (resp.code == '211' && resp.data != [] && resp.data.length > 0) {
                vm.webPublishAllArray = resp.data;
                var wpAllPageNum = resp.page;
                if (type == 1) {
                    // 处理分页
                    $.jqPaginator('#paginationWpAllId', {
                        totalPages: wpAllPageNum,
                        visiblePages: 4,
                        currentPage: 1,
                        onPageChange: function (mineNum, pageType) {
                            if (pageType === 'change') {
                                getWebPublishAllData(mineNum, 0);
                            }
                        }
                    });
                }
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    // 请求
    getWebPublishAllData(1, 1);
});