package com.example.blog_2.repos;

import com.example.blog_2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface UserRepo extends CrudRepository<User,Long> {
    User findByUsername(String username);
    User findByActivationCode(String code);
}
