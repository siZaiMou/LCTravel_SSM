package com.travel.service;

import com.travel.domain.Route;

import java.util.List;

public interface RouteService
{
    List<Route> findByPage(int cid, String rname, int currentPage, int pageSize);

    Route findOne(int rid);
}
