clientModule.controller('advSearchCtrl',function($scope,$cookies,req,md5,$state,$rootScope,layerIfShow,$stateParams,getFullText){
        var vm = this;
        function initSetting(){
            //初始化搜索对象
            vm.orginalSearch = {
                /*gtitle:'',
                people:'',
                place:'',
                authorName:'',
                keyWords:'',
                memo:'',
                category_id:'',
                fileName:'',
                staTime:'',
                endTime:''*/
            }
            //最终搜索对象
            vm.finaSearch = {};
        }
        function init(){
            initSetting();
            getselCpCategories(function(category){
                angular.forEach(category,function(item,index){
                    if(item.categoryName == '新闻类别'){
                        vm.categories = item.categories;
                    }
                });
            });
        }
        init();


        //获取稿件分类信息
        function getselCpCategories(callback){
            req.post('classification/selCpCategories.do',{
            	langType:0
            }).success(function(resp){
                if(resp.code == '211'){
                    vm.selCpCategories = resp.data;
                    if (callback) callback(resp.data);
                    console.log('success');
                }else if(resp.msg != '未登录'){
                    layer.alert(resp.msg);
                }
            });
        }

        //高级检索
        vm.search = function(){
            var fullTextUrl = $state.href('root.fullText', {searchObj:angular.toJson(vm.orginalSearch)});
            window.open(fullTextUrl,'_blank');
        }
        //重置
        vm.reset = function(){
            initSetting();
        }
});