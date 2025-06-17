package com.devcambo.crudmybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User findById(int id) {
        return userMapper.findById(id);
    }

    public List<User> findAll() {
        return userMapper.findAll();
    }

    public void save(User user) {
        if (user.getId() == 0) {
            userMapper.insert(user);
        } else {
            userMapper.update(user);
        }
    }

    public void delete(int id) {
        userMapper.delete(id);
    }
}