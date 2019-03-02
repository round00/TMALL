package com.gjk.service;

import com.gjk.pojo.User;

import java.util.List;

public interface UserService {
    /**
     * 通过ID获取用户对象
     * */
    User getUserById(int uid);
    /**
     * 通过用户名和密码获取对象
     * */
    User getUserByNameAndPass(String name, String pass);
    /**
     * 通过用户名获取对象
     * */
    User getUserByName(String name);
    /**
     * 增加一个用户
     * */
    boolean addUser(User user);
    /**
     * 获得用户列表
     * */
    List<User> getUserList();
}
