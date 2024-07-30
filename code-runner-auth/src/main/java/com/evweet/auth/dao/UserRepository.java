package com.evweet.auth.dao;

import com.evweet.auth.model.entity.RcUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<RcUser, Long>{
    Optional<RcUser> findByUsername(String username);
    Optional<RcUser> findByOauth2Id(String oauth2Id);
}
