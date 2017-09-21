package com.cwsm.home.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页和登录基控制器
 * @author zhangqing
 */
@Controller
public class HomeController {


//    @Value(value = "${qiyu.default.small.photo.path}")
//    private String defaultPath;



    private Logger logger = LoggerFactory.getLogger(HomeController.class);
    /**
     * 根目录
     * @return
     */
    @RequestMapping("/")
    public String root() {
        return "redirect:/index";
    }

//    /**
//     * 首页
//     * @return
//     */
//    @RequestMapping("/index")
//    public String index(ModelMap modelMap) throws Exception{
//        SysFile sysFile = new SysFile();
//        sysFile.setParentId(ShiroManager.getUserInfo().getId());//根据用户主键查询头像
//        //List<SysFile> sysFileList = sysFileService.select(sysFile);
//        List<SysFile> sysFileList=null;
//        if(HelpUtils.isNotEmpty(sysFileList)){
//            modelMap.put("filePath","http://121.42.166.32/"+sysFileList.get(0).getFilePath());
//        }else{
//            modelMap.put("filePath",defaultPath);
//        }
//
//        modelMap.addAttribute("onlineCount",sessionService.selectOnlineCount());
//        return "index";
//    }
//    /**
//     * 登录页面
//     * @return
//     */
//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public String login(String message, ModelMap modelMap) {
//        if(HelpUtils.isNotEmpty(message)){
//            modelMap.addAttribute("message",message);
//        }
//        return "login";
//    }
//
//    /**
//     * 登录动作
//     * @param user
//     * @return
//     */
//    @RequestMapping(value = "/login",method = RequestMethod.POST)
//    public String login(HttpServletRequest req, User user, RedirectAttributes attr){
//        if(HelpUtils.isEmpty(user.getLoginName()) || HelpUtils.isEmpty(user.getLoginPwd())){
//            attr.addAttribute("message","用户名或密码不能为空");
//            return "redirect:/login";
//        }
//        String userName = user.getLoginName();
//        //开始调用shiro验证
//        UsernamePasswordToken token = new UsernamePasswordToken(userName,user.getLoginPwd(),"login");
//        //获取当前的subject
//        Subject currentUser = ShiroManager.getSubject();
//        try {
//            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
//            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
//            //所以这一步在调用login(token)方法时,它会走到doGetAuthenticationInfo()方法中,具体验证方式详见此方法
//            logger.info("对用户[" + userName + "]进行登录验证..验证开始");
//            currentUser.login(token);
//            logger.info("对用户[" + userName + "]进行登录验证..验证通过");
//            ShiroManager.getSession().setAttribute("userInfo",ShiroManager.getUserInfo());
//            ShiroManager.getSession().setAttribute("loginName",ShiroManager.getLoginName());
//        }catch(UnknownAccountException uae){
//            logger.info("对用户[" + userName + "]进行登录验证..验证未通过,未知账户");
//            attr.addAttribute("message", "用户名不存在");
//        }catch(IncorrectCredentialsException ice){
//            logger.info("对用户[" + userName + "]进行登录验证..验证未通过,错误的凭证");
//            attr.addAttribute("message", "密码不正确");
//        }catch(LockedAccountException lae){
//            logger.info("对用户[" + userName + "]进行登录验证..验证未通过,账户已锁定");
//            attr.addAttribute("message", "账户已锁定");
//        }catch(ExcessiveAttemptsException eae){
//            logger.info("对用户[" + userName + "]进行登录验证..验证未通过,错误次数过多");
//            attr.addAttribute("message", "用户名或密码错误次数过多");
//        }catch(AuthenticationException ae){
//            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
//            logger.info("对用户[" + userName + "]进行登录验证..验证未通过,堆栈轨迹如下");
//            ae.printStackTrace();
//            attr.addAttribute("message", "用户名或密码不正确");
//        }
//        if(req.getParameter("forceLogout") != null) {
//            attr.addAttribute("error", "您已经被管理员强制退出，请重新登录");
//            return "redirect:/login";
//        }
//        //验证是否登录成功
//        if(currentUser.isAuthenticated()){
//            return "redirect:/index";
//        }else{
//            token.clear();
//            return "redirect:/login";
//        }
//    }

}

