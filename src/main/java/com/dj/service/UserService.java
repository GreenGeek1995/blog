package com.dj.service;

import com.dj.po.User;
import org.springframework.stereotype.Service;


public interface UserService {
    User checkUser(String username,String password);
}
