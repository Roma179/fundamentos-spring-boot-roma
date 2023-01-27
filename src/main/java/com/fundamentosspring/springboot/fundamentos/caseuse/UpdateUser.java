package com.fundamentosspring.springboot.fundamentos.caseuse;

import com.fundamentosspring.springboot.fundamentos.entity.User;
import com.fundamentosspring.springboot.fundamentos.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UpdateUser {
    private UserService userService;

    public UpdateUser(UserService userService) {
        this.userService = userService;
    }

    public User update(User newUser, long id) {
        return userService.update(newUser,id);
    }
}
