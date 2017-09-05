/**
 * Created by Sun on 2017/1/21.
 */
clientModule.controller('releaseNeedCtrl',function($scope,$cookies,req,md5,$state,$rootScope){
    var vm = this;
    //添加需求
    vm.addNeed = function(){
        validAddParams(function(){
            req_add();
        });
    }
    //添加需求前参数验证
    function validAddParams(callback){
        if(!vm.tempNeedData.desc){
            layer.alert('请填写需求说明');
            return;
        }
        if(vm.tempNeedData.desc  && vm.tempNeedData.desc.length > 2000){
            layer.alert('需求说明须少于2000字');
            return;
        }
        if(!vm.tempNeedData.count){
            layer.alert('请填写购买张数');
            return;
        }
        if(!(/\d/.test(vm.tempNeedData.count))){
            layer.alert('购买张数为整数');
            return;
        }
        if(!vm.tempNeedData.userDesc){
            layer.alert('请填写图片用途');
            return;
        }
        if(vm.tempNeedData.userDesc  && vm.tempNeedData.userDesc.length > 250){
            layer.alert('图片用途须少于250字');
            return;
        }
        if(!vm.tempNeedData.kanzaimeijian){
            layer.alert('请填写刊载媒介');
            return;
        }
        if(vm.tempNeedData.kanzaimeijian  && vm.tempNeedData.kanzaimeijian.length > 250){
            layer.alert('刊载媒介须少于250字');
            return;
        }
        if(!vm.tempNeedData.price){
            layer.alert('请填写欲购价格');
            return;
        }
        if(!(/\d/.test(vm.tempNeedData.price))){
            layer.alert('欲购价格为整数');
            return;
        }

        if(!vm.tempNeedData.Starttime){
            layer.alert('请填写使用时间的开始时间');
            return;
        }
        if(!vm.tempNeedData.Endtime){
            layer.alert('请填写使用时间的结束时间');
            return;
        }
        if(vm.tempNeedData.Starttime >= vm.tempNeedData.Endtime){
            layer.alert('开始时间必须小于结束时间');
            return;
        }
        if(callback) callback();
    }
    //添加需求请求
    function req_add() {
        req.post('needs/add.do', {
            descs: vm.tempNeedData.desc,
            count: parseInt(vm.tempNeedData.count) || '',
            pictureuse: vm.tempNeedData.userDesc,
            kanzaimeijian: vm.tempNeedData.kanzaimeijian,
            price: parseInt(vm.tempNeedData.price)  || '',
            Starttime: vm.tempNeedData.Starttime,
            Endtime: vm.tempNeedData.Endtime,
            langType:0
        }).success(function(resp) {
            if(resp.code && resp.code == 211){
                //callback(resp.data);
                layer.msg('提交成功！',{time: 2000},function(index){
                    $state.go('root.needChannel');
                    //layer.close(index);
                });
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        })
    }
    //初始化设置
    function resetTempNeedData(){
        vm.tempNeedData = {
            desc: '',
            count: '',
            userDesc: '',
            kanzaimeijian: '',
            price: '',
            Starttime: '',
            Endtime: ''
        }
    }
    //初始化
    function init() {
        resetTempNeedData();
    }

    init();



});