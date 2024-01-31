package org.example.forum.service.Discussions.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscussionRepository extends JpaRepository<DiscussionEntity, Long> {
    List<DiscussionEntity> findAll();
    Optional<DiscussionEntity> findById(Long id);
}
