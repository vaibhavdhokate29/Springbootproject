package com.example.automation.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.automation.Model.LoginModel;
import com.example.automation.Model.User;
import com.example.automation.Repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public LoginModel registerUser(@RequestBody LoginModel user) {
    	user.setRole_id(1);
    
        return userRepository.save(user);}
    

    @GetMapping("/getAll")
    public List<LoginModel> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/getById/{id}")
    public LoginModel getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @PutMapping("/update/{id}")
    public LoginModel updateUser(@PathVariable Long id, @RequestBody LoginModel user) {
        LoginModel existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            existingUser.setRole_id(user.getRole_id());
            existingUser.setBotNames(user.getBotNames());
            existingUser.setDepartmentNames(user.getDepartmentNames());
            return userRepository.save(existingUser);
        }
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "User deleted with id : " + id;
    }
}

