package org.example.forum.service.Users;

import org.example.forum.service.Users.persistence.UserEntity;
import org.example.forum.service.Users.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public UserEntity register(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            return null;
        }

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole("user");
        return userRepository.save(user);
    }

    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
