clientModule.controller('fashionPicCtrl',function($scope, $cookies, req, md5, $state, $rootScope, layerIfShow, ellipsis, $timeout, jugeGroupPos){
    var vm = this;
    //页面初始化
    function init(){
    }
    init();

    //获取娱乐风尚签发的稿件
    vm.getFashionGroups = function (signId) {
        req_getClientGroups(signId);
    };
    //获取娱乐风尚的稿件请求
    function req_getClientGroups(signId) {
        req.post('getPicture/getClientGroups.do', {
            sginId: signId,
            limit: 10
        }).success(function (resp) {
            if (resp.code == '211') {
                //大头条: 371
                if (signId == 513) {
                    vm.fashionHeadlineGroups = resp.data;
                }
                //精彩推荐: 372
                if (signId == 132) {
                    vm.goodRecommend = resp.data;
                    vm.goodRecommendGroups = jugeGroupPos.jugeGroupPos(132,vm.goodRecommend);
                }
                //最新滚动: 373
                if (signId == 222) {
                    vm.latestRolling = resp.data;
                    vm.latestRollingGroups = jugeGroupPos.jugeGroupPos(222,vm.latestRolling);
                }
                //电影现场: 374
                if (signId == 223) {
                    vm.liveCinema = resp.data;
                    vm.liveCinemaGroups = jugeGroupPos.jugeGroupPos(223,vm.liveCinema);
                }
                //电影剧照: 375
                if (signId == 224) {
                    vm.actionStill = resp.data;
                    vm.actionStillGroups = jugeGroupPos.jugeGroupPos(224,vm.actionStill);
                }
                //电影宣传: 376
                if (signId == 225) {
                    vm.filmPromotion = resp.data;
                    vm.filmPromotionGroups = jugeGroupPos.jugeGroupPos(225,vm.filmPromotion);
                }
                //电视现场: 378
                if (signId == 226) {
                    vm.tvlive = resp.data;
                    vm.tvliveGroups = jugeGroupPos.jugeGroupPos(226,vm.tvlive);
                }
                //电视剧照: 400
                if (signId == 227) {
                    vm.tvstills = resp.data;
                    vm.tvstillsGroups = jugeGroupPos.jugeGroupPos(227,vm.tvstills);
                }

                //电视宣传: 401
                if (signId == 229) {
                    vm.tvpropaganda = resp.data;
                    vm.tvpropagandaGroups = jugeGroupPos.jugeGroupPos(229,vm.tvpropaganda);
                }


                //音乐现场: 402
                if (signId == 230) {
                    vm.liveMusic = resp.data;
                    vm.liveMusicGroups = jugeGroupPos.jugeGroupPos(230,vm.liveMusic);
                }

                //音乐海报: 377
                if (signId == 231) {
                    vm.musicPoster = resp.data;
                    vm.musicPosterGroups = jugeGroupPos.jugeGroupPos(231,vm.musicPoster);
                }

                //音乐宣传: 379
                if (signId == 232) {
                    vm.musicPropaganda = resp.data;
                    vm.musicPropagandaGroups = jugeGroupPos.jugeGroupPos(232,vm.musicPropaganda);
                }

                //戏剧: 380
                if (signId == 233) {
                    vm.drama = resp.data;
                    vm.dramaGroups = jugeGroupPos.jugeGroupPos(233,vm.drama);
                }

                //84届奥斯卡金像奖: 381
                if (signId == 234) {
                    vm.AcademyAwards84 = resp.data;
                    vm.AcademyAwards84Groups = jugeGroupPos.jugeGroupPos(234,vm.AcademyAwards84);
                }

                //明星写真: 382
                if (signId == 235) {
                    vm.starPhoto = resp.data;
                    vm.starPhotoGroups = jugeGroupPos.jugeGroupPos(235,vm.starPhoto);
                }


                //明星秀场: 383
                if (signId == 236) {
                    vm.starShow = resp.data;
                    vm.starShowGroups = jugeGroupPos.jugeGroupPos(236,vm.starShow);
                }


                //明星街拍: 384
                if (signId == 237) {
                    vm.starStreetSnap = resp.data;
                    vm.starStreetSnapGroups = jugeGroupPos.jugeGroupPos(237,vm.starStreetSnap);
                }

                //时装: 385
                if (signId == 238) {
                    vm.fashionableDress = resp.data;
                    vm.fashionableDressGroups = jugeGroupPos.jugeGroupPos(238,vm.fashionableDress);
                }


                //选美室内秀: 386
                if (signId == 239) {
                    vm.beautyIndoorShow = resp.data;
                    vm.beautyIndoorShowGroups = jugeGroupPos.jugeGroupPos(239,vm.beautyIndoorShow);
                }

                //选美室外秀: 387
                if (signId == 240) {
                    vm.beautyOutdoorShow = resp.data;
                    vm.beautyOutdoorShowGroups = jugeGroupPos.jugeGroupPos(240,vm.beautyOutdoorShow);
                }


                //精彩回顾: 388
                if (signId == 241) {
                    vm.wonderfulReview = resp.data;
                    vm.wonderfulReviewGroups = jugeGroupPos.jugeGroupPos(241,vm.wonderfulReview);
                }


            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //轮播
    $(function () {
        $timeout(function () {
            $('#fashion-focus-lb').slideBox({
                duration: 0.4,//滚动持续时间，单位：秒
                easing: 'linear',//swing,linear//滚动特效
                delay: 6,//滚动延迟时间，单位：秒
                hideClickBar: false,//不自动隐藏点选按键
                clickBarRadius: 3,
                width: 520,
                height: 330
            });
        }, 500);
    });

});