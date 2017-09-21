/**
 * 一些插件的封装和共用方法
 */
var QiYuComponents = function () {
    var getContextPath=function () {
        var pathName = document.location.pathname;
        var index = pathName.substr(1).indexOf("/");
        var result = pathName.substr(0,index+1);
        return result;
    }
    /**
     * ajax方式获取整个页面的代码
     * @param ajaxUrl
     * @param data
     * @param targetClass 一般是page-content
     */
    var getPageData = function (ajaxUrl,data,targetClass) {
        $.ajax({
            url : ajaxUrl,
            data : data,
            dataType : "html",
            beforeSend:function(XMLHttpRequest){
                // 显示遮罩层
                App.blockUI({target : '.'+targetClass,animate : true});
            },
            success : function(htmlData) {
                $("."+targetClass).html(htmlData);
            },
            complete:function(XMLHttpRequest, textStatus){
                //请求结束方法增强处理  ,隐藏遮罩层
                App.unblockUI('.'+targetClass);
            },
            error: function (response) {
                $("."+targetClass).html(response.responseText);
            }
        });
    }
    /**
     * 绑定左侧菜单的点击事件
     */
    var handleLeftMenuClick = function () {
        $(".page-sidebar-menu").off("click","li > a").on("click","li > a",function(){
            var $this=$(this);
            var $sideBar=$(".page-sidebar-menu");
            // //选中子菜单
            // var ajaxUrl=$(this).attr("href");
            // if(ajaxUrl==="#" || ajaxUrl==="javascript:;"){
            //     return;
            // }
            //getPageData(ajaxUrl,"","page-content");
            $sideBar.find("li").removeClass("active open");
            $sideBar.find(".selected").remove();
            $this.closest("li").addClass("active open");
            $this.find(".title").after("<span class='selected' ></span>");
            return;
        });
    }
    /**
     * 初始化基本表格 v
     * @param tableId
     * @param queryUrl
     * @param columns
     * @returns {jQuery}
     */
    var initBootStrapTable=function(table,ajaxUrl,columns) {
        table.bootstrapTable({
            method: 'get',
            url: ajaxUrl,
            cache: false,
            dataType: "json",
            striped: false,	 //使表格带有条纹
            sortable: true,
            pagination: true,	//在表格底部显示分页工具栏
            pageSize: 10,
            pageNumber: 1,
            pageList: [10, 15, 20],
            idField: "id",  //标识哪个字段为id主键
            showToggle: false,   //名片格式
            cardView: false,//设置为True时显示名片（card）布局
            singleSelect: false,//复选框只能选择一条记录
            search: false,//是否显示右上角的搜索框
            clickToSelect: true,//点击行即可选中单选/复选框
            sidePagination: "server",//表格分页的位置
            queryParamsType: "", //参数格式,发送标准的RESTFul类型的参数请求
            strictSearch: true,
            showColumns: false,     //是否显示所有的列
            showRefresh: false,     //是否显示刷新按钮
            minimumCountColumns: 2,    //最少允许的列数
            responseHandler: function (res) {
                return {
                    "rows": res.dataList,
                    "total": res.total
                };
            },
            silent: true,  //刷新事件必须设置
            formatNoMatches: function () {  //没有匹配的结果
                return '无符合条件的记录';
            },
            rowStyle: function (row, index) {
                return {classes: "cursorHand"}
            },
            columns: columns
        });
    }
    /**
     * 弹出层
     */
    var layerOpen=function(title,width,height,content){
        return layer.open({
            type:2,
            skin:"qiyu-custom-layer",
            title: title,
            area: [width,height],
            fix: false, //不固定
            shadeClose: false,
            shade: 0.5,
            scrollbar: false,
            content: content
        });
    }
    /**
     * 刷新表格方法
     * @param tableId
     */
    var refreshTable = function (table) {
        table.bootstrapTable('refresh');
    }
    /**
     * 获取表格选中行
     * @param table
     */
    var getTableSelections=function (table) {
        return table.bootstrapTable('getSelections');
    }
    /**
     * bootstrapSweetAlert提示框--简单api
     */
    var bootstrapSweetAlert=function (sa_title,sa_message,sa_type) {
        swal({
            title: sa_title,
            text: sa_message,
            type: sa_type
        });
    }
    /**
     * form表单ajax提交封装方法
     */
    var qiYuAjaxFormSumbitTable=function (ajaxUrl,formId,pTableId) {
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
                    ajaxFormSubmit(ajaxUrl,formId,pTableId);
                } else {
                    swal("", "已经取消了当前操作", "error");
                }
            });
    }
    /**
     * Ajax操作
     * @param method
     * @param ajaxUrl
     * @param data
     * @param pTableId
     */
    var ajaxFormSubmit=function (ajaxUrl,formId,pTableId) {
        $.ajax({
            type:"POST",
            cache:false,
            url:ajaxUrl,
            data:$("#"+formId).serialize(),
            success:function(responseJson){
                if(responseJson.success==true){//返回true
                    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                    parent.layer.close(index);
                    var pTable = parent.$("#"+pTableId);
                    QiYuComponents.refreshTable(pTable);//刷新表格
                    (parent.QiYuComponents).bootstrapSweetAlert("",responseJson.msg,"success");
                }
                if(responseJson.success==false){//返回false
                    bootstrapSweetAlert("",responseJson.msg,"error");
                }
            },
            beforeSend:function(XMLHttpRequest){
                //请求之前方法增强处理 ,显示遮罩层
                App.blockUI({target: '#'+formId,animate: true});
            },
            complete:function(XMLHttpRequest, textStatus){
                //请求结束方法增强处理  ,隐藏遮罩层
                App.unblockUI('#'+formId);
            },
            error:function (XMLHttpRequest, textStatus, errorThrown) {
                bootstrapSweetAlert("","系统错误!!!","error");
            }
        });
    }
    
    var qiYuAjaxDeleteTable=function (ajaxUrl,table) {
        var rows =  QiYuComponents.getTableSelections(table);
        if(rows.length==0){
            QiYuComponents.bootstrapSweetAlert("","请选择一条记录","error");
            return;
        }
        swal({
                title: "确定删除?",
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
                    var idDataArray=[];
                    $.each(rows, function(i, obj) {
                        idDataArray.push(obj.id);
                    });
                    ajaxDelete(ajaxUrl,idDataArray,table);
                } else {
                    swal("", "已经取消了当前操作", "error");
                }
            });
    }

    var ajaxDelete=function (ajaxUrl,idDataArray,table) {
        $.ajax({
            type: 'DELETE',
            url: ajaxUrl,
            contentType: "application/json",//加入contentType,后端需要用requestBody接受参数,此时的参数不在request里面了
            data: JSON.stringify(idDataArray),
            dataType: "json",
            success: function (responseJson) {
                if(responseJson.success==true){
                    table.bootstrapTable('refresh');
                    QiYuComponents.bootstrapSweetAlert("",responseJson.msg,"success");
                }else{
                    QiYuComponents.bootstrapSweetAlert("",responseJson.msg,"error");
                }
            },
            error: function (response) {
                QiYuComponents.bootstrapSweetAlert("","系统错误,请联系管理员!!","error");
            }
        });
    }

    return{
        initIndexLeftMenu:function () {
            handleLeftMenuClick();
        },
        initBootStrapTable:function (table,ajaxUrl,columns) {
            initBootStrapTable(table,ajaxUrl,columns);
        },
        layerOpen:function (title,width,height,content) {
            layerOpen(title,width,height,content);
        },
        refreshTable:function (table) {
            refreshTable(table);
        },
        getTableSelections:function (table) {
            return getTableSelections(table);
        },
        bootstrapSweetAlert:function (sa_title,sa_message,sa_type) {
            bootstrapSweetAlert(sa_title,sa_message,sa_type);
        },
        qiYuAjaxFormSumbitTable:function (ajaxUrl,formId,pTableId) {
            qiYuAjaxFormSumbitTable(ajaxUrl,formId,pTableId);
        },
        qiYuAjaxDeleteTable:function (ajaxUrl,table) {
            qiYuAjaxDeleteTable(ajaxUrl,table);
        },
        getContextPath:function () {
            return getContextPath();
        },
        getPageData:function (ajaxUrl,data,targetClass) {
            getPageData(ajaxUrl,data,targetClass);
        }
    }
}();