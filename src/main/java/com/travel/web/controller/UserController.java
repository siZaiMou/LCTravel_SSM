package com.travel.web.controller;

import com.github.pagehelper.PageInfo;
import com.travel.domain.Favorite;
import com.travel.domain.ResultInfo;
import com.travel.domain.Route;
import com.travel.domain.User;
import com.travel.service.FavoriteService;
import com.travel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController
{
    @Autowired
    UserService userService;

    @Autowired
    FavoriteService favoriteService;

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
        if (code != null)
        {
            boolean flag = userService.active(code);
            if (flag)
            {
                msg = "激活成功，请<a href='/login.html'>登录</a>";
            }
            else
            {
                msg = "激活失败，请联系管理员！";
            }
        }
        return msg;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo login(HttpServletResponse response, User user, @RequestParam("check") String check, @RequestParam(value = "autoLogin", required = false) boolean autoLogin, HttpSession session)
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
                if (autoLogin)
                {
                    Cookie cookie = new Cookie("auto_login", Integer.toString(u.getUid()));
                    cookie.setMaxAge(60 * 60 * 24 * 7);
                    response.addCookie(cookie);
                }
            }
        }
        return info;
    }

    @RequestMapping("/findOne")
    @ResponseBody
    public User findOne(@CookieValue(value = "auto_login", required = false) String uid_auto, HttpSession session)
    {
        if(!uid_auto.equals("0"))
        {
            User u = new User();
            u.setUid(Integer.parseInt(uid_auto));
            u = userService.login(u);
            ResultInfo info = new ResultInfo();
            info.setFlag(true);
            session.setAttribute("user", u);
        }
        return (User) session.getAttribute("user");
    }

    @RequestMapping("/exit")
    public String exit(HttpSession session,HttpServletResponse response)
    {
        response.addCookie(new Cookie("auto_login","0"));
        session.invalidate();
        return "redirect: /login.html";
    }

    @RequestMapping("/addFavorite")
    @ResponseBody
    public void addFavorite(@RequestParam(name = "rid") int rid, HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        if (user == null)
        {
            return;
        }
        favoriteService.addFavorite(rid, user.getUid());
    }

    @RequestMapping("/delFavorite")
    @ResponseBody
    public void delFavorite(@RequestParam(name = "rid") int rid, HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        if (user == null)
        {
            return;
        }
        favoriteService.delFavorite(rid, user.getUid());
    }

    @RequestMapping("/userFavorite")
    @ResponseBody
    public PageInfo<Route> userFavorite(HttpServletRequest request, HttpServletResponse response, @RequestParam(name = "currentPage", required = false, defaultValue = "1") int currentPage, @RequestParam(name = "pageSize", required = false, defaultValue = "4") int pageSize, HttpSession session) throws IOException
    {
        User user = (User) session.getAttribute("user");
        if (user == null)
        {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return null;
        }
        List<Route> routeList = favoriteService.userFavorite(user.getUid(), currentPage, pageSize);
        PageInfo<Route> pageInfo = new PageInfo<>(routeList);
        return pageInfo;
    }
}
