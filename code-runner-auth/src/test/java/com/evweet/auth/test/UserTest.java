package com.evweet.auth.test;

import com.evweet.auth.dao.UserRepository;
import com.evweet.auth.enums.AuthType;
import com.evweet.auth.model.entity.RcUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class UserTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void createUser() {
        RcUser rcUser = new RcUser();
        rcUser.setUsername("admin");
        rcUser.setAuthType(AuthType.LOCAL);
        rcUser.setPassword(passwordEncoder.encode("admin"));

        userRepository.save(rcUser);
    }
}
