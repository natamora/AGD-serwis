package com.ksundaysky.service;

import com.ksundaysky.model.User;

public interface UserService {
    public User findUserByEmail(String email);
    public User findUserById(int id);
    public void saveUser(User user);
    public void updateUser(User user);
}