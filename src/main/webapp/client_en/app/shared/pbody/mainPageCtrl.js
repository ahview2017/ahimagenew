// 监视器 监视ng-repeat循环完成
cnsphoto_enModule.directive('onFinishRenderFilters', function ($timeout) {
    return {
        restrict: 'A',
        link: function(scope, element, attr) {
            if (scope.$last === true) {
                $timeout(function() {
                	
                	/* 把最后一个放到第一个前面，然后通过外层ul{margin-left:-980px !important; }来显示第一个 */
                   jQuery(".slider .bd li").first().before( jQuery(".slider .bd li").last() );
                   /* 控制左右按钮显示 */
                   jQuery(".slider").hover(function(){ jQuery(this).find(".arrow").stop(true,true).fadeIn(300) },function(){ jQuery(this).find(".arrow").fadeOut(300) });
                   /* 调用SuperSlide */
                   jQuery(".slider").slide({ titCell:".hd ul", mainCell:".bd ul", effect:"leftLoop",  autoPlay:true, vis:3, autoPage:true, trigger:"click"});
                    //scope.$emit('ngRepeatFinished');
                    
                    var z1=getClass('div','z1');
                    var ul_width=document.getElementById('ul_width');
                    for(var i=0;z1.length;i++){
                        z1[i].style.width=950-ul_width.offsetWidth+'px';
                    } 
                    function getClass(tagName,classname){
                       if(document.getElementsByClassName){
                          return document.getElementsByClassName(classname);
                       }else{
                         var aResult=[];
                         var aNode=document.getElementsByTagName(tagName);

                         for(var i=0;i<aNode.length;i++){
                           if(aNode[i].className.match(classname)!=null){
                             aResult.push(aNode[i]);
                            }
                         }
                         return aResult;
                       }
                    } 

                });
            } 
        }
    };
});
cnsphoto_enModule.controller('mainPageCtrl', function ($state ,req ,$http ,$scope) {
    var vm = this;
    console.log("mainPage");
    //轮播图
    var params6 = {
        "columnId": 6,
        "groupType":1,
        "langType":1
    };
    req.get("getGroupPicCtro/showColumnToGroupPic.do", params6).success(function (resp) {
    	console.info(resp)
        if(resp.code == '211'){
            $scope.cpLanmuPictures = resp.data;
            console.info(resp)
        }else{
            console.log("服务器出现错误！");
        }
    });
    //广告
    var paramsadv = {
        page: 1,
        rows: vm.selPageRows,
        langType:window.localStorage.lang,
    };
    req.get("adver/showToEnAdver.do", paramsadv).success(function (resp) {
        console.log(resp);
        if(resp.code == '211'){
            $scope.advLanmuPictures = resp.data;
        }else{
            console.log("服务器出现错误！");
        }
    });
    //系统公告
    var paramsNotice = {
        "langType":window.localStorage.lang,
        "limit":4,
        "site":1
    };

    req.post("notice/showToHomePage.do?callback=JSON_CALLBACK", paramsNotice).success(function (resp) {
        console.log(resp);
        if(resp.code == '211'){
            $scope.cpNoticeList = resp.data;console.log(resp.data);

        }else{
            console.log("服务器出现错误！");
        }
    });

    //编辑者的选择
    var params4 = {
        "columnId": 7,
        "groupType":0,
        "langType":1
    };

    req.get("getGroupPicCtro/showColumnToGroupPic.do", params4).success(function (resp) {
        console.log(resp);
        if(resp.code == '211'){
            $scope.choiceLanmuPictures = resp.data;
            console.log($scope.choiceLanmuPictures);
        }else{
            console.log("服务器出现错误！");
        }
    });
    //标题列表
    var paramsColumn = {
        "columnId":0,
        "langType":1,
    };
    req.get("getGroupPicCtro/showAllGroupPic.do", paramsColumn).success(function (resp) {
        console.log(resp);
        if(resp.code == '211'){
            $scope.columnGroupPictures = resp.data;
        }else{
            console.log("服务器出现错误！");
        }
    });

    //$scope.$on('ngRepeatFinished', function (ngRepeatFinishedEvent) {
        //下面是在table render完成后执行的js
        /* 把最后一个放到第一个前面，然后通过外层ul{margin-left:-980px !important; }来显示第一个 */
        //jQuery(".slider .bd li").first().before( jQuery(".slider .bd li").last() );
        /* 控制左右按钮显示 */
       // jQuery(".slider").hover(function(){ jQuery(this).find(".arrow").stop(true,true).fadeIn(300) },function(){ jQuery(this).find(".arrow").fadeOut(300) });
        /* 调用SuperSlide */
       // jQuery(".slider").slide({ titCell:".hd ul", mainCell:".bd ul", effect:"leftLoop",  autoPlay:true, vis:3, autoPage:true, trigger:"click"});

    //});

});
