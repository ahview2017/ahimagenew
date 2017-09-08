/**
 * Created by Sun on 2016/12/14.
 */
adminModule.controller('myManuscriptEditCtrl', function($scope, $cookies, req, md5, $state, $rootScope, cityList, $stateParams ,$filter, modalOperate){
    var vm = this;

    //获取从路由传过来的稿件id
    vm.groupId = $stateParams.id;

    //获取从路由传过来的稿件类型
    vm.gType = $stateParams.gType;

    //页面初始化相关设置
    function initSetting(){
        //获得城市列表
        vm.msCityList = cityList.citylist;
        //默认激活的导航项为全部稿件f
        vm.acitiveSlideTit = 1;
        //默认选中境内稿签
        vm.locationType = 0;
        //默认不定价
        vm.selDraftPriceBtn = 0;
        //上传图片列表
        vm.upMenuscriptPicArr = [];

        //从cookie里取得作者id
        vm.authorId = $cookies.get('admin_id');
        //从cookie里取得作者名
        vm.authorName = $cookies.get('admin_authorName');
        //从cookie里取得真实名
        vm.trueName = $cookies.get('admin_tureName');
        //作者展示默认采用真实名的方式
        vm.photoUNameWay = '0';
        //从cookie里取得角色id
        vm.adminRoleId = $cookies.get('admin_roleId');

        //存储稿件图片信息
        vm.manuscriptPicData = [];

        //编辑稿件对象
        vm.editManuscript = {
            title: '',
            people: '',
            keywords:'',
            place:'',
            memo:'',
            remark:'',
            selProv:'',
            selCity:'',
            abroadPlace:'',
            cates:'',
            price:''
        }

        //分类下拉框显示
        $('.selectpicker').selectpicker();

    }

    //改变省的时候
    vm.changeProv = function (prov) {
        for(var i = 0; i < vm.msCityList.length; i++){
            if(prov == vm.msCityList[i].p){
                vm.cities = vm.msCityList[i].c;
            }
        }
    };

    //初始化时展示详情
    function showMenuscriptDetail(){
        //初始化地点信息
        vm.changeProv(vm.manuscriptPlaceArr[0]);
        //vm.manuscriptCategoryId
        //存储编辑稿件信息
        vm.editManuscriptArr = [];
        //存储编辑稿件信息
        vm.editManuscript = {
            fTime:  $filter('date')(vm.manuscriptDetail.fileTime,'yyyy-MM-dd'),
            place: vm.manuscriptDetail.place,
            title: vm.manuscriptDetail.title,
            people: vm.manuscriptDetail.people,
            keywords: vm.manuscriptDetail.keywords,
            memo: vm.manuscriptDetail.memo,
            remark: vm.manuscriptDetail.remark,
            selProv: vm.manuscriptPlaceArr[0],
            selCity: vm.manuscriptPlaceArr[1],
            abroadPlace: vm.manuscriptDetail.place
        };
        //初始化展示地点（从详情获取）
        vm.locationType = vm.manuscriptDetail.locationType;


        angular.forEach(vm.manuscriptCates,function(item,index){
            vm.editManuscriptArr.push(item['id']);
        });
        vm.editManuscript.cates = vm.editManuscriptArr.join();
        //摄影师名(从详情里取得)
        vm.photoUserName = vm.manuscriptAuthor;
        //处理详情里的图片信息
        angular.forEach(vm.manuscriptPicResult,function(item,index){
            vm.upMenuscriptPicArr.push({
                id: item.id + '',
                filmTime: $filter('date')(item.filmTime,'yyyy-MM-dd'),
                img: item.smallPath,
                isCover: item.isCover + '',
                sortId: item.sortId + '',
                people: item.people,
                keywords: item.keywords,
                authorName: item.authorName,
                memo: item.memo
            })
        });
    }
    //页面初始化
    function init(){
        initSetting();
        getManuscriptDetails(function(){
            showMenuscriptDetail();
        });
    }
    init();

    //获取稿件详情
    function getManuscriptDetails(callback){
        req.post('groupPicCtro/getGroupPics.do',{
            groupId: vm.groupId
        }).success(function(resp){
            if(resp.code == '211'){
                vm.manuscriptDetail = resp.data;
                vm.groupStatus = resp.data.groupStatus;
                vm.manuscriptPlaceArr = resp.data.place.split(' ');
                vm.manuscriptPicResult = resp.data.pics;
                vm.manuscriptProperties = resp.data.properties;
                vm.manuscriptAuthor = resp.data.author;
                vm.manuscriptCates = resp.data.cates;
                vm.authorId = resp.data.authorId;
                if(callback) callback();
                console.log('success');
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //查询摄影师：用于新建稿件搜索作者
    vm.getPhotoUser = function(event){
        vm.hasSeledUNameFlag = false;
        vm.addAuthorItemFlag = false;
        req_getPhotoUser();
        vm.showNameFlag = true;
    }
    //选择作者
    vm.choosePhotoUser = function(item){
        vm.hasSeledUNameItem = item;
        vm.hasSeledUNameItem.ANONYMOUS_NAME = '匿名';
        if(vm.photoUserName){
            if((vm.photoUNameWay == '0')){
                vm.photoUserName =  vm.hasSeledUNameItem.AUTHOR_NAME;
            }
            if(vm.photoUNameWay == '1'){
                vm.photoUserName =  vm.hasSeledUNameItem.TURE_NAME;
            }
            if(vm.photoUNameWay == '2'){
                vm.photoUserName = vm.hasSeledUNameItem.ANONYMOUS_NAME;
            }
        }
        vm.authorId =  vm.hasSeledUNameItem.ID;
        vm.hasSeledUNameFlag = true;
        //显示作者名的div隐藏
        vm.showNameFlag = false;
    }
    //查询摄影师请求
    function req_getPhotoUser(callback){
        req.post('userCtro/getPhotoUser.do',{
            uName: vm.photoUserName
        }).success(function(resp){
            if(resp.code == 211){
                vm.photoUser = resp.data;
                if(callback) callback();
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    //选择用户展示方式
    vm.choseUnameShowWay = function(){
        console.log(vm.photoUNameWay);
        //如果是默认的作者名，没有修改过
        if(!vm.hasSeledUNameFlag && !vm.addAuthorItemFlag){
            if((vm.photoUNameWay == '0')){
                vm.photoUserName = vm.manuscriptAuthor;
            }
            if(vm.photoUNameWay == '1'){
                vm.photoUserName = vm.trueName;
            }
            if(vm.photoUNameWay == '2'){
                vm.photoUserName = '匿名';
            }
        }
        //如果搜索选出的作者
        if(vm.hasSeledUNameFlag){
            if((vm.photoUserName)){
                if((vm.photoUNameWay == '0')){
                    vm.photoUserName = vm.hasSeledUNameItem.AUTHOR_NAME;
                }
                if(vm.photoUNameWay == '1'){
                    vm.photoUserName = vm.hasSeledUNameItem.TURE_NAME;
                }
                if(vm.photoUNameWay == '2'){
                    vm.photoUserName = vm.hasSeledUNameItem.ANONYMOUS_NAME;
                }
            }
        }
        //如果添加社外作者存在
        if(vm.addAuthorItemFlag){
            if(vm.photoUNameWay == '0'){
                vm.photoUserName =  vm.addAuthorItem.authorName;
            }
            if(vm.photoUNameWay == '1'){
                vm.photoUserName = vm.addAuthorItem.tureName;
            }
            if(vm.photoUNameWay == '2'){
                vm.photoUserName =  vm.addAuthorItem.ANONYMOUS_NAME;
            }
        }

    }
    //添加作者模态框显示
    vm.addAuthorNameModalShow = function(modalId){
        vm.hasSeledUNameFlag = false;
        vm.addAuthorItemFlag = false;
        modalOperate.modalShow(modalId);
    }
    //确认添加社外作者
    vm.addForeignAuthor = function(modalId){
        req_addForeignAuthor(modalId);
    }
    //确认添加涉外作者请求
    function req_addForeignAuthor(modalId){
        if(!vm.authorInfo.loginName){
            layer.alert('请填写登录名');
            return;
        }
        if(!vm.authorInfo.authorName){
            layer.alert('请填写作者名');
            return;
        }
        if(!vm.authorInfo.trueName){
            layer.alert('请填写真实姓名');
            return;
        }
        if(!vm.authorInfo.pwd){
            layer.alert('请填写密码');
            return;
        }
        req.post('userCtro/addUser.do',{
            userName: vm.authorInfo.loginName,
            authorName: vm.authorInfo.authorName,
            tureName: vm.authorInfo.trueName,
            password: md5.createHash(vm.authorInfo.pwd),
            emailBind: vm.authorInfo.mail,
            telBind: vm.authorInfo.phone,
            roleIds: 3
        }).success(function(resp){
            if(resp.code == '211'){
                layer.alert('操作成功');
                vm.addAuthorItem = resp.data;
                vm.addAuthorItem.ANONYMOUS_NAME = '匿名';
                if(vm.photoUNameWay == '0'){
                    vm.photoUserName =  vm.addAuthorItem.authorName;
                }
                if(vm.photoUNameWay == '1'){
                    vm.photoUserName = vm.addAuthorItem.tureName;
                }
                if(vm.photoUNameWay == '2'){
                    vm.photoUserName =  vm.addAuthorItem.ANONYMOUS_NAME;
                }
                vm.authorId =  vm.addAuthorItem.id;
                vm.addAuthorItemFlag = true;
                modalOperate.modalHide(modalId);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //选择境内外稿签
    vm.homeAbroadToggle = function(locationType){
        vm.locationType = locationType;
    }


    //删除每项图片
    vm.removePicItem = function(index){
        vm.upMenuscriptPicArr.splice(index, 1);
    }

    //上次的图片请求是否完成的标志
    var displayed = [];
    //上传稿件请求
    function req_upMs(j){
        var formdata = new FormData();
        console.log(vm.upMsFiles[j]);
        formdata.append('picFiles', vm.upMsFiles[j]);
        formdata.append("langType",lang);
        $.ajax({
            type: "POST",
            data: formdata,
            url: "/photo/groupPicCtro/upPic.do" + '?time=' + new Date().getTime(),
            cache: false,
            contentType: false,     //必须false才会自动加上正确的Content-Type
            processData: false,     //必须false才会避开jQuery对formdata的默认处理
            async: true,
            xhrFields: {
                withCredentials: true
            },
            xhr:function(){
                //获取ajaxSetting中的xhr对象，为它的upload属性绑定progress事件的处理函数
                myXhr = $.ajaxSettings.xhr();
                if(myXhr.upload){
                    //检查upload属性是否存在
                    myXhr.upload.addEventListener('progress',function(e){
                        if(e.lengthComputable) {
                            var percent = e.loaded/e.total*100;
                            $('.ms-upinfo-box .ms-up-content-box .ms-up-progress-desc .ms-up-progress-bar').eq(j).html(percent.toFixed(2) + "%");
                            $('.ms-upinfo-box .ms-up-content-box .ms-up-progress-bg-box .ms-up-progress-bg').eq(j).width(percent.toFixed(2) + "%");
                        }
                    },false);
                }
                return myXhr; //xhr对象返回给jQuery使用
            }
        }).success(function (resp) {
            if(resp.code && resp.code == '211'){
                vm.uploadEditPicList = resp.data;
                console.log(vm.uploadEditPicList);
                uploadedPicCallback(j);
                displayed[j] = true;
                if(j === vm.upMsTotalLen -1){
                    console.log('显示完毕');
                    //操作结束清空input中的内容，解决input file表单无法重复上传同一个图片的问题
                    $('#picFile').val('');
                }else if(j < vm.upMsTotalLen){
                    req_upMs(j+1);
                }
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
                //上传不成功时去掉进度条，多张图片时继续下面的图片上传
                $('.ms-upinfo-box .ms-up-content-box').eq(j).fadeOut();
                displayed[j] = true;
                if(j < vm.upMsTotalLen){
                    req_upMs(j+1);
                }
            }
        }).error(function (resp) {
            console.log(resp);
        });
    }

    //上传完图片向upMenuscriptPicArr数组推入图片的相关信息，让图片列表展示
    function uploadedPicCallback(j){
        //完成后对应的速度条消失
        if(j == 0 || displayed[j-1]){
            $('.ms-upinfo-box .ms-up-content-box').eq(j).fadeOut();
        }
        angular.forEach(vm.uploadEditPicList,function(item,index){
            $scope.$apply(function(){
                vm.upMenuscriptPicArr.push({
                    id: item.id + '',
                    filmTime: $filter('date')(item.filmTime,'yyyy-MM-dd'),
                    img: item.smallPath,
                    isCover: '0',
                    sortId: (index + 1) + '',
                    people: item.people,
                    keywords: item.keywords,
                    authorName: item.authorName,
                    memo: item.memo
                })
                //默认上传的第一个为主图
                if(index == '0'){
                    vm.upMenuscriptPicArr[0].isCover = '1';
                }
            });
        });
    }

    //上传图片格式的验证
    function validUpMsPic(filename){
        var upPicSuffix= filename.substring(name.lastIndexOf(".")+1).toLowerCase();
        if(!/\.(jpg|jpeg|JPG|JPEG)$/.test(upPicSuffix))
        {
            layer.alert("图片类型必须是.jpg,jpeg中的一种")
            return false;
        }else{
            return true;
        }
    }

    //上传图片
    $scope.uploadEditManuscript = function(element){
        //每次上传前清除之前的速度条div里的速度条
        $('.ms-upinfo-box .ms-up-content-box').remove();
        //每次上传前初始化displayed标志
        displayed = [];
        //保存上传的文件对象
        vm.upMsFiles = element.files;
        vm.upMsTotalLen = element.files.length;
        //遍历上传的文件
        for(var i = 0,len = vm.upMsTotalLen;i < len;i++){
            //验证上传图片是否是jpg/jpeg格式
            if(validUpMsPic(vm.upMsFiles[i].name)){
                if(vm.upMsFiles && vm.upMsFiles[i].size){
                    vm.msFileSize = $filter('Filesize')(vm.upMsFiles[i].size);
                }
                var msSpeedHtml = '<div class="ms-up-content-box">'
                    + '<p class="ms-up-progress-desc">'
                    + '<span>' + vm.upMsFiles[i].name + "（" + vm.msFileSize + "）- "+ '</span>'
                    + '<span class="ms-up-progress-bar">0%</span>'
                    +'</p>'
                    + '<div class="ms-up-progress-bg-box">'
                    + '<div class="ms-up-progress-bg">'
                    + '</div>'
                    + '</div>'
                    + '</div>';
                $('.ms-upinfo-box').append(msSpeedHtml);
                (function(j){
                    if(j == 0 || displayed[j-1]){
                        req_upMs(j);
                    }
                })(i);
            }else{
                //如果上传图片格式不正确则从上传数组中删除（Array.prototype.slice.call把类数组转为数组）
                vm.upMsFiles = Array.prototype.slice.call(element.files,0);
                vm.upMsFiles.splice(i,1);
                vm.upMsTotalLen = vm.upMsFiles.length;
                console.log(vm.upMsFiles);
                console.log(vm.upMsTotalLen);
            }
        }
        return false; // 必须返回false，否则表单会自己再做一次提交操作，并且页面跳转
    }

    //改变主图副图
    vm.changePicIsCover = function(isCover,index){
        if(isCover == '1'){
            for(var i = 0; i <  vm.upMenuscriptPicArr.length; i++){
                vm.upMenuscriptPicArr[i].isCover = '0';
            }
            vm.upMenuscriptPicArr[index].isCover = '1';
        }
    }


    //保存待发稿件
    vm.saveEditManuscript = function(){
        valid_editPramas(function(){
            getPlaceParams();
            getPicDataParams();
            req_saveEditManuscript();
        });
    }

    // 验证编辑相关参数
    function valid_editPramas(callback){
        if(!vm.editManuscript.title){
            layer.alert('请填写标题');
            return;
        }
        if(vm.editManuscript.title && vm.editManuscript.title.length > 30){
            layer.alert('标题要少于30个字');
            return;
        }
        if(!vm.editManuscript.memo){
            layer.alert('请填写新闻说明');
            return;
        }
        if(vm.editManuscript.memo && vm.editManuscript.memo.length > 600){
            layer.alert('新闻说明要少于600字');
            return;
        }
        if(vm.editManuscript.remark && vm.editManuscript.remark.length > 255){
            layer.alert('备注要少于255字');
            return;
        }
        if(vm.editManuscript.people && vm.editManuscript.people.length > 200){
            layer.alert('人物要少于200字');
            return;
        }
        if(vm.editManuscript.keywords && vm.editManuscript.keywords.length > 500){
            layer.alert('关键词要少于500字');
            return;
        }
        if((!vm.editManuscript.selProv &&  !vm.editManuscript.selCity) && (!vm.editManuscript.abroadPlace)){
            layer.alert('请填写地点');
            return;
        }
        if(vm.editManuscript.abroadPlace && vm.editManuscript.abroadPlace.length > 200){
            layer.alert('地点要少于200字');
            return;
        }
        if(!vm.authorName){
            layer.alert('请填写作者');
            return;
        }
        //验证图片相关信息填写是否合格
        var validUpMsPicFlag = false;
        //验证图片列表相关信息
        for(var i = 0, len = vm.upMenuscriptPicArr.length; i < len; i++) {
            if (vm.upMenuscriptPicArr[i].people && vm.upMenuscriptPicArr[i].people.length > 200) {
                layer.alert('人物要少于200字');
                return;
            }
            if (vm.upMenuscriptPicArr[i].keywords && vm.upMenuscriptPicArr[i].keywords.length > 500) {
                layer.alert('关键词要少于500字');
                return;
            }
            if (vm.upMenuscriptPicArr[i].authorName && vm.upMenuscriptPicArr[i].authorName.length > 200) {
                layer.alert('作者要少于200字');
                return;
            }
            if (vm.upMenuscriptPicArr[i].memo && vm.upMenuscriptPicArr[i].memo.length > 600) {
                layer.alert('图片说明要少于600字');
                return;
            }
        }
        validUpMsPicFlag = true;
        if(callback && validUpMsPicFlag) callback();
    }

    //获取图片的相关参数
    function getPicDataParams(){
        //获取picData参数
        console.log(typeof angular.toJson(vm.upMenuscriptPicArr,true));
        angular.forEach(vm.upMenuscriptPicArr,function(item,index){
            vm.manuscriptPicData.push({
                id: item.id + '',
                people: vm.upMenuscriptPicArr[index].people || '',
                keywords: vm.upMenuscriptPicArr[index].keywords || '',
                authorId:  vm.authorId + '',
                authorName: vm.upMenuscriptPicArr[index].authorName || '',
                memo: vm.upMenuscriptPicArr[index].memo || '',
                isCover: vm.upMenuscriptPicArr[index].isCover,
                sortId: (index + 1) + '',
                filmTime: vm.upMenuscriptPicArr[index].filmTime + ' 00:00:00'
            })
        });
        console.log(vm.manuscriptPicData);
    }
    //获取地点参数
    function getPlaceParams(){
        if(vm.locationType == 0){
            vm.editManuscript.place = vm.editManuscript.selProv + ' ' + vm.editManuscript.selCity;
        }
        if(vm.locationType == 1){
            vm.editManuscript.place = vm.editManuscript.abroadPlace;
        }
    }
    //保存稿件请求
    function req_saveEditManuscript(){
        req.post('groupPicCtro/editGroupPic.do',{
            fTime: vm.editManuscript.fTime,
            picData:  angular.toJson(vm.manuscriptPicData,true),
            authorId:  vm.authorId + '',
            author: vm.photoUserName,
            title: vm.editManuscript.title,
            people: vm.editManuscript.people,
            keywords: vm.editManuscript.keywords,
            place: vm.editManuscript.place,
            locationType: vm.locationType,
            memo: vm.editManuscript.memo,
            cateIds: vm.editManuscript.cates,
            remark: vm.editManuscript.remark,
            id: vm.groupId
        }).success(function(resp){
            console.log(resp);
            if(resp.code == '211'){
                layer.alert(resp.msg);
                $state.go('role.manager.myManuscriptDetail',{id: vm.groupId, gType: vm.gType});
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        }).error(function(resp){
            //todo 因为net::ERR_INCOMPLETE_CHUNKED_ENCODING，暂时让它无论都跳转
            $state.go('role.manager.myManuscriptDetail',{id: vm.groupId, gType: vm.gType});
            console.log(resp);
        });
    }

    //结束编辑稿件：用户编辑不保存返回
    vm.returnSendManuscript = function(){
        req_endEditSendManuscript();
    }
    //结束编辑稿件：用户编辑不保存返回请求
    function req_endEditSendManuscript(){
        req.post('groupPicCtro/editEnd.do',{
            groupId: vm.groupId
        }).success(function(resp){
            if(resp.code == '211'){
                console.log('success');
                $state.go('role.manager.myManuscriptDetail',{id: vm.groupId,gType: vm.gType});
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }


});