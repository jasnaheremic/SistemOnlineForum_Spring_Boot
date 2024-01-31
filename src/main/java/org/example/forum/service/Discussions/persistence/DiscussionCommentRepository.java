package org.example.forum.service.Discussions.persistence;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface DiscussionCommentRepository extends JpaRepository<DiscussionCommentEntity, Long> {
    List<DiscussionCommentEntity> findByDiscussionIdOrderByCommentDateAsc(Long discussionId);
    @Transactional
    void deleteByDiscussionId(Long discussionId);
}
