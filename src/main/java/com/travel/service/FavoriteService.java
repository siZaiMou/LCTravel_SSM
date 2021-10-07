package com.travel.service;

import com.travel.domain.Route;

import java.util.List;

public interface FavoriteService
{
    void addFavorite(int rid, int uid);

    void delFavorite(int rid, int uid);

    boolean isFavorite(int rid, int uid);

    List<Route> userFavorite(int uid, int currentPage, int pageSize);

    List<Route> favoriteRank(int currentPage, int pageSize);

}
