package com.xxm.service;

import com.xxm.entity.UserInfo;
import com.xxm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<UserInfo> getUsers() {
        return repository.findAll();
    }
}
