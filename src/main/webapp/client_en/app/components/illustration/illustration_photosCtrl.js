photo_enModule.controller('illustration_photosCtrl', function () {
    var vm = this;
    console.log("illustration_photosCtrl");
    $(document).ready(function () {
        /*弹出层的显示*/
        var popup_box=document.getElementById('popup_box');
        var aClose=document.getElementById('close');
        var photos_cont_img_box=getClass('div','photos_cont_img_box');
        var popup_container=getClass('div','popup_container');
        var photos_banner_l_click=getClass('div','photos_banner_l_click');
        console.log(photos_banner_l_click.length);

        aClose.onclick=function(){
            popup_box.style.display='none';
        };

        /*所有的弹出层隐藏，点击对应的img出现弹出层*/
        for(var i=0;i<popup_container.length;i++){
            popup_container[i].style.display='none';
        }

        for(var i=0;i<photos_banner_l_click.length;i++){
            photos_banner_l_click[i].onclick=function(){
                popup_box.style.display='block';
                popup_container[0].style.display='block';
            }
        }


        for(var i=0;i<photos_cont_img_box.length;i++){
            photos_cont_img_box[i].index=i;
            photos_cont_img_box[i].onclick=function(){
                popup_box.style.display='block';
                for(var j=0;j<popup_container.length;j++){
                    popup_container[j].style.display='none';
                }
                popup_container[this.index].style.display='block';

                /*弹出层切换*/
                var oIndex=this.index;//获取到当前的index值
                var popup_next=document.getElementById('popup_next');
                var popup_pre=document.getElementById('popup_pre');

                popup_next.onclick=function(){
                    console.log(popup_container.length)
                    for(var i=0;i<popup_container.length;i++){
                        popup_container[i].style.display='none';
                    }

                    if(oIndex<popup_container.length-1){
                        oIndex+=1;
                        popup_container[oIndex].style.display='block';
                    }else{
                        popup_container[oIndex].style.display='block';
                    }

                }
                popup_pre.onclick=function(){
                    for(var i=0;i<popup_container.length;i++){
                        popup_container[i].style.display='none';
                    }

                    if(oIndex>0){
                        oIndex-=1;
                        popup_container[oIndex].style.display='block';

                    }else{
                        popup_container[oIndex].style.display='block';
                    }

                }


            }
        }


        /*S/M/L具体信息切换*/
        var popup_select_box=getClass('li','popup_select_box');
        var popup_select_detail=getClass('li','popup_select_detail');
        var count=0
        popup_select_detail[0].style.display='block';
        popup_select_detail[4].style.display='block';

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
        if(document.getElementsByClassName){
            return document.getElementsByClassName(classname);
        }else{
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
    }
});