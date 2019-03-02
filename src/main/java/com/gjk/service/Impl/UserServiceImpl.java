package com.gjk.service.Impl;

import com.gjk.mapper.UserMapper;
import com.gjk.pojo.User;
import com.gjk.pojo.UserExample;
import com.gjk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User getUserById(int uid) {
        return userMapper.selectByPrimaryKey(uid);
    }

    @Override
    public User getUserByNameAndPass(String name, String pass) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andNameEqualTo(name)
                .andPasswordEqualTo(pass);
        List<User> users = userMapper.selectByExample(userExample);
        if(users.isEmpty()){
            return null;
        }
        return users.get(0);
    }

    @Override
    public User getUserByName(String name) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andNameEqualTo(name);
        List<User> users = userMapper.selectByExample(userExample);
        if(users.isEmpty()){
            return null;
        }
        return users.get(0);
    }

    @Override
    public boolean addUser(User user) {
        int res = userMapper.insert(user);
        if(res != 1){
            return  false;
        }
        return true;
    }

    @Override
    public List<User> getUserList() {
        UserExample userExample = new UserExample();
        return userMapper.selectByExample(userExample);
    }
}
