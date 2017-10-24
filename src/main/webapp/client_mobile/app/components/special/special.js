
clientModule.controller('specialCtrl', function($scope, $cookies, req, md5, $state, $rootScope, $stateParams) {
	var vm = this;
	$scope.topicId = $stateParams.id;
	/**
	 * 处理数据
	 * obj 实例化对象
	 * opt 参数对象
	 * */
	$scope.handle_special = function(obj, opt) {
		var h = [];
		for(var i = 0; i < opt.length; i++) {
			/**
			 * 按照模板类型进行渲染
			 * typeId 栏目类型
			 * 0 有子栏目
			 * 2 轮播图1
			 * 3 轮播图2
			 * 4 四展
			 * 5 五展
			 * 6 六展
			 * 8 八展
			 * 10 十展
			 * */
			//有子栏目
			if(opt[i].typeId == 0) {
				var arr1 = opt[i].cpLanmu1.cpLanmuPictures || [],
					arr2 = opt[i].cpLanmu2.cpLanmuPictures || [];
				var sonType1 = Number(opt[i].cpLanmu1.typeId),
					sonType2 = Number(opt[i].cpLanmu2.typeId);
				var sonClass1 = '',
					sonClass2 = '';
				switch(sonType1) {
					case 4:
						sonClass1 = 'col-md-3';
						break;
					case 5:
						sonClass1 = 'w-p-20';
						break;
					case 6:
						sonClass1 = 'col-md-4';
						break;
					case 8:
						sonClass1 = 'col-md-3';
						break;
					case 10:
						sonClass1 = 'w-p-20';
						break;
				}
				switch(sonType2) {
					case 4:
						sonClass2 = 'col-md-3';
						break;
					case 5:
						sonClass2 = 'w-p-20';
						break;
					case 6:
						sonClass2 = 'col-md-4';
						break;
					case 8:
						sonClass2 = 'col-md-3';
						break;
					case 10:
						sonClass2 = 'w-p-20';
						break;
				}
				h.push('<div class="panel panel-success img-back mt20"><div id="lanmu_' + opt[i].id + '" class="panel-heading clear"><h3 class="panel-title text-center text-shad">' + opt[i].name + '</h3></div><div class=""><img ng-src="' + opt[i].subAdds + '" class="img-thumbnail" alt="栏目图片"></div>');
				//子栏目1
				if(arr1.length != 0) {
					h.push('<div class="panel-body"><div class="son-title"><h5 class="fll ml5" style="height:30px;line-height:30px;">' + opt[i].cpLanmu1.name + '</h5><span ui-sref="root.moreContribute({id:' + opt[i].cpLanmu1.id + '})" class="flr cursor change-hover-color">more>></span></div><div class="row p5 n-margin">');
					for(var j = 0; j < arr1.length; j++) {
						h.push('<div class="col-xs-6 ' + sonClass1 + '"><div ui-sref="root.newDetailList({groupId: ' + arr1[j].groupId + '})" class="h-150 img-box"><img src="' + arr1[j].cpLanmuGroupPic.allpath + '" class="cursor img-thumbnail img-hover div-center" alt="Responsive image"></div><a ui-sref="root.newDetailList({groupId: ' + arr1[j].groupId + '})"><h5 class="mt20 photo-file-name change-hover-color">' + arr1[j].cpLanmuGroupPic.title + '</h5></a></div>');
					}
					h.push('</div></div>');
				}
				//子栏目2
				if(arr2.length != 0) {
					h.push('<div class="panel-body"><div class="son-title"><h5 class="fll ml5" style="height:30px;line-height:30px;">' + opt[i].cpLanmu2.name + '</h5><span ui-sref="root.moreContribute({id:' + opt[i].cpLanmu2.id + '})" class="flr cursor change-hover-color">more>></span></div><div class="row p5 n-margin">');
					for(var k = 0; k < arr2.length; k++) {
						h.push('<div class="col-xs-6 ' + sonClass2 + '"><div ui-sref="root.newDetailList({groupId: ' + arr2[k].groupId + '})" class="h-150 img-box"><img src="' + arr2[k].cpLanmuGroupPic.allpath + '" class="cursor img-thumbnail img-hover div-center" alt="Responsive image"></div><a ui-sref="root.newDetailList({groupId: ' + arr2[k].groupId + '})"><h5 class="mt20 photo-file-name change-hover-color">' + arr2[k].cpLanmuGroupPic.title + '</h5></a></div>');
					}
					h.push('</div></div>');
				}
				h.push('</div>');

				//轮播一
			} else if(opt[i].typeId == 2) {
				arr1 = opt[i].cpLanmuPictures || [];
				arr2 = opt[i].cpNoticesList || [];
				h.push('<div class="panel panel-info img-back mt20"><div id="lanmu_' + opt[i].id + '" class="panel-heading clear"><h3 class="fll panel-title text-center text-shad2">' + opt[i].name + '</h3><span ui-sref="root.moreContribute({id:' + opt[i].id + '})" class="flr cursor change-hover-color">more>></span></div><div style="min-height: 350px;" class="panel-body"><div class="row n-margin"><div class="col-sm-6" style="height:100%"><div class="swiper-container lb-view" style="height:100%"><div class="swiper-wrapper">');
				if(arr1.length != 0) {
					for(var j = 0; j < arr1.length; j++) {
						h.push('<div class="swiper-slide" on-finish-render-filters><a ui-sref="root.newDetailList({groupId: ' + arr1[j].groupId + '})"><img class="w100p" src="' + arr1[j].cpLanmuGroupPic.allpath + '" alt=""></a></div>');
					}
				}
				h.push('</div><div class="swiper-pagination"></div></div></div><div class="col-sm-6 text-left"><p><b>公告：</b></p><br><ul>');
				if(arr2.length != 0) {
					var ggLength = (arr2.length < 8) ? arr2.length : 8;
					for(var k = 0; k < ggLength; k++) {
						h.push('<li class="l-h-30"><i class="icon-circle mr5"></i><span ui-sref="root.ggDisplay({id:' + arr2[k].id + '})" class="change-hover-color">' + arr2[k].noticeTitle + '</span></li>');
					}
				}
				h.push('</ul><br><div class="clear"><a ui-sref="root.ggList({id:' + $scope.topicId + '})"><span class="flr cursor change-hover-color">more>></span></a></div></div></div></div></div>');

				//轮播二
			} else if(opt[i].typeId == 3 && opt[i].cpLanmuPictures.length != 0) {
				arr1 = arr1 = opt[i].cpLanmuPictures || [];
				h.push('<div class="panel panel-warning img-back mt20" style="position:relative;"><div id="lanmu_' + opt[i].id + '" class="panel-heading clear"><h3 class="fll panel-title text-center text-shad2">' + opt[i].name + '</h3><span ui-sref="root.moreContribute({id:' + opt[i].id + '})" class="flr cursor change-hover-color">more>></span></div><div class="panel-body lb-box-1 pad-20-30 div-center"><div class="swiper-container1 lb-view" style="height:100%"><div class="swiper-wrapper">');
				for(var j = 0; j < arr1.length; j++) {
					h.push('<div class="swiper-slide" on-finish-render-filters><div class="lb-box"><a ui-sref="root.newDetailList({groupId: ' + arr1[j].groupId + '})"><img class="div-center" src="' + arr1[j].cpLanmuGroupPic.allpath + '" alt=""><p class="lb-title">' + arr1[j].cpLanmuGroupPic.title + '</p></a></div></div>');
				}
				h.push('</div><div class="swiper-button-prev"></div><div class="swiper-button-next"></div></div></div></div>');
				//四展
			} else if(opt[i].typeId == 4 && opt[i].cpLanmuPictures.length != 0) {
				arr1 = opt[i].cpLanmuPictures || [];
				h.push('<div class="panel panel-info img-back mt20"><div id="lanmu_' + opt[i].id + '" class="panel-heading clear"><h3 class="fll panel-title text-center text-shad2">' + opt[i].name + '</h3><span ui-sref="root.moreContribute({id:' + opt[i].id + '})" class="flr cursor change-hover-color">more>></span></div><div class="panel-body"><div class="row p5">');
				for(var j = 0; j < arr1.length; j++) {
					h.push('<div class="col-xs-6 col-md-3"><div ui-sref="root.newDetailList({groupId: ' + arr1[j].groupId + '})" class="h-150 img-box"><img src="' + arr1[j].cpLanmuGroupPic.allpath + '" class="img-thumbnail img-hover div-center cursor" alt="Responsive image"></div><a ui-sref="root.newDetailList({groupId: ' + arr1[j].groupId + '})"><h5 class="mt20 photo-file-name change-hover-color">' + arr1[j].cpLanmuGroupPic.title + '</h5></a></div>');
				}
				h.push('</div></div></div>');

				//五展
			} else if(opt[i].typeId == 5 && opt[i].cpLanmuPictures.length != 0) {
				arr1 = opt[i].cpLanmuPictures || [];
				h.push('<div class="panel panel-success img-back mt20"><div id="lanmu_' + opt[i].id + '" class="panel-heading clear"><h3 class="fll panel-title text-center text-shad2">' + opt[i].name + '</h3><span ui-sref="root.moreContribute({id:' + opt[i].id + '})" class="flr cursor change-hover-color">more>></span></div><div class="panel-body"><div class="row p5">');
				for(var j = 0; j < arr1.length; j++) {
					h.push('<div class="col-xs-6 w-p-20"><div ui-sref="root.newDetailList({groupId: ' + arr1[j].groupId + '})" class="h-118 img-box"><img src="' + arr1[j].cpLanmuGroupPic.allpath + '" class="img-thumbnail img-hover div-center cursor" alt="Responsive image"></div><a ui-sref="root.newDetailList({groupId: ' + arr1[j].groupId + '})"><h5 class="mt20 photo-file-name change-hover-color">' + arr1[j].cpLanmuGroupPic.title + '</h5></a></div>');
				}
				h.push('</div></div></div>');

				//六展
			} else if(opt[i].typeId == 6 && opt[i].cpLanmuPictures.length != 0) {
				arr1 = opt[i].cpLanmuPictures || [];
				h.push('<div class="panel panel-info img-back mt20"><div id="lanmu_' + opt[i].id + '" class="panel-heading clear"><h3 class="fll panel-title text-center text-shad2">' + opt[i].name + '</h3><span ui-sref="root.moreContribute({id:' + opt[i].id + '})" class="flr cursor change-hover-color">more>></span></div><div class="panel-body"><div class="row p5">');
				for(var j = 0; j < arr1.length; j++) {
					h.push('<div class="col-xs-6 col-md-4"><div ui-sref="root.newDetailList({groupId: ' + arr1[j].groupId + '})" class="h-210 img-box"><img src="' + arr1[j].cpLanmuGroupPic.allpath + '" class="img-thumbnail img-hover div-center cursor" alt="Responsive image"></div><a ui-sref="root.newDetailList({groupId: ' + arr1[j].groupId + '})"><h5 class="mt20 photo-file-name change-hover-color h-30">' + arr1[j].cpLanmuGroupPic.title + '</h5></a></div>');
				}
				h.push('</div></div></div>');

				//八展
			} else if(opt[i].typeId == 8 && opt[i].cpLanmuPictures.length != 0) {
				arr1 = opt[i].cpLanmuPictures || [];
				h.push('<div class="panel panel-warning img-back mt20"><div id="lanmu_' + opt[i].id + '" class="panel-heading clear"><h3 class="fll panel-title text-center text-shad2">' + opt[i].name + '</h3><span ui-sref="root.moreContribute({id:' + opt[i].id + '})" class="flr cursor change-hover-color">more>></span></div><div class="panel-body"><div class="row p5">');
				for(var j = 0; j < arr1.length; j++) {
					h.push('<div class="col-xs-6 col-md-3"><div ui-sref="root.newDetailList({groupId: ' + arr1[j].groupId + '})" class="h-150 img-box"><img src="' + arr1[j].cpLanmuGroupPic.allpath + '" class="img-thumbnail img-hover div-center cursor" alt="Responsive image"></div><a ui-sref="root.newDetailList({groupId: ' + arr1[j].groupId + '})"><h5 class="mt20 photo-file-name change-hover-color h-30">' + arr1[j].cpLanmuGroupPic.title + '</h5></a></div>');
				}
				h.push('</div></div></div>');

				//十展
			} else if(opt[i].typeId == 10 && opt[i].cpLanmuPictures.length != 0) {
				arr1 = opt[i].cpLanmuPictures || [];
				h.push('<div class="panel panel-info img-back mt20"><div id="lanmu_' + opt[i].id + '" class="panel-heading clear"><h3 class="fll panel-title text-center text-shad2">' + opt[i].name + '</h3><span ui-sref="root.moreContribute({id:' + opt[i].id + '})" class="flr cursor change-hover-color">more>></span></div><div class="panel-body"><div class="row p5">');
				for(var j = 0; j < arr1.length; j++) {
					h.push('<div class="col-xs-6 w-p-20"><div ui-sref="root.newDetailList({groupId: ' + arr1[j].groupId + '})" class="h-118 img-box"><img src="' + arr1[j].cpLanmuGroupPic.allpath + '" class="img-thumbnail img-hover div-center cursor" alt="Responsive image"></div><a ui-sref="root.newDetailList({groupId: ' + arr1[j].groupId + '})"><h5 class="mt20 photo-file-name change-hover-color h-30">' + arr1[j].cpLanmuGroupPic.title + '</h5></a></div>');
				}
				h.push('</div></div></div>');
			}
		}
		return h.join('');
	}
	//banner
	function init(id) {
		req.post('lanmu/showPicAndLanmuByTopicId.do', {
			topicId: id
		}).success(function(resp) {
			if(resp.code == '211') {
				vm.banner = resp.data.banna;
				vm.specialExplain = resp.data.desc || '';
				vm.specialLanmu = angular.fromJson(resp.data.lanmu);
				console.log(vm.specialLanmu);
			}
		})
	}
	init($scope.topicId);
});