angular.module("client.directive", [])
	.constant('layerIfShow', false)
	.directive('roleCommonTit', function() {
		return {
			restrict: 'E',
			scope: {
				title: '='
			},
			replace: true,
			template: '<h4 class="c-common-title"></h4>',
			link: function(scope, element, attrs) {
				element.html(attrs.value);
			}
		}
	})
	.directive('maskLayer', function() {
		return {
			restrict: 'AE',
			replace: true,
			templateUrl: 'client/app/shared/mask/mask.html',
			controller: function() {
				function initMask() {
					$('.mask-layer')[0].style.width = document.body.clientWidth + 'px';
					$('.mask-layer')[0].style.height = document.body.clientHeight + 'px';
				}
				initMask();
			}
		};
	})
	.directive('backImg', function() {
		return {
			restrict: 'AE',
			replace: true,
			link: function(scope, element, attrs) {
				var url = attrs.backImg;
				console.log(url);
				var height = attrs.size;
				if(!url) {
					return;
				}
				element.css({
					'background-image': 'url(' + url + ')',
					'background-size': '100% 100%',
					'height': height + 'px'
				});
			}
		};
	})
	.directive('datepicker', function() {
		return {
			restrict: 'A',
			require: '?ngModel',
			scope: {},
			link: function(scope, element, attrs, ngModel) {
				if(!ngModel) return;
				element.on("focus", function() {
					var val = this.value;
					console.log(val);
					scope.$apply(function() {
						ngModel.$setViewValue(val);
					});
				})
			}
		};
	})
	.directive('specialContainer', function(req, $compile) {
		return {
			restrict: 'E',
			replace: true,
			scope: true,
			/*controller: function ($scope) {
			    req.post('lanmu/showPicAndLanmuByTopicId.do', {
			        topicId: $scope.topicId
			    }).success(function (resp) {
			        if(resp.code == '211'){
			            //转义
			            //$scope.content = $compile($scope.handle_special(angular.fromJson(resp.data.lanmu) || []))($scope);
			        }
			    });
			},*/
			//template: '<div>{{content}}</div>',
			link: function($scope, element, attrs) {
				req.post('lanmu/showPicAndLanmuByTopicId.do', {
					topicId: $scope.topicId
				}).success(function(resp) {
					if(resp.code == '211') {
						var arr = angular.fromJson(resp.data.lanmu) || [];
						if(arr.length != 0) {
							//这里是把函数处理过return回来的数据转换为angular模块下的内容，然后替换指令
							element.replaceWith(angular.element($compile($scope.handle_special($scope, arr))($scope)));
							//轮播1
							var mySwiper = new Swiper('.swiper-container', {
								autoplay: 1500,
								pagination: '.swiper-pagination',
								paginationClickable: true,
								autoplayDisableOnInteraction: false,
								paginationBulletRender: function(swiper, index, className) {
									return '<span class="' + className + '">' + (index + 1) + '</span>';
								}
							});
							//轮播2
							var mySwiper1 = new Swiper('.swiper-container1', {
								autoplay: 2500,
								autoplayDisableOnInteraction: false,
								prevButton: '.swiper-button-prev',
								nextButton: '.swiper-button-next',
								effect: 'cube',
								cube: {
									slideShadows: true,
									shadow: true,
									shadowOffset: 100,
									shadowScale: 0.6
								}
							});
						}
					}
				});
			}
		}
	})
	//轮播加载
	.directive('onFinishRenderFilters', function($timeout) {
		return {
			restrict: 'AE',
			link: function(scope, element, attr) {
				if(scope.$last === true) {
					$timeout(function() {
						scope.$emit('ngRepeatFinished');
						jQuery(".slideBox_pic").slide({
							mainCell: ".bd ul",
							autoPlay: false
						});
					});
				}
			}
		};
	})
	//专题--子栏目导航
	.directive('lanmuNav', function($timeout) {
		return {
			restrict: 'A',
			link: function(scope, element, attr) {
				if(scope.$last === true) {
					$timeout(function() {
						var ali = getClass('li', 'lanmu_li');
						console.log(ali.length);
						for(var i = 0; i < ali.length; i++) {
							ali[i].onclick = function() {
								var lanmu_top = document.getElementById('lanmu_' + this.value);
								if(document.documentElement.scrollTop){
									document.documentElement.scrollTop=lanmu_top.offsetTop;
								}else{
									document.body.scrollTop = lanmu_top.offsetTop;
								}
								
								
							}
						}

						function getClass(tagName, classname) {
							if(document.getElementsByClassName) {
								return document.getElementsByClassName(classname);
							} else {
								var aResult = [];
								var aNode = document.getElementsByTagName(tagName);

								for(var i = 0; i < aNode.length; i++) {
									if(aNode[i].className.match(classname) != null) {
										aResult.push(aNode[i]);
									}
								}
								return aResult;
							}
						}
					}, 50);
				}
			}
		};
	})