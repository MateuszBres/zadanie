package com.crud.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("")
    public List<User> getUser(){
        return userService.getUser();
    }

    @GetMapping("{id}")
    public Optional<User> getUser (@PathVariable long id){
        return userService.getUserById(id);
    }

    @PostMapping("")
    public User addUser(@RequestBody User user){
        return userService.addUser(user.getName(), user.getLastname());
    }

    @PutMapping("{id}")
    public Optional<User> updateUser(@PathVariable long id, @RequestBody User user){
        return userService.updateUser(id,user.getName(), user.getLastname());
    }

    @PatchMapping("{id}")
    public Optional<User> partialUpdateUser(@PathVariable long id, @RequestBody
                                            Map<String, Object>updates){
        return userService.partialUpdate(id,updates);
    }

    @DeleteMapping("{id}")
    public boolean deleteUser(@PathVariable long id){
       return userService.deleteUser(id);
    }
}
