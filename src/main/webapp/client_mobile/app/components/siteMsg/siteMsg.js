clientModule.controller('siteMsgCtrl',function($scope, $cookies, req, md5, $state, $rootScope){
    var vm = this;


    function init(){
        //网站信息对象
        vm.siteMsg = {
            content:'',
            contactWay:''
        }
    }
    init();
    //提交网站信息
    vm.submitSiteMsg = function(){
        req_submitSiteMsg();
    }

    function req_submitSiteMsg(){
        var phoneRexExp = /^1(3|4|5|7|8)\d{9}$/;
        var maillRegExp = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        if(!vm.siteMsg.content){
            layer.alert('请填写网站内容');
            return;
        }
        if(!vm.siteMsg.contactWay){
            layer.alert('请填写联系方式');
            return;
        }
        if(vm.siteMsg.contactWay && !(phoneRexExp.test(vm.siteMsg.contactWay)) && !(maillRegExp.test(vm.siteMsg.contactWay))){
            layer.alert('联系方式是您的邮箱或者电话！');
            return;
        }
        req.post('leavingmsg/add.do',{
            smContent: vm.siteMsg.content,
            smLink: vm.siteMsg.contactWay
        }).success(function(resp){
            if(resp.code == '211'){
                layer.alert('提交成功');
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

});
