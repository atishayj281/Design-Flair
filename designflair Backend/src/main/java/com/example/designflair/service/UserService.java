package com.example.designflair.service;

import com.example.designflair.model.User;

import java.security.NoSuchAlgorithmException;
import java.util.List;


public interface UserService {
    public User getUserById(long id);
    public User addUser(User user) throws NoSuchAlgorithmException;
    public List<User> getAll();
    public long getUserIdByUsername(String username) throws NoSuchAlgorithmException;
}
