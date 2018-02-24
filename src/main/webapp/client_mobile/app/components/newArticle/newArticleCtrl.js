clientModule.controller('newArticleCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, $timeout, jugeGroupPos,cityList,$filter,modalOperate) {
    var vm = this;
    //初始化页面相关配置
    function initSetting() {
    	//省市县联动数据
		vm.msCityList = cityList.citylist;
		//存储picData数组
        vm.manuscriptPicData = [];
        //上传图片列表
        vm.upMenuscriptPicArr = [];
        vm.locationType = 0;
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


	//我要投稿  add by xiayunan@20171017
	

	// 关闭按钮
	vm.go_close = function() {
		$("#gray").hide();
		$(".login_box").hide();
		$(".register_box").hide();
		$(".newArticle_box").hide();
	}
	
	// 发送验证码
	var clock = '';
	var nums = 60;
	var start = true;
	vm.sendmsg = function(form) {
		var phoneCode = $("#phone_code").val();
		if (phoneCode == '') {
			layer.msg("请输入手机号");
			$('#phone_code').focus();
			return;
		}
		
		if(phoneCode==''||!(/^1[3,4,5,7,8]\d{9}$/.test(phoneCode))){
            layer.alert('请输入正确格式的移动电话');
            return;
        }

		if (nums == 60 && start) {
			start = false;
			$("#code").removeAttr('ng-click');
			$("#code").attr('disabled', "true");
			$("#code").html(nums + '秒后可重新获取');
			clock = setInterval(doLoop, 1000); // 一秒执行一次
			console.log("发送短信");
			req.post('phonemsg/getVilidateCodeForMobile.do', {
				phoneNum : $("#phone_code").val()
			}).success(function(resp) {
				if (resp.code != '211') {
					layer.alert(resp.msg);
				}
			});

		}
	}
	
	function doLoop() {
		nums--;
		if (nums > 0) {
			$("#code").html(nums + '秒后可重新获取');
		} else {
			clearInterval(clock); // 清除js定时器
			$("#code").html('获取验证码');
			$("#code").attr('ng-click', 'header.sendmsg()');
			$("#code").removeAttr('disabled');
			nums = 60; // 重置时间
			start = true;
		}
	}
	
	//改变省的时候
	vm.changeProv = function(prov) {
		for(var i = 0; i < vm.msCityList.length; i++) {
			if(prov == vm.msCityList[i].p) {
				vm.cities = vm.msCityList[i].c;
			}
		}
	};
	
	//获取首页栏目信息
	vm.getselChildColumn = function (pColumnId) {
        req_getselChildColumn(pColumnId);
    };
	
	// 初始化各市图库栏目信息
	function req_getselChildColumn(pColumnId) {
		req.post('enColumn/selChildColumn.do', {
			pColumnId : pColumnId
		}).success(function(resp) {
			if (resp.code == '211') {
				//各市图库
				if (pColumnId == 3080) {
                    vm.citys = resp.data;
                }
				//皖风徽韵
				if (pColumnId == 3078) {
                    vm.style = resp.data;
                }
				//艺苑菁华
				if (pColumnId == 3082) {
                    vm.essence = resp.data;
                }
				
			} else {
				console.log(resp.msg);
			}
		});
	}

    //页面初始化
    function init() {
        initSetting();
        //req_getClientPicture();
        //getClientGroupPics();
    }

    init();
	
   
    /**
     * 向数组指定位置插入数据
     * @param array 原数组
     * @param index 指定位置
     * @param item  插入元素
     */
    function insertIndexArrayFun(array, index, item) {
        return array.splice(index, 0, item);
    }


    
    //getOnlineUserList();
    // 获取在线用户列表
    function getOnlineUserList() {
        req.post('login/getOnLineUsers.do', {
            rows : '',
            page : ''
        }).success(function(resp) {
            if (resp.code == '211') {
                vm.OnLineUsersList = resp.data;
                vm.onlineList_total = resp.other;
            } else if (resp.msg != '未登录') {
                console.log(resp.msg);
            }
        });
    }
    //我要投稿
    vm.newManuscript = function(){
        window.location.href = "/photo/admin.html#/manager/newManuscript";
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
                    //keywords: item.keywords,
                    keywords: '游客',
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
    //上传图片请求
    function req_upMs(j){
    	if(vm.upMenuscriptPicArr.length>=5){
    		alert("最多只能上传5张图片");
    		return;
    	}
        var formdata = new FormData();
        formdata.append('picFiles', vm.upMsFiles[j]);
        formdata.append("langType",0);
      
        //console.log(formdata);
        $.ajax({
            type: "POST",
            data: formdata,
            url: "/photo/groupPicCtro/upPicForPhone.do" + '?time=' + new Date().getTime(),
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
                //上传图片进度条 add by xiayunan@20171128
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
                //keywords: vm.upMenuscriptPicArr[index].keywords || '',
                keywords: '游客',//修改每张图片关键词默认为游客  edit by xiayunan@20180117
                /*authorId:  vm.authorId + '',*/
                authorId:  '342',
                authorName: vm.upMenuscriptPicArr[index].authorName || '',
                memo: vm.upMenuscriptPicArr[index].memo || '',
                isCover: vm.upMenuscriptPicArr[index].isCover,
                sortId: (index + 1) + '',
                filmTime: vm.upMenuscriptPicArr[index].filmTime + ' 00:00:00'
            })
        });
        //console.log(vm.manuscriptPicData);
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
        req.post('classification/selCpCategoriesNoLogin.do',{langType:0}).success(function(resp){
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
        
        var saveGroupUrl = 'groupPicCtro/saveGroupPicForPhone.do';
        req.post(saveGroupUrl,{
            type: type,
            fTime: vm.nowfTime,
            picData: picParam,
            /*authorId:  vm.authorId + '',*/
            authorId:  '342',
            author: vm.phoneNum,
            title: vm.title,
            people: vm.people,
//            keywords: vm.keywords,
            keywords: '游客',
            locationType: vm.locationType,
//            place: vm.place,
            place:'安徽 合肥',
            memo: vm.memo,
            remark: vm.remark,
            langType: 0,
            properties: vm.acitiveSlideTit,
            roleId: vm.adminRoleId,
            //cateIds: vm.finalSortParam,
            cateIds: 1766,
            phoneNum:vm.phoneNum,
            valicode:vm.valicode
        }).success(function(resp){
            layer.close(vm.loadUpMs);
            if(resp && resp.code == '211'){
                layer.msg('提交成功');
                /*
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
                */
                window.location.href = "/photo/index_m.html#/";
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
    
    
    //保存或提交前的验证
    function valid_manuscriptInfo(callback){
        if(!vm.title){
            layer.alert('请填写标题');
            return;
        }
        if(vm.title && vm.title.length > 100){
            layer.alert('标题要少于100个字');
            return;
        }
        if(!vm.memo){
            layer.alert('请填写稿件说明');
            return;
        }
        if(vm.memo && vm.memo.length > 4000){
            layer.alert('稿件说明要少于4000字');
            return;
        }
        //add by liu.jinfeng@20170912
        
//        vm.finalSortParam = getChildNodesSortId();
//        if(vm.finalSortParam == ''){
//            layer.alert('请选择稿件类别');
//            return;
//        }
//        if(vm.remark && vm.remark.length > 2000){
//            layer.alert('备注要少于2000字');
//            return;
//        }
//        if(vm.people && vm.people.length > 200){
//            layer.alert('人物要少于200字');
//            return;
//        }
//        if(!vm.keywords){
//            layer.alert('请填写关键词');
//            return;
//        }
//        if(vm.keywords && vm.keywords.length > 200){
//            layer.alert('关键词要少于200字');
//            return;
//        }
        
//        if((!vm.selProv||!vm.selCity)){
//            layer.alert('请填写地点');
//            return;
//        }
        
        
        
        
        //获取地点参数
//        if(vm.locationType == 0){
//        		vm.place = vm.selProv + ' ' + vm.selCity;
//        }
        if(vm.upMenuscriptPicArr.length == 0){
            layer.alert('请上传图片!');
            return;
        }
        //验证图片相关信息填写是否合格
        var validUpMsPicFlag = false;
        //验证图片列表相关信息
        
        //去掉 单张图片验证 edit by xiayunan@20180117
        /*
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
        */
        if(!vm.phoneNum){
            layer.alert('请填写手机号');
            return;
        }
        
        if(!vm.valicode){
            layer.alert('请填写验证码');
            return;
        }
        validUpMsPicFlag = true;
        if(callback && validUpMsPicFlag) callback();
    }
    
    //提交
    vm.commitManuscript = function(){
        valid_manuscriptInfo(function(){
            getPicDataParams();
            vm.loadUpMs = layer.load(1);
            req_saveManuscript('1');
        });
    }
    
    
    
    
    
});