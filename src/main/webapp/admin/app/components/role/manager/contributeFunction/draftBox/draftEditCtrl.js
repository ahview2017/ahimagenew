/**
 * Created by Sun on 2016/12/14.
 */
adminModule.controller('mDraftEditCtrl', function($scope, $cookies, req, md5, $state, $rootScope, cityList, $stateParams ,$filter, modalOperate){
    var vm = this;

    //获取从路由传过来的稿件id
    vm.groupId = $stateParams.id;

    //页面初始化相关设置
    function initSetting(){
        //获得城市列表
        vm.msCityList = cityList.citylist;
        //默认激活的导航项为全部稿件
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
            price:''
        }



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
        vm.editManuscript = {
            fTime:  $filter('date')(vm.manuscriptDetail.fileTime,'yyyy-MM-dd'),
            place: vm.manuscriptDetail.place,
            title: vm.manuscriptDetail.title,
            people: vm.manuscriptDetail.people,
            keywords: vm.manuscriptDetail.keywords,
            memo: vm.manuscriptDetail.memo,
            remark: vm.manuscriptDetail.remark,
            selProv: vm.manuscriptPlaceArr[0] || '',
            selCity: vm.manuscriptPlaceArr[1]  || '',
            abroadPlace: vm.manuscriptDetail.place
        };
        //初始化展示地点（从详情获取）
        vm.locationType = vm.manuscriptDetail.locationType;
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
                vm.authorId = resp.data.authorId;
                if(callback) callback();
                console.log('success');
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
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

    //上传图片
    $scope.uploadEditManuscript = function(element){
        // jquery 表单提交
        req_uploadManuscript(function(){
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
                });
            });
        });
        function req_uploadManuscript(callback){
            $("#uploadDraftForm").ajaxSubmit(function(resp) {
                // 对于表单提交成功后处理，message为提交页面saveReport.htm的返回内容
                console.log(resp);
                vm.uploadEditPicList = resp.data;
                if(resp.code == '211'){
                    if(callback) callback();
                }else if(resp.msg != '未登录'){
                    layer.alert(resp.msg);
                }
            });
        }
        return false; // 必须返回false，否则表单会自己再做一次提交操作，并且页面跳转
    }


    //保存待发稿件
    vm.saveEditManuscript = function(){
        valid_editPramas(function(){
            getPlaceParams();
            getPicDataParams();
            req_saveEditManuscript();
        });
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
        //验证价格参数
        if(vm.editManuscript.price && !(/^\d+$/.test(vm.editManuscript.price))){
            layer.alert('定价必须为整数!');
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
            remark: vm.editManuscript.remark,
            id: vm.groupId
        }).success(function(resp){
            console.log(resp);
            if(resp.code == '211'){
                layer.alert(resp.msg);
                $state.go('role.manager.draftDetail',{id: vm.groupId});
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        }).error(function(resp){
            //todo 因为net::ERR_INCOMPLETE_CHUNKED_ENCODING，暂时让它无论都跳转
            $state.go('role.manager.draftDetail',{id: vm.groupId});
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
                $state.go('role.manager.draftDetail',{id: vm.groupId});
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }


});