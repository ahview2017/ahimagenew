/**
 * Created by Sun on 2016/12/14.
 */
adminModule.controller('mInternalRetentionDetailCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, $stateParams, modalOperate) {
    var vm = this;
    //中英文标记
    vm.langTypeFlag = window.localStorage.lang;
    vm.inInformId = $stateParams.id;
    vm.dtType = $stateParams.dtType;
    // 点击管理详情div是否展示的标识
    vm.mangeOperateFlag = false;
    //模态框遮罩层显示
    function maskShow() {
        $rootScope.layerIfShow = true;
    }

    //模态框遮罩层隐藏
    function maskHide() {
        $rootScope.layerIfShow = false;
    }

    //专题模态框显示
    vm.projectModalShow = function (modalId) {
        $('#' + modalId).show();
        maskShow();
    };
    //专题模态框隐藏
    vm.projectModalHide = function (modalId) {
        $('#' + modalId).hide();
        maskHide();
    };
    //下载模态显示
    vm.ModalShow = function (modalId) {
        modalOperate.modalShow(modalId);
    };
    //下载模态框隐藏
    vm.ModalHide = function (modalId) {
        modalOperate.modalHide(modalId);
    };

    // 点击管理控制下面详情div切换
    vm.toggleMangeOperateCon = function() {
        vm.mangeOperateFlag = !vm.mangeOperateFlag;
    }

    //全选
    $(".all").click(function(){
        var xz = $(this).prop("checked"); //判断全选按钮的选中状态
        $(".check").prop("checked", xz);
    });
    /**
     * 获取内部留资详情
     */
    function getDataInInformDetailData(groupId) {
        req.post('groupPicCtro/getGroupPics.do', {
            groupId: groupId
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.inInformObj = resp.data;
                var cateArray = vm.inInformObj['cates'];
                vm.inInformType = "";
                for (var i = 0; i < cateArray.length; i++) {
                    var cateObj = cateArray[i];
                    vm.inInformType += cateObj['categoryName'] + " ";
                }
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    /**
     * 点击取回
     */
    vm.onGetBackClick = function () {
        req.post("groupPicCtro/goBackGroups.do", {
            groupIds: vm.inInformId
        }).success(function (resp) {
            if (resp.code == '211' && resp.data != []) {
                vm.projectModalHide('getBackModalId');
                layer.alert("处理取回成功");
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    };

    // 请求
    getDataInInformDetailData(vm.inInformId);
    /**
     * 下载
     * @param type 0：只图片 1：图片加说明
     */
    vm.downLoadPic = function (type){
        var id_array=new Array();
        $('input[name="check"]:checked').each(function(){
            id_array.push($(this).val());//向数组中添加元素
        });
        var picIds=id_array.join(',');//将数组元素连接起来以构建一个字符串
        if(id_array.length==0){
            layer.alert("请选择图片");
            return;
        }
        document.location = "/cnsphoto/enGroupPicDown/downSinglePic.do?picIds=" + picIds+"&type="+type+"&langType="+vm.langTypeFlag;
        modalOperate.modalHide('detail-type-modal');
    }
});