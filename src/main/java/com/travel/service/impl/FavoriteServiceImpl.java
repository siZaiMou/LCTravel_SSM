package com.travel.service.impl;

import com.github.pagehelper.PageHelper;
import com.travel.domain.Favorite;
import com.travel.domain.Route;
import com.travel.mapper.FavoriteMapper;
import com.travel.mapper.RouteMapper;
import com.travel.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("favoriteService")
public class FavoriteServiceImpl implements FavoriteService
{
    @Autowired
    FavoriteMapper favoriteMapper;

    @Autowired
    RouteMapper routeMapper;

    @Override
    public void addFavorite(int rid, int uid)
    {
        favoriteMapper.add(rid,new Date(),uid);
        int count = favoriteMapper.findCountByRid(rid);
        System.out.println("count:"+count);
        routeMapper.updateCountByRid(rid,count);
    }

    @Override
    public boolean isFavorite(int rid, int uid)
    {
        return favoriteMapper.findByRidAndUid(rid,uid)!=null;
    }

    @Override
    public List<Route> userFavorite(int uid, int currentPage, int pageSize)
    {
        List<Integer> ridList = favoriteMapper.finByUid(uid);
        PageHelper.startPage(currentPage,pageSize);
        return routeMapper.findByRids(ridList);
    }

    @Override
    public List<Route> favoriteRank(int currentPage, int pageSize)
    {
        PageHelper.startPage(currentPage,pageSize);
        return routeMapper.findRoutesByRank();
    }


}
