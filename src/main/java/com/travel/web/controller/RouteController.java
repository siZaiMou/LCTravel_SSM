package com.travel.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.travel.domain.Route;
import com.travel.domain.User;
import com.travel.service.FavoriteService;
import com.travel.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/route")
public class RouteController
{
    @Autowired
    RouteService routeService;

    @Autowired
    FavoriteService favoriteService;

    @RequestMapping("/pageQuery")
    @ResponseBody
    public PageInfo<Route> pageQuery(@RequestParam(name="currentPage",required = false,defaultValue = "1")int currentPage, @RequestParam(name="pageSize",required = false,defaultValue = "5")int pageSize,@RequestParam(name="cid",required = false,defaultValue = "0")int cid,@RequestParam(name="rname")String rname)
    {
        List<Route>routes = routeService.findByPage(cid,rname,currentPage,pageSize);
        PageInfo pageInfo = new PageInfo(routes);
        return pageInfo;
    }

    @RequestMapping("/findOne")
    @ResponseBody
    public Route findOne(@RequestParam(name="rid",required = true,defaultValue = "0")int rid)
    {
        Route route = routeService.findOne(rid);

        return route;
    }

    @RequestMapping("/isFavorite")
    @ResponseBody
    public boolean isFavorite(@RequestParam(name="rid",defaultValue = "0")int rid, HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        int uid = 0;
        if(user!=null)
        {
            uid = user.getUid();
        }
        return favoriteService.isFavorite(rid,uid);
    }

    @RequestMapping("/favoriteRank")
    @ResponseBody
    public PageInfo<Route> favoriteRank(@RequestParam(name="currentPage",required = false,defaultValue = "1")int currentPage, @RequestParam(name="pageSize",required = false,defaultValue = "8")int pageSize)
    {
        List<Route> routeList = favoriteService.favoriteRank(currentPage,pageSize);
        PageInfo<Route> pageInfo = new PageInfo<>(routeList);
        return pageInfo;
    }

}
