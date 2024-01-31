package org.example.forum.service.Users.persistence;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u WHERE u.username = :username AND u.password = :password")
    UserEntity findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    boolean existsByUsername(String username);

    Optional<UserEntity> findById(Long id);
}


