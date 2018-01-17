/**
 * Created by Sun on 2016/12/30.
 */
clientModule.controller('picsDetailCtrl', function ($scope,$sce,$cookies, req, md5, $state, $rootScope, $stateParams, getFullText, $timeout,jugeGroupPos) {
    var vm = this;

    //首先将#back-to-top隐藏 
    $("#back-to-top").hide(); 
    //当滚动条的位置处于距顶部100像素以下时，跳转链接出现，否则消失 
    $(function () { 
    $(window).scroll(function(){ 
    if ($(window).scrollTop()>100){ 
    $("#back-to-top").fadeIn(200); 
    } 
    else
    { 
    $("#back-to-top").fadeOut(200); 
    } 
    }); 
    //当点击跳转链接后，回到页面顶部位置 
    $("#back-to-top").click(function(){ 
	    $('body,html').animate({scrollTop:0},100); 
	        return false; 
	    }); 
    }); 
    
    
    
    
    
    
    
    
    $(document).ready(function() {
    	
    	
    	
	/*************PhotoSwipe Start**************/
	var ImgHt = $('.demo-gallery img').height();
	var initPhotoSwipeFromDOM = function(gallerySelector) {

		var parseThumbnailElements = function(el) {
		    var thumbElements = el.childNodes,
		        numNodes = thumbElements.length,
		        items = [],
		        el,
		        childElements,
		        thumbnailEl,
		        size,
		        item;

		    for(var i = 0; i < numNodes; i++) {
		        el = thumbElements[i];

		        // include only element nodes 
		        if(el.nodeType !== 1) {
		          continue;
		        }

		        childElements = el.children;

		        size = el.getAttribute('data-size').split('x');

		        // create slide object
		        item = {
					src: el.getAttribute('href'),
					w: parseInt(size[0], 10),
					h: parseInt(size[1], 10),
					author: el.getAttribute('data-author')
		        };

		        item.el = el; // save link to element for getThumbBoundsFn

		        if(childElements.length > 0) {
		          item.msrc = childElements[0].getAttribute('src'); // thumbnail url
		          if(childElements.length > 1) {
		              item.title = childElements[1].innerHTML; // caption (contents of figure)
		          }
		        }


				var mediumSrc = el.getAttribute('data-med');
	          	if(mediumSrc) {
	            	size = el.getAttribute('data-med-size').split('x');
	            	// "medium-sized" image
	            	item.m = {
	              		src: mediumSrc,
	              		w: parseInt(size[0], 10),
	              		h: parseInt(size[1], 10)
	            	};
	          	}
	          	// original image
	          	item.o = {
	          		src: item.src,
	          		w: item.w,
	          		h: item.h
	          	};

		        items.push(item);
		    }

		    return items;
		};

		// find nearest parent element
		var closest = function closest(el, fn) {
		    return el && ( fn(el) ? el : closest(el.parentNode, fn) );
		};

		var onThumbnailsClick = function(e) {
		    e = e || window.event;
		    e.preventDefault ? e.preventDefault() : e.returnValue = false;

		    var eTarget = e.target || e.srcElement;

		    var clickedListItem = closest(eTarget, function(el) {
		        return el.tagName === 'A';
		    });

		    if(!clickedListItem) {
		        return;
		    }

		    var clickedGallery = clickedListItem.parentNode;

		    var childNodes = clickedListItem.parentNode.childNodes,
		        numChildNodes = childNodes.length,
		        nodeIndex = 0,
		        index;

		    for (var i = 0; i < numChildNodes; i++) {
		        if(childNodes[i].nodeType !== 1) { 
		            continue; 
		        }

		        if(childNodes[i] === clickedListItem) {
		            index = nodeIndex;
		            break;
		        }
		        nodeIndex++;
		    }

		    if(index >= 0) {
		        openPhotoSwipe( index, clickedGallery );
		    }
		    return false;
		};

		var photoswipeParseHash = function() {
			var hash = window.location.hash.substring(1),
		    params = {};

		    if(hash.length < 5) { // pid=1
		        return params;
		    }

		    var vars = hash.split('&');
		    for (var i = 0; i < vars.length; i++) {
		        if(!vars[i]) {
		            continue;
		        }
		        var pair = vars[i].split('=');  
		        if(pair.length < 2) {
		            continue;
		        }           
		        params[pair[0]] = pair[1];
		    }

		    if(params.gid) {
		    	params.gid = parseInt(params.gid, 10);
		    }

		    return params;
		};

		var openPhotoSwipe = function(index, galleryElement, disableAnimation, fromURL) {
		    var pswpElement = document.querySelectorAll('.pswp')[0],
		        gallery,
		        options,
		        items;

			items = parseThumbnailElements(galleryElement);

		    // define options (if needed)
		    options = {

			    barsSize: { 
		            top: 100,
		            bottom: 100
		        }, 
		        closeOnVerticalDrag:true,
	            fullscreenEl : false, // 是否支持全屏按钮
	            /*
	            shareButtons: [
		            {id:'download', label:'保存图片', url:'{{raw_image_url}}', download:true}
	            ], 
	            */
	            // 分享按钮

		        galleryUID: galleryElement.getAttribute('data-pswp-uid'),

		        getThumbBoundsFn: function(index) {
		            // See Options->getThumbBoundsFn section of docs for more info
		            var thumbnail = items[index].el.children[0],
		                pageYScroll = window.pageYOffset || document.documentElement.scrollTop,
		                rect = thumbnail.getBoundingClientRect(); 

		            return {x:rect.left, y:rect.top + pageYScroll, w:rect.width};
		        },

		        addCaptionHTMLFn: function(item, captionEl, isFake) {
					if(!item.title) {
						captionEl.children[0].innerText = '';
						return false;
					}
					captionEl.children[0].innerHTML = item.title +  '<br/><small>Photo: ' + item.author + '</small>';
					return true;
		        },
				
		    };


		    if(fromURL) {
		    	if(options.galleryPIDs) {
		    		// parse real index when custom PIDs are used 
		    		// http://photoswipe.com/documentation/faq.html#custom-pid-in-url
		    		for(var j = 0; j < items.length; j++) {
		    			if(items[j].pid == index) {
		    				options.index = j;
		    				break;
		    			}
		    		}
			    } else {
			    	options.index = parseInt(index, 10) - 1;
			    }
		    } else {
		    	options.index = parseInt(index, 10);
		    }

		    // exit if index not found
		    if( isNaN(options.index) ) {
		    	return;
		    }



			var radios = document.getElementsByName('gallery-style');
			for (var i = 0, length = radios.length; i < length; i++) {
			    if (radios[i].checked) {
			        if(radios[i].id == 'radio-all-controls') {

			        } else if(radios[i].id == 'radio-minimal-black') {
			        	options.mainClass = 'pswp--minimal--dark';
				        options.barsSize = {top:0,bottom:0};
						options.captionEl = false;
						options.fullscreenEl = false;
						options.shareEl = false;
						options.bgOpacity = 0.85;
						options.tapToClose = true;
						options.tapToToggleControls = false;
			        }
			        break;
			    }
			}

		    if(disableAnimation) {
		        options.showAnimationDuration = 0;
		    }

		    // Pass data to PhotoSwipe and initialize it
		    gallery = new PhotoSwipe( pswpElement, PhotoSwipeUI_Default, items, options);

		    // see: http://photoswipe.com/documentation/responsive-images.html
			var realViewportWidth,
			    useLargeImages = false,
			    firstResize = true,
			    imageSrcWillChange;

			gallery.listen('beforeResize', function() {

				var dpiRatio = window.devicePixelRatio ? window.devicePixelRatio : 1;
				dpiRatio = Math.min(dpiRatio, 2.5);
			    realViewportWidth = gallery.viewportSize.x * dpiRatio;


			    if(realViewportWidth >= 1200 || (!gallery.likelyTouchDevice && realViewportWidth > 800) || screen.width > 1200 ) {
			    	if(!useLargeImages) {
			    		useLargeImages = true;
			        	imageSrcWillChange = true;
			    	}
			        
			    } else {
			    	if(useLargeImages) {
			    		useLargeImages = false;
			        	imageSrcWillChange = true;
			    	}
			    }

			    if(imageSrcWillChange && !firstResize) {
			        gallery.invalidateCurrItems();
			    }

			    if(firstResize) {
			        firstResize = false;
			    }

			    imageSrcWillChange = false;

			});

			gallery.listen('gettingData', function(index, item) {
			    if( useLargeImages ) {
			        item.src = item.o.src;
			        item.w = item.o.w;
			        item.h = item.o.h;
			    } else {
			        item.src = item.m.src;
			        item.w = item.m.w;
			        item.h = item.m.h;
			    }
			});

		    gallery.init();
		};

		// select all gallery elements
		var galleryElements = document.querySelectorAll( gallerySelector );
		for(var i = 0, l = galleryElements.length; i < l; i++) {
			galleryElements[i].setAttribute('data-pswp-uid', i+1);
			galleryElements[i].onclick = onThumbnailsClick;
		}

		// Parse URL and open gallery if it contains #&pid=3&gid=1
		var hashData = photoswipeParseHash();
		if(hashData.pid && hashData.gid) {
			openPhotoSwipe( hashData.pid,  galleryElements[ hashData.gid - 1 ], true, true );
		}
	};

	initPhotoSwipeFromDOM('.demo-gallery');
	
    $("#auto-loop").lightGallery({
            loop:true,
            auto:true,
            pause:2000,
            lang: {
                allPhotos: ''
            },
            vimeoColor: 'cccccc',
        });
     });
    
    /*************PhotoSwipe End**************/
    
    
    
    
    
    
    
    
    
    
    /** 
     * Created by Jeffery Wang. 
     * Create Time: 2015-06-16 19:52 
     * Author Link: http://blog.wangjunfeng.com 
     */  
    var nativeShare = function (elementNode, config) {  
        if (!document.getElementById(elementNode)) {  
            return false;  
        }  
      
        var qApiSrc = {  
            lower: "http://3gimg.qq.com/html5/js/qb.js",  
            higher: "http://jsapi.qq.com/get?api=app.share"  
        };  
        var bLevel = {  
            qq: {forbid: 0, lower: 1, higher: 2},  
            uc: {forbid: 0, allow: 1}  
        };  
        var UA = navigator.appVersion;  
        var isqqBrowser = (UA.split("MQQBrowser/").length > 1) ? bLevel.qq.higher : bLevel.qq.forbid;  
        var isucBrowser = (UA.split("UCBrowser/").length > 1) ? bLevel.uc.allow : bLevel.uc.forbid;  
        var version = {  
            uc: "",  
            qq: ""  
        };  
        var isWeixin = false;  
      
        config = config || {};  
        this.elementNode = elementNode;  
        this.url = config.url || document.location.href || '';  
        this.title = config.title || document.title || '';  
        this.desc = config.desc || document.title || '';  
        this.img = config.img || document.getElementsByTagName('img').length > 0 && document.getElementsByTagName('img')[0].src || '';  
        this.img_title = config.img_title || document.title || '';  
        this.from = config.from || window.location.host || '';  
        this.ucAppList = {  
            sinaWeibo: ['kSinaWeibo', 'SinaWeibo', 11, '新浪微博'],  
            weixin: ['kWeixin', 'WechatFriends', 1, '微信好友'],  
            weixinFriend: ['kWeixinFriend', 'WechatTimeline', '8', '微信朋友圈'],  
            QQ: ['kQQ', 'QQ', '4', 'QQ好友'],  
            QZone: ['kQZone', 'QZone', '3', 'QQ空间']  
        };  
      
        this.share = function (to_app) {  
            var title = this.title, url = this.url, desc = this.desc, img = this.img, img_title = this.img_title, from = this.from;  
            if (isucBrowser) {  
                to_app = to_app == '' ? '' : (platform_os == 'iPhone' ? this.ucAppList[to_app][0] : this.ucAppList[to_app][1]);  
                if (to_app == 'QZone') {  
                    B = "mqqapi://share/to_qzone?src_type=web&version=1&file_type=news&req_type=1&image_url="+img+"&title="+title+"&description="+desc+"&url="+url+"&app_name="+from;  
                    k = document.createElement("div"), k.style.visibility = "hidden", k.innerHTML = '<iframe src="' + B + '" scrolling="no" width="1" height="1"></iframe>', document.body.appendChild(k), setTimeout(function () {  
                        k && k.parentNode && k.parentNode.removeChild(k)  
                    }, 5E3);  
                }  
                if (typeof(ucweb) != "undefined") {  
                    ucweb.startRequest("shell.page_share", [title, title, url, to_app, "", "@" + from, ""])  
                } else {  
                    if (typeof(ucbrowser) != "undefined") {  
                        ucbrowser.web_share(title, title, url, to_app, "", "@" + from, '')  
                    } else {  
                    }  
                }  
            } else {  
                if (isqqBrowser && !isWeixin) {  
                    to_app = to_app == '' ? '' : this.ucAppList[to_app][2];  
                    var ah = {  
                        url: url,  
                        title: title,  
                        description: desc,  
                        img_url: img,  
                        img_title: img_title,  
                        to_app: to_app,//微信好友1,腾讯微博2,QQ空间3,QQ好友4,生成二维码7,微信朋友圈8,啾啾分享9,复制网址10,分享到微博11,创意分享13  
                        cus_txt: "请输入此时此刻想要分享的内容"  
                    };  
                    ah = to_app == '' ? '' : ah;  
                    if (typeof(browser) != "undefined") {  
                        if (typeof(browser.app) != "undefined" && isqqBrowser == bLevel.qq.higher) {  
                            browser.app.share(ah)  
                        }  
                    } else {  
                        if (typeof(window.qb) != "undefined" && isqqBrowser == bLevel.qq.lower) {  
                            window.qb.share(ah)  
                        } else {  
                        }  
                    }  
                } else {  
                }  
            }  
        };  
      
        this.html = function() {  
            var position = document.getElementById(this.elementNode);  
            var html = '<div class="am-share">'+  
                '<div class="am-share-sns list clearfix">'+  
                '<span data-app="weixin" class="nativeShare weixin"><i></i>微信好友</span>'+  
                '<span data-app="weixinFriend" class="nativeShare weixin_timeline"><i></i>微信朋友圈</span>'+  
                '<span data-app="sinaWeibo" class="nativeShare weibo"><i></i>新浪微博</span>'+  
                '<span data-app="QQ" class="nativeShare qq"><i></i>QQ好友</span>'+  
                '<span data-app="QZone" class="nativeShare qzone"><i></i>QQ空间</span>'+  
                '<span data-app="" class="nativeShare more"><i></i>更多</span>'+  
                '<div class="am-share-footer"><button class="share_btn">取消</button></div>'+  
                '</div>'+  
                '</div>';  
            position.innerHTML = html;  
        };  
      
        this.isloadqqApi = function () {  
            if (isqqBrowser) {  
                var b = (version.qq < 5.4) ? qApiSrc.lower : qApiSrc.higher;  
                var d = document.createElement("script");  
                var a = document.getElementsByTagName("body")[0];  
                d.setAttribute("src", b);  
                a.appendChild(d)  
            }  
        };  
      
        this.getPlantform = function () {  
            ua = navigator.userAgent;  
            if ((ua.indexOf("iPhone") > -1 || ua.indexOf("iPod") > -1)) {  
                return "iPhone"  
            }  
            return "Android"  
        };  
      
        this.is_weixin = function () {  
            var a = UA.toLowerCase();  
            if (a.match(/MicroMessenger/i) == "micromessenger") {  
                return true  
            } else {  
                return false  
            }  
        };  
      
        this.getVersion = function (c) {  
            var a = c.split("."), b = parseFloat(a[0] + "." + a[1]);  
            return b  
        };  
      
        this.init = function () {  
            platform_os = this.getPlantform();  
            version.qq = isqqBrowser ? this.getVersion(UA.split("MQQBrowser/")[1]) : 0;  
            version.uc = isucBrowser ? this.getVersion(UA.split("UCBrowser/")[1]) : 0;  
            isWeixin = this.is_weixin();  
            if ((isqqBrowser && version.qq < 5.4 && platform_os == "iPhone") || (isqqBrowser && version.qq < 5.3 && platform_os == "Android")) {  
                isqqBrowser = bLevel.qq.forbid  
            } else {  
                if (isqqBrowser && version.qq < 5.4 && platform_os == "Android") {  
                    isqqBrowser = bLevel.qq.lower  
                } else {  
                    if (isucBrowser && ((version.uc < 10.2 && platform_os == "iPhone") || (version.uc < 9.7 && platform_os == "Android"))) {  
                        isucBrowser = bLevel.uc.forbid  
                    }  
                }  
            }  
            this.isloadqqApi();  
            if (isqqBrowser || isucBrowser) {  
                this.html();  
            } else {  
                document.write('目前该分享插件仅支持手机UC浏览器和QQ浏览器');  
            }  
        };  
      
        this.init();  
      
        var share = this;  
        var items = document.getElementsByClassName('nativeShare');  
        for (var i=0;i<items.length;i++) {  
            items[i].onclick = function(){  
                share.share(this.getAttribute('data-app'));  
            }  
        }  
      
        return this;  
    };  
 
    
    
    
    $(".pswp__share--wechat").bind("click", function() {  
        var config = {  
            url: '', //分享url  
            title: '', //内容标题  
            desc: '', //描述  
            img: '', //分享的图片  
            img_title: '', //图片名称  
            from: '' //来源  
        };  
        var share_obj = new nativeShare('nativeShare', config);  
        $(".am-share").addClass("am-modal-active");  
        if ($(".sharebg").length > 0) {  
            $(".sharebg").addClass("sharebg-active");  
        } else {  
            $("body").append('<div class="sharebg"></div>');  
            $(".sharebg").addClass("sharebg-active");  
        }  
        $(".sharebg-active,.share_btn").click(function() {  
            $(".am-share").removeClass("am-modal-active");  
            setTimeout(function() {  
                $(".sharebg-active").removeClass("sharebg-active");  
                $(".sharebg").remove();  
            }, 300);  
        })  
});  
    

    
    
    

    //从路由取得稿件id
    vm.groupId = $stateParams.groupId;

	//从路由取得图片id
    vm.pictureId = $stateParams.pictureId;


    //初始化页面相关配置
    function initSetting() {
        //选中图片id
        vm.selPicIds = {};
        //默认页
        $scope.page = 1;
    }

    //获取客户端稿件详情
    function getClientGroupPics(callback) {
        req.post('getPicture/getClientGroupPics.do', {
            groupId: vm.groupId,
			picType: 1,
			size: 2
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.clientPictureDetail = resp.data;
                if(!vm.clientPictureDetail.author||vm.clientPictureDetail.author=='匿名'){
                	vm.clientPictureDetail.author = '匿名';
                }else{
                	vm.clientPictureDetail.author = vm.clientPictureDetail.author+" 摄";
                }
                //获取图片尺寸 add by xiayunan@20171025
                angular.forEach(vm.clientPictureDetail.pics, function (item, index) {
                	item.allSize = "1600x1600";
                	if(item.pictureWidth&&item.pictureHeight){
                		item.allSize = item.pictureWidth+"x"+item.pictureHeight;
                	}
                });
                
                
                
                vm.groupKeyWords = resp.data.keywords;

				var videoId = resp.data.videoId;
				//console.log("videoId:"+videoId);
				vm.videoId = videoId;
				vm.masUrl = '';
				if(videoId!=0){
					vm.masUrl = vm.masBaseUrl+"&method=exPlay&type=vod&id="+videoId;
				}
				vm.masUrl = $sce.trustAsResourceUrl(vm.masUrl);
				//console.log("vm.masUrl:"+vm.masUrl);

                if(callback) callback();
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    
    // 全文检索-检索相似稿件
    function req_getFullText(page){    
            if(page){
                $scope.page = page;
            }
            getFullText.req_getFullText({
                page: $scope.page,
                rows: 4,
                gKEYWORDS: vm.groupKeyWords
            },function (resp) {
                if(resp.code == 211){
                    vm.similarMs = resp.data;
                    $scope.similarTotalPage = resp.page;
                }else if(resp.msg != '未登录'){
                    layer.alert(resp.msg);
                }
            });
     }
    //页面初始化
    function init() {
        initSetting();
		getMasBaseUrl();
        getClientGroupPics(function(){
            req_getFullText(1);
        });
		

    }

    init();



	//获取Mas视频基础URL add by xiayunan 20170907
	function getMasBaseUrl(){
		vm.masBaseUrl = 'http://vi.ahnews.com.cn/mas/openapi/pages.do?appKey=TRSPMS123';
		req.get('groupPicCtro/getMasBaseUrl.do').success(function(resp) {
			if(resp.code == '211') {
				vm.masBaseUrl = resp.data.masBaseUrl;
			}else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}

    // 切换相似稿件图片
    $scope.changePic = function(addsubFlag){
        if(addsubFlag && ($scope.page <  $scope.similarTotalPage)){
            $scope.page++;
            req_getFullText($scope.page);
        }else if(!addsubFlag && ($scope.page > 1)){
            $scope.page--;
              req_getFullText($scope.page);
        }
    }


    //全选
    vm.checkAll = function () {
        angular.forEach(vm.clientPictureDetail.pics, function (item, index) {
            if (vm.selectedAll) {
                vm.selPicIds[item.id] = true;
            } else {
                vm.selPicIds[item.id] = false;
            }
        });
    };



    //判断是否选择了数据
    function judgeIfSelData() {
        vm.selKeyArr = [];
        for (var key in vm.selPicIds) {
            if (vm.selPicIds[key]) {
                vm.selKeyArr.push(key);
               // console.log(vm.selKeyArr.length);
            }
        }
       // console.log(vm.selKeyArr.length);
    }

    //获取选中图片ID
    function getPicIds() {
        vm.finalPicIds = '';
        for (var key in vm.selPicIds) {
            if (vm.selPicIds[key]) {
                vm.finalPicIds += key + ',';
            }
        }
        vm.picIds = vm.finalPicIds.slice(0, vm.finalPicIds.length - 1);
    }

    //下载图片
   /* vm.downPic = function() {
        judgeIfSelData();
        if (vm.selKeyArr.length < 1) {
            layer.alert('请至少选择一个图片进行操作');
            return;
        } else {
            getPicIds();
            req_genereateOrder();
        }

    };
    //订户下载生成订单请求
    function req_genereateOrder(callback) {
        req.post('downloadPicture/downForOrder.do', {
            picIds: vm.picIds
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.orderList = resp.other;
                vm.orderId = resp.other.id;
                vm.pics= resp.data;
                vm.orderDetailId='';
                angular.forEach(vm.pics,function (iterator) {
                    vm.orderDetailId += iterator.id+",";
                });
                $('#down-pic-order-modal').modal('show');
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //确定下载图片
    vm.sureDownPic = function (orderId, downWay) {
        req_sureDownPic(orderId, downWay);
    };
    //确定下载图片请求
    function req_sureDownPic(orderId, downWay) {
        var params =
            '?orderId=' + orderId +
            '&langType=' + 0 +
            '&type=' + downWay;
        window.open('/photo/downloadPicture/downByOrder.do' + params, '_blank');
    }

    //根据订单详情id下载图片
    vm.sureDownPicByOrderDetail = function (details, downWay) {
        req_sureDownPicByOrderDetailId(details, downWay);
    };
    //根据订单详情id下载图片
    function req_sureDownPicByOrderDetailId(details, downWay) {
        var params =
            '?picIds=' + details +
            '&type=' + downWay;
        window.open('/photo/downloadPicture/downByPicId.do' + params, '_blank');
    } */

    /**
     * 获取网站公告
     */
    /*function getWebPublishData() {
        req.post('notice/showToHomePage.do', {}).success(function (resp) {
            if (resp.code == '211') {
                vm.webPublishArray = resp.data;console.log(resp.data);
                if (vm.webPublishArray.length > 2) {
                    vm.webPublishArray = vm.webPublishArray.slice(0, 2);
                }
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //加入购物车
    vm.addCart = function () {
        judgeIfSelData();
        if (vm.selKeyArr.length < 1) {
            layer.alert('请至少选择一个图片进行操作');
            return;
        } else {
            getPicIds();
            req_onCartPicClick();
        }

    };

    //加入购物车请求
    function req_onCartPicClick() {
        req.post("car/add.do", {
            pictureId: vm.picIds,
        }).success(function (resp) {
            if (resp.code == '211') {
                contacts=resp.data.contacts
                notSales=resp.data.notSales
                if(contacts.length!=0 && notSales.length!=0 ){
                    layer.msg("包含不可出售图，张数用户不能添加定价图片")
                }else if(contacts.length!=0  ){
                    layer.msg("张数用户不能添加定价图片")
                }else if( notSales.length!=0 ){
                    layer.msg("包含不可出售图")
                }else{
                    layer.msg("加入购物车成功");
                }
                // layer.msg("加入购物车成功");
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }; */

     //下载时切换图文
    vm.toggleImgTxt = function (downType) {
        vm.downType = downType;
    };

    //下载图片
    vm.downPic = function (picid) {
        req_genereateOrder(picid);
    };
    //订户下载生成订单请求
    function req_genereateOrder(picid) {
        req.post('downloadPicture/downForOrder.do', {
            picIds: picid
        }).success(function (resp) {
            if (resp.code == '211') {

				vm.orderList = resp.other;
                vm.orderId = resp.other.id;
                vm.pics= resp.data;
                vm.orderDetailId='';
                angular.forEach(vm.pics,function (iterator) {
                    vm.orderDetailId += iterator.id+",";
                });
                // alert(vm.orderDetailId);
                $('#down-pic-order-modal').modal('show');

            }else if(resp.msg != '未登录'){
               layer.alert(resp.msg);
            }
        });
    }

    //确定下载图片
    vm.sureDownPic = function (orderId, downWay) {
        req_sureDownPic(orderId, downWay);
    };
    //确定下载图片请求
    function req_sureDownPic(orderId, downWay) {
        var params =
            '?orderId=' + orderId +
            '&type=' + downWay;
        window.open('/photo/downloadPicture/downByOrder.do' + params, '_blank');
    }

    //根据订单详情id下载图片
    vm.sureDownPicByOrderDetail = function (details, downWay) {
        req_sureDownPicByOrderDetailId(details, downWay);
    };
    //根据订单详情id下载图片
    function req_sureDownPicByOrderDetailId(details, downWay) {
        var params =
            '?picIds=' + details +
            '&type=' + downWay;
        window.open('/photo/downloadPicture/downByPicId.do' + params, '_blank');
    }

    /**
     * 获取网站公告
     */
    function getWebPublishData() {
        req.post('notice/showToHomePage.do', {}).success(function (resp) {
            if (resp.code == '211') {
                vm.webPublishArray = resp.data;
                if (vm.webPublishArray.length > 2) {
                    vm.webPublishArray = vm.webPublishArray.slice(0, 2);
                }
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    /**
     * 获取收藏夹
     */
    function getCollectBookData() {
        req.post('favorite/showAllFavoriteFolderName.do', {}).success(function (resp) {
            if (resp.code == '211') {
                vm.collectBookArray = resp.data;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    /**
     * 点击是否显示收藏夹
     */
    vm.IsShowMineCollectFlag = false;
    vm.onIsShowMineCollectClick = function () {
        vm.IsShowMineCollectFlag = !vm.IsShowMineCollectFlag;
        if(vm.IsShowMineCollectFlag){
            // 获取收藏夹名
            getCollectBookData();
        }
    };

    /**
     * 添加收藏夹
     */
    vm.onAddCollectConClick = function () {
        if (!vm.addCollectNameModel) {
            layer.alert('请输入收藏夹名');
            return;
        }
        req.post("favorite/addFavoriteFolder.do", {
            folderName: vm.addCollectNameModel
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.addCollectNameModel = "";
                vm.onModalHide('favoriteAddModal');
                getCollectBookData();
                layer.msg("添加成功");
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    };

    /**
     * 将图片加入收藏夹
     */
    vm.onCollectPicClick = function (fileId) {
        req.post("favoriteFolderPic/addFavoriteFolderPic.do", {
            picid: vm.pictureId,
            folderId: fileId
        }).success(function (resp) {
            if (resp.code == '211') {
                layer.msg("收藏成功");
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    };

    /**
     * 将图片加入购物车
     */
    vm.onCartPicClick = function (picid) {
        req.post("car/add.do", {
            pictureId: picid,
        }).success(function (resp) {
            if (resp.code == '211') {
                contacts=resp.data.contacts
                notSales=resp.data.notSales
                if(contacts.length!=0 && notSales.length!=0 ){
                    layer.msg("包含不可出售图，张数用户不能添加定价图片")
                }else if(contacts.length!=0  ){
                    layer.msg("张数用户不能添加定价图片")
                }else if( notSales.length!=0 ){
                    layer.msg("包含不可出售图")
                }else{
                    layer.msg("加入购物车成功");
                }
                // layer.msg("加入购物车成功");
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    };

    // 请求
    getWebPublishData();

    //获取首页签发的稿件请求
    req_getClientGroups(25);
    function req_getClientGroups(signId) {
        req.post('getPicture/getClientGroups.do', {
            sginId: signId,
            limit: 10
        }).success(function (resp) {
            if (resp.code == '211') {
                //5一周最佳采用
                if (signId == 25) {
                    vm.bestUse = jugeGroupPos.jugeGroupPos(25,resp.data);
                }
              //  console.log(vm.bestUse);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
});
