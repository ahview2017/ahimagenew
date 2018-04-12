/**
 * Created by Sun on 2016/12/14.
 */
adminModule.controller('mSendManuscriptEditCtrl', function($scope,$sce,$cookies, req, md5, $state, $rootScope, cityList, $stateParams ,$filter, modalOperate){
	
    var vm = this;
    if (typeof $stateParams.id === 'undefined') {
        return;
    }
    if (typeof $stateParams.dtType === 'undefined') {
        return;
    }
    //页面初始化相关设置
    function initSetting(){
        //获取从路由传过来的稿件id
        vm.groupId = $stateParams.id;
        //获取从路由传过来的稿件值别
        vm.dtType = $stateParams.dtType;
        //获取从路由传过来是否是强制解锁
        vm.type = $stateParams.type;
        //获得城市列表
        vm.msCityList = cityList.citylist;
        vm.cities = [];
        //默认激活的导航项为全部稿件
        vm.acitiveSlideTit = 0;
        //默认选中境内稿签
        vm.locationType =1;
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
        //vm.photoUNameWay = '0';
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
            selCounty:'',
            abroadPlace:'',
            price:'',
            editor:''
        }

        //默认输入时显示选择作者的列表div
        vm.showNameFlag = false;
        //初始化涉外作者信息
        vm.authorInfo  = {
            loginName:'',
            authorName:'',
            trueName:'',
            pwd:'',
            mail:'',
            phone:''
        }
        
        vm.netFlag = false;//内外网标识，true表示外网，false表示内网,默认是内网
        
        //是否采用搜索方式选择作者，默认无
        vm.hasSeledUNameFlag = false;
        //是否采用添加社外作者方式选择作者，默认无
        vm.addAuthorItemFlag = false;

        //存放已选的稿件分类数组
        vm.manuscriptCategoryId = [];
        //存放已选的稿件分类名数组
        vm.msCategoryNameArr = [];
        // 已选择的编辑分类ID
        vm.editMsSortsId = '';
       // 已选择的编辑分类名（此处分类名只供显示，不发生实际作用）
        vm.categoryNameStr = ''
       // 接收最终传递到编辑接口的分类id的变量
        vm.finalSortParam = '';
        
        //专题分类名称
        vm.specialCategoryNameStr = [];
        
        vm.isSign = 1;
        
        vm.coverPicPath = "";
        
        vm.fileName = "";
        
        vm.oriPicId = 0;
 
    }
    
    
   
    
    
  //人物、关键词、稿件说明快速复制 add by xiayunan@20171011
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

    
    //一键清除样式 add by xiayunan@20171204
    vm.cleanWord=function(){
    	req.post('groupPicCtro/cleanWord.do',{
    		memo: vm.editManuscript.memo,
    		remark: vm.editManuscript.remark
        }).success(function(resp){
            if(resp.code == 211){
            	vm.editManuscript.memo = resp.data.memo;
            	vm.editManuscript.remark = resp.data.remark;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    
    
    function isChinese(temp) { 
    	var re = /[^\u4e00-\u9fa5\u0020]/; 
    	if(re.test(temp)){
    		return false;
    	}
    	return true; 
    } 
    
    
    function isPhoneNum(temp){
    	var reg = /^1[3|4|5|7|8][0-9]{9}$/;
    	if(reg.test(temp)){
    		return true;
    	}
    	return false;
    }
    //初始化时展示详情
    function showMenuscriptDetail(){
        //初始化地点信息
        vm.changeProv(vm.manuscriptPlaceArr[0]);
        vm.changeCity(vm.manuscriptPlaceArr[1]);
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
            selCity: vm.manuscriptPlaceArr[1] || '',
            selCounty: vm.manuscriptPlaceArr[2] || '',
            abroadPlace: vm.manuscriptDetail.abroadPlace
        };
        //初始化展示地点（从详情获取）
        vm.locationType = vm.manuscriptDetail.locationType;
        //摄影师名(从详情里取得)
        vm.photoUserName = vm.manuscriptAuthor;
        //从详情里获得定价不定价不出售
        vm.selDraftPriceBtn = vm.manuscriptDetail.priceType;
        if(!vm.editManuscript.price){
            vm.editManuscript.price = vm.manuscriptDetail.price;
        }
        //初始化类别
        angular.forEach(vm.manuscriptCates,function(item,index){
            vm.manuscriptCategoryId.push(item.id);
            vm.msCategoryNameArr.push(item.categoryName);
        });
        // 从详情回显类别Id
        vm.finalSortParam = vm.manuscriptCategoryId.join();
        // 从详情回显类别名
        vm.categoryNameStr = vm.msCategoryNameArr.join();

        //处理详情里的图片信息
        angular.forEach(vm.manuscriptPicResult,function(item,index){
            vm.upMenuscriptPicArr.push({
                id: item.id + '',
                filmTime: $filter('date')(item.filmTime,'yyyy-MM-dd'),
                img: item.smallPath,
                wmImg: item.wmPath,
                isCover: item.isCover + '',
                isSign: item.isSign==null?'0':item.isSign+'',//图片签网标识 add by xiayunan@20180306
                sortId: item.sortId + '',
                people: item.people,
                keywords: item.keywords,
                authorName: item.authorName,
                memo: item.memo
            })
        });
    }
	// 编辑稿件分类模态框显示
    vm.selectMasVideo = function(){
        //window.open(vm.masBaseUrl+"&method=list");
    	window.open(vm.masBaseUrl+"&method=list&netFlag="+vm.netFlag);
    }

    //页面初始化
    function init(){
        initSetting();
		getMasBaseUrl();
        getselCpCategories(function(category){
//            angular.forEach(category,function(item,index){
//                if(item.categoryName == '新闻类别'){
//                    vm.categories = item.categories;
//                    loadEditSortZTree();
//                }
//            });
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
        getManuscriptDetails(function(){
            showMenuscriptDetail();
            loadEditSortZTree();
        });

    }
    init();

	//获取Mas视频基础URL
	function getMasBaseUrl(){
		req.get('groupPicCtro/getMasBaseUrl.do').success(function(resp) {
			if(resp.code == '211') {
				vm.masBaseUrl = resp.data.masBaseUrl;
				//获取
				var netFlag = window.location.href;
				if(netFlag.indexOf("vi.ahnews.com.cn")>=0){
					vm.netFlag = true;//外网标识
				}
			}else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}


    //获取稿件详情
    function getManuscriptDetails(callback){
        req.post('groupPicCtro/getGroupPics.do',{
            groupId: vm.groupId
        }).success(function(resp){
            if(resp.code == '211'){
                vm.manuscriptDetail = resp.data;
                
                vm.coverPicPath = vm.manuscriptDetail.pics[0].wmPath;
                vm.coverPicPath = vm.coverPicPath.replace("watermarkedmedium","classification");
                vm.fileName = vm.manuscriptDetail.pics[0].filename;
                vm.oriPicId = vm.manuscriptDetail.pics[0].id;
                
                if(vm.manuscriptDetail.videoId!=null&&vm.manuscriptDetail.videoId!=0){
					vm.masUrl = vm.masBaseUrl+"&method=exPlay&type=vod&id="+vm.manuscriptDetail.videoId;
					//vm.masUrl = "http://192.168.18.85:8081/mas/openapi/pages.do?method=exPlay&appKey=TRSPMS123&type=vod&id="+vm.manuscriptDetail.videoId;
					$(".smt-detail-video").show();
				}
				vm.masUrl =  $sce.trustAsResourceUrl(vm.masUrl);
                vm.groupStatus = resp.data.groupStatus;
                vm.manuscriptPlaceArr = resp.data.place.split(' ');
                if(vm.manuscriptPlaceArr.length==1){
                	vm.manuscriptDetail.abroadPlace = resp.data.place;
                }
                vm.manuscriptPicResult = resp.data.pics;
                vm.manuscriptProperties = resp.data.properties;
                
                vm.acitiveSlideTit = vm.manuscriptProperties;//add by xiayunan@20180208
                
                vm.manuscriptPros = resp.data.pros;
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
    // 加载编辑分类树 edit by xiayunan@20180208 添加专题分类
    function loadEditSortZTree(){
    	if(vm.acitiveSlideTit==0){
    		$.fn.zTree.init($("#edit_sort_tree"), setting, vm.selCpCategories);
    		$.fn.zTree.init($("#edit_special_tree"), setting, null);
    	}else if(vm.acitiveSlideTit==1){
    		$.fn.zTree.init($("#edit_sort_tree"), setting, vm.selCpCategories);
    		$.fn.zTree.init($("#edit_special_tree"), setting, vm.selSpecialCategories);
    	}
       // $.fn.zTree.init($("#edit_sort_tree"), setting, vm.selCpCategories);
        selectedEverSorts();
    }
    // 编辑稿件分类模态框显示
    vm.editMsSortsModalShow = function(){
         $('#edit-sort-modal').modal('show');
        loadEditSortZTree();
    }
    
    
    vm.returnSendModalShow = function(modalId){
    	 modalOperate.modalShow(modalId);
    }

	

    // 获取子节点的分类id，多个用英文逗号隔开
   function getChildNodesSortId(){
       var cateIdParamArr = [];
       var categoryNameArr  = [];
       
       var specialCateIdParamArr = [];
       var specialCategoryNameArr  = [];
       var cateIdParamStr = '';
       var treeObj = $.fn.zTree.getZTreeObj("edit_sort_tree");
       var nodes = treeObj.getCheckedNodes(true); //获取选中的值
       for(var i = 0, nodesLen = nodes.length; i < nodesLen; i++){
           // 排除父节点
           //if(!nodes[i].isParent){
               cateIdParamArr.push(nodes[i].id);
               categoryNameArr.push(nodes[i].categoryName);
//               cateIdParamStr = cateIdParamArr.join();
//               vm.categoryNameStr = categoryNameArr.join();
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

    // 确定编辑稿件分类
    vm.confirmEditMsSorts = function(){
        if(getChildNodesSortId() == ''){
            layer.alert('请选择稿件类别');
            return;
        }
        $('#edit-sort-modal').modal('hide');
    }

    //获取稿件分类信息
    function getselCpCategories(callback){
        req.post('classification/selCpCategories.do',{
	langType:window.localStorage.lang}).success(function(resp){
            if(resp.code == '211'){
            	category = resp.data;
//            	angular.forEach(category, function (item, index) {
//            		if (item.categoryName == '新闻类别') {
//            			vm.selCpCategories  = item.categories;
//	                }
//	            });
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

    //查询摄影师：用于新建稿件搜索作者
    vm.getPhotoUser = function(event){
        vm.hasSeledUNameFlag = false;
        vm.addAuthorItemFlag = false;
        req_getPhotoUser();
        vm.showNameFlag = true;
    }
    //编辑稿件模态框隐藏
    vm.editMenuscriptModalHide = function(modalId){
        modalOperate.modalHide(modalId);
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
    
    //图片裁剪模态框显示	add by xiayunan@20180402
    vm.picCutShow = function(modalId){
        modalOperate.modalShow(modalId);
        
        /************  图片裁剪 start *****************/
        
        
    	jQuery(function($){
    		
    	    var jcrop_api;

    	    $('#target').Jcrop({
    	      onChange:   showCoords,
    	      onSelect:   showCoords,
    	      boxWidth:600,
    	      onRelease:  clearCoords
    	    },function(){
    	      jcrop_api = this;
    	    });
    	    
    	    $('#coords').on('change','input',function(e){
    	      var x1 = $('#x1').val(),
    	          x2 = $('#x2').val(),
    	          y1 = $('#y1').val(),
    	          y2 = $('#y2').val();
    	      jcrop_api.setSelect([x1,y1,x2,y2]);
    	    });

    	  });
    	  
    	
    	  
    	
    	/************  图片裁剪 end   ****************/
        
        
    }
    
    function showCoords(c){
	    $('#x1').val(c.x);
	    $('#y1').val(c.y);
	    $('#x2').val(c.x2);
	    $('#y2').val(c.y2);
	    $('#w').val(c.w);
	    $('#h').val(c.h);
	};

    function clearCoords(){
        $('#coords input').val('');
    };
    
    vm.initAspectRadio = function(){
    	if (!parseInt($('#aspect-radio').val())){
    	  	layer.alert('请填写需要初始化的宽高比');
    	  	return false;
    	}; 
    	jQuery(function($){
    	    var jcrop_api;
    	    $('#target').Jcrop({
    	      onChange:   showCoords,
    	      onSelect:   showCoords,
    	      aspectRatio: vm.aspectRadio,
    	      boxWidth:600,
    	      onRelease:  clearCoords
    	    },function(){
    	      jcrop_api = this;
    	    });
    	    
    	    $('#coords').on('change','input',function(e){
    	      var x1 = $('#x1').val(),
    	          x2 = $('#x2').val(),
    	          y1 = $('#y1').val(),
    	          y2 = $('#y2').val();
    	      jcrop_api.setSelect([x1,y1,x2,y2]);
    	    });

    	  });
    	
    	  

    	
    }
    
    vm.picCutHide = function(modalId){
        modalOperate.modalHide(modalId);
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


    //改变主图副图
    vm.changePicIsCover = function(isCover,index){
        if(isCover == '1'){
            for(var i = 0; i <  vm.upMenuscriptPicArr.length; i++){
                vm.upMenuscriptPicArr[i].isCover = '0';
            }
            vm.upMenuscriptPicArr[index].isCover = '1';
            vm.upMenuscriptPicArr[index].isSign= '0';//主图网站必须显示 add by xiayunan@20180306
        }
    }
    //选择境内外稿签
    vm.homeAbroadToggle = function(locationType){
        vm.locationType = locationType;
    }

    //选择限价不限价
    vm.draftPriceToggle = function(selDraftPriceBtn){
        vm.selDraftPriceBtn = selDraftPriceBtn;
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
                vm.bIsExif = true;
                uploadedPicCallback(j);
                displayed[j] = true;
                if(!vm.bIsExif){
                	layer.alert("上传不包含Exif信息的图片失败");
                	if((j+1) < vm.upMsTotalLen){
               		 req_upMs(j+1);
                	}
               		 
                }else if(j === vm.upMsTotalLen -1){
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
        });
    }
    
    
    
    
    /**
     * 裁剪并上传请求
     * @returns
     */
    vm.cropAndUpPic = function(){
    	if (!parseInt($('#w').val())){
    	  	layer.alert('请选择裁剪区域');
    	  	return false;
    	}; 
    	vm.loadUpMs = layer.load(1);
    	
        var formdata = new FormData();
        formdata.append("langType",lang);
        formdata.append("x1",$('#x1').val());
        formdata.append("x2",$('#x2').val());
        formdata.append("y1",$('#y1').val());
        formdata.append("y2",$('#y2').val());
        formdata.append("width",$('#w').val());
        formdata.append("height",$('#h').val());
        formdata.append("oriPicPath",vm.coverPicPath);
        formdata.append("fileName",vm.fileName);
        formdata.append("oriPicId",vm.oriPicId);
        $.ajax({
            type: "POST",
            data: formdata,
            url: "/photo/groupPicCtro/CutAndUpPic.do" + '?time=' + new Date().getTime(),
            cache: false,
            contentType: false,     //必须false才会自动加上正确的Content-Type
            processData: false,     //必须false才会避开jQuery对formdata的默认处理
            async: true
        }).success(function (resp) {
        	layer.close(vm.loadUpMs);
            if(resp.code && resp.code == '211'){
                vm.uploadCropEditPicList = resp.data;//vm.uploadCropEditPicList为裁剪图片列表
                vm.bIsExif = true;
                uploadedCropPicCallback();
                if(!vm.bIsExif){
                	layer.alert("上传不包含Exif信息的图片失败");
                }
                layer.alert("裁图成功");
                modalOperate.modalHide('edit-piccut-modal');//隐藏裁图框
                
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        }).error(function (resp) {
        	layer.close(vm.loadUpMs);
        });
    }
    
    function uploadedCropPicCallback(){
    	if(vm.uploadCropEditPicList){
    		angular.forEach(vm.upMenuscriptPicArr,function(item,index){
    			if(item.img.indexOf("crop")!=-1){
    				vm.isExit = true;
    				vm.upMenuscriptPicArr[index].id = vm.uploadCropEditPicList.id + '';
    				vm.upMenuscriptPicArr[index].filmTime = $filter('date')(vm.uploadCropEditPicList.filmTime,'yyyy-MM-dd');
    				vm.upMenuscriptPicArr[index].img = vm.uploadCropEditPicList.smallPath;
    				vm.upMenuscriptPicArr[index].wmImg = vm.uploadCropEditPicList.wmPath;
    				vm.upMenuscriptPicArr[index].isCover = '1';
    				vm.upMenuscriptPicArr[index].isSign = '0';
    				vm.upMenuscriptPicArr[index].people = vm.uploadCropEditPicList.people;
    				vm.upMenuscriptPicArr[index].keywords = vm.uploadCropEditPicList.keywords;
    				vm.upMenuscriptPicArr[index].authorName = vm.uploadCropEditPicList.authorName;
    				vm.upMenuscriptPicArr[index].memo = vm.uploadCropEditPicList.memo;
    				
    				 //默认上传的第一个为主图
                    //vm.upMenuscriptPicArr[0].isCover = '0';
                    //vm.upMenuscriptPicArr[0].isSign = '1';//add by xiayunan@20180306
    			}
    			
    		});
    		if(!vm.isExit){
    			for(var i = 0; i <  vm.upMenuscriptPicArr.length; i++){
                    vm.upMenuscriptPicArr[i].isCover = '0';
                }
    			vm.upMenuscriptPicArr.unshift({//
                    id: vm.uploadCropEditPicList.id + '',
                    filmTime: $filter('date')(vm.uploadCropEditPicList.filmTime,'yyyy-MM-dd'),
                    img: vm.uploadCropEditPicList.smallPath,
                    wmImg: vm.uploadCropEditPicList.wmPath,
                    isCover: '1',
                    isSign: '0',//图片签网标识 add by xiayunan@20180306
                    sortId: (0) + '',
                    people: vm.uploadCropEditPicList.people,
                    keywords: vm.uploadCropEditPicList.keywords,
                    authorName: vm.uploadCropEditPicList.authorName,
                    memo: vm.uploadCropEditPicList.memo
                })
                

    		}
    	}
    	
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
                    wmImg: item.wmPath,
                    isCover: '0',
                    isSign: '0',//图片签网标识 add by xiayunan@20180306
                    sortId: (index + 1) + '',
                    people: item.people,
                    keywords: item.keywords,
                    authorName: item.authorName,
                    memo: item.memo
                })
                //默认上传的第一个为主图
                if(index == '0'){
                    vm.upMenuscriptPicArr[0].isCover = '1';
                    vm.upMenuscriptPicArr[0].isSign = '0';//add by xiayunan@20180306
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
            }
        }
        return false; // 必须返回false，否则表单会自己再做一次提交操作，并且页面跳转



      /*  // jquery 表单提交
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
                vm.uploadEditPicList = resp.data;
                if(resp.code == '211'){
                    if(callback) callback();
                }else if(resp.msg != '未登录'){
                    layer.alert(resp.msg);
                }
            });
        }
        return false; // 必须返回false，否则表单会自己再做一次提交操作，并且页面跳转*/
    }


    //保存待发稿件
    vm.saveEditManuscript = function(){
        judgeGradeEditReqUrl(function(){
            valid_editPramas(function(){
                getPlaceParams();
                getPicDataParams();
                vm.loadUpMs = layer.load(1);
                req_saveEditManuscript();
            });
        });

    }

    //获取图片的相关参数
    function getPicDataParams(){
        //获取picData参数
    	console.log("<<<vm.upMenuscriptPicArr:"+vm.upMenuscriptPicArr);
        angular.forEach(vm.upMenuscriptPicArr,function(item,index){
            vm.manuscriptPicData.push({
                id: item.id + '',
                people: vm.upMenuscriptPicArr[index].people || '',
                keywords: vm.upMenuscriptPicArr[index].keywords || '',
                authorId:  vm.authorId + '',
                authorName: vm.upMenuscriptPicArr[index].authorName || '',
                memo: vm.upMenuscriptPicArr[index].memo || '',
                isCover: vm.upMenuscriptPicArr[index].isCover,
                isSign: vm.upMenuscriptPicArr[index].isSign,//图片签网标识 add by xiayunan@20180306
                sortId: (index + 1) + '',
                filmTime: vm.upMenuscriptPicArr[index].filmTime + ' 00:00:00'
            })
        });
      
    }
    //获取地点参数
    function getPlaceParams(){
        if(vm.locationType == 0){
        	if(!vm.editManuscript.selCounty){
       		 	vm.editManuscript.place = vm.editManuscript.selProv + ' ' + vm.editManuscript.selCity;
	       	}else{
	       		vm.editManuscript.place = vm.editManuscript.selProv + ' ' + vm.editManuscript.selCity+ ' ' + vm.editManuscript.selCounty;
	       	}

        }
        if(vm.locationType == 1){
            vm.editManuscript.place = vm.editManuscript.abroadPlace;
        }
    }
    //区分一二三级编辑请求url
    function judgeGradeEditReqUrl(callback){
        vm.editGropPicUrl = '';
        if(vm.groupStatus == 1){
            vm.editGropPicUrl = 'groupPicCtro/editFristGroupPic.do';
        }
        if(vm.groupStatus == 2){
            vm.editGropPicUrl = 'groupPicCtro/editSecondGroupPic.do';
        }
        if(vm.groupStatus == 3){
            vm.editGropPicUrl = 'groupPicCtro/editThreeGroupPic.do';
        }
        if(callback) callback();
    }
    // 验证编辑相关参数
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
            layer.alert('请填写新闻说明');
            return;
        }
        if(vm.editManuscript.memo && vm.editManuscript.memo.length > 2000){
            layer.alert('新闻说明要少于2000字');
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
        if(vm.editManuscript.keywords && vm.editManuscript.keywords.length > 50){
            layer.alert('关键词要少于50字');
            return;
        }
        if((!vm.editManuscript.selProv||!vm.editManuscript.selCity) && (!vm.editManuscript.abroadPlace)){
            layer.alert('请填写地点');
            return;
        }
        
        if(vm.editManuscript.abroadPlace && vm.editManuscript.abroadPlace.length > 100){
            layer.alert('地点要少于100字');
            return;
        }
        
        if(!isChinese(vm.photoUserName)&&!isPhoneNum(vm.photoUserName)){
            layer.alert('作者名请填写中文');
            return;
        }
        
        
        if(!vm.authorName){
            layer.alert('请填写作者');
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
        
        
        //验证价格参数
        if(vm.editManuscript.price && !(/^\d+$/.test(vm.editManuscript.price))){
            layer.alert('定价必须为整数!');
            return;
        }
        //验证图片相关信息填写是否合格
        var validUpMsPicFlag = false;
        
        
        //验证图片列表相关信息
        for(var i = 0, len = vm.upMenuscriptPicArr.length; i < len; i++) {
        	if(vm.upMenuscriptPicArr[i].isCover==1){
        		validUpMsPicFlag = true;
        		if(vm.upMenuscriptPicArr[i].isSign==1){
        			layer.alert("主图必须选择网站显示！");
        			return;
        		}
        	}
        	
            if (vm.upMenuscriptPicArr[i].people && vm.upMenuscriptPicArr[i].people.length > 50) {
                layer.alert('人物要少于50字');
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
        if(!validUpMsPicFlag){
        	layer.alert("请选择主图");
        }
        //validUpMsPicFlag = true;
        if(callback && validUpMsPicFlag) callback();
    }
    //编辑稿件请求
    function req_saveEditManuscript(){
        //编辑稿件请求
		var videoId = $("#selmasvideo").val();
        req.post(vm.editGropPicUrl,{
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
            // cateIds : vm.manscriptCateId,
            cateIds: vm.finalSortParam,
            priceType: vm.selDraftPriceBtn,
            price: vm.editManuscript.price,
            roleId: vm.adminRoleId,
            type: vm.type,
			videoId:videoId
        }).success(function(resp){
            layer.close(vm.loadUpMs);
            if(resp.code == '211'){
                layer.msg(resp.msg);
                //$rootScope.msSensitiveWord=false;
                $state.go('role.manager.sendManuscriptDetail',{id: vm.groupId,dtType:vm.dtType,type:vm.type});
            }else if(resp.code == '212'){
                $state.go('role.manager.sendManuscriptDetail',{id: vm.groupId,dtType:vm.dtType,type:vm.type});
                //code为212时代表存在敏感词时
                if(resp.msg){
                    $rootScope.msSensitiveWord = resp.msg;
                    layer.msg(resp.msg);
                }
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        }).error(function(resp){
            layer.close(vm.loadUpMs);
            //todo 因为net::ERR_INCOMPLETE_CHUNKED_ENCODING错误暂时这么处理
            $state.go('role.manager.sendManuscriptDetail',{id: vm.groupId,dtType:vm.dtType});
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
                $state.go('role.manager.sendManuscriptDetail',{id: vm.groupId,dtType:vm.dtType});
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
	
    
    //add by xiayunan@20171215
    vm.confirmSave = function(modalId){
    	modalOperate.modalHide(modalId);
    	vm.saveEditManuscript();
    }
    
    vm.cancelSave = function(modalId){
    	modalOperate.modalHide(modalId);
    	vm.returnSendManuscript();
    	
    }

    
    

});