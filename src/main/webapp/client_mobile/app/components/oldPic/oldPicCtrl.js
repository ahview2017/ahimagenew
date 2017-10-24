clientModule.controller('oldPicCtrl',function($scope,$cookies,req,md5,$state,$rootScope,layerIfShow,ellipsis){
    var vm = this;
    //页面初始化
    function init(){
        initRecTopic();
    }
    init();
    //初始化页面头部文字-超出文字变成省略号
    function initRecTopic(){
        var recObj = $('.rec-topic-desc');
        var recTxt =  ellipsis.ellipsis(recObj.html(),80);
        recObj.html(recTxt);
    }



});