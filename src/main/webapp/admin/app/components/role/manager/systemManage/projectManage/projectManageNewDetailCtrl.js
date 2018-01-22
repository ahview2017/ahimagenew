adminModule.controller('projectManageNewDetailCtrl',function($scope,$cookies,req,md5,$state,$rootScope,$stateParams,lanmuShareDataService,Upload){
    var vm = this;
    // 从服务获取专题栏目数据
    $scope.items = lanmuShareDataService.lanmuData; 
    // 从路由获取专题id
    $scope.topicId = $stateParams.id;
    // 从路由获取专题名字
    $scope.topicName = $stateParams.name;

    // 栏目显示方式数组
    $scope.showWayArr = [
        {
            id: 1,
            val: '人工签发'
        },
        {
            id: 2,
            val: '条件检索'
        },
        {
            id:3,
            val: '外部链接'
        }
    ]


     /**
     * 富文本编辑器配置
     * 初始化
     * */
    var configParams = {
        //focus时自动清空初始化时的内容
        autoClearinitialContent: true,
        //关闭字数统计
        wordCount: false,
        //关闭elementPath
        elementPathEnabled: false,
        //默认的编辑区域高度
        initialFrameHeight: 300,
        autoHeightEnabled: false,
        autoFloatEnabled: false
    }
    
    //实例化一个编辑器
    var addNoticeUE = new UE.ui.Editor(configParams);
    addNoticeUE.render('addAnnocementEditor');

    // var bannerDescUE = new UE.ui.Editor(configParams);
    // bannerDescUE.render('bannerDescEditor');
   
    

     
    // 添加栏目
    $scope.add=function() {
        $scope.items.push({
            id:'',
            name: '',
            lanmuNo:'',
            showWay:'',
            typeId:'',
            keyWords:'',
            url:'',
            pic:'',
            zhanshuCount:'',
            words:'',
            cpLanmuList:[],
            moreLanmuPictures:[],
            cpLanmuPictures:[],
            cpNoticesList:[]
        });
         
    }

    // 添加子栏目
    $scope.addChild=function(item) {
        // item.childItems=[{columnName: '栏目1', lanmuNo: 0}];
        item.cpLanmuList.push({
            id:'',
            name: '',
            lanmuNo:'',
            showWay:'',
            typeId:'',
            keyWords:'',
            url:'',
            pic:'',
            zhanshuCount:'',
            words:'',
            cpLanmuList:[],
            moreLanmuPictures:[],
            cpLanmuPictures:[],
            cpNoticesList:[]
        });
         
    }
    // 接受最后栏目id
    vm.finalLanmuIds = '';
    var acceptLanmuArr = [];
    // 获取被删的栏目id
    function getDelLanmuIds(item){
        if(item.id){
            acceptLanmuArr.push(item.id);
        }
        vm.finalLanmuIds = acceptLanmuArr.join(',');
    }
    // 删除栏目
    $scope.delecolumn = function(item,index){
        getDelLanmuIds(item);
        $scope.items.splice(index,1);
    }
    // 选择模板
    $scope.selColTemplate = function(item){
        // 中间过渡数组
        var middleArr = [];
        // 模板个数
        var templateNum = 0;
        switch(item.typeId){
            case  '0': //当typeId为1时，表示轮播图+公告模板
                templateNum = 5;
                item.zhanshuCount =  5;
                break;
            case  '1': //当typeId为2时，表示大轮播图
                templateNum = 5;
                item.zhanshuCount =  5;
                break;
            case  '2': //当typeId为3时，表示轮播图+说明模板
                templateNum = 5;
                item.zhanshuCount =  5;
                break;
            case  '3': //当typeId为3时，表示4展模板
                templateNum = 4;
                item.zhanshuCount =  4;
                break;
            case  '4': //当typeId为3时，表示5展模板
                templateNum = 5;
                item.zhanshuCount =  5;
                break;
            case  '5': //当typeId为3时，表示6展模板
                templateNum = 6;
                item.zhanshuCount =  6;
                break;
            case  '6': //当typeId为7时，表示8展模板
                templateNum = 8;
                item.zhanshuCount =  8;
                break;
            case  '7':  //当typeId为7时，表示10展模板
                templateNum = 10;
                item.zhanshuCount =  10;
                break;
        }
        // 根据templateNum个数生成对应模板
        for(var i = 0; i < templateNum;i++){
            middleArr.push({
                id:'',
                potision: i+1,
                groupId:'',
                url:'',
                flag:false,
                title:''
            });
        }
        // 中间过渡数组赋值给对应的cpLanmuPictures
        item.cpLanmuPictures = middleArr;
    }

    // 添加公告
    $scope.addAnnocement = function(item){
        $scope.WillAddNotices = item.cpNoticesList;
        $('#addAnnocementModal').modal('show');
    }

    // 保存公告
    $scope.saveAnnocement = function(){
        vm.noticeContent = addNoticeUE.getContent();
        $scope.WillAddNotices.push({
            id:'',
            noticeTitle: vm.annocementTitle,
            noticeContent: vm.noticeContent,
            topicId: $scope.topicId,
            topicName: $scope.topicName,
            position: vm.annocementOrderNum,
            langType: '0'
        });
         
        $('#addAnnocementModal').modal('hide');
    }
    //  保存栏目设置
    $scope.saveColumnSetting = function(){
         
        var finalParams = {
            id: $scope.topicId ,
            children:$scope.items
        }
        req.post('enTopicColum/add.do',{
            cpTopic:  angular.toJson(finalParams, true),
            lanmuIds:  vm.finalLanmuIds,
        }).success(function(resp){
            // 清空最后栏目id
            vm.finalLanmuIds = '';
            init();
        });
    }


    // 选择稿件
    $scope.selectMs = function(template){
        // 设置flag是否要选择图片(true：是，false:否)
        template.flag = true;
        layer.confirm('请您先保存页面数据，以防数据丢失',{
            btn: ['前往选择稿件', '返回保存']
        }, function (index) {
            $cookies.put('columnFlag', true);
            $state.go('role.manager.database',{topicId: $scope.topicId});
            // var url = $state.href('role.manager.database');
            // window.open(url,'_blank');
            layer.close(index);
        }, function () {
            //取消
        });
    }

    // 添加更多稿件
    $scope.addMoreMs = function(item){
        // 设置flag是否要添加更多稿件图片(true：是，false:否)
        item.flag = true;
        layer.confirm('请您先保存页面数据，以防数据丢失',{
            btn: ['前往选择稿件', '返回保存']
        }, function (index) {
            $cookies.put('columnMoreFlag', true);
            $state.go('role.manager.database',{topicId: $scope.topicId});
            // var url = $state.href('role.manager.database');
            // window.open(url,'_blank');
            layer.close(index);
        }, function () {
            //取消
        });
    }

    // 上传banner图
    $scope.uploadBannerFile = function(file,item){
        if(!file)  return;
        Upload.upload({
            url: '/photo/enTopicColum/upPic.do',
            data: {pic: file}
        }).then(function (resp) {
            item.pic = resp.data.data;
        }, function (resp) {
        }, function (evt) {

        });
    }

    function init(){
        req.post('enTopicColum/show.do',{id: $scope.topicId}).success(function(resp){
            // if(resp.data && resp.data.length > 0){
            //     $scope.items = resp.data;
            // }
        });
    }
    init();

});