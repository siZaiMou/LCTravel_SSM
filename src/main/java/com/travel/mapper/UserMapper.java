package com.travel.mapper;

import com.travel.domain.User;

import java.util.List;

public interface UserMapper
{
    User findByUser(User user);

    User findByUsername(String username);

    void save(User user);

    User findByCode(String code);

    void updateStatus(User user);
}
