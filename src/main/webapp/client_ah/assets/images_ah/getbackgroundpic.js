
$.post('adver/adverToHomePage.do', {}).success(function (resp) {
	var adShowImageArray = [];
	if (resp.code == '211') {

        var adArray = resp.data;
        var isEqualFlag = false;
        for (var i = 1; i < 14; i++) {
            for (var j = 0; j < adArray.length; j++) {
                if (adArray[j]['position'] == i) {
                    isEqualFlag = true;
                    insertIndexArrayFun(adShowImageArray, i - 1, adArray[j]);
                    break;
                } else {
                    isEqualFlag = false;
                }
            }
            if (!isEqualFlag) {
                insertIndexArrayFun(adShowImageArray, i - 1, {file: ""});
            }
        }
       
       if(adShowImageArray[0]['file']!=null&&adShowImageArray[0]['display']==1){
    	   $(".wrapper").css("background","url("+adShowImageArray[0]['file']+") no-repeat center top");
    	   $(".wrapper").css("background-attachment","fixed");
       }
       
    }else if(resp.msg != '未登录'){
        layer.alert(resp.msg);
    }
});
 
 /**
  * 向数组指定位置插入数据
  * @param array 原数组
  * @param index 指定位置
  * @param item  插入元素
  */
 function insertIndexArrayFun(array, index, item) {
     return array.splice(index, 0, item);
 }