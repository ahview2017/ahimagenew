/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('projectManageViewCtrl',function($scope,$cookies,req,md5,$state,$rootScope,$stateParams){
    var vm = this;
    //专题id
    vm.topicId = $stateParams.topicId;
    vm.columnId = $stateParams.id;
    vm.templateId = $stateParams.templateId;
    /**
     * 根据子栏目模板显示稿件数量
     * type 子栏目模板类型 4,5,6,8,10
     * contribute 现有稿件
     * */
    function verdict (type,contribute) {
        contribute = contribute ? contribute : [];
        var num  = parseInt(type);
        var arr = [];
        if(num === 4){
            arr.length = 4;
        }else if (num === 5){
            arr.length = 5;
        }else if (num === 6){
            arr.length = 6;
        }else if(num === 8){
            arr.length = 8;
        }else if(num === 10){
            arr.length = 10;
        }
        for(var i = 0; i < contribute.length; i++){
            arr[(contribute[i].potision - 1)] = contribute[i];
        }
        return arr;
    }
    /**请求栏目信息
     * id 栏目id
     */
    function reqColInfo (id) {
        if(vm.templateId == 2){
            req.post('lanmu/lanMuPicWithNoticePreview.do',{
                lanmuid: id
            }).success(function (resp) {
                if(resp.code == '211'){
                    vm.colName = resp.data.name;
                    vm.columnTemplate = resp.data.typeId;
                    vm.picList = resp.data.cpLanmuPictures;
                    vm.ggList = (resp.data.cpNoticesList || []).slice(0,8);
                    $scope.$on('ngRepeatFinished', function() {
                        var mySwiper = new Swiper('.swiper-container',{
                            autoplay: 2000,
                            pagination : '.swiper-pagination'
                        });
                    });
                }
            });
        }else{
            req.post('lanmu/lanMuPicPreview.do', {
                lanmuid: id
            }).success(function(resp){
                if(resp.code == '211'){
                    vm.colName = resp.data.name;
                    vm.columnTemplate = resp.data.typeId;
                    vm.colPic = verdict(vm.columnTemplate, resp.data.cpLanmuPictures);
                    $scope.$on('ngRepeatFinished', function() {
                        var mySwiper = new Swiper('.swiper-container',{
                            autoplay: 2000
                        });
                    });
                }
            });
        }
    }
    /**
     * 返回编辑页
     */
    vm.backEdit = function () {
        var _url = 'role.manager.projectManageEdit' + vm.columnTemplate;
        $state.go(_url, {id:vm.columnId,specialId:vm.topicId});
    }
    reqColInfo(vm.columnId);
});