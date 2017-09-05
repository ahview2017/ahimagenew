/**
 * Created by wx on 2017/5/10.
 */
cnsphoto_enModule.directive('repeatFinish', function ($timeout) {
    return {
        restrict: 'A',
        link: function(scope, element, attr) {
            if (scope.$last === true) { 
                $timeout(function() {
                    jQuery(".slideBox").slide({mainCell:".bd ul",autoPlay:true});

                    var nav_cont=getClass('div','nav_cont_content');
                    var nav_ul=document.getElementById('nav_ul');
                    var aNav_title_li=nav_ul.getElementsByTagName('li');
                    var toggle_clicks=getClass('a','toggle_clicks');

                    for(var i=0;i<toggle_clicks.length;i++){
                       toggle_clicks[i].index=i;
                       toggle_clicks[i].onclick=function(){
                         aNav_title_li[this.index].className='active';
                         nav_cont[0].style.display='none';
                         nav_cont[1].style.display='block';
                       }
                    
                    }  

                     var z1=getClass('div','z1');
                     var ul_width=document.getElementById('ul_width');
                     for(var i=0;z1.length;i++){
                        z1[i].style.width=800-ul_width.offsetWidth+'px';
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
                },50);                       
            }
        }
    };
}); 
cnsphoto_enModule.controller('newsCtrl', function ($state, req, $stateParams, $scope) {
    var vm = this;
    console.log("newsCtrl");

    /*轮播参数说明：
     *container：容器ID
     *len:轮播的图的数量
     *interval:延迟时间
     */
    
    function lunbo(container){
        var list=getClassN(container,'div','list')[0];
        var buttons_box=getClassN(container,'div','buttons')[0];
        var buttons=buttons_box.getElementsByTagName('span');
        var prev=getClassN(container,'a','prev')[0];
        var next=getClassN(container,'a','next')[0];
        var list_a=list.getElementsByTagName('a');
        var list_img=list.getElementsByTagName('img');
        var index = 1;
        var animated = false;
        var interval = 3000;
        var timer;
        var len=list_a.length;
        showButton();
        buttons[0].className='on';
        /*动态生成轮播图第一张和最后一张*/

        var new_a=document.createElement('a');
        var new_as=document.createElement('a');
        new_a.className='new_a';
        new_as.className='new_a';
        new_a.href=list_a[list_a.length-1].href;
        new_as.href=list_a[0].href;

        var new_img=document.createElement('img');
        var new_imgs=document.createElement('img');
        new_imgs.src=list_img[list_img.length-1].src;
        new_img.src=list_img[0].src;

        var new_div=document.createElement('div');
        var new_divs=document.createElement('div');
        new_div.className='nav_cont_top_w';
        new_divs.className='nav_cont_top_w';

        list.appendChild(new_a);
        list.insertBefore(new_as,list_a[0]);

        list_a[0].appendChild(new_div);
        list_a[list_a.length-1].appendChild(new_divs);

        list_a[0].appendChild(new_imgs);
        list_a[list_a.length-1].appendChild(new_img);

        list.style.width=818*list_a.length+'px';
        list.style.left=-818+'px';

        /*生成buttons 的span*/
        for(var i=0;i<list_a.length-3;i++){
            var buttons_span=document.createElement('span');
            buttons_span.className='buttons_span';
            buttons_span.setAttribute("index",i+2);
            buttons_box.appendChild(buttons_span);
        }
        console.log('生成buttons 的span的长度:'+buttons.length);
        var buttons_length=buttons.length;

        function showButton() {
            for (var i = 0; i < buttons.length;i++) {
                buttons[i].index=parseInt(buttons[i].getAttribute('index'));
                if( buttons[i].className == 'on'){
                    buttons[i].className = '';
                    break;
                }
            }
            buttons[index-1].className='on';
        }

        function animate (offset) {
            if (offset == 0) {
                return;
            }
            animated = true;
            var time = 300;
            var inteval = 10;
            var speed = offset/(time/inteval);
            var left = parseInt(list.style.left) + offset;

            var go = function (){                

                if ( (speed > 0 && parseInt(list.style.left) < left) || (speed < 0 && parseInt(list.style.left) > left)) {
                    list.style.left = parseInt(list.style.left) + speed + 'px';
                    setTimeout(go, inteval);
                }
                else {
                    list.style.left = left + 'px';
                    if(left>-200){
                        list.style.left = -818 * len + 'px';
                    }
                    if(left<(-818 * len)) {
                        list.style.left = '-818px';
                    }
                    animated = false;
                }
            }
            go();
        }


        function play() {
            timer = setTimeout(function () {
                next.onclick();
                play();
            }, interval);
        }
        function stop() {
            clearTimeout(timer);
        }

        next.onclick = function () {
            if (animated) {
                return;
            }
            if (index == len) {
                index = 1;
            }
            else {
                index += 1;
            }
            animate(-818);
            showButton();

        }
        prev.onclick = function () {
            if (animated) {
                return;
            }
            if (index == 1) {
                index = len;
            }
            else {
                index -= 1;
            }
            animate(818);
            showButton();
        }

        for (var i = 0; i < buttons.length; i++) {
            buttons[i].onclick = function () {
                if (animated) {
                    return;
                }
                if(this.className == 'on') {
                    return;
                }

                var myIndex = parseInt(this.getAttribute('index'));
                var offset = -818 * (myIndex - index);

                animate(offset);
                index = myIndex;
                showButton();
            }
        }

        container.onmouseover = stop;
        container.onmouseout = play;
        
        timer=setTimeout(play(),10);
    }
    /*分页函数参数说明
     *fenye_box:分页最外面的容器的id
     *count:需要展示的图片总数
     *each_page_count:每一页图片的个数
     *urrent_page:当前页面的页数
     */
    function togglePage(fenye_box,count,each_page_count,urrent_page){
        var fenye=fenye_box.getElementsByTagName('ul')[0];/*通过父容器获取ul*/
        var fenye_li=fenye.getElementsByTagName('li');console.log(fenye_li);
        for(var i=0; i<fenye_li.length-4;){
            fenye_li[i+2].remove();
        }
        var first=fenye_li[0];
        var pre=fenye_li[1];
        var next=fenye_li[2];
        var last=fenye_li[3];
        var goTo=fenye_box.getElementsByTagName('p')[0];
        var goTobtn=goTo.getElementsByTagName('span')[0];
        var goToInput=goTo.getElementsByTagName('input')[0];
        var fenye_li_c=getClassN(fenye,'li','fenye_li_c');/*获取动态生成存储页数的li*/
        var page_count=Math.ceil(count/each_page_count);/*计算共有多少页*/

        for(var i=0;i<page_count;i++){
            var newNode = document.createElement("li");
            newNode.innerHTML = i+1;
            newNode.className='fenye_li_c';
            fenye.insertBefore(newNode,fenye_li[fenye_li.length-2]);

        }
        var fenye_li_c=getClassN(fenye,'li','fenye_li_c');/*获取动态生成存储页数的li*/
        fenye_li_c[urrent_page-1].style.background='#ccc';/*给当前页加背景*/
        console.log('共有多少页数fenye_li_c.length：'+fenye_li_c.length);

        for(var i=0;i<fenye_li_c.length;i++){
            fenye_li_c[i].index=i+1; //给每一个页数添加index

            fenye_li_c[i].onclick=function(){
                urrent_page=this.index; //当前页数
                console.log('当前页数为urrent_page：'+urrent_page);
                targetPage(urrent_page,'couuretPage');
                clearbg();
                this.style.background='#ccc';
            }
        }
        console.log('当前页数为2：'+urrent_page);

        next.onclick=function(){
            if(urrent_page<page_count){
                urrent_page+=1;
            }else{
                urrent_page=urrent_page;
                // alert('最后一页了。')
            }
            console.log('当前页数为next：'+urrent_page);
            clearbg();
            fenye_li_c[urrent_page-1].style.background='#ccc';
        }

        pre.onclick=function(){
            if(urrent_page>1){
                urrent_page-=1;
            }else{
                urrent_page=urrent_page;
                // alert('已经是第一页了。');
            }
            console.log('当前页数为pre：'+urrent_page);
            clearbg();
            fenye_li_c[urrent_page-1].style.background='#ccc';
        }

        first.onclick=function(){
            clearbg();
            fenye_li_c[0].style.background='#ccc';
            urrent_page=1;
            console.log('点击firstPage：'+urrent_page);
        }

        last.onclick=function(){
            clearbg();
            fenye_li_c[page_count-1].style.background='#ccc';
            urrent_page=page_count;
            console.log('点击lastPage：'+urrent_page);
        }

        goTobtn.onclick=function(){
            var topage=goToInput.value;
            if( topage <= fenye_li_c.length & topage > 0){
                clearbg();
                fenye_li_c[topage-1].style.background='#ccc';
                urrent_page=topage;
                console.log('点击Go：'+urrent_page);
            }

        }
        return urrent_page;

        /*清空点击出现的颜色*/
        function clearbg(){
            for(var j=0;j<fenye_li_c.length;j++){
                fenye_li_c[j].style.background='#fff';
            }
        }
    }
    function getClassN(parent,tagName,classname){
            
            var aResult=[];
            var aNode=parent.getElementsByTagName(tagName);

            for(var i=0;i<aNode.length;i++){
                if(aNode[i].className.match(classname)!=null){
                    //原来为 aNode[i].className==classnameaNode[i].className.match(classname)!=null
                    aResult.push(aNode[i]);
                }
            }
            return aResult;
        
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

     $scope.isShow=1
    //查询当前父栏目的名字
    req.post('enColumn/showColumnById.do',{
        "columnId":$stateParams.id
    }).success(function(resp){
        if(resp.code == '211'){
            $scope.upLanMuName = resp.data;
            $scope.recommendId = resp.data.recommendId
            //获取当前父栏目的衍生栏目
            req.post('getGroupPicCtro/showColumnToGroupPic.do',{
                "columnId":$scope.recommendId,
                "groupType":1,
                "langType":1
            }).success(function(resp){
                if(resp.code == '211'){
                    console.log(resp.data);
                    $scope.upColumnPictures = resp.data;
                    /*$("#qlist").empty();
                    for(var i=0; i< resp.data.length;i++){
                        $("#qlist").append('<a href="javascript:void(0);" ui-sref="root.news_photos({id:resp.data[i].cpPicGroup.id})">'+
                            '<img src="'+resp.data[i].cpPicGroup.cpLanmuGroupPic.allpath+'" alt="3"/>'
                            +'<div class="nav_cont_top_w"><p class="nav_cont_top_wo">'+resp.data[i].cpPicGroup.cpLanmuGroupPic.title+'</p></div></a>')
                    }
                    lunbo(document.getElementById("container"));*/

                    var nav_ul=document.getElementById('nav_ul');
                    var aNav_title_li=nav_ul.getElementsByTagName('li');
                    var nav_cont=getClass('div','nav_cont_content');
                    nav_cont[0].style.display='block';                   
                    
                    

                    for(var i=0;i<aNav_title_li.length;i++){
                        aNav_title_li[i].index=i;
                        aNav_title_li[i].onclick=function(){
                            for(var j=0;j<aNav_title_li.length;j++){
                                aNav_title_li[j].className='';
                            }
                            for(var j=0;j<nav_cont.length;j++){
                                nav_cont[j].style.display='none';
                            }
                            this.className='active';
                            console.log('你点了第几个子栏目：'+this.index);                            
                            nav_cont[1].style.display='block';
                            setTimeout(function(){
                                var z1_b=getClass('div','z1_b');
                                var ul_width_b=document.getElementById('ul_width_b');
                                for(var i=0;z1_b.length;i++){
                                   z1_b[i].style.width=800-ul_width_b.offsetWidth+'px';
                                } 
                            },300);
                            
                        };
                    }                    

                }else if(resp.msg != '未登录'){
                    layer.alert(resp.msg);
                }

            });
        }else if(resp.msg != '未登录'){
            layer.alert(resp.msg);
        }
    });
    //查询子栏目
    req.post('enColumn/showEnColumn.do',{
        "pid":$stateParams.id,
        "state":1,
        "langType": 1
    }).success(function(resp){
        if(resp.code == '211'){
            $scope.nextLanMu = resp.data;
        }else if(resp.msg != '未登录'){
            layer.alert(resp.msg);
        }
    });
    //获取当前栏目下子栏目及组图
  /*  var paramsColumn = {
        "columnId":$stateParams.id,
        "langType":1
    };*/
    req.post('getGroupPicCtro/showAllGroupPic.do',{
        "columnId":$stateParams.id,
        "langType":1
    }).success(function(resp){
        if(resp.code == '211'){
            $scope.columnGroupPictures = resp.data;
        }else if(resp.msg != '未登录'){
            layer.alert(resp.msg);
        }
    })
  /*  req.get("cnsphoto/getGroupPicCtro/showAllGroupPic.do?callback=JSON_CALLBACK", paramsColumn).success(function (resp) {
        console.log(resp);
        if(resp.code == '211'){
            $scope.columnGroupPictures = resp.data;
        }else{
            console.log("服务器出现错误！");
        }
    })*/
    //子栏目的衍生轮播图
    $scope.nextLanMuDetail = function(columnId,recommendId){
        $scope.isShow=1;
        $scope.columnId = columnId;
        req.post('getGroupPicCtro/showColumnToGroupPic.do',{
            "columnId":recommendId,
            "groupType":1,
            "langType":1
        }).success(function(resp){
            if(resp.code == '211'){                 
                 $scope.cpLanmuPictures=resp.data;                
                 /*动态添加轮播图结构*/
                /*$("#list").empty();
                $("#listBut").empty();
                $("#listBut").append("<span index='1' class='on'></span>");
                for(var i=0; i< resp.data.length;i++){
                    $("#list").append('<a href="javascript:void(0);">'+
                        '<img src="'+resp.data[i].cpPicGroup.cpLanmuGroupPic.allpath+'" alt="3"/>'
                        +'<div class="nav_cont_top_w"><p class="nav_cont_top_wo">'+resp.data[i].cpPicGroup.cpLanmuGroupPic.title+'</p></div></a>')
                }
                /*调用轮播 */                            
                // var timer=setTimeout(lunbo(document.getElementById("container_f1")),4000);
                // clearTimeout(timer);
 
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }

        });

        //获取当前子栏目的所有以签发的稿件
        req.post('getGroupPicCtro/showColumnToGroupPic.do',{
            "columnId":columnId,
            "groupType":2,
            "langType":1
        }).success(function(resp){
            if(resp.code == '211'){
                $scope.NextColumnGroup = resp.data;
             //   togglePage(fenye_box,resp.page.count,12,resp.page.currtPage,0); /*调用分页函数*/
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    /*分页函数参数说明
     *fenye_box:分页最外面的容器的id
     *count:需要展示的图片总数
     *each_page_count:每一页图片的个数
     *urrent_page:当前页面的页数
     */
    $scope.more = function(){
        $scope.isShow=2;
        req.post('getGroupPicCtro/showAllColumnPic.do',{
            "columnId":$scope.columnId,
            "page":1,
            "rows":12,
            "langType":1
        }).success(function(resp){
            if(resp.code == '211'){
                $scope.NextColumnGroup = resp.data;
                $scope.other = resp.other;
                togglePage(fenye_box,resp.other,12,1,0); /*调用分页函数*/
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    //获取子栏目的分页数据
    $scope.targetPage = function(page,count,turnParam){
        var fenye_box=document.getElementById('fenye_box'); /*最外面的容器id*/
        var each_page_count=12;/*每一页需要展示的图片个数*/
        if(turnParam == 'first'){
            page=1;
        }else if(turnParam =='next'){
            if(page+1 > Math.ceil(count/each_page_count)){
                return;
            }else{
                page = page+1;
            }
        }
        else if(turnParam =='pre'){
            if(page-1 < 1){
                return;
            }else{
                page=page-1;
            }
        }else if(turnParam =='last'){
            page=Math.ceil(count/each_page_count);
            }else if(turnParam =='pageNo'){
               pageNumber = document.getElementById('pageNumber').value;
                if( pageNumber > Math.ceil(count/each_page_count)){
                  /*var fenye_li_c=getClassN(fenye,'li','fenye_li_c');/!*获取动态生成存储页数的li*!/
                    for(var i=0; i<page; i++){
                     fenye_li_c[i].style.background='#ccc';/!*给当前页添加背景颜色*!/
                    }*/
                return;
            }else if(1 >pageNumber ){
                return;
            }else{
                page = pageNumber
            }
        }else if(turnParam == 'couuretPage'){
            page=page;
        }
        req.post('getGroupPicCtro/showAllColumnPic.do',{
            "columnId":$scope.columnId,
            "page":page,
            "rows":12,
            "langType":1
        }).success(function(resp){
            if(resp.code == '211'){
                $scope.other = resp.other;
                $scope.page = page;
                $scope.NextColumnGroup = resp.data;
                togglePage(fenye_box,resp.other,each_page_count,page,1); /*调用分页函数*/
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
      /*  req.post('getGroupPicCtro/showColumnAndGroupPic.do',{
            "columnId":$scope.columnId,
            "currtPage":page,
            "maxPage":each_page_count,
            "moreColumn":1
        }).success(function(resp){
            if(resp.code == '211'){
                $scope.page = resp.page;
                $scope.NextColumnGroup = resp.data;
                var fenye_box=document.getElementById('fenye_box'); /!*最外面的容器id*!/
                var fenye=fenye_box.getElementsByTagName('ul')[0];/!*通过父容器获取ul*!/
                var fenye_li_c=getClassN(fenye,'li','fenye_li_c');/!*获取动态生成存储页数的li*!/
                for(var i=0; i<fenye_box.length; i++){
                    fenye_li_c[i].html="";
                }
                togglePage(fenye_box,resp.page.count,each_page_count,page,1); /!*调用分页函数*!/
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });*/

    }
    //点击页码的分页
     function targetPage(page,turnParam){
        var fenye_box=document.getElementById('fenye_box'); /*最外面的容器id*/
        var each_page_count=12;/*每一页需要展示的图片个数*/
        if(turnParam == 'couuretPage') {
            req.post('getGroupPicCtro/showAllColumnPic.do',{
                "columnId":$scope.columnId,
                "page":page,
                "rows":each_page_count,
                "langType":1
            }).success(function(resp){
                if(resp.code == '211'){
                    $scope.NextColumnGroup = resp.data;
                    $scope.page = page;
                    $scope.other = resp.other;
                    var fenye_box = document.getElementById('fenye_box');
                    /*最外面的容器id*/
                    var fenye = fenye_box.getElementsByTagName('ul')[0];
                    /*通过父容器获取ul*/
                    var fenye_li_c = getClassN(fenye, 'li', 'fenye_li_c');
                    /*获取动态生成存储页数的li*/
                    for (var i = 0; i < fenye_box.length; i++) {
                        fenye_li_c[i].html = "";
                    }
                    togglePage(fenye_box,resp.other,each_page_count,page,1); /*调用分页函数*/
                }else if(resp.msg != '未登录'){
                    layer.alert(resp.msg);
                }
            });
          /*  req.post('getGroupPicCtro/showColumnAndGroupPic.do', {
                "columnId": $scope.columnId,
                "currtPage": page,
                "maxPage": each_page_count,
                "moreColumn": 1
            }).success(function (resp) {
                if (resp.code == '211') {
                    $scope.NextColumnGroup = resp.data;
                    $scope.page = resp.page;
                    var fenye_box = document.getElementById('fenye_box');
                    /!*最外面的容器id*!/
                    var fenye = fenye_box.getElementsByTagName('ul')[0];
                    /!*通过父容器获取ul*!/
                    var fenye_li_c = getClassN(fenye, 'li', 'fenye_li_c');
                    /!*获取动态生成存储页数的li*!/
                    for (var i = 0; i < fenye_box.length; i++) {
                        fenye_li_c[i].html = "";
                    }
                    togglePage(fenye_box, resp.page.count, each_page_count, page, 1);
                    /!*调用分页函数*!/
                } else if (resp.msg != '未登录') {
                    layer.alert(resp.msg);
                }
            });*/
        }
    }


});