/**
 * Created by Admin on 2016/6/28.
 */
angular.module('client.services', [])
    .constant('baseUrl', '/photo/')
    //封装$http请求
    .factory('req', function ($http, baseUrl) {
        var transform = function (data) {
            return $.param(data);
        };
        var postConfig = {
            headers:          {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
            transformRequest: transform
        };

        //GET 请求
        function get(url, data) {
            return $http.get(baseUrl + url, {
                params: data
            });
        }

        //POST 请求
        function post(url, data) {
            return $http.post(
                baseUrl + url,
                data,
                postConfig
            );
        }

        return {
            get:      get,
            post:     post
        }
    })
    //省略
    .factory('ellipsis',function(){
        var trimObj = {};
        trimObj.ellipsis = function(text,num){
            return text.replace(/^\s+|\s+$/g,'').slice(0,num)+'...';
        }
        return trimObj;
    })
    //模态框
    .factory('modalOperate',function($rootScope){
        //模态框显示
        function modalShow(modalId){
            $('#' + modalId).show();
            //模态框遮罩层显示
            $rootScope.layerIfShow = true;
        }
        //模态框隐藏
        function modalHide(modalId){
            $('#' + modalId).hide();
            //模态框遮罩层隐藏
            $rootScope.layerIfShow = false;
        }
        return {
            modalShow: modalShow,
            modalHide: modalHide
        }
    })
    //获取全文检索数据
    .factory('getFullText',function(req){
        //获取我的值班级别
        function req_getFullText(obj,callback){
            req.post('trsSearch/trsSearch.do',obj).success(function(resp){
                if(resp.code == '211'){
                    if (callback) callback(resp);
                }else{
                    console.log(resp.msg);
                }
            });
        }
        return {
            req_getFullText: req_getFullText
        }
    })
    //获取专题
    .factory('getSpecial',function(req){
        //获取我的值班级别
        function req_getFullText(obj,callback){
            req.post('lanmu/showPicAndLanmuByTopicId.do',obj).success(function(resp){
                if(resp.code == '211'){
                    if (callback) callback(resp);
                }else{
                    console.log(resp.msg);
                }
            });
        }
        return {
            req_getFullText: req_getFullText
        }
    })
    //根据数量判断稿件显示在第几位
    .factory('jugeGroupPos',function(){
        function jugeGroupPos (signId,msGroups) {
            msGroups = msGroups ? msGroups : [];
            //存放最终结果的数组
            var arr = [];
            //需要两个位置的数组
            var twoArr = [6,7,8,9,10,11,357];
            //需要三个位置的数组
            var threeArr = [5];
            //需要四个位置的数组
            var fourArr = [390,391,392,393,394,395,396,397,398,373,374];
            //需要五个位置的数组
            var fiveArr = [3,358,359,360,361,362,363,364,365,366,367,368,369,372,375,376,400,402,377,379,380,400,401,382,383,384,385,386,387,388];
            //需要六个位置的数组
            var sixArr = [399];
            //需要十个位置的数组
            var tenArr = [24];
            if(twoArr.indexOf(signId) != -1){
                arr.length = 2;
            }
            if(threeArr.indexOf(signId) != -1){
                arr.length = 3;
            }
            if(fourArr.indexOf(signId) != -1){
                arr.length = 4;
            }
            if(fiveArr.indexOf(signId) != -1){
                arr.length = 5;
            }
            if(sixArr.indexOf(signId) != -1){
                arr.length = 6;
            }
            if(tenArr.indexOf(signId) != -1){
                arr.length = 10;
            }
            for(var i = 0; i < msGroups.length; i++){
                //arr[(msGroups[i].sgin_show - 1)] = msGroups[i];
                arr[(msGroups[i].POSITION - 1)] = msGroups[i];
            }
            return arr;
        }
        return {
            jugeGroupPos: jugeGroupPos
        }
    })
    //实现多列等高布局
    .factory('matchColumns',function(){
        function matchColumns(classname){
            var divs,contDivs,maxHeight,divHeight,d;
            // get all <div> elements in the document
            divs=document.getElementsByTagName('div');
            contDivs=[];
            // initialize maximum height value
            maxHeight=0;
            // iterate over all <div> elements in the document
            for(var i=0;i<divs.length;i++){
                // make collection with <div> elements with class attribute 'container'
                if(new RegExp("\\b" + classname + "\\b").test(divs[i].className)){
                    d=divs[i];
                    contDivs[contDivs.length]=d;
                    // determine height for <div> element
                    if(d.offsetHeight){
                        divHeight=d.offsetHeight;
                    }
                    else if(d.style.pixelHeight){
                        divHeight=d.style.pixelHeight;
                    }
                    // calculate maximum height
                    maxHeight=Math.max(maxHeight,divHeight);
                }
            }
            // assign maximum height value to all of container <div> elements
            for(var i=0;i<contDivs.length;i++){
                contDivs[i].style.height=maxHeight + "px";
            }
        }
        return {
            matchColumns: matchColumns
        }
    })
    .factory('forgetPassword', function () {
    //定义参数对象
    var pwdStatus;
    var pwdUsername;
    var pwdTel;
    var pwdEmail;
    var pwdMark;
    var pwdSuccess;

    var username_setter = function (data) {
        pwdUsername = data;
    };

    var username_getter = function () {
        return pwdUsername;
    };
    var tel_setter = function (data) {
        pwdTel = data;
    };

    var tel_getter = function () {
        return pwdTel;
    };
    var email_setter = function (data) {
        pwdEmail = data;
    };

    var email_getter = function () {
        return pwdEmail;
    };
    var status_setter = function (data) {
        pwdStatus = data;
    };

    var status_getter = function () {
        return pwdStatus;
    };
    var mark_setter = function (data) {
        pwdMark = data;
    };

    var mark_getter = function () {
        return pwdMark;
    };
    var success_setter = function (data) {
        pwdSuccess = data;
    };

    var success_getter = function () {
        return pwdSuccess;
    };


    return {
        usernameSetter: username_setter,
        usernameGetter: username_getter,
        telSetter: tel_setter,
        telGetter: tel_getter,
        emailSetter: email_setter,
        emailGetter: email_getter,
        staSetter: status_setter,
        staGetter: status_getter,
        markSetter: mark_setter,
        markGetter: mark_getter,
        successSetter: success_setter,
        successGetter: success_getter
    };
});


