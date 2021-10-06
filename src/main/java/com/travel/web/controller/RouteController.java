package com.travel.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.travel.domain.Route;
import com.travel.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/route")
public class RouteController
{
    @Autowired
    RouteService routeService;

    @RequestMapping("/pageQuery")
    @ResponseBody
    public PageInfo<Route> pageQuery(@RequestParam(name="currentPage",required = false,defaultValue = "1")int currentPage, @RequestParam(name="pageSize",required = false,defaultValue = "5")int pageSize,@RequestParam(name="cid",required = false,defaultValue = "0")int cid,@RequestParam(name="rname")String rname)
    {
        List<Route>routes = routeService.findByPage(cid,rname,currentPage,pageSize);
        PageInfo pageInfo = new PageInfo(routes);
        return pageInfo;
    }
}
