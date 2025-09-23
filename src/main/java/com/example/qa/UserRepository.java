package com.example.qa;

public interface UserRepository {
    boolean existsByEmail(String email);
}
