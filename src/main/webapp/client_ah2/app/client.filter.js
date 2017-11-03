angular.module('client.filter', [])
    .filter('trustHtml', function ($sce) {
        return function (input) {
            return $sce.trustAsHtml(input);
        }
    })
    //需求管理-列表状态
    .filter('demandStatusFilter', function () {
        return function (input) {
            var result = "";
            switch (parseInt(input)) {
                case 0:
                    result = "未审核";
                    break;
                case 1:
                    result = "审核通过";
                    break;
                case 2:
                    result = "审核未通过";
                    break;
                case 3:
                    result = "保存文档";
                    break;
                case 4:
                    result = "审核通过需求关闭";
                    break;
                case 5:
                    result = "审核未通过需求关闭";
                    break;
            }
            return result;
        }
    })
    //生成订单状态
    .filter('orderStatusFilter', function () {
        return function (input) {
            var result = "";
            switch (parseInt(input)) {
                case 0:
                    result = "待付款";
                    break;
                case 1:
                    result = "取消付款";
                    break;
                case 2:
                    result = "已付款";
                    break;
            }
            return result;
        }
    })
    //用户结算方式
    .filter('payWayFilter', function () {
        return function (input) {
            var result = "";
            switch (parseInt(input)) {
                case 0:
                    result = "充值";
                    break;
                case 1:
                    result = "合同";
                    break;
                case 2:
                    result = "充值与合同";
                    break;
            }
            return result;
        }
    })
    //分页处理
    .filter('pageHandle', function () {
        return function (input) {
            var result = "";
            if(!parseInt(input)) {
                result = 1;
            }else {
                result = parseInt(input);
            }
            return result;
        }
    })
    //图片类型
    .filter('picTypeFilter', function () {
        return function (input) {
            var result = "";
            switch (parseInt(input)) {
                case 0:
                    result = "图片";
                    break;
                case 1:
                    result = "图文";
                    break;
            }
            return result;
        }
    })
    //余额下载限制类型：0每天，1每月，2每年,3总共
    .filter('balanceLimitTypeFilter',function(){
        return function(input){
            var result = "";
            switch (parseInt(input)) {
                case 0:
                    result = "每天";
                    break;
                case 1:
                    result = "每月";
                    break;
                case 2:
                    result = "每年";
                    break;
                case 3:
                    result = "总共";
                    break;
            }
            return result;
        }
    })
    //当前状态："0已开通，1正申请，2已删除，3已禁用"
    .filter('userStatusFilter',function(){
        return function(input){
            var result = "";
            switch (parseInt(input)) {
                case 0:
                    result = "已开通";
                    break;
                case 1:
                    result = "正申请";
                    break;
                case 2:
                    result = "已删除";
                    break;
                case 3:
                    result = "已禁用";
                    break;
            }
            return result;
        }
    })


