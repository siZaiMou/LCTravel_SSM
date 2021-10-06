package com.travel.service;

import com.travel.domain.User;

public interface UserService
{
    User login(User user);

    boolean regist(User user);

    boolean active(String code);
}
