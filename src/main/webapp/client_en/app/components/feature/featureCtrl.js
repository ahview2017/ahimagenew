/**
 * Created by wx on 2017/5/10.
 */
photo_enModule.controller('featureCtrl', function () {
    var vm = this;
    console.log("featureCtrl");

    $(document).ready(function () {
        /*news选项卡*/
        var nav_ul=document.getElementById('nav_ul');
        var aNav_title_li=nav_ul.getElementsByTagName('li');
        var nav_cont=getClass('div','nav_cont_content');
        console.log("li"+aNav_title_li.length);
        console.log('cont'+nav_cont.length);
        nav_cont[0].style.display='block';

        for(var i=0;i<aNav_title_li.length;i++){
            aNav_title_li[i].index=i;
            aNav_title_li[i].onclick=function(){
                for(var j=0;j<aNav_title_li.length;j++){
                    aNav_title_li[j].className='';
                }
                for(var j=0;j<aNav_title_li.length;j++){
                    nav_cont[j].style.display='none';
                }
                this.className='active';
                nav_cont[this.index+1].style.display='block';
            };
        }


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
    })

});