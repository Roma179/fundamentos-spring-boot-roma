package com.fundamentosspring.springboot.fundamentos.configuration;

import com.fundamentosspring.springboot.fundamentos.caseuse.GetUser;
import com.fundamentosspring.springboot.fundamentos.caseuse.GetUserImplement;
import com.fundamentosspring.springboot.fundamentos.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaseUseConfiguration {
    @Bean
    GetUser getUser(UserService userService){
        return new GetUserImplement(userService);
    }
}
