package com.crud.crud;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUser(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(long id){
        return userRepository.findById(id);
    }

    public User addUser(String name, String lastname){
        return userRepository.save(new User(name,lastname));
    }

    public Optional<User> updateUser(long id, String newName, String newLastName){
        return userRepository.findById(id).map(user->{
        user.setName(newName);
        user.setLastname(newLastName);
        return userRepository.save(user);
        });
    }

    public Optional<User> partialUpdate(long id, Map<String, Object>updates ){
        return userRepository.findById(id).map(user -> {
            updates.forEach((key,value) ->{
                if (value instanceof String){
                    switch (key){
                    case "name": user.setName((String)value);
                    break;
                        case "lastname": user.setLastname((String)value);
                    }
                }
            });
            return userRepository.save(user);
        });
    }

    public boolean deleteUser(long id){
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }
}

