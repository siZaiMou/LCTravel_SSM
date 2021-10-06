package com.travel.service.impl;

import com.github.pagehelper.PageHelper;
import com.travel.domain.Route;
import com.travel.domain.RouteImg;
import com.travel.domain.Seller;
import com.travel.mapper.FavoriteMapper;
import com.travel.mapper.RouteImgMapper;
import com.travel.mapper.RouteMapper;
import com.travel.mapper.SellerMapper;
import com.travel.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("routeService")
public class RouteServiceImpl implements RouteService
{
    @Autowired
    RouteMapper routeMapper;

    @Autowired
    RouteImgMapper routeImgMapper;

    @Autowired
    SellerMapper sellerMapper;

    @Autowired
    FavoriteMapper favoriteMapper;

    @Override
    public List<Route> findByPage(int cid, String rname, int currentPage, int pageSize)
    {
        PageHelper.startPage(currentPage,pageSize);
        return routeMapper.findByPage(cid,rname);
    }

    @Override
    public Route findOne(int rid)
    {
        Route route = routeMapper.findOne(rid);
        List<RouteImg> routeImgList = routeImgMapper.findByRid(rid);
        Seller seller = sellerMapper.findById(route.getSid());
        int favoriteCount = favoriteMapper.findCountByRid(rid);
        route.setRouteImgList(routeImgList);
        route.setSeller(seller);
        route.setCount(favoriteCount);
        return route;
    }
}
