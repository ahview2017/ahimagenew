cnsphoto_enModule.directive('repeatFinishss',function(){
    return {
        link: function(scope,element,attr){
            // console.log(scope.$index)
            if(scope.$last == true){
                /*获取动态生成的DOM结构代码*/
                $(document).ready(function () { 
                  /*弹出层的显示*/
                   var popup_box=document.getElementById('popup_box');
                   var aClose=document.getElementById('close');
                   var photos_cont_img_box=getClass('div','photos_cont_img_box');
                   var popup_container=getClass('div','popup_container');
                   var photos_banner_l_click=getClass('img','photos_banner_l_click')[0];
                   var current_page=getClass('span','current_page');
                   var total_page=getClass('span','total_page');

                   aClose.onclick=function(){
                      popup_box.style.display='none';
                   };

                   /*所有的弹出层隐藏，点击对应的img出现弹出层*/
                   for(var i=0;i<popup_container.length;i++){
                      popup_container[i].style.display='none';
                      current_page[i].innerHTML=i+1;                      
                   }             

                   photos_banner_l_click.onclick=function(){
                    var popup_next=document.getElementById('popup_next');
                    var popup_pre=document.getElementById('popup_pre');
                    popup_box.style.display='block';
                    popup_container[0].style.display='block';
                    popup_next.style.background='url(./client_en/assets/images/details/arrow_next.png) no-repeat';
                    popup_pre.style.background='url(./client_en/assets/images/details/arrow_pre_0.png) no-repeat';
                    /*弹出层切换*/
                         var oIndex=0;//获取到当前的index值  
                         popup_next.onclick=function(){                         	 
                             for(var i=0;i<popup_container.length;i++){
                                 popup_container[i].style.display='none';
                             }

                             if(oIndex<popup_container.length-1){
                               popup_next.style.background='url(./client_en/assets/images/details/arrow_next.png) no-repeat';
                               popup_pre.style.background='url(./client_en/assets/images/details/arrow_pre.png) no-repeat';
                               oIndex+=1;
                               popup_container[oIndex].style.display='block';
                             }else{
                              popup_container[oIndex].style.display='block';
                              popup_next.style.background='url(./client_en/assets/images/details/arrow_next_0.png) no-repeat';                            
                             }
                         }

                        popup_pre.onclick=function(){
                            for(var i=0;i<popup_container.length;i++){
                               popup_container[i].style.display='none';
                            }

                           if(oIndex>0){
                              popup_pre.style.background='url(./client_en/assets/images/details/arrow_pre.png) no-repeat';
                              popup_next.style.background='url(./client_en/assets/images/details/arrow_next.png) no-repeat';
                              oIndex-=1;
                              popup_container[oIndex].style.display='block';
                           }else{
                             popup_pre.style.background='url(./client_en/assets/images/details/arrow_pre_0.png) no-repeat';
                             popup_container[oIndex].style.display='block';
                           }
                        }
                   }


                   for(var i=0;i<photos_cont_img_box.length;i++){
                       photos_cont_img_box[i].index=i;
                       photos_cont_img_box[i].onclick=function(){
                          popup_box.style.display='block';
                          for(var j=0;j<popup_container.length;j++){
                             popup_container[j].style.display='none';
                          }
                          console.log("点击了第几个图："+this.index);
                          popup_container[this.index].style.display='block';

                         /*弹出层切换*/                         
                         var oIndex=this.index;//获取到当前的index值
                         var popup_next=document.getElementById('popup_next');
                         var popup_pre=document.getElementById('popup_pre');
                         if(oIndex==0){
                              popup_pre.style.background='url(./client_en/assets/images/details/arrow_pre_0.png) no-repeat';
                              popup_next.style.background='url(./client_en/assets/images/details/arrow_next.png) no-repeat';
                         }else if(oIndex==popup_container.length-1){
                           popup_pre.style.background='url(./client_en/assets/images/details/arrow_pre.png) no-repeat';
                           popup_next.style.background='url(./client_en/assets/images/details/arrow_next_0.png) no-repeat';
                         }else{
                            popup_pre.style.background='url(./client_en/assets/images/details/arrow_pre.png) no-repeat';
                              popup_next.style.background='url(./client_en/assets/images/details/arrow_next.png) no-repeat';
                         }                         

                         popup_next.onclick=function(){
                             for(var i=0;i<popup_container.length;i++){
                                 popup_container[i].style.display='none';
                             }
 
                             if(oIndex<popup_container.length-1){
                               popup_next.style.background='url(./client_en/assets/images/details/arrow_next.png) no-repeat';
                               popup_pre.style.background='url(./client_en/assets/images/details/arrow_pre.png) no-repeat';
                               oIndex+=1;
                               popup_container[oIndex].style.display='block';                               
                             }else{
                              popup_container[oIndex].style.display='block';
                              popup_next.style.background='url(./client_en/assets/images/details/arrow_next_0.png) no-repeat';                            
                             }
                         }

                        popup_pre.onclick=function(){
                            for(var i=0;i<popup_container.length;i++){
                               popup_container[i].style.display='none';
                            }

                           if(oIndex>0){
                              popup_pre.style.background='url(./client_en/assets/images/details/arrow_pre.png) no-repeat';
                              popup_next.style.background='url(./client_en/assets/images/details/arrow_next.png) no-repeat';
                              oIndex-=1;
                              popup_container[oIndex].style.display='block';
                           }else{
                             popup_pre.style.background='url(./client_en/assets/images/details/arrow_pre_0.png) no-repeat';
                             popup_container[oIndex].style.display='block';
                           }
                        }
                      }
                    }


                   /*S/M/L具体信息切换*/
                  var popup_select_box=getClass('li','popup_select_box');
                  var popup_select_detail=getClass('li','popup_select_detail');
                  var count=0
                  // popup_select_detail[0].style.display='block';
                  // popup_select_detail[4].style.display='block';

                  for(var i=0;i<popup_select_box.length;i++){
                     popup_select_box[i].index=i;

                     popup_select_box[i].onclick=function(){

                        for(var j=0;j<popup_select_box.length;j++){
                             popup_select_detail[j].style.display='none';
                             popup_select_box[j].style.background='white';
                             popup_select_box[j].style.border='solid #d5d5d5 1px';
                        }
                        // this.className='selected';
                       this.style.background="#eff4f8";
                       this.style.border='solid 1px #86ace9';
                       popup_select_detail[this.index].style.display='block';

                    }
                  }


             })
            /*通过类名获取元素
             通过遍历标签,参数为标签名和类名
             */
           function getClass(tagName,classname){        
               var aResult=[];
               var aNode=document.getElementsByTagName(tagName);
               for(var i=0;i<aNode.length;i++){
                  if(aNode[i].className.match(classname)!=null){
                    //原来为 aNode[i].className==classnameaNode[i].className.match(classname)!=null
                    aResult.push(aNode[i]);
                   }
                }
                return aResult;        
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

            }
        }
    }
})

cnsphoto_enModule.controller('news_photosCtrl', function ($stateParams,$state,req,$scope,$cookies, md5, $rootScope, getFullText, $timeout) {
    var vm = this;
    console.log("news_photosCtrl");    
    var params = { 
        "groupId":$stateParams.id 
        };
        /*获取小图数据*/
        req.post("getPicture/getClientGroupPics.do", params)
        .success(function (resp) {
           if(resp.code==211){             
               console.log(resp.data);
               $scope.groupPics=resp.data; /*组图小图*/
               $scope.groupPics_a=resp.data;/*组图小图弹出层*/
               
               //加入购物车                        
                        vm.addCart = function (cartId) {  
                            req.post("car/add.do", {
                                //pictureId: $scope.groupPics.coverPictureId
                                pictureId: cartId,
                                langType:window.localStorage.lang
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
                                    // layer.alert(resp.msg);
                                 }else if(resp.msg != '未登录'){
                                    layer.alert(resp.msg);
                                 }
                             });
                         };
               /*获取另一个接口里的水印图给第一个弹出层*/
               req.post('getPicture/getClientPicture.do', {
                    groupId:$stateParams.id,
                    pictureId:$scope.groupPics.coverPictureId
                }).success(function (resp) {
                    if (resp.code == '211') {
                        console.log(resp);
                        $scope.groupPicsHasCoverPArray = resp.data;
                        
                        
                    }else if(resp.msg != '未登录'){
                        layer.alert(resp.msg);
                    }
                });

            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }           
        });  
});