package com.travel.web.controller;

import com.travel.domain.ResultInfo;
import com.travel.domain.User;
import com.travel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController
{
    @Autowired
    UserService userService;

    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo regist(User user, @RequestParam("check") String check, HttpSession session)
    {
        String checkcode = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if (checkcode == null || !checkcode.equalsIgnoreCase(check))
        {
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误!");
            return info;
        }
        boolean flag = userService.regist(user);
        ResultInfo info = new ResultInfo();
        if (flag)
        {
            info.setFlag(true);
        }
        else
        {
            info.setFlag(false);
            info.setErrorMsg("注册失败");
        }
        System.out.println(info);
        return info;
    }

    @RequestMapping(value = "/active")
    @ResponseBody
    public String active(@RequestParam("code") String code)
    {
        String msg = null;
        if(code!=null)
        {
            boolean flag = userService.active(code);
            if(flag)
            {
                msg="激活成功，请<a href='/login.html'>登录</a>";
            }
            else
            {
                msg="激活失败，请联系管理员！";
            }
        }
        return msg;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo login(User user, @RequestParam("check") String check, HttpSession session)
    {
        String checkCode = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if (checkCode == null || !checkCode.equalsIgnoreCase(check))
        {
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误!");
            return info;
        }
        User u = userService.login(user);
        ResultInfo info = new ResultInfo();
        if (u == null)
        {
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }
        else
        {
            if (!"Y".equals(u.getStatus()))
            {
                info.setFlag(false);
                info.setErrorMsg("用户未激活");
            }
            else
            {
                info.setFlag(true);
                session.setAttribute("user", u);
            }
        }
        return info;
    }

    @RequestMapping("/findOne")
    @ResponseBody
    public User findOne(HttpSession session)
    {
        return (User) session.getAttribute("user");
    }

    @RequestMapping("/exit")
    public String exit(HttpSession session)
    {
        session.invalidate();
        return "redirect: /login.html";
    }
}
