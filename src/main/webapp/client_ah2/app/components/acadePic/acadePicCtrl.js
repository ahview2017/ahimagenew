clientModule.controller('acadePicCtrl',function($scope,$cookies,req,md5,$state,$rootScope,layerIfShow,ellipsis){
    var vm = this;
    //页面初始化
    function init(){
        initRecTopic();
    }
    init();
    //初始化推荐专题的文字-超出文字变成省略号
    function initRecTopic(){
        var recObj = $('.acade-pic-txt');
        var hotObj = $('.hot-pic-txt');
        var recTxt =  ellipsis.ellipsis(recObj.html(),76);
        recObj.html(recTxt);
        var hotTxt =  ellipsis.ellipsis(hotObj.html(),40);
        hotObj.html(hotTxt);
    }
    $(window).load(function() {
        $('.flexslider').flexslider({
            directionNav: false,
            slideshow: true,
            slideshowSpeed: 4000,
            animationDuration: 600
        });
    });


});