clientModule.controller('specPicCtrl',function($scope,$cookies,req,md5,$state,$rootScope,layerIfShow,ellipsis){
    var vm = this;
    //页面初始化
    function init(){
        initRecTopic();
        getPropertiesGroups();
    }
    init();
    //初始化推荐专题的文字-超出文字变成省略号
    function initRecTopic(){
        var recObj = $('.rec-topic-desc');
        var recTxt =  ellipsis.ellipsis(recObj.html(),80);
        recObj.html(recTxt);
    }

    //初始化页面相关配置
    function initSetting(){

    }

    //获取专题图片
    function getPropertiesGroups(){
        req.post('getPicture/getPropertiesGroups.do',{
            type: 1,
            rows:'',
            page:'',
            parameter:'',
            pType:'',
            timeType:''
        }).success(function(resp){
            if(resp.code == '211'){
                vm.specList = resp.data;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

});