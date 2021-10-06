package com.travel.mapper;

import com.travel.domain.Route;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RouteMapper
{
    List<Route> findByPage(@Param("cid")int cid, @Param("rname")String rname);

    Route findOne(int rid);
}
