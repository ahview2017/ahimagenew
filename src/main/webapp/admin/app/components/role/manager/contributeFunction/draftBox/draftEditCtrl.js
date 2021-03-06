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
        
        //默认激活的导航项为新闻图片
        vm.acitiveSlideTit = 0;
        
        //
        vm.picsFlag = false;
        
        //初始化城市为安徽省
        vm.cities = vm.msCityList[11].c;
        //初始化城市为合肥
        vm.counties = vm.cities[0].a;
        
        //默认激活的导航项为全部稿件
        vm.acitiveSlideTit = 0;
        //默认选中境内稿签
        vm.locationType = 0;
        //默认不定价
        vm.selDraftPriceBtn = 0;
        //上传图片列表
        vm.upMenuscriptPicArr = [];
        
        //存放已选的稿件分类数组
        vm.manuscriptCategoryId = [];
        //存放已选的稿件分类名数组
        vm.msCategoryNameArr = [];
        vm.upMenuscriptPicArr = [];

        //从cookie里取得作者id
        vm.authorId = $cookies.get('admin_id');
        //从cookie里取得作者名
        vm.authorName = $cookies.get('admin_authorName');
        //从cookie里取得真实名
        vm.trueName = $cookies.get('admin_tureName');
        //作者展示默认采用真实名的方式
       // vm.photoUNameWay = '0';
        vm.photoUNameWay = '1';
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
    
  //改变市的时候
    vm.changeCity = function (city) {
    	//$("#proSel").find("#defaultProOpt").remove();
        for(var i = 0; i < vm.cities.length; i++){
            if(city == vm.cities[i].n){
                vm.counties = vm.cities[i].a;
            }
        }
    };
    

    //初始化时展示详情
    function showMenuscriptDetail(){
        //初始化地点信息
        vm.changeProv(vm.manuscriptPlaceArr[0]);
        vm.changeCity(vm.manuscriptPlaceArr[1]);
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
            selCounty: vm.manuscriptPlaceArr[2] || '',
            abroadPlace: vm.manuscriptDetail.abroadPlace
        };
        
        //初始化类别
        angular.forEach(vm.manuscriptCates,function(item,index){
           vm.manuscriptCategoryId.push(item.id);
           vm.msCategoryNameArr.push(item.categoryName);
        });
        // 从详情回显类别Id
        vm.finalSortParam = vm.manuscriptCategoryId.join();
        // 从详情回显类别名
        vm.categoryNameStr = vm.msCategoryNameArr.join();
        
        //初始化展示地点（从详情获取）
        vm.locationType = vm.manuscriptDetail.locationType;
        //摄影师名(从详情里取得)
        vm.photoUserName = vm.manuscriptAuthor;
        //处理详情里的图片信息
        angular.forEach(vm.manuscriptPicResult,function(item,index){
        	if(item.id !=null){
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
        	}
        });
        if(vm.upMenuscriptPicArr.length>0){
        	vm.picsFlag = true;
        }
    }
    //页面初始化
    function init(){
        initSetting();
        getManuscriptDetails(function(){
            showMenuscriptDetail();
            
            getselCpCategories(function(category){
            	if(vm.acitiveSlideTit==0){
            		angular.forEach(category,function(item,index){
                        if(item.categoryName == '新闻类别'){
                            vm.categories = item.categories;
                            loadEditSortZTree();
                        }
                    });
            	}else if(vm.acitiveSlideTit==1){
            		angular.forEach(category,function(item,index){
                        if(item.categoryName == '专题图片'){
                            vm.categories = item.categories;
                            loadEditSortZTree();
                        }
                    });
            	}
            	
                
            });
        });
        
    }
    init();
    
    
    
    //人物、关键词、稿件说明快速复制 add by xiayunan@20171218
    vm.copyPeople = function(){
    	if(!vm.editManuscript.people){
    		layer.alert("人物为空，请输入后再进行复制！");
    		return;
    	}
    	
    	 if(vm.upMenuscriptPicArr.length == 0){
             layer.alert('未选择图片，请上传后再进行复制!');
             return;
         }
    	var flag = $("#people-toggle-flag").hasClass("people-toggle");
    	if(flag){
    		$(".pic-people").val(vm.editManuscript.people);
    		$("#people-toggle-flag").removeClass("people-toggle");
    		angular.forEach(vm.upMenuscriptPicArr,function(item,index){
    			vm.upMenuscriptPicArr[index].people = vm.editManuscript.people;
    		});
    		alert("复制人物成功！");
    	}else{
    		$(".pic-people").val("");
    		$("#people-toggle-flag").addClass("people-toggle");
    		angular.forEach(vm.upMenuscriptPicArr,function(item,index){
    			vm.upMenuscriptPicArr[index].people = vm.editManuscript.people;
    		});
    		alert("清空人物成功！");
    	}
    }
    
    
    vm.copyKeyword = function(){
    	if(!vm.editManuscript.keywords){
    		layer.alert("关键词为空，请输入后再进行复制！");
    		return;
    	}
    	if(vm.upMenuscriptPicArr.length == 0){
             layer.alert('未选择图片，请上传后再进行复制!');
             return;
        }
    	var flag = $("#keyword-toggle-flag").hasClass("keyword-toggle");
    	if(flag){
    		$(".pic-keyword").val(vm.editManuscript.keywords);
    		$("#keyword-toggle-flag").removeClass("keyword-toggle");
    		angular.forEach(vm.upMenuscriptPicArr,function(item,index){
    			vm.upMenuscriptPicArr[index].keywords = vm.editManuscript.keywords;
    		});
    		alert("复制关键词成功！");
    	}else{
    		$(".pic-keyword").val("");
    		$("#keyword-toggle-flag").addClass("keyword-toggle");
    		angular.forEach(vm.upMenuscriptPicArr,function(item,index){
    			vm.upMenuscriptPicArr[index].keywords = '';
    		});
    		alert("清空关键词成功！");
    	}
    	
    	
    	//$(".pic-keyword").val(vm.newManuscriptManuscript.keywords);
    }
    
    vm.copyMemo = function(){
    	
    	if(vm.upMenuscriptPicArr.length == 0){
             layer.alert('未选择图片，请上传后再进行复制!');
             return;
        }
    	var memo = '';
    	angular.forEach(vm.upMenuscriptPicArr,function(item,index){
    		memo = vm.upMenuscriptPicArr[0].memo ;
		});
    	if(!memo){
    		layer.alert("图片说明为空，请输入后再进行复制！");
    		return;
    	}
    	
    	var flag = $("#memo-toggle-flag").hasClass("memo-toggle");
    	if(flag){
    		$(".pic-memo").val(memo);
    		$("#memo-toggle-flag").removeClass("memo-toggle");
    		angular.forEach(vm.upMenuscriptPicArr,function(item,index){
    			if(index>0){
    				vm.upMenuscriptPicArr[index].memo = memo;
    			}
    		});
    		alert("复制图片说明成功！");
    	}else{
    		$(".pic-memo").each(function(index,element){
    			if(index>0){
    				$(this).val("");
    			}			
    		});
    		
    		$("#memo-toggle-flag").addClass("memo-toggle");
    		angular.forEach(vm.upMenuscriptPicArr,function(item,index){
    			if(index>0){
    				vm.upMenuscriptPicArr[index].memo = '';
    			}
    		});
    		alert("清空图片说明成功！");
    	}
    	
    	//$(".pic-memo").val(vm.newManuscriptManuscript.memo);
    }
    
    
    
    //确认提交校审
    vm.confirmCommit = function (modalId) {
        if (vm.groupStatus == 0) {
        	
        	valid_editPramas(function(){
                getPlaceParams();
                getPicDataParams();
                //req_saveEditManuscript();
                req_saveDraftEditManuscript(modalId);
            });
        	
        }
    }
    
    //确认保存并提交提交草稿箱
    function req_commitDraft(modalId){
        req.post('groupPicCtro/submitGroups.do',{
            groupIds: vm.groupId,
            roleId: vm.adminRoleId,
            langType: window.localStorage.lang
        }).success(function(resp){
            if(resp.code == '211'){
                layer.alert('操作成功');
                modalOperate.modalHide(modalId);
               // getManuscriptDetails();
                $state.go('role.manager.myManuscript');
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    
    
    //保存草稿箱稿件请求
    function req_saveDraftEditManuscript(modalId){
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
            id: vm.groupId,
            properties: vm.acitiveSlideTit,
            cateIds: vm.finalSortParam
        }).success(function(resp){
            if(resp.code == '211'){
//               layer.alert(resp.msg);
//                $state.go('role.manager.draftDetail',{id: vm.groupId});
            	req_commitDraft(modalId);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        }).error(function(resp){
            //todo 因为net::ERR_INCOMPLETE_CHUNKED_ENCODING，暂时让它无论都跳转
            $state.go('role.manager.draftDetail',{id: vm.groupId});
        });
    }

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
                
                vm.acitiveSlideTit = vm.manuscriptProperties;// add byxiayunan@20180211
                vm.manuscriptAuthor = resp.data.author;
                vm.authorId = resp.data.authorId;
                if(resp.data.cates){
                    vm.manuscriptCates = resp.data.cates;
                }
                if(callback) callback();
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
                	if(item.id!=null){
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
                	}
                   
                });
            });
            
        });
        
        
        function req_uploadManuscript(callback){
            $("#uploadDraftForm").ajaxSubmit(function(resp) {
                // 对于表单提交成功后处理，message为提交页面saveReport.htm的返回内容
                vm.uploadEditPicList = resp.data;
                if(vm.uploadEditPicList.length>0){
                	vm.picsFlag = true;
                }
                
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

    function isChinese(temp) { 
    	var re = /[^\u4e00-\u9fa5\u0020]/; 
    	if(re.test(temp)){
    		return false;
    	}
    	return true; 
    } 

    
    
    //获取图片的相关参数
    function getPicDataParams(){
        //获取picData参数
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
    }

    function valid_editPramas(callback){
        if(!vm.editManuscript.title){
            layer.alert('请填写标题');
            return;
        }
        if(vm.editManuscript.title && vm.editManuscript.title.length > 100){
            layer.alert('标题要少于100个字');
            return;
        }
        if(!vm.editManuscript.memo){
            layer.alert('请填写稿件说明');
            return;
        }
        if(vm.editManuscript.memo && vm.editManuscript.memo.length > 2000){
            layer.alert('稿件说明要少于2000字');
            return;
        }
        if(vm.editManuscript.remark && vm.editManuscript.remark.length > 500){
            layer.alert('备注要少于500字');
            return;
        }
        if(vm.editManuscript.people && vm.editManuscript.people.length > 50){
            layer.alert('人物要少于50字');
            return;
        }
        if(!vm.editManuscript.keywords){
            layer.alert('请填写关键词');
            return;
        }
        if(vm.editManuscript.keywords && vm.editManuscript.keywords.length > 50){
            layer.alert('关键词要少于50字');
            return;
        }
        vm.finalSortParam = getChildNodesSortId();
        if(vm.finalSortParam == ''){
            layer.alert('请选择稿件类别');
            return;
        }
        
        //如果是专题图片，需选择专题类别
        if(vm.acitiveSlideTit==1){
        	 if(vm.specialCategoryNameStr == ''){
                 layer.alert('请选择专题类别');
                 return;
             }
        	 if(vm.specialCategoryNameStr.indexOf(",")!=-1){
                 layer.alert('专题图片只能选择一个专题类别！');
                 return;
             }
        	 
        }
        
        console.log("vm.specialCategoryNameStr:"+vm.specialCategoryNameStr);
        
        if((!vm.editManuscript.selProv||!vm.editManuscript.selCity)  && (!vm.editManuscript.abroadPlace)){
            layer.alert('请填写地点');
            return;
        }
        if(vm.editManuscript.abroadPlace && vm.editManuscript.abroadPlace.length > 100){
            layer.alert('地点要少于100字');
            return;
        }
        
        if(!isChinese(vm.photoUserName)){
            layer.alert('作者名请填写中文');
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
            if (vm.upMenuscriptPicArr[i].people && vm.upMenuscriptPicArr[i].people.length > 50) {
                layer.alert('人物要少于50字');
                return;
            }
            if(!vm.upMenuscriptPicArr[i].keywords){
                layer.alert('请填写关键词！');
                return;
            }
            if (vm.upMenuscriptPicArr[i].keywords && vm.upMenuscriptPicArr[i].keywords.length > 50) {
                layer.alert('关键词要少于50字');
                return;
            }
            if (vm.upMenuscriptPicArr[i].authorName && vm.upMenuscriptPicArr[i].authorName.length > 50) {
                layer.alert('作者要少于50字');
                return;
            }
            if (vm.upMenuscriptPicArr[i].memo && vm.upMenuscriptPicArr[i].memo.length > 500) {
                layer.alert('图片说明要少于500字');
                return;
            }
        }
        validUpMsPicFlag = true;
        if(callback && validUpMsPicFlag) callback();
    }

    //获取地点参数
    function getPlaceParams(){
        if(vm.locationType == 0){
        	//add by xiayunan@20171218
        	if(!vm.editManuscript.selCounty){
        		vm.editManuscript.place = vm.editManuscript.selProv + ' ' + vm.editManuscript.selCity;
	       	}else{
	       		vm.editManuscript.place = vm.editManuscript.selProv + ' ' + vm.editManuscript.selCity+ ' ' + vm.editManuscript.selCounty;
	       	}
        	
        	
        	
           // vm.editManuscript.place = vm.editManuscript.selProv + ' ' + vm.editManuscript.selCity;
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
            id: vm.groupId,
            properties: vm.acitiveSlideTit,
            cateIds: vm.finalSortParam
        }).success(function(resp){
            if(resp.code == '211'){
               layer.alert(resp.msg);
                $state.go('role.manager.draftDetail',{id: vm.groupId});
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        }).error(function(resp){
            //todo 因为net::ERR_INCOMPLETE_CHUNKED_ENCODING，暂时让它无论都跳转
            $state.go('role.manager.draftDetail',{id: vm.groupId});
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
                $state.go('role.manager.draftDetail',{id: vm.groupId});
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    
    
    // 获取子节点的分类id，多个用英文逗号隔开  add by xiayunan@20180211
    function getChildNodesSortId(){
        var cateIdParamArr = [];
        var categoryNameArr  = [];
        var cateIdParamStr = '';
        
        var specialCateIdParamArr = [];
        var specialCategoryNameArr  = [];
        
        var treeObj = $.fn.zTree.getZTreeObj("edit_sort_tree");
        var nodes = treeObj.getCheckedNodes(true); //获取选中的值
        for(var i = 0, nodesLen = nodes.length; i < nodesLen; i++){
            // 排除父节点
            //if(!nodes[i].isParent){
                cateIdParamArr.push(nodes[i].id);
                categoryNameArr.push(nodes[i].categoryName);
//                cateIdParamStr = cateIdParamArr.join();
//                vm.categoryNameStr = categoryNameArr.join();
            //}
        }
        
        var specialTreeObj = $.fn.zTree.getZTreeObj("edit_special_tree");//专题类别树
        var specialNodes = specialTreeObj.getCheckedNodes(true); //获取选中的值
        for(var i = 0, nodesLen = specialNodes.length; i < nodesLen; i++){
            // 排除父节点
            //if(!nodes[i].isParent){
                cateIdParamArr.push(specialNodes[i].id);
                categoryNameArr.push(specialNodes[i].categoryName);
                
                specialCateIdParamArr.push(specialNodes[i].id);
                specialCategoryNameArr.push(specialNodes[i].categoryName);
                
            //}
        }
        cateIdParamStr = cateIdParamArr.join();
        vm.categoryNameStr = categoryNameArr.join();
        vm.specialCategoryNameStr = specialCategoryNameArr.join();
        
        
        return cateIdParamStr;
    }
    
    //*****************草稿箱添加稿件类别 add by xiayunan@20180206********************//
    //**********************************Start**********************************//
    //确定编辑稿件分类
    vm.confirmEditMsSorts = function(){
        if(getChildNodesSortId() == ''){
            layer.alert('请选择稿件类别');
            return;
        }
        $('#edit-sort-modal').modal('hide');
    }

    
    // 编辑稿件分类模态框显示
    vm.editMsSortsModalShow = function(){
         $('#edit-sort-modal').modal('show');
        loadEditSortZTree();
    }
    
    // 加载编辑分类树
    function loadEditSortZTree(){
//    	layer.alert(vm.selCpCategories);
    	if(vm.acitiveSlideTit==0){
    		$.fn.zTree.init($("#edit_sort_tree"), setting, vm.selCpCategories);
    		$.fn.zTree.init($("#edit_special_tree"), setting, null);
    	}else if(vm.acitiveSlideTit==1){
    		$.fn.zTree.init($("#edit_sort_tree"), setting, vm.selCpCategories);
    		$.fn.zTree.init($("#edit_special_tree"), setting, vm.selSpecialCategories);
    	}
        //$.fn.zTree.init($("#edit_sort_tree"), setting, vm.selCpCategories);
        selectedEverSorts();
    }
    
    function getselCpCategories(callback){
        req.post('classification/selCpCategories.do',{
        	langType:window.localStorage.lang}).success(function(resp){
            if(resp.code == '211'){
            	category = resp.data;
            	if(vm.acitiveSlideTit==0){
            		angular.forEach(category, function (item, index) {
                		if (item.categoryName == '新闻类别') {
                			vm.selCpCategories  = item.categories;
    	                }
    	            });
            	}else if(vm.acitiveSlideTit==1){
            		angular.forEach(category, function (item, index) {
                		if (item.categoryName == '新闻类别') {
                			vm.selCpCategories  = item.categories;
    	                }
                		if (item.categoryName == '专题图片') {
                			vm.selSpecialCategories  = item.categories;
                			
    	                }
    	            });
            	}
            	
                if (callback) callback(resp.data);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    
    
    // 选中已经编辑完的分类
    function selectedEverSorts(){
        var treeObj = $.fn.zTree.getZTreeObj("edit_sort_tree");
        var nodes = treeObj.transformToArray(treeObj.getNodes());
        
        // add by xiayunan@20180208 选中已经编辑的专题分类
        var specialTreeObj = $.fn.zTree.getZTreeObj("edit_special_tree");
        var specialNodes = specialTreeObj.transformToArray(specialTreeObj.getNodes());
        
        if(vm.manuscriptCates && vm.manuscriptCates.length){
            for (var i=0, l=nodes.length; i < l; i++) {
                for(var j = 0,Len = vm.manuscriptCates.length; j < Len; j++){
                    if(vm.manuscriptCates[j].id == nodes[i].id){
                        treeObj.checkNode(nodes[i], true, true);
                    }
                }
            }
            
            // add by xiayunan@20180208 选中已经编辑的专题分类
            for (var i=0, l=specialNodes.length; i < l; i++) {
                for(var j = 0,Len = vm.manuscriptCates.length; j < Len; j++){
                    if(vm.manuscriptCates[j].id == specialNodes[i].id){
                    	specialTreeObj.checkNode(specialNodes[i], true, true);
                    }
                }
            }
            
        }
    }
    
    var zTree;
    var setting = {
        view: {
            dblClickExpand: false,//双击节点时，是否自动展开父节点的标识
       /*     showLine: true,//是否显示节点之间的连线
            fontCss:{'color':'black','font-weight':'bold'},//字体样式函数*/
            selectedMulti: false //设置是否允许同时选中多个节点
        },
        check:{
            chkboxType: { "Y": "", "N": "" },
            chkStyle: "checkbox",//复选框类型
            enable: true //每个节点上是否显示 CheckBox
        },
        data: {
            key: {
                checked: "checked",
                children: "categories",
                name: "categoryName"
            },
            simpleData: {//简单数据模式
                enable:true
            }
        },
        callback: {
            /*beforeClick: function(treeId, treeNode) {
                zTree = $.fn.zTree.getZTreeObj("edit_sort_tree");
                if (treeNode.isParent) {
                    zTree.expandNode(treeNode);//如果是父节点，则展开该节点
                }else{
                    zTree.checkNode(treeNode, !treeNode.checked, true, true);//单击勾选，再次单击取消勾选
                }
            }*/
        }
    };
    
    //选择激活的导航项
    vm.chooseManuscriptType = function(acitiveSlideTit){
        vm.acitiveSlideTit = acitiveSlideTit;
        //切换新闻图片、专题图片时重新加载分类树
        getselCpCategories(function(category){
        	if(vm.acitiveSlideTit==0){
        		angular.forEach(category,function(item,index){
                    if(item.categoryName == '新闻类别'){
                        vm.categories = item.categories;
                        loadEditSortZTree();
                    }
                });
        	}else if(vm.acitiveSlideTit==1){
        		angular.forEach(category,function(item,index){
                    if(item.categoryName == '专题图片'){
                        vm.categories = item.categories;
                        loadEditSortZTree();
                    }
                });
        	}
        	
            
        });
    }
  //**********************************End**********************************//

});