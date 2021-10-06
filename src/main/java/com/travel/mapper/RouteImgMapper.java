package com.travel.mapper;

import com.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgMapper
{
    List<RouteImg> findByRid(int rid);
}
