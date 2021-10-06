package com.travel.service.impl;

import com.travel.domain.User;
import com.travel.mapper.UserMapper;
import com.travel.service.UserService;
import com.travel.util.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("userService")
public class UserServiceImpl implements UserService
{
    @Autowired
    UserMapper userMapper;

    @Override
    public User login(User user)
    {
        return userMapper.findByUser(user);
    }

    @Override
    public boolean regist(User user)
    {
        User u = userMapper.findByUsername(user.getUsername());
        if(u!=null)
        {
            return false;
        }
        user.setCode(UUID.randomUUID());
        user.setStatus("N");
        userMapper.save(user);
        String content="<a href='http://127.0.0.1:8080/user/active?code="+user.getCode()+"'>点击激活LCTravel</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return true;
    }

    @Override
    public boolean active(String code)
    {
        User user = userMapper.findByCode(code);
        if(user!=null)
        {
            System.out.println(user);
            userMapper.updateStatus(user);
            return true;
        }
        return false;
    }
}
