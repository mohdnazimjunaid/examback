package com.exam.service.impl;

import com.exam.helper.UserFoundException;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.repo.RoleRepository;
import com.exam.repo.UserRepository;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceimpl implements UserService {

    @Autowired
    private UserRepository userrepository;

    @Autowired
    private RoleRepository roleRepository;

    //creating user
     @Override
    public User createUser(User user, Set<UserRole> userRole) throws Exception {

        User local=this.userrepository.findByUserName(user.getUserName());
        if(local!=null)
        {
            System.out.println("User is already there!!");
            throw new UserFoundException();
        }
        else {
            //user create
            for(UserRole ur:userRole)
            {
                roleRepository.save(ur.getRole()); //save roles
            }

            user.getUserRoles().addAll(userRole);  //assign all roles to user
            local = this.userrepository.save(user);

        }
        return local;
    }

    //getting user by username
    @Override
    public User getUser(String userName) {
        return this.userrepository.findByUserName(userName);
    }

    @Override
    public void deleteUser(Long userId) {
        this.userrepository.deleteById(userId);
    }
}
