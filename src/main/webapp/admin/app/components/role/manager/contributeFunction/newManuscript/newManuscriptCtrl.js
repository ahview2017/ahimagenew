/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('newManuscriptCtrl',function($scope, $cookies, req, md5, $state, $rootScope, layerIfShow, cityList, Upload, $http, $filter,modalOperate,allModalMove, $window){
    var vm = this;
    //页面初始化相关配置
    function initSetting(){
        //默认激活的导航项为新闻图片
        vm.acitiveSlideTit = 0;
        //默认选中境内稿签
        vm.locationType = 0;
        //默认选中中文稿件
        vm.selManuscript = 1;
        //省市县联动数据
        vm.msCityList = cityList.citylist;
        //初始化城市为安徽省
        vm.cities = vm.msCityList[11].c;
        //初始化城市为合肥
        vm.counties = vm.cities[0].a;
        //上传图片列表
        vm.upMenuscriptPicArr = [];
        //从cookie里取得作者id
        vm.authorId = $cookies.get('admin_id');
        //从cookie里取得作者名
        vm.authorName = $cookies.get('admin_authorName');
        //从cookie里取得真实名
        vm.trueName = $cookies.get('admin_tureName');
        //摄影师名
        vm.photoUserName = vm.authorName;
        //console.log(vm.photoUserName);
        //作者展示默认采用真实名的方式
        vm.photoUNameWay = '0';
        //从cookie里取得角色id
        vm.adminRoleId = $cookies.get('admin_roleId');
        //存储picData数组
        vm.manuscriptPicData = [];
        //拍摄时间
        vm.nowfTime = $filter('date')(new Date(),'yyyy-MM-dd');
        //存储稿件信息
        vm.newManuscriptManuscript = {
            title: '',
            people: '',
            keywords:'',
            place:'',
            memo:'',
            remark:'',
            abroadPlace:'',
            selProv:'安徽',
            selCity:'合肥'
        }
        //初始化图片相关信息
        vm.manuscriptPic = {
            picpeople: {},
            pickeywords:{},
            picAuthorName:{},
            picmemo:{}
        }
        //默认输入时显示选择作者的列表div
        vm.showNameFlag = false;
        //初始化涉外作者信息
        vm.newManuscript  = {
            loginName:'',
            authorName:'',
            trueName:'',
            pwd:'',
            mail:'',
            phone:''
        }
        //是否采用搜索方式选择作者，默认无
        vm.hasSeledUNameFlag = false;
        //是否采用添加社外作者方式选择作者，默认无
        vm.addAuthorItemFlag = false;
    }
    //页面初始化
    function init(){
        initSetting();
		getMasBaseUrl();
        req_getPhotoUser();
        getselCpCategories(function(category){
            angular.forEach(category,function(item,index){
                if(item.categoryName == '新闻类别'){
                    vm.categories = item.categories;
                    //console.log("2=="+vm.categories);
                    loadEditSortZTree();
                }
            });
        });
    }
    init();
    
    
    
    //人物、关键词、稿件说明快速复制 add by xiayunan@20171011
    vm.copyPeople = function(){
    	if(!vm.newManuscriptManuscript.people){
    		layer.alert("人物为空，请输入后再进行复制！");
    		return;
    	}
    	
    	 if(vm.upMenuscriptPicArr.length == 0){
             layer.alert('未选择图片，请上传后再进行复制!');
             return;
         }
    	var flag = $("#people-toggle-flag").hasClass("people-toggle");
    	if(flag){
    		$(".pic-people").val(vm.newManuscriptManuscript.people);
    		$("#people-toggle-flag").removeClass("people-toggle");
    		angular.forEach(vm.upMenuscriptPicArr,function(item,index){
    			vm.upMenuscriptPicArr[index].people = vm.newManuscriptManuscript.people;
    		});
    		alert("复制人物成功！");
    	}else{
    		$(".pic-people").val("");
    		$("#people-toggle-flag").addClass("people-toggle");
    		angular.forEach(vm.upMenuscriptPicArr,function(item,index){
    			vm.upMenuscriptPicArr[index].people = vm.newManuscriptManuscript.people;
    		});
    		alert("清空人物成功！");
    	}
    }
    
    
    vm.copyKeyword = function(){
    	if(!vm.newManuscriptManuscript.keywords){
    		layer.alert("关键词为空，请输入后再进行复制！");
    		return;
    	}
    	if(vm.upMenuscriptPicArr.length == 0){
             layer.alert('未选择图片，请上传后再进行复制!');
             return;
        }
    	var flag = $("#keyword-toggle-flag").hasClass("keyword-toggle");
    	if(flag){
    		$(".pic-keyword").val(vm.newManuscriptManuscript.keywords);
    		$("#keyword-toggle-flag").removeClass("keyword-toggle");
    		angular.forEach(vm.upMenuscriptPicArr,function(item,index){
    			vm.upMenuscriptPicArr[index].keywords = vm.newManuscriptManuscript.keywords;
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
    
    
 // 编辑稿件分类模态框显示
    vm.editMsSortsModalShow = function(){
         $('#edit-sort-modal').modal('show');
        loadEditSortZTree();
    }
    // 加载编辑分类树
    function loadEditSortZTree(){
//    	layer.alert(vm.selCpCategories);
    	//console.log("3=="+vm.selCpCategories);
        $.fn.zTree.init($("#edit_sort_tree"), setting, vm.selCpCategories);
        selectedEverSorts();
    }
 // 确定编辑稿件分类
    vm.confirmEditMsSorts = function(){
       //console.log(getChildNodesSortId());
        if(getChildNodesSortId() == ''){
            layer.alert('请选择稿件类别');
            return;
        }
        $('#edit-sort-modal').modal('hide');
    }
    // 获取子节点的分类id，多个用英文逗号隔开
    function getChildNodesSortId(){
        var cateIdParamArr = [];
        var categoryNameArr  = [];
        var cateIdParamStr = '';
        var treeObj = $.fn.zTree.getZTreeObj("edit_sort_tree");
        var nodes = treeObj.getCheckedNodes(true); //获取选中的值
        for(var i = 0, nodesLen = nodes.length; i < nodesLen; i++){
            // 排除父节点
            //if(!nodes[i].isParent){
                cateIdParamArr.push(nodes[i].id);
                categoryNameArr.push(nodes[i].categoryName);
                cateIdParamStr = cateIdParamArr.join();
                vm.categoryNameStr = categoryNameArr.join();
//                console.log(cateIdParamStr);
//                console.log(vm.categoryNameStr);
            //}
        }
        return cateIdParamStr;
    }
  //获取稿件分类信息
    function getselCpCategories(callback){
        req.post('classification/selCpCategories.do',{
	langType:window.localStorage.lang}).success(function(resp){
            if(resp.code == '211'){
            	category = resp.data;
            	angular.forEach(category, function (item, index) {
                if (item.categoryName == '新闻类别') {
                    vm.selCpCategories  = item.categories;
//                    console.log(vm.selCpCategories);
	                }
	            });
                if (callback) callback(resp.data);
                //console.log('success');
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    
    
    
    
    
    // 选中已经编辑完的分类
    function selectedEverSorts(){
        var treeObj = $.fn.zTree.getZTreeObj("edit_sort_tree");
        var nodes = treeObj.transformToArray(treeObj.getNodes());
        if(vm.manuscriptCates && vm.manuscriptCates.length){
            for (var i=0, l=nodes.length; i < l; i++) {
                for(var j = 0,Len = vm.manuscriptCates.length; j < Len; j++){
                    if(vm.manuscriptCates[j].id == nodes[i].id){
                        treeObj.checkNode(nodes[i], true, true);
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
	//获取Mas视频基础URL add by xiayunan 20170907
	function getMasBaseUrl(){
		req.get('groupPicCtro/getMasBaseUrl.do').success(function(resp) {
			if(resp.code == '211') {
				vm.masBaseUrl = resp.data.masBaseUrl;
			}else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}

    //拖拽弹框
    vm.modalMove = function(dragDiv,tagDiv){
        allModalMove.modalMove(dragDiv,tagDiv);
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
                vm.photoUserName =  vm.hasSeledUNameItem.ANONYMOUS_NAME ;
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
        //console.log(vm.photoUNameWay);
        //如果是默认的作者名，没有修改过
        if(!vm.hasSeledUNameFlag && !vm.addAuthorItemFlag){
                if((vm.photoUNameWay == '0')){
                    vm.photoUserName = vm.authorName;
                }
                if(vm.photoUNameWay == '1'){
                    vm.photoUserName = vm.trueName;
                }
                if(vm.photoUNameWay == '2'){
                    vm.photoUserName =  '匿名';
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
        var pwdExp = /^(?![A-Z]+$)(?![a-z]+$)(?!\d+$)(?![\W_]+$)\S{8,16}$/;
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
        if(!(pwdExp.test(vm.authorInfo.pwd))){
            layer.alert('请输入8-16个字符密码，且密码要含有小写字母、大写字母、数字、特殊符号的两种及以上');
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
    //新建稿件模态框隐藏
    vm.newMenuscriptModalHide = function(modalId){
        modalOperate.modalHide(modalId);
    }
    //选择激活的导航项
    vm.chooseManuscriptType = function(acitiveSlideTit){
        vm.acitiveSlideTit = acitiveSlideTit;
    }
    //选择境内外稿签
    vm.homeAbroadToggle = function(locationType){
        vm.locationType = locationType;
    }
    //选择中英文
    vm.chooseManuscriptLang = function(selManuscript){
        vm.selManuscript = selManuscript;
        vm.toggleSelLang = !vm.toggleSelLang;
        vm.selManuscriptShow = !vm.selManuscriptShow;
    }
    
    //选择Mas视频
    vm.selectMasVideo =  function(){
    	window.open(vm.masBaseUrl+"&method=list");
    }
    
    
    
   //上传稿件帮助说明
   vm.helpshow=function(){
   	$('#user-help-modal').show();
   }
   vm.helpHide=function(){
   	$('#user-help-modal').hide();
   }

    //上传完图片向upMenuscriptPicArr数组推入图片的相关信息，让图片列表展示
    function uploadedPicCallback(j){
        //完成后对应的速度条消失
        if(j == 0 || displayed[j-1]){
            $('.ms-upinfo-box .ms-up-content-box').eq(j).fadeOut();
        }
        angular.forEach(vm.uploadEditPicList,function(item,index){
        	//add by liu.jinfeng@20170914
        	if(!item.bIsExif){
        		vm.bIsExif = false;
        		return;
        	}
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

    //上次的图片请求是否完成的标志
    var displayed = [];
    //上传稿件请求
    function req_upMs(j){
        var formdata = new FormData();
        //console.log(vm.upMsFiles[j]);
        formdata.append('picFiles', vm.upMsFiles[j]);
        formdata.append("langType",lang);
        //console.log(formdata);
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
                //console.log(vm.uploadEditPicList);
                vm.bIsExif = true;
                uploadedPicCallback(j);
                displayed[j] = true;
                if(!vm.bIsExif){
                	layer.alert("上传不包含Exif信息的图片失败");
                	if((j+1) < vm.upMsTotalLen){
                		 req_upMs(j+1);
                	}
                	
                }else if(j === vm.upMsTotalLen -1){
                   // console.log('显示完毕');
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
          // console.log($('#picFile').val());
        }).error(function (resp) {
            //console.log(resp);
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
    $scope.uploadManuscript = function(element){
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
                //console.log(vm.upMsFiles);
                //console.log(vm.upMsTotalLen);
            }
        }
        return false; // 必须返回false，否则表单会自己再做一次提交操作，并且页面跳转
    }


    //删除每项图片
    vm.removePicItem = function(index){
        vm.upMenuscriptPicArr.splice(index, 1);
    }

    //保存
    vm.saveManuscript = function(){
        valid_manuscriptInfo(function(){
            getPicDataParams();
            vm.loadUpMs = layer.load(1);
            req_saveManuscript('0');
        });
    }


    //改变省的时候
    vm.changeProv = function (prov) {
    	$("#proSel").find("#defaultProOpt").remove();
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


    //提交
    vm.commitManuscript = function(){
        valid_manuscriptInfo(function(){
            getPicDataParams();
            vm.loadUpMs = layer.load(1);
            req_saveManuscript('1');
        });
    }
    
    //获取图片的相关参数
    function getPicDataParams(){
        //console.log(typeof angular.toJson(vm.upMenuscriptPicArr,true));
        angular.forEach(vm.upMenuscriptPicArr,function(item,index){
            if(vm.upMenuscriptPicArr[index].people && vm.upMenuscriptPicArr[index].people.length > 200){
                layer.alert('人物要少于200字');
                return;
            }
             if(!vm.upMenuscriptPicArr[index].keywords){
             layer.alert('请填写图片关键词！');
             return;
             }
            if(vm.upMenuscriptPicArr[index].keywords && vm.upMenuscriptPicArr[index].keywords.length > 200){
                layer.alert('关键词要少于200字');
                return;
            }
            if(vm.upMenuscriptPicArr[index].authorName && vm.upMenuscriptPicArr[index].authorName.length > 200){
                layer.alert('作者要少于200字');
                return;
            }
            if(vm.upMenuscriptPicArr[index].memo && vm.upMenuscriptPicArr[index].memo.length > 4000){
                layer.alert('图片说明要少于4000字');
                return;
            }
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
        //console.log(vm.manuscriptPicData);
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
    //保存或提交前的验证
    function valid_manuscriptInfo(callback){
        if(!vm.newManuscriptManuscript.title){
            layer.alert('请填写标题');
            return;
        }
        if(vm.newManuscriptManuscript.title && vm.newManuscriptManuscript.title.length > 100){
            layer.alert('标题要少于100个字');
            return;
        }
        if(!vm.newManuscriptManuscript.memo){
            layer.alert('请填写新闻说明');
            return;
        }
        if(vm.newManuscriptManuscript.memo && vm.newManuscriptManuscript.memo.length > 4000){
            layer.alert('新闻说明要少于4000字');
            return;
        }
        if(vm.newManuscriptManuscript.remark && vm.newManuscriptManuscript.remark.length > 2000){
            layer.alert('备注要少于2000字');
            return;
        }
        if(vm.newManuscriptManuscript.people && vm.newManuscriptManuscript.people.length > 200){
            layer.alert('人物要少于200字');
            return;
        }
        if(!vm.newManuscriptManuscript.keywords){
            layer.alert('请填写关键词');
            return;
        }
        if(vm.newManuscriptManuscript.keywords && vm.newManuscriptManuscript.keywords.length > 200){
            layer.alert('关键词要少于200字');
            return;
        }
        //add by liu.jinfeng@20170912
        vm.finalSortParam = getChildNodesSortId();
        if(vm.finalSortParam == ''){
            layer.alert('请选择稿件类别');
            return;
        }
        if((!vm.newManuscriptManuscript.selProv||!vm.newManuscriptManuscript.selCity) && (!vm.newManuscriptManuscript.abroadPlace)){
            layer.alert('请填写地点');
            return;
        }
        if(vm.newManuscriptManuscript.abroadPlace && vm.newManuscriptManuscript.abroadPlace.length > 200){
            layer.alert('地点要少于200字');
            return;
        }
        if(!vm.photoUserName){
            layer.alert('请填写作者');
            return;
        }
        //获取地点参数
        if(vm.locationType == 0){
        	if(!vm.newManuscriptManuscript.selCounty){
        		 vm.newManuscriptManuscript.place = vm.newManuscriptManuscript.selProv + ' ' + vm.newManuscriptManuscript.selCity;
        	}else{
        		vm.newManuscriptManuscript.place = vm.newManuscriptManuscript.selProv + ' ' + vm.newManuscriptManuscript.selCity+ ' ' + vm.newManuscriptManuscript.selCounty;
        	}
        	
           
        }
        if(vm.locationType == 1){
            vm.newManuscriptManuscript.place = vm.newManuscriptManuscript.abroadPlace;
        }
        if(vm.upMenuscriptPicArr.length == 0){
            layer.alert('请上传图片!');
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
            if(!vm.upMenuscriptPicArr[i].keywords){
                layer.alert('请填写关键词！');
                return;
            }
            if (vm.upMenuscriptPicArr[i].keywords && vm.upMenuscriptPicArr[i].keywords.length > 200) {
                layer.alert('关键词要少于200字');
                return;
            }
            if (vm.upMenuscriptPicArr[i].authorName && vm.upMenuscriptPicArr[i].authorName.length > 200) {
                layer.alert('作者要少于200字');
                return;
            }
            if (vm.upMenuscriptPicArr[i].memo && vm.upMenuscriptPicArr[i].memo.length > 4000) {
                layer.alert('图片说明要少于4000字');
                return;
            }
            
        }
        validUpMsPicFlag = true;
        if(callback && validUpMsPicFlag) callback();
    }
    //保存或提交请求
    function req_saveManuscript(type){
        var picParam = angular.toJson(vm.manuscriptPicData,true);
/*        //保存或提交请求url
        var saveGroupUrl = '';
        //稿件类型：0代表中文，1代表英文
        var langType = '0';

        //vm.selManuscript等于1时为中文稿件
        if(vm.selManuscript == 1){
            saveGroupUrl = 'groupPicCtro/saveGroupPic.do';
            langType = '0';
       //vm.selManuscript等于1时为英文稿件
        }else if(vm.selManuscript == 2){
            saveGroupUrl = 'groupPicCtro/editFristGroupPic.do';
            langType = '1';
        }*/
        
        var saveGroupUrl = 'groupPicCtro/saveGroupPic.do';
		var videoId = document.getElementById("selmasvideo").value;
		//console.info("============videoId:"+videoId);
        req.post(saveGroupUrl,{
            type: type,
            fTime: vm.nowfTime,
            picData: picParam,
            authorId:  vm.authorId + '',
            author: vm.photoUserName,
            title: vm.newManuscriptManuscript.title,
            people: vm.newManuscriptManuscript.people,
            keywords: vm.newManuscriptManuscript.keywords,
            locationType: vm.locationType,
            place: vm.newManuscriptManuscript.place,
            memo: vm.newManuscriptManuscript.memo,
            remark: vm.newManuscriptManuscript.remark,
            langType: lang,
            properties: vm.acitiveSlideTit,
            roleId: vm.adminRoleId,
            videoId:videoId,
            cateIds: vm.finalSortParam
        }).success(function(resp){
            layer.close(vm.loadUpMs);
            if(resp && resp.code == '211'){
               //console.log('success');
                layer.msg('操作成功');
                if(type == '0'){
                    $state.go('role.manager.draftbox');
                }
                if(type == '1'){
                    if(vm.adminRoleId == 2){
                        $state.go('role.manager.sendManuscript');
                    }else{
                        $state.go('role.manager.myManuscript');
                    }
                }
            }else if(resp && resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        }).error(function(){
            layer.close(vm.loadUpMs);
            //console.log('error');
            //todo 因为net::ERR_INCOMPLETE_CHUNKED_ENCODING错误暂时这么处理
            if(type == '0'){
                $state.go('role.manager.draftbox');
            }
            if(type == '1'){
                if(vm.adminRoleId == 2){
                    $state.go('role.manager.sendManuscript');
                }else{
                    $state.go('role.manager.myManuscript');
                }
            }

        });
    }

});