package com.travel.mapper;

import com.travel.domain.Favorite;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface FavoriteMapper
{
    int findCountByRid(int rid);

    void add(@Param("rid") int rid, @Param("date") Date date, @Param("uid") int uid);

    Favorite findByRidAndUid(@Param("rid") int rid, @Param("uid") int uid);

    List<Integer> finByUid(int uid);
}
