package com.exam.service;

import com.exam.model.User;
import com.exam.model.UserRole;

import java.util.Set;

public interface UserService {

    //creating user
    public User createUser(User user, Set<UserRole> userRole) throws Exception;

    //get user by username
    public User getUser(String userName);

    //delete user by id
    public void deleteUser(Long userId);
}
