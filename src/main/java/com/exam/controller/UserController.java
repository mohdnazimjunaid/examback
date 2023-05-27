package com.exam.controller;

import com.exam.helper.UserFoundException;
import com.exam.helper.UserNotFoundException;
import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //creating user
    @PostMapping("/")
    //data user me aaega
    public User createUser(@RequestBody User user) throws Exception {

              user.setProfile("default.org");
              //encoding password with bcrypt password encoder
                user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));



              Set<UserRole> roles=new HashSet<>();
              Role role=new Role();
              role.setRoleId(45L);
              role.setRoleName("NORMAL");

              UserRole userRole =new UserRole();
              userRole.setUser(user);
              userRole.setRole(role);
              roles.add(userRole);
              return this.userService.createUser(user, roles) ;
    }

    @GetMapping("/{userName}")
    public User getUser(@PathVariable("userName") String userName)
    {
        return this.userService.getUser(userName);
    }

    //delete the user by id
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Long userId)
    {
        this.userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK) ;
    }

    //update the user by username

   @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> exceptionHandler(UserNotFoundException ex){
        return ResponseEntity.ok(ex.getMessage());
    }

    @ExceptionHandler(UserFoundException.class)
    public ResponseEntity<?> exceptionHandler(UserFoundException ex){
        return ResponseEntity.ok(ex.getMessage());
    }

}
