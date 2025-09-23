package com.example.qa;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Test
    void register_existingEmail_returnsFalse() {
        UserRepository repo = mock(UserRepository.class);
        when(repo.existsByEmail("a@a.com")).thenReturn(true);

        UserService service = new UserService(repo);

        assertFalse(service.register("a@a.com"));
        verify(repo).existsByEmail("a@a.com");
    }
}
