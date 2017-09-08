adminModule.controller('downloaddataStatisticsCtrl', function(req, $state, $scope, $stateParams) {
	var vm = this;

	//初始化标题
	vm.title = '';
	//默认显示今日下载 0
	vm.selectType = 0;
	//默认当前页1
	vm.pagination = {
		current: 1
	};
	//是否为内部下载,默认为不是
	vm.isInteriorDownLoad = 0;
	//默认每页10条
	vm.selPageRows = '10';
	//判断显示下载统计的模块
	if($stateParams.rightId == 402) {
		vm.title = '今日下载';
		vm.selectType = 1;
	} else if($stateParams.rightId == 403) {
		vm.title = '昨日下载';
		vm.selectType = 2;
	} else if($stateParams.rightId == 404) {
		vm.title = '本周下载';
		vm.selectType = 3;
	} else if($stateParams.rightId == 405) {
		vm.title = '本月下载';
		vm.selectType = 4;
	} else if($stateParams.rightId == 406) {
		vm.title = '上月下载';
		vm.selectType = 5;
	} else if($stateParams.rightId == 407) {
		vm.title = '全部下载';
		vm.selectType = 0;
	} else if($stateParams.rightId == 408) {
		vm.title = '内部下载';
		vm.selectType = 6;
		vm.isInteriorDownLoad = 1;
	}
	//初始化
	getdownloaddatalist();
	vm.pictureAuthor = '';
	vm.pictureTitle = '';
	vm.authorId = '';
	vm.authorLoginName = '';
	vm.editUser = '';
	vm.pictureFileName = '';
	vm.orderByCasetext = '';
	vm.pictureGroup = '';
	vm.downStartTime = '';
	vm.downEndTime = '';
	vm.signStartTime = '';
	vm.signEndTime = '';
	vm.showtypetext = true;
	//获取下载记录
	function getdownloaddatalist() {
		req.post('downLoadStatisticsCtrl/showDownLoadStatistics.do', {
			selectType: vm.selectType,
			langtype: window.localStorage.lang,
			rows: vm.selPageRows,
			page: vm.pagination.current,
			pictureAuthor: vm.pictureAuthor,
			authorId: vm.authorId,
			authorLoginName: vm.authorLoginName,
			pictureTitle: vm.pictureTitle,
			editUser: vm.editUser,
			pictureFileName: vm.pictureFileName,
			orderByCase: vm.orderByCasetext,
			pictureGroup: vm.pictureGroup,
			downStartTime: vm.downStartTime,
			downEndTime: vm.downEndTime,
			signStartTime: vm.signStartTime,
			signEndTime: vm.signEndTime
		}).success(function(resp) {
			if(resp && resp.code == '211') {
				console.log('success');
				vm.downloaddatalist = resp.data;
				vm.downloaddatalist_total = resp.other;
				vm.totalPages = resp.page;
				console.log(vm.downloaddatalist);
			} else if(resp && resp.msg != '未登录') {
				layer.alert(resp.msg);
			}

		});
	}
	//搜索
	vm.search = function() {
		vm.pagination.current = 1;	
		//判断时间搜索条件为一个时，不作为检索条件
		if((vm.downStartTime&&!vm.downEndTime)||(!vm.downStartTime&&vm.downEndTime)){
			 layer.msg('下载时间区间只填一个时不作为检索条件');
		}
		if((vm.signStartTime&&!vm.signEndTime)||(!vm.signStartTime&&vm.signEndTime)){
			 layer.msg('签发时间区间只填一个时不作为检索条件');
		}
		//判断显示方式
		if(vm.showtype!=2) {
			vm.showtypetext = true;
		}
		if(vm.showtype == 2) {
			vm.showtypetext = false;
		}
		//判断排序字段
		if(vm.orderByCase==1){
			vm.orderByCasetext='DOWNLOAD_TIME';
		}
		if(vm.orderByCase==2){
			vm.orderByCasetext='USERNAME';
		}
		if(vm.orderByCase==3){
			vm.orderByCasetext='PICTURE_FILE_NAME';
		}
		if(vm.orderByCase==4){
			vm.orderByCasetext='PICTURE_AUTHOR';
		}
		getdownloaddatalist();

	}
	//页数变化
	vm.pageChanged = function(pageNumber) {
		getdownloaddatalist();
	};
	//获取稿件类别
	req.post('classification/selCpCategories.do', {
		langType: window.localStorage.lang
	}).success(function(resp) {
		if(resp.code == '211') {
			category = resp.data;
			angular.forEach(category, function(item, index) {
				if(item.categoryName == '新闻类别') {
					vm.selCpCategories = item.categories;
					console.log(vm.selCpCategories);
				}
			});
		} else if(resp.msg != '未登录') {
			layer.alert(resp.msg);
		}
	});
   //删除下载记录
   vm.deleteDownLoaddata='';
   vm.deleteDownLoadStatistics=function(){
   	  vm.deleteId='';
   	  $('input.deleteDownLoaddata:checked').each(function(){
   	  	 vm.deleteId+=$(this).attr('value')+','
   	  });
   	  req.post('downLoadStatisticsCtrl/deleteDownLoadStatistics.do', {
			id:vm.deleteId
		}).success(function(resp) {
			if(resp && resp.code == '211') {
				layer.alert('删除成功');
				getdownloaddatalist();
			} else if(resp && resp.msg != '未登录') {
				layer.alert(resp.msg);
			}

		});
   }
   //处理全选
   vm.deletedateall=function(){
   	 $('input.deleteDownLoaddata').prop('checked',$('input.deletedateall').prop('checked'));
   }
   
   //导出数据
   vm.exportExcel=function(){
   	document.location = "/photo/downLoadStatisticsCtrl/exportExcel.do?selectType="
   	 +vm.selectType+"&langtype="+window.localStorage.lang+"&rows="+vm.selPageRows+"&page="+vm.pagination.current+
   	 "&pictureAuthor="+vm.pictureAuthor+"&authorId="+vm.authorId+"&authorLoginName="+vm.authorLoginName+
   	 "&pictureTitle="+vm.pictureTitle+"&editUser="+vm.editUser+"&pictureFileName="+vm.pictureFileName+
   	 "&orderByCase"+vm.orderByCasetext+"&pictureGroup="+vm.pictureGroup+"&downStartTime="+vm.downStartTime+
   	 "&downEndTime="+vm.downEndTime+"&signStartTime="+vm.signStartTime+"&signEndTime="+vm.signEndTime;
   
   	 }
   
})