package com.example.blog_2.repos;

import com.example.blog_2.models.Messages;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Messages,Long> {
    List<Messages> findByTag(String tag);
}
