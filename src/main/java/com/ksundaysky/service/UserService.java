package com.ksundaysky.service;

import com.ksundaysky.model.User;

public interface UserService {
    public User findUserByEmail(String email);
    public void saveUser(User user);
}