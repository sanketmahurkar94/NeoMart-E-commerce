package com.piyush.Flicksy.repository;

import com.piyush.Flicksy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    User getByEmail(String email);

}
