package com.travel.service.impl;

import com.github.pagehelper.PageHelper;
import com.travel.domain.Route;
import com.travel.mapper.RouteMapper;
import com.travel.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("routeService")
public class RouteServiceImpl implements RouteService
{
    @Autowired
    RouteMapper routeMapper;

    @Override
    public List<Route> findByPage(int cid, String rname, int currentPage, int pageSize)
    {
        PageHelper.startPage(currentPage,pageSize);
        return routeMapper.findByPage(cid,rname);
    }
}
