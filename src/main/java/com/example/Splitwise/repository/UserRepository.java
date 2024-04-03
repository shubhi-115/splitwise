package com.example.Splitwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Splitwise.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
}
