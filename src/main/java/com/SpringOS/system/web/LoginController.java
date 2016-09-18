package com.SpringOS.system.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.hibernate.mapping.Collection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Iterator;

@Controller
@RequestMapping(value = "/")
public class LoginController {

    @RequestMapping(value = "")
    public String index() {
        return "redirect:/home";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String showLoginForm(HttpServletRequest req, Model model) {
        String exceptionClassName = (String)req.getAttribute("shiroLoginFailure");
        String error = null;
        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(exceptionClassName != null) {
            error = "其他错误：" + exceptionClassName;
        }

//        Subject subject = SecurityUtils.getSubject();
//        if(subject.getSession() != null) {
//            subject.logout();
//        }else{
//            model.addAttribute("error", error);
//        }

        model.addAttribute("error", error);
        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        @RequestParam(defaultValue = "false") boolean rememberMe, Model model){

        String error = null;

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        subject.getSession(true);
        try {
            //4、登录，即身份验证
            token.setRememberMe(rememberMe);
            subject.login(token);
            return "redirect:/home";
        } catch (AuthenticationException e) {
            //5、身份验证失败
            error = e.getMessage();
        }
        model.addAttribute("error", error);
        return "login";
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(ServletRequest request, ServletResponse response){
//        Session ss;
//        Subject subject = SecurityUtils.getSubject();
//
//        subject.logout();
//        System.out.println("@@@####");
//        subject.getSession(true);
//        try {
//            WebUtils.issueRedirect(request, response, "/login");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return "login";
    }

}
