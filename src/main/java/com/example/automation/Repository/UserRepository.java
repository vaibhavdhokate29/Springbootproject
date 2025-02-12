package com.example.automation.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.automation.Model.LoginModel;
import com.example.automation.Model.User;

public interface UserRepository extends JpaRepository<LoginModel, Long> {
}

