angular.module('admin.filter', [])
    //广告位对应位置-广告位管理
    .filter('adPositionFilter', function () {
        return function (input) {
            var result = "";
            if (input) {
                result = "第" + input + "广告位";
            }
            return result;
        }
    })
    //是否开启校审-校审配置
    .filter('proofIfableFiter', function () {
        return function (input) {
            if (input == '0') {
                return '否';
            } else if (input == '1') {
                return '是';
            }
        }
    })
    //是否有水印-下载级别管理
    .filter('IfHasWaterMarkFilter', function () {
        return function (input) {
            if (input == '0') {
                return '是';
            } else if (input == '1') {
                return '否';
            }
        }
    })
    //稿件状态
    .filter('grouppStatusFilter', function () {
        return function (input) {
            if (input == '0') {
                return '保存';
            } else if (input == '1') {
                return '提交（待一审）';
            } else if (input == '2') {
                return '待二审';
            } else if (input == '3') {
                return '待三审';
            } else if (input == '4') {
                return '已发布（已签发）';
            } else if (input == '5') {
                return '内部留资';
            } else if (input == '6') {
                return '撤稿';
            } else if (input == '7') {
                return '被退稿件';
            }
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
    //日志管理-操作类型
    .filter('logOperateTypeFilter', function () {
        return function (input) {
            var result = "";
            switch (parseInt(input)) {
                case 0:
                    result = "增";
                    break;
                case 1:
                    result = "删";
                    break;
                case 2:
                    result = "改";
                    break;
            }
            return result;
        }
    })
    //图片管理-图片类型
    .filter('imgManageImgTypeFilter', function () {
        return function (input) {
            var result = "";
            switch (parseInt(input)) {
                case 0:
                    result = "普通图";
                    break;
                case 1:
                    result = "活动图";
                    break;
                case 2:
                    result = "老照片";
                    break;
                case 3:
                    result = "ta图";
                    break;
                case 4:
                    result = "fa图";
                    break;
            }
            return result;
        }
    })

    //我的稿件-图片类型
    .filter('draftImgTypeFilter', function () {
        return function (input) {
            var result = "";
            switch (parseInt(input)) {
                case 0:
                    result = "普通图";
                    break;
                case 1:
                    result = "活动图";
                    break;
                case 2:
                    result = "老照片";
                    break;
                case 3:
                    result = "ta图";
                    break;
                case 4:
                    result = "fa图";
                    break;
            }
            return result;
        }
    })

    //消息管理-状态类型
    .filter('msgManageStatusFilter', function () {
        return function (input) {
            var result = "";
            switch (parseInt(input)) {
                case 0:
                    result = "不发布";
                    break;
                case 1:
                    result = "发布";
                    break;
            }
            return result;
        }
    })

    //消息管理-状态类型
    .filter('msgManageTipIdFilter', function () {
        return function (input) {
            var result = "";
            switch (parseInt(input)) {
                case 0:
                    result = "不置顶";
                    break;
                case 1:
                    result = "置顶";
                    break;
            }
            return result;
        }
    })

    //广告位管理-返回宽高
    .filter('adWidthHeightFilter', function () {
        return function (input, height) {
            if (input == null || input === 0 || typeof(input) == "undefined") {
                input = 0;
            }
            if (height == null || height === 0 || typeof(height) == "undefined") {
                height = 0;
            }
            return input + "*" + height;
        }
    })

    //消息管理-短信状态类型
    .filter('phoneMsgStatusFilter', function () {
        return function (input) {
            var result = "";
            switch (parseInt(input)) {
                case 0:
                    result = "发送成功";
                    break;
                case 1:
                    result = "保存成功";
                    break;
                case 2:
                    result = "发送失败";
                    break;
            }
            return result;
        }
    })

    //消息管理-站内信操作状态
    .filter('msgWorkStatusFilter', function () {
        return function (input) {
            if (parseInt(input) == 0) {
                return '已读';
            } else if (parseInt(input) == 1) {
                return '未读';
            }
        }
    })

    //是否为封面图
    .filter('isCoverFilter', function () {
        return function (input) {
            var result = "";
            switch (parseInt(input)) {
                case 0:
                    result = "辅图";
                    break;
                case 1:
                    result = "主图";
                    break;
            }
            return result;
        }
    })

    //消息管理-接受状态
    .filter('msgMailReceiverFilter', function () {
        return function (input, receiver) {
            var flag1 = -1;
            var flag2 = -1;
            if (input == null || input == "" || typeof(input) == "undefined") {
                flag1 = 0;
            }
            if (receiver == null || receiver == "" || typeof(receiver) == "undefined") {
                flag2 = 0;
            }
            if (flag1 == 0 && flag2 != 0) {
                return receiver;
            } else if (flag1 != 0 && flag2 == 0) {
                return input;
            }
        }
    })

    //校审级别
    .filter('dtTypeFilter', function () {
        return function (input) {
            var result = "";
            switch (parseInt(input)) {
                case 1:
                    result = "一级校审";
                    break;
                case 2:
                    result = "二级校审";
                    break;
                case 3:
                    result = "三级校审";
                    break;
            }
            return result;
        }
    })
    //专题网站
    .filter('specialSite', function () {
        return function (input) {
            var result = '';
            switch (parseInt(input)) {
                case 0:
                    result = "否";
                    break;
                case 1:
                    result = "是";
                    break;
            }
            return result;
        }
    })
    //专题签发状态
    .filter('signIssue', function () {
        return function (input) {
            var result = "";
            switch (parseInt(input)) {
                case 0:
                    result = "否";
                    break;
                case 1:
                    result = "是";
                    break;
            }
            return result;
        }
    })
    //栏目模板
    .filter('columnStyle', function () {
        return function (input) {
            var result = "";
            switch (parseInt(input)) {
                case 0:
                    result = "无";
                    break;
                case 1:
                    result = "一图一文";
                    break;
                case 2:
                    result = "轮播图一";
                    break;
                case 3:
                    result = "轮播图二";
                    break;
                case 4:
                    result = "4展";
                    break;
                case 5:
                    result = "5展";
                    break;
                case 6:
                    result = "6展";
                    break;
                case 8:
                    result = "8展";
                    break;
                case 10:
                    result = "10展";
                    break;
            }
            return result;
        }
    })
    //是否有子栏目
    .filter('sonColumnStyle', function () {
        return function (input) {
            var result = "";
            if (parseInt(input) === 0) {
                result = "是";
            } else {
                result = "否";
            }
            return result;
        }
    })
    //子栏目模板
    .filter('sonColumnDis', function () {
        return function (input) {
            var result = '';
            switch (parseInt(input)) {
                case 4:
                    result = "4展";
                    break;
                case 5:
                    result = "5展";
                    break;
                case 6:
                    result = "6展";
                    break;
                case 8:
                    result = "8展";
                    break;
                case 10:
                    result = "10展";
                    break;
            }
            return result;
        }
    })
    //稿件编辑状态 0否，1是
    .filter('isLockFilter', function () {
        return function (input) {
            if (parseInt(input) == 0) {
                return '否';
            } else if (parseInt(input) == 1) {
                return '是';
            } else {
                return '否';
            }
        }
    })

    //用户管理-用户状态
    .filter('userManageTypeFilter', function () {
        return function (input) {
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

    //用户管理-用户类型
    .filter('userShowArrayTypeFilter', function () {
        return function (input, isFlag) {
            var result = "";
            if (input != null && input.length > 0) {
                for (var i = 0; i < input.length; i++) {
                    result += (input[i]['roleName'] + ",");
                }
                result = result.substr(0, result.length - 1);
                if (isFlag && result.length > 9) {
                    result = result.substr(0, 9) + "...";
                }
            }
            return result;
        }
    })

    //稿件-操作记录
    .filter('flowTypeFilter', function () {
        return function (input) {
            var result = "";
            switch (parseInt(input)) {
                case -1:
                    result = "保存稿件";
                    break;
                case 0:
                    result = "发稿提交";
                    break;
                case 1:
                    result = "一审提交";
                    break;
                case 2:
                    result = "二审提交";
                    break;
                case 3:
                    result = "三审签发";
                    break;
                case 4:
                    result = "补签";
                    break;
                case 5:
                    result = "内部留资";
                    break;
                case 6:
                    result = "撤稿";
                    break;
                case 7:
                    result = "上架";
                    break;
                case 8:
                    result = "删除";
                    break;
                case 9:
                    result = "推送";
                    break;
                case 10:
                    result = "编辑";
                    break;
                case 11:
                    result = "一审退稿";
                    break;
                case 12:
                    result = "二审退稿";
                    break;
                case 13:
                    result = "三审退稿";
                    break;
                case 14:
                    result = "取回";
                    break;
                case 15:
                    result = "稿件合并";
                    break;
                case 16:
                    result = "强制解锁";
                    break;
                case 18:
                    result = "还原";
                    break;
            }
            return result;
        }
    })

    //稿件属性
    .filter('manuscriptFilter', function () {
        return function (input) {
            var result = "";
            switch (parseInt(input)) {
                case 0:
                    result = "新闻图片";
                    break;
                case 1:
                    result = "专题图片";
                    break;
            }
            return result;
        }
    })
    //是否限价
    .filter('priceTypeFilter', function () {
        return function (input) {
            var result = "";
            switch (parseInt(input)) {
                case 0:
                    result = "不定价";
                    break;
                case 1:
                    result = "定价";
                    break;
                case 2:
                    result = "不出售";
                    break;
            }
            return result;
        }
    })
    //识别html格式
    .filter('trustHtml', function ($sce) {
        return function (input) {
            return $sce.trustAsHtml(input);
        }
    })
    //处理广告位文件上传提示
    .filter('adFileUploadTipFilter', function () {
        return function (input, length, tip) {
            if (!input) {
                return tip;
            } else {
                if (input.length > length) {
                    return input.substr(0, length) + "...";
                } else {
                    return input;
                }
            }
        }
    })
    //过滤是否启用敏感词
    .filter('sensitiveFilter', function () {
        return function (input) {
            var result = "";
            switch (parseInt(input)) {
                case 0:
                    result = "启用";
                    break;
                case 1:
                    result = "禁用";
                    break;
            }
            return result;
        }
    })

    //订单管理-价格状态
    .filter('omPriceStateFilter', function () {
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

    //权限分类
    .filter('rightSortsFilter', function () {
        return function (input) {
            var result = "";
            switch (parseInt(input)) {
                case 1:
                    result = "管理功能";
                    break;
                case 2:
                    result = "稿件编辑";
                    break;
                case 3:
                    result = "数据统计";
                    break;
            }
            return result;
        }
    })

    //横向导航权限图标过滤
    .filter('rightIconUrlFilter', function () {
        return function (input, rightId) {
            var result = "";
            switch (parseInt(rightId)) {
                case 123:
                    result = "admin/assets/img/role/zxs_ld_but_picb_03.png";
                    break;
                case 245:
                    result = "admin/assets/img/role/zxs_ld_but_picb_05.png";
                    break;
                case 146:
                    result = "admin/assets/img/role/zxs_ld_but_picb_07.png";
                    break;
                case 276:
                    result = "admin/assets/img/role/zxs_ld_but_picb_09.png";
                    break;
                case 210:
                    result = "admin/assets/img/role/zxs_ld_but_picb_11.png";
                    break;
                case 266:
                    result = "admin/assets/img/role/zxs_ld_but_pica_15.png";
                    break;
                case 110:
                    result = "admin/assets/img/role/zxs_ld_but_pica_03.png";
                    break;
                case 113:
                    result = "admin/assets/img/role/zxs_ld_but_pica_05.png";
                    break;
                case 121:
                    result = "admin/assets/img/role/zxs_ld_but_pica_07.png";
                    break;
                case 270:
                    result = "admin/assets/img/role/zxs_ld_but_pica_07.png";
                    break;
                case 65:
                    result = "admin/assets/img/role/zxs_ld_but_pica_15.png";
                    break;
            }
            return result;
        }
    })
    //文件大小的单位（Bytes、Kb、Mb、Gb、Tb）
    .filter('Filesize', function () {
        return function (size) {
            if (isNaN(size))
                size = 0;

            if (size < 1024)
                return size + ' Bytes';

            size /= 1024;

            if (size < 1024)
                return size.toFixed(2) + ' Kb';

            size /= 1024;

            if (size < 1024)
                return size.toFixed(2) + ' Mb';

            size /= 1024;

            if (size < 1024)
                return size.toFixed(2) + ' Gb';

            size /= 1024;

            return size.toFixed(2) + ' Tb';
        };
    })
