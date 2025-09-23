package com.example.qa;

public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public boolean register(String email) {
        if (repo.existsByEmail(email)) return false;
        return true;
    }
}
