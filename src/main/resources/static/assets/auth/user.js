//@ sourceURL=user.js
/**
 * 用户模块js:包括用户主界面、用户增加修改界面
 */
var QiYuUser = function () {
    //用户主页面属性开始
    var userTableId = "userTable";
    var $userTable=$("#userTable");
    var tableAjaxUrl="user/page/list";
    var userTableColum=[{checkbox: true}
                        ,{title: 'id',field: 'id',align: 'center',valign: 'middle',visible:false}
                        ,{title: '登录账号',field: 'loginName',align: 'center',valign: 'middle'}
                        ,{title: '用户名称',field: 'userName',align: 'center',valign: 'middle'}
                        ,{title: '用户性别',field: 'gender',align: 'center',valign: 'middle',formatter:function (value,row,index) {
                            if(value==1){return "男";}else if(value==-1){return "女";}else{return "未知";}
                        }}
                        ,{title: '用户状态',field: 'isLock',align: 'center',formatter:function (value,row,index) {
            if(value==1){return "正常";}else if(value==-1){return "锁定";}else{return "未知";}
        }}];
    var $addUserView=$("#addUserView");
    var $updateUserView=$("#updateUserView");
    var $deleteUsers=$("#deleteUsers");
    //用户主页面属性结束
    //用户修改页面属性开始
    var $updateUserInfo = $("#updateUserInfo");//修改用户信息按钮
    var $resetUserInfo = $("#resetUserInfo");//取消用户信息按钮
    var updateUserFormId="updateUserForm";//个人信息表单
    var $updateUserForm=$("#updateUserForm");
    var $updateUserBtn = $("#updateUserBtn");
    var $inputDom=$("#imgUpload");//图片input dom元素
    var uploadAjaxUrl=QiYuComponents.getContextPath()+"/fastdfs/upload/photo";//上传图片地址
    var updateUserPwdFormId="updateUserPwdForm";
    var $updateUserPwdForm = $("#updateUserPwdForm");//修改密码表单
    var $updateUserPwdBtn = $("#updateUserPwdBtn");//修改密码按钮
    //用户修改页面属性结束

    //用户增加页面属性开始
    var $cancelUserBtn = $("#cancelUserBtn");
    var $addUserBtn = $("#addUserBtn");
    var addUserFormId = "addUserForm";
    var $addUserForm = $("#addUserForm");
    //用户增加页面属性结束

    //部门管理模块-用户列表开始
    var $depUserTable = $("#depUserTable");
    //部门管理模块-用户列表结束

    /**
     * 用户主页面初始化用户表格数据
     */
    var initTable=function () {
        QiYuComponents.initBootStrapTable($userTable,tableAjaxUrl,userTableColum);
    }
    /**
     * 用户主页面按钮绑定事件:增加、修改、删除
     */
    var userMainEventHandler=function () {
        //绑定增加用户弹出页面的按钮事件
        $addUserView.on("click",function () {
            QiYuComponents.layerOpen("添加账户",'1000px','650px',"user/add/view");
        });
        //绑定修改用户弹出页面的按钮事件
        $updateUserView.on("click",function () {
           var rows =  QiYuComponents.getTableSelections($userTable);
            if(rows.length==0){
                QiYuComponents.bootstrapSweetAlert("","请选择一个用户","error");
                return;
            }
            if(rows.length>=2){
                QiYuComponents.bootstrapSweetAlert("","不能选择多个用户","error");
                return;
            }
            //查看用户的基本信息
            QiYuComponents.layerOpen("配置账户",'1000px','650px',"user/profile/view/"+rows[0].id+"?operator=1");
        });
        //绑定删除按钮事件
        $deleteUsers.on("click",function () {
            QiYuComponents.qiYuAjaxDeleteTable("user/batch",$userTable);
        });
    }
    /**
     * 用户修改页面事件绑定
     */
    var userProfileEventHandler = function () {

        $updateUserInfo.on("click",function () {
            
        });
    }
    /**
     * 用户增加页面事件绑定
     */
    var userAddEventHandler = function () {
        $addUserBtn.on("click",function () {
            $addUserForm.submit();
        });
    }
    /**
     * 用户增加页面验证表单
     */
    var initUserAddBootStrapValidate = function (addAjaxUrl) {
        $addUserForm.bootstrapValidator({
//          trigger: 'blur',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                userCode: {
                    validators: {
                        notEmpty: {
                            message: '员工编码不能为空'
                        }
                    }
                },
                loginName: {
                    validators: {
                        notEmpty: {
                            message: '登录账号不能为空'
                        },
                        remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                            url: QiYuComponents.getContextPath()+'/user/uniqueness/loginName',//验证地址
                            message: '账号已存在',//提示消息
                            delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                            type: 'POST'//请求方式
                        }
                    }
                },
                userName:{
                    validators: {
                        notEmpty: {
                            message: '员工名称不能为空'
                        }
                    }
                }
            }
        }).on("success.form.bv",function(e){
            QiYuComponents.qiYuAjaxFormSumbitTable(addAjaxUrl,addUserFormId,userTableId);
        });
    }

    /**
     * 用户修改页面验证表单
     */
    var initUserUpdateBootStrapValidate = function (updateAjaxUrl) {
        $updateUserForm.bootstrapValidator({
//          trigger: 'blur',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                userCode: {
                    validators: {
                        notEmpty: {
                            message: '员工编码不能为空'
                        }
                    }
                },
                userName:{
                    validators: {
                        notEmpty: {
                            message: '员工名称不能为空'
                        }
                    }
                }
            }
        }).on("success.form.bv",function(e){
            QiYuComponents.qiYuAjaxFormSumbitTable(updateAjaxUrl,updateUserFormId,userTableId);
        });
    }

    /**
     * 用户修改密码页面验证表单
     */
    var initUserPwsUpdateBootStrapValidate = function (updateAjaxUrl) {
        $updateUserPwdForm.bootstrapValidator({
//          trigger: 'blur',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                loginPwd:{
                    validators: {
                        notEmpty: {
                            message: '当前不能为空'
                        }
                    }
                },
                currentPwd:{
                    validators: {
                        notEmpty: {
                            message: '新密码不能为空'
                        }
                    }
                },
                loginRepPwd:{
                    validators: {
                        notEmpty: {
                            message: '新重复密码不能为空'
                        },
                        identical: {//相同
                            field: 'loginPwd', //需要进行比较的input name值
                            message: '两次密码不一致'
                        }
                    }
                }
            }
        }).on("success.form.bv",function(e){
            QiYuComponents.qiYuAjaxFormSumbitTable(updateAjaxUrl,updateUserPwdFormId,userTableId);
        });
    }

    /**
     * 用户增加页面事件绑定
     */
    var userUpdateEventHandler = function () {
        $updateUserBtn.on("click",function () {
            $updateUserForm.submit();
        });

        $updateUserPwdBtn.on("click",function () {
            $updateUserPwdForm.submit();
        });
    }

    /**
     * 上传头像
     * @param $inputDom
     * @param uploadAjaxUrl
     */
    var initBootStrapFileInput=function () {
        $inputDom
            .fileinput({
                language: 'zh',
                uploadUrl: uploadAjaxUrl,
                autoReplace: true,
                maxFileCount: 1,
                maxFileSize : 2000,
                uploadAsync: true,
                enctype: 'multipart/form-data',
                dropZoneTitle:"可以拖拽头像到这里,快到碗里来!!!",
                allowedFileExtensions: ["jpg", "png", "gif"],
                browseClass: "btn green btn-outline sbold", //按钮样式
                removeClass:"btn red btn-outline sbold",
                uploadClass:"btn purple btn-outline sbold",
                previewFileIcon: "<i class='glyphicon glyphicon-king'></i>"
            })
            .on("fileuploaded", function (e, data) {
                var res = data.response;
                if (res.success==true) {
                    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                    parent.layer.close(index);
                    (parent.QiYuComponents).bootstrapSweetAlert("",res.msg,"success");
                }
                else {
                    QiYuComponents.bootstrapSweetAlert("",res.msg,"error");
                }
            })
    }
    
    var initDepUserTable = function (depId) {
        QiYuComponents.initBootStrapTable($depUserTable,tableAjaxUrl+"?depId="+depId,userTableColum);
    }


    return{
        //初始化用户管理的主页面:用户列表
        initUserMain:function () {
            initTable();
            userMainEventHandler();
        },
        //初始化用户管理的修改页面:user_profile.html
        initUserProfile:function (updateAjaxUrl) {
            initUserUpdateBootStrapValidate(updateAjaxUrl);
            userUpdateEventHandler();
            initUserPwsUpdateBootStrapValidate(updateAjaxUrl);

        },
        //初始化用户增加页面
        initUserAdd:function (addAjaxUrl) {
            initUserAddBootStrapValidate(addAjaxUrl);
            userAddEventHandler();
        },
        //初始化上传图片插件
        initUserUploadPhoto:function () {
            initBootStrapFileInput();
        },
        //初始化部门管理模块下的用户列表
        initDepUserTable:function (depId) {
            initDepUserTable(depId);
        }
    }
}();
