/**
 * Created by suzhangjun on 2017/2/17.
 */
angular.module('cnsphoto_en.services', [])
    .constant('baseUrl', '/cnsphoto/')
    .factory('req', function ($http, baseUrl) {
        var transform = function (data) {
            return $.param(data);
        };
        var postConfig = {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
            },
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
            get: get,
            post: post
        }
        //省略
    
    })
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
    .factory('ellipsis',function(){
        var trimObj = {};
        trimObj.ellipsis = function(text,num){
            return text.replace(/^\s+|\s+$/g,'').slice(0,num)+'...';
        }
        return trimObj;
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
    //获取我的订单
    .factory('getFullTexts',function(req,$state){
        //获取我的值班级别
        function req_getFullText(obj,callback){
            req.post('order/getMyOrders.do',obj).success(function(resp){
                if(resp.code == '211'){
                    if (callback) callback(resp);
                }else{
                    layer.alert('请登录查看我的订单');
                    $state.go('root.login');
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
    })