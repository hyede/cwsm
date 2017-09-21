//@ sourceURL=function.js
/**
 * 资源管理(菜单、按钮等)模块js
 */
var QiYuFunction = function () {
    //资源主页面
    var $functionTree;//树定义
    var functionId="functionTree";//树节点id
    var functionDataAjaxUrl="function/json/list";//获取资源json数据地址
    var functionData;//资源josn数据
    var functionInfoAjaxHtml="function/info";//获取资源信息
    var rightContentClass="todo-content";
    var deleteFunctionpAjaxUrl = "function/";
    //增加资源属性
    var $addFunctionForm=$("#functionAddForm");//增加资源表单
    var addFunctionAjaxUrl=QiYuComponents.getContextPath()+"/function";
    var $addFunctionBtn =$("#addFunctionBtn");//增加资源按钮
    //修改资源属性
    var $updateFunctionForm = $("#functionUpdateForm");
    var updateFunctionAjaxUrl=QiYuComponents.getContextPath()+"/function";
    var $updateFunctionBtn = $("#updateFunctionBtn");

    var initFunctionData=function () {
        $.ajax({
            type:"GET",
            cache:false,
            url:functionDataAjaxUrl,
            success:function(responseJson){
                if(responseJson.success){//成功
                    functionData=responseJson.obj;
                    initFunctionTree();//初始化树列表
                }else{
                    QiYuComponents.bootstrapSweetAlert("",responseJson.msg,"error");
                }
            },
            error:function (XMLHttpRequest, textStatus, errorThrown) {
                QiYuComponents.bootstrapSweetAlert("","获取资源数据出错,系统错误!!!","error");
            }
        });
    }

    var addHoverDom=function(treeId, treeNode) {
        var sObj = $("#" + treeNode.tId + "_span");
        if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
            + "' title='add node' onfocus='this.blur();'></span>";
        sObj.after(addStr);
        var btn = $("#addBtn_"+treeNode.tId);
        if (btn) btn.bind("click", function(){
            QiYuComponents.layerOpen("增加资源",'800px','350px',"function/add/view?funId="+treeNode.id)
        });
    };
    var removeHoverDom=function(treeId, treeNode) {
        $("#addBtn_"+treeNode.tId).unbind().remove();
    };

    var onZtreeClick=function(event, treeId, treeNode) {
        showFunInfoData(treeNode);
    }

    var onZtreeEditorClick=function (treeId, treeNode) {
        QiYuComponents.layerOpen("修改资源",'800px','350px',"function/update/view?functionId="+treeNode.id);
        return false;
    }
    var onZTreeRemoveClick = function (treeId, treeNode) {
        if(null!=treeNode.children && treeNode.children.length>0){
            QiYuComponents.bootstrapSweetAlert("","不能删除父节点","error");
            return false;
        }
        swal({
                title: "确定删除 "+treeNode.name+"?",
                text: "",
                type: "warning",
                showCancelButton: true,
                confirmButtonClass: "btn-danger",
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                closeOnConfirm: false,
                closeOnCancel: false
            },
            function(isConfirm) {
                if (isConfirm) {
                    $.ajax({
                        type:"DELETE",
                        cache:false,
                        url:deleteFunctionpAjaxUrl+"?id="+treeNode.id,
                        success:function(responseJson){
                            if(responseJson.success==true){//返回true
                                $functionTree.removeNode(treeNode);//动态删除该节点
                                QiYuComponents.bootstrapSweetAlert("",responseJson.msg,"success");
                            }
                            if(responseJson.success==false){//返回false
                                QiYuComponents.bootstrapSweetAlert("",responseJson.msg,"error");
                            }
                        },
                        error:function (XMLHttpRequest, textStatus, errorThrown) {
                            QiYuComponents.bootstrapSweetAlert("","系统错误!!!","error");
                        }
                    });
                } else {
                    swal("", "已经取消了当前操作", "error");
                }
            });
        return false;
    }

    var initFunctionTree=function () {
        //初始化树的配置参数
        var setting = {
            view: {
                showLine: true,
                addHoverDom: addHoverDom,
                removeHoverDom: removeHoverDom,
                selectedMulti: false
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            check: {
                enable: false,//在节点前显示checkbox
                chkboxType: {"Y": "", "N": ""}//父节点和字节不级联
            },
            edit: {
                enable: true
            },
            callback: {
                onClick: onZtreeClick,
                beforeEditName:onZtreeEditorClick,
                beforeRemove: onZTreeRemoveClick
            }
        };
        if(null!=$functionTree && ""!=$functionTree && undefined!=$functionTree){//不为空

        }else{
            $functionTree = $.fn.zTree.init($("#"+functionId), setting, functionData);
        }
        var allNodes = $functionTree.getNodes();
        if(allNodes.length>0){//查询出来的是有数据的
            if(null!=allNodes[0].children && allNodes[0].children.length>0) $functionTree.expandNode(allNodes[0]);//展开第一个节点
            showFunInfoData(allNodes[0]);
        }
    }

    var showFunInfoData=function(treeNode){
        $.ajax({
            type:"GET",
            cache:false,
            url:functionInfoAjaxHtml+"/"+treeNode.id,
            success:function(responseJson){
                if(responseJson.success==true){//返回true
                    $("#functionName").html("资源名称:"+treeNode.name);
                    var obj  = responseJson.obj;
                    if(null!=obj && ""!=obj){
                        $.each($("#functionInfoForm").find("input"),function(index,value){
                            var element=$(value);
                            var key=element.attr("name");
                            if(typeof(key) != "undefined"){
                                if(key=="type"){//数字变成文字
                                    if(obj[key]==1){//菜单
                                        element.val("菜单");
                                    }else if(obj[key]==0){//按钮
                                        element.val("按钮");
                                    }
                                }else{
                                    element.val(obj[key]);
                                }
                            }
                        });
                    }else{
                        QiYuComponents.bootstrapSweetAlert("","获取数据为空值","error");
                    }
                }
                if(responseJson.success==false){//返回false
                    QiYuComponents.bootstrapSweetAlert("",responseJson.msg,"error");
                }
            },
            error:function (XMLHttpRequest, textStatus, errorThrown) {
                QiYuComponents.bootstrapSweetAlert("","系统错误!!!","error");
            }
        });
    }

    var initFunctionAddBootStrapValidate = function () {
        $addFunctionForm.bootstrapValidator({
            fields: {
                funName: {
                    validators: {
                        notEmpty: {
                            message: '资源名称不能为空'
                        }
                    }
                },
                funCode:{
                    validators: {
                        notEmpty: {
                            message: '资源编码不能为空'
                        }
                    }
                },
                type:{
                    validators: {
                        notEmpty: {
                            message: '资源类型不能为空'
                        }
                    }
                },
                url:{
                    validators: {
                        notEmpty: {
                            message: '资源地址不能为空'
                        }
                    }
                },
                icon:{
                    validators: {
                        notEmpty: {
                            message: '资源图标不能为空'
                        }
                    }
                }
            }
        }).on("success.form.bv",function(e){
            swal({
                    title: "确定提交表单?",
                    text: "",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonClass: "btn-danger",
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    closeOnConfirm: false,
                    closeOnCancel: false
                },
                function(isConfirm) {
                    if (isConfirm) {
                        functionAjax(addFunctionAjaxUrl,$addFunctionForm.serialize(),"POST");
                    } else {
                        swal("", "已经取消了当前操作", "error");
                    }
                });
        });
    }

    var functionAjax = function (ajaxUrl,data,method) {
        $.ajax({
            type:method,
            cache:false,
            url:ajaxUrl,
            data:data,
            success:function(responseJson){
                if(responseJson.success==true){//返回true
                    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                    parent.layer.close(index);
                    (parent.QiYuComponents).bootstrapSweetAlert("",responseJson.msg,"success");
                    if(method=="POST"){
                        (parent.QiYuFunction).addFunctionNode(responseJson.obj);//调用添加方法
                    }
                    // else if(method=="PUT"){
                    //     (parent.QiYuDep).updateDepNode(responseJson.obj);//调用修改方法
                    // }

                }
                if(responseJson.success==false){//返回false
                    QiYuComponents.bootstrapSweetAlert("",responseJson.msg,"error");
                }
            },
            error:function (XMLHttpRequest, textStatus, errorThrown) {
                QiYuComponents.bootstrapSweetAlert("","系统错误!!!","error");
            }
        });
    }

    var initFunctionUpdateBootStrapValidate = function () {
        $updateFunctionForm.bootstrapValidator({
            fields: {
                funName: {
                    validators: {
                        notEmpty: {
                            message: '资源名称不能为空'
                        }
                    }
                },
                funCode:{
                    validators: {
                        notEmpty: {
                            message: '资源编码不能为空'
                        }
                    }
                },
                type:{
                    validators: {
                        notEmpty: {
                            message: '资源类型不能为空'
                        }
                    }
                },
                url:{
                    validators: {
                        notEmpty: {
                            message: '资源地址不能为空'
                        }
                    }
                },
                icon:{
                    validators: {
                        notEmpty: {
                            message: '资源图标不能为空'
                        }
                    }
                }
            }
        }).on("success.form.bv",function(e){
            swal({
                    title: "确定提交表单?",
                    text: "",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonClass: "btn-danger",
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    closeOnConfirm: false,
                    closeOnCancel: false
                },
                function(isConfirm) {
                    if (isConfirm) {
                        functionAjax(updateFunctionAjaxUrl,$updateFunctionForm.serialize(),"PUT");
                    } else {
                        swal("", "已经取消了当前操作", "error");
                    }
                });
        });
    }
    /**
     * 增加页面调用父页面方法,给zTree动态添加数据
     * @param depNode
     */
    var addFunctionNode = function (funNode) {
        var pNode = $functionTree.getNodeByParam("id",funNode.pId, null);
        $functionTree.addNodes(pNode,funNode);
    }
    /**
     * 修改页面调用父页面方法,给zTree动态修改数据
     * @param depNode
     */
    // var updateDepNode = function (depNode) {
    //     $depTree.updateNode(depNode);
    // }
    return{
        //初始主界面树
        initFunctionMain:function () {
            initFunctionData();
        },
        //初始化资源增加页面
        initFunctionAdd:function () {
            initFunctionAddBootStrapValidate();
            $addFunctionBtn.on("click",function () {
                $addFunctionForm.submit();
            });
        },
        //初始化资源修改页面
        initFunctionUpdate:function () {
            initFunctionUpdateBootStrapValidate();
            $updateFunctionBtn.on("click",function () {
                $updateFunctionForm.submit();
            });
        },
        //增加页面调用父页面方法
        addFunctionNode:function (funNode) {
           addFunctionNode(funNode);
        }
        //修改页面调用父页面方法
        // updateDepNode:function (depNode) {
        //     updateDepNode(depNode);
        // }
    }
}();
