angular.module("admin.directive", [])
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
	//修改用户管理是摄影师被禁用时标红
	.directive('photographerDisableBids', function() {
		return {
			restrict: 'A',
			controller: function($rootScope) {
				function initMask() {
					if($rootScope.user_namecolor) {
						for(var i = 0; i < $rootScope.user_namecolor.length; i++) {
							if($rootScope.user_namecolor[i].USER_STATUS == 3) {
								var result = $rootScope.user_namecolor[i];
								for(var j = 0; j < result.roles.length; j++) {
									if(result.roles[j].id == 3) {
										$('.username_color').eq(i).css('color', 'red');
										$('.truename_color').eq(i).css('color', 'red');
										$('.id_color').eq(i).css('color', 'red');
										$('.authorname_color').eq(i).css('color', 'red');
										$('.langtype_color').eq(i).css('color', 'red');
										$('.roles_color').eq(i).css('color', 'red');
										$('.user_status_color').eq(i).css('color', 'red');
										$('.last_login_time_color').eq(i).css('color', 'red');
										$('.reg_date_color').eq(i).css('color', 'red');
									}
								}
							}
						}
					}
				}
				initMask();
			}

		};
	})
	.directive('maskLayer', function() {
		return {
			restrict: 'AE',
			replace: true,
			templateUrl: 'admin/app/shared/mask/mask.html',
			controller: function() {
				function initMask() {
					$('.mask-layer')[0].style.width = document.body.clientWidth + 'px';
					$('.mask-layer')[0].style.height = document.body.clientHeight + 'px';
				}
				initMask();
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
	//图片上传预览
	.directive("fileinput", [function() {
		return {
			scope: {
				fileinput: "=",
				filepreview: "="
			},
			link: function(scope, element, attributes) {
				element.bind("change", function(changeEvent) {
					$('#addSortImageId').show();
					$('#noUpImgsDesc').hide();
					$('#editSortImageId').show();
					scope.fileinput = changeEvent.target.files[0];
					var reader = new FileReader();
					reader.onload = function(loadEvent) {
						scope.$apply(function() {
							scope.filepreview = loadEvent.target.result;
						});
					}
					reader.readAsDataURL(scope.fileinput);
				});
			}
		}
	}])
	//file表单change事件
	.directive('customOnChange', function() {
		return {
			restrict: 'A',
			link: function(scope, element, attrs) {
				var onChangeFunc = scope.$eval(attrs.customOnChange);
				element.bind('change', onChangeFunc);
			}
		};
	})
	//轮播加载
	.directive('onFinishRenderFilters', function($timeout) {
		return {
			restrict: 'A',
			link: function(scope, element, attr) {
				if(scope.$last === true) {
					$timeout(function() {
						scope.$emit('ngRepeatFinished');
					});
				}
			}
		};
	})
	//使div可编辑，同时实现div里输入的数据的双向绑定
	.directive('contenteditable', function() {
		return {
			restrict: 'A',
			require: '?ngModel',
			link: function(scope, element, attrs, ngModel) {
				if(!ngModel) return;

				ngModel.$render = function() {
					element.html(ngModel.$viewValue || '');
				};

				element.on('blur change', function() {
					scope.$evalAsync(read);
				});
				read(); // initialize

                function read() {
                    var html = element.html();
                    if ( attrs.stripBr && html == '<br>' ) {    //清除 <br>
                        html = '';
                    }
                    ngModel.$setViewValue(html);
                }
            }
        };
    })
    //新增以及编辑用户时文本框添加字数限制
    .directive("limitTo", [function() {
        return {
            restrict: "A",
            link: function(scope, elem, attrs) {
                var limit = parseInt(attrs.limitTo);
                angular.element(elem).on("keydown keypress blur", function(e) {
                    //if (this.value.length == limit) e.preventDefault();
                    if (this.value.length > limit){
						elem.css('border','1px solid red');
						var ifNeedHintFlag = elem.siblings().hasClass('invalid-length-hint');
						if(ifNeedHintFlag){
							elem.siblings().show();
						}
                    }else{
						elem.css('border','1px solid #ccc');
						var ifNeedHintFlag = elem.siblings().hasClass('invalid-length-hint');
						if(ifNeedHintFlag){
							elem.siblings().hide();
						}
					}

                });
            }
        }
    }])
    //待发稿件显示中图
   .directive('showMiddlePic', function ($timeout,$rootScope,$cookies) {
	return {
		restrict: 'A',
		link: function(scope, element, attr,rootScope) {
			if (scope.$last === true) {
				var atd=getClass('td','pic_parent');
				var apic_children=getClass('div','pic_children');
				var oParentleft=document.documentElement.clientWidth||document.body.offsetWidth;
				var oParenttop=document.getElementById('tests').offsetTop;
				
				console.log(atd.length);
				console.log(apic_children.length);
				console.log('oParentleft:'+oParentleft+';oParenttop'+oParenttop);

				for(var i=0;i<atd.length;i++){
					atd[i].index=i;
					atd[i].onmousemove=function(ev){
						var oEvent=ev||event;
						var oscrollhieght=document.documentElement.scrollTop||document.body.scrollTop;
						var oleft=atd[this.index].offsetLeft;
						var otop=atd[this.index].offsetTop;
						var oheight=atd[this.index].offsetHeight;
						var owidth=atd[this.index].offsetWidth;
						var oleft=atd[this.index].offsetLeft;

						//document.title=oEvent.clientX+','+otop+','+oleft+','+oscrollhieght+','+$rootScope.li_count;
						//document.title=oEvent.clientX+','+oParentleft*0.04+','+oleft+','+oscrollhieght+',';						
						
						apic_children[this.index].style.width=980-oleft-owidth+'px';

						apic_children[this.index].style.display='block';
						apic_children[this.index].style.left=oEvent.clientX-oParentleft*0.04-oParentleft*0.92*0.14-oleft+10+'px';
						if(this.index<1){
							//apic_children[this.index].style.top=oheight*(this.index-this.index*1)+20+'px';
							apic_children[this.index].style.top=oEvent.clientY-oParenttop-otop+oscrollhieght+'px';
						}else if(this.index>=1&&this.index<=3){
							apic_children[this.index].style.top=oEvent.clientY-oParenttop-otop+oscrollhieght-oheight*(this.index)+'px';
						}
						else{
							//apic_children[this.index].style.bottom=20+'px';
							apic_children[this.index].style.bottom=oheight-(oEvent.clientY-oParenttop-otop+oscrollhieght)+'px';
						}
					}
					atd[i].onmouseout=function(){
						apic_children[this.index].style.display='none';
					}
				}
				function getClass(tagName,classname){
					if(document.getElementsByClassName){
						return document.getElementsByClassName(classname);
					}else{
						var aResult=[];
						var aNode=document.getElementsByTagName(tagName);

						for(var i=0;i<aNode.length;i++){
							if(aNode[i].className.match(classname)!=null){
								aResult.push(aNode[i]);
							}
						}
						return aResult;
					}
				}
			}
		}
	};
})
   //资料库和内部留资显示中图
   .directive('sendShowMiddlePic', function($timeout, $rootScope) {
	return {
		restrict: 'A',
		link: function(scope) {
			if(scope.$last === true) {
				var pic_parent='pic_parent';
				var apic_parent_pic='apic_parent_pic';
				var pic_children='pic_children';
				
				var pic_parent_r='pic_parent_r';
				var apic_parent_pic_r='apic_parent_pic_r';
				var pic_children_r='pic_children_r';
				
								
				moddlepic(pic_parent, apic_parent_pic, pic_children);
				moddlepic(pic_parent_r, apic_parent_pic_r, pic_children_r);
				
				function moddlepic(pic_parent, apic_parent_pic, pic_children) {
					var atd = getClass('td', pic_parent);
					var aimg = getClass('img', apic_parent_pic);
					var apic_children = getClass('div', pic_children);

					var oParentleft = document.documentElement.clientWidth || document.body.offsetWidth;
					var oParenttop = document.getElementById('manager-containers').offsetTop;

					for(var i = 0; i < aimg.length; i++) {
						aimg[i].index = i;
						aimg[i].onmousemove = function(ev) {
							var oEvent = ev || event;
							var oscrollhieght = document.documentElement.scrollTop || document.body.scrollTop;
							var oleft = atd[this.index].offsetLeft;
							var otop = atd[this.index].offsetTop;
							var oheight = atd[this.index].offsetHeight;
							var owidth = atd[this.index].offsetWidth;
							apic_children[this.index].style.width=980-oleft-owidth+'px';

							apic_children[this.index].style.display = 'block';
							apic_children[this.index].style.left = oEvent.clientX - oParentleft * 0.04 - oParentleft * 0.92 * 0.14 - oleft + 10 + 'px';
							if(this.index < 1) {
								//apic_children[this.index].style.top=oheight*(this.index-this.index*1)+20+'px';
								apic_children[this.index].style.top = oEvent.clientY - oParenttop - otop + oscrollhieght + 'px';
							} else if(this.index >= 1 && this.index <= 2) {
								apic_children[this.index].style.top = oEvent.clientY - oParenttop - otop + oscrollhieght - oheight * (this.index) + 'px';
							} else {
								//apic_children[this.index].style.bottom=20+'px';
								apic_children[this.index].style.bottom =oheight-(oEvent.clientY - oParenttop - otop + oscrollhieght) + 'px';
							}
						}
						aimg[i].onmouseout = function() {
							apic_children[this.index].style.display = 'none';
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
			}
		}
	};
})
   //待发稿件-稿件详情显示中图
   .directive('detailShowMiddlePic', function () {
	  return {
		restrict: 'A',
		link: function(scope, element) {
			if (scope.$last === true) {
				var smt_vm_parent=$('.parent_pic');
				var smt_wm_box=$('.smt-wm-box');
				smt_wm_box.css('display','none');				
				for(let i=0;i<smt_vm_parent.length;i++){
					smt_vm_parent.eq(i).mousemove(function(e){
						smt_wm_box.eq(i).css('display','block');
						var oEvent=e||event;
						$('.parent_pic').eq(i).height();
						if(smt_vm_parent.length>1){
							if(i<smt_vm_parent.length-1){
								smt_wm_box.eq(i).css('left',oEvent.clientX-smt_vm_parent.eq(i).offset().left+20+'px');
						        smt_wm_box.eq(i).css('top',oEvent.clientY+$(window).scrollTop()-smt_vm_parent.eq(i).offset().top+20+'px');
							}else{
								smt_wm_box.eq(i).css('left',oEvent.clientX-smt_vm_parent.eq(i).offset().left+20+'px');
						        smt_wm_box.eq(i).css('bottom',$('.parent_pic').eq(i).height()-(oEvent.clientY+$(window).scrollTop()-smt_vm_parent.eq(i).offset().top+20)+'px');
							}
						}else{
							smt_wm_box.eq(i).css('left',oEvent.clientX-smt_vm_parent.eq(i).offset().left+20+'px');
						    smt_wm_box.eq(i).css('top',oEvent.clientY+$(window).scrollTop()-smt_vm_parent.eq(i).offset().top+20+'px');
						}						
					});
					smt_vm_parent.eq(i).mouseout(function(){
						smt_wm_box.eq(i).css('display','none');
					});
				}
			}
		}
	  };
    })
	//左侧菜单栏权限被选中时改变样式
	.directive('menuActiveChangeStyle', function ($timeout) {
		return {
			restrict: 'A',
			link: function(scope, element, attr) {
				if (scope.$last === true) {
					$timeout(function() {
						//权限被选中时改变样式
						console.log($('.man_onclick').length);
						for(var i=0;i<$('.man_onclick').length;i++){
							$('.man_onclick').eq(i).click(function(){
								$(this).addClass('manager_active');
								$(this).siblings().removeClass('manager_active');
							});
						}
					});
				}
			}
		};
	})
	//实现群组管理关闭取消按钮功能
	.directive('groupCancelClose', function() {
		return {
			link: function(scope, element, attr) {
				if(scope.$last == true) {

					//关闭按钮
					$('.tit-close').click(function() {
						$('.group_cont').css('display', 'none');
						$('.goup_update_na').css('display', 'none');
					});
					//取消按钮
					$('.btn_cancel').click(function() {
						$('.group_cont').css('display', 'none');
						$('.goup_update_na').css('display', 'none');
					});
				}
			}
		}
	})
    //角色管理列表语种中英文显示
	.directive('roleCnEnChange', function ($timeout) {
		return {
			restrict: 'A',
			link: function(scope, element, attr) {
				if (scope.$last === true) {
					$timeout(function() {
						//语种切换数据判断展示
						var langType_tog=$('.langType_tog');
						for(var i=0;i<langType_tog.length;i++){
							if(langType_tog.eq(i).attr('value')==0){
								langType_tog.eq(i).text('中文');
							}else{
								langType_tog.eq(i).text('英文');
							}
						}
					});
				}
			}
		};
	})
	//下载管理列表订户类型文字显示
	.directive('subscriberType', function ($timeout) {
		return {
			restrict: 'A',
			link: function(scope, attr) {
				if (scope.$last === true) {
					$timeout(function() {
						var subscriberType=$('.subscriberType');
						for(var i=0;i<subscriberType.length;i++){
							if(subscriberType.eq(i).attr('value')==1){
								subscriberType.eq(i).text('图书');
							}else if(subscriberType.eq(i).attr('value')==2){
								subscriberType.eq(i).text('杂志');
							}else if(subscriberType.eq(i).attr('value')==3){
								subscriberType.eq(i).text('报纸');
							}else if(subscriberType.eq(i).attr('value')==4){
								subscriberType.eq(i).text('网络');
							}else if(subscriberType.eq(i).attr('value')==5){
								subscriberType.eq(i).text('影视');
							}else if(subscriberType.eq(i).attr('value')==6){
								subscriberType.eq(i).text('广告');
							}else if(subscriberType.eq(i).attr('value')==7){
								subscriberType.eq(i).text('其他');
							}else{
								subscriberType.eq(i).text('');
							}
						}
					});
				}
			}
		};
	})
