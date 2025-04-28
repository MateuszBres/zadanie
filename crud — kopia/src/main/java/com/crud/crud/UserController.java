package com.crud.crud;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping("")
    public List<User> getUser(){
        return userService.getUser();
    }

    @GetMapping("/token")
    public CsrfToken csrfToken (HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");
    }



    @GetMapping("{id}")
    public Optional<User> getUser (@PathVariable long id){
        return userService.getUserById(id);
    }

    @PostMapping("")
    public ResponseEntity<String> add(@RequestBody User user) {
        userService.validateUniqueFields(user.getUsername(),user.getEmail(),user.getId());

        userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created");
    }



    @PutMapping("{id}")
    public ResponseEntity<String> update(@PathVariable long id, @RequestBody User user) {
        userService.validateUniqueFields(user.getUsername(),user.getEmail(),user.getId());
        userService.updateUser(id, user.getName(), user.getLastname(), user.getUsername(),
                user.getPassword(), user.getEmail(),user.getStatus().name());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("User updated");
    }
    @PatchMapping("{id}")
    public ResponseEntity<String> partialUpdate(@PathVariable long id, @RequestBody Map<String, Object> updates) {
        String username = (String) updates.get("username");
        String email = (String) updates.get("email");
        userService.validateUniqueFields(username, email, id);
        userService.partialUpdate(id, updates);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("User updated");
    }

    @DeleteMapping("{id}")
    public boolean deleteUser(@PathVariable long id){
       return userService.deleteUser(id);
    }






    @DeleteMapping("/all")
    public void deleteAll(){
        userService.deleteall();
    }


}
