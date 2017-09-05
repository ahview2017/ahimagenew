/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('sensitiveWordManageCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, modalOperate, allModalMove) {
    var vm = this;

    //从cookie获取userToken
    vm.userToken = $cookies.get('userToken');

    //模态框遮罩层显示
    function maskShow() {
        $rootScope.layerIfShow = true;
    }

    //模态框遮罩层隐藏
    function maskHide() {
        $rootScope.layerIfShow = false;
    }
    //移动模态框
    vm.moveModal = function(dragDiv,tagDiv) {
        allModalMove.modalMove(dragDiv,tagDiv);
    }

    //敏感词模态框显示
    vm.sensitiveModalShow = function (modalId) {
        $('#' + modalId).show();
        maskShow();
    };

    //敏感词模态框隐藏
    vm.sensitiveModalHide = function (modalId) {
        $('#' + modalId).hide();
        maskHide();
    };
    vm.addWord = function () {
        req_add(function (resp) {
            renderList("",vm.pagination.current);
            vm.sensitiveModalHide("sensitive-add-modal");
        })
    };
    vm.onCancelAddWordClick = function () {
        vm.addSensitiveWordModel = "";
        vm.addWordRecordModel = "";
        vm.addWordNoModel = "";
        vm.sensitiveModalHide('sensitive-add-modal');
         vm.sensitiveModalHide('sensitive-addlot-modal');
    };
    vm.updateWord = function () {
        req_update(function (resp) {
            renderList("",vm.pagination.current);
            vm.sensitiveModalHide("sensitive-modify-modal");
        })
    };
    vm.showUpdateModel = function (word) {
        //resetTempWordData();
        vm.updateId = word.ID;
        vm.tempWordData.status = word.WORD_STATUS;
        vm.tempWordData.memo = word.MEMO;
        vm.tempWordData.sstvWord = word.WORD_CONTENT;
        //vm.tempWordData.unmOrder = word.UNM_ORDER;
        vm.sensitiveModalShow("sensitive-modify-modal");
    };
    //选择checkbox进行修改
    vm.oneUpdate = function () {
        //resetTempWordData();
        vm.updateId = vm.listData.filter(function (item) {
            return item.Checked;
        }).map(function (item) {
            return item.ID;
        }).join(',');
        vm.tempWordData.status = vm.listData.filter(function (item) {
            return item.Checked;
        }).map(function (item) {
            return item.WORD_STATUS;
        }).join(',');

        vm.tempWordData.memo =  vm.listData.filter(function (item) {
            return item.Checked;
        }).map(function (item) {
            return item.MEMO;
        })

        vm.tempWordData.sstvWord =  vm.listData.filter(function (item) {
            return item.Checked;
        }).map(function (item) {
            return item.WORD_CONTENT;
        })


        if(((vm.updateId.length == 0) && (vm.updateId.split(',').length == 1)) || ((vm.updateId.length > 0) && (vm.updateId.split(',').length > 1))) {
            layer.alert('请选择一条数据进行操作！');
        } else if ((vm.updateId.length != 0) && (vm.updateId.split(',').length == 1)) {
            vm.sensitiveModalShow("sensitive-modify-modal");
        }
    };
    vm.delWord = function () {
        req_delete(function (resp) {
            renderList("",vm.pagination.current);
            vm.sensitiveModalHide("sensitive-del-modal");
        })
    };
    vm.showDelModel = function (wordId) {
        vm.deleteIdStr = wordId;
        vm.sensitiveModalShow("sensitive-del-modal");
    };
    //批量删除
    vm.multiDelete = function () {
        vm.deleteIdStr = vm.listData.filter(function (item) {
            return item.Checked;
        }).map(function (item) {
            return item.ID;
        }).join(',');
        if (vm.deleteIdStr.length == 0) {
            layer.alert('请至少选择一条数据进行操作！');
        } else {
            vm.sensitiveModalShow("sensitive-del-modal");
        }
    };

    //全选
    vm.selectAll = function () {
        var selectedAll = vm.listData.every(function (item) {
            return item.Checked;
        });
        vm.listData.map(function (item) {
            item.Checked = selectedAll ? false : true;
            return item;
        })
    };
    function resetSelectAll() {
        vm.SelectedAll = false;
    }

    function hasSelectedAll() {
        return vm.listData.every(function (item) {
            return item.Checked;
        })
    }

    vm.selectItem = function () {
        vm.SelectedAll = hasSelectedAll() ? true : false
    };

    function req_list(searchStr, pageNum, callback) {
        req.post('sstvWordCtro/getSstvWordByQuery.do', {
            sstvWord: searchStr,
            page: pageNum,
            rows: vm.selPageRows,
            userToken: vm.userToken
        }).success(function (resp) {
            vm.totalPages = resp.page;
            vm.sensitiveList_total = resp.other;
            if (callback) callback(resp.data);

        });
    }

    //页数变化
    vm.pageChanged = function (pageNumber) {
        renderList(vm.searchSensitiveModel,pageNumber);
    };

    function req_add(callback) {
        req.post('sstvWordCtro/addSstvWord.do', {
            sstvWord: vm.addSensitiveWordModel,
            memo: vm.addWordRecordModel,
            status: vm.addWordNoModel,
            userToken: vm.userToken
        }).success(function (resp) {
            if (resp.code && resp.code == 211) {
                vm.addSensitiveWordModel = "";
                vm.addWordRecordModel = "";
                vm.addWordNoModel = "";
                callback(resp.data)
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        })
    }

    function req_update(callback) {
        req.post('sstvWordCtro/updateSstvWord.do', {
            sstvWordId: vm.updateId,
            status: vm.tempWordData.status,
            sstvWord: vm.tempWordData.sstvWord,
            memo: vm.tempWordData.memo,
            userToken: vm.userToken
            //unmOrder: vm.tempWordData.unmOrder
        }).success(function (resp) {
            if (resp.code && resp.code == 211) {
                callback(resp.data)
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        })
    }

    function req_delete(callback) {
        req.post('sstvWordCtro/delSstvWordByIds.do', {
            sstvWordIds: vm.deleteIdStr,
            userToken: vm.userToken
        }).success(function (resp) {
            if (resp.code && resp.code == 211) {
                callback(resp.data)
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        })
    }

    function renderList(searchStr,pageNumber) {
        req_list(searchStr, pageNumber, function (listData) {
            vm.listData = listData.map(function (item) {
                item.Checked = false;
                return item;
            })

        });
        resetSelectAll();
    }

    function resetTempWordData() {
        vm.tempWordData = {
            sstvWord: '',
            memo: '',
            status: '',
            unmOrder: ''
        }
        //存放敏感词数组
        vm.listData = [];
        //在线用户总条数
        vm.sensitiveList_total = 0;
        //默认当前页1
        vm.pagination = {
            current: 1
        };
        //默认每页10条
        vm.selPageRows = '10';
    }

    function init() {
        resetTempWordData();
        renderList("",vm.pagination.current);
    }

    init();

    /**
     * 搜索
     */
    vm.onSearchSensitiveClick = function () {
        renderList(vm.searchSensitiveModel,vm.pagination.current);
    };

    /**
     * 回车搜索
     */
    vm.onEnterSearchClick = function (e) {
        var keyCode = window.event ? e.keyCode : e.which;
        if (keyCode == 13) {
            vm.onSearchSensitiveClick();
        }
    };
    
    //批量添加
     $scope.onSensitiveClick = function (param) {  
     	console.info(0)
	       $("#uploadSensitiveForm").ajaxSubmit({
	            xhrFields: {
	                withCredentials: true
	            },
               async:false,
	            success: function (resp) {
	            	console.info(resp)
	            if (resp.code == '211') {
	                //vm.currentClickNodeId = "";
	                //vm.addSortGrade = "";
	                //vm.addSortParentId = "";
	                renderList("",vm.pagination.current);
	                layer.msg('保存成功！成功：'+resp.other.successCount+',重复未添加数：'+resp.other.failCount);
	            } else {
	            	
	                layer.alert("保存失败");
	            }
	        }});
        return false;
    };
    //附件下载
    $scope.fileSensitiveDown = function (url, name) {
    	window.location.href="cnsphoto/enSensitiveWordManage/downFile.do";
    }
    
});