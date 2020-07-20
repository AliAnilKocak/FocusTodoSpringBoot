package com.cale.focustodo;

import com.cale.focustodo.entity.ApplicationUser;
import com.cale.focustodo.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class FocusTodoApplication {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    public void seedUser() {
        if (userRepository.findByUsername("test") == null) {
            String encodedPassword = passwordEncoder.encode("123123");

            ApplicationUser user = new ApplicationUser(
                    1
                    , "test"
                    , "alianilkocak@gmail.com"
                    , encodedPassword
                    , encodedPassword);

            userRepository.save(user);
        }
    }

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }


    public static void main(String[] args) {
        SpringApplication.run(FocusTodoApplication.class, args);
    }


}
