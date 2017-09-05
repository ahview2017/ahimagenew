cnsphoto_enModule.controller('advSearchCtrl',function($scope,$cookies,req,md5,$state,$rootScope,layerIfShow,$stateParams,getFullText){
        var vm = this;
        function initSetting(){
            //初始化搜索对象
            vm.orginalSearch = {
                gtitle:'',
                people:'',
                place:'',
                authorName:'',
                keyWords:'',
                memo:'',
                category_id:'',
                fileName:'',
                staTime:'',
                endTime:''
            }
            //最终搜索对象
            vm.finaSearch = {};
        }
        function init(){
            initSetting();
            getselCpCategories(function(category){
                angular.forEach(category,function(item,index){
                    if(item.categoryName == '新闻图片'){
                        vm.categories = item.categories;
                    }
                });
            });
        }
        init();


        //获取稿件分类信息
        function getselCpCategories(callback){
            req.post('classification/selCpCategories.do',{
            	langType:window.localStorage.lang
            }).success(function(resp){
                if(resp.code == '211'){
                    vm.selCpCategories = resp.data;console.log(resp.data);
                    if (callback) callback(resp.data);
                    console.log('success');
                }else if(resp.msg != '未登录'){
                    layer.alert(resp.msg);
                }
            });
        }

        //高级检索
        vm.search = function(){
            if(vm.orginalSearch && vm.orginalSearch.gtitle){
                vm.finaSearch.gtitle = vm.orginalSearch.gtitle;
                
            }
            if(vm.orginalSearch && vm.orginalSearch.properties){
                vm.finaSearch.properties = vm.orginalSearch.properties;
            }
            if(vm.orginalSearch && vm.orginalSearch.people){
                vm.finaSearch.people = vm.orginalSearch.people;
            }
            if(vm.orginalSearch && vm.orginalSearch.place){
                vm.finaSearch.place = vm.orginalSearch.place;
            }
            if(vm.orginalSearch && vm.orginalSearch.authorName){
                vm.finaSearch.authorName = vm.orginalSearch.authorName;
            }
            if(vm.orginalSearch && vm.orginalSearch.keyWords){
                vm.finaSearch.keyWords = vm.orginalSearch.keyWords;
            }
            if(vm.orginalSearch && vm.orginalSearch.category_id){
                vm.finaSearch.category_id = vm.orginalSearch.category_id;
            }
            if(vm.orginalSearch && vm.orginalSearch.memo){
                vm.finaSearch.memo = vm.orginalSearch.memo;
            }
            if(vm.orginalSearch && vm.orginalSearch.category_id){
                vm.finaSearch.category_id = vm.orginalSearch.category_id;
            }
            if(vm.orginalSearch && vm.orginalSearch.fileName){
                vm.finaSearch.fileName = vm.orginalSearch.fileName;
            }
            if(vm.orginalSearch && vm.orginalSearch.staTime){
                vm.finaSearch.staTime = vm.orginalSearch.staTime;
            }
            if(vm.orginalSearch && vm.orginalSearch.endTime){
                vm.finaSearch.endTime = vm.orginalSearch.endTime;
            }
            var fullTextUrl = $state.href('root.fullText', {searchObj:angular.toJson(vm.finaSearch)});
            window.open(fullTextUrl,'_self');
        }
});