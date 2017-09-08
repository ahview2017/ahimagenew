photo_enModule.controller('footerCtrl', function (req) {
    var vm = this;
    console.log("footerCtrl");
    //友情链接
    req.post('friendshipLink/friendshipLinkShow.do',{        
        langType:window.localStorage.lang
    }).success(function(resp){
        if(resp.code == '211'){
        	console.log(resp.data);
            vm.friendshipLink = resp.data;
        }else if(resp.msg != '未登录'){
            layer.alert(resp.msg);
        }
    });
});